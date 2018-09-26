package com.cm.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;








import com.cm.entity.Equipments;
import com.cm.entity.Sensor;
import com.cm.entity.Substation;
import com.cm.entity.SwitchSensor;
import com.cm.entity.TopologyEquipment;
import com.cm.security.LoginManage;
import com.cm.service.BaseinfoService;
import com.cm.service.EquipmentsService;
import com.cm.service.SubstationService;
import com.cm.service.SwitchSensorService;



@Scope("prototype")
@Controller
@RequestMapping("/topologyEquipment")
public class TopologyEquipmentController {
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private BaseinfoService service2;
	
	@Autowired
	private SwitchSensorService service3;
	
	@Autowired
	private SubstationService service4;
	
	@Autowired
	private EquipmentsService service5;
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	@ResponseBody
	public Object getall(HttpServletRequest request){
//		if(!loginManage.isUserLogin(request)){
//			return result.setStatus(-1, "no login");
//		}
//		try {
			List<Substation> list = service4.getAll();
			List<Sensor> list2 = service2.getAllSensor(0);
			List<SwitchSensor> list3 = service3.getAll(null);
			List<Equipments> list4 = service5.getAllElec(); 
			List<TopologyEquipment> list5 = new ArrayList<TopologyEquipment>();
			Map<String, TopologyEquipment> map = new HashMap<String, TopologyEquipment>();
			Map<String, TopologyEquipment> map2 = new HashMap<String, TopologyEquipment>();
			Map<String, Integer> map3 = new HashMap<String, Integer>();
			if(list4!=null&&list4.size()>0){
				for(int i=0;i<list4.size();i++){
					Equipments sensor = list4.get(i);
					if(sensor.getType()==104){
						TopologyEquipment a = new TopologyEquipment();
						a.setKey(sensor.getName());
//						a.setBoss(0);
						a.setK(sensor.getIp()+":"+sensor.getType());
						a.setPath(sensor.getPath());
						a.setName(sensor.getName());
						a.setParent("服务器");
						list5.add(a);
					}
				}
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Substation substation = list.get(i);
					TopologyEquipment equipment = map.get(substation.getIpaddr());
					if(equipment==null){
						equipment = new TopologyEquipment();
						equipment.setKey(substation.getStation_name());
//						equipment.setBoss(1);
						equipment.setK(substation.getIpaddr());
						equipment.setPath(substation.getPath());
						equipment.setName(substation.getStation_name());
						if(list5!=null&&list5.size()>0){
							if (list5.size() == 1)  equipment.setParent(list5.get(0).getName());
							equipment.setParent("环网出口");
						}
						list5.add(equipment);
						map.put(equipment.getK(), equipment);
					}
				}
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Substation substation = list.get(i);
					TopologyEquipment equipment = map.get(substation.getIpaddr());
					TopologyEquipment a = new TopologyEquipment();
					TopologyEquipment b = new TopologyEquipment();
					TopologyEquipment c = new TopologyEquipment();
					a.setKey(substation.getAlais()+"模拟量");
//					a.setBoss(equipment.getKey());
					a.setName(substation.getAlais()+"模拟量");
					a.setParent(equipment.getName());
					a.setRemark(substation.getIpaddr()+":"+1);
					b.setKey(substation.getAlais()+"开关量");
//					b.setBoss(equipment.getKey());
					b.setName(substation.getAlais()+"开关量");
					b.setRemark(substation.getIpaddr()+":"+2);
					b.setParent(equipment.getName());
					c.setKey(substation.getAlais()+"其他");
//					c.setBoss(equipment.getKey());
					c.setName(substation.getAlais()+"其他");
					c.setRemark(substation.getIpaddr()+":"+3);
					c.setParent(equipment.getName());
					list5.add(a);
					list5.add(b);
					list5.add(c);
					map2.put(substation.getIpaddr()+":"+1, a);
					map2.put(substation.getIpaddr()+":"+2, b);
					map2.put(substation.getIpaddr()+":"+3, c);
					map3.put(substation.getIpaddr()+":"+1, 0);
					map3.put(substation.getIpaddr()+":"+2, 0);
					map3.put(substation.getIpaddr()+":"+3, 0);
				}
			}
			if(list2!=null&&list2.size()>0){
				String parent = null;
				String stationIp = null;
				for(int i=0;i<list2.size();i++){
					Sensor sensor = list2.get(i);
					if(sensor.getIpaddr() == null) continue;
					TopologyEquipment equipment = map2.get(sensor.getIpaddr()+":"+1);
					TopologyEquipment a = new TopologyEquipment();
					a.setKey(sensor.getType() + sensor.getAlais());
					a.setK(sensor.getIpaddr()+":"+sensor.getSensorId()+":"+sensor.getSensor_type());
					a.setPath(sensor.getPath());
					a.setName(sensor.getType() + sensor.getAlais());
					if(equipment != null && parent ==null && stationIp == null){
//						a.setBoss(equipment.getKey());
						parent = equipment.getName();
						stationIp = sensor.getIpaddr();
					}
					if(sensor.getIpaddr().equals(stationIp)){
						a.setParent(parent);
					}else{
						stationIp = sensor.getIpaddr();
						a.setParent(equipment.getName());
					}
					parent = a.getName();
					list5.add(a);
					map3.put(sensor.getIpaddr()+":"+1, map3.get(sensor.getIpaddr()+":"+1)+1);
				}
			}
			if(list3!=null&&list3.size()>0){
				String parent = null;
				String stationIp = null;
				for(int i=0;i<list3.size();i++){
					SwitchSensor sensor = list3.get(i);
					if(sensor.getIpaddr() == null) continue;
					TopologyEquipment equipment = map2.get(sensor.getIpaddr()+":"+2);
					TopologyEquipment a = new TopologyEquipment();
					a.setKey(sensor.getType() + sensor.getAlais());
					a.setK(sensor.getIpaddr()+":"+sensor.getSensorId()+":"+sensor.getSensor_type());
					a.setPath(sensor.getPath());
					a.setName(sensor.getType() + sensor.getAlais());
					if(equipment != null && parent ==null && stationIp == null){
//						a.setBoss(equipment.getKey());
						parent = equipment.getName();
						stationIp = sensor.getIpaddr();
					}
					if(sensor.getIpaddr().equals(stationIp)){
						a.setParent(parent);
					}else{
						stationIp = sensor.getIpaddr();
						a.setParent(equipment.getName());
					}
					parent = a.getName();
					list5.add(a);
					map3.put(sensor.getIpaddr()+":"+2, map3.get(sensor.getIpaddr()+":"+2)+1);
				}
			}
			if(list4!=null&&list4.size()>0){
				String parent = null;
				String stationIp = null;
				for(int i=0;i<list4.size();i++){
					Equipments sensor = list4.get(i);
					if(sensor.getType()==72){
						if(sensor.getIpaddr() == null) continue;
						TopologyEquipment equipment = map2.get(sensor.getIpaddr()+":"+3);
						TopologyEquipment a = new TopologyEquipment();
						a.setKey(sensor.getSensorname() + sensor.getAlais());
						if(equipment != null  && parent ==null){
//							a.setBoss(equipment.getKey());
							parent = equipment.getName();
						}	
						a.setK(sensor.getIpaddr()+":"+sensor.getDevid()+":"+sensor.getType());
						a.setPath(sensor.getPath());
						a.setName(sensor.getSensorname() + sensor.getAlais());
						if(equipment != null && parent ==null && stationIp == null){
//							a.setBoss(equipment.getKey());
							parent = equipment.getName();
							stationIp = sensor.getIpaddr();
						}
						if(sensor.getIpaddr().equals(stationIp)){
							a.setParent(parent);
						}else{
							stationIp = sensor.getIpaddr();
							a.setParent(equipment.getName());
						}
						parent = a.getName();
						list5.add(a);
						map3.put(sensor.getIpaddr()+":"+3, map3.get(sensor.getIpaddr()+":"+3)+1);
					}
				}
			}
			
			for(TopologyEquipment l:list5){
				if(l.getRemark()!=null){
					l.setName2(l.getName()+"("+map3.get(l.getRemark())+")");
				}
			}
			
			if(list5.size()==0){
				result.put("data", list5);
				return result.setStatus(0, "没有数据！");
			}else{
				result.put("data", list5);
			}
			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return result.setStatus(-4, "exception");
//		}
		return result.setStatus(0, "ok");
	}
	
}
