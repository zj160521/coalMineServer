package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import util.StaticUtilMethod;

import com.cm.entity.vo.AreaPass;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.Searchform;

@Service("AreaTodayService")
public class AreaTodayService extends AreaPassBaseService{
	
	private String date = null;
	private List<AreaPass> resultList;
	
	/**
	 * 区域基础出入数据处理，计算人员出区域时刻及时长
	 * @param searchform
	 * @return
	 * @throws ParseException
	 */
	public List<AreaPass> areaPassRecords(Searchform searchform) throws ParseException{
		//设置查询时间段
		date = searchform.getStarttime();
		String stime = getStartTime();
		String etime = getEndTime();
		searchform.setStarttime(stime);
		searchform.setEndtime(etime);
		searchform.setArea_ids(null);
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
		//获取区域进出时间及时长
		dealWithData(dailyRecs);
		
		return resultList;
	}
	
	public String getStartTime(){
		return date.concat(startTime);
	}
	
	public String getEndTime(){
		return date.concat(endTime);
	}
	
	public void dealWithData(List<AreaPass> dailyRecs) throws ParseException{
		if(dailyRecs != null && dailyRecs.size() > 0){
			for(int i = 0; i < dailyRecs.size(); i++){
				AreaPass wiar = dailyRecs.get(i);
				
				if(wiar.getWorkerId() == 0)
					wiar.setCard(wiar.getCardId()+"/临时卡");
				else if(wiar.getCardId() > 0 && wiar.getRfcard_id() != null && wiar.getRfcard_id() == 0)
					wiar.setCard(wiar.getCardId()+"/离职");
				else 
					wiar.setCard(wiar.getCardId()+"");
				
				wiar.setStartTime(wiar.getStartTime().substring(0, 19));
				rfId = wiar.getCardId();
				
				if(cardId == 0){
					setApt(wiar,dailyRecs,i);
				}else{
					if(cardId == rfId && areaId == wiar.getAreaId()){
						status(wiar);
						wiT = wiar;
						if(StaticUtilMethod.isLastRec(dailyRecs, i+1))
							dealWithLast();
					}else{
						listAddResult(wiar);
						setApt(wiar,dailyRecs,i);
					}
				}
			}
		}
	}
	
	public void setApt(AreaPass wiar,List<AreaPass> dailyRecs,int i) throws ParseException{
		status(wiar);
		setVariable(wiar);
		setNewApT();
		if(StaticUtilMethod.isLastRec(dailyRecs, i+1))
			dealWithLast();
	}
	
	public void status(AreaPass wiar){
		if(wiar.getStatus() == 1 && wiT == null){
			wiar.setEndTime(wiar.getStartTime());
			wiar.setStartTime("昨日未出该区域");
		}else if(wiar.getStatus() == 1 && wiT.getCardId() == rfId){
			wiar.setEndTime(wiar.getStartTime());
			wiar.setStartTime("未检测到进入区域读卡记录");
		}else if(wiar.getStatus() == 1 && wiT.getCardId() != rfId){
			wiar.setEndTime(wiar.getStartTime());
			wiar.setStartTime("昨日未出该区域");
		}else if(wiar.getStatus() == 2)
			wiar.setEndTime("至今");
			
	}
	
	public void dealWithLast() throws ParseException{
		if(wiT.getAreaId() != 0){
			if(wiT.getStatus() == 2){
				if(apT.getStatus() == 1){
					apT.setWellduration("-");
					apT.setEndTime("至今");
					resultList.add(apT);
				}else if(apT.getStatus() == 2){
					getWellduration(apT.getStartTime(), getNow());
					apT.setWellduration(wellduration);
					apT.setEndTime("至今");
					resultList.add(apT);
				}
				
			}else if(wiT.getStatus() == 1){
				if(apT.getStatus() == 1){
					if("昨日未出该区域".equals(apT.getStartTime()))
						getWellduration(getStartTime(), wiT.getEndTime());
					else if("未检测到进入区域读卡记录".equals(apT.getStartTime()))
						wellduration = "-";
					apT.setWellduration(wellduration);
					apT.setEndTime(wiT.getStartTime());
					resultList.add(apT);
				}else if(apT.getStatus() == 2){
					apT.setEndTime(wiT.getStartTime());
					getWellduration(apT.getStartTime(), apT.getEndTime());
					apT.setWellduration(wellduration);
					resultList.add(apT);
				}
			}
		}
	}

	public void listAddResult(AreaPass wiar) throws ParseException{
		if(wiT.getStatus() == 2){
			if(apT.getStatus() == 1){
				if(wiT.getCardId() == rfId){
					apT.setEndTime("未检测到出区域读卡记录");
				}else
					apT.setEndTime("至今");
				apT.setWellduration("-");
				
			}else if(apT.getStatus() == 2){
				if(wiT.getCardId() == rfId){
					apT.setEndTime("未检测到出区域读卡记录");
					apT.setWellduration("-");
				}else{
					getWellduration(apT.getStartTime(), getNow());
					apT.setWellduration(wellduration);
				}	
			}
			
		}else if(wiT.getStatus() == 1){
			if(wiT.getStartTime().equals("未检测到进入区域读卡记录") || wiT.getStartTime().equals("昨日未出该区域") 
					&& wiT.getEndTime() != null)
				apT.setEndTime(wiT.getEndTime());
			else
				apT.setEndTime(wiT.getStartTime());
			
			if(apT.getStatus() == 1){
				if(apT.getStartTime().equals("未检测到进入区域读卡记录"))
					apT.setWellduration("-");
				else{
					apT.setStartTime("-");
					getWellduration(getStartTime(), apT.getEndTime());
					apT.setWellduration(wellduration);
				}
			}else if(apT.getStatus() == 2){
				getWellduration(apT.getStartTime(), apT.getEndTime());
				apT.setWellduration(wellduration);
			}
		}
		if(apT.getAreaId() != 0)
			resultList.add(apT);
	}
	
	public String getNow(){
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		return df.format(date);
	}
	
	public void setVariable(AreaPass wiar){
		wiT = wiar;
		areaId = wiar.getAreaId();
		cardId = rfId;
	}
	
	public void setNewApT(){
		apT = new AreaPass();
		apT.setAreaId(wiT.getAreaId());
		apT.setAreaName(wiT.getAreaName());
		apT.setCardId(wiT.getCardId());
		apT.setWorkerId(wiT.getWorkerId());
		apT.setName(wiT.getName());
		apT.setDepartId(wiT.getDepartId());
		apT.setDepartName(wiT.getDepartName());
		apT.setDutyId(wiT.getDutyId());
		apT.setDutyName(wiT.getDutyName());
		apT.setSpecial(wiT.getSpecial());
		apT.setWorkday(date);
		apT.setWorkplace(wiT.getWorkplace());
		apT.setWorkplaceId(wiT.getWorkplaceId());
		apT.setStartTime(wiT.getStartTime());
		if(wiT.getEndTime() != null)
			apT.setEndTime(wiT.getEndTime());
		apT.setWorktypeId(wiT.getWorktypeId());
		apT.setWorktypeName(wiT.getWorktypeName());
		apT.setDefaultAllow(wiT.getDefaultAllow());
		apT.setEmphasis(wiT.getEmphasis());
		apT.setStatus(wiT.getStatus());
		apT.setDefaultAllow(wiT.getDefaultAllow());
		apT.setCard(wiT.getCard());
	}
	
	public void getWellduration(String starttime, String endtime) throws ParseException{
		LongStringVo lsv = StaticUtilMethod.longToTimeFormat(starttime, endtime);
		wellduration = lsv.getTimCast();
	}
}

