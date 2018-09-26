package com.cm.dao;

import java.util.List;

import com.cm.entity.vo.CardBattaryRecordVo;
import com.cm.entity.vo.Searchform;

public interface CardRecordQueryDao {

	public List<CardBattaryRecordVo> getCardReaderRecord(Searchform searchform);
}
