package com.cm.dao;

import java.util.List;
import java.util.Map;

import com.cm.entity.OvertimeAlarm;
import com.cm.entity.vo.AreaPass;
import com.cm.entity.vo.ExpVo;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.Searchform;
import com.cm.entity.vo.Unconnection;


public interface IExceptionDao {

	public List<OvertimeAlarm> getOvertime(Searchform searchform);
	
	public List<Unconnection> getUnconnection(Searchform searchform);
	
	public List<AreaPass> getAreaLimited(Searchform searchform);
	
	public List<OverManVo> getAreasOverMan(String sTime, String eTime);
	
	public List<OverManVo> getOverManByDay(Searchform searchform);
	
	public List<OverManVo> getOverManByMonth(Searchform searchform);
	
	public List<Integer> getEXPIds(Map<String, Object> map);
	
	public List<ExpVo> getExpAL(String startTime,String endTime);
	
	public List<ExpVo> getExpUN(String startTime,String endTime);
	
	public List<AreaPass> getLimited(Searchform searchform);
	
	public List<OverManVo> getMineOverman(String startTime, String endTime);
}
