package com.cm.controller;

import com.cm.entity.SensorLog;
import com.cm.entity.vo.PageBean;
import com.cm.security.LoginManage;
import com.cm.service.SensorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/sensorlog")
public class SensorLogController {

	@Autowired
	private SensorLogService sensorLogService;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	/**
	 * 根据传感器id查询相关的操作日志
	 * @param log
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/id",method=RequestMethod.POST)
	@ResponseBody
	public Object getById(@RequestBody SensorLog log,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			PageBean<SensorLog> pageBean = sensorLogService.getByPage(log.getCurrentPage(), log.getPageSize(), log.getUid());
			List<SensorLog> list = pageBean.getList();
			if(!list.isEmpty()){
                for (SensorLog sensorLog : list) {
                    String date = formatDate(sensorLog.getTime());
                    sensorLog.setTime(date);
                }
            }
			pageBean.setList(list);
			result.put("data", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 根据传感器id查询相关的最近的一条操作日志
	 * @param log
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/recent",method=RequestMethod.POST)
	@ResponseBody
	public Object getOrderByTime(@RequestBody SensorLog log, HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			SensorLog sensorLog = sensorLogService.getOrderByTime(log.getUid());
			if(null == sensorLog){
				return result.setStatus(-2,"暂时没有数据");
			}
			result.put("data", sensorLog);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	public String formatDate(String time){
		Timestamp timestamp = Timestamp.valueOf(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(timestamp);
		return format;
	}
}
