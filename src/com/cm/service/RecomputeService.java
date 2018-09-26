package com.cm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.RecomputeDao;
import com.cm.entity.Coalmine_route;
import com.cm.entity.WorkerInAreaRec;

@Service
public class RecomputeService implements RecomputeDao {

	@Autowired
	private RecomputeDao dao;

	@Override
	public List<Coalmine_route> getReimport() {
		return dao.getReimport();
	}

	@Override
	public List<Coalmine_route> getCardGroup() {
		return dao.getCardGroup();
	}

	@Override
	public List<Coalmine_route> getDayRoute(Map<String, String> map) {
		return dao.getDayRoute(map);
	}

	@Override
	public void addAll(List<WorkerInAreaRec> list) {
		dao.addAll(list);
	}

	@Override
	public void del(String time, int card) {
		dao.del(time, card);
	}

	@Override
	public void addold(Map<String, String> map) {
		dao.addold(map);
	}

	@Override
	public List<Coalmine_route> getReimportByTime() {
		return dao.getReimportByTime();
	}

	@Override
	public void reimportDel() {
		dao.reimportDel();
	}
	
}
