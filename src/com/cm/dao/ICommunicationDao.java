package com.cm.dao;

import java.util.List;

import com.cm.entity.Communication;

public interface ICommunicationDao {

	public List<Communication> getAll(Communication communication);
}
