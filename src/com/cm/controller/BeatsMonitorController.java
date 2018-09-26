package com.cm.controller;

import com.cm.controller.extrun.IRunCmd;
import com.cm.controller.extrun.RunFindMaster;
import com.cm.entity.vo.BeatMssage;
import com.cm.security.ConfigUtil;
import com.cm.security.LoginManage;
import com.cm.service.kafka.BeatsRecvThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.LogOut;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Scope("singleton")
@Controller
@RequestMapping("/monitor")
public class BeatsMonitorController {
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;

	@RequestMapping(value="/beats",method=RequestMethod.GET)
	@ResponseBody
	public Object beatsMonitor(){
		try {
			int tomcatCount = 0;
			int batchCount = 0;
			int realtimeCount = 0;
			List<BeatMssage> list = new ArrayList<BeatMssage>();
			HashMap<String,BeatMssage> map = BeatsRecvThread.getMap();
			Iterator<Entry<String, BeatMssage>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, BeatMssage> entry = it.next();
				BeatMssage mssage = entry.getValue();
				if ("tomcat".equals(mssage.getServicename())) {
					tomcatCount++;
				} else if ("batch_data_server".equals(mssage.getServicename())) {
					batchCount++;
				} else if ("realtime_data_server".equals(mssage.getServicename())) {
					realtimeCount++;
				}
				list.add(mssage);
			}
			
			if (tomcatCount == 0){
				BeatMssage beat = new BeatMssage();
				beat.setName("主应用服务");
				beat.setRemark("严重错误：主应用服务未运行");
				beat.setStatus(2);
				list.add(beat);
			} else if (tomcatCount == 1){
				for (BeatMssage mssage : list) {
					if("tomcat".equals(mssage.getServicename())){
						mssage.setName("主应用服务");
						mssage.setRemark("警告：主应用服务只有一个，双机热备未能生效");
						mssage.setStatus(1);
					}
				}
			} else if (tomcatCount >= 2){
				for (BeatMssage mssage : list) {
					if("tomcat".equals(mssage.getServicename())){
						mssage.setName("主应用服务");
						mssage.setRemark("主应用服务运行正常");
						mssage.setStatus(0);
					}
				}
			}
			
			if (batchCount == 0){
				BeatMssage beat = new BeatMssage();
				beat.setName("数据批处理服务");
				beat.setRemark("严重错误：数据批处理服务未运行");
				beat.setStatus(2);
				list.add(beat);
			} else if (batchCount == 1){
				for (BeatMssage mssage : list) {
					if("batch_data_server".equals(mssage.getServicename())){
						mssage.setName("数据批处理服务");
						mssage.setRemark("警告：数据批处理服务只有一个，双机热备未能运行");
						mssage.setStatus(1);
					}
				}
			} else if (batchCount >= 2){
				for (BeatMssage mssage : list) {
					if("batch_data_server".equals(mssage.getServicename())){
						mssage.setName("数据批处理服务");
						mssage.setRemark("数据批处理服务运行正常");
						mssage.setStatus(0);
					}
				}
			}
			
			if (realtimeCount == 0){
				BeatMssage beat = new BeatMssage();
				beat.setName("数据实时接收服务");
				beat.setRemark("严重错误：数据实时接收服务未运行");
				beat.setStatus(2);
				list.add(beat);
			} else if (realtimeCount == 1){
				for (BeatMssage mssage : list) {
					if("realtime_data_server".equals(mssage.getServicename())){			
						mssage.setName("数据实时接收服务");
						mssage.setRemark("警告：数据实时接收服务只有一个，双机热备未能生效");
						mssage.setStatus(1);
					}
				}
			} else if (realtimeCount >= 2){
				for (BeatMssage mssage : list) {
					if("realtime_data_server".equals(mssage.getServicename())){
						mssage.setName("数据实时接收服务");
						mssage.setRemark("数据实时接收服务运行正常");
						mssage.setStatus(0);
					}
				}
			}
			int warn = 0;
			for (BeatMssage mssage : list) {
				mssage.setResponsetime(currentTimeToString(mssage.getTime()));
				if(mssage.getStatus()!=0){
					warn += 1;
				}
				if(mssage.getStatus()==2){
					mssage.setResponsetime("");
				}
			}
			result.clean();
			IRunCmd runcmd = new RunFindMaster();
			Map<String,String> m=new HashMap<String,String>();
			if (runcmd.init(m) != 0) {
				result.put("remark", "查找命令初始化失败！");
			}else{
				if (runcmd.runCli() != 0) {
					result.put("remark", "查找命令运行失败！");
				}else{
					String r=(String) runcmd.doEnd();
					if(r.equals("-1")){
						result.put("remark", "查找命令回复出错！");
					}else if(r.equals("0")){
						String ma=ConfigUtil.getInstance().getSys_peerip();
						String pe=ConfigUtil.getInstance().getSys_ip();
                        getServerStatus(list, ma, pe);
						result.put("remark1", "主机名：h"+ma.split("\\.")[3]+" 主机ip："+ma);
						result.put("remark2", "备机名：h"+pe.split("\\.")[3]+" 备机ip："+pe);
					}else if(r.equals("1")){
						String pe=ConfigUtil.getInstance().getSys_peerip();
						String ma=ConfigUtil.getInstance().getSys_ip();
                        getServerStatus(list, ma, pe);
						result.put("remark1", "主机名：h"+ma.split("\\.")[3]+" 主机ip："+ma);
						result.put("remark2", "备机名：h"+pe.split("\\.")[3]+" 备机ip："+pe);
					}
				}
			}
			
			result.put("data", list);
			result.put("warn", warn);
		} catch (Exception e) {
			LogOut.log.debug(e.getMessage());
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	private String currentTimeToString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}

	private void getServerStatus(List<BeatMssage> list, String ma, String pe){
        for (BeatMssage mssage : list) {
            if (mssage.getIp().equals(ma)){
                mssage.setServer_status(1);
            } else  if (mssage.getIp().equals(pe)){
                mssage.setServer_status(2);
            } else {
                mssage.setServer_status(3);
            }
        }
    }
}
