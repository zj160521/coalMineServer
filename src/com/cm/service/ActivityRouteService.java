package com.cm.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.ActivityRouteDao;
import com.cm.entity.Area2;
import com.cm.entity.Coalmine_route;
import com.cm.entity.PersonAlarmSearch;
import com.cm.entity.Worker;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.WorkerTrackRecord;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.OvertimeAlarmVo;
import com.cm.entity.vo.WorkerAreaWarnVo;
import com.cm.entity.vo.WorkerExitWarnVo;
import com.cm.entity.vo.WorkerInAreaRecVo;

@Service("ActivityRouteService")
public class ActivityRouteService{
	@Autowired
	public ActivityRouteDao dao;
	
	@Autowired
	public PersonAlarmSearchService service;
	
	public List<Coalmine_route> getRouteByCard(Coalmine_route route){
		return dao.getRouteByCard(route);
	}
	
	public List<Coalmine_route> cardroute(Coalmine_route route){
		return dao.cardroute(route);
	}

	public List<Area2> getAllArea() {
		return dao.getAllArea();
	}

	public List<Worker> getAllWorker() {
		return dao.getAllWorker();
	}

	public List<WorkerTrackRecord> getbyCard(NameTime nameTime) {
		List<WorkerInAreaRec> list = dao.getbyCard(nameTime);
		List<WorkerTrackRecord> list2 = new ArrayList<WorkerTrackRecord>();
		String e = null;
		String f = null;
		for(int i=0;i<list.size();i++){
			if(i==0){
				e = list.get(i).getResponsetime();
			}
			if(list.get(i).getArea_id()==0&&list.get(i).getStatus()==1){
				e = list.get(i).getResponsetime();
			}
			if(list.get(i).getArea_id()==0&&list.get(i).getStatus()==2){
				f = list.get(i).getResponsetime();
				WorkerTrackRecord record = new WorkerTrackRecord();
				record.setCard_id(list.get(i).getCard_id());
				record.setName(list.get(i).getName());
				record.setTheDate(list.get(i).getTheDate());
				record.setIntoTime(e);
				record.setOutTime(f);
				list2.add(record);
				e = list.get(i).getResponsetime();
			}
			/*if(list.get(i).getArea_id()==0&&list.get(i).getDev_id()==0&&list.get(i).getStatus()==2){
				f = list.get(i).getResponsetime();
				WorkerTrackRecord record = new WorkerTrackRecord();
				record.setCard_id(list.get(i).getCard_id());
				record.setName(list.get(i).getName());
				record.setTheDate(list.get(i).getTheDate());
				record.setIntoTime(e);
				record.setOutTime(f);
				list2.add(record);
				e = list.get(i).getResponsetime();
			}*/
			if(i==list.size()-1&&list.get(i).getArea_id()!=0){
				f = list.get(i).getResponsetime();
				WorkerTrackRecord record = new WorkerTrackRecord();
				record.setCard_id(list.get(i).getCard_id());
				record.setName(list.get(i).getName());
				record.setTheDate(list.get(i).getTheDate());
				record.setIntoTime(e);
				record.setOutTime(f);
				list2.add(record);
			}
		}
		
		return list2;
	}

	public int getCountByCard(NameTime nameTime) {
		
		return dao.getCountByCard(nameTime);
	}

	public List<WorkerInAreaRecVo> getTrajectory(WorkerTrackRecord record) {
		PersonAlarmSearch search = new PersonAlarmSearch();
		search.setCard_id(Integer.parseInt(record.getCard_id()));
		search.setStarttime(record.getIntoTime());
		search.setEndtime(record.getOutTime());
		List<WorkerAreaWarnVo> list = service.getallWorkerAreaWarn(search);
		List<WorkerExitWarnVo> list2 = service.getallWorkerExitWarn(search);
		List<OvertimeAlarmVo> list3 = service.getallOvertimeAlarm(search);
		List<OverManVo> list4 = service.getallOverMan(search);
		Map<String,WorkerAreaWarnVo> map = new HashMap<String, WorkerAreaWarnVo>();
		Map<String, WorkerExitWarnVo> map2 = new HashMap<String, WorkerExitWarnVo>();
		Map<String, OvertimeAlarmVo> map3 = new HashMap<String, OvertimeAlarmVo>();
		Map<String, OverManVo> map4 = new HashMap<String, OverManVo>();
		for(WorkerAreaWarnVo l:list){
			WorkerAreaWarnVo a = map.get(l.getResponsetime());
			if(a==null){
				map.put(l.getResponsetime(), l);
			}
		}
		for(WorkerExitWarnVo l:list2){
			WorkerExitWarnVo a = map2.get(l.getResponsetime());
			if(a==null){
				map2.put(l.getResponsetime(), l);
			}
		}
		for(OvertimeAlarmVo l:list3){
			OvertimeAlarmVo a = map3.get(l.getAlarm_time());
			if(a==null){
				map3.put(l.getAlarm_time(), l);
			}
		}
		for(OverManVo l:list4){
			OverManVo a = map4.get(l.getResponsetime());
			if(a==null){
				map4.put(l.getResponsetime(), l);
			}
		}
		List<WorkerInAreaRecVo> list5 = dao.getTrajectory(record);
		for(int i=0;i<list5.size();i++){
			if(list5.get(i).getArea_id()==-2){
				list5.get(i).setAreaname("休息区");
			}
			if(list5.get(i).getArea_id()==0){
				list5.get(i).setAreaname("出入口");
			}
			list5.get(i).setId(i+1);;
			if(map.get(list5.get(i).getStartTime())!=null){
				list5.get(i).setAlarm(list5.get(i).getAlarm()+"限制区域报警,");;
			}
			if(map2.get(list5.get(i).getStartTime())!=null){
				list5.get(i).setAlarm(list5.get(i).getAlarm()+"门禁异常报警,");;
			}
			if(map3.get(list5.get(i).getStartTime())!=null){
				list5.get(i).setAlarm(list5.get(i).getAlarm()+"超时报警,");;
			}
			if(map4.get(list5.get(i).getStartTime())!=null){
				String[] a = map4.get(list5.get(i).getStartTime()).getCardss();
				for(int j=0;j<a.length;j++){
					if(a[j].equals(list5.get(i).getCard_id())){
						list5.get(i).setAlarm(list5.get(i).getAlarm()+"超员报警");;
						continue;
					}
				}
			}
			if(i<list5.size()-1){
				if(list5.get(i).getE_point()==list5.get(i+1).getE_point()&&list5.get(i).getN_point()==list5.get(i+1).getN_point()){
					list5.get(i+1).setSame(true);
				}
			}
		}
		return list5;
	}
}
