package com.cm.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import util.StaticUtilMethod;

import com.cm.dao.DailyDao;
import com.cm.dao.ICoalmineRouteDao;
import com.cm.dao.IWorkerInAreaRecDao;
import com.cm.dao.WorkerDao;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.DailyRecVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.WorkerVo;

public class DailyBaseService {

	@Autowired
	protected WorkerDao workDao;
	@Autowired
	protected IWorkerInAreaRecDao wiDao;
	@Autowired
	protected DailyDao dDao;
	@Autowired
	protected ICoalmineRouteDao crDao;
	@Autowired
	protected AreaService areaService;
	
	protected String startTime = " 00:00:00";
	protected String endTime = " 23:59:59";
	protected String date = null;
	protected List<DailyRecVo> resultList;
	protected WorkerInAreaRec wiar;
	protected String starttime;
	protected String endtime;
	protected String wellduration;
	protected int flag;
	protected int count;
		
	public void dealWithData(List<WorkerInAreaRec> dailyRecByWorker, WorkerVo worker) throws ParseException{
		if(dailyRecByWorker != null && dailyRecByWorker.size() > 0){
			WorkerInAreaRec fisrtRec = dailyRecByWorker.get(0);
			if(fisrtRec.getStatus() == 1){
				status1GetResultList(dailyRecByWorker, worker);
			}else if(fisrtRec.getStatus() ==  2){
				status2GetResultList(dailyRecByWorker, worker);
			}
		}else{
			setNoDataVo(worker, resultList);
		}
	}
	
	public String getStartTime(){
		return date.concat(startTime);
	}
	
	public String getEndTime(){
		return date.concat(endTime);
	}
	
	public int String2Int(String s){
		return Integer.parseInt(s);
	}
	
	public void setNoDataVo(WorkerVo worker,  List<DailyRecVo> resultList){
		starttime = "未入井";
		endtime = "-";
		wellduration = "-";
		setVo(worker, starttime, endtime, wellduration, resultList, new WorkerInAreaRec());
	}
	
	public void setVo(WorkerVo worker, String starttime, String endtime,
			String wellduration, List<DailyRecVo> resultList, WorkerInAreaRec wi){
		
		String rfcard_id = worker.getRfcard_id();
		String name = worker.getName();
		int departId = worker.getDepart_id();
		String departName = worker.getDepartname();
		int worktypeId = worker.getWorktype_id();
		String worktypeName = worker.getWorktypename();
		int special = worker.getSpecial();
		int duty_id = worker.getDuty_id();
		String dutyname = worker.getDutyname();
		int workplace_id = worker.getWorkplace_id();
		String workplace = worker.getWorkplace();
		
		DailyRecVo dv = new DailyRecVo();
		dv.setWorkerId(worker.getId());
		dv.setRfcard_id(rfcard_id);
		dv.setName(name);
		dv.setDepartId(departId);
		dv.setDepartName(departName);
		dv.setWorktypeId(worktypeId);
		dv.setWorktypeName(worktypeName);
		dv.setWellduration(wellduration);
		dv.setStartTime(starttime);
		dv.setSpecial(special);
		dv.setDuty_id(duty_id);
		dv.setDutyname(dutyname);
		dv.setWorkplace_id(workplace_id);
		dv.setWorkplace(workplace);
		if(flag == 1)
			dv.setStartTime("-");
		dv.setEndTime(endtime);
		if(flag == 2)
			dv.setEndTime("当日未出井");
		dv.setWorkday(date);
		
		if(wi.getRemark() != null && wi.getRemark() != "")
			dv.setRemark(wi.getRemark());
		
		resultList.add(dv);
		flag = 0;
	}
	
	public void status1GetResultList(List<WorkerInAreaRec> dailyRecByWorker, WorkerVo worker) throws ParseException{
		count = 0;
		for(int i = 0 ; i < dailyRecByWorker.size(); i++){
			WorkerInAreaRec wi = dailyRecByWorker.get(i);
			if(wi.getDev_id() == 0 && wi.getStatus() == 2)
				wi.setRemark("强制出井");
			
			wi.setStartTime(wi.getStartTime().substring(0, 19));
			isSetVo(wi, worker);
			count++;
			
			if(StaticUtilMethod.isLastRec(dailyRecByWorker, i+1))
				dealWithLast(wi, worker);
		}
	}
	
	public void isSetVo(WorkerInAreaRec wi, WorkerVo worker) throws ParseException{
		if(count % 2 == 0){
			dealDouble(wi, worker);
		}else{
			if(wi.getStatus() == 1){
				setVo(worker, wiar.getStartTime(), "出井异常", "-", resultList, wi);
				wiar = wi;
				count--;
			}else{
				countWellduration(wiar, wi);
				setVo(worker, starttime, endtime, wellduration, resultList, wi);
			}
		}
	}
	
	public void dealDouble(WorkerInAreaRec wi, WorkerVo worker) throws ParseException{
		if(wi.getStatus() == 2){
			setVo(worker, "入井异常", wi.getStartTime(), "-", resultList, wi);
			count--;
		}else{
			wiar = wi;
		}
	}
	
	public void countWellduration(WorkerInAreaRec fst, WorkerInAreaRec scd) throws ParseException{
		starttime = fst.getStartTime();
		endtime = scd.getStartTime();
		getWellduration(starttime, endtime);
	}
	
	public void getWellduration(String starttime, String endtime) throws ParseException{
		LongStringVo lsv = StaticUtilMethod.longToTimeFormat(starttime, endtime);
		wellduration = lsv.getTimCast();
	}
	
	public void dealWithLast(WorkerInAreaRec wi, WorkerVo worker) throws ParseException{
		if(wi.getStatus() == 1){
			starttime = wi.getStartTime();
			endtime = getEndTime();
			flag = 2;
			
			getWellduration(starttime, endtime);
			setVo(worker, starttime, endtime, wellduration, resultList, wi);
		}
	}
	
	public void status2GetResultList(List<WorkerInAreaRec> dailyRecByWorker, WorkerVo worker) throws ParseException{
		count = 1;
		for(int i = 0; i < dailyRecByWorker.size(); i++){
			WorkerInAreaRec wi = dailyRecByWorker.get(i);
			wi.setStartTime(wi.getStartTime().substring(0, 19));
			if(wi.getDev_id() == 0 && wi.getStatus() == 2)
				wi.setRemark("强制出井");
			
			if(i == 0){
				dealWithFirst(wi, worker);
				count++;
				continue;
			}
			
			isSetVo(wi, worker);
			count++;
			if(StaticUtilMethod.isLastRec(dailyRecByWorker, i+1))
				dealWithLast(wi, worker);
		}
	}
	
	public void dealWithFirst(WorkerInAreaRec wi, WorkerVo worker) throws ParseException{
		starttime = getStartTime();
		endtime = wi.getStartTime();
		flag = 1;
		
		getWellduration(starttime, endtime);
		setVo(worker, starttime, endtime, wellduration, resultList, wi);
	}
}
