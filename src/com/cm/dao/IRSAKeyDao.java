package com.cm.dao;

import com.cm.entity.RsaKey;

public interface IRSAKeyDao {

	public void add(RsaKey rsa);
	public void delete(String filltime);
	public void update(RsaKey rsa);
	public String get(String key);
	public RsaKey getRSAKey(String key);
}
