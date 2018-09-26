package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.dao.ILocalizeDao;
import com.cm.dao.WorkerDao;
import com.cm.entity.Localize;
import com.cm.entity.Worker;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.LocalizeVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.Searchform;

@Service("localService")
public class LocalService {

	@Autowired
	private ILocalizeDao ldao;
	@Autowired
	private WorkerDao workDao;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 时间段内下井人员统计
	 * 
	 */
	public List<LocalizeVo> getAllAream(Searchform searchform) throws Exception {
		//按条件获取员工
		List<Worker> allWorker = workDao.getAllBySearchform(searchform);
		
		List<LocalizeVo> resultList = new ArrayList<LocalizeVo>();
		
		for (Worker worker : allWorker) {
			//获取小于endTime的最大的进出时间记录
			List<WorkerInAreaRec> workerInOutRecord = ldao
					.getWorkerInOutRecord(worker.getRfcard_id(), searchform.getEndtime());
			
			subStringTime(workerInOutRecord);
			
			//没有出入井记录的显示为"-"
			if(workerInOutRecord == null || workerInOutRecord.size() == 0){
				setNoDataLocalizeVo(worker, resultList);
				
			//有一条出入记录
			}else if(workerInOutRecord.size() == 1){
				WorkerInAreaRec wiar = workerInOutRecord.get(0);
				String starttime = null;
				String endtime = null;
				String wellduration = null;
				
				//有入井记录没有出井记录
				if(wiar.getStatus() ==  1){
					starttime = wiar.getStartTime();
					WorkerInAreaRec outRecord = ldao.getOutRecord(worker.getRfcard_id(), searchform.getEndtime());
					endtime = (outRecord == null) ? "未出井" : outRecord.getStartTime().substring(0, 19);
					
					if(endtime.equals("未出井"))
						endtime = df.format(new Date());
					
					setWL(starttime, endtime, wellduration, worker, resultList);
				
				//有出井记录没有入井记录，按理是错误数据，置为"-"
				}else if(wiar.getStatus() == 2){
					setNoDataLocalizeVo(worker, resultList);
				}
			//有出入记录
			}else if(workerInOutRecord.size() == 2){
				WorkerInAreaRec inRecord = null;
				WorkerInAreaRec outRecord = null;
				
				getInOutRecord(workerInOutRecord, inRecord, outRecord);
				String starttime = inRecord.getStartTime();
				String endtime = outRecord.getStartTime();
				String wellduration = null;
				
				boolean compareTwoTime = compareTwoTime(endtime, starttime);
				
				if(compareTwoTime){
					boolean compareTwoTime2 = compareTwoTime(searchform.getStarttime(), starttime);
					if(compareTwoTime2){
						setWL(starttime, endtime, wellduration, worker, resultList);
					}else{
						List<WorkerInAreaRec> records = ldao.getStarttimeInOutRecord(worker.getRfcard_id(),
								searchform.getStarttime());
						if(records.size() == 1){
							String time = records.get(0).getStartTime();
							if(time.equals(starttime)){
								setWL(starttime, endtime, wellduration, worker, resultList);
							}else{
								List<WorkerInAreaRec> SErecords = ldao.getRecordsBTWStartAndEnd(time, endtime);
								getResultObjects(SErecords, worker, resultList);
							}
						}else{
							
						}
					}
				}else{
					
				}
			}
			
		}

		return resultList;
	}

	public List<Localize> getAllAream2(Searchform searchform) throws Exception {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<Localize> localizeList = ldao.getOutWorker(searchform);
		
		for (Localize localize : localizeList) {
			List<WorkerInAreaRec> workerInOutRecord = ldao
					.getAllWorkerInOutRecord(localize.getRfcard_id() + "");
			localize.setEndtime(localize.getEndtime().substring(0, 19));
			if (localize.getArea_id() == 0)
				localize.setAreaname("起始区域");

			long minEnd = 0;
			WorkerInAreaRec startWorkerInAreaRec = null;
			for (WorkerInAreaRec workerInAreaRec : workerInOutRecord) {
				if (workerInAreaRec.getStatus() == 1) {
					long time = df.parse(localize.getEndtime()).getTime()
							- df.parse(
									workerInAreaRec.getStartTime()
											.substring(0, 19)).getTime();
					if (time > 0) {
						if (minEnd == 0) {
							minEnd = time;
							startWorkerInAreaRec = workerInAreaRec;
						} else if (minEnd > 0 && time < minEnd) {
							minEnd = time;
							startWorkerInAreaRec = workerInAreaRec;
						}
					}
				}

			}
			if (startWorkerInAreaRec != null) {
				localize.setStarttime(startWorkerInAreaRec.getStartTime()
						.substring(0, 19));
				LongStringVo lsv = StaticUtilMethod.longToTimeFormat(
						localize.getEndtime(), localize.getStarttime());
				localize.setWellduration(lsv.getTimCast());
				localize.setLongTimeCast(lsv.getTime());
			}

		}

		return localizeList;
	}
	
	public List<Localize> areaQuery(Searchform searchform) throws Exception {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<Localize> allLocalize = ldao.getAreaAll(searchform);
		ArrayList<WorkerInAreaRec> allworkerInAreaRec = ldao
				.getAllworkerInAreaRec();

		List<Localize> delLocalizeList = new ArrayList<Localize>();

		for (Localize localize : allLocalize) {

			localize.setStarttime(localize.getStarttime().substring(0, 19));
			if (localize.getStatus() == 1) {

				long minEnd = 0;
				WorkerInAreaRec minEndWorkerInAreaRec = null;
				WorkerInAreaRec selfWorkerInAreaRec = null;
				for (WorkerInAreaRec workerInAreaRec : allworkerInAreaRec) {

					workerInAreaRec.setStartTime(workerInAreaRec.getStartTime()
							.substring(0, 19));

					if (workerInAreaRec.getCard_id().equals(
							localize.getRfcard_id() + "")
							&& workerInAreaRec.getStartTime().equals(
									localize.getStarttime()))
						selfWorkerInAreaRec = workerInAreaRec;

					if (workerInAreaRec.getStatus() == 1)
						continue;

					if (workerInAreaRec.getCard_id().equals(
							localize.getRfcard_id() + "")) {
						long timeCast = df
								.parse(workerInAreaRec.getStartTime())
								.getTime()
								- df.parse(localize.getStarttime()).getTime();
						if (timeCast > 0) {
							if (minEnd == 0) {
								minEnd = timeCast;
								minEndWorkerInAreaRec = workerInAreaRec;
							} else if (minEnd > 0 && timeCast < minEnd) {
								minEnd = timeCast;
								minEndWorkerInAreaRec = workerInAreaRec;
							}
						}
					}
				}
				if (minEndWorkerInAreaRec != null
						&& selfWorkerInAreaRec != null) {
					allworkerInAreaRec.remove(minEndWorkerInAreaRec);
					allworkerInAreaRec.remove(selfWorkerInAreaRec);
					localize.setEndtime(minEndWorkerInAreaRec.getStartTime());
				} else {
					delLocalizeList.add(localize);
				}

			} else if (localize.getStatus() == 2) {

				localize.setEndtime(localize.getStarttime());

				long minstart = 0;
				WorkerInAreaRec minStartWorkerInAreaRec = null;
				WorkerInAreaRec selfWorkerInAreaRec = null;
				for (WorkerInAreaRec workerInAreaRec : allworkerInAreaRec) {

					workerInAreaRec.setStartTime(workerInAreaRec.getStartTime()
							.substring(0, 19));

					if (workerInAreaRec.getCard_id().equals(
							localize.getRfcard_id() + "")
							&& workerInAreaRec.getStartTime().equals(
									localize.getStarttime()))
						selfWorkerInAreaRec = workerInAreaRec;

					if (workerInAreaRec.getStatus() == 2)
						continue;

					if (workerInAreaRec.getCard_id().equals(
							localize.getRfcard_id() + "")) {
						long timeCast = df.parse(localize.getEndtime())
								.getTime()
								- df.parse(workerInAreaRec.getStartTime())
										.getTime();
						if (timeCast > 0) {
							if (minstart == 0) {
								minstart = timeCast;
								minStartWorkerInAreaRec = workerInAreaRec;
							} else if (minstart > 0 && timeCast < minstart) {
								minstart = timeCast;
								minStartWorkerInAreaRec = workerInAreaRec;
							}
						}
					}
				}
				if (minStartWorkerInAreaRec != null
						&& selfWorkerInAreaRec != null) {
					allworkerInAreaRec.remove(minStartWorkerInAreaRec);
					allworkerInAreaRec.remove(selfWorkerInAreaRec);
					localize.setStarttime(minStartWorkerInAreaRec
							.getStartTime());
				} else {
					delLocalizeList.add(localize);
				}
			}
		}

		if (delLocalizeList.size() > 0) {
			for (Localize localize : delLocalizeList) {
				allLocalize.remove(localize);
			}
		}

		return allLocalize;
	}

	public List<Localize> getAllAtGate(Localize localize) {
		return ldao.getAllAtGate(localize);
	}

	public List<Localize> getAllandCardreder(Localize localize) {
		List<Localize> list = ldao.getAllandCardreder(localize);
		List<Localize> tlist = new ArrayList<Localize>();
		for (int i = 0; i < list.size(); i++) {
			Localize ll = new Localize();
			for (int j = i + 1; j < list.size(); j++) {
				// (下井)进入时间，(下井)进入时长
				if (list.get(i).getRfcard_id() == list.get(j).getRfcard_id()
						&& list.get(i).getArea_id() != list.get(j).getArea_id()) {

					ll.setRfcard_id(list.get(j).getRfcard_id());
					ll.setName(list.get(j).getName());
					ll.setDepartname(list.get(j).getDepartname());
					ll.setWorktypename(list.get(j).getWorktypename());
					ll.setAddr(list.get(j).getAddr());
					ll.setPosition(list.get(j).getPosition());
					ll.setStarttime(list.get(i).getStarttime());
					ll.setEndtime(list.get(j).getStarttime());
					tlist.add(ll);
					break;
				}
			}
		}
		return tlist;
	}

	/**
	 * 递归回去对象
	 * 
	 */
	public void getChildren(WorkerInAreaRec minWorkerInAreaRec,
			List<WorkerInAreaRec> wiList, List<LocalizeVo> resultList,
			Searchform searchform, Worker worker) throws ParseException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long minEnd = 0;
		WorkerInAreaRec endWorkerInAreaRec = null;
		for (WorkerInAreaRec wi : wiList) {
			if (wi.getStatus() == 1 || wi.getArea_id() != 0)
				continue;
			long time = df.parse(wi.getStartTime()).getTime()
					- df.parse(minWorkerInAreaRec.getStartTime()).getTime();
			if (time > 0) {
				if (minEnd == 0) {
					minEnd = time;
					endWorkerInAreaRec = wi;
				} else if (time < minEnd) {
					minEnd = time;
					endWorkerInAreaRec = wi;
				}
			}
		}
		if (endWorkerInAreaRec != null) {
			LocalizeVo localize = new LocalizeVo();
			localize.setDepartname(worker.getDepartname());
			localize.setWorktypename(worker.getWorktypename());
			localize.setEndtime(endWorkerInAreaRec.getStartTime());
			// localize.setEntry_status(entry_status);
			localize.setName(worker.getName());
			localize.setRfcard_id(Integer.parseInt(worker.getRfcard_id()));
			localize.setStarttime(minWorkerInAreaRec.getStartTime());

			LongStringVo lsv = StaticUtilMethod.longToTimeFormat(
					endWorkerInAreaRec.getStartTime(),
					minWorkerInAreaRec.getStartTime());
			localize.setWellduration(lsv.getTimCast());
			localize.setLongTimeCast(lsv.getTime());
			resultList.add(localize);

			if(df.parse(endWorkerInAreaRec.getStartTime()).getTime()
					- df.parse(searchform.getEndtime()).getTime() < 0){
				long minEnd2 = 0;
				WorkerInAreaRec endWorkerInAreaRec2 = null;
				for (WorkerInAreaRec wi : wiList) {
					if (wi.getStatus() == 1 && wi.getArea_id() == 0) {
						long time = df.parse(wi.getStartTime()).getTime()
								- df.parse(endWorkerInAreaRec.getStartTime())
										.getTime();
						if (time > 0) {
							if (minEnd2 == 0) {
								minEnd2 = time;
								endWorkerInAreaRec2 = wi;
							} else if (time < minEnd2) {
								minEnd2 = time;
								endWorkerInAreaRec2 = wi;
							}
						}
					}
				}

				if (endWorkerInAreaRec2 != null
						&& df.parse(endWorkerInAreaRec2.getStartTime()).getTime()
								- df.parse(searchform.getEndtime()).getTime() < 0) {
					getChildren(endWorkerInAreaRec2, wiList, resultList,
							searchform, worker);
				}
			}
			
		}else{
			LocalizeVo localize = new LocalizeVo();
			localize.setDepartname(worker.getDepartname());
			localize.setWorktypename(worker.getWorktypename());
			localize.setEndtime("至今");
			// localize.setEntry_status(entry_status);
			localize.setName(worker.getName());
			localize.setRfcard_id(Integer.parseInt(worker
					.getRfcard_id()));
			localize.setStarttime(minWorkerInAreaRec.getStartTime());
			
			LongStringVo lsv = StaticUtilMethod
					.longToTimeFormat(df.format(new Date()),
							minWorkerInAreaRec.getStartTime());
			localize.setWellduration(lsv.getTimCast());
			localize.setLongTimeCast(lsv.getTime());
			
			resultList.add(localize);
		}

	}

	/**
	 * 截取time字符串
	 * 
	 */
	public void subStringTime(List<WorkerInAreaRec> workerInOutRecord){
		for (WorkerInAreaRec wi : workerInOutRecord) {
			wi.setStartTime(wi.getStartTime().substring(0, 19));
		}
	}
	
	/**
	 * 新建LocalizeVo并添加进入resultList
	 * 
	 */
	public void setLocalizeVo(Worker worker, String starttime, String endtime,
			String wellduration, List<LocalizeVo> resultList){
		
		int rfcard_id = Integer.parseInt(worker.getRfcard_id());
		String name = worker.getName();
		String departname = worker.getDepartname();
		String worktypename = worker.getWorktypename();
		
		LocalizeVo lv = new LocalizeVo();
		lv.setRfcard_id(rfcard_id);
		lv.setName(name);
		lv.setDepartname(departname);
		lv.setWorktypename(worktypename);
		lv.setStarttime(starttime);
		lv.setEndtime(endtime);
		lv.setWellduration(wellduration);
		
		resultList.add(lv);
	}
	
	public void dealWithInOrOut(){
		
	}
	
	public void setNoDataLocalizeVo(Worker worker,  List<LocalizeVo> resultList){
		String starttime = "-";
		String endtime = "-";
		String wellduration = "-";
		setLocalizeVo(worker, starttime, endtime, wellduration, resultList);
	}
	
	public void getInOutRecord(List<WorkerInAreaRec> list, WorkerInAreaRec inRecord, WorkerInAreaRec outRecord ){
		for(WorkerInAreaRec wi : list){
			if(wi.getStatus() == 1)
				inRecord = wi;
			else if(wi.getStatus() == 2)
				outRecord = wi;
		}
	}
	
	public boolean compareTwoTime(String endtime, String starttime) throws ParseException{
			Date endTime = df.parse(endtime);
			Date startTime = df.parse(starttime);
			return (endTime.getTime() - startTime.getTime() >= 0) ? true : false;
	}
	
	public void getWellduration(String starttime, String endtime, String wellduration) throws ParseException{
		LongStringVo lsv = StaticUtilMethod.longToTimeFormat(starttime, endtime);
		wellduration = lsv.getTimCast();
	}
	
	public void getResultObjects(List<WorkerInAreaRec> SErecords, Worker worker,
			List<LocalizeVo> resultList) throws ParseException{
		int i = 0;
		WorkerInAreaRec wiar= null;
		String starttime = null;
		String endtime = null;
		String wellduration = null;
		
		for(WorkerInAreaRec wi : SErecords){
			if(i % 2 == 0){
				wiar = wi;
			}else{
				starttime = wiar.getStartTime();
				endtime = wi.getStartTime();
				setWL(starttime, endtime, wellduration, worker, resultList);
			}
		}
	}
	
	public void setWL(String starttime, String endtime, String wellduration,
			Worker worker, List<LocalizeVo> resultList) throws ParseException{
		getWellduration(starttime, endtime, wellduration);
		setLocalizeVo(worker, starttime, endtime, wellduration, resultList);
	}
}
