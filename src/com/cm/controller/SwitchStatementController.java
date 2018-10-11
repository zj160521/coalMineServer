package com.cm.controller;

import com.alibaba.fastjson.JSON;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.SSParaVo;
import com.cm.entity.vo.SSWarning;
import com.cm.entity.vo.SSWarningVo;
import com.cm.security.LoginManage;
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
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 开关量日班报表
 * @author Administrator
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/switchStatement")
public class SwitchStatementController {
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private SwitchinfoService service;
	
	
	/**
	 * 开关量报表
	 * @param para
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getallAnaloginfo(@RequestBody SSParaVo para,HttpServletRequest request){
		if(!loginManage.isUserLogin(request))
            return result.setStatus(-1, "no login");
		try {
            if(timeCheck(para)) {
                if(para.getId() <= 0 && para.getSensor_type() <= 0){
                    //选择查询报警类型
                    changeAtype(para);
                }
                if (para.getAtype() == 3){
                    para.setAtype(2);
                }
                HashMap<String, List<SSWarning>> warningMap = new HashMap<String, List<SSWarning>>();
                HashMap<String, Long> timeMap = new HashMap<String, Long>();
                //报警基础数据获取
                List<SSWarning> warnimgRec = service.getWarnimgRec(para);
                for (SSWarning warning : warnimgRec) {
                    //把相同的报警对象放进map里面
                    List<SSWarning> list = warningMap.get(warning.getIp() + ":" + warning.getSensorId());
                    if (null == list) {
                        list = new ArrayList<SSWarning>();
                    }
                    Map jsonMap = (Map) JSON.parse(warning.getK());
                    //获取报警状态
                    String startStatus = (String) jsonMap.get(warning.getStartValue());
                    //获取设备状态
                    String endStatus = (String) jsonMap.get(warning.getEndValue());
                    warning.setStartStatus(startStatus);
                    warning.setEndStatus(endStatus);
                    list.add(warning);
                    warningMap.put(warning.getIp() + ":" + warning.getSensorId(), list);
                    //把相同报警对象的时间加总并放到map里面
                    Long time = timeMap.get(warning.getIp() + ":" + warning.getSensorId());
                    if (null == warning.getEndtime()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String nowtime = sdf.format(new Date());
                        warning.setEndtime(nowtime);
                    }
                    LongStringVo longTime = UtilMethod.longToTimeFormat(warning.getStarttime(), warning.getEndtime());
                    warning.setWellduration(longTime.getTimCast());
                    if (null != time) {
                        time += longTime.getTime();
                        timeMap.put(warning.getIp() + ":" + warning.getSensorId(), time);
                    } else {
                        timeMap.put(warning.getIp() + ":" + warning.getSensorId(), longTime.getTime());
                    }

                }
                List<SSWarningVo> list = new ArrayList<SSWarningVo>();
                Map<Integer, String> map = debugMap();
                Set<Entry<String, List<SSWarning>>> entrySet = warningMap.entrySet();
                for (Entry<String, List<SSWarning>> entry : entrySet) {
                    SSWarningVo warningVo = new SSWarningVo();
                    List<SSWarning> list2 = entry.getValue();
                    warningVo.setAlais(list2.get(0).getAlais());
                    warningVo.setPosition(list2.get(0).getPosition());
                    warningVo.setSensor_position(list2.get(0).getSensor_position());
                    warningVo.setTotalnum(list2.size());
                    Long totalTime = timeMap.get(list2.get(0).getIp() + ":" + list2.get(0).getSensorId());
                    String timeCast = UtilMethod.countTimeCast(totalTime);
                    warningVo.setCountTime(timeCast);
                    warningVo.setMeasureId(list2.get(0).getMeasureId());
                    warningVo.setMeasure(list2.get(0).getMeasure());
                    warningVo.setMeasureTime(list2.get(0).getMeasuretime());
                    warningVo.setSensor_type(list2.get(0).getSensor_type());
                    warningVo.setType(list2.get(0).getType());
                    warningVo.setAtype(list2.get(0).getStatus());
                    if (null == list2.get(0).getPower_scope()){
                        warningVo.setPowerArea("未配置断电范围");
                    } else {
                        warningVo.setPowerArea(list2.get(0).getPower_scope());
                    }
                    warningVo.setSensor_id(list2.get(0).getSensor_id());
                    for (SSWarning ssWarning : list2) {
                        if(null == ssWarning.getMeasure() || "".equals(ssWarning.getMeasure())){
                            ssWarning.setMeasure("暂未处理");
                            ssWarning.setMeasuretime("-");
                        }
                        if(null == ssWarning.getPower_scope()){
                            ssWarning.setPower_scope("未配置断电范围");
                        }
                    }
                    if(list2.size() < 2){
                        SSWarning ssWarning = list2.get(0);
                        List<String> first = new ArrayList<>();
                        first.add(ssWarning.getWellduration());
                        first.add(ssWarning.getStarttime() + "~" + ssWarning.getEndtime());
                        if(null == ssWarning.getMeasure() || "暂未处理".equals(ssWarning.getMeasure())){
                            first.add("暂未处理");
                        } else {
                            first.add(ssWarning.getMeasure() + " " + ssWarning.getMeasuretime());
                        }
                        first.add(map.get(ssWarning.getDebug()));
                        warningVo.setFirst(first);
                    } else {
                        SSWarning ssWarning = list2.get(0);
                        List<String> first = new ArrayList<>();
                        first.add(ssWarning.getWellduration());
                        first.add(ssWarning.getStarttime() + "~" + ssWarning.getEndtime());
                        if(null == ssWarning.getMeasure() || "暂未处理".equals(ssWarning.getMeasure())){
                            first.add("暂未处理");
                        } else {
                            first.add(ssWarning.getMeasure() + " " + ssWarning.getMeasuretime());
                        }
                        first.add(map.get(ssWarning.getDebug()));
                        warningVo.setFirst(first);
                        SSWarning ssWarning1 = list2.get(1);
                        List<String> second = new ArrayList<>();
                        second.add(ssWarning1.getWellduration());
                        second.add(ssWarning1.getStarttime() + "~" + ssWarning1.getEndtime());
                        if(null == ssWarning1.getMeasure() || "暂未处理".equals(ssWarning1.getMeasure())){
                            second.add("暂未处理");
                        } else {
                            second.add(ssWarning1.getMeasure() + " " + ssWarning1.getMeasuretime());
                        }
                        second.add(map.get(ssWarning1.getDebug()));
                        warningVo.setSecond(second);
                    }
                    warningVo.setList(list2);
                    list.add(warningVo);
                }
                result.put("data", list);
            } else {
                return result.setStatus(-2, "时间输入不正确，请重新输入");
            }

			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
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

	public Map<Integer,String> debugMap(){
	    HashMap<Integer, String> map = new HashMap<>();
        map.put(0,"正常");
        map.put(1,"欠压");
        map.put(2,"故障");
        map.put(3,"标校");
        map.put(4,"开机");
	    return map;
    }

    public boolean timeCheck(SSParaVo para){
	    if(null == para.getStarttime()){
	        return false;
        }
	    if(null != para.getStarttime()){
            boolean start = valiDateTimeWithLongFormat(para.getStarttime());
            if(start)
                return true;
            else
                return false;
        }
        if(null != para.getStarttime() && null != para.getEndtime()){
            boolean start = valiDateTimeWithLongFormat(para.getStarttime());
            boolean end = valiDateTimeWithLongFormat(para.getEndtime());
            if(start && end)
                return true;
            else
                return false;
        }
        return false;
    }

}
