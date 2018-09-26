package com.cm.dao;

import java.util.List;
import java.util.Map;

import com.cm.entity.Coalmine_route;
import com.cm.entity.WorkerInAreaRec;

public interface RecomputeDao {
	public List<Coalmine_route> getReimportByTime();
	public List<Coalmine_route> getReimport();
	public List<Coalmine_route> getCardGroup();
	public List<Coalmine_route> getDayRoute(Map<String,String> map);
	public void addAll(List<WorkerInAreaRec> list);
	public void del(String time,int card);
	public void addold(Map<String,String> map);
	public void reimportDel();
}
