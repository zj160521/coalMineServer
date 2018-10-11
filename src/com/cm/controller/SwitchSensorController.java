package com.cm.controller;

/**
 * @project : coalMineServer
 * @author:kz
 */

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.DevLink;
import com.cm.entity.Static;
import com.cm.entity.Substation;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.AnalogParamVo;
import com.cm.entity.vo.AnaloghisVo;
import com.cm.entity.vo.DevlinkSensor;
import com.cm.security.LoginManage;
import com.cm.service.*;
import com.cm.service.kafka.ConfigSyncThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Scope
@Controller
@RequestMapping("/switchsensor")
public class SwitchSensorController {

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private ResultObj result;

	@Autowired
	private SwitchSensorService sensorService;

	@Autowired
	private SubstationService stationService;

	@Autowired
	private StaticService staticService;
	
	@Autowired
	private DevLinkService devService;
	
	@Autowired
	private AnalogHistoryService analogService;

	@Autowired
	private DrainageService drainageService;
	
	private static final String RADIO_ACTION = "broadcast";
	private static final String CUTOUT_ACTION = "cutout";
	private static final String CARDREADER_ACTION = "call";

	/**
	 * 查询所有开关量传感器
	 * 
	 * @param typeid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSwitchSenor(Integer typeid, HttpServletRequest request) {
		/*if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}*/
		List<SwitchSensor> list = sensorService.getAll(typeid);
		if (null == list || list.isEmpty() || list.size() < 1) {
			result.put("data", list);
			return result.setStatus(0, "no data");
		} else {
			result.put("data", list);
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 添加或者更新
	 * 
	 * @param sensor
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addorUpdate(@RequestBody SwitchSensor sensor, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		int id = 0;
		try {
			sensor.setControl(1);
			if(sensor.getId()>0){
			    if(sensor.getSensorId()==0||sensor.getSensorId()==255){
			        return result.setStatus(-4,"你输入的设备地址有误");
                }
                SwitchSensor sensor2 = sensorService.getById(sensor.getId());
                if (sensor2.getSensorId() != sensor.getSensorId()) {
                    String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                    if (null!=isuse) {
                        result.put("isuse", 1);
                        return result.setStatus(0, isuse);
                    } else {
                        result.put("isuse", 0);
                    }
                }
                if (null == sensor.getPosition()) {
                    return result.setStatus(0, "位置信息不能为空");
                }
                Static sta = staticService.getByPosition(sensor.getPosition());
                if (null == sta) {
                    sta = staticService.addPosition(sensor.getPosition());
                    sensor.setSensor_position(sta.getId());
                } else {
                    sensor.setSensor_position(sta.getId());
                }
                if(sensor.getFeedId()>0){
                    if(sensor2.getFeedId()==0){
                        SwitchSensor sensor3 = new SwitchSensor();
                        sensor3.setId(sensor.getFeedId());
                        sensor3.setFeedId(id);
                        sensorService.updateFeedId(sensor3);
                    }
                    if(sensor2.getFeedId()>0&&sensor2.getFeedId()!=sensor.getFeedId()){
                        SwitchSensor sensor5 = new SwitchSensor();
                        sensor5.setId(sensor2.getFeedId());
                        sensor5.setFeedId(0);
                        sensorService.updateFeedId(sensor5);
                        SwitchSensor sensor4 = new SwitchSensor();
                        sensor4.setId(sensor.getFeedId());
                        sensor4.setFeedId(sensor.getId());
                        sensorService.updateFeedId(sensor4);
                    }
                }
                addDevLink(sensor);
                String remark = JSONObject.toJSONString(sensor);
                String operation2 = "修改开关量设备" + sensor.getAlais();
                loginManage.addLog(request, remark, operation2, 15140);
                sensorService.update(sensor);
                sensorService.updateConfigById(1, sensor.getId());
                if(sensor.getIsDrainage()==1){
                    drainageService.deleteDrainageSensor(sensor.getId());
                    drainageService.addDrainageSensor(sensor.getMenu(), sensor.getId());
                }
                result.put("success", 0);
            } else {
                String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                if (null!=isuse) {
                    result.put("isuse", 1);
                    return result.setStatus(0, isuse);
                } else {
                    result.put("isuse", 0);
                }
                Integer count = stationService.sensorCount(sensor.getStation());
                result.put("count", count);
                if (null != count && count >= 255) {
                    return result.setStatus(0, "该分站设备已达到上限");
                }
                if (null == sensor.getPosition()) {
                    return result.setStatus(0, "位置信息不能为空");
                }
                Static sta = staticService.getByPosition(sensor.getPosition());
                if (null == sta) {
                    sta = staticService.addPosition(sensor.getPosition());
                    sensor.setSensor_position(sta.getId());
                } else {
                    sensor.setSensor_position(sta.getId());
                }
                //添加别名
                Substation sub = stationService.getSubbyid(sensor.getStation());
                String ipaddr = sub.getIpaddr();
                int i = ipaddr.lastIndexOf(".");
                String string = ipaddr.substring(i+1);
                String alais = "G"+string+"K"+sensor.getSensorId();
                sensor.setAlais(alais);
                sensorService.addSwitchSensor(sensor);
                sensorService.updateConfigById(1, sensor.getId());
                String uid = "SW"+sensor.getId()+alais;
                sensor.setUid(uid);
                sensorService.updateUid(sensor.getId(), uid);
                addDevLink(sensor);
                if(sensor.getIsDrainage()==1){
                    drainageService.addDrainageSensor(sensor.getMenu(), sensor.getId());
                }
                if(sensor.getFeedId()>0){
                    SwitchSensor sensor3 = new SwitchSensor();
                    sensor3.setId(sensor.getFeedId());
                    sensor3.setFeedId(sensor.getId());
                    sensorService.updateFeedId(sensor3);
                }
                String remark = JSONObject.toJSONString(sensor);
                String operation2 = "增加开关量设备" + sensor.getAlais();
                loginManage.addLog(request, remark, operation2, 15140);
                result.put("success", 0);
                result.put("id", sensor.getId());
            }
			String value = "{\"id\":" + sensor.getId() + ",\"ip\":\"" + sensor.getIpaddr()
			+ "\",\"devid\":" + sensor.getSensorId() + ",\"type\":" + sensor.getSensor_type()
			+ ",\"typename\":\"" + sensor.getType() + "\"}";
			ConfigSyncThread.SendMessage(value);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteSwitchSensor(@PathVariable int id, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		try {
			SwitchSensor switchSensor = sensorService.getById(id);
			String value = "{\"id\":" + switchSensor.getId() + ",\"ip\":\"" + switchSensor.getIpaddr() + "\",\"devid\":"
					+ switchSensor.getSensorId() + ",\"type\":" + switchSensor.getSensor_type() + ",\"typename\":\""
					+ switchSensor.getType() + "\",\"op\":\"del\"}";
			ConfigSyncThread.SendMessage(value);
			SwitchSensor sensor2 = sensorService.getById(id);
			DevLink link = new DevLink();
			link.setLogic_uid(sensor2.getUid());
			sensorService.delete(id);
			String remark = JSONObject.toJSONString(sensor2);
			String operation2 = "删除开关量设备" + sensor2.getAlais();
			loginManage.addLog(request, remark, operation2, 15141);
            devService.deleteBasisDevlink(link);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/data",method=RequestMethod.POST)
	@ResponseBody
	public Object getAlldata(@RequestBody AnalogParamVo analogParamVo,HttpServletRequest request ){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long startTimeLong = System.currentTimeMillis() - analogParamVo.getMinutes()*60000;
			String startTime = df.format(new Date(startTimeLong));
			analogParamVo.setStartTime(startTime);
			List<AnaloghisVo> list = analogService.getHistory(analogParamVo);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object getById(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			SwitchSensor sensor = sensorService.getById(id);
			result.put("data", sensor);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	public void addDevLink(SwitchSensor sensor){
    	DevLink link1 = new DevLink();
    	link1.setLogic_uid(sensor.getUid());
    	devService.deleteBasisDevlink(link1);
    	List<DevlinkSensor> devlinkSensors = sensor.getSensorList();
    	if(devlinkSensors!=null&&devlinkSensors.size()>0){
    		List<DevLink> links = new ArrayList<DevLink>();
        	for(DevlinkSensor d:devlinkSensors){
        		DevLink link = new DevLink();
        		link.setLogic_uid(sensor.getUid());
        		link.setLgcDevId(sensor.getSensorId());
        		link.setLogic_type(sensor.getSensor_type());
        		link.setLogic_ip(sensor.getIpaddr());
        		if(sensor.getAlarm_status()==1){
        			link.setLogic_exps(sensor.getUid()+"=="+sensor.getAlarm_status());
        			link.setRepower_exps(sensor.getUid()+"==0");
        		}
        		if(sensor.getAlarm_status()==0){
        			link.setLogic_exps(sensor.getUid()+"=="+sensor.getAlarm_status());
        			link.setRepower_exps(sensor.getUid()+"==1");
        		}
        		link.setAction_uid(d.getUid());
        		link.setActDevId(d.getSensorId());
        		link.setAction_ip(d.getIp());
        		link.setAction_type(d.getSensor_type());
        		if(d.getSensor_type()==56||d.getSensor_type()==71||d.getSensor_type()==53){
        			link.setAction(CUTOUT_ACTION);
        			link.setParam(""+d.getAction());
        		}
        		if(d.getSensor_type()==65){
        			link.setAction(RADIO_ACTION);
        			link.setParam(""+d.getAction());
        		}
        		if(d.getSensor_type()==64){
        			link.setAction(CARDREADER_ACTION);
        			link.setParam(""+d.getAction());
        		}
        		links.add(link);
        	}
        	devService.addBasisDevLink(links);
    	}
    	
    }
}
