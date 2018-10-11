package com.cm.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.AnalogHistoryDao;
import com.cm.entity.vo.AnalogCurveVo;
import com.cm.entity.vo.AnalogParamVo;
import com.cm.entity.vo.AnalogQueryVo;
import com.cm.entity.vo.AnaloghisVo;
import com.cm.entity.vo.CutAlarmVo;
import com.cm.entity.vo.DevVo;
import com.cm.entity.vo.SensorVo;

@Service("AnalogHistoryService")
public class AnalogHistoryService {
	
	@Autowired
	AnalogHistoryDao analogHistoryDao;

	public List<AnaloghisVo> getHistory(AnalogParamVo analogParamVo){
		try {
			return  analogHistoryDao.getHistory(analogParamVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AnalogCurveVo> getCurveHistory(AnalogParamVo analogParamVo){
		try {
			return  analogHistoryDao.getCurveHistory(analogParamVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AnaloghisVo> getRealTimeRec(AnalogParamVo analogParamVo){
		try {
			return  analogHistoryDao.getRealTimeRec(analogParamVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AnaloghisVo> getFeedErrorRealTimeRec(AnalogParamVo analogParamVo){
		try {
			return  analogHistoryDao.getFeedErrorRealTimeRec(analogParamVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<SensorVo> getSensor(List<DevVo> ldv){
		try {
			return  analogHistoryDao.getSensor(ldv);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AnalogQueryVo> getAnalogQry(int devid,String ip,String starttime, String endTime){
		try {
			return  analogHistoryDao.getAnalogQry(devid,ip,starttime, endTime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getAlarmEndTime(String startTime, int devid, String ip) {
		try {
			return  analogHistoryDao.getAlarmEndTime(startTime, devid, ip);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AnaloghisVo> getHistoryRcs(AnalogParamVo analogParamVo){
		try {
			return  analogHistoryDao.getHistoryRcs(analogParamVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<SensorVo> getSwitchSensor(List<DevVo> ldv){
		try {
			return analogHistoryDao.getSwitchSensor(ldv);
		} catch (Exception e) {
			e.printStackTrace();
			 return null;
		}
	}
	
	public LinkedList<SensorVo> getAllSensor(List<DevVo> ldv){
		try {
			return analogHistoryDao.getAllSensor(ldv);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AnalogQueryVo> getCutDevAlarmRecs(List<CutAlarmVo> list){
		try {
			return  analogHistoryDao.getCutDevAlarmRecs(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getTotalRec(AnalogParamVo analogParamVo){
		try {
			return  analogHistoryDao.getTotalRec(analogParamVo);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
