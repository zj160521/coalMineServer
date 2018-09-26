package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IConfigDao;
import com.cm.entity.Config;

@Service("ConfigService")
public class ConfigService implements IConfigDao {

	@Autowired
	private IConfigDao cfgDao;

	@Override
	public void add(Config config) {
		cfgDao.add(config);
	}

	@Override
	public void delete(String k) {
		cfgDao.delete(k);
	}

	@Override
	public void update(Config config) {
		cfgDao.update(config);
	}

	@Override
	public String get(String key) {
		
		try {
			return cfgDao.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Config getConfig(String key) {
		try {
			return cfgDao.getConfig(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Config> getConfigByV(String V){
		try {
			return cfgDao.getConfigByV(V);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
