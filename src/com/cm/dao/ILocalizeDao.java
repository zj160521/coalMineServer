package com.cm.dao;

import java.util.ArrayList;
import java.util.List;

import com.cm.entity.Localize;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.Searchform;

public interface ILocalizeDao {

	public List<Localize> getAll(Searchform searchform);

	public List<Localize> getAllAtGate(Localize l);
	
	public List<Localize> getAllandCardreder(Localize localize);
	
	public List<WorkerInAreaRec> getWorkerInOutRecord(String rfId, String endTime);
	
	public List<WorkerInAreaRec> getStarttimeInOutRecord(String rfId, String stratTime);
	
	public List<WorkerInAreaRec> getRecordsBTWStartAndEnd(String stratTime, String endTime);
	
	public WorkerInAreaRec getOutRecord(String rfId, String endTime);
	
	public List<WorkerInAreaRec> getAllWorkerInOutRecord(String rfId);
	
	public List<Localize> getOutWorker(Searchform searchform); 
	
	public List<Localize> getAreaAll(Searchform searchform);
	
	public ArrayList<WorkerInAreaRec> getAllworkerInAreaRec();

}
