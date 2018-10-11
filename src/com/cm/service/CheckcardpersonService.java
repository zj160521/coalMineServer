package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.dao.CheckcardpersonDao;
import com.cm.entity.Checkcardperson;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.WorkerTrackRecord;
import com.cm.entity.vo.CheckcardpersonVo;

@Service("checkcardpersonService")
public class CheckcardpersonService {
	
	@Autowired
	private CheckcardpersonDao dao;
	
	
	public List<Checkcardperson> getall(CheckcardpersonVo checkcardpersonVo) throws ParseException{
		List<Checkcardperson> list = dao.getall(checkcardpersonVo);
		CheckcardpersonVo vo = checkcardpersonVo;
		for(Checkcardperson l:list){
			vo.setRfcard_id(l.getRfcard_id());
			vo.setWorker_id(l.getWorker_id());
			if(l.getName()==null||l.getName()==""){
				l.setName("临时卡");
			}
			if(l.getIsuse()>1){
				l.setName(l.getName()+"(离职)");
			}
			Checkcardperson checkcardperson = getData(vo);
			l.setNum_month(checkcardperson.getNum_month());
			l.setWelltime(checkcardperson.getWelltime());
		
		}
		
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WorkerTrackRecord> getallbycard(CheckcardpersonVo checkcardpersonVo) throws ParseException{
		List<WorkerInAreaRec> list = dao.getData(checkcardpersonVo);
		List<WorkerTrackRecord> list2 = new ArrayList<WorkerTrackRecord>();
		String e = null;
		String f = null;
		String k = null;
		String s = null;
		String h = null;
		for(int i=0;i<list.size();i++){
			if(i==0){
				e = list.get(i).getResponsetime();
				k = list.get(i).getTheDate();
				s = list.get(i).getCard_id();
				h = list.get(i).getName();
				if(h==null||h==""){
					h = "临时卡";
				}
				if(list.get(i).getIsuse()>1){
					h = h+"(离职)";
				}
			}
			if(list.get(i).getArea_id()==0&&list.get(i).getStatus()==1){
				e = list.get(i).getResponsetime();
				k = list.get(i).getTheDate();
				s = list.get(i).getCard_id();
				h = list.get(i).getName();
				if(h==null||h==""){
					h = "临时卡";
				}
				if(list.get(i).getIsuse()>1){
					h = h+"(离职)";
				}
			}
			if(i>0&&list.get(i).getArea_id()==0&&list.get(i).getStatus()==2){
				f = list.get(i).getResponsetime();
				h = list.get(i).getName();
				if(h==null||h==""){
					h = "临时卡";
				}
				if(list.get(i).getIsuse()>1){
					h = h+"(离职)";
				}
				WorkerTrackRecord record = new WorkerTrackRecord();
				record.setCard_id(s);
				record.setName(h);
				record.setTheDate(k);
				record.setIntoTime(e);
				record.setOutTime(f);
				list2.add(record);
				e = list.get(i).getResponsetime();
				k = list.get(i).getTheDate();
			}
			/*if(list.get(i).getArea_id()==0&&list.get(i).getDev_id()==0&&list.get(i).getStatus()==2){
				f = list.get(i).getResponsetime();
				h = list.get(i).getName();
				if(h==null||h==""){
					h = "临时卡";
				}
				WorkerTrackRecord record = new WorkerTrackRecord();
				record.setCard_id(s);
				record.setName(h);
				record.setTheDate(k);
				record.setIntoTime(e);
				record.setOutTime(f);
				list2.add(record);
				e = list.get(i).getResponsetime();
				k = list.get(i).getTheDate();
			}*/
			if(i==list.size()-1&&list.get(i).getArea_id()!=0){
				f = list.get(i).getResponsetime();
				h = list.get(i).getName();
				if(h==null||h==""){
					h = "临时卡";
				}
				if(list.get(i).getIsuse()>1){
					h = h+"(离职)";
				}
				WorkerTrackRecord record = new WorkerTrackRecord();
				record.setCard_id(s);
				record.setName(h);
				record.setTheDate(k);
				record.setIntoTime(e);
				record.setOutTime(f);
				list2.add(record);
			}
		}
		List<WorkerTrackRecord> list3 = new ArrayList<WorkerTrackRecord>();
		Map<String, WorkerTrackRecord> map = new HashMap<String, WorkerTrackRecord>();
		for(WorkerTrackRecord l:list2){
			WorkerTrackRecord a = map.get(l.getTheDate());
			if(a==null){
				a = new WorkerTrackRecord();
				a.setCard_id(l.getCard_id());
				a.setName(l.getName());
				a.setTheDate(l.getTheDate());
				list3.add(a);
				map.put(l.getTheDate(), a);
			}
			a.setName(l.getName());
			a.getList().add(getmap(l));
		}
		return list3;
	}
	
	private Map<String, Object> getmap(WorkerTrackRecord record) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("intoTime", record.getIntoTime());
		map.put("outTime", record.getOutTime());
		map.put("times", UtilMethod.longToTimeFormat(record.getIntoTime(), record.getOutTime()).getTimCast());
		
		return map;
	}
	
	
	private Checkcardperson getData(CheckcardpersonVo vo) throws ParseException{
		Checkcardperson checkcardperson = new Checkcardperson();
		List<WorkerInAreaRec> list = dao.getData(vo);
		int count = 0;
		long times = 0; 
//		LogOut.log.debug(list);
		if(list!=null){
			String a = null;
			for(int i=0;i<list.size();i++){
				if(i==0){
					a = list.get(i).getResponsetime();
				}
				if(list.get(i).getArea_id()==0&&list.get(i).getStatus()==1){
					a = list.get(i).getResponsetime();
				}
				if(i>0&&list.get(i).getArea_id()==0&&list.get(i).getStatus()==2){
					times = times+UtilMethod.countLongDvalue(a, list.get(i).getResponsetime());
					a = list.get(i).getResponsetime();
					count++;
				}
				/*if(list.get(i).getArea_id()==0&&list.get(i).getDev_id()==0&&list.get(i).getStatus()==2){
					times = times+StaticUtilMethod.countLongDvalue(a, list.get(i).getResponsetime());
					a = list.get(i).getResponsetime();
					count++;
				}*/
				if(i==list.size()-1&&list.get(i).getArea_id()!=0){
					times = times+UtilMethod.countLongDvalue(a, list.get(i).getResponsetime());
					count++;
				}
			}
			checkcardperson.setNum_month(count);
			checkcardperson.setWelltime(UtilMethod.countTimeCast(times));
			return checkcardperson;
		}else{
			return null;
		}
		
		
	}
}
