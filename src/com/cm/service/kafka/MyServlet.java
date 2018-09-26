package com.cm.service.kafka;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

	private KafkaRecvThread recvThread;
	
	private KafkaSendThread sendThread;
	
	private KafkaCleanThread cleanThread;
	
	private KafkaConsumerThread websoketThread;
	
	private AllConsumerThread consumerThread;
	
	private BeatsSendThread beatsSendThread;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void destroy() { 
		if(recvThread != null && recvThread.isInterrupted())
			recvThread.interrupt();
		if(sendThread != null && sendThread.isInterrupted())
			sendThread.interrupt();
		if(cleanThread != null && cleanThread.isInterrupted())
			cleanThread.interrupt();
		if(websoketThread != null && websoketThread.isInterrupted())
			websoketThread.interrupt();
		if(beatsSendThread != null && beatsSendThread.isInterrupted())
			beatsSendThread.interrupt();
		if(consumerThread != null && consumerThread.isInterrupted())
			consumerThread.interrupt();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void init() throws ServletException {
		String str = null;
		
		if(null == str && null == recvThread){
			recvThread = new KafkaRecvThread();
			recvThread.start();
		}
		if(null == str && null == sendThread){
			sendThread = new KafkaSendThread();
			sendThread.start();
		}
		if(null == str && null ==cleanThread){
			cleanThread = new KafkaCleanThread();
			cleanThread.start();
		}
		if(null == str && null ==websoketThread){
			websoketThread = new KafkaConsumerThread();
			websoketThread.start();
		}
		if(null == str && null ==beatsSendThread){
			beatsSendThread = new BeatsSendThread();
			beatsSendThread.start();
		}
		if(null == str && null ==consumerThread){
			consumerThread = new AllConsumerThread();
			consumerThread.start();
		}
	}

}
