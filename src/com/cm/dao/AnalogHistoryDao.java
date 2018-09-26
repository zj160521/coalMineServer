package com.cm.dao;

import java.util.LinkedList;
import java.util.List;

import com.cm.entity.vo.AnalogCurveVo;
import com.cm.entity.vo.AnalogParamVo;
import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AnaloghisVo;
import com.cm.entity.vo.CutAlarmVo;
import com.cm.entity.vo.DevVo;
import com.cm.entity.vo.SensorVo;

public interface AnalogHistoryDao {

	public List<AnalogCurveVo> getCurveHistory(AnalogParamVo analogParamVo);
	
	public List<AnaloghisVo> getHistory(AnalogParamVo analogParamVo);
	
	public List<AnaloghisVo> getRealTimeRec(AnalogParamVo analogParamVo);
	
	public List<AnaloghisVo> getFeedErrorRealTimeRec(AnalogParamVo analogParamVo);
	
	public LinkedList<SensorVo> getSensor(List<DevVo> ldv);
	
	public LinkedList<SensorVo> getSwitchSensor(List<DevVo> ldv);
	
	public LinkedList<SensorVo> getAllSensor(List<DevVo> ldv);
	
	public List<AnalogQueryVo> getAnalogQry(int devid,String ip,String starttime, String endTime);
	
	public List<AnaloghisVo> getHistoryRcs(AnalogParamVo analogParamVo);
	
	public List<AnalogQueryVo> getCutDevAlarmRecs(List<CutAlarmVo> list);
	
	public int getTotalRec(AnalogParamVo analogParamVo);
}
