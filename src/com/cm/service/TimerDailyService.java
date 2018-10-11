package com.cm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.dao.IAreaDao;
import com.cm.dao.IExceptionDao;
import com.cm.entity.Area;
import com.cm.entity.Area2;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.AreaChangeVo;
import com.cm.entity.vo.DailyRecVo;
import com.cm.entity.vo.ExpVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.WorkerVo;

@Service("TimerDailyService")
public class TimerDailyService extends DailyBaseService{
	@Autowired
	private CreateTableService ctService;
	@Autowired
	private IAreaDao areaDao;
	@Autowired
	private IExceptionDao eptDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd");
//	private String tableDate = null;
//	private final String table = "t_coalMine_route_";
	private String stime;
	private String etime;
	private Map<String, List<ExpVo>> unMap;
	private Map<String, List<ExpVo>> alMap;
	/**
	 * 计算昨日上下井情况
	 * @throws ParseException
	 */
	@Scheduled(cron = "30 0 0 * * ?")
	public void countDailyRecords() throws ParseException{
		//初始化变量
		init();
		//保存区域配置
		saveAreaConfig();
		
		//获取临时卡工作人员信息
		List<WorkerVo> allWorker = workDao.getWorkers();
		List<Integer> cards = wiDao.getCard(stime, etime);
		for(Integer card : cards){
			WorkerVo wv = new WorkerVo();
			wv.setId(0);
			wv.setRfcard_id(card+"");
			allWorker.add(wv);
		}
		
		resultList = new ArrayList<DailyRecVo>();
		resultList.clear();
		
//		Map<String,String> map = new HashMap<String,String>();
//		String sql = "SELECT worker_id FROM "+table.concat(tableDate)+" WHERE responsetime BETWEEN '"+stime+"' AND '"+etime+"' GROUP BY worker_id";
//		map.put("sql", sql);
//		List<Integer> workerIds = ctService.getWorkerIds(map);
		
		for(WorkerVo worker : allWorker){
			List<WorkerInAreaRec> dailyRecByWorker = null;
			if(worker.getId() > 0)
				dailyRecByWorker = wiDao.getDailyRecByWorker(worker.getId(), stime, etime);
			else if(worker.getId() == 0){
				dailyRecByWorker = wiDao.getDailyRecByCard(worker.getRfcard_id(), stime, etime);
				worker.setRfcard_id(dailyRecByWorker.get(0).getCard_id()+"/临时卡");
			}
			
			if(dailyRecByWorker == null || dailyRecByWorker.size() == 0){
//				boolean contains = workerIds.contains(worker.getId());
//				if(contains)
//					setVo(worker, "-", "当日未出井", "24小时0分0秒", resultList, new WorkerInAreaRec());
			}else{
				if("0".equals(worker.getRfcard_id()))
					worker.setRfcard_id(dailyRecByWorker.get(0).getCard_id()+"/离职");
				dealWithData(dailyRecByWorker, worker);
			}
			
		}
		
		int maxTime = 0;
		AreaChangeVo areaChange = areaDao.getAreaChange(date);
		Area2 area = areaDao.getById(1);
		if(areaChange != null){
			maxTime = areaChange.getMax_time() * 60;
		}else{
			maxTime = area.getMax_time() * 60;
		}
		
		if(resultList.size() > 0){
			try {
				makeExceptionData(resultList, maxTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dDao.addRecs(resultList);
		}
			
	}
	
	public void init(){
		//初始化变量
		starttime = null;
		endtime = null;
		wellduration = null;
		flag = 0;
		count = 0;
		//设置时间
		getYesterday();
		stime = getStartTime();
		etime = getEndTime();
	}
	
	public void getYesterday(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date date1 = c.getTime();
		date = sdf.format(date1);
//		tableDate = sdf2.format(date1);
	}
	
	public void saveAreaConfig(){
		List<AreaChangeVo> areaMSG = areaService.getAreaMSG(date);
		if(areaMSG == null){
			List<AreaChangeVo> acList = new ArrayList<AreaChangeVo>();
			AreaChangeVo ac = null;
			//矿井配置
			Area2 byId = areaService.getById(1);
			ac = new AreaChangeVo();
			ac.setOld_max_allow(byId.getMax_allow());
			ac.setMax_time(byId.getMax_time());
			ac.setOld_max_time(byId.getMax_time());
			ac.setMax_allow(byId.getMax_allow());
			ac.setArea_id(byId.getId());
			ac.setWork_day(date);
			ac.setIs_change(0);
			acList.add(ac);
			//区域配置
			List<Area> allArea = areaService.getAllArea();
			for(Area area : allArea){
				ac = new AreaChangeVo();
				ac.setOld_max_allow(area.getMax_allow());
				ac.setMax_time(area.getMax_time());
				ac.setOld_max_time(area.getMax_time());
				ac.setMax_allow(area.getMax_allow());
				ac.setArea_id(area.getId());
				ac.setWork_day(date);
				ac.setIs_change(0);
				acList.add(ac);
			}
			areaService.insertAreaConfig(acList);
		}

	}
	
	public void makeExceptionData(List<DailyRecVo> recs, int maxTime) throws Exception {
		// 获取失联人员信息
		getUnMap();
		// 获取进入限制区域人员信息
		getAlMap();
		// 获取超员列表
		List<OverManVo> mineOverman = eptDao.getMineOverman(stime, etime);
		for (DailyRecVo dr : recs) {
			// 处理昨日未出井
			if ("-".equals(dr.getStartTime())) {
				LinkedList<WorkerInAreaRec> list = wiDao.getRecentlyInMineRec(dr.getWorkerId(), stime);
				WorkerInAreaRec recentlyInMineRec = null;
				if (list != null && list.size() > 0)
					recentlyInMineRec = list.getLast();
				if (recentlyInMineRec != null && recentlyInMineRec.getStatus() == 1) {
					dr.setStartTime(recentlyInMineRec.getResponsetime());
				} else {
					dr.setStartTime("入井异常");
					dr.setWellduration("-");
				}
			}
			
			// 超员判定
			if(mineOverman != null && mineOverman.size() > 0){
				String startTime = dr.getStartTime();
				String endTime;
				if("当日未出井".equals(dr.getEndTime())){
					endTime = getEndTime();
				}else{
					endTime = dr.getEndTime();
				}
				if(UtilMethod.isTimeString(startTime) && UtilMethod.isTimeString(endTime)){
					long start = df.parse(startTime).getTime();
					long end = df.parse(endTime).getTime();
					for(OverManVo ov : mineOverman){
						if(start <= df.parse(ov.getEndtime()).getTime() && end >= df.parse(ov.getResponsetime()).getTime()){
							if(dr.getRemark() == null){
								dr.setRemark("超员");
							}else{
								if(!dr.getRemark().contains("超员"))
									dr.setRemark(dr.getRemark()+",超员");
							}
						}
					}
				}
			}
			
			// 超时判定
			Integer sec = UtilMethod.getSec(dr.getWellduration());
			LongStringVo wdt = null;
			if (maxTime > 0 && sec > maxTime) {
				try {
					if ("当日未出井".equals(dr.getEndTime())) {
						wdt = UtilMethod.longToTimeFormat(dr.getStartTime(), etime);
					} else{
						wdt = UtilMethod.longToTimeFormat(dr.getStartTime(), dr.getEndTime());
					}
					
					dr.setWellduration(wdt.getTimCast());
					
					int ot = (int) wdt.getTime() / 1000 - maxTime;

					String countTimeCast = UtilMethod.countTimeCast(ot * 1000);
					
					dr.setWellduration(dr.getWellduration().concat("/ 超时").concat(countTimeCast));
					
					if(dr.getRemark() == null){
						dr.setRemark("超时");
					}else if(dr.getRemark() != null && !dr.getRemark().contains("超时")){
						dr.setRemark(dr.getRemark().concat(",超时"));
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			//失联和进入限制区域判定
			boolean timeString = false;
			
			timeString = UtilMethod.isTimeString(dr.getStartTime());
				
			boolean timeString2 = false;
			if(dr.getEndTime().equals("当日未出井")){
				timeString2 = true;
			}else{
				timeString2 = UtilMethod.isTimeString(dr.getEndTime());
			}
				
			if(timeString && timeString2){
				List<ExpVo> list = unMap.get(dr.getRfcard_id());
				if(list != null && list.size() > 0){
					for(ExpVo ev : list){
						try {
							boolean mid = false;
							
							if(dr.getEndTime().equals("当日未出井")){
								mid = UtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), getEndTime());
							}else{
								mid = UtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), dr.getEndTime());
							}
							
							if(mid){
								if(dr.getRemark() == null){
									dr.setRemark("失联");
								}else{
									if(!dr.getRemark().contains("失联"))
										dr.setRemark(dr.getRemark()+",失联");
								}
							}
								
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				
				List<ExpVo> listAl = alMap.get(dr.getRfcard_id());
				if(listAl != null && listAl.size() > 0){
					for(ExpVo ev : listAl){
						try {
							boolean mid = false;
							
							if(dr.getEndTime().equals("当日未出井")){
								mid = UtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), getEndTime());
							}else{
								mid = UtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), dr.getEndTime());
							}
							
							if(mid){
								if(dr.getRemark() == null){
									dr.setRemark("进入限制区域");
								}else{
									if(!dr.getRemark().contains("进入限制区域"))
										dr.setRemark(dr.getRemark()+",进入限制区域");
								}
							}
								
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void getUnMap(){
		List<ExpVo> unIds = eptDao.getExpUN(getStartTime(), getEndTime());
		unMap = new HashMap<String,List<ExpVo>>();
		if(unIds != null){
			for(ExpVo un: unIds){
				if(un.getCard_id() > 0 && un.getWorker_id() == 0)
					un.setCard(un.getCard_id()+"/临时卡");
				else
					un.setCard(un.getCard_id()+"");
				List<ExpVo> list = unMap.get(un.getCard());
				if(list == null){
					list = new ArrayList<ExpVo>();
				}
				list.add(un);
				unMap.put(un.getCard(), list);
			}
		}
	}
	
	public void getAlMap(){
		List<ExpVo> alIds = eptDao.getExpAL(getStartTime(), getEndTime());
		alMap = new HashMap<String,List<ExpVo>>();
		if(alIds != null){
			for(ExpVo al: alIds){
				if(al.getCard_id() > 0 && al.getWorker_id() == 0)
					al.setCard(al.getCard_id()+"/临时卡");
				else
					al.setCard(al.getCard_id()+"");
				List<ExpVo> list = alMap.get(al.getCard());
				if(list == null){
					list = new ArrayList<ExpVo>();
				}
				list.add(al);
				alMap.put(al.getCard(), list);
			}
		}
	}
	
}