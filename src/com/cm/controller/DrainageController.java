package com.cm.controller;

import com.cm.entity.*;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.*;
import com.cm.service.kafka.ConfigSyncThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import util.LogOut;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/drain")
public class DrainageController {

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private ResultObj result;

	@Autowired
	private DrainageService drainageService;

	@Autowired
	private BaseinfoService baseinfoService;

	@Autowired
	private StaticService staticService;

	@Autowired
	private SubstationService stationService;

	@Autowired
	private DefaultChartService chartService;

	@Autowired
	private SwitchSensorService switchSensorService;

	/**
	 * 查询所有抽采参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllDrainage(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			Drainage drainage = new Drainage();
			drainage.setPid(10);
			List<Drainage> drainages = new ArrayList<>();
			List<Drainage> list = drainageService.getAllDrainage(drainage);
			for (Drainage l : list) {
				drainage.setPid(l.getId());
				List<Drainage> list2 = drainageService.getAllDrainage(drainage);
				l.setLists(list2);
				if(l.getTypeid()==2||l.getTypeid()==3||l.getTypeid()==4||l.getTypeid()==5||l.getTypeid()==6){
				    drainages.add(l);
                }
			}
            Iterator<Drainage> it = list.iterator();
			while (it.hasNext()){
                Drainage next = it.next();
                if(next.getTypeid()==2||next.getTypeid()==3||next.getTypeid()==4||next.getTypeid()==5||next.getTypeid()==6){
                    it.remove();
                }
                if(next.getTypeid()==1){
                    next.setList(drainages);
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
	 * 按抽采参数分类查询瓦斯抽放系统的传感器
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sensorparam", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSensor(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
		    List<Object> obj = new ArrayList<>();
		    List<Drainage> drainages = new ArrayList<>();
            List<Drainage> list = drainageService.getAlloneparam();
            List<Sensor> allSensor = baseinfoService.getAllSensor2();
            Map<Integer, Sensor> sensorMap = makeSensorMap(allSensor);
            List<Sensor> sensors = null;
            for (Drainage drain : list) {
                List<Map<String, String>> titless = new ArrayList<>();
                Map<String, String> map5 = new HashMap<>();
                map5.put("key", "now_value");
                map5.put("title", "值");
                titless.add(map5);
                drain.setTitles(titless);
                if(drain.getTypeid() == 1){
                    List<Map<String, String>> list1 = makeTitles();
                    drain.setTitles(list1);
                    sensors = drain.getSensors();
                    List sensors1 = drain.getSensors();
                    Iterator it = sensors1.iterator();
                    while (it.hasNext()){
                        Sensor sensor = (Sensor)it.next();
                        if(sensor.getSensorId() < 1){
                            it.remove();
                        }

                    }
                    continue;
                }
                List<Sensor> sensorList = drain.getSensors();
                if(null == sensorList){
                    sensorList = new ArrayList<>();
                }
                if(drain.getTypeid()==2||drain.getTypeid()==3||drain.getTypeid()==4){
                    for (Sensor sensor : sensors) {
                        Sensor clone = (Sensor) sensor.clone();
                        clone.setControl(3);
                        clone.setPosition(clone.getPosition() + drain.getType());
                        sensorList.add(clone);
                    }
                }
                if(drain.getTypeid() == 5){
                    for (Sensor sensor : sensors) {
                        if(sensor.getMethaneId() > 0){
                            Sensor sensor1 = sensorMap.get(sensor.getMethaneId());
                            sensor1.setPosition(sensor1.getPosition() + drain.getType());
                            sensorList.add(sensor1);
                        } else {
                            Sensor clone = (Sensor) sensor.clone();
                            clone.setControl(3);
                            clone.setPosition(clone.getPosition() + drain.getType());
                            sensorList.add(clone);
                        }
                    }
                }
                if(drain.getTypeid() == 6){
                    for (Sensor sensor : sensors) {
                        if(sensor.getCoId() > 0){
                            Sensor sensor1 = sensorMap.get(sensor.getCoId());
                            if (null != sensor1){
                                sensor1.setPosition(sensor1.getPosition() + drain.getType());
                                sensorList.add(sensor1);
                            } else {
                                Sensor clone = (Sensor) sensor.clone();
                                clone.setControl(3);
                                clone.setPosition(clone.getPosition() + drain.getType());
                                sensorList.add(clone);
                            }
                        } else {
                            Sensor clone = (Sensor) sensor.clone();
                            clone.setControl(3);
                            clone.setPosition(clone.getPosition() + drain.getType());
                            sensorList.add(clone);
                        }
                    }
                }
                if(drain.getTypeid() == 2){
                    List<Map<String, String>> titles = new ArrayList<>();
                    Map<String, String> map4 = new HashMap<>();
                    map4.put("key", "flow_work");
                    map4.put("title", "工况混合流量(m³/min)");
                    titles.add(map4);
                    drain.setTitles(titles);
                    drainages.add(drain);
                }
                if(drain.getTypeid() == 3){
                    List<Map<String, String>> titles  = new ArrayList<>();
                    Map<String, String> map = new HashMap<>();
                    map.put("key","temperature");
                    map.put("title", "温度(℃)");
                    titles.add(map);
                    drain.setTitles(titles);
                    drainages.add(drain);
                }
                if(drain.getTypeid() == 4){
                    List<Map<String, String>> titles = new ArrayList<>();
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("key", "pressure");
                    map1.put("title", "压力(kPa)");
                    titles.add(map1);
                    drain.setTitles(titles);
                    drainages.add(drain);
                }
                if(drain.getTypeid() == 5){
                    List<Map<String, String>> titles = new ArrayList<>();
                    Map<String, String> map2 = new HashMap<>();
                    map2.put("key", "wasi");
                    map2.put("title", "瓦斯浓度(%)");
                    titles.add(map2);
                    drain.setTitles(titles);
                    drainages.add(drain);
                }
                if(drain.getTypeid() == 6){
                    List<Map<String, String>> titles = new ArrayList<>();
                    Map<String, String> map3 = new HashMap<>();
                    map3.put("key", "co");
                    map3.put("title", "一氧化碳浓度(ppm)");
                    titles.add(map3);
                    drain.setTitles(titles);
                    drainages.add(drain);
                }
                Iterator<Sensor> it = sensorList.iterator();
                while (it.hasNext()){
                    Sensor next = it.next();
                    if(next.getSensorId() <= 0){
                        it.remove();
                    }
                }
                drain.setSensors(sensorList);
            }
            for (Drainage drain : list) {
                List<Sensor> sensors1 = drain.getSensors();
                for (Sensor sensor : sensors1) {
                    if(sensor.getSensorId() > 0 && sensor.getControl() == 1){
                        obj.add(sensor);
                    }
                }
                List<SwitchSensor> switches = drain.getSwitches();
                for (SwitchSensor aSwitch : switches) {
                    if (aSwitch.getSensorId() > 0 && aSwitch.getControl() == 1){
                        obj.add(aSwitch);
                    }
                }
                if(drain.getTypeid() == 1){
                    drain.setList(drainages);
                }
            }
            Iterator<Drainage> it = list.iterator();
            while (it.hasNext()){
                Drainage drain = it.next();
                if(drain.getTypeid()==2||drain.getTypeid()==3||drain.getTypeid()==4||drain.getTypeid()==5||drain.getTypeid()==6){
                    it.remove();
                }
            }
            Drainage dra = null;
            Iterator<Drainage> iterator = list.iterator();
            while (iterator.hasNext()){
                Drainage next = iterator.next();
                if(next.getTypeid() == 1){
                    dra = next;
                    iterator.remove();
                }
            }
            list.add(0,dra);
            result.put("data",list);
            result.put("list",obj);
        } catch (Exception e) {
			e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (int i = 0; i < stackTrace.length ; i++) {
                StackTraceElement element = stackTrace[i];
                sb.append(element.toString());
            }
            LogOut.log.error(sb.toString());
            return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 添加抽采参数
	 * 
	 * @param drainage
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addDrainageParam(@RequestBody Drainage drainage, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		try {
			int a = drainageService.validation(drainage);
			if (a > 0) {
				return result.setStatus(-2, "参数名称已存在，请重新输入！");
			} else {
				if (drainage.getId() == null || drainage.getId() == 0) {
					if (drainage.getPid() == null || drainage.getPid() == 0) {
						drainage.setPid(10);
						drainageService.addDrainageParam(drainage);
					} else {
						drainageService.addDrainageParam(drainage);
					}
				} else {
					drainageService.updateDrainage(drainage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteDrainage(@RequestBody Drainage drainage, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			Object per = loginManage.checkPermission(request);
			if (null != per)
				return per;
			Drainage drainage2 = drainageService.getById(drainage.getId());
			if (drainage2.getPid() == 10) {
				Drainage drainage3 = new Drainage();
				drainage3.setPid(drainage.getId());
				List<Drainage> list = drainageService.getAllDrainage(drainage3);
				for (Drainage l : list) {
					drainageService.deleteDrainage(l);
				}
				drainageService.deleteDrainage(drainage);
			} else {
				drainageService.deleteDrainage(drainage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 添加模拟量传感器
	 * 
	 * @param sensor
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addsensor", method = RequestMethod.POST)
	@ResponseBody
	public Object addSensor(@RequestBody Sensor sensor, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Jedis jedis=null;
		try {
			jedis = RedisClient.getInstance().getJedis();
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, e.getMessage());
		}
		Sensor sensor2 = new Sensor();
		try {
			Object per = loginManage.checkPermission(request);
			if (null != per)
				return per;
			if (sensor.getDrainageId() < 1) {
				return result.setStatus(-2, "请选择正确的测点！");
			}
			sensor.setControl(1);
			if (sensor.getId() > 0) {
				Sensor sensor3 = baseinfoService.getById2(sensor.getId());
				if (sensor3.getSensorId() != sensor.getSensorId()) {
                    String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                    if (null!=isuse) {
						result.put("isuse", 1);
						return result.setStatus(0, isuse);
					} else {
						result.put("isuse", 0);
					}
				}
				// 添加位置信息
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
				// 添加别名
				Substation sub = stationService.getSubbyid(sensor.getStation());
				String ipaddr = sub.getIpaddr();
				int i = ipaddr.lastIndexOf(".");
				String string = ipaddr.substring(i + 1);
				String alais = "G" + string + "S" + sensor.getSensorId();
				sensor.setAlais(alais);
				baseinfoService.updataSensor(sensor);
			} else {
				// 判断设备id是否重复
                String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                if (null!=isuse) {
					result.put("isuse", 1);
					return result.setStatus(0, isuse);
				} else {
					result.put("isuse", 0);
				}
				// 添加位置信息
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
				// 添加别名
				Substation sub = stationService.getSubbyid(sensor.getStation());
				String ipaddr = sub.getIpaddr();
				int i = ipaddr.lastIndexOf(".");
				String string = ipaddr.substring(i + 1);
				String alais = "G" + string + "S" + sensor.getSensorId();
				sensor.setAlais(alais);
				// 添加设备
				baseinfoService.addBaseinfo(sensor);
                String uid = "SE"+sensor.getId()+alais;
				baseinfoService.updateUid(sensor.getId(),uid);
				int id = switchSensorService.getaddedId(sensor.getStation(), sensor.getSensorId(),
						sensor.getSensor_type());
				sensor2.setId(id);
			}
			// 将更新之后的设备信息发送给分站
			Sensor switchSensor = baseinfoService.getById2(sensor.getId());
			String value = "{\"id\":" + switchSensor.getId() + ",\"ip\":\"" + switchSensor.getIpaddr() + "\",\"devid\":"
					+ switchSensor.getSensorId() + ",\"type\":" + switchSensor.getSensor_type() + ",\"typename\":\""
					+ switchSensor.getType() + "\"}";
			ConfigSyncThread.SendMessage(value);
			// 修改redis
			
			if (!jedis.hexists("gd5", switchSensor.getIpaddr() + ":" + sensor.getSensorId() + ":" + "flow_work_sum")
					&& sensor.getSensor_type() == 69) {
				jedis.hset("sensorWarn",
						switchSensor.getIpaddr() + ":" + sensor.getSensorId() + ":" + "flow_work_sum", "0");
				jedis.hset("sensorWarn",
						switchSensor.getIpaddr() + ":" + sensor.getSensorId() + ":" + "flow_standard_sum", "0");
				jedis.hset("sensorWarn",
						switchSensor.getIpaddr() + ":" + sensor.getSensorId() + ":" + "flow_pure_sum", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}

		return result.setStatus(0, "ok");
	}

	/**
	 * 查询瓦斯抽放系统传感器的值
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sensordata", method = RequestMethod.POST)
	@ResponseBody
	public Object getAllSensorData(@RequestBody DefaultChart chart, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			chart.setPid(10);
			DefaultChart chart2 = chartService.getById(chart.getModule_id());
			if (null == chart2) {
				chartService.add(chart);
			} else {
				chartService.update(chart);
			}
			long time = System.currentTimeMillis();
			long times = time - (24 * 60 * 60 * 1000);
			String starttime = currentTimeToString(times);
			List<Drainage> list = drainageService.getAllSensorData(chart.getDrainage_id(), starttime);
			if (list.isEmpty()) {
				return result.setStatus(-3, "no data");
			}
			for (Drainage<Sensor> drainage : list) {
				List<Sensor> sensors = drainage.getSensors();
				for (Sensor sensor : sensors) {
					List<Coalmine> coalmines = sensor.getCoalmines();
					if (coalmines.isEmpty()) {
						// continue;
					}
					List<Coalmine> list2 = new ArrayList<Coalmine>();
					for (int i = 24; i > 0; i--) {
						for (Coalmine coalmine : coalmines) {
							// 判断每个对象的时间是否处于过去的i个小时和i-1个小时之间，如果是则取一个对象放入list2并跳出循环进行下一次循环
							if (StringTimeToCurrentTime(
									coalmine.getFilltime()) > (System.currentTimeMillis() - (i * 60 * 60 * 1000))
									&& StringTimeToCurrentTime(coalmine.getFilltime()) < (System.currentTimeMillis()
											- ((i - 1) * 60 * 60 * 1000))) {
								String filltime = coalmine.getFilltime();
								String date = formatDate(filltime);
								int hour = getHour(date);
								Coalmine coal = new Coalmine();
								coal.setFilltime(hour + "");
								coal.setValue(coalmine.getValue());
								list2.add(coal);
								break;
							}
						}
					}
					for (int i = 23; i >= 0; i--) {
						boolean b = false;
						for (Coalmine coalmine : list2) {
							if (coalmine.getFilltime().equals(i + "")) {
								b = true;
							}
						}
						if (!b) {
							Coalmine coalmine = new Coalmine();
							coalmine.setValue(0);
							coalmine.setFilltime(i + "");
							list2.add(coalmine);
						}
					}
					Map<String, String> map = new HashMap<String, String>();
					for (Coalmine coalmine : list2) {
						map.put(coalmine.getFilltime(), coalmine.getValue() + "");
					}
					sensor.setMap(map);
					sensor.setCoalmines(list2);
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
	 * 删除瓦斯抽放系统的传感器
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteSensor(@PathVariable int id, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			Object per = loginManage.checkPermission(request);
			if (null != per)
				return per;
			drainageService.deleteSensor(id);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	// 添加或者更新用户浏览的数据表格记录
	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@ResponseBody
	public Object records(@RequestBody DefaultChart chart, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<DefaultChart> list = chartService.getAll(20);
			if (list.isEmpty()) {
				chartService.batchadd(chart);
			} else {
				chartService.deleteByPid(20);
				chartService.batchadd(chart);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	// 取用户最近一次查看的图信息
	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllChart(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<DefaultChart> list = chartService.getAll(10);
			if (list.isEmpty()) {
				return result.setStatus(-3, "no data");
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 添加开关量传感器
	 * 
	 * @param sensor
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addswitch", method = RequestMethod.POST)
	@ResponseBody
	public Object addSwitchSensor(@RequestBody SwitchSensor sensor, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		int id = 0;
		try {
			Object per = loginManage.checkPermission(request);
			if (null != per)
				return per;
			if (sensor.getDrainageId() < 1) {
				return result.setStatus(-2, "请选择正确的测点！");
			}
			sensor.setControl(1);
			if (sensor.getId() > 0) {
				SwitchSensor sensor2 = switchSensorService.getById(sensor.getId());
				if(sensor2.getSensorId()!=sensor.getSensorId()){
					// 判断设备id是否重复
                    String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                    if (null!=isuse) {
						result.put("isuse", 1);
						return result.setStatus(0, isuse);
					} else {
						result.put("isuse", 0);
					}
				}
				// 添加位置信息
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
                switchSensorService.update(sensor);
			} else {

				// 判断设备id是否重复
                String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                if (null!=isuse) {
					result.put("isuse", 1);
					return result.setStatus(0, isuse);
				} else {
					result.put("isuse", 0);
				}
				// 添加位置信息
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
				switchSensorService.addSwitchSensor(sensor);
                String uid = "SW"+sensor.getId()+alais;
                switchSensorService.updateUid(sensor.getId(), uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		id = switchSensorService.getaddedId(sensor.getStation(), sensor.getSensorId(), sensor.getSensor_type());
		SwitchSensor switchSensor = switchSensorService.getById(id);
		String value = "{\"id\":" + switchSensor.getId() + ",\"ip\":\"" + switchSensor.getIpaddr() + "\",\"devid\":"
				+ switchSensor.getSensorId() + ",\"type\":" + switchSensor.getSensor_type() + ",\"typename\":\""
				+ switchSensor.getType() + "\"}";
		ConfigSyncThread.SendMessage(value);
		return result.setStatus(0, "ok");
	}

	// 取用户最近一次查看的表信息
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/charts", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllRecord(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<DefaultChart> list = chartService.getAll(20);
			List<Drainage> list2 = new ArrayList<Drainage>();
			for (DefaultChart chart : list) {
				Drainage drainage = drainageService.getById(chart.getDrainage_id());
				list2.add(drainage);
			}
			result.put("data", list2);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 删除开关量传感器
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteswitch/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteSwitchSensor(@PathVariable int id, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
		    String operation2 = "删除瓦斯抽放开关量传感器";
			Object per = loginManage.checkPermission(request);
			if (null != per)
				return per;
			drainageService.deleteSwitch(id);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	// 处理timestamp后面的.0
	private String formatDate(String time) {
		Timestamp timestamp = Timestamp.valueOf(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(timestamp);
		return format;
	}

	// 毫秒时间转换成字符串时间
	private String currentTimeToString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}

	// 字符串时间转换成毫秒时间
	private long StringTimeToCurrentTime(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(time));
		return calendar.getTimeInMillis();
	}

	// 取字符串时间的小时
	private int getHour(String time) {
		int i = time.indexOf(" ");
		String atime = time.substring(i + 1, i + 3);
		Integer integer = Integer.valueOf(atime);
		return integer;
	}

	private List<Map<String, String>> makeTitles(){
	    List<Map<String, String>> list  = new ArrayList<>();
        Map<String, String> map5 = new HashMap<>();
        map5.put("key", "type");
        map5.put("title", "类型");
        list.add(map5);
	    Map<String, String> map = new HashMap<>();
	    map.put("key","temperature");
	    map.put("title", "温度(℃)");
	    list.add(map);
	    Map<String, String> map1 = new HashMap<>();
	    map1.put("key", "pressure");
	    map1.put("title", "压力(kPa)");
	    list.add(map1);
	    Map<String, String> map2 = new HashMap<>();
	    map2.put("key", "wasi");
	    map2.put("title", "瓦斯浓度(%)");
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
	    map3.put("key", "co");
	    map3.put("title", "一氧化碳浓度(ppm)");
	    list.add(map3);
	    Map<String, String> map4 = new HashMap<>();
	    map4.put("key", "flow_work");
	    map4.put("title", "工况混合流量(m³/min)");
	    list.add(map4);
	    return list;
    }

    @RequestMapping(value = "/allcosensor", method = RequestMethod.GET)
    @ResponseBody
    public Object getAlldrainageCOSensor(HttpServletRequest request){
	    if(!loginManage.isUserLogin(request)){
	        return result.setStatus(-1,"no login");
        }
        try{
            List<Sensor> list = drainageService.getAlldrainageCOSensor();
            result.put("data",list);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
	    return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/allmethanesensor", method = RequestMethod.GET)
    @ResponseBody
    public Object getAlldrainageMethaneSensor(HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            List<Sensor> list = drainageService.getAlldrainageMethaneSensor();
            result.put("data",list);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }

    private Map<Integer,Sensor> makeSensorMap(List<Sensor> allSensor){
	    Map<Integer,Sensor> map = new HashMap<>();
	    if(!allSensor.isEmpty()){
            for (Sensor sensor : allSensor) {
                map.put(sensor.getId(), sensor);
            }
        }
	    return map;
    }
}
