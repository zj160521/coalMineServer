package com.cm.controller;

import com.cm.entity.vo.KafkaMessage;
import com.cm.security.LoginManage;
import com.cm.service.kafka.KafkaMsgQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Scope
@Controller
@RequestMapping("/radio")
public class RadioOperationController {

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@RequestMapping(value="/operation",method=RequestMethod.POST)
	@ResponseBody
	public Object radioOperation(@RequestBody KafkaMessage message,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			if(null==message.getIp()||"".equals(message.getIp())||message.getId()<1||message.getType()<1){
				return result.setStatus(-3, "参数不正确");
			}
			message.setCmd(0x12);
			KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
			msgQueue.SendMessage(message);
			long sendtime = System.currentTimeMillis();
			long recvtime = System.currentTimeMillis();
			String msgid = message.getIp()+":"+message.getId()+"-"+message.getCmd();
			while((recvtime-sendtime)/1000<10){
				KafkaMessage recvMessage = msgQueue.RecvMessage(msgid);
				if(null!=recvMessage){
					if(recvMessage.getStatus()==0){
						return result.setStatus(0, "ok");
					}else{
						result.setStatus(recvMessage.getStatus(), recvMessage.getDesp());
					}
				}
				recvtime = System.currentTimeMillis();
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "服务器异常");
		}
		return result.setStatus(-5, "命令发送超时");
	}
}
