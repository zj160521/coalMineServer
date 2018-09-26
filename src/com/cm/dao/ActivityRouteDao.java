package com.cm.dao;

import java.util.List;

import com.cm.entity.Area2;
import com.cm.entity.Coalmine_route;
import com.cm.entity.Worker;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.WorkerTrackRecord;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.OvertimeAlarmVo;
import com.cm.entity.vo.WorkerAreaWarnVo;
import com.cm.entity.vo.WorkerExitWarnVo;
import com.cm.entity.vo.WorkerInAreaRecVo;

public interface ActivityRouteDao {

	public List<Coalmine_route> getRouteByCard(Coalmine_route route);
	
	public List<Coalmine_route> cardroute(Coalmine_route route);
	
	public List<Area2> getAllArea();
	
	public List<Worker> getAllWorker();
	
	//分页查询人员轨迹记录列表
	public List<WorkerInAreaRec> getbyCard(NameTime nameTime);
	
	//查询数据总条数
	public int getCountByCard(NameTime nameTime);
	
	//人员轨迹数据
	public List<WorkerInAreaRecVo> getTrajectory(WorkerTrackRecord record);
	
	public List<WorkerAreaWarnVo> getWorkerAreaWarn(WorkerTrackRecord record);
	
	public List<WorkerExitWarnVo> getWorkerExitWarn(WorkerTrackRecord record);
	
	public List<OvertimeAlarmVo> getOvertimeAlarm(WorkerTrackRecord record);
	
	public List<OverManVo> getOverMan(WorkerTrackRecord record);
}
