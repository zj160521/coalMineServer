package com.cm.service.kafka;

import org.springframework.stereotype.Component;

import util.LogOut;

@Component
public class KafkaCleanThread extends Thread {

	@Override
	public void run() {
		LogOut.log.debug("KafkaCleanThread Start");
	
		KafkaMsgQueue messageObj = KafkaMsgQueue.getInstance();
		
		while(!messageObj.isKafkaStop()) {
			try {
				messageObj.clean();
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LogOut.log.debug("KafkaCleanThread Stop");
		
		super.run();
	}
}
