package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.DailyRecVo;
import com.cm.entity.vo.Searchform;
import com.cm.entity.vo.WorkerVo;

@Service("TodayCountService")
public class TodayCountService extends DailyBaseService{
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 计算当日人员上下井记录
	 * @param searchform
	 * @return
	 * @throws ParseException
	 */
	public List<DailyRecVo> countTodayRecords(Searchform searchform) throws ParseException{
		//初始化变量
		starttime = null;
		endtime = null;
		wellduration = null;
		flag = 0;
		count = 0;
		date = searchform.getStarttime();
		String stime = getStartTime();
		String etime = getEndTime();
		List<WorkerVo> allWorker = workDao.getWorkersBySearchform(searchform);
		
		if(searchform.getWorkerPlaceId() == 0 && searchform.getDepart_id() == 0 
				&& searchform.getWorktype_id() == 0 && searchform.getDutyId() == 0){
			List<Integer> cards = wiDao.getCard(stime, etime);
			for(Integer card : cards){
				WorkerVo wv = new WorkerVo();
				wv.setId(0);
				wv.setRfcard_id(card+"");
				allWorker.add(wv);
			}
		}
		
		resultList = new ArrayList<DailyRecVo>();
		resultList.clear();
//		List<Integer> workerIds = crDao.getWorkerIds(stime, etime);
		
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
//				if(contains){
//					getWellduration(getStartTime(), getNow());
//					setVo(worker, "-", "至今", wellduration, resultList, new WorkerInAreaRec());
//				}
					
			}else{
				if("0".equals(worker.getRfcard_id()))
					worker.setRfcard_id(dailyRecByWorker.get(0).getCard_id()+"/离职");
				dealWithData(dailyRecByWorker, worker);
			}
		}
		
		return resultList;
		
	}
	
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
			dv.setEndTime("至今");
		dv.setWorkday(date);
		
		if(wi.getRemark() != null && wi.getRemark() != "")
			dv.setRemark(wi.getRemark());
		
		resultList.add(dv);
		flag = 0;
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
	
	public void dealWithLast(WorkerInAreaRec wi, WorkerVo worker) throws ParseException{
		if(wi.getStatus() == 1){
			starttime = wi.getStartTime();
			endtime = getNow();
			flag = 2;
			
			getWellduration(starttime, endtime);
			setVo(worker, starttime, endtime, wellduration, resultList, wi);
		}
	}
	
	public void dealWithFirst(WorkerInAreaRec wi, WorkerVo worker) throws ParseException{
		starttime = getStartTime();
		endtime = wi.getStartTime();
		flag = 1;
		
		getWellduration(starttime, endtime);
		setVo(worker, starttime, endtime, wellduration, resultList, wi);
	}
	
	public String getNow(){
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		return df.format(date);
	}
}

