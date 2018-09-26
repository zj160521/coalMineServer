package com.cm.service.kafka;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ThreadStart extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private BeatsRecvThread beatsRecvThread;
	
	private ConfigSyncThread configThread;

	public void destroy() {
		if(beatsRecvThread != null && beatsRecvThread.isInterrupted())
			beatsRecvThread.interrupt();
		if(configThread!=null&&configThread.isInterrupted()){
			configThread.interrupt();
		}
		
	}

	public void init() throws ServletException {
		String str = null;
		if(null == str && null ==beatsRecvThread){
			beatsRecvThread = new BeatsRecvThread();
			beatsRecvThread.start();
		}
		if(null == str && null == configThread){
			configThread = new ConfigSyncThread();
			configThread.start();
		}


    }

}
