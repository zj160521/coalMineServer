package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.SensorCallVo;



public interface SensorCallDao {
	
	//最近一次模拟量报警
	public List<AnaloginfoQuery> getAnalogAlarm(SensorCallVo callVo);
	
	//最近一次模拟量断电
	public List<AnaloginfoQuery> getAnalogPower(SensorCallVo callVo);
	
	//最近一次统计数据
	public List<AnaloginfoQuery> getAnalogStatis(@Param("tablename")String tablename);
	
	//开关量调用显示
	public List<AnaloginfoQuery> getSwitchSensorCall(SensorCallVo callVo);
	
	//模拟量报警显示
	public List<AnaloginfoQuery> getsensorAlarm(SensorCallVo callVo);
	//开关量报警显示
	public List<AnaloginfoQuery> getswitchSensorAlarm(SensorCallVo callVo);
	
	//模拟量断电显示
	public List<AnaloginfoQuery> getsensorPower(SensorCallVo callVo);
	//开关量断电显示
	public List<AnaloginfoQuery> getswitchSensorPower(SensorCallVo callVo);
	
	//模拟量馈电异常显示
	public List<AnaloginfoQuery> getsensorRepower(SensorCallVo callVo);
	//开关量馈电异常显示
	public List<AnaloginfoQuery> getswitchSensorRepower(SensorCallVo callVo);
	
	public List<AnaloginfoQuery> getfailure(SensorCallVo callVo);
	
	public List<AnaloginfoQuery> getfeedstatus();
	
	public List<AnaloginfoQuery> getswitchfeedstatus();
	
	//开关量状态变动
	public List<AnaloginfoQuery> getSwitchStateChange(NameTime nameTime);
}
