package com.cm.security;

public class SqlDay {
	// 定义了一个ThreadLocal变量，用来保存int或Integer数据
	private ThreadLocal<String> day = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return "";
		}
	};

	public String get() {
		return day.get();
	}
	
	public void set(String i) {
		day.set(i);
	}
	
}
