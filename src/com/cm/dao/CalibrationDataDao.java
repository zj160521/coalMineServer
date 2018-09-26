package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.CalibrationData;
import com.cm.entity.Coalmine;
import com.cm.entity.vo.NameTime;

public interface CalibrationDataDao {
	
	public List<Coalmine> getall(@Param("tablename")String tablename);
	
	
	public void updatedata(CalibrationData data);
	
	public List<CalibrationData> getpagedata(NameTime nameTime);
	
	public int getalldata(NameTime nameTime);
	
	public List<CalibrationData> getallnull();
	
	public CalibrationData getvalue(@Param("tablename")String tablename,@Param("data")CalibrationData data);
}
