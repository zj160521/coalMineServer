package com.cm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import util.GetHostName;

import com.cm.entity.Config;
import com.cm.service.ConfigService;

public class HostInit {
	@Autowired
	private ConfigService cfgService;

	public void initialized() {
		List<Config> configByV = cfgService.getConfigByV("hostName");
		if (configByV == null || configByV.size() == 0 ||
				(configByV.size() == 1 && !configByV.get(0).getK().equals(GetHostName.getHostName()))) {
			Config cfg = new Config();
			cfg.setK(GetHostName.getHostName());
			cfg.setV("hostName");
			cfgService.add(cfg);
		}
	}
}
