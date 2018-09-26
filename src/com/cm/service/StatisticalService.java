package com.cm.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.controller.ResultObj;
import com.cm.dao.IStatisticalDao;
import com.cm.entity.Moni;
import com.cm.entity.Statistical;
import com.cm.security.LoginManage;

@Service("statisticalService")
public class StatisticalService {

	@Autowired
	private IStatisticalDao sdao;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ResultObj result;

	public Object getAll(HttpServletRequest request) {
		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;
		
		try {
			List<Statistical> list = sdao.getAll();
			if (null == list || list.isEmpty() || list.size() < 1) {
				result.put("data", list);
				return result.setStatus(0, "no data");
			} else {
				result.put("data", list);
			}
			return result.setStatus(0, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}
	}
}
