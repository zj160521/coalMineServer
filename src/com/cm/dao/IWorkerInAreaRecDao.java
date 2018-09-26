package com.cm.dao;

import java.util.LinkedList;
import java.util.List;

import com.cm.entity.CardWorker;
import com.cm.entity.Worker;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.AreaPass;
import com.cm.entity.vo.Searchform;

public interface IWorkerInAreaRecDao {
	public void addRec(WorkerInAreaRec w);
	
	public List<CardWorker> getCradWorker();
	
	public List<WorkerInAreaRec> getDailyRecByWorker(int workerId, String startTime, String endTime);
	
	public List<WorkerInAreaRec> getDailyRecByCard(String cardId, String startTime, String endTime);
	
	public List<AreaPass> getDailyRecs(Searchform searchform);
	
	public LinkedList<WorkerInAreaRec> getRecentlyInMineRec(int workerId, String time);
	
	public LinkedList<WorkerInAreaRec> getRecentlyInMineRec2(int cardId, String time);
	
	public List<Worker> getWorkerName();
	
	public List<Integer> getCard(String sTime, String eTime);
}
