package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.Searchform;

public interface IWorkerWarningFactory {

	public List<?> warningQuery(Searchform searchform);
}
