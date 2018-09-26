package com.cm.controller;

import com.cm.entity.Cardreder;
import com.cm.entity.Static;
import com.cm.entity.Substation;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.AreaCardService;
import com.cm.service.CardrederService;
import com.cm.service.StaticService;
import com.cm.service.SubstationService;
import com.cm.service.kafka.ConfigSyncThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.LogOut;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 读卡器管理
 * @author hetaiyun
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/cardreder")
public class CardrederController {
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private CardrederService service;
	
	@Autowired
	private SubstationService subService;
	
	@Autowired
	private StaticService staticService;
	
	@Autowired
	private AreaCardService as;
	
	/**
	 * 添加或者修改
	 * @param cardreder
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addCardreder(@RequestBody Cardreder cardreder,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (per != null)
			return per;
		
		try {
		     RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		
		try {
			if(cardreder.getCid()==0||cardreder.getCid()==255){
				return result.setStatus(-2, "设备编号不能为0或者255!");
			}
			if(cardreder.getPosition()==null||cardreder.getPosition()==""){
				return result.setStatus(-2, "位置不能为空或者空格!");
			}
			Static ps  = staticService.getByPosition(cardreder.getPosition());
			if(ps==null){
				Static position = staticService.addPosition(cardreder.getPosition());
				cardreder.setPosition_id(position.getId());
			}else{
				cardreder.setPosition_id(ps.getId());
			}
			
			Substation sub = subService.getSubbyid(cardreder.getSubstation_id());
			String a = sub.getIpaddr();
			String[] sub1 = a.split("\\.");
			String address = "G"+sub1[sub1.length-1]+"C"+cardreder.getCid();
			cardreder.setAddr(address);
			Substation station = subService.getSubbyid(cardreder.getSubstation_id());
			String ipaddr = station.getIpaddr();
			int i = ipaddr.lastIndexOf(".");
			String string = ipaddr.substring(i+1);
			String alais = "G"+string+"C"+cardreder.getCid();
			cardreder.setAlais(alais);
			if(cardreder.getId()>0){
				Cardreder c = service.getCardreder(cardreder);
				if(cardreder.getSubstation_id()!=c.getSubstation_id()||cardreder.getCid()!=c.getCid()){
					String isuse = subService.isuse(cardreder.getSubstation_id(), cardreder.getCid());
					if(isuse!=null){
							return result.setStatus(-2,isuse);
					}
				}
				cardreder.setUid("RD"+cardreder.getId()+cardreder.getAlais());
				service.updateCardreder(cardreder);
				//更新redis表
				RedisClient.getInstance().setAreaMap(as);
				RedisClient.getInstance().setExitCardreader(as);
				RedisClient.getInstance().setCardrederName(as);
			}else{
				String isuse = subService.isuse(cardreder.getSubstation_id(), cardreder.getCid());
				if(isuse!=null){
					return result.setStatus(-2,isuse);
				}
				Cardreder cardreder4 = service.addCardreder(cardreder);
				cardreder.setId(cardreder4.getId());
				cardreder.setUid("RD"+cardreder.getId()+cardreder.getAlais());
				service.updateCardreder(cardreder);
				Cardreder c = service.getCardreder(cardreder);
				if (null != c) {
					String value = "{\"id\":" + c.getId() + ",\"ip\":\"" + c.getSubname()
							+ "\",\"devid\":" + c.getCid() + ",\"type\":"
							+ c.getTypeid() + ",\"typename\":\"人员定位读卡器\"}";
					ConfigSyncThread.SendMessage(value);
				}
				//更新redis表
				RedisClient.getInstance().setAreaMap(as);
				RedisClient.getInstance().setExitCardreader(as);
				RedisClient.getInstance().setCardrederName(as);
				result.put("data", cardreder);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.info("异常：",e);
			LogOut.log.debug(e.getMessage());
			return result.setStatus(-4, "错误！");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有状态为在用和停用的读卡器
	 * @param cardreder
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="/getall",method=RequestMethod.POST)
	@ResponseBody
	public Object getallCardreder(@RequestBody Cardreder cardreder,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Cardreder> list = service.getallCardreder(cardreder);
			for(Cardreder c:list){
				c.setPath("people.svg");
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有读卡器 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getcardreders",method=RequestMethod.GET)
	@ResponseBody
	public Object getCardreders(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		try {
			List<Cardreder> list = service.getCardreders();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteCardreder(@RequestBody Cardreder cardreder,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (per != null)
			return per;
		try {
			Cardreder c = service.getById(cardreder.getId());
			if (null != c) {
				String value = "{\"id\":" + c.getId() + ",\"ip\":\"" + c.getSubname()
						+ "\",\"devid\":" + c.getCid() + ",\"type\":"
						+ c.getTypeid() + ",\"typename\":\"人员定位读卡器\",\"op\":\"del\"}";
				ConfigSyncThread.SendMessage(value);
			}
			service.delete(cardreder);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 验证读卡器通信地址是否已存在
	 * @param cardreder
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/validation",method=RequestMethod.POST)
	@ResponseBody
	public Object getCardreder(@RequestBody Cardreder cardreder,HttpServletRequest request){
		String car = subService.isuse(cardreder.getSubstation_id(), cardreder.getCid());
		if(car!=null){
			return result.setStatus(-2, "地址已存在");
		}else{
			return result.setStatus(0, "验证通过");
		}
	}
	
	/**
	 * 按分站查询读卡器并分组
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getCardrederbysub",method=RequestMethod.GET)
	@ResponseBody
	public Object getCardrederbysub(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			
			List<Substation> slist = subService.getAll();
			for(Substation s:slist){
				List<Cardreder> list = service.getCardrederbysub(s.getId());
					s.setCardreders(list);
			}
			result.put("data", slist);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
