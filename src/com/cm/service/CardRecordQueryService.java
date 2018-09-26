package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.controller.ResultObj;
import com.cm.dao.CardRecordQueryDao;
import com.cm.entity.vo.CardBattaryRecordVo;
import com.cm.entity.vo.Searchform;

@Service("CardRecordQueryService")
public class CardRecordQueryService implements CardRecordQueryDao{

	@Autowired
	private ResultObj result;
	@Autowired
	private CardRecordQueryDao cardRecordQueryDao;

	public List<CardBattaryRecordVo> getCardReaderRecord(Searchform searchform) {
		
		List<CardBattaryRecordVo>  list= cardRecordQueryDao.getCardReaderRecord(searchform);
		
		return list;
	}
}