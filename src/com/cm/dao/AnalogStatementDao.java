
package com.cm.dao;

import java.util.List;

import com.cm.entity.Analoginfo;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.HistoryData;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SensorReportsVo;

public interface AnalogStatementDao {
	
	//根据某天分页查询
	public List<SensorReportsVo> getAnasByDay(NameTime nameTime);
	
	
	//根据时间范围查询模拟量报警信息
	public List<AnaloginfoQuery> getAlerts(NameTime nameTime);
	
	public List<Analoginfo> getTimecount(NameTime nameTime);
	//分页查询单个传感器的历史数据
	public List<Analoginfo> getHistory(NameTime nameTime);
	
	//查询单个传感器的数据
	public List<HistoryData> getHistorys(NameTime nameTime);
	
	public int getcountHis(NameTime nameTime);
	
	public List<AnaloginfoQuery> getTime(NameTime nameTime);
	
	public List<AnaloginfoQuery> getPowers(NameTime nameTime);
	
	public List<AnaloginfoQuery> getfeeds(NameTime nameTime);
	
}
