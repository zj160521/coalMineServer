package com.cm.controller.extrun;

import java.util.Map;

public interface IRunCmd {

	public int init(Map<String,String> params);

	public int runCli();

	public Object doEnd();
	
	public String getCmdline();

}
