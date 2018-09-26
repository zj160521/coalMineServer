package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.controller.extrun.IRunCmd;
import com.cm.controller.extrun.RunFFmpeg;
import com.cm.entity.NVR;
import com.cm.entity.Static;
import com.cm.security.LoginManage;
import com.cm.service.StaticService;
import com.cm.service.VideoService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("singleton")
@Controller
@RequestMapping("/video")
public class VideoController {
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private StaticService staticService;

	/**
	 * 查询所有NVR和摄像头
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<NVR> all = videoService.getAll();
			result.put("data", all);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	/**
	 * 添加更新NVR
	 * @param nvr
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addup",method=RequestMethod.POST)
	@ResponseBody
	public Object addup(@RequestBody NVR nvr,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			Static<T> static1 = staticService.getByPosition(nvr.getPosition());
			if(static1!=null){
				nvr.setPositionId(static1.getId());
			}else{
				nvr.setPositionId(staticService.addPosition(nvr.getPosition()).getId());
			}
			if(null!=nvr.getId()&&nvr.getId()>0){
				videoService.updateNVR(nvr);
				String remark = JSONObject.toJSONString(nvr);
				String operation2 = "修改视频监控配置";
				loginManage.addLog(request, remark, operation2, 301);
			}else{
				videoService.addNVR(nvr);
				String remark = JSONObject.toJSONString(nvr);
				String operation2 = "增加视频监控配置";
				loginManage.addLog(request, remark, operation2, 301);
			}
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
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteNVR(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if(null != per)
			return per;
		try {
			videoService.delete(id);
            NVR nvr = videoService.getById(id);
            String remark = JSONObject.toJSONString(nvr);
			String operation2 = "删除视频监控配置";
			loginManage.addLog(request, remark, operation2, 302);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	private Map<Integer, IRunCmd> runMap = new HashMap<Integer, IRunCmd>(); 
	
	/**
	 * 启动播放
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/play/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object playById(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		/*Object per = loginManage.checkPermission(request);
		if(null != per)
			return per;*/
		
		String name = "v_" + id;
		
		IRunCmd runcmd = runMap.get(id);
		if (runcmd != null) {
			result.put("video", "hls/" + name);
			return result.setStatus(0, "ok");
		}
		
		try {
			NVR nvr = videoService.getById(id);
			if (nvr == null) {
				return result.setStatus(-3, "没找到摄像机");
			}
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("user", nvr.getUsername());
			params.put("password", nvr.getPassword());
			params.put("ip", nvr.getIp());
			if (nvr.getPort() <= 0){
				params.put("port", "554");
			}else{
				params.put("port", "" + nvr.getPort());
			}
			params.put("channel", ""+nvr.getAccnum());
			params.put("name", name);
			
			runcmd = new RunFFmpeg();
			if (runcmd.init(params) != 0) {
				return result.setStatus(-3, "初始化失败");
			}
			
			if (runcmd.runCli() != 0) {
				return result.setStatus(-3, "启动直播失败");
			}
			
			runMap.put(id, runcmd);
			
			result.put("video", "hls/"+name);
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}

		return result.setStatus(0, "ok");
	}
	
	/**
	 * 停止播放
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/stop/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object stopPlay(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if(null != per)
			return per;
		
		IRunCmd runcmd = runMap.get(id);
		if (runcmd != null) {
			runcmd.doEnd();
			runMap.remove(id);
			return result.setStatus(0, "ok");
		} else {
			return result.setStatus(-3, "没找到对应直播");
		}
	}
}
