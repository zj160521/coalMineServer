package com.cm.dao;

import java.util.List;

import com.cm.entity.Person;
import com.cm.entity.PersonAlarmSearch;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.OvertimeAlarmVo;
import com.cm.entity.vo.WorkerAreaWarnVo;
import com.cm.entity.vo.WorkerExitWarnVo;
import com.cm.entity.vo.WorkerUnconnectionVo;

public interface PersonAlarmSearchDao {
	
	
	//查询区域限制报警人员
	public List<WorkerAreaWarnVo> getWorkerAreaWarn(PersonAlarmSearch search);
	public List<WorkerAreaWarnVo> getallWorkerAreaWarn(PersonAlarmSearch search);
	public int getWorkerAreaWarnCount(PersonAlarmSearch search);
	
	//查询门禁异常报警人员
	public List<WorkerExitWarnVo> getWorkerExitWarn(PersonAlarmSearch search);
	public List<WorkerExitWarnVo> getallWorkerExitWarn(PersonAlarmSearch search);
	public int getWorkerExitWarnCount(PersonAlarmSearch search);
	
	//查询失联报警人员
	public List<WorkerUnconnectionVo> getWorkerUnconnection(PersonAlarmSearch search);
	public List<WorkerUnconnectionVo> getallWorkerUnconnection(PersonAlarmSearch search);
	public int getWorkerUnconnectionCount(PersonAlarmSearch search);
	
	
	//查询区域超时报警人员
	public List<OvertimeAlarmVo> getOvertimeAlarm(PersonAlarmSearch search);
	public List<OvertimeAlarmVo> getallOvertimeAlarm(PersonAlarmSearch search);
	public int getOvertimeAlarmCount(PersonAlarmSearch search);
	
	//查询井下超时报警人员
	public List<OvertimeAlarmVo> getOvertimeAlarmbyone(PersonAlarmSearch search);
	public List<OvertimeAlarmVo> getallOvertimebyone(PersonAlarmSearch search);
	public int getOvertimebyoneCount(PersonAlarmSearch search);
	
	//查询区域超员、井下超员报警
	public List<OverManVo> getOverMan(PersonAlarmSearch search);
	public List<OverManVo> getallOverMan(PersonAlarmSearch search);
	public int getOverManCount(PersonAlarmSearch search);
	
	//查询井下超员报警
	public List<OverManVo> getOverManbyone(PersonAlarmSearch search);
	public List<OverManVo> getallOverManbyone(PersonAlarmSearch search);
	public int getOverManbyoneCount(PersonAlarmSearch search);
	
	public List<Person> getWorkerName(String[] card);
}
