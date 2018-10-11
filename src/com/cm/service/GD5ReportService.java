package com.cm.service;

import com.cm.dao.GD5ReportDao;
import com.cm.dao.ISubstationDao;
import com.cm.entity.GD5Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.UtilMethod;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GD5ReportService {

	@Autowired
	private GD5ReportDao dao;

	@Autowired
	private ISubstationDao stationDao;

	// 时报表
	@SuppressWarnings("rawtypes")
	public List<GD5Report> getHourReport(String startime, String endtime) {
        return dao.getAll(startime, endtime);
	}

	// 日报表
	@SuppressWarnings("rawtypes")
	public List<GD5Report> getDayReport(String startime, String endtime) {
        List<GD5Report> list = dao.getAll(startime, endtime);
        if(list.isEmpty()){
            return list;
        }
        List<GD5Report> gd5ReportList = new ArrayList<>();
        List<List<GD5Report>> reportList = new ArrayList<>();
        try{
            Map<String, List<GD5Report>> baseMap = getBaseMap(list);
            Calendar previous = Calendar.getInstance();
            Calendar next = Calendar.getInstance();
            Calendar center = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Map<Date, Date> map = getDate(startime, endtime, "yyyy-MM-dd", Calendar.DAY_OF_MONTH, 1);
            for (Map.Entry<Date, Date> entry : map.entrySet()) {
                previous.setTime(entry.getKey());
                next.setTime(entry.getValue());
                for (List<GD5Report> reports : baseMap.values()) {
                    List<GD5Report> gd5Reports = new ArrayList<>();
                    for (GD5Report report : reports) {
                        center.setTime(df.parse(report.getResponsetime()));
                        if (center.equals(previous) || (center.after(previous) && center.before(next))){
                            String responsetime = df.format(previous.getTime()) + "~" +df.format(next.getTime());
                            report.setResponsetime(responsetime);
                            gd5Reports.add(report);
                        }
                    }
                    reportList.add(gd5Reports);
                }
            }
            Iterator<List<GD5Report>> iterator = reportList.iterator();
            while (iterator.hasNext()){
                List<GD5Report> list1 = iterator.next();
                if (UtilMethod.notEmptyList(list1)){
                    GD5Report report = list1.get(0);
                    GD5Report report1 = new GD5Report();
                    report1.setPosition(report.getPosition());
                    report1.setResponsetime(report.getResponsetime());
                    report1.setIp(report.getIp());
                    report1.setType("GD多参数传感器");
                    report1.setDevid(report.getDevid());
                    report1.setFlow_standard(report.getFlow_standard());
                    report1.setFlow_pure(report.getFlow_pure());
                    report1.setFlow_work(report.getFlow_work());
                    double flow_work_sum = 0.0;
                    double flow_pure_sum = 0.0;
                    double flow_standard_sum = 0.0;
                    for (GD5Report gd5Report : list1) {
                        flow_pure_sum += gd5Report.getFlow_pure_sum();
                        flow_standard_sum += gd5Report.getFlow_standard_sum();
                        flow_work_sum += gd5Report.getFlow_work_sum();
                    }
                    report1.setFlow_standard_sum(flow_standard_sum);
                    report1.setFlow_pure_sum(flow_pure_sum);
                    report1.setFlow_work_sum(flow_work_sum);
                    report1.setTemperature(report.getTemperature());
                    report1.setPressure(report.getPressure());
                    report1.setWasi(report.getWasi());
                    report1.setCo(report.getCo());
                    gd5ReportList.add(report1);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return gd5ReportList;
	}

	// 旬报表
	@SuppressWarnings("rawtypes")
	public List<GD5Report> getXunReport(String startime, String endtime) {
		List<GD5Report> list = dao.getAll(startime, endtime);
		if(list.isEmpty()){
			return list;
		}
		List<GD5Report> gd5ReportList = new ArrayList<>();
		List<List<GD5Report>> reportList = new ArrayList<>();
		try{
            Map<String, List<GD5Report>> baseMap = getBaseMap(list);
            Calendar previous = Calendar.getInstance();
            Calendar next = Calendar.getInstance();
            Calendar center = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Map<Date, Date> map = getDate(startime, endtime, "yyyy-MM-dd", Calendar.DAY_OF_MONTH, 10);
            for (Map.Entry<Date, Date> entry : map.entrySet()) {
                previous.setTime(entry.getKey());
                next.setTime(entry.getValue());
                for (List<GD5Report> reports : baseMap.values()) {
                    List<GD5Report> gd5Reports = new ArrayList<>();
                    for (GD5Report report : reports) {
                        center.setTime(df.parse(report.getResponsetime()));
                        if (center.equals(previous) || (center.after(previous) && center.before(next))){
                            String responsetime = df.format(previous.getTime()) + "~" +df.format(next.getTime());
                            report.setResponsetime(responsetime);
                            gd5Reports.add(report);
                        }
                    }
                    reportList.add(gd5Reports);
                }
            }
            Iterator<List<GD5Report>> iterator = reportList.iterator();
            while (iterator.hasNext()){
                List<GD5Report> list1 = iterator.next();
                if (UtilMethod.notEmptyList(list1)){
                    GD5Report report = list1.get(0);
                    GD5Report report1 = new GD5Report();
                    report1.setPosition(report.getPosition());
                    report1.setResponsetime(report.getResponsetime());
                    report1.setIp(report.getIp());
                    report1.setType("GD多参数传感器");
                    report1.setDevid(report.getDevid());
                    report1.setFlow_standard(report.getFlow_standard());
                    report1.setFlow_pure(report.getFlow_pure());
                    report1.setFlow_work(report.getFlow_work());
                    double flow_work_sum = 0.0;
                    double flow_pure_sum = 0.0;
                    double flow_standard_sum = 0.0;
                    for (GD5Report gd5Report : list1) {
                        flow_pure_sum += gd5Report.getFlow_pure_sum();
                        flow_standard_sum += gd5Report.getFlow_standard_sum();
                        flow_work_sum += gd5Report.getFlow_work_sum();
                    }
                    report1.setFlow_standard_sum(flow_standard_sum);
                    report1.setFlow_pure_sum(flow_pure_sum);
                    report1.setFlow_work_sum(flow_work_sum);
                    report1.setTemperature(report.getTemperature());
                    report1.setPressure(report.getPressure());
                    report1.setWasi(report.getWasi());
                    report1.setCo(report.getCo());
                    gd5ReportList.add(report1);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return gd5ReportList;
	}

	// 月报表
	@SuppressWarnings("rawtypes")
	public List<GD5Report> getMonthReport(String startime, String endtime) {
        List<GD5Report> list = dao.getAll(startime, endtime);
        if(list.isEmpty()){
            return list;
        }
        List<GD5Report> gd5ReportList = new ArrayList<>();
        List<List<GD5Report>> reportList = new ArrayList<>();
        try{
            Map<String, List<GD5Report>> baseMap = getBaseMap(list);
            Calendar previous = Calendar.getInstance();
            Calendar next = Calendar.getInstance();
            Calendar center = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            Map<Date, Date> map = getDate(startime, endtime, "yyyy-MM", Calendar.MONTH, 1);
            for (Map.Entry<Date, Date> entry : map.entrySet()) {
                previous.setTime(entry.getKey());
                next.setTime(entry.getValue());
                for (List<GD5Report> reports : baseMap.values()) {
                    List<GD5Report> gd5Reports = new ArrayList<>();
                    for (GD5Report report : reports) {
                        center.setTime(df.parse(report.getResponsetime()));
                        if (center.equals(previous) || (center.after(previous) && center.before(next))){
                            String responsetime = df.format(previous.getTime()) + "~" +df.format(next.getTime());
                            report.setResponsetime(responsetime);
                            gd5Reports.add(report);
                        }
                    }
                    reportList.add(gd5Reports);
                }
            }
            Iterator<List<GD5Report>> iterator = reportList.iterator();
            while (iterator.hasNext()){
                List<GD5Report> list1 = iterator.next();
                if (UtilMethod.notEmptyList(list1)){

                    GD5Report report = list1.get(0);
                    GD5Report report1 = new GD5Report();
                    report1.setPosition(report.getPosition());
                    report1.setResponsetime(report.getResponsetime());
                    report1.setIp(report.getIp());
                    report1.setType("GD多参数传感器");
                    report1.setDevid(report.getDevid());
                    report1.setFlow_standard(report.getFlow_standard());
                    report1.setFlow_pure(report.getFlow_pure());
                    report1.setFlow_work(report.getFlow_work());
                    double flow_work_sum = 0.0;
                    double flow_pure_sum = 0.0;
                    double flow_standard_sum = 0.0;
                    for (GD5Report gd5Report : list1) {
                        flow_pure_sum += gd5Report.getFlow_pure_sum();
                        flow_standard_sum += gd5Report.getFlow_standard_sum();
                        flow_work_sum += gd5Report.getFlow_work_sum();
                    }
                    report1.setFlow_standard_sum(flow_standard_sum);
                    report1.setFlow_pure_sum(flow_pure_sum);
                    report1.setFlow_work_sum(flow_work_sum);
                    report1.setTemperature(report.getTemperature());
                    report1.setPressure(report.getPressure());
                    report1.setWasi(report.getWasi());
                    report1.setCo(report.getCo());
                    gd5ReportList.add(report1);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return gd5ReportList;
	}

	// 季报表
	@SuppressWarnings("rawtypes")
	public List<GD5Report> getQuarterReport(String startime, String endtime) {
        List<GD5Report> list = dao.getAll(startime, endtime);
        if(list.isEmpty()){
            return list;
        }
        List<GD5Report> gd5ReportList = new ArrayList<>();
        List<List<GD5Report>> reportList = new ArrayList<>();
        try{
            Map<String, List<GD5Report>> baseMap = getBaseMap(list);
            Calendar previous = Calendar.getInstance();
            Calendar next = Calendar.getInstance();
            Calendar center = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            Map<Date, Date> map = getDate(startime, endtime, "yyyy-MM", Calendar.MONTH, 3);
            for (Map.Entry<Date, Date> entry : map.entrySet()) {
                previous.setTime(entry.getKey());
                next.setTime(entry.getValue());
                for (List<GD5Report> reports : baseMap.values()) {
                    List<GD5Report> gd5Reports = new ArrayList<>();
                    for (GD5Report report : reports) {
                        center.setTime(df.parse(report.getResponsetime()));
                        if (center.equals(previous) || (center.after(previous) && center.before(next))){
                            String responsetime = df.format(previous.getTime()) + "~" +df.format(next.getTime());
                            report.setResponsetime(responsetime);
                            gd5Reports.add(report);
                        }
                    }
                    reportList.add(gd5Reports);
                }
            }
            Iterator<List<GD5Report>> iterator = reportList.iterator();
            while (iterator.hasNext()){
                List<GD5Report> list1 = iterator.next();
                if (UtilMethod.notEmptyList(list1)){
                    GD5Report report = list1.get(0);
                    GD5Report report1 = new GD5Report();
                    report1.setPosition(report.getPosition());
                    report1.setResponsetime(report.getResponsetime());
                    report1.setIp(report.getIp());
                    report1.setType("GD多参数传感器");
                    report1.setDevid(report.getDevid());
                    report1.setFlow_standard(report.getFlow_standard());
                    report1.setFlow_pure(report.getFlow_pure());
                    report1.setFlow_work(report.getFlow_work());
                    double flow_work_sum = 0.0;
                    double flow_pure_sum = 0.0;
                    double flow_standard_sum = 0.0;
                    for (GD5Report gd5Report : list1) {
                        flow_pure_sum += gd5Report.getFlow_pure_sum();
                        flow_standard_sum += gd5Report.getFlow_standard_sum();
                        flow_work_sum += gd5Report.getFlow_work_sum();
                    }
                    report1.setFlow_standard_sum(flow_standard_sum);
                    report1.setFlow_pure_sum(flow_pure_sum);
                    report1.setFlow_work_sum(flow_work_sum);
                    report1.setTemperature(report.getTemperature());
                    report1.setPressure(report.getPressure());
                    report1.setWasi(report.getWasi());
                    report1.setCo(report.getCo());
                    gd5ReportList.add(report1);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return gd5ReportList;
	}

	// 年报表
	@SuppressWarnings("rawtypes")
	public List<GD5Report> getYearReport(String startime, String endtime) {
        List<GD5Report> list = dao.getAll(startime, endtime);
        if(list.isEmpty()){
            return list;
        }
        List<GD5Report> gd5ReportList = new ArrayList<>();
        List<List<GD5Report>> reportList = new ArrayList<>();
        try{
            Map<String, List<GD5Report>> baseMap = getBaseMap(list);
            Calendar previous = Calendar.getInstance();
            Calendar next = Calendar.getInstance();
            Calendar center = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            Map<Date, Date> map = getDate(startime, endtime, "yyyy", Calendar.YEAR, 1);
            for (Map.Entry<Date, Date> entry : map.entrySet()) {
                previous.setTime(entry.getKey());
                next.setTime(entry.getValue());
                for (List<GD5Report> reports : baseMap.values()) {
                    List<GD5Report> gd5Reports = new ArrayList<>();
                    for (GD5Report report : reports) {
                        center.setTime(df.parse(report.getResponsetime()));
                        if (center.equals(previous) || (center.after(previous) && center.before(next))){
                            String responsetime = df.format(previous.getTime()) + "~" +df.format(next.getTime());
                            report.setResponsetime(responsetime);
                            gd5Reports.add(report);
                        }
                    }
                    reportList.add(gd5Reports);
                }
            }
            Iterator<List<GD5Report>> iterator = reportList.iterator();
            while (iterator.hasNext()){
                List<GD5Report> list1 = iterator.next();
                if (UtilMethod.notEmptyList(list1)){
                    GD5Report report = list1.get(0);
                    GD5Report report1 = new GD5Report();
                    report1.setPosition(report.getPosition());
                    report1.setResponsetime(report.getResponsetime());
                    report1.setIp(report.getIp());
                    report1.setType("GD多参数传感器");
                    report1.setDevid(report.getDevid());
                    report1.setFlow_standard(report.getFlow_standard());
                    report1.setFlow_pure(report.getFlow_pure());
                    report1.setFlow_work(report.getFlow_work());
                    double flow_work_sum = 0.0;
                    double flow_pure_sum = 0.0;
                    double flow_standard_sum = 0.0;
                    for (GD5Report gd5Report : list1) {
                        flow_pure_sum += gd5Report.getFlow_pure_sum();
                        flow_standard_sum += gd5Report.getFlow_standard_sum();
                        flow_work_sum += gd5Report.getFlow_work_sum();
                    }
                    report1.setFlow_standard_sum(flow_standard_sum);
                    report1.setFlow_pure_sum(flow_pure_sum);
                    report1.setFlow_work_sum(flow_work_sum);
                    report1.setTemperature(report.getTemperature());
                    report1.setPressure(report.getPressure());
                    report1.setWasi(report.getWasi());
                    report1.setCo(report.getCo());
                    gd5ReportList.add(report1);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return gd5ReportList;
	}

	private Map<Date, Date> getDate(String starttime, String endtime, String pattern, int filed, int amount) throws Exception{
        Map<Date, Date> map = new TreeMap<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        calendar.setTime(df.parse(starttime));
        Date previous = calendar.getTime();
        calendar.add(filed, amount);
        Date next = calendar.getTime();
        map.put(previous, next);
        while (true){
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(df.parse(endtime));
            if (calendar.equals(endCalendar) || calendar.after(endCalendar)){
                break;
            }
            Date preDate = calendar.getTime();
            calendar.add(filed, amount);
            Date nextDate = calendar.getTime();
            map.put(preDate, nextDate);
        }
        return map;
    }

    private Map<String, List<GD5Report>> getBaseMap(List<GD5Report> list){
	    Map<String, List<GD5Report>> map = new HashMap<>();
        for (GD5Report report : list) {
            String ukey = report.getIp() + ":" + report.getDevid();
            List<GD5Report> list1 = map.get(ukey);
            if (null == list1){
                list1 = new ArrayList<>();
            }
            list1.add(report);
            map.put(ukey, list1);
        }
	    return map;
    }

	public String getSensorPosition(int station, int sensorId) {
		return dao.getSensorPosition(station, sensorId);
	}
}
