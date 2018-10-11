package com.cm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.UtilMethod;

import com.cm.dao.AreaDailyDao;
import com.cm.dao.IAreaDao;
import com.cm.dao.ICoalmineRouteDao;
import com.cm.dao.IExceptionDao;
import com.cm.dao.IWorkerInAreaRecDao;
import com.cm.entity.Area;
import com.cm.entity.OvertimeAlarm;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.AreaChangeVo;
import com.cm.entity.vo.AreaPass;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.Searchform;
import com.cm.entity.vo.Unconnection;
import com.cm.security.LoginManage;
import com.cm.service.AreaTodayService;

@Scope("prototype")
@Controller
@RequestMapping("/daily")
public class AreaPassController {

	@Autowired
	private AreaDailyDao adDao;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ResultObj result;
	@Autowired
	private IAreaDao areaDao;
	@Autowired
	private ICoalmineRouteDao crDao;
	@Autowired
	private IExceptionDao excDao;
	@Autowired
	private AreaTodayService tcService;
	@Autowired
	protected IWorkerInAreaRecDao wiDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Map<Integer, List<AreaPass>> areasMap = new HashMap<Integer, List<AreaPass>>();
	private Map<String, List<AreaPass>> DayRecsMap = new HashMap<String, List<AreaPass>>();
	private Map<String, Integer> overManMap = new HashMap<String, Integer>();
	private Map<String, List<AreaPass>> limitMap = new HashMap<String, List<AreaPass>>();
	private Map<String, List<OvertimeAlarm>> overtimeMap = new HashMap<String, List<OvertimeAlarm>>();
	private Map<String, List<Unconnection>> uncntMap = new HashMap<String, List<Unconnection>>();
	private Map<Integer, List<Unconnection>> unMap = new HashMap<Integer, List<Unconnection>>();
	private Map<Integer, Integer> areaMap = new HashMap<Integer, Integer>();
	private String startTime = " 00:00:00";
	private String endTime = " 23:59:59";
	private String day = null;
	private List<AreaPass> recs;
	private Date firstday = null;
	private Date lastday = null;
	
	private int totalOT = 0;
	private int totalUN = 0;
	private int totalAL = 0;
	private int overManCount = 0;
	private HashSet<String> idSet;
	
	/**
	 * 每天区域出入查询
	 * @param request
	 * @param searchform
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/passPoint", method = RequestMethod.POST)
	@ResponseBody
	public Object getDailyRec(HttpServletRequest request
			,@RequestBody Searchform searchform
			) throws ParseException {
		
		List<Integer> idList = searchform.getArea_ids();//区域集合
		
		// 判断查询日期,para > 0 是历史，para = 0是今日
		day = searchform.getStarttime();
		long para = compare();
		
		searchform.setStarttime(getStartTime());
		searchform.setEndtime(getEndTime());
		
		//失联人员
		List<Unconnection> un = excDao.getUnconnection(searchform);
		if(un != null && un.size() > 0)
			cutUnList(un);
		
		//进入限制区域人员
		List<AreaPass> areaLimited = excDao.getLimited(searchform);
		
		//获取区域超时时长
		getAreaMaxTime();
		
		//获取人员上下井情况
		searchform.setStarttime(day);
		recs = new ArrayList<AreaPass>();
		recs = para > 0 ? adDao.getRecs(searchform) : tcService.areaPassRecords(searchform);
		getExceptionMSG(areaLimited);
		
		//筛选所选区域记录
		List<AreaPass> dataRecs = new ArrayList<AreaPass>();
		
		//计算总人数
		Map<Integer, Integer> areaIdMap = new HashMap<Integer, Integer>();
		if(idList != null && idList.size() > 0){
			idSet = new HashSet<String>();
			for(Integer id : idList){
				areaIdMap.put(id, id);
			}
			for(AreaPass ap : recs){
				Integer integer = areaIdMap.get(ap.getAreaId());
				if(integer != null){
					idSet.add(ap.getCard());
					dataRecs.add(ap);
				}
			}
		}else if(idList == null){
			dataRecs = recs;
		}
		
		//获取超员情况
		List<OverManVo> aeras = getAllOverManArea();
		List<AreaPass> omResultList = new ArrayList<AreaPass>();
		if(aeras != null && aeras.size() > 0){
			List<Integer> area_ids = searchform.getArea_ids();
			overManCount = 0;
			for(OverManVo areaOM : aeras){
				overManCount += (areaOM.getPersonNum() - areaOM.getMaxAllow());
				if(area_ids != null && area_ids.contains(areaOM.getAreaId()) || area_ids == null){
					for(AreaPass ap : dataRecs){
						if(UtilMethod.isTimeString(ap.getStartTime()) && 
								(UtilMethod.isTimeString(ap.getEndTime()) || ap.getEndTime().equals("至今"))){
							boolean mid = false;
							if(ap.getEndTime().equals("至今"))
								mid = UtilMethod.isMid(areaOM.getFilltime(),ap.getStartTime(),getEndTime());
							else
								mid = UtilMethod.isMid(areaOM.getFilltime(),ap.getStartTime(),ap.getEndTime());
							
							if(mid && ap.getAreaId() == areaOM.getAreaId()){
								List<String> list = ap.getEptList();
								if(list != null){
									if(!list.contains("超员")){
										list.add("超员");
									}
								}else{
									list = new ArrayList<String>();
									list.add("超员");
								}
								
								ap.setEptList(list);
								omResultList.add(ap);
							}
						}
					}
				}	
			}
			
		}
		result.put("overManSize", overManCount);
		result.put("inArea", dataRecs);
		result.put("inAreaSize", idSet.size());
		result.put("OverTime", totalOT);
		
		return result.setStatus(0, "ok");
	}
	
	public void getAreaMaxTime(){
		Calendar cal =Calendar.getInstance();
		String format = sdf.format(cal.getTime());
		List<Area> allArea = areaDao.getAllArea();
		if(day.equals(format)){
			for(Area area : allArea){
				Integer integer = areaMap.get(area.getId());
				if(integer == null)
					areaMap.put(area.getId(), area.getMax_time() * 60);
			}
		}else{
			List<AreaChangeVo> areaMSG = areaDao.getAreaMSG(day);
			if(UtilMethod.notEmptyList(areaMSG)){
				for(AreaChangeVo area : areaMSG){
					Integer integer = areaMap.get(area.getId());
					if(integer == null)
						areaMap.put(area.getId(), area.getMax_time() * 60);
				}
			}else{
				for(Area area : allArea){
					Integer integer = areaMap.get(area.getId());
					if(integer == null)
						areaMap.put(area.getId(), area.getMax_time() * 60);
				}
			}
		}
		
	
	}
	
	public String addMinutes(String time, int minutes) throws ParseException{
		Date inAreaTime = UtilMethod.StringToDateFormat(time);
		Calendar inTime = Calendar.getInstance();
		inTime.setTime(inAreaTime);
		inTime.add(Calendar.MINUTE, minutes);
		return UtilMethod.DateToStringFormat(inTime.getTime());
	}
	
	public void getExceptionMSG(List<AreaPass> areaLimited) throws ParseException{
		idSet = new HashSet<String>();
		if(recs != null && recs.size() > 0){
			makeExceptionData(areaLimited);
		}else{
			recs = new ArrayList<AreaPass>();
		}
	}
	
	public void makeExceptionData(List<AreaPass> areaLimited) throws ParseException{
		Set<Integer> otSet = new HashSet<Integer>();
		for(AreaPass ap : recs){
			idSet.add(ap.getCard());
			List<Unconnection> list2 = unMap.get(ap.getWorkerId());
			if(list2 != null && list2.size() > 0){
				boolean mid = false;
				for(Unconnection un : list2){
					String stime = ap.getStartTime();
					String etime = ap.getEndTime();
					if("-".equals(ap.getStartTime()) || "昨日未出该区域".equals(ap.getStartTime()))
						stime = getStartTime();
					if("当日未出该区域".equals(ap.getEndTime()))
						etime = getEndTime();
					if("至今".equals(ap.getEndTime()))
						etime = UtilMethod.getNow();
					if("未检测到出区域读卡记录".equals(ap.getEndTime()) || "未检测到进入区域读卡记录".equals(ap.getStartTime()))
						continue;
					
					mid = UtilMethod.isMid(un.getLastTime(), stime, etime);
					
					if(mid){
						if(ap.getEptList() == null || ap.getEptList().isEmpty()){
							List<String> ept = new ArrayList<String>();
							ept.add("失联");
							ap.setEptList(ept);
						}else if(!ap.getEptList().contains("失联")){
							List<String> ept = ap.getEptList();
							ept.add("失联");
							ap.setEptList(ept);
						}
					}	
				}
			}
			
			ap.setDefault_allow(1);
			if(ap.getAreaId() > 0)
				for(AreaPass lmt : areaLimited){
				if(lmt.getWorkerId() == ap.getWorkerId() && lmt.getAreaId() == ap.getAreaId()
						&& lmt.getStartTime().equals(ap.getStartTime())){
					ap.setDefault_allow(2);
					totalAL++;
					if(ap.getEptList() == null || ap.getEptList().isEmpty()){
						List<String> ept = new ArrayList<String>();
						ept.add("进入限制区域");
						ap.setEptList(ept);
					}else if(!ap.getEptList().contains("进入限制区域")){
						List<String> ept = ap.getEptList();
						ept.add("进入限制区域");
						ap.setEptList(ept);
					}
				}
			}
			
			Integer sec = UtilMethod.getSec(ap.getWellduration());
			Integer max = areaMap.get(ap.getAreaId());
			if(ap.getAreaId() > 0 && sec > max){
				LongStringVo wdt = null;
				if("-".equals(ap.getStartTime())) {
					LinkedList<WorkerInAreaRec> list = wiDao.getRecentlyInMineRec2(ap.getCardId(), ap.getWorkday().concat(startTime));
					WorkerInAreaRec recentlyInMineRec = null;
					if(list != null && list.size() > 0)
						recentlyInMineRec = list.getLast();
					
					if(recentlyInMineRec != null && recentlyInMineRec.getStatus() == 2 && recentlyInMineRec.getArea_id() == ap.getAreaId()){
						ap.setStartTime(recentlyInMineRec.getResponsetime());
						
						if("当日未出该区域".equals(ap.getEndTime()))
							ap.setEndTime(ap.getWorkday().concat(endTime));
						
						try {
							wdt = UtilMethod.longToTimeFormat(ap.getStartTime(), ap.getEndTime());
							ap.setWellduration(wdt.getTimCast());
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}else {
						ap.setStartTime("未检测到进入区域读卡记录");
						ap.setWellduration("-");
					}
						
				}
				if(!"未检测到进入区域读卡记录".equals(ap.getStartTime())){
					otSet.add(ap.getCardId());
					int ot;
					if(wdt != null)
						ot = (int)wdt.getTime()/1000 - max;
					else
						ot = sec - max;
					String countTimeCast = UtilMethod.countTimeCast(ot*1000);
					ap.setWellduration(ap.getWellduration().concat("/ 超时").concat(countTimeCast));
					
					if(ap.getEptList() == null || ap.getEptList().isEmpty()){
						List<String> ept = new ArrayList<String>();
						ept.add("超时");
						ap.setEptList(ept);
					}else if(!ap.getEptList().contains("超时")){
						List<String> ept = ap.getEptList();
						ept.add("超时");
						ap.setEptList(ept);
					}
				}
			
			}
			
			if("-".equals(ap.getStartTime()))
				ap.setStartTime("昨日未出该区域");

			List<AreaPass> list = areasMap.get(ap.getAreaId());
			
			if(list == null){
				list = new ArrayList<AreaPass>();
			}
			
			list.add(ap);
			areasMap.put(ap.getAreaId(), list);	
		}
		totalOT = otSet.size();
	}
	
	public void cutUnList(List<Unconnection> unList){
		for(Unconnection un :unList){
			List<Unconnection> list = unMap.get(un.getWorkerId());
			
			if(list == null){
				list = new ArrayList<Unconnection>();
			}
			
			list.add(un);
			unMap.put(un.getWorkerId(), list);
		}
	}
	
	public long compare() throws ParseException{
		Date paraDay = sdf.parse(day.concat(startTime));
		Date today = sdf.parse(sdf.format(today()).concat(startTime));
		return (today.getTime() - paraDay.getTime());
	}
	
	public String getToday(){
		Date date = today();
		return sdf.format(date);
	}
	
	public Date today(){
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		return date;
	}

	public String getStartTime(){
		return day.concat(startTime);
	}
	
	public String getEndTime(){
		return day.concat(endTime);
	}
	
	//获取所有超员区域
	public List<OverManVo> getAllOverManArea(){
		String sTime = getStartTime();
		String eTime = getEndTime();
		return excDao.getAreasOverMan(sTime, eTime);
	}
	
	/**
	 * 月及其每天数据汇总
	 * @param request
	 * @param searchform
	 * @return
	 * @throws ParseException
	 */
	private List<Object> resultList = new ArrayList<Object>();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monthlyArea", method = RequestMethod.POST)
	@ResponseBody
	public Object getMonth(HttpServletRequest request
			,@RequestBody Searchform searchform
			) throws ParseException {
		
		Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
		
        //获取月第一和最后一天
  		getFLDay(searchform);
      	cal.setTime(firstday);	
        int month2 = cal.get(Calendar.MONTH) + 1;
        int year2 = cal.get(Calendar.YEAR);
        if(month == month2 && year == year2){
        	Calendar c = Calendar.getInstance();
    		c.add(Calendar.DATE, -1);
    		lastday = c.getTime();
        }
		
		cal.setTime(firstday);
		
		//获取月数据
		searchform.setStarttime(dateToString(firstday));
		searchform.setEndtime(dateToString(lastday));
		List<AreaPass> monthlyData = adDao.getMonthlyData(searchform);
		
		//获取超员人员
		searchform.setStarttime(dateToString(firstday, startTime));
		searchform.setEndtime(dateToString(lastday, endTime));
		List<OverManVo> overManByDay = excDao.getOverManByDay(searchform);
		//将本月所有超员人员算成每天超员及计算总数
		int totalOM = dealPN(overManByDay);
		
		//月数据算入每天
		if(monthlyData != null && monthlyData.size() > 0){
			for(AreaPass ap : monthlyData){
				String workDay = ap.getWorkday();
				setDayData(ap,workDay);
			}
		}
		
		//
		getExpRecs(searchform);
		
		int totalPn = 0;
		//计算每天总体情况
		while(lastday.getTime() - cal.getTime().getTime() >= 0){
			String tDay = sdf.format(cal.getTime());
			
			Map<String, Object> dailyDataMap = new HashMap<String, Object>();
			dailyDataMap.put("day", tDay);
			
			List<AreaPass> list2 = DayRecsMap.get(tDay);
			Set<Integer> set = new HashSet<Integer>();
			if(list2 != null && !list2.isEmpty()){
				for(AreaPass ap : list2){
					set.add(ap.getWorkerId());
				}
			}
			totalPn += set.size();
			setDailyRec(set, dailyDataMap, "totalPN");
			
			Integer listOM = overManMap.get(tDay);
			if(null == listOM)
				listOM = 0;
			dailyDataMap.put("totalOM", listOM);
			
			Set<Integer> setLimit = new HashSet<Integer>();
			Set<Integer> setOvertime = new HashSet<Integer>();
			Set<Integer> setUn = new HashSet<Integer>();
			
			List<AreaPass> listLimt = limitMap.get(tDay);
			listLimt = isNull(listLimt);
			for(AreaPass ap : listLimt){
				setLimit.add(ap.getWorkerId());
			}
			
			List<OvertimeAlarm> listOvertime = overtimeMap.get(tDay);
			listOvertime = isNull(listOvertime);
			for(OvertimeAlarm ap : listOvertime){
				setOvertime.add(ap.getWorkerId());
			}
			
			List<Unconnection> listUn = uncntMap.get(tDay);
			listUn = isNull(listUn);
			for(Unconnection ap : listUn){
				setUn.add(ap.getWorkerId());
			}
			
			dailyDataMap.put("totalOT", setOvertime.size());
			dailyDataMap.put("totalUN", setUn.size());
			dailyDataMap.put("totalAL", setLimit.size());
			
			totalOT += setOvertime.size();
			totalUN += setUn.size();
			totalAL += setLimit.size();
			
			resultList.add(dailyDataMap);
			
			cal.add(Calendar.DATE, +1);
		}
		
		Map<String, Object> dailyDataMap = new HashMap<String, Object>();
		Calendar calen = Calendar.getInstance();
		dailyDataMap.put("day", sdf.format(calen.getTime()));
		dailyDataMap.put("totalPN", "-");
		dailyDataMap.put("totalOM", "-");
		dailyDataMap.put("totalOT", "-");
		dailyDataMap.put("totalUN", "-");
		dailyDataMap.put("totalAL", "-");
		resultList.add(dailyDataMap);
		
		result.put("data", resultList);
		result.put("totalPN", totalPn);
		result.put("totalOM", totalOM);
		result.put("totalOT", totalOT);
		result.put("totalUN", totalUN);
		result.put("totalAL", totalAL);
		
        return result.setStatus(0, "ok");
	}
	
	public int dealPN(List<OverManVo> overManByDay){
		int totalOM = 0;
		int areaNum = 0;
		Integer vNum = 0;
		int areaId = 0;
		String vDay = " ";
		if(overManByDay != null && overManByDay.size() > 0){
			for(int i = 0; i < overManByDay.size(); i++){
				OverManVo om = overManByDay.get(i);
				String mDay = om.getFilltime().substring(0,10);
				
				if(vDay.equals(mDay) && areaId == om.getAreaId() && om.getPersonNum() - om.getMaxAllow() > vNum){
					vNum = om.getPersonNum() - om.getMaxAllow();
					overManMap.put(mDay, vNum);
				}else if(vDay.equals(mDay) && areaId != om.getAreaId()){
					totalOM += vNum;
					areaNum += vNum;
					areaId = om.getAreaId();
					vNum = om.getPersonNum() - om.getMaxAllow();
				}else if(!vDay.equals(mDay)){
					totalOM += vNum;
					areaNum += vNum;
					vNum = om.getPersonNum() - om.getMaxAllow();
					areaId = om.getAreaId();
					if(vDay != " ")
						overManMap.put(vDay, areaNum);
					areaNum = 0;
					vDay = mDay;
					overManMap.put(mDay, vNum);
				}
				
				if(i+1 == overManByDay.size()){
					totalOM += vNum;
					areaNum += vNum;
					overManMap.put(mDay, areaNum);
				}
			}
			
		}
		return totalOM;
	}
	
	public void getFLDay(Searchform searchform) throws ParseException{
		Calendar cal = Calendar.getInstance();
		String s= searchform.getStarttime().concat("-01");
		Date parse = sdf.parse(s);
		cal.setTime(parse);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        firstday = cal.getTime();
        cal.add(Calendar.MONTH,1);  
        cal.set(Calendar.DAY_OF_MONTH, 0);
        lastday = cal.getTime();
	}

	public void setDayData(AreaPass ap, String day){
		List<AreaPass> listDayRecs = DayRecsMap.get(day);
		
		if(listDayRecs == null){
			listDayRecs = new ArrayList<AreaPass>();
		}
		listDayRecs.add(ap);
		DayRecsMap.put(day, listDayRecs);
	}
	
	public boolean isThisMonth(){
		return true;
	}
	
	public String dateToString(Date date){
		return sdf.format(date);
	}
	
	public String dateToString(Date date, String s){
		return sdf.format(date).concat(s);
	}
	
	public void setDailyRec(Set<?> set, Map<String, Object> dailyDataMap, String name){
		if(set == null)
			set = new HashSet<>();
		
		dailyDataMap.put(name, set.size());
	}
	
	@SuppressWarnings("rawtypes")
	public List isNull(List list){
		if(list == null)
			list = new ArrayList();
		return list;
	}
	
	public void getExpRecs(Searchform searchform){
		
		//本月所有进入限制区域、超时、失联人员的基础数据
		List<AreaPass> areaLimited = excDao.getAreaLimited(searchform);
		List<OvertimeAlarm> overtime = excDao.getOvertime(searchform);
		List<Unconnection> unconnection = excDao.getUnconnection(searchform);
		
		if(areaLimited != null && areaLimited.size() > 0){
			for(AreaPass obj : areaLimited){
				String mDay = obj.getStartTime().substring(0,10);
				List<AreaPass> list = limitMap.get(mDay);
				if(list == null){
					list = new ArrayList<AreaPass>();
				}
				list.add(obj);
				limitMap.put(mDay, list);
			}
		}
		
		if(overtime != null && overtime.size() > 0){
			for(OvertimeAlarm obj : overtime){
				String mDay = obj.getAlarmTime().substring(0,10);
				List<OvertimeAlarm> list = overtimeMap.get(mDay);
				if(list == null){
					list = new ArrayList<OvertimeAlarm>();
				}
				list.add(obj);
				overtimeMap.put(mDay, list);
			}
		}
		
		if(unconnection != null && unconnection.size() > 0){
			for(Unconnection obj : unconnection){
				String mDay = obj.getLastTime().substring(0,10);
				List<Unconnection> list = uncntMap.get(mDay);
				if(list == null){
					list = new ArrayList<Unconnection>();
				}
				list.add(obj);
				uncntMap.put(mDay, list);
			}
		}
	}
	
	@RequestMapping(value="/areaPass",method=RequestMethod.POST)
	@ResponseBody
	public Object getAllemphasisPass(HttpServletRequest request
			,@RequestBody Searchform searchform) throws ParseException{
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object obj = null;
		if(searchform.getType()==3){
			List<Integer> list = areaDao.getAllemphasisAreaIds();
			if (UtilMethod.notEmptyList(list)) {
				searchform.setArea_ids(list);
				obj = getDailyRec(request, searchform);
			} else { 
				return result.setStatus(0, "");
			}
		}
		if(searchform.getType()==4){
			List<Integer> list = areaDao.getAllAreaLimitIds();
			if (UtilMethod.notEmptyList(list)) {
				searchform.setArea_ids(list);
				obj = getDailyRec(request, searchform);
			} else { 
				return result.setStatus(0, "");
			}
		}
		return obj;
	}
}