package com.cm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.controller.ResultObj;
import com.cm.dao.CardReaderErrorQueryDao;
import com.cm.entity.vo.CommunicationVo;
import com.cm.entity.vo.NameTime;

@Service("cardReaderErrorQueryService")
public class CardReaderErrorQueryService implements CardReaderErrorQueryDao{

	@Autowired
	private ResultObj result;
	@Autowired
	private CardReaderErrorQueryDao cardReaderErrorQueryDao;
	
	public List<CommunicationVo> cardReaderErrorQuery(NameTime nameTime) throws ParseException{
		List<CommunicationVo> list = cardReaderErrorQueryDao.cardReaderErrorQuery(nameTime);
		for(CommunicationVo l:list){
			l.setTimes(getTimes(l.getStarttime(), l.getEndtime()));
			if(l.getEndtime()==null){
				l.setEndtime("至今");
			}
		}
		return list;
	}
	
	private String getTimes(String time1,String time2) throws ParseException{
		if(time1!=null&&time2!=null){
			return StaticUtilMethod.longToTimeFormat(time1, time2).getTimCast();
		}else if(time1!=null&&time2==null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time3 =format.format(new Date());
			return StaticUtilMethod.longToTimeFormat(time1, time3).getTimCast();
		}else{
			return null;
		}
		
	}
}
