package com.cm.controller;

import com.cm.entity.Equipments;
import com.cm.entity.Static;
import com.cm.entity.Substation;
import com.cm.security.LoginManage;
import com.cm.service.EquipmentsService;
import com.cm.service.StaticService;
import com.cm.service.SubstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/equip")
public class EquipmentsController {

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private EquipmentsService equipService;
	
	@Autowired
	private StaticService staticService;
	
	@Autowired
	private SubstationService stationService;
	
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addup(@RequestBody Equipments equip,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			if(equip.getId()>0){
				if(equip.getType()==104){
					String regex = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
					if (null==equip.getIp()||!equip.getIp().matches(regex)) {
						return result.setStatus(-3, "请输入正确的ip地址");
					}
					String ip = equipService.getIpbyId(equip.getId());
					List<String> list = equipService.getallIp();
					for(String l:list){
						if(l.equals(equip.getIp())&&!ip.equals(equip.getIp())){
							return result.setStatus(-2, "系统中IP地址不能相同");
						}
					}
				}
				if(equip.getType()==72){
					Equipments equipments = equipService.getById(equip.getId());
					if(equipments.getDevid()!=equip.getDevid()){
                        String isuse = stationService.isuse(equip.getStationId(), equip.getDevid());
                        if (null!=isuse) {
							result.clean();
							result.put("isuse", 1);
							return result.setStatus(-2, isuse);
						} else {
							result.clean();
							result.put("isuse", 0);
						}
					}
				}
				if (null == equip.getPosition()) {
					result.clean();
					return result.setStatus(-2, "位置信息不能为空");
				}
				Static sta = staticService.getByPosition(equip.getPosition());
				if (null == sta) {
					sta = staticService.addPosition(equip.getPosition());
					equip.setPositionId(sta.getId());
				} else {
					equip.setPositionId(sta.getId());
				}
				equipService.update(equip);
			}else{
				String uid = "";
                String alais = "";
				if(equip.getType()==72){
					// 判断设备id是否重复
                    String isuse = stationService.isuse(equip.getStationId(), equip.getDevid());
                    if (null!=isuse) {
						result.clean();
						result.put("isuse", 1);
						return result.setStatus(-2, isuse);
					} else {
						result.clean();
						result.put("isuse", 0);
					}
					Substation sub = stationService.getSubbyid(equip.getStationId());
					String ipaddr = sub.getIpaddr();
					int i = ipaddr.lastIndexOf(".");
					String string = ipaddr.substring(i+1);
					alais = "G"+string+"P"+equip.getDevid();
					equip.setAlais(alais);
				}
				if(equip.getType()==104){
					String regex = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
					if (null==equip.getIp()||!equip.getIp().matches(regex)) {
						return result.setStatus(-3, "请输入正确的ip地址");
					}
					List<String> list = equipService.getallIp();
					for(String l:list){
						if(l.equals(equip.getIp())){
							return result.setStatus(-2, "系统中IP地址不能相同");
						}
					}
				}
				if (null == equip.getPosition()) {
					result.clean();
					return result.setStatus(-2, "位置信息不能为空");
				}
				Static sta = staticService.getByPosition(equip.getPosition());
				if (null == sta) {
					sta = staticService.addPosition(equip.getPosition());
					equip.setPositionId(sta.getId());
				} else {
					equip.setPositionId(sta.getId());
				}
				equipService.add(equip);
				if(equip.getType()==72){
                    uid = "OT"+equip.getId()+alais;
                    equipService.updateUid(equip.getId(), uid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");*/
		try {
			List<Equipments> list = equipService.getAllEquipments();
			List<Equipments> updateList = new ArrayList<>();
            for (Equipments equipments : list) {
                if(null == equipments.getSensorname() || null == equipments.getPath()){
                    if(equipments.getType() == 72){
                        equipments.setSensorname("电源箱");
                        equipments.setPath("dianyuanxiang.svg");
                    }
                    if(equipments.getType() == 102){
                        equipments.setSensorname("传输接口");
                        equipments.setPath("chuanshujiekou.svg");
                    }
                    if(equipments.getType() == 103){
                        equipments.setSensorname("电缆");
                        equipments.setPath("dianlan.svg");
                    }
                    if(equipments.getType()==104){
                    	equipments.setSensorname("交换机");
                    	equipments.setPath("jiaohuanji.svg");
                    }
                    updateList.add(equipments);
                }
            }
            if(null != updateList && !updateList.isEmpty()){
                equipService.batchUpdate(updateList);
            }
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4,"exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object delete(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			equipService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
