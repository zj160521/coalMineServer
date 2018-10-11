package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.controller.ResultObj;
import com.cm.dao.IFeedbackDao;
import com.cm.dao.ISwitchDao;
import com.cm.entity.Feedback;
import com.cm.entity.Static;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.AnalogParamVo;
import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AnaloghisVo;
import com.cm.entity.vo.DevVo;
import com.cm.entity.vo.SensorVo;
import com.cm.entity.vo.StatusCurveVo;

@Service(value = "StatusCurve")
public class StatusCurveService implements SingleCurveInterface {

	@Autowired
	private ResultObj result;
	@Autowired
	private AnalogHistoryService analogHistoryService;
	@Autowired
	private StaticService staticService;
	@Autowired
	private IFeedbackDao fdDao;
	@Autowired
	private ISwitchDao switchDao;
	
	@SuppressWarnings("rawtypes")
	private Map<Integer, Static> map2 ;
	private Map<String, CopyOnWriteArrayList<AnalogQueryVo>> map;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private final String sec = ":00";
	private Map<String,List<Feedback>> feedMap;
	private Map<String,List<Feedback>> minFeedMap;
	private Map<Integer,SwitchSensor> switchSensorMap;
	private String alarmEndTime;
	
	/**
	 * 获取开关量数据
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResultObj getData(AnalogParamVo analogParamVo) {
		//初始化变量
		map2 = new HashMap<Integer, Static>();
		map = new HashMap<String, CopyOnWriteArrayList<AnalogQueryVo>>();
		alarmEndTime = null;
		//获取设备信息
		List<DevVo> ldv = setDlv(analogParamVo);
		List<SensorVo> sensor = analogHistoryService.getSwitchSensor(ldv);
		if (sensor == null){
			result.setStatus(1, "无该传感器");
			return result;
		}
		SensorVo sensorVo = sensor.get(0);
		//位置筛选
		if(analogParamVo.getPositionId()!= 0 && analogParamVo.getPositionId() != sensorVo.getPositionId()){
			result.put("data", new ArrayList<>());
			result.setStatus(0, "ok");
			return result;
		}
		
		//设置曲线开始结束时间及判断是否是全点曲线
		setTime(analogParamVo);
		//分页
		if(analogParamVo.getCur_page() > 0 && analogParamVo.getPage_rows() > 0){
			analogParamVo.setCur_page((analogParamVo.getCur_page() - 1) * analogParamVo.getPage_rows());
			int totalRec = analogHistoryService.getTotalRec(analogParamVo);
			analogParamVo.setTotal_record(totalRec);
			analogParamVo.setTotal_pages(totalRec/analogParamVo.getPage_rows() + 1);
			result.put("page", analogParamVo);
		}
		//基础数据处理：如果开关量状态无变化，数据一分钟取一条，debug数据全取
		List<AnaloghisVo> resultList = analogHistoryService.getHistory(analogParamVo);
		List<StatusCurveVo> valueList;
		if(UtilMethod.notEmptyList(resultList)){
			valueList = getValueList(resultList, sensorVo);
		}else{
			result.put("data", new ArrayList<>());
			result.setStatus(0, "ok");
			return result;
		}
		//获取报警原始数据
		List<AnalogQueryVo> analogQry = analogHistoryService.getAnalogQry(analogParamVo.getDevid(),analogParamVo.getIp(),
				analogParamVo.getStartTime(),analogParamVo.getEndTime());
		if(analogQry != null && analogQry.size() > 0)
			setAlarmMap(analogQry);
		alarmEndTime = analogHistoryService.getAlarmEndTime(analogParamVo.getStartTime(), analogParamVo.getDevid(),analogParamVo.getIp());
		//获取传感器状态
		List<Static> ssensorList = staticService.getAllSwitchSensorType();
		if(ssensorList != null && ssensorList.size() > 0){
			for(Static stic : ssensorList){
				map2.put(stic.getId(), stic);
			}
		}
		//获取所有开关量
		List<SwitchSensor> allSwitchSensor = switchDao.AllSwitchSensor();
		switchSensorMap = new HashMap<Integer,SwitchSensor>();
		if(UtilMethod.notEmptyList(allSwitchSensor)){
			for(SwitchSensor ss : allSwitchSensor){
				switchSensorMap.put(ss.getId(), ss);
			}
		}
		//获取断馈电信息
		getCutFeedMap(analogParamVo);
		//set设备报警、断馈电状态
		if(UtilMethod.notEmptyList(valueList) && sensor.size() > 0) {
			resultList = makeData(sensor.get(0), valueList);
		} else {
			resultList = new ArrayList<AnaloghisVo>();
		}
		
		result.put("data", resultList);
		result.setStatus(0, "ok");
		return result;
	}
	
	public void getCutFeedMap(AnalogParamVo analogParamVo){
		//获取断馈电信息
		List<Feedback> cutFeedStatus = fdDao.getCutStatus(analogParamVo.getIp(), analogParamVo.getDevid(), 0,
				analogParamVo.getStartTime(), analogParamVo.getEndTime());
		feedMap = new HashMap<String,List<Feedback>>();
		minFeedMap = new HashMap<String,List<Feedback>>();
		if(UtilMethod.notEmptyList(cutFeedStatus)){
			for(Feedback fb : cutFeedStatus){
				String key = fb.getIp() + ":" + fb.getDevid() + ":" + fb.getResponsetime(); 
				List<Feedback> list = feedMap.get(key);
				if(null == list)
					list = new ArrayList<Feedback>();
				list.add(fb);
				feedMap.put(key, list);
				
				//分钟数据获取一个分钟汇总断馈电map
				String minKey = fb.getIp() + ":" + fb.getDevid() + ":" + fb.getResponsetime().substring(0, 16);
				List<Feedback> minList = minFeedMap.get(minKey);
				if(null == minList){
					minList = new ArrayList<Feedback>();
					minList.add(fb);
				}else{
					boolean isExist = false;
					for(Feedback f : minList){
						if(f.getCut_devid() == fb.getCut_devid()){
							isExist = true;
							if(fb.getFeedstatus() == 1 && f.getFeedstatus() != 1){
								f.setFeedstatus(1);
							}else if(fb.getFeedstatus() == 5 && f.getFeedstatus() == 0){
								f.setFeedstatus(5);
							}
							
							if(fb.getIs_cut() == 1){
								f.setFeedstatus(1);
							}
							break;
						}
					}
					
					if(!isExist){
						minList.add(fb);
					}
				}
				minFeedMap.put(minKey, minList);
			}
		}
	}
	
	/*基础数据处理：1.首尾两条数据全取
    			2.如果开关量状态无变化，数据一分钟取一条，
    			3.debug非0数据全取
    			4.断馈电仪状态有无变化需断电值和馈电值一起看*/
	public List<StatusCurveVo> getValueList(List<AnaloghisVo> resultList, SensorVo sensorVo){
		List<StatusCurveVo> rList = new ArrayList<StatusCurveVo>();
		Double status = -1.0;
		Integer feedStatus = -1;
		int count = 0;
		Date minutsTime = null;
		StatusCurveVo valueVo;
		for(AnaloghisVo av : resultList){
			valueVo = new StatusCurveVo();
			/*如果是断线状态，值设为null*/	
			if(av.getDataStatus() == 5){
				av.setAvalue(null);
				av.setFeedback(null);
			}
			//防止上传数据错误，出现小数，对数据进行四舍五入
			if(av.getAvalue() != null){
				av.setAvalue((double) Math.round(av.getAvalue()));
			}
			count ++;
			/*添加最后一条记录*/
			if(count == resultList.size()){
				setValueVo(valueVo, av);
				rList.add(valueVo);
			}else{
				try {
					if(status != null && status == -1.0){/*保存第一条数据*/	
						status = av.getAvalue();
						feedStatus = av.getFeedback();
						minutsTime = df.parse(av.getStarttime());
						setValueVo(valueVo, av);
						rList.add(valueVo);
					}else{
						if(av.getDebug() != 0){/*debug非0数据记录*/
							status = av.getAvalue();
							feedStatus = av.getFeedback();
							minutsTime = df.parse(av.getStarttime());
							setValueVo(valueVo, av);
							rList.add(valueVo);
						}
						boolean notAddRec = true;
						if(status == null){//获取断电状态变化记录
							if(av.getAvalue() != null){
								status = av.getAvalue();
								feedStatus = av.getFeedback();
								minutsTime = df.parse(av.getStarttime());
								setValueVo(valueVo, av);
								rList.add(valueVo);
								notAddRec = false;
							}else{
								//状态无变化的数据一分钟取一条
								if(minutsTime.getTime() != df.parse(av.getStarttime()).getTime()){
									av.setStarttime(av.getStarttime().substring(0, 16)+":00");
									status = av.getAvalue();
									feedStatus = av.getFeedback();
									minutsTime = df.parse(av.getStarttime());
									setValueVo(valueVo, av);
									rList.add(valueVo);
									notAddRec = false;
								}
							}
						}else{
							if(av.getAvalue() == null || Double.doubleToLongBits(av.getAvalue()) != Double.doubleToLongBits(status)){
								status = av.getAvalue();
								feedStatus = av.getFeedback();
								minutsTime = df.parse(av.getStarttime());
								setValueVo(valueVo, av);
								rList.add(valueVo);
								notAddRec = false;
							}else{
								//状态无变化的数据一分钟取一条
								if(minutsTime.getTime() != df.parse(av.getStarttime()).getTime()){
									av.setStarttime(av.getStarttime().substring(0, 16)+":00");
									status = av.getAvalue();
									feedStatus = av.getFeedback();
									minutsTime = df.parse(av.getStarttime());
									setValueVo(valueVo, av);
									rList.add(valueVo);
									notAddRec = false;
								}
							}
						}
						
						if(sensorVo.getType() == 56 && notAddRec){/*获取馈电状态变化记录*/
							if(feedStatus == null){
								if(av.getFeedback() != null){
									status = av.getAvalue();
									feedStatus = av.getFeedback();
									minutsTime = df.parse(av.getStarttime());
									setValueVo(valueVo, av);
									rList.add(valueVo);
								}
							}else{
								if(av.getFeedback() == null || Double.doubleToLongBits(av.getFeedback()) != Double.doubleToLongBits(feedStatus)){
									status = av.getAvalue();
									feedStatus = av.getFeedback();
									minutsTime = df.parse(av.getStarttime());
									setValueVo(valueVo, av);
									rList.add(valueVo);
								}
							}
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return rList;
	}
	
//	public void reSetVariablesAndAddVo(Double status, Integer feedStatus, Date minutsTime, AnaloghisVo av, StatusCurveVo valueVo, List<StatusCurveVo> rList){
//		try {
//			status = av.getAvalue();
//			feedStatus = av.getFeedback();
//			minutsTime = df.parse(av.getStarttime());
//			setValueVo(valueVo, av);
//			rList.add(valueVo);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	public void setValueVo(StatusCurveVo valueVo, AnaloghisVo av){
		valueVo.setDebug(av.getDebug());
		valueVo.setStartTime(av.getStarttime());
		valueVo.setCutValue(av.getAvalue());
		valueVo.setFeedValue(av.getFeedback());
	}
	
	public void setTime(AnalogParamVo analogParamVo){
			if("1".equals(analogParamVo.getModel())){
				analogParamVo.setEndTime(analogParamVo.getDay().concat(" 23:59:59"));
				analogParamVo.setStartTime(analogParamVo.getDay().concat(" 00:00:00"));
			}else if("2".equals(analogParamVo.getModel())){
				String st = analogParamVo.getEndTime() == null ? UtilMethod.getNow() : analogParamVo.getDay().concat(" ").concat(analogParamVo.getEndTime()).concat(sec);
				analogParamVo.setEndTime(st);
				analogParamVo.setStartTime(analogParamVo.getDay().concat(" ").concat(analogParamVo.getStartTime()).concat(sec));
			}
	}
	
	public void setAlarmMap(List<AnalogQueryVo> analogQry){
		for(AnalogQueryVo aqv: analogQry){
			if(aqv.getStatus() == 1 || aqv.getStatus() == 3 || aqv.getStatus() == 2 || aqv.getStatus() == 5){
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
	
	@SuppressWarnings("rawtypes")
	public List<AnaloghisVo> makeData(SensorVo sensor, List<StatusCurveVo> valueList){
		List<AnaloghisVo> resultList = new ArrayList<AnaloghisVo>();
		setSensorMsg(sensor);
		for(StatusCurveVo vo : valueList){
			Static static1 = map2.get(sensor.getType());
			String[]  k = static1 == null ? null : static1.getK().split("\"");
			AnaloghisVo af =new AnaloghisVo();
			af.setStarttime(vo.getStartTime());
			setStartEndTime(af);
			af.setAvalue(vo.getCutValue());
			af.setFeedback(vo.getFeedValue());
			af.setIp(sensor.getIp());
			af.setDevid(sensor.getDevid());
			af.setDebug(vo.getDebug());
			
			if(vo.getCutValue() != null){
				if(af.getAvalue() == 0 && k != null)
					af.setStatus(k[1]);
				else if(af.getAvalue() == 1 && k != null)
					af.setStatus(k[3]);
				else if (af.getAvalue() == 2)
					af.setStatus("断线");
			}else{
				af.setStatus("断线");
			}
			
			if(sensor.getType() == 56 && vo.getFeedValue() != null){
				if(af.getFeedback() == 0)
					af.setFeedbackstatus("无馈电");
				else if(af.getFeedback() == 1)
					af.setFeedbackstatus("有馈电");
				
				if(af.getStatus() != null)
					af.setStatus(af.getStatus()+"/"+af.getFeedbackstatus());
			}
			setAlarm(af, sensor);//set报警、断馈电状态
			resultList.add(af);
		}
		return resultList;
	}
	
	public void setStartEndTime (AnaloghisVo af) {
		if (UtilMethod.notEmptyStr(af.getStarttime())) {
			String start = af.getStarttime().substring(11, 13);
			String startTime = start + ":00";
			String endTime = (Integer.parseInt(start) + 1) + ":00";
			af.setStartEndTime(startTime + "~" + endTime);
		}
	}
	
	public void setAlarm(AnaloghisVo af, SensorVo sensor){
		try {
			//报警状态
			CopyOnWriteArrayList<AnalogQueryVo> list = map.get("alarm");
			if(UtilMethod.notEmptyList(list)){
				for(AnalogQueryVo aqv : list){
					if(UtilMethod.isTimeString(af.getStarttime()) && 
							UtilMethod.isTimeString(aqv.getStartTime()) &&
							UtilMethod.isTimeString(aqv.getEndTime()) &&
							UtilMethod.isMid(af.getStarttime(), aqv.getStartTime(), aqv.getEndTime())){
						String measure = aqv.getMeasure() == null ? "--" : aqv.getMeasure();
						String measureTime = aqv.getMeasuretime() == null ? "--" : aqv.getMeasuretime();
						af.setMeasure(measure + " / " + measureTime);
						af.setAlarmStatus("报警/ " + aqv.getStartTime());
						alarmEndTime = aqv.getEndTime();
						break;
					}
				}
			} 
			if (af.getAlarmStatus() == null || af.getAlarmStatus() == "") {
				String time = alarmEndTime != null ? alarmEndTime : "--";
				af.setAlarmStatus("解除/ "+ time);
			} 
			
			//断馈电状态
			if(af.getStarttime().substring(17, 19).equals("00")){
				String key = af.getIp() + ":" + af.getDevid() + ":" + af.getStarttime().substring(0, 16);
				setCutFeedStatus(af, key, minFeedMap);
			}else{
				String key = af.getIp() + ":" + af.getDevid() + ":" + af.getStarttime();
				setCutFeedStatus(af, key, feedMap);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setCutFeedStatus(AnaloghisVo af, String key, Map<String,List<Feedback>> feedMap){
		List<String> feedStatusList = new ArrayList<String>();
		List<String> powerStatusList = new ArrayList<String>();
		//获取断馈电数据
		List<Feedback> list = feedMap.get(key);
		if(UtilMethod.notEmptyList(list)){
			for(Feedback fb : list){
				SwitchSensor switchSensor = switchSensorMap.get(fb.getCut_devid());
				if(null != switchSensor){
					String position = switchSensor.getPosition() == null ? "位置未配置" : switchSensor.getPosition();
					String feedStatus = position+"/"+switchSensor.getAlais()+"/";
					if(fb.getFeedstatus() != null){
						if(fb.getFeedstatus() == 0){
							feedStatus += "正常";
						}else if(fb.getFeedstatus() == 1){
							feedStatus += "异常";
						}else if(fb.getFeedstatus() == 5){
							feedStatus += "通信中断";
						}
					}
					
					String powerStatus = position + "/" + switchSensor.getAlais() + "/";
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
	
	public void setSensorMsg(SensorVo sensor){
		String position = sensor.getPosition() == null ? "位置未配置" : sensor.getPosition();
		result.put("position", position);
		result.put("alais", sensor.getAlais());
		result.put("areaname", sensor.getPower_scope());
	}
}
