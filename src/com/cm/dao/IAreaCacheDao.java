package com.cm.dao;

import java.util.List;

import com.cm.entity.Area;
import com.cm.entity.AreaWorker2;
import com.cm.entity.AttendanceMap;
import com.cm.entity.Cardreder;
import com.cm.entity.Substation2;
import com.cm.entity.Worker;
import com.cm.entity.vo.AreaVo;

public interface IAreaCacheDao {
	public List<AreaVo> getAreaTable();
	public List<String> getAllCard();
	public List<Area> getAllArea();
	public List<AreaWorker2> getAreaWorker();
	public List<Cardreder> getExitCardreader();
	public List<Cardreder> getCardreaderName();
	public List<Substation2> getSubstation();
	public List<Worker> getEntranceCard();
	public List<AttendanceMap> getAttendance();
}
