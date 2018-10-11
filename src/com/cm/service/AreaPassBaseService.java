package com.cm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import util.UtilMethod;

import com.cm.dao.AreaDailyDao;
import com.cm.dao.IWorkerInAreaRecDao;
import com.cm.entity.vo.AreaPass;
import com.cm.entity.vo.LongStringVo;

public class AreaPassBaseService {

	@Autowired
	protected AreaDailyDao adDao;
	@Autowired
	protected IWorkerInAreaRecDao wirDao;
	
	protected DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected final String startTime = " 00:00:00";
	protected final String endTime = " 23:59:59";
	protected String date = null;
	protected List<AreaPass> resultList;
	protected AreaPass wiT;//记录上一条AreaPass的变量
	protected AreaPass apT = null;//结果对象
	protected String wellduration;//待在区域的时长
	protected int cardId;
	protected int areaId;
	protected int rfId;
	
	/**
	 * 区域基础出入数据处理，计算人员出区域时刻及时长
	 * @param dailyRecs
	 * @throws ParseException
	 */
	public void dealWithData(List<AreaPass> dailyRecs) throws ParseException{
		if(dailyRecs != null && dailyRecs.size() > 0){
			for(int i = 0; i < dailyRecs.size(); i++){
				//areaID非0区域，wiar的status是2的为进入区域记录，1为出区域记录
				//areaID为0区域，是出入口，不计入区域范畴
				AreaPass wiar = dailyRecs.get(i);
				if(wiar.getWorkerId() == 0)
					wiar.setCard(wiar.getCardId()+"临时卡");
				else if(wiar.getCardId() > 0 && wiar.getRfcard_id() != null && wiar.getRfcard_id() == 0)
					wiar.setCard(wiar.getCardId()+"/离职");
				else 
					wiar.setCard(wiar.getCardId()+"");
				
				wiar.setStartTime(wiar.getStartTime().substring(0, 19));
				rfId = wiar.getCardId();
				
				//判断记录是否是出入区域的记录
				if(cardId == 0){
					setApt(wiar,dailyRecs,i);
				}else{
					//如果当前记录与入区域记录的员工ID和区域ID相同，则将临时AreaPass对象wiT设置为当前记录
					if(cardId == rfId && areaId == wiar.getAreaId()){
						status(wiar);
						wiT = wiar;
						if(UtilMethod.isLastRec(dailyRecs, i+1))
							dealWithLast();
					}else{
						//如果当前记录与入区域记录的员工ID或区域ID不相同，将区域出入记录添加到结果列表
						if(apT.getAreaId() != 0)
							listAddResult(wiar);
						//区域出入记录添加后设置当前记录所对应的变量
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
		//处理最后一条记录
		if(UtilMethod.isLastRec(dailyRecs, i+1))
			dealWithLast();
	}
	
	public void setVariable(AreaPass wiar){
		wiT = wiar;
		areaId = wiar.getAreaId();
		cardId = rfId;
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
			wiar.setEndTime("当日未出该区域");
			
	}
	
	public void dealWithLast() throws ParseException{
		if(wiT.getAreaId() != 0){
			if(wiT.getStatus() == 2){
				if(apT.getStatus() == 1){
					apT.setWellduration("-");
					apT.setEndTime("当日未出该区域");
					resultList.add(apT);
				}else if(apT.getStatus() == 2){
					getWellduration(apT.getStartTime(), getEndTime());
					apT.setWellduration(wellduration);
					apT.setEndTime("当日未出该区域");
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
					apT.setEndTime("当日未出该区域");
				apT.setWellduration("-");
				
			}else if(apT.getStatus() == 2){
				if(wiT.getCardId() == rfId){
					apT.setEndTime("未检测到出区域读卡记录");
					apT.setWellduration("-");
				}else{
					apT.setEndTime("当日未出该区域");
					getWellduration(apT.getStartTime(), getEndTime());
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
		resultList.add(apT);
	}
	
	public void getYesterday(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date date1 = c.getTime();
		date = sdf.format(date1);
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
		apT.setCard(wiT.getCard());
	}
	
	public String getStartTime(){
		return date.concat(startTime);
	}
	
	public String getEndTime(){
		return date.concat(endTime);
	}
	
	public void getWellduration(String starttime, String endtime) throws ParseException{
		LongStringVo lsv = UtilMethod.longToTimeFormat(starttime, endtime);
		wellduration = lsv.getTimCast();
	}
	
}
