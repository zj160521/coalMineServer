package com.cm.dao;

import java.util.List;

import com.cm.entity.Card;

public interface CardDao {

	public List<Card> getAll(Card card);
}
