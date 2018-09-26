package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cm.entity.vo.AreaPass;
import com.cm.entity.vo.Searchform;

@Service("AreaPassService")
public class TimerAreaPassService extends AreaPassBaseService{
	/**
	 * 计算昨日区域出入情况
	 * @throws ParseException
	 */
	
	@Scheduled(cron = "30 1 0 * * ?")
	public void areaPassRecords() throws ParseException{
		//获取昨日日期
		getYesterday();
		//设置查询时间段
		String stime = getStartTime();
		String etime = getEndTime();
		Searchform searchform = new Searchform();
		searchform.setStarttime(stime);
		searchform.setEndtime(etime);
		//获取基础数据
		List<AreaPass> dailyRecs = wirDao.getDailyRecs(searchform);
		//初始化变量
		wiT = null;
		wellduration = null;
		areaId = -2;
		rfId = 0;
		cardId = 0;
		apT = null;
		resultList  = new ArrayList<AreaPass>();
		//获取区域出入时间及时长
		dealWithData(dailyRecs);
		//插入数据库
		if(resultList.size() > 0)
			adDao.addRecs(resultList);
	}
	
}

