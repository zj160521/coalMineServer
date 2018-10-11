package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.*;
import com.cm.entity.vo.StationSensorVo;
import com.cm.security.LoginManage;
import com.cm.service.*;
import com.cm.service.kafka.ConfigSyncThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/station")
public class SubstationController {

	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private SubstationService substationService;

	@Autowired
	private CardrederService cardrederService;

	@Autowired
	private BaseinfoService baseinfoService;

	@Autowired
	private SubstationService stationService;

	@Autowired
	private SwitchSensorService switchSensorService;

	@Autowired
	private StaticService staticService;
	
	@Autowired
	private EquipmentsService equipmentsService;

	/***
	 * 查询所有分站
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request) {
		/*if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}*/
		try {
			List<Substation> list = substationService.getAll();
			if (null!=list && !list.isEmpty()){
                for (Substation substation : list) {
                    String send_msg_time = substation.getSend_msg_time();
                    if (null != send_msg_time && !send_msg_time.equals("")){
                        String[] split = send_msg_time.split("-");
                        substation.setSend_msg_time1(Integer.valueOf(split[0]));
                        substation.setSend_msg_time2(Integer.valueOf(split[1]));
                        substation.setSend_msg_time3(Integer.valueOf(split[2]));
                    }

                }
            }
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 添加或者更新分站
	 * 
	 * @param station
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addup(@RequestBody Substation station, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		try {
			if (null != station.getId() && station.getId() > 0) {
				if (null == station.getPosition()) {
					result.clean();
					return result.setStatus(-2, "位置信息不能为空");
				}
				Static sta = staticService.getByPosition(station.getPosition());
				if (null == sta) {
					sta = staticService.addPosition(station.getPosition());
					station.setPositionId(sta.getId());
				} else {
					station.setPositionId(sta.getId());
				}
                int send_msg_time1 = station.getSend_msg_time1();
                int send_msg_time2 = station.getSend_msg_time2();
                int send_msg_time3 = station.getSend_msg_time3();
                String send_msg_time = send_msg_time1 + "-" + send_msg_time2 + "-" + send_msg_time3;
                station.setSend_msg_time(send_msg_time);
                station.setPath("fenzhan.svg");
                String remark = JSONObject.toJSONString(station);
                String operation2 = "修改分站" + station.getAlais();
                loginManage.addLog(request, remark, operation2, 1411);
				substationService.update(station);
			} else {
				String regex = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
				if (null==station.getIpaddr()||!station.getIpaddr().matches(regex)) {
					return result.setStatus(-3, "请输入正确的ip地址");
				}
				if (null == station.getPosition()) {
					result.clean();
					return result.setStatus(-2, "位置信息不能为空");
				}
				Static sta = staticService.getByPosition(station.getPosition());
				if (null == sta) {
					sta = staticService.addPosition(station.getPosition());
					station.setPositionId(sta.getId());
				} else {
					station.setPositionId(sta.getId());
				}
				String substring = station.getIpaddr().substring(station.getIpaddr().lastIndexOf(".")+1);
				List<String> list = equipmentsService.getallIp();
				for (String ipaddr : list) {
					if(station.getIpaddr().equals(ipaddr)){
						return result.setStatus(-2, "分站地址不能相同");
					}
					String string = ipaddr.substring(ipaddr.lastIndexOf(".")+1);
					if(string.equals(substring)){
						return result.setStatus(-2, "分站地址第四部分不能相同");
					}
				}
				station.setPath("fenzhan.svg");
                int i = station.getIpaddr().lastIndexOf(".");
                String alais = "G"+station.getIpaddr().substring(i+1);
                station.setAlais(alais);
                int send_msg_time1 = station.getSend_msg_time1();
                int send_msg_time2 = station.getSend_msg_time2();
                int send_msg_time3 = station.getSend_msg_time3();
                String send_msg_time = send_msg_time1 + "-" + send_msg_time2 + "-" + send_msg_time3;
                station.setSend_msg_time(send_msg_time);
                substationService.addup(station);
                String uid = "ST" + station.getId() + alais;
                substationService.updateUid(station.getId(), uid);
                String remark =JSONObject.toJSONString(station);
                String operation2 = "增加分站" + alais;
                loginManage.addLog(request, remark, operation2, 1411);
                Substation s = substationService.getByIp(station.getIpaddr());
				String value = "{\"id\":" + s.getId() + ",\"ip\":\"" + s.getIpaddr()
						+ "\",\"devid\":0,\"type\":\"0x10\"}";
				ConfigSyncThread.SendMessage(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("isuse", 1);
			return result.setStatus(-4, "exception");
		}
		result.clean();
		return result.setStatus(0, "ok");
	}

	/**
	 * 删除分站
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteSubstation(@PathVariable int id, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		try {
			Integer count = stationService.sensorCount(id);
			result.put("count", count);
			if (null != count && count > 0) {
				return result.setStatus(-3, "分站下存在设备，不允许删除");
			}
			Substation s = substationService.getSubbyid(id);
			String value = "{\"id\":" + s.getId() + ",\"ip\":\"" + s.getIpaddr()
					+ "\",\"devid\":0,\"type\":\"0x10\",\"op\":\"del\"}";
			ConfigSyncThread.SendMessage(value);
			String remark = JSONObject.toJSONString(s);
			String operation2 = "删除分站" + s.getAlais();
			loginManage.addLog(request, remark, operation2, 1412);
            substationService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 得到分站及传感器
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllSensor", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSensor(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		List<StationSensorVo> list = substationService.getAllSensor();
		if (list != null && list.size() > 0) {
			HashSet<Integer> set = new LinkedHashSet<Integer>();
			for (StationSensorVo vo : list) {
				set.add(vo.getStationId());
			}
			List<Substation> reslist = new ArrayList<Substation>();
			for (Integer stid : set) {
				Substation sub = new Substation();
				sub.setId(stid);
				List<Sensor> slist = new ArrayList<Sensor>();
				for (StationSensorVo vo : list) {
					if (stid == vo.getStationId()) {
						sub.setE_point(vo.getStation_e_point());
						sub.setN_point(vo.getStation_n_point());
						sub.setIpaddr(vo.getIpaddr());
						sub.setStation_name(vo.getStation_name());
						if (vo.getId() > 0) {
							Sensor sensor = new Sensor();
							sensor.setControl(vo.getControl());
							sensor.setE_point(vo.getE_point());
							sensor.setFloor_alarm(vo.getFloor_alarm());
							sensor.setFloor_power(vo.getFloor_power());
							sensor.setFloor_repower(vo.getFloor_repower());
							sensor.setFloor_warning(vo.getFloor_warning());
							sensor.setId(vo.getId());
							sensor.setLimit_alarm(vo.getLimit_alarm());
							sensor.setLimit_power(vo.getLimit_power());
							sensor.setLimit_repower(vo.getLimit_repower());
							sensor.setLimit_warning(vo.getLimit_warning());
							sensor.setMax_frequency(vo.getMax_frequency());
							sensor.setMax_range(vo.getMax_range());
							sensor.setMin_frequency(vo.getMin_frequency());
							sensor.setMin_range(vo.getMin_range());
							sensor.setN_point(vo.getN_point());
							sensor.setPosition(vo.getPosition());
							sensor.setSensor_position(vo.getSensor_position());
							sensor.setSensor_type(vo.getSensor_type());
							sensor.setSensorId(vo.getSensorId());
							sensor.setSensorUnit(vo.getSensorUnit());
							sensor.setStation_name(vo.getStation_name());
							sensor.setStation(vo.getStationId());
							sensor.setType(vo.getType());
							sensor.setAlais(vo.getAlais());
							slist.add(sensor);
						}
					}
				}
				sub.setSensors(slist);
				reslist.add(sub);
			}
			result.put("data", reslist);
		} else {
			result.put("data", list);
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 查询所有分站下的开关量传感器
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/allswitchsensor", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSwitchSensor(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<Substation> list = substationService.getAllSwitchSensor();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/allsubsensor", method = RequestMethod.GET)
	@ResponseBody
	public Object getallsubsensor(int substationid, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<Cardreder> list1 = cardrederService.getCardrederbysub(substationid);
			List<Sensor> list2 = baseinfoService.getbysubid(substationid);
			List<SwitchSensor> list3 = switchSensorService.getbysubid(substationid);
			result.put("cardreders", list1);
			result.put("sensors", list2);
			result.put("switchs", list3);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

}
