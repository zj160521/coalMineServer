package com.cm.controller;

import com.cm.entity.Radio;
import com.cm.entity.Static;
import com.cm.entity.Substation;
import com.cm.security.LoginManage;
import com.cm.service.RadioService;
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
@RequestMapping("/radio")
public class RadioController {
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private RadioService radioService;
	
	@Autowired
	private SubstationService stationService;
	
	@Autowired
	private StaticService staticService;
	
	/**
	 * 添加或更新广播
	 * @param radio
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addRadio(@RequestBody Radio radio,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			Object per = loginManage.checkPermission(request);
			if(null!=per) return per;
			if(radio.getId()>0){
				Radio radio2 = radioService.getById(radio.getId());
				if(radio2.getRadioId()!=radio.getRadioId()){
                    String isuse = stationService.isuse(radio.getStationId(), radio.getRadioId());
                    if(null!=isuse){
						result.clean();
						result.put("isuse", 1);
						return result.setStatus(-2, isuse);
					}else{
						result.clean();
						result.put("isuse", 0);
					}
				}
				//添加位置信息
				if(null==radio.getPosition()){
					return result.setStatus(-2, "位置信息不能为空");
				}
				Static sta = staticService.getByPosition(radio.getPosition());
				if(null == sta){
					sta = staticService.addPosition(radio.getPosition());
					radio.setPositionId(sta.getId());
				}else{
					radio.setPositionId(sta.getId());
				}
				radio.setStatus(1);
				radioService.update(radio);
			}else{
                String isuse = stationService.isuse(radio.getStationId(), radio.getRadioId());
                if(null!=isuse){
					result.clean();
					result.put("isuse", 1);
					return result.setStatus(-2, isuse);
				}else{
					result.clean();
					result.put("isuse", 0);
				}
				//添加位置信息
				if(null==radio.getPosition()){
					return result.setStatus(-2, "位置信息不能为空");
				}
				Static sta = staticService.getByPosition(radio.getPosition());
				if(null == sta){
					sta = staticService.addPosition(radio.getPosition());
					radio.setPositionId(sta.getId());
				}else{
					radio.setPositionId(sta.getId());
				}
				radio.setStatus(1);
                Substation sub = stationService.getSubbyid(radio.getStationId());
                String ipaddr = sub.getIpaddr();
                int i = ipaddr.lastIndexOf(".");
                String string = ipaddr.substring(i+1);
                String alais = "G"+string+"R"+radio.getRadioId();
                radio.setAlais(alais);
                radioService.add(radio);
                String uid = "AU"+radio.getId()+alais;
				radioService.updateUid(radio.getId(), uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有广播设备
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		try {
			List<Radio> list = radioService.getAll();
			if(list.isEmpty() || list == null){
				list = new ArrayList<Radio>();
			}
			result.clean();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 删除
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete/{id}")
	@ResponseBody
	public Object delete(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			Object per = loginManage.checkPermission(request);
			if(null!=per) return per;
			radioService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

}
