package com.cm.controller;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class ResultObj {

	private HashMap<String, Object> result = new HashMap<String,Object>();

	public ResultObj() {
		result.put("status", 0);
	}
	
	ResultObj(int status, String msg) {
		result.put("status", status);
		result.put("msg", msg);
	}
	
	public synchronized HashMap<String, Object> getResult() {
		return result;
	}

	public synchronized HashMap<String, Object> put(String key, Object val) {
		result.put(key, val);
		return result;
	}
	
	public synchronized void put(HashMap<String, Object> map) {
		result.putAll(map);
	}
	
	public synchronized HashMap<String, Object> setStatus(int status, String msg) {
		result.put("status", status);
		result.put("msg", msg);
		return result;
	}
	
	public synchronized void clean(){
		result.clear();
	}
	
}
