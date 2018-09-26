package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.CardReaderREC;
import com.cm.entity.vo.Searchform;

public interface ICardReaderPassQryDao {

	public List<CardReaderREC> getStepInRecs(Searchform searchform);
}
