package com.cm.dao;

import java.util.List;

import com.cm.entity.AnalogQuery;
import com.cm.entity.vo.AnalogAllVo;
import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.AnalogoutVo;
import com.cm.entity.vo.NameTime;

public interface AnaloginfoDao {
	

	//查询
	public List<AnaloginfoQuery> getall(NameTime nameTime);
	
	//查询符合记录的总条数
	public int getallcount(NameTime nameTime);
	
	//查询所有记录
	public List<AnaloginfoQuery> getallAna(NameTime nameTime);
	
	public List<AnalogoutVo> getAnas(NameTime nameTime);
	
	public AnaloginfoQuery getvalue(AnalogQueryVo queryVo);
	
	public List<AnaloginfoQuery> getpower(NameTime nameTime);
	
	public List<AnaloginfoQuery> getfeed(NameTime nameTime);
	
	public List<AnaloginfoQuery> getfeedError(NameTime nameTime);
	
	public List<AnalogQuery> getNullRec(String startTime);
	
	public List<AnalogAllVo> getAllAnaloginfo(String startTime);
	
	public List<AnaloginfoQuery> getDevlink();
}
