package com.cm.controller;


import com.cm.dao.SensorCallDao;
import com.cm.entity.*;
import com.cm.entity.vo.AnaloginfoQuery;
import com.cm.entity.vo.AreaListVo;
import com.cm.entity.vo.DevlinksVo;
import com.cm.entity.vo.WindwattVo;
import com.cm.security.LoginManage;
import com.cm.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.LogOut;
import util.StaticUtilMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 设备管理
 * @author Administrator
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/equipment")
public class EquipmentController {
	

	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private StaticService service1;
	
	@Autowired
	private BaseinfoService service2;
	
	@Autowired
	private SwitchSensorService service3;
	
	@Autowired
	private SubstationService service4;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private WindwattService windService;
	
	@Autowired
	private DevLinkService devService;
	
	@Autowired
	private EnvAreaService envAreaService;
	
	@Autowired
	private EquipmentsService equimentsService;
	
	@Autowired
	private SensorCallDao sensorCallDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	@ResponseBody
	public Object getall(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Equipment> equipments = new ArrayList<Equipment>();
			Equipment equipment1 = new Equipment();
			Equipment equipment2 = new Equipment();
			Equipment equipment3 = new Equipment();
			Equipment equipment4 = new Equipment();
			Equipment equipment5 = new Equipment();
			Equipment equipment6 = new Equipment();
			Equipment equipment7 = new Equipment();
			Equipment equipment8 = new Equipment();
			
			List<Substation> substations = service4.getAll();
			List<Sensor> sensors2 = service2.getAllSensor(0);
            List<Sensor> sensors1 = new ArrayList<Sensor>();
            for(Sensor s:sensors2){
            	if(s.getSensor_type()!=69){
            		sensors1.add(s);
            	}
            }
            List<SwitchSensor> switchSensors1 = service3.getAll(0);
            List<Static> statics = service1.getAllSensorType();
            List<Static> statics2 = service1.getAllSwitchSensorType();
            Iterator<Static> it = statics.iterator();
            while (it.hasNext()){
                if(it.next().getId() == 69){
                    it.remove();
                }
            }
            Map<String, Substation> map = new HashMap<String,Substation>();
			for(Substation s:substations){
				Substation a = map.get(s.getIpaddr());
				if(a==null){
					a = s;
					a.setLabel(a.getStation_name());
					map.put(a.getIpaddr(), a);
				}
			}
			 Map<Integer, Static> map2 = new HashMap<Integer,Static>();
	            for(Static s:statics){
	            	Static a = map2.get(s.getId());
	            	if(a==null){
	            		a = s;
	            		a.setLabel(s.getV());
	            		map2.put(a.getId(), a);
	            	}
	            }
			Map<Integer, Static> map3 = new HashMap<Integer,Static>();
			for(Static s:statics2){
				Static a = map3.get(s.getId());
				if(a==null){
					a = s;
					a.setLabel(s.getV());
					map3.put(a.getId(), a);
				}
			}
			for(Sensor s:sensors1){
				equipment1.getLists().add(s);
				Substation a = map.get(s.getIpaddr());
				if (null != a){
                    a.getLists().add(s);
                }
				equipment2.getLists().add(s);
				Static b = map2.get(s.getSensor_type());
				b.getLists().add(s);
			}
			for(SwitchSensor s:switchSensors1){
				equipment1.getLists().add(s);
				Substation a = map.get(s.getIpaddr());
				a.getLists().add(s);
				equipment3.getLists().add(s);
				Static b = map3.get(s.getSensor_type());
				b.getLists().add(s);
			}
			equipment1.setId(0);
			equipment1.setLabel("分站/传感器");
			for(Substation value:map.values()){
				equipment1.getChildren().add(value);
			}
			Collections.sort(equipment1.getChildren(),new Comparator() {

				@Override
				public int compare(Object o1, Object o2) {
					Substation substation1 = (Substation) o1;
					Substation substation2 = (Substation) o2;
					return substation1.getIpaddr().compareTo(substation2.getIpaddr());
				}
			});
			equipment2.setId(1);
			equipment2.setLabel("模拟量传感器");
			for(Static value:map2.values()){
				equipment2.getChildren().add(value);
			}
			
			equipment3.setId(2);
			equipment3.setLabel("开关量传感器");
			for(Static value:map3.values()){
				equipment3.getChildren().add(value);
			}
			List<AreaListVo> areas = areaService.getallarealist();
			equipment4.setId(3);
			equipment4.setLabel("区域/传感器");
			for(AreaListVo s:areas){
				List<Sensor> sensors = service2.getSensorByAreaId(s.getId());
                Iterator<Sensor> ite = sensors.iterator();
                while (ite.hasNext()){
                    if (ite.next().getSensor_type() == 69){
                        it.remove();
                    }
                }
                List<SwitchSensor> sensorsList = service3.getSensorByAreaId(s.getId());
				s.setLists(sensors);
				for(SwitchSensor w:sensorsList){
					s.getLists().add(w);
				}
				s.setLabel(s.getAreaname());
			}
			equipment4.setChildren(areas);
			
			equipment5.setId(4);
			equipment5.setLabel("联动控制");
			List<Windwatt> windwatts = windService.getallWindwatt();
			List<DevlinksVo> linkVos =  devService.getDevLinkVos();
			for(Windwatt w:windwatts){
				List<WindwattVo> vos = w.getList();
				List<String> uids = new ArrayList<String>();
				for(WindwattVo s:vos){
					uids.add(s.getUid());
				}
                List<Sensor> sensors = new ArrayList<>();
				if (StaticUtilMethod.notNullOrEmptyList(uids)){

                    sensors = service2.getbyuids(uids);
                }
				List<SwitchSensor> switchSensors = service3.getbyuids(uids);
				w.setLists(sensors);
				for(SwitchSensor s:switchSensors){
					w.getLists().add(s);
				}
				w.setLabel(w.getName());
			}
			equipment5.setChildren(windwatts);
			for(DevlinksVo d:linkVos){
				List<Sensor> sensors = service2.getbyuids(d.getUids());
				List<SwitchSensor> switchSensors = service3.getbyuids(d.getUids());
				d.setLists(sensors);
				for(SwitchSensor s:switchSensors){
					d.getLists().add(s);
				}
				equipment5.getChildren().add(d);
			}
			
			equipment6.setId(5);
			equipment6.setLabel("区域/设施");
			List<EnvArea> areas2 = envAreaService.getAll();
			for(EnvArea area:areas2){
				area.setType(900);
			}
			equipment6.setLists(areas2);
			
			equipment7.setId(6);
			equipment7.setLabel("分站");
			List<Substation> substationss = service4.getAll();
			equipment7.setLists(substationss);
			
			equipment8.setId(7);
			equipment8.setLabel("交换机");
			List<Equipments> list = equimentsService.getAllElec();
			List<Equipments> list2 = new ArrayList<Equipments>();
			for(Equipments l:list){
				if(l.getType()==104){
					list2.add(l);
				}
			}
			equipment8.setLists(list2);
			
			equipments.add(equipment1);
			equipments.add(equipment2);
			equipments.add(equipment3);
			equipments.add(equipment4);
			equipments.add(equipment5);
			equipments.add(equipment6);
			equipments.add(equipment7);
			equipments.add(equipment8);
			result.put("data",equipments);
		} catch (Exception e) {
		    StringBuffer sb = new StringBuffer();
			e.printStackTrace();
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                sb.append(elements[i].toString()+"\n");
            }
            LogOut.log.error("EquipmentController Exception:"+sb);
            return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 设备树形结构表
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getallequipment",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllequipment(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Substation> substation = service4.getAll();
			List<Substation> substations = service4.getAll();
			List<Sensor> sensors = service2.getAllSensor2();
			List<SwitchSensor> switchSensors = service3.getAll(0);
			List<Substation> sensorsubstations= new ArrayList<Substation>();
	        List<Substation> switchsubstations = new ArrayList<Substation>();
	        Map<String,Substation> map = new HashMap<String,Substation>();
            Map<String,Substation> map2 = new HashMap<String,Substation>();
            Iterator<Sensor> it = sensors.iterator();
            while (it.hasNext()){
                if(it.next().getSensor_type()== 69){
                    it.remove();
                }
            }
            for(Substation s:substation){
            	map.put(s.getIpaddr(), s);
            }
            for(Substation s:substations){
            	map2.put(s.getIpaddr(), s);
            }
           for(Sensor s:sensors){
        	   Substation a = map.get(s.getIpaddr());
        	   if(a!=null){
        		   a.getLists().add(s);  
        	   }
           }
           for(SwitchSensor s:switchSensors){
        	   Substation a = map2.get(s.getIpaddr());
        	   if(a!=null){
        		   a.getLists().add(s);
        	   }
           }
           for(Substation value:map.values()){
        	   sensorsubstations.add(value);
           }
           for(Substation value:map2.values()){
        	   switchsubstations.add(value);
           }
			Collections.sort(sensorsubstations,new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					Substation substation1 = (Substation) o1;
					Substation substation2 = (Substation) o2;
					return substation1.getIpaddr().compareTo(substation2.getIpaddr());
				}
			});
			Collections.sort(switchsubstations,new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					Substation substation1 = (Substation) o1;
					Substation substation2 = (Substation) o2;
					return substation1.getIpaddr().compareTo(substation2.getIpaddr());
				}
			});
			result.put("data", sensorsubstations);
			result.put("list", switchsubstations);
		} catch (Exception e) {
		    StringBuffer sb = new StringBuffer();
			e.printStackTrace();
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                sb.append(elements[i].toString()+"\n");
            }
            LogOut.log.error("EquipmentController Exception:"+sb);
            return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 控制设备列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getcontrolequipment",method=RequestMethod.GET)
	@ResponseBody
	public Object getControlequipment(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<SwitchSensor> switchSensors = service3.getControlSensor();
		} catch (Exception e) {
		    StringBuffer sb = new StringBuffer();
			e.printStackTrace();
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                sb.append(elements[i].toString()+"\n");
            }
            LogOut.log.error("EquipmentController Exception:"+sb);
            return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 报警设备列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getalarmequipment/{type}",method=RequestMethod.POST)
	@ResponseBody
	public Object getAlarmequipment(@PathVariable int type,HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		try {
			List<Sensor> sensors = service2.getAllSensor(0);
			if(type==0){
				result.put("data", sensors);
			}else if(type==1){
				List<AnaloginfoQuery> alarmsensors = sensorCallDao.getsensorAlarm(null);
				Map<String, String> map = new HashMap<String,String>();
				List<Sensor> sensors2 = new ArrayList<Sensor>();
				for(AnaloginfoQuery s:alarmsensors){
					String a = map.get(s.getIp()+s.getSensorId()+s.getSensor_type());
					if(a==null){
						map.put(s.getIp()+s.getSensorId()+s.getSensor_type(), s.getIp()+s.getSensorId()+s.getSensor_type());
					}
				}
				for(Sensor s:sensors){
					if(map.get(s.getIpaddr()+s.getSensorId()+s.getSensor_type())!=null){
						sensors2.add(s);
					}
				}
				result.put("data", sensors2);
			}else if(type==2){
				List<AnaloginfoQuery> powersensors = sensorCallDao.getsensorPower(null);
				Map<String, String> map = new HashMap<String,String>();
				List<Sensor> sensors2 = new ArrayList<Sensor>();
				for(AnaloginfoQuery s:powersensors){
					String a = map.get(s.getIp()+s.getSensorId()+s.getSensor_type());
					if(a==null){
						map.put(s.getIp()+s.getSensorId()+s.getSensor_type(), s.getIp()+s.getSensorId()+s.getSensor_type());
					}
				}
				for(Sensor s:sensors){
					if(map.get(s.getIpaddr()+s.getSensorId()+s.getSensor_type())!=null){
						sensors2.add(s);
					}
				}
				result.put("data", sensors2);
			}else if(type==3){
				List<AnaloginfoQuery> feedsensors = sensorCallDao.getfeedstatus();
				Map<String, String> map = new HashMap<String,String>();
				List<Sensor> sensors2 = new ArrayList<Sensor>();
				for(AnaloginfoQuery s:feedsensors){
					String a = map.get(s.getIp()+s.getSensorId()+s.getSensor_type());
					if(a==null){
						map.put(s.getIp()+s.getSensorId()+s.getSensor_type(), s.getIp()+s.getSensorId()+s.getSensor_type());
					}
				}
				for(Sensor s:sensors){
					if(map.get(s.getIpaddr()+s.getSensorId()+s.getSensor_type())!=null){
						sensors2.add(s);
					}
				}
				result.put("data", sensors2);
			}
		} catch (Exception e) {
		    StringBuffer sb = new StringBuffer();
			e.printStackTrace();
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                sb.append(elements[i].toString()+"\n");
            }
            LogOut.log.error("EquipmentController Exception:"+sb);
            return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	
}
