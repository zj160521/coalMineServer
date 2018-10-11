package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.Static;
import com.cm.entity.vo.SensorTypeVO;
import com.cm.security.LoginManage;
import com.cm.service.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/static")
public class StaticController {

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private StaticService staticService;
	
	/**
	 * 查询所有传感器
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/alltype", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllType(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		try {
			List<Static> list = staticService.getAllType();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有模拟量传感器设备类型
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/sensortype", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSensorType(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		List<Static> list = staticService.getAllSensorType();
		for (Static sta : list) {
			if(sta.getId()==41||sta.getId()==47||sta.getId()==49){
				sta.setHasfloor(1);
			}
		}
		result.put("data", list);
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有开关量传感器设备类型
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/switchsensor", method=RequestMethod.GET)
	@ResponseBody
	public Object getAllSwitchSensorType(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		List<Static> list = staticService.getAllSwitchSensorType();
		if(null==list||list.isEmpty()||list.size()<1){
			result.put("data", list);
			return result.setStatus(0, "no data");
		}
		for (Static st : list) {
			if(st.getId()==53){
				st.setHasfloor(1);
			}
			if(st.getId()==54){
				st.setHasfloor(2);
			}
			if(st.getId()==56){
				st.setHasfloor(3);
			}
		}
		result.put("data", list);
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有传感器位置信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/sensorposition", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSensorPostion(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		List<Static> list = staticService.getAllSensorPosition();
		if(null==list||list.isEmpty()||list.size()<1){
			result.put("data", list);
			return result.setStatus(0, "no data");
		}else{
			result.put("data", list);
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有传感器单位
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/sensorunit", method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSensorUnit(HttpServletRequest request){
		/*if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}*/
		List<Static> list = staticService.getAllSensorUnit();
		if(null==list||list.isEmpty()||list.size()<1){
			result.put("data", list);
			return result.setStatus(0, "no data");
		}else{
			result.put("data", list);
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有显示信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/display",method = RequestMethod.GET)
	@ResponseBody
	public Object getAlldisplay(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Static> list = staticService.getAlldisplay();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有传感器类型下的具体传感器
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sensoroftype",method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSensorOfType(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<SensorTypeVO> list = staticService.getAllSensorOfType();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 查询所有传感器类型下的具体开关量传感器
	 * @return
	 */
	@RequestMapping(value="/switchoftype",method = RequestMethod.GET)
	@ResponseBody
	public Object getAllSwitchSensorOfType(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<SensorTypeVO> list = staticService.getAllSwitchSensorOfType();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "excrption");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 
	 * 数据字典
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/datamap",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllDataMap(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Static<Static>> list = staticService.getAllDataMap();
			Iterator<Static<Static>> it = list.iterator();
			while(it.hasNext()){
				Static<Static> next = it.next();
				if("传感器单位".equals(next.getV())){
					it.remove();
					continue;
				}
				if("开关量传感器状态显示".equals(next.getV())){
					it.remove();
					continue;
				}
				if("传感器类型".equals(next.getV())){
					List<Static> lists = next.getLists();
					Iterator<Static> it2 = lists.iterator();
					while(it2.hasNext()){
						Static next2 = it2.next();
						if("人员定位读卡器".equals(next2.getV())){
							it2.remove();
							continue;
						}
						if("广播".equals(next2.getV())){
							it2.remove();
							continue;
						}
						if("摄像头".equals(next2.getV())){
							it2.remove();
							continue;
						}
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
	 * 添加数据字典
	 * @param sta
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addDataMap(@RequestBody Static sta,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if(null!=per) return per;
		try {
            if(null == sta.getV() || "".equals(sta.getV())){
                return result.setStatus(-4,"请输入正确的信息");
            }
            if (sta.getId() > 0){
                staticService.update(sta);
                String remark = JSONObject.toJSONString(sta);
                String operation2 = "修改数据字典";
                loginManage.addLog(request, remark, operation2, 127);
            } else {
                Static stac = staticService.getByPosition(sta.getV());
                if(null == stac){
                    staticService.addDataMap(sta);
                }
                String remark = JSONObject.toJSONString(sta);
                String operation2 = "增加数据字典";
                loginManage.addLog(request, remark, operation2, 127);
            }
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object delete(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
            Static aStatic = staticService.getPositionByid(id);
            String remark = JSONObject.toJSONString(aStatic);
            String operation2 = "删除数据字典信息";
            loginManage.addLog(request, remark, operation2, 128);
            staticService.delete(id);
        } catch (Exception e) {
			e.printStackTrace();
            return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 所有数据字典信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getdata",method=RequestMethod.GET)
	@ResponseBody
	public Object getalldata(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Static> list = staticService.getAllSensorType();
			List<Static> list2 = staticService.getAllSwitchSensorType();
			List<Static> list3 = staticService.getAllSensorPosition();
			List<Static> list4 = staticService.getallduty();
			List<Static> list5 = staticService.getallWorkplace();
            List<Static> list6 = staticService.getAllRadio();
            result.put("sensor", list);
			result.put("switchsensor", list2);
			result.put("sensorposition", list3);
			result.put("duty", list4);
			result.put("workplace", list5);
            result.put("radio", list6);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/encode", method = RequestMethod.POST)
    @ResponseBody
    public Object addEncode(@RequestBody Static sta,HttpServletRequest request){
	    if(!loginManage.isUserLogin(request)){
	        return result.setStatus(-1,"no login");
        }
        try{
            Static stat = staticService.getPositionByid(8);
            if(null!=stat){
                staticService.update(sta);
            } else {
                staticService.addK(sta);
            }
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
	    return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/areatype", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllAreaType(HttpServletRequest request){
	    if(!loginManage.isUserLogin(request)){
	        return result.setStatus(-1, " no login");
        }
        try{
            List<Static> allAreaType = staticService.getAllAreaType();
            result.clean();
            result.put("data", allAreaType);
        } catch (Exception e){
            e.printStackTrace();
        }
	    return result.setStatus(0, "ok");
    }
}