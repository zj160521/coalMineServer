package com.cm.dao;

import java.text.ParseException;
import java.util.List;

import com.cm.entity.vo.CommunicationVo;
import com.cm.entity.vo.NameTime;

public interface CardReaderErrorQueryDao {

	public List<CommunicationVo> cardReaderErrorQuery(NameTime nameTime) throws ParseException;
	
	
}
