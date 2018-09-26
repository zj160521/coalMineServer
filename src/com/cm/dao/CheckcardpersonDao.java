package com.cm.dao;

import java.util.List;

import com.cm.entity.Checkcardperson;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.WorkerTrackRecord;
import com.cm.entity.vo.CheckcardpersonVo;

public interface CheckcardpersonDao {
	
	public List<Checkcardperson> getall(CheckcardpersonVo checkcardpersonVo);
	
	public List<WorkerTrackRecord> getallbycard(CheckcardpersonVo checkcardpersonVo);
	
	public List<WorkerInAreaRec> getData(CheckcardpersonVo vo);
}
