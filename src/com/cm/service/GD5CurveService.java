package com.cm.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.controller.ResultObj;
import com.cm.dao.IGD5Dao;
import com.cm.entity.GD5;
import com.cm.entity.vo.AnalogParamVo;

@Service(value = "GD5Curve")
public class GD5CurveService implements SingleCurveInterface {

	@Autowired
	private ResultObj result;
	@Autowired
	private IGD5Dao theDao;
	
	private final String start = " 00:00:00";
	private final String end = " 23:59:59";
	
	
	/**
	 * 获取GD5数据
	 */
	public ResultObj getData(AnalogParamVo analogParamVo) {
		boolean isDetail = analogParamVo.getType() == 1 ? true : false;
		List<GD5> all = null;
		if(isDetail){
			String startTime = analogParamVo.getDay()+" "+ analogParamVo.getStartTime();
			String endTime = null;
			if(analogParamVo.getEndTime() != null){
				endTime = analogParamVo.getDay()+" "+ analogParamVo.getEndTime();
				all = theDao.getAll(startTime, endTime, analogParamVo.getIp(),analogParamVo.getDevid());
			}else{
				all = theDao.getAll(startTime, null, analogParamVo.getIp(),analogParamVo.getDevid());
			}
			result.put("data", all);
		}else{
			String startTime = analogParamVo.getDay()+ start;
			String endTime = analogParamVo.getDay()+ end;
			List<GD5> allList = theDao.getAll(startTime, endTime, analogParamVo.getIp(),analogParamVo.getDevid());
			if(UtilMethod.notEmptyList(allList)){
				LinkedHashMap<String,GD5> map = new LinkedHashMap<String,GD5>();
				for(GD5 data : allList){
					String key = data.getResponsetime().substring(0, 16);
					GD5 gd5 = map.get(key);
					if(gd5 == null){
						map.put(key,data);
					}
				}
				all = new ArrayList<GD5>();
				for(Map.Entry<String, GD5> entry : map.entrySet()){
					all.add(entry.getValue());
				}
			}else{
				all = new ArrayList<GD5>();
			}
		}
		result.put("data", all);
		result.setStatus(0, "ok");
		return result;
	}
}
