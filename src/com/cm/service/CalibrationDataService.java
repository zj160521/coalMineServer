package com.cm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.CalibrationDataDao;
import com.cm.entity.CalibrationData;
import com.cm.entity.vo.NameTime;


@Service("calibrationDataService")
public class CalibrationDataService {
	
	@Autowired
	private CalibrationDataDao dao;
	
	public List<CalibrationData> getall() throws ParseException{
		List<CalibrationData> list = dao.getallnull();
		for(CalibrationData l:list){
			String tablename = "t_coalMine_"+getDay(l.getStarttime());
			CalibrationData data =dao.getvalue(tablename, l);
			l.setMax_value(data.getMax_value());
			l.setMin_value(data.getMin_value());
		}
		return list;
	}
	
	
	public void updatedata(List<CalibrationData> list){
		for(CalibrationData l:list){
			dao.updatedata(l);
		}
	}
	
	
	public List<CalibrationData> getpagedata(NameTime nameTime){
		List<CalibrationData> list = dao.getpagedata(nameTime);
		for(CalibrationData l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	public int getalldata(NameTime nameTime){
		return dao.getalldata(nameTime);
	}
	
	private String getDay(String starttime) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd");
		Date date = 	format.parse(starttime);
		return format1.format(date);
	}
	
}
