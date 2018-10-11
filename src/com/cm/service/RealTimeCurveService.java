package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.controller.ResultObj;
import com.cm.dao.AnalogStatisticsDao;
import com.cm.dao.DevLinkDao;
import com.cm.dao.IFeedbackDao;
import com.cm.dao.ISwitchDao;
import com.cm.entity.Feedback;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.AnalogParamVo;
import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AnalogStatisticsVo;
import com.cm.entity.vo.AnaloghisVo;
import com.cm.entity.vo.CutFeedVo;
import com.cm.entity.vo.DevVo;
import com.cm.entity.vo.SensorVo;

@Service(value = "RealTimeCurve")
public class RealTimeCurveService implements SingleCurveInterface {

	@Autowired
	private ResultObj result;
	@Autowired
	private AnalogHistoryService analogHistoryService;
	@Autowired
	private DevLinkDao devLinkDao;
	@Autowired
	private StatusCurveService scService;
	@Autowired
	private ISwitchDao switchDao;
	@Autowired
	private IFeedbackDao fdDao;
	@Autowired
	private AnalogStatisticsDao asDao;
	
	private List<AnaloghisVo> resultList;
	private double max;
	private Integer type;
	private String alarmEndTime;
	private Map<String, CopyOnWriteArrayList<AnalogQueryVo>> map;
	private Map<String,List<Feedback>> feedMap;
	private Map<Integer,SwitchSensor> switchSensorMap;
	private List<String> cutDevScope;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取模拟量数据
	 */
	public ResultObj getData(AnalogParamVo analogParamVo) {
		//初始化参数
		init();
		type = analogParamVo.getType();
		//获取传感器
		List<DevVo> ldv = setDlv(analogParamVo);
		List<SensorVo> sensors = analogHistoryService.getSensor(ldv);
		SensorVo sensorVo = UtilMethod.notEmptyList(sensors) ? sensors.get(0) : null;
		if (sensorVo == null) {
			result.setStatus(1, "无该传感器");
			return result;
		}
		//断电范围
		cutDevScope = devLinkDao.getCutDevScope(analogParamVo.getDevid(), analogParamVo.getIp());
		//传感器基本信息设置
		setData(sensorVo);
		//位置筛选
		if(analogParamVo.getPositionId()!= 0 && analogParamVo.getPositionId() != sensorVo.getPositionId()){
			result.put("max", 0);
			result.put("data", resultList);
			result.put("feedData", new ArrayList<>());
			result.setStatus(0, "ok");
			return result;
		}
		//设置曲线开始结束时间
		setTime(analogParamVo);
		//获取设备上报数据
		getDataList(analogParamVo);
		//计算最大最小平均值及设置报警、断馈电状态
		if(UtilMethod.notEmptyList(resultList) && sensors.size() > 0){
			List<AnalogStatisticsVo> fiveMinutesData = null;
			if (type != null && type == 1) {
				fiveMinutesData = asDao.getOneHourData(analogParamVo.getIp(),analogParamVo.getDevid(), analogParamVo.getStartTime(), analogParamVo.getEndTime());
			}
			//获取设备在查询时间内的报警记录
			List<AnalogQueryVo> analogQry = analogHistoryService.getAnalogQry(analogParamVo.getDevid(),analogParamVo.getIp(),
					analogParamVo.getStartTime(),analogParamVo.getEndTime());
			alarmEndTime = analogHistoryService.getAlarmEndTime(analogParamVo.getStartTime(), analogParamVo.getDevid(),analogParamVo.getIp());
			//生成报警数据map
			if(analogQry != null && analogQry.size() > 0){
				generateAlarmMap(analogQry);
			}
			//获取断馈电信息
			@SuppressWarnings("unused")
			Set<Integer> cutDevIdSet = generateCutFeedMap(analogParamVo);
			
			//获取所有开关量
			List<SwitchSensor> allSwitchSensor = switchDao.AllSwitchSensor();
			if(UtilMethod.notEmptyList(allSwitchSensor)){
				for(SwitchSensor ss : allSwitchSensor){
					switchSensorMap.put(ss.getId(), ss);
				}
			}
			
			//获取时间段内最大值及设置数据状态
			getMaxAndSetStatus(resultList, sensorVo, fiveMinutesData);

			if(analogParamVo.getMax() != null)
				max = (max > analogParamVo.getMax()) ? max : analogParamVo.getMax();
		}else{
			resultList = new ArrayList<AnaloghisVo>();
			result.put("feedData", new ArrayList<>());
		}
		if(UtilMethod.notEmptyList(resultList)){
			// 获取绑定的断馈电仪的断电数据
			getCutCurveData(analogParamVo);
		}
		result.put("max", max);
		result.put("data", resultList);
		result.setStatus(0, "ok");
		return result;
	}
	
	//初始化参数
	public void init(){
		resultList = new ArrayList<AnaloghisVo>();
		switchSensorMap = new HashMap<Integer,SwitchSensor>();
		map = new HashMap<String, CopyOnWriteArrayList<AnalogQueryVo>>();
		max = 0;
		alarmEndTime = null;
	}
	
	public Set<Integer> generateCutFeedMap(AnalogParamVo analogParamVo){
		//获取断馈电信息
		List<Feedback> cutFeedStatus = fdDao.getCutStatus(analogParamVo.getIp(), analogParamVo.getDevid(), 0,
				analogParamVo.getStartTime(), analogParamVo.getEndTime());
		feedMap = new HashMap<String,List<Feedback>>();
		Set<Integer> cutDevIdSet = new HashSet<Integer>();
		if(UtilMethod.notEmptyList(cutFeedStatus)){
			for(Feedback fb : cutFeedStatus){
				if(fb.getFeedstatus() == 1){
					cutDevIdSet.add(fb.getCut_devid());
				}
				String key = fb.getIp() + ":" + fb.getDevid() + ":" + fb.getResponsetime(); 
				List<Feedback> list = feedMap.get(key);
				if(null == list)
					list = new ArrayList<Feedback>();
				list.add(fb);
				feedMap.put(key, list);
			}
		}
		return cutDevIdSet;
	}
	
	// 获取绑定的断馈电仪断电数据
	public void getCutCurveData(AnalogParamVo analogParamVo){
		String ip = analogParamVo.getIp();
		int devid = analogParamVo.getDevid();
		List<CutFeedVo> switchSensor = fdDao.getCutFeedSensor(ip, devid);
		if(UtilMethod.notEmptyList(switchSensor)){
			List<CutFeedVo> feedDataList = new ArrayList<CutFeedVo>();
			for(CutFeedVo cf : switchSensor){
				String position = cf.getPosition() == null ? "位置未配置" : cf.getPosition();
				cf.setDetailDescription(cf.getAlais() + "/" + cf.getSensorTypeName() + "/" + position);
				List<Feedback> feedList = fdDao.getCutStatus(analogParamVo.getIp(), analogParamVo.getDevid(), cf.getId(),
						analogParamVo.getStartTime(),analogParamVo.getEndTime());
				LinkedHashMap<String, Integer> valueMap = getValueMap(feedList);
				List<Map<String,String>> feedData = new ArrayList<Map<String,String>>();
				for(Entry<String, Integer> kv : valueMap.entrySet()){
					Map<String,String> map = new HashMap<String,String>();
					map.put("starttime", kv.getKey());
					map.put("avalue", kv.getValue()+"");
					feedData.add(map);
				}
				cf.setDataList(feedData);
				feedDataList.add(cf);
			}
			result.put("feedData", feedDataList);
		}else{
			result.put("feedData", new ArrayList<CutFeedVo>());
		}
	}
	
	// 基础数据处理：如果开关量状态无变化，数据一分钟取一条，debug数据全取
	public LinkedHashMap<String, Integer> getValueMap(List<Feedback> feedList){
		LinkedHashMap<String, Integer> valueMap = new LinkedHashMap<String, Integer>();
		int status = -1;
		int count = 0;
		Date minutsTime = null;
		for(Feedback av : feedList){
			count ++;
			if (status == -1) {//获取第一条数据保存
				status = av.getIs_cut();
				try {
					minutsTime = df.parse(av.getResponsetime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				valueMap.put(av.getResponsetime(), status);
			} else {
				if(av.getIs_cut() != status){//获取状态变化数据
					valueMap.put(av.getResponsetime(), av.getIs_cut());
					status = av.getIs_cut();
				} else {
					try {//状态无变化的数据一分钟取一条
						if(av.getIs_cut() == status && minutsTime.getTime() != df.parse(av.getResponsetime()).getTime()){
							valueMap.put(av.getResponsetime(), av.getIs_cut());
							minutsTime = df.parse(av.getResponsetime());
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(count == resultList.size()){
				valueMap.put(av.getResponsetime(), av.getIs_cut());
			}
		}
		return valueMap;
	}
	
	// 获取原始数据
	public void getDataList(AnalogParamVo analogParamVo){
		if(analogParamVo.getCur_page() > 0 && analogParamVo.getPage_rows() > 0){
			analogParamVo.setCur_page((analogParamVo.getCur_page() - 1) * analogParamVo.getPage_rows());
			int totalRec = analogHistoryService.getTotalRec(analogParamVo);
			analogParamVo.setTotal_record(totalRec);
			analogParamVo.setTotal_pages(totalRec/analogParamVo.getPage_rows() + 1);
			result.put("page", analogParamVo);
		}
		
		resultList = analogHistoryService.getRealTimeRec(analogParamVo);
	}
	
	// 设置曲线开始结束时间及判断是否是密采曲线
	public void setTime(AnalogParamVo analogParamVo){
		String endTime = analogParamVo.getEndTime() != null ? analogParamVo.getDay().concat(" ").concat(analogParamVo.getEndTime())
				: UtilMethod.getNow();
		analogParamVo.setEndTime(endTime);
		analogParamVo.setStartTime(analogParamVo.getDay().concat(" ").concat(analogParamVo.getStartTime()));
	}
	
	public void generateAlarmMap(List<AnalogQueryVo> analogQry){
		for(AnalogQueryVo aqv: analogQry){
			if(Math.abs(aqv.getStatus()) == 4 || Math.abs(aqv.getStatus()) == 3 || Math.abs(aqv.getStatus()) == 2 || aqv.getStatus() == 5){
				CopyOnWriteArrayList<AnalogQueryVo> list = map.get("alarm");
				if(list == null) 
					list = new CopyOnWriteArrayList<AnalogQueryVo>();
				list.add(aqv);
				map.put("alarm", list);
			}
		}
	}

	public List<DevVo> setDlv(AnalogParamVo analogParamVo){
		List<DevVo> ldv = new ArrayList<DevVo>();
		DevVo dv1 = new DevVo();
		dv1.setDevid(analogParamVo.getDevid());
		dv1.setIp(analogParamVo.getIp());
		ldv.add(dv1);
		return ldv;
	}
	
	public void getMaxAndSetStatus(List<AnaloghisVo> resultList, SensorVo sensor, List<AnalogStatisticsVo> fiveMinutesData) {
		for (AnaloghisVo af : resultList) {
			if (type != null && type == 1 && fiveMinutesData != null){// 历史曲线需返回三量曲线
				for (AnalogStatisticsVo as : fiveMinutesData) {
					try {
						long timeCast = countLongValueOfTwoTimeStr(as.getStatistictime(), af.getStarttime());
						if (timeCast < 300000 && timeCast >= 0) {
							af.setMaxValue(as.getMaxvalues());
							af.setMinValue(as.getMinvalue());
							af.setAvgValue(as.getAvgvalue());
							if (af.getStarttime().equals(as.getMaxtime())) {
								af.setIsMax(1);
							}
							if (af.getStarttime().equals(as.getMintime())) {
								af.setIsMin(1);
							}
							if (as.getAvgTime() == null) {
								af.setIsAvg(1);
								as.setAvgTime(af.getStarttime());
							}
							break;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			af.setUnit(sensor.getUnit());
			setRealStatus(af, sensor);//set设备状态
			//计算最大值
			if(af.getAvalue() > max){
				max = af.getAvalue();
			}
			if(af.getDataStatus() == 5){
				af.setAvalue(null);
			}
		}
	}
	
	public void setRealStatus(AnaloghisVo af, SensorVo sensor){
		try {
			setAlarm(af, sensor);//报警状态
			String key = af.getIp() + ":" + af.getDevid() + ":" + af.getStarttime();
			setCutFeedStatus(af, key, feedMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setCutFeedStatus(AnaloghisVo af, String key, Map<String,List<Feedback>> feedMap) throws ParseException{
		List<String> feedStatusList = new ArrayList<String>();
		List<String> powerStatusList = new ArrayList<String>();
		//获取断馈电数据
		List<Feedback> list = feedMap.get(key);
		if(UtilMethod.notEmptyList(list)){
			for(Feedback fb : list){
				SwitchSensor switchSensor = switchSensorMap.get(fb.getCut_devid());
				if(null != switchSensor){
					String position = switchSensor.getPosition() == null ? "位置未配置" : switchSensor.getPosition();
					String feedStatus = position + "/" + switchSensor.getAlais() + "/";
					if(fb.getFeedstatus() != null){
						if(fb.getFeedstatus() == 0){
							feedStatus += "正常";
						}else if(fb.getFeedstatus() == 1){
							feedStatus += "异常";
						}else if(fb.getFeedstatus() == 5){
							feedStatus += "通信中断";
						}
					}
					String powerStatus = position+"/"+switchSensor.getAlais()+"/";
					if(fb.getIs_cut() != null){
						if(fb.getIs_cut() == 0){
							powerStatus += "复电";
						}else if(fb.getIs_cut() == 1){
							powerStatus += "断电";
						}
					}
					feedStatusList.add(feedStatus);
					powerStatusList.add(powerStatus);
				}
			}
		}
		af.setFeedStatusList(feedStatusList);
		af.setPowerStatusList(powerStatusList);
	}
	
	public void setAlarm(AnaloghisVo af, SensorVo sensor){
		try {
			CopyOnWriteArrayList<AnalogQueryVo> list = map.get("alarm");
			if(UtilMethod.notEmptyList(list)){
				if(af.getAvalue() >= sensor.getLimit_alarm() || af.getAvalue() <= sensor.getFloor_alarm()){
					for(AnalogQueryVo aqv : list){
						if(af.getStarttime() != null && af.getStarttime() != "" && 
								UtilMethod.isTimeString(af.getStarttime()) && 
								UtilMethod.isTimeString(aqv.getStartTime()) &&
								UtilMethod.isTimeString(aqv.getEndTime()) &&
								UtilMethod.isMid(af.getStarttime(), aqv.getStartTime(), aqv.getEndTime())){
							String measure = aqv.getMeasure() == null ? "--" : aqv.getMeasure();
							String measureTime = aqv.getMeasuretime() == null ? "--" : aqv.getMeasuretime();
							af.setMeasure(measure + " / " + measureTime);
							if(Math.abs(aqv.getStatus()) == 3){
								af.setCutAlarmStartTime(aqv.getStartTime());
								af.setCutAlarmEndTime(aqv.getEndTime());
							}else if(Math.abs(aqv.getStatus()) == 2){
								af.setAlarmStartTime(aqv.getStartTime());
								af.setAlarmEndTime(aqv.getEndTime());
							}
							alarmEndTime = aqv.getEndTime();
							af.setAlarmStatus("报警/ " + aqv.getStartTime());
							break;
						}
					}
				} else {
					String time = alarmEndTime != null ? alarmEndTime : "--";
					af.setAlarmStatus("解除/ "+ time);
				}	
			} else {
				String time = alarmEndTime != null ? alarmEndTime : "--";
				af.setAlarmStatus("解除/ "+ time);
			} 
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//设置传感器基本信息
	public void setData(SensorVo sensor){
		String position = sensor.getPosition() == null ? "位置未配置" : sensor.getPosition();
		result.put("position", position);
		result.put("alais", sensor.getAlais());
		String cutScope = "";
		if(UtilMethod.notEmptyList(cutDevScope)){
			for(String scope : cutDevScope){
				cutScope += scope+";";
			}
		}
		result.put("areaname", cutScope);
		
		setWarningValue("limit_warning", sensor.getFloor_alarm(), sensor.getLimit_alarm());
		setWarningValue("limit_power", sensor.getFloor_power(), sensor.getLimit_power());
		setWarningValue("limit_repower", sensor.getFloor_repower(), sensor.getLimit_repower());
	}
	
	public void setWarningValue(String fieldName, Double floorValue, Double limitValue){
		Object floor = getValue(floorValue);
		Object limit = getValue(limitValue);
		result.put(fieldName, floor + " / " +limit);
	}
	
	public Object getValue(Double value){
		return value == 0 || (int)Math.abs(value) == 999999 ? "--" : value;
	}
	
	public long countLongValueOfTwoTimeStr(String time1, String time2)
			throws ParseException {
		time1 = time1.substring(0, 19);
		time2 = time2.substring(0, 19);
		long longTime1 = df2.parse(time1).getTime();
		long longTime2 = df2.parse(time2).getTime();

		return longTime1 - longTime2;
	}
}
