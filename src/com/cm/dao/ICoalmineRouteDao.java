package com.cm.dao;

import java.util.List;


public interface ICoalmineRouteDao {

	public int getMaxPNByDay(String sTime, String eTime);
	public List<Integer> getWorkerIds(String sTime, String eTime);
}
