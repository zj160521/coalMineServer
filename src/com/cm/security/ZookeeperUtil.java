package com.cm.security;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class ZookeeperUtil {
	
	private static String server ;
	
	private static final int SESSION_TIMEOUT = 3000;
	
	private static final String PATH = "/configsync";
	
	private static CuratorFramework curator;
	
	//创建zookeeper连接
	public static void createConnection(){
		server = getZookeeperAddr();
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 5);
		curator = CuratorFrameworkFactory.builder()
				.connectString(server)
				.sessionTimeoutMs(SESSION_TIMEOUT)
				.retryPolicy(retryPolicy).build();
		curator.start();
	}
	
	//关闭连接
	public static void closeConnection(){
		curator.close();
	}
	
	//判断节点是否存在
	public static boolean existsZnode(){
		try {
			Stat stat = curator.checkExists().forPath(PATH);
			if(null == stat){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	//创建节点
	public static void createZnode(){
		try {
			curator.create().withMode(CreateMode.EPHEMERAL).forPath(PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删除节点
	public static void deleteZnode(){
		try {
			curator.delete().forPath(PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getZookeeperAddr(){
		ConfigUtil cfg = ConfigUtil.getInstance();
		return cfg.getZookeeper();
	}
}
