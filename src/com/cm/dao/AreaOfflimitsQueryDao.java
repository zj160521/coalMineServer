package com.cm.dao;

import java.util.List;

import com.cm.entity.AreaWorker;
import com.cm.entity.vo.AreaOfflimitsVo;
import com.cm.entity.vo.Searchform;

public interface AreaOfflimitsQueryDao {

	public List<AreaOfflimitsVo> getData(Searchform searchform);
	public List<AreaWorker> getAllAreaWorker();
}
