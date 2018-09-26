package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.Coalmine_routeVo;
import com.cm.entity.vo.Searchform;

public interface PassAreaDao {

	public List<Coalmine_routeVo> getCardReaderRecord(Searchform searchform);
}
