package com.cm.controller;

import com.cm.entity.Localize;
import com.cm.entity.vo.LocalizeVo;
import com.cm.entity.vo.Searchform;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.PageHelper;
import util.SearchformCheck;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Scope("prototype")
@Controller
@RequestMapping("/localize")
public class LocalizeController {

	@Autowired
	private LocalService localService;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ResultObj result;

	@RequestMapping(value = "/dayQueryWell", method = RequestMethod.POST)
	@ResponseBody
	public Object getAllAream(@RequestBody Searchform searchform,
			HttpServletRequest request) {
		// 测试用代码
		// Searchform searchform = new Searchform();
		// searchform.setStarttime("2017-12-30 19:55:56");
		// searchform.setEndtime("2017-12-31 19:55:56");
		// searchform.setRadio(1);
		// searchform.setCur_page(1);
		// searchform.setPage_rows(20);
		// // searchform.setDepart_id(32);

		if (SearchformCheck.searchformCheck(searchform, result))
			result.setStatus(-2, "请选择时间！");
		List<LocalizeVo> resultList = null;
		List<Localize> resultList2 = null;
		try {
			if (searchform.getRadio() == 1) {
				resultList = localService.getAllAream(searchform);
				return PageHelper.controllerReturnMethod(resultList,
						searchform, result);
			} else if (searchform.getRadio() == 2) {
				resultList2 = localService.getAllAream2(searchform);
				return PageHelper.controllerReturnMethod(resultList2,
						searchform, result);
			}

			return result.setStatus(-2, "请选择出入井！");
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}

	}

	@RequestMapping(value = "/passPoint", method = RequestMethod.POST)
	@ResponseBody
	public Object areaQuery(
	 @RequestBody Searchform searchform,
			HttpServletRequest request) {

		// 测试用代码
//		Searchform searchform = new Searchform();
//		searchform.setStarttime("2018-03-07 19:55:56");
//		searchform.setEndtime("2018-03-08 19:55:56");
//		// searchform.setRadio(1);
//		searchform.setCur_page(1);
//		searchform.setPage_rows(10);
		// // searchform.setRfcard_id(56);
		// // searchform.setArea_id(area_id);

		if (SearchformCheck.searchformCheck(searchform, result))
			result.setStatus(-2, "请选择时间！");

		try {

			List<Localize> resultList = localService.areaQuery(searchform);

			return PageHelper.controllerReturnMethod(resultList, searchform,
					result);

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}
	}

	@RequestMapping(value = "/getNowInArea", method = RequestMethod.GET)
	@ResponseBody
	public Object getNowInArea(HttpServletRequest request,
			@RequestBody Localize localize) {
		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;
		Object per = loginManage.checkPermission(request);
		if (per != null)
			return per;
		
		try {
		     RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		
		try {
			// 查询出在出入口位置的人员
			Localize l = new Localize();
			l.setArea_id(0);
			List<Localize> tlist = localService.getAllAtGate(l);

			Map<String, String> map = RedisClient.getInstance().getWorkerInAreaTable();
			List<String> cards = new ArrayList<String>();
			for (Entry<String, String> e : map.entrySet()) {
				String areaDev = e.getValue();
				String[] areaDevid = areaDev.split(":");
				if (!areaDevid[0].equals("0")) {
					cards.add(e.getKey());
				}
			}
			List<Localize> Llist = new ArrayList<Localize>();
			if (cards.size() > 0) {
				for (String card : cards) {
					for (Localize ll : tlist) {
						if (Integer.parseInt(card) == ll.getRfcard_id()) {
							Llist.add(ll);
							break;
						}
					}
				}
			}

			if (Llist == null || Llist.isEmpty() || Llist.size() < 1) {
				result.put("data", new ArrayList<>());
				return result.setStatus(0, "ok");
			} else {
				result.put("data", Llist);
			}
			return result.setStatus(0, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "exception");
		}
	}

}