package com.cm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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

import util.StaticUtilMethod;

import com.cm.dao.DailyDao;
import com.cm.dao.IAreaDao;
import com.cm.dao.ICoalmineRouteDao;
import com.cm.dao.IExceptionDao;
import com.cm.dao.IWorkerInAreaRecDao;
import com.cm.entity.Area2;
import com.cm.entity.OvertimeAlarm;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.AreaChangeVo;
import com.cm.entity.vo.DailyRecVo;
import com.cm.entity.vo.DailyVo;
import com.cm.entity.vo.ExpVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.Searchform;
import com.cm.security.LoginManage;
import com.cm.service.TimerDailyService;
import com.cm.service.TodayCountService;

@Scope("prototype")
@Controller
@RequestMapping("/daily")
public class DailyInMineController {

	@Autowired
	private TimerDailyService dailyService;
	@Autowired
	private DailyDao dailyDao;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ResultObj result;
	@Autowired
	private IAreaDao areaDao;
	@Autowired
	private ICoalmineRouteDao crDao;
	@Autowired
	private IExceptionDao eptDao;
	@Autowired
	private TodayCountService tcService;
	@Autowired
	protected IWorkerInAreaRecDao wiDao;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
	private Map<String, DailyVo> inMineMap;
	private Map<String, Integer> overManMap;
	private Map<String, List<DailyRecVo>> DayRecsMap;
	private Map<String, List<ExpVo>> unMap;
	private Map<String, List<ExpVo>> alMap;
	private String startTime = " 00:00:00";
	private String endTime = " 23:59:59";
	private String day = null;
	private List<OvertimeAlarm> overTimeRecs = null;
	private Date firstday = null;
	private Date lastday = null;
	private List<Object> resultList;
	private int totalOT;
	private int totalUN;
	private int totalAL;
	private int maxAllow;
	private int maxPN ;
//	private Map<String, Object> map;

	/**
	 * 每天上下井查询
	 * 
	 * @param request
	 * @param searchform 参数对象
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/dayQueryWell", method = RequestMethod.POST)
	@ResponseBody
	public Object getDailyRec(HttpServletRequest request,
			@RequestBody Searchform searchform) throws ParseException {
		//初始化全局变量
		init(searchform);
		
		// 判断查询日期,para > 0 是历史，para = 0是今日
		long para = compare();
		
		// 获取人员上下井情况
		List<DailyRecVo> recs = para > 0 ? dailyDao.getRecs(searchform) : tcService.countTodayRecords(searchform);
		
		// 计算下井人数
		Set<String> workerCount = new HashSet<String>();
		for (DailyRecVo dr : recs) {
			workerCount.add(dr.getRfcard_id());
		}
		maxPN = workerCount.size();
		
		// 获取允许最大下井人数
		getMaxAllow();
		
		//异常情况判定
		Integer totalOM = null;
		if(para > 0){
			List<DailyRecVo> listOverTime = new ArrayList<DailyRecVo>();
			for (DailyRecVo dr : recs) {
				DailyVo dailyVo = inMineMap.get(dr.getRfcard_id());
				DailyVo dailyVoA = isNull(dr, dailyVo);
				String remark  = dr.getRemark();
				if(remark != null){
					List<String> eptList = dailyVoA.getEptList();
					if(eptList == null){
						eptList = new ArrayList<String>();
					}
					if(remark.contains("超员") && !eptList.contains("超员")){
						eptList.add("超员");
					}
					if(remark.contains("超时") && !eptList.contains("超时")){
						listOverTime.add(dr);
						eptList.add("超时");
					}
					if(remark.contains("失联") && !eptList.contains("失联")){
						eptList.add("失联");
					}
					if(remark.contains("进入限制区域") && !eptList.contains("进入限制区域")){
						eptList.add("进入限制区域");
					}
					dailyVoA.setEptList(eptList);
				}
				inMineMap.put(dr.getRfcard_id(), dailyVoA);
			}
			//超时人数
			if(listOverTime.size() > 0){
				Set<String> set = new HashSet<String>();
				for(DailyRecVo drv : listOverTime){
					set.add(drv.getRfcard_id());
				}
				totalOT = set.size();
			}
			//超员人数计算
			List<OverManVo> mineOverman = eptDao.getMineOverman(getStartTime(), getEndTime());
			if(mineOverman != null && mineOverman.size() > 0){
				totalOM = 0;
				for(OverManVo omv : mineOverman){
					totalOM += omv.getPersonNum() - omv.getMaxAllow();
				}
			}
		}else{
			//计算超员人数
			if(maxPN > maxAllow){
				totalOM = maxPN - maxAllow;
			}
			
			// 获取异常map
			getExceptionMap(searchform);
			
			int maxTime = 0;
			if(overTimeRecs != null && overTimeRecs.size() > 0)
				maxTime = overTimeRecs.get(0).getMaxTime() * 60;
			// 判断是否超时、失联、进入限制区域,并返回进入区域总人数
			setExceptionData(recs, maxTime);
		}
		
		result.put("inMine", inMineMap);
		result.put("inMineSize", recs.size());
		result.put("OverTime", totalOT);
		result.put("OverManSize", totalOM);

		// 获取超员情况
		overManMap.put("MaxAllow", maxAllow);
		overManMap.put("MaxPersonNumber", maxPN);
		result.put("overMan", overManMap);

		return result.setStatus(0, "ok");
	}
	
	public void init(Searchform searchform){
		resultList = new ArrayList<Object>();
		inMineMap = new LinkedHashMap<String, DailyVo>();
		overManMap = new HashMap<String, Integer>();
		totalOT = 0;
		totalUN = 0;
		totalAL = 0;
		maxAllow = 0;
		maxPN = 0;
		day = searchform.getStarttime();
	}
	
	public void getExceptionMap(Searchform searchform){
		// 获取失联人员信息
		getUnMap();
		
		// 获取进入限制区域人员信息
		getAlMap();
		
		// 获取井下超时人员情况
		getOTList(searchform);
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

	public void getOTList(Searchform searchform){
		 searchform.setStarttime(getStartTime());
		 searchform.setEndtime(getEndTime());
		 List<Integer> list = new ArrayList<Integer>();
		 list.add(1);
		 searchform.setArea_ids(list);
		 overTimeRecs = eptDao.getOvertime(searchform);
	 }

//	public void makeMap(String table, String time) {
//		map = new HashMap<String, Object>();
//		map.put("table", table);
//		map.put("time", time);
//		map.put("startTime", getStartTime());
//		map.put("endTime", getEndTime());
//	}

	public long compare() throws ParseException {
		Date paraDay = sdf.parse(day.concat(startTime));
		Date today = sdf.parse(sdf.format(today()).concat(startTime));
		return (today.getTime() - paraDay.getTime());
	}

	public String getToday() {
		Date date = today();
		return sdf.format(date);
	}

	public Date today() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		return date;
	}

	public void setExceptionData(List<DailyRecVo> recs, int maxTime) {
		Set<String> set = new HashSet<String>();
		for (DailyRecVo dr : recs) {
			if(maxPN > maxAllow){
				if(dr.getRemark() == null){
					dr.setRemark("超员");
				}else{
					if(!dr.getRemark().contains("超员"))
						dr.setRemark(dr.getRemark()+",超员");
				}
			}
			
			// 超时判定
			Integer sec = StaticUtilMethod.getSec(dr.getWellduration());
			if (maxTime > 0 && sec > maxTime) {
				LongStringVo wdt = null;
				if ("-".equals(dr.getStartTime())) {
					LinkedList<WorkerInAreaRec> list = wiDao
							.getRecentlyInMineRec(dr.getWorkerId(), dr
									.getWorkday().concat(startTime));
					WorkerInAreaRec recentlyInMineRec = null;
					if (list != null && list.size() > 0)
						recentlyInMineRec = list.getLast();

					if (recentlyInMineRec != null
							&& recentlyInMineRec.getStatus() == 1) {
						dr.setStartTime(recentlyInMineRec.getResponsetime());
						try {
							if ("至今".equals(dr.getEndTime()))
								wdt = StaticUtilMethod.longToTimeFormat(
										dr.getStartTime(),
										StaticUtilMethod.getNow());
							else if ("当日未出井".equals(dr.getEndTime())) {
								dr.setEndTime(dr.getWorkday().concat(endTime));
								wdt = StaticUtilMethod.longToTimeFormat(
										dr.getStartTime(), dr.getEndTime());
							} else
								wdt = StaticUtilMethod.longToTimeFormat(
										dr.getStartTime(), dr.getEndTime());

							dr.setWellduration(wdt.getTimCast());
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						dr.setStartTime("入井异常");
						dr.setWellduration("-");
					}

				}
				if (!"入井异常".equals(dr.getStartTime())) {
					int ot;
					if (wdt != null){
						ot = (int) wdt.getTime() / 1000 - maxTime;
					}else{
						ot = sec - maxTime;
					}
					String countTimeCast = StaticUtilMethod
							.countTimeCast(ot * 1000);
					dr.setWellduration(dr.getWellduration().concat("/ 超时")
							.concat(countTimeCast));
					
					if(dr.getRemark() == null){
						dr.setRemark("超时");
					}else{
						if(!dr.getRemark().contains("超时"))
							dr.setRemark(dr.getRemark()+",超时");
					}
				}
			}
			
			//失联和进入限制区域判定
			String al = null;
			String un = null;
			boolean timeString = false;
			if(dr.getStartTime().equals("-")){
				timeString = true;
			}else{
				timeString = StaticUtilMethod.isTimeString(dr.getStartTime());
			}
				
			boolean timeString2 = false;
			if(dr.getEndTime().equals("当日未出井") || dr.getEndTime().equals("至今")){
				timeString2 = true;
			}else{
				timeString2 = StaticUtilMethod.isTimeString(dr.getEndTime());
			}
				
			if(timeString && timeString2){
				List<ExpVo> list = unMap.get(dr.getRfcard_id());
				if(list != null && list.size() > 0){
					for(ExpVo ev : list){
						try {
							boolean mid = false;
							
							if(dr.getStartTime().equals("-") && dr.getEndTime().equals("当日未出井")){
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), getStartTime(), getEndTime());
							}else if(dr.getStartTime().equals("-") ){
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), getStartTime(), dr.getEndTime());
							}else if(dr.getEndTime().equals("当日未出井") || dr.getEndTime().equals("至今")){
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), getEndTime());
							}else{
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), dr.getEndTime());
							}
							
							if(mid){
								if(dr.getRemark() == null){
									dr.setRemark("失联");
								}else{
									if(!dr.getRemark().contains("失联"))
										dr.setRemark(dr.getRemark()+",失联");
								}
								un = "失联";
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
							
							if(dr.getStartTime().equals("-") && dr.getEndTime().equals("当日未出井")){
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), getStartTime(), getEndTime());
							}else if(dr.getStartTime().equals("-") ){
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), getStartTime(), dr.getEndTime());
							}else if(dr.getEndTime().equals("当日未出井")  || dr.getEndTime().equals("至今")){
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), getEndTime());
							}else{
								mid = StaticUtilMethod.isMid(ev.getResponsetime(), dr.getStartTime(), dr.getEndTime());
							}
							
							if(mid){
								if(dr.getRemark() == null){
									dr.setRemark("进入限制区域");
								}else{
									if(!dr.getRemark().contains("进入限制区域"))
										dr.setRemark(dr.getRemark()+",进入限制区域");
								}
								al = "进入限制区域";
							}
								
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			DailyVo dailyVo = inMineMap.get(dr.getRfcard_id());
			DailyVo dailyVoA = isNull(dr, dailyVo);

			//超时人员统计
			if (maxTime > 0 && sec > maxTime && !"入井异常".equals(dr.getStartTime())) {
				set.add(dr.getRfcard_id());
				if (dailyVoA.getEptList() == null
						|| dailyVoA.getEptList().isEmpty()) {
					List<String> ept = new ArrayList<String>();
					ept.add("超时");
					dailyVoA.setEptList(ept);
				} else if (!dailyVoA.getEptList().contains("超时")) {
					dailyVoA.getEptList().add("超时");
				}
			}
			
			//失联标识添加
			if(un != null){
				if(dailyVoA.getEptList() == null){
					List<String> eptList = new ArrayList<String>();
					eptList.add(un);
					dailyVoA.setEptList(eptList);
				}else{
					if(!dailyVoA.getEptList().contains(un)){
						List<String> eptList = dailyVoA.getEptList();
						eptList.add(un);
						dailyVoA.setEptList(eptList);
					}
				}
			}
			//进入限制区域添加
			if(al != null){
				if(dailyVoA.getEptList() == null){
					List<String> eptList = new ArrayList<String>();
					eptList.add(al);
					dailyVoA.setEptList(eptList);
				}else{
					if(!dailyVoA.getEptList().contains(al)){
						List<String> eptList = dailyVoA.getEptList();
						eptList.add(al);
						dailyVoA.setEptList(eptList);
					}
				}
			}
			
			inMineMap.put(dr.getRfcard_id(), dailyVoA);
		}
		totalOT = set.size();
	}

	@SuppressWarnings("unchecked")
	public DailyVo isNull(DailyRecVo dr, DailyVo dailyVo) {
		List<Map<String, String>> list;
		if (dailyVo == null) {
			dailyVo = new DailyVo();
			list = new ArrayList<Map<String, String>>();
		} else {
			list = dailyVo.getList();
		}
		addData(dr, dailyVo, list);
		return dailyVo;
	}

	public void addData(DailyRecVo dr, DailyVo dailyVo,
			List<Map<String, String>> list) {

		Map<String, String> map = new HashMap<String, String>();
		if (dr.getStartTime().equals("-"))
			map.put("starttime", "昨日未出井");
		else
			map.put("starttime", dr.getStartTime());
		
		map.put("endtime", dr.getEndTime());
		map.put("wellduration", dr.getWellduration());
		
		if (dr.getRemark() != null && dr.getRemark() != "")
			map.put("remark", dr.getRemark());
		else
			map.put("remark", "");
		
		list.add(map);
		setVo(dailyVo, dr, list);
	}

	public void setVo(DailyVo dailyVo, DailyRecVo dr,
			List<Map<String, String>> list) {
		dailyVo.setRfcard_id(dr.getRfcard_id());
		dailyVo.setName(dr.getName());
		dailyVo.setDepartId(dr.getDepartId());
		dailyVo.setDepartName(dr.getDepartName());
		dailyVo.setWorkday(dr.getWorkday());
		dailyVo.setWorktypeId(dr.getWorktypeId());
		dailyVo.setWorktypeName(dr.getWorktypeName());
		dailyVo.setList(list);
	}

	public String getStartTime() {
		return day.concat(startTime);
	}

	public String getEndTime() {
		return day.concat(endTime);
	}

	public void getMaxAllow() {
		Calendar cal =Calendar.getInstance();
		String format = sdf.format(cal.getTime());
		Area2 area = areaDao.getById(1);
		if(day.equals(format)){
			maxAllow = area.getMax_allow();
		}else{
			AreaChangeVo areaChange = areaDao.getAreaChange(day);
			if(areaChange != null)
				maxAllow = areaChange.getMax_allow();
			else
				maxAllow = area.getMax_allow();
		}
		
	}

	/**
	 * 每月上下井及每天汇总统计
	 * 
	 * @param request
	 * @param searchform
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/monthlyInMine", method = RequestMethod.POST)
	@ResponseBody
	public Object getMonth(HttpServletRequest request,
			@RequestBody Searchform searchform) throws ParseException {
		// 初始化全局变量
		DayRecsMap = new HashMap<String, List<DailyRecVo>>();
		overManMap = new HashMap<String, Integer>();
		resultList = new ArrayList<Object>();
		// 获取当前年月
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);

		// 获取传入月份第一和最后一天
		String yearmonth = searchform.getStarttime();
		getFLDay(searchform);
		cal.setTime(firstday);
		int month2 = cal.get(Calendar.MONTH) + 1;
		int year2 = cal.get(Calendar.YEAR);
		if (month == month2 && year == year2) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			lastday = c.getTime();
		}

		cal.setTime(firstday);

		// 获取月数据
		searchform.setStarttime(dateToString(firstday));
		searchform.setEndtime(dateToString(lastday));
		List<DailyRecVo> monthlyData = dailyDao.getMonthlyData(searchform);

		// 获取超员人员
		searchform.setStarttime(dateToString(firstday, startTime));
		searchform.setEndtime(dateToString(lastday, endTime));
		List<OverManVo> overManByMonth = eptDao.getOverManByMonth(searchform);
		
		// 将本月所有超员人员算成每天超员及计算总数
		int totalOM = dealPN(overManByMonth);

		// 月数据算入每天
		int totalPn = 0;
		Set<String> totalPersonNumSet = new HashSet<String>();
		if (StaticUtilMethod.notNullOrEmptyList(monthlyData)) {
			for (DailyRecVo ap : monthlyData) {
				totalPersonNumSet.add(ap.getRfcard_id());
				String workDay = ap.getWorkday();
				setDayData(ap, workDay);
			}
		}
		totalPn = totalPersonNumSet.size();
		
		// 计算每天总体情况
		while (lastday.getTime() - cal.getTime().getTime() >= 0) {
			String tDay = sdf.format(cal.getTime());
			Map<String, Object> dailyDataMap = new HashMap<String, Object>();
			dailyDataMap.put("day", tDay);
			Integer om = overManMap.get(tDay);
			if(om == null){
				dailyDataMap.put("totalOM", 0);
			}else{
				dailyDataMap.put("totalOM", om);
			}

			List<DailyRecVo> dayRecs = DayRecsMap.get(tDay);
			Set<String> personNumSet = new HashSet<String>();
			Set<String> unSet = new HashSet<String>();
			Set<String> OTSet = new HashSet<String>();
			Set<String> ALSet = new HashSet<String>();
			if (StaticUtilMethod.notNullOrEmptyList(dayRecs)) {
				for (DailyRecVo ap : dayRecs) {
					personNumSet.add(ap.getRfcard_id());
					String remark = ap.getRemark();
					if(remark != null){
						if(remark.contains("超时")){
							OTSet.add(ap.getRfcard_id());
						}
						if(remark.contains("失联")){
							unSet.add(ap.getRfcard_id());
						}
						if(remark.contains("进入限制区域")){
							ALSet.add(ap.getRfcard_id());
						}
					}
				}
			}
			setDailyRec(personNumSet, dailyDataMap, "totalPN");
			setDailyRec(OTSet, dailyDataMap, "totalOT");
			setDailyRec(unSet, dailyDataMap, "totalUN");
			setDailyRec(ALSet, dailyDataMap, "totalAL");
			totalOT += OTSet.size();
			totalUN += unSet.size();
			totalAL += ALSet.size();
			resultList.add(dailyDataMap);
			cal.add(Calendar.DATE, +1);
		}
		// 加上当日统计，统计值为“-”
		Calendar clen = Calendar.getInstance();
		String format = df.format(clen.getTime());
		if (yearmonth.equals(format)) {
			Map<String, Object> dailyDataMap = new HashMap<String, Object>();
			Calendar calen = Calendar.getInstance();
			dailyDataMap.put("day", sdf.format(calen.getTime()));
			dailyDataMap.put("totalPN", "-");
			dailyDataMap.put("totalOM", "-");
			dailyDataMap.put("totalOT", "-");
			dailyDataMap.put("totalUN", "-");
			dailyDataMap.put("totalAL", "-");
			resultList.add(dailyDataMap);
		}
		result.put("data", resultList);
		result.put("totalPN", totalPn);
		result.put("totalOM", totalOM);
		result.put("totalOT", totalOT);
		result.put("totalUN", totalUN);
		result.put("totalAL", totalAL);
		
		return result.setStatus(0, "ok");
	}

	public void setDailyRec(Set<?> set, Map<String, Object> dailyDataMap,
			String name) {
		if (set == null)
			set = new HashSet<>();

		dailyDataMap.put(name, set.size());
	}

	public int dealPN(List<OverManVo> overManByMonth) {
		int totalOM = 0;
		if (StaticUtilMethod.notNullOrEmptyList(overManByMonth)) {
			for (int i = 0; i < overManByMonth.size(); i++) {
				OverManVo om = overManByMonth.get(i);
				String mDay = om.getResponsetime().substring(0, 10);
				Integer total = overManMap.get(mDay);
				if(total == null){
					total = 0;
				}
				total += om.getPersonNum() - om.getMaxAllow();
				totalOM += om.getPersonNum() - om.getMaxAllow();
				overManMap.put(mDay, total);
			}
		}
		return totalOM;
	}

	public void setDayData(DailyRecVo ap, String day) {
		List<DailyRecVo> listDayRecs = DayRecsMap.get(day);

		if (listDayRecs == null) {
			listDayRecs = new ArrayList<DailyRecVo>();
		}
		listDayRecs.add(ap);
		DayRecsMap.put(day, listDayRecs);
	}

	public void getFLDay(Searchform searchform) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String s = searchform.getStarttime().concat("-01");
		Date parse = sdf.parse(s);
		cal.setTime(parse);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		firstday = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		lastday = cal.getTime();
	}

	public String dateToString(Date date) {
		return sdf.format(date);
	}

	public String dateToString(Date date, String s) {
		return sdf.format(date).concat(s);
	}

}