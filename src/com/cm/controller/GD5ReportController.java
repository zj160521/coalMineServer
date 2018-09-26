package com.cm.controller;

import com.cm.entity.GD5Report;
import com.cm.security.LoginManage;
import com.cm.service.GD5ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/report")
public class GD5ReportController {
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private GD5ReportService service;
	
	@RequestMapping(value="/all",method=RequestMethod.POST)
	@ResponseBody
	public Object getAll(@RequestBody GD5Report report,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			DecimalFormat df = new DecimalFormat("#0.00");
			List<GD5Report> list = new ArrayList<GD5Report>();
			if(report.getReporttype()==1){
                list = service.getHourReport(report.getStarttime(), report.getEndtime());
                for (GD5Report gd5 : list) {
                    gd5.setType("GD多参数传感器");
                }
            }
			if(report.getReporttype()==2){
				list = service.getDayReport(report.getStarttime(), report.getEndtime());
                list = service.getHourReport(report.getStarttime(), report.getEndtime());
                for (GD5Report gd5 : list) {
                    gd5.setType("GD多参数传感器");
                }
			}
			if(report.getReporttype()==3){
				list = service.getXunReport(report.getStarttime(), report.getEndtime());
                list = service.getHourReport(report.getStarttime(), report.getEndtime());
                for (GD5Report gd5 : list) {
                    gd5.setType("GD多参数传感器");
                }
			}
			if(report.getReporttype()==4){
				list = service.getMonthReport(report.getStarttime(), report.getEndtime());
                list = service.getHourReport(report.getStarttime(), report.getEndtime());
                for (GD5Report gd5 : list) {
                    gd5.setType("GD多参数传感器");
                }
			}
			if(report.getReporttype()==5){
				list = service.getQuarterReport(report.getStarttime(), report.getEndtime());
                list = service.getHourReport(report.getStarttime(), report.getEndtime());
                for (GD5Report gd5 : list) {
                    gd5.setType("GD多参数传感器");
                }
			}
			if(report.getReporttype()==6){
				list = service.getYearReport(report.getStarttime(), report.getEndtime());
                list = service.getHourReport(report.getStarttime(), report.getEndtime());
                for (GD5Report gd5 : list) {
                    gd5.setType("GD多参数传感器");
                }
			}
			result.clean();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
