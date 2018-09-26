package com.cm.controller;

import redis.clients.jedis.Jedis;
import util.JedisUtil;

public class RedisOperation {

	public static void setHashValue(String key, String field, String value){
		Jedis jedis = JedisUtil.getJedis();
		jedis.hset(key, field, value);
	}
	
	public static String getHashValue(String key, String field){
		return (isExist(key, field)) ? JedisUtil.getJedis().hget(key, field) : null;
	}
	
	public static boolean isExist(String key, String field){
		Jedis jedis = JedisUtil.getJedis();
		return jedis.hexists(key, field) ? true : false;
	}
	
	public static void delete(String key){
		Jedis jedis = JedisUtil.getJedis();
		jedis.del(key);
	}
}
