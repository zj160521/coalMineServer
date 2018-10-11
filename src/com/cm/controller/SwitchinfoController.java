package com.cm.controller;

import com.alibaba.fastjson.JSON;
import com.cm.entity.Area;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.SSParaVo;
import com.cm.entity.vo.SSWarning;
import com.cm.entity.vo.SwitchRecs;
import com.cm.security.LoginManage;
import com.cm.service.AreaService;
import com.cm.service.StaticService;
import com.cm.service.SwitchinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.UtilMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 开关量报警、断电、馈电异常历史信息查询
 * @author Administrator
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/switchinfo")
public class SwitchinfoController {
	

	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private SwitchinfoService service;
	
	private Map<Integer, Area> map = new HashMap<Integer, Area>();
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private StaticService staticService;
	
	@RequestMapping(value="/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getAnalogWarningInfo(
			@RequestBody SSParaVo para,
			HttpServletRequest request){
		if(!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
			
//		SSParaVo para = new SSParaVo();
//		para.setEndtime("2018-04-26 00:00:00");
//		para.setStarttime("2018-04-20 00:00:00");
		
		try {
			if(timeCheck(para)){
				//选择查询报警类型
				changeAtype(para);
				
				//报警基础数据获取
				List<SSWarning> warnimgRec = service.getWarnimgRec(para);
				
				if(null == warnimgRec)
					warnimgRec = new ArrayList<SSWarning>();
				area();
				long totalCast = 0;//累计时长变量
				for(SSWarning ssw : warnimgRec){
					//获取开始和结束状态
                    Map jsonMap = (Map) JSON.parse(ssw.getK());
//					String[]  k = ssw.getK().split("\"");
					String startStatus = (String) jsonMap.get(ssw.getStartValue());
					String endStatus = (String) jsonMap.get(ssw.getEndValue());
					
					//获取持续时间
                    if (null == ssw.getEndtime()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String endtime = sdf.format(new Date());
                        ssw.setEndtime(endtime);
                    }
					LongStringVo longToTimeFormat = UtilMethod.longToTimeFormat(ssw.getStarttime(), ssw.getEndtime());
					
					ssw.setStartStatus(startStatus);
					ssw.setEndStatus(endStatus);
					ssw.setWellduration(longToTimeFormat.getTimCast());
					
					if(ssw.getAreaId() > 0)
						ssw.setAreaName(map.get(ssw.getAreaId()).getAreaname());
					
					totalCast += longToTimeFormat.getTime();
				}
				String countTimeCast = UtilMethod.countTimeCast(totalCast);//累计时长转换
				
				result.put("totalnum", warnimgRec.size());
				result.put("totalTime", countTimeCast);
				result.put("data", warnimgRec);
				
			}else{
				return result.setStatus(-2, "时间输入不正确，请重新输入");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	public void changeAtype(SSParaVo para){
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		if(para.getAtype() == 1){
			list.clear();
			list.add(54);
			list.add(56);
		}else{
			list.add(50);
			list.add(51);
			list.add(52);
			list.add(61);
		}
		
		para.setList(list);
	}
	
	public Map<Integer, Area> area(){
		List<Area> allArea = areaService.getAllArea();
		
		for(Area area : allArea){
			map.put(area.getId(), area);
		}
		return map;
	}
	
	public boolean timeCheck(SSParaVo para){
		boolean start = valiDateTimeWithLongFormat(para.getStarttime());
		boolean end = valiDateTimeWithLongFormat(para.getEndtime());
		
		if(start == true && end == true)
			return true;
		else 
			return false;
	}
	
	public void dealWithEndId(SSWarning ssw){
		if(ssw.getEndId() == 0) {
			String now = UtilMethod.getNow();
			ssw.setEndtime(now);
			ssw.setEndValue(ssw.getStartValue());
		}
	}
	
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
				+ "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m-1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}
	
	@RequestMapping(value="/getStatusChangeRecs",method=RequestMethod.POST)
	@ResponseBody
	public Object getStatusChangeRecs(
			@RequestBody SSParaVo para,
			HttpServletRequest request){
		try {
			List<Integer> ids = staticService.getByPid(25);
			para.setList(ids);
			
			List<SwitchRecs> statusChangeRecs = service.getStatusChangeRecs(para);
			
			if(statusChangeRecs != null && statusChangeRecs.size() > 0){
				for(SwitchRecs sr : statusChangeRecs){
					
				}
			}
			return result.setStatus(0, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "exception");
		}
		
	}
}
