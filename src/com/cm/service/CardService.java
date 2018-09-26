package com.cm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.controller.ResultObj;
import com.cm.dao.CardDao;
import com.cm.entity.Card;
import com.cm.entity.Statistical;
import com.cm.security.LoginManage;

@Service("cardService")
public class CardService {

	@Autowired
	private ResultObj result;
	@Autowired
	private CardDao cdao;
	
	public List<Card> getAll(Card card){
		return cdao.getAll(card);
	}
}
