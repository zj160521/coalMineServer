package com.cm.controller.extrun;

import java.io.IOException;
import java.util.Map;


public class RunFFmpeg extends RunCmdBase implements IRunCmd {

	String cmdline;
	
	public RunFFmpeg() {
	}
	
	public int init(Map<String,String> params) {
		
		try {
			cmdline = "ffmpeg -re -i rtsp://" + params.get("user") + 
					  ":" + params.get("password") +
					  "@" + params.get("ip") + 
					  ":" + params.get("port") +
					  "/Streaming/Channels/" + params.get("channel") +
					  "?transportmode=unicast -vcodec: copy -f flv rtmp://localhost:1935/hls/" + params.get("name");
//			LogOut.log.debug(cmdline+"------------------------------------------------------");
		} catch(Exception e) {
			e.printStackTrace();
			runlog.setOpresult(-1);
			return -1;
		}

		return 0;
	}

	public int runCli() {
		
		runlog.setOpcode(1);
		runlog.setOpcodetxt("启动摄像机直播");

		return super.runCmd(cmdline);
	}

	public Object doEnd() {
		
		killRun(process);
		waitFor(process);
		try {
			logRun(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCmdline() {
		return cmdline;
	}

}
