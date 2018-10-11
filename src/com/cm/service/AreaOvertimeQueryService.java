package com.cm.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.UtilMethod;

import com.cm.dao.AreaOvertimeQueryDao;
import com.cm.dao.IWorkerWarningFactory;
import com.cm.entity.vo.AreaLimitTimeVo;
import com.cm.entity.vo.AreaOfflimitsVo;
import com.cm.entity.vo.LongStringVo;
import com.cm.entity.vo.Searchform;
import com.cm.entity.vo.TimeInAreaVo;

/**
 * 
 */
@Service(value = "AreaOvertimeQueryService")
public class AreaOvertimeQueryService implements IWorkerWarningFactory {

	@Autowired
	private AreaOvertimeQueryDao dao;

	public List<?> warningQuery(Searchform searchform) {

		try {
			List<TimeInAreaVo> resultList = new ArrayList<TimeInAreaVo>();

			List<AreaOfflimitsVo> list = dao.getData(searchform);

			Map<Integer, Integer> map = getAreaLimitTime();

			if (list != null && list.size() > 0) {
				List<List<AreaOfflimitsVo>> eachWorkerList = getEachWorkerList(list);

				for (List<AreaOfflimitsVo> aovoList : eachWorkerList) {

					List<List<AreaOfflimitsVo>> cutList = cutList(aovoList);
					List<TimeInAreaVo> timeInAreaList = getTimeInArea(cutList, searchform);
					for (TimeInAreaVo vo : timeInAreaList) {
						Integer max_time = map.get(vo.getArea_id()) * 60 * 1000;
						if (max_time < vo.getLongDuration()) {
							resultList.add(vo);
						}
					}
				}

				return resultList;
			}

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<Integer, Integer> getAreaLimitTime() {

		List<AreaLimitTimeVo> areaLimitTime = dao.getAreaLimitTime();

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (AreaLimitTimeVo vo : areaLimitTime) {
			map.put(vo.getArea_id(), vo.getMax_time());
		}

		return map;

	}

	public List<TimeInAreaVo> getTimeInArea(
			List<List<AreaOfflimitsVo>> cutList, Searchform searchform)
			throws ParseException {

		List<TimeInAreaVo> resultList = new ArrayList<TimeInAreaVo>();

		if (cutList != null && cutList.size() > 0) {

			List<AreaOfflimitsVo> firstList = cutList.get(0);
			
			List<AreaOfflimitsVo> startTimeList = isStartTime(firstList, searchform);
			
			int i = 0;
			for (List<AreaOfflimitsVo> listVo : cutList) {

				if (listVo != null && listVo.size() > 0) {
					AreaOfflimitsVo aovo = null;
					TimeInAreaVo tiav = new TimeInAreaVo();
					if(i == 0 && startTimeList != null && startTimeList.size() > 0){
						aovo = startTimeList.get(0);
						i++;
					}
					aovo = listVo.get(0);
					AreaOfflimitsVo lastVo = listVo.get(listVo.size() - 1);
					tiav.setArea_id(aovo.getArea_id());
					tiav.setStarttime(aovo.getResponsetime());
					tiav.setEndtime(lastVo.getResponsetime());
					tiav.setAreaname(aovo.getAreaname());
					tiav.setRfcard_id(aovo.getRfcard_id());
					tiav.setName(aovo.getName());
					tiav.setDepartname(aovo.getDepartname());
					tiav.setWorktypename(aovo.getWorktypename());

					LongStringVo longToTimeFormat = UtilMethod
							.longToTimeFormat(aovo.getResponsetime(),
									lastVo.getResponsetime());

					if (longToTimeFormat.getTime() < 1000)
						tiav.setWellduration("0 小时 00 分 01秒");
					else
						tiav.setWellduration(longToTimeFormat.getTimCast());

					tiav.setLongDuration(longToTimeFormat.getTime());

					resultList.add(tiav);
				}
			}
		}

		return resultList;
	}

	public List<AreaOfflimitsVo> isStartTime(List<AreaOfflimitsVo> firstList,
			Searchform searchform) throws ParseException {

		AreaOfflimitsVo areaOfflimitsVo = firstList.get(0);
		
		String responsetime = areaOfflimitsVo.getResponsetime();
		LongStringVo longToTimeFormat = UtilMethod.longToTimeFormat(
				responsetime, searchform.getStarttime());
		if (longToTimeFormat.getTime() <= 60000) {
			searchform.setRfcard_id(areaOfflimitsVo.getRfcard_id());
			searchform.setArea_id(areaOfflimitsVo.getArea_id());
			
			List<AreaOfflimitsVo> beforeData = dao.getBeforeData(searchform);
			
			if(beforeData != null && beforeData.size() > 0){
				List<List<AreaOfflimitsVo>> cutLists = cutList(beforeData);
				List<AreaOfflimitsVo> list = cutLists.get(cutLists.size()-1);
				
				return list;
			}
			
			
		}
		
		return null;

	}

	public List<List<AreaOfflimitsVo>> getEachWorkerList(
			List<AreaOfflimitsVo> list) {

		List<List<AreaOfflimitsVo>> ll = new ArrayList<List<AreaOfflimitsVo>>();

		int cardId = 0;
		int i = 0;
		List<AreaOfflimitsVo> resultList = null;
		for (AreaOfflimitsVo aovo : list) {

			if (cardId == 0) {
				cardId = aovo.getRfcard_id();
				resultList = new ArrayList<AreaOfflimitsVo>();
				resultList.add(aovo);
			} else if (cardId == aovo.getRfcard_id()) {
				resultList.add(aovo);
			} else {
				ll.add(resultList);
				cardId = aovo.getRfcard_id();
				resultList = new ArrayList<AreaOfflimitsVo>();
				resultList.add(aovo);
			}
			i++;
			if (i == list.size())
				ll.add(resultList);
		}
		return ll;
	}

	public List<List<AreaOfflimitsVo>> cutList(List<AreaOfflimitsVo> list)
			throws ParseException {

		int area_id = 0;
		List<AreaOfflimitsVo> listvo = null;
		List<List<AreaOfflimitsVo>> resultList = new ArrayList<List<AreaOfflimitsVo>>();

		for (int i = 0; i < list.size(); i++) {

			list.get(i).setResponsetime(
					list.get(i).getResponsetime().substring(0, 19));

			if (area_id == 0) {
				area_id = list.get(i).getArea_id();
				listvo = new ArrayList<AreaOfflimitsVo>();
				listvo.add(list.get(i));
			} else if (area_id == list.get(i).getArea_id()) {
				listvo.add(list.get(i));
			} else {
				resultList.add(listvo);
				area_id = list.get(i).getArea_id();
				listvo = new ArrayList<AreaOfflimitsVo>();
				listvo.add(list.get(i));
			}

			if (i == list.size() - 1) {
				resultList.add(listvo);
			}
		}

		return resultList;
	}
}