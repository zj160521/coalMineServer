package com.cm.dao;

import java.util.List;

import com.cm.entity.Config;

public interface IConfigDao {

	public void add(Config config);
	public void delete(String k);
	public void update(Config config);
	public String get(String key);
	public Config getConfig(String key);
	public List<Config> getConfigByV(String v);
}
