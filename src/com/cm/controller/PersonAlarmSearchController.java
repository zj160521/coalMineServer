package com.cm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.PersonAlarmSearch;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.OvertimeAlarmVo;
import com.cm.entity.vo.WorkerAreaWarnVo;
import com.cm.entity.vo.WorkerExitWarnVo;
import com.cm.entity.vo.WorkerUnconnectionVo;
import com.cm.security.LoginManage;
import com.cm.service.PersonAlarmSearchService;


@Scope("prototype")
@Controller
@RequestMapping("/daily")
public class PersonAlarmSearchController {
	
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private PersonAlarmSearchService service;
	
	@RequestMapping(value="/personAlarmSearch",method=RequestMethod.POST)
	@ResponseBody
	public Object getall(@RequestBody PersonAlarmSearch search,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			if(search.getType()==1){
				search.setStart_row((search.getCur_page()-1)*search.getPage_rows());
				List<WorkerAreaWarnVo> list = service.getWorkerAreaWarn(search);
				search.setTotal_rows(service.getWorkerAreaWarnCount(search));
				result.put("data", list);
				result.put("search",search);
			}
			if(search.getType()==2){
				search.setStart_row((search.getCur_page()-1)*search.getPage_rows());
				List<WorkerExitWarnVo> list = service.getWorkerExitWarn(search);
				search.setTotal_rows(service.getWorkerExitWarnCount(search));
				result.put("data", list);
				result.put("search",search);
			}
			if(search.getType()==3){
				search.setStart_row((search.getCur_page()-1)*search.getPage_rows());
				List<WorkerUnconnectionVo> list = service.getWorkerUnconnection(search);
				search.setTotal_rows(service.getWorkerUnconnectionCount(search));
				result.put("data", list);
				result.put("search",search);
			}
			if(search.getType()==4){
				search.setStart_row((search.getCur_page()-1)*search.getPage_rows());
				List<OvertimeAlarmVo> list = service.getOvertimeAlarm(search);
				search.setTotal_rows(service.getOvertimeAlarmCount(search));
				result.put("count", service.getOvertimeAlarmCount(search));
				result.put("data", list);
				result.put("search",search);
			}
			if (search.getType()==5) {
				search.setStart_row((search.getCur_page()-1)*search.getPage_rows());
				search.setArea_id(1);
				List<OvertimeAlarmVo> list = service.getOvertimeAlarm(search);
				search.setTotal_rows(service.getOvertimeAlarmCount(search));
				result.put("count", service.getOvertimeAlarmCount(search));
				result.put("data", list);
				result.put("search",search);
			}
			if(search.getType()==6){
				search.setStart_row((search.getCur_page()-1)*search.getPage_rows());
				List<OverManVo> list = service.getOverMan(search);
				search.setTotal_rows(service.getOverManCount(search));
				List<OverManVo> list2 = service.getallOverMan(search);
				int count = 0;
				for(OverManVo l:list2){
					count = count+l.getOverNum();
				}
				result.put("data", list);
				result.put("search",search);
				result.put("count", count);
				result.put("alldata", list2);
			}
			if(search.getType()==7){
				search.setStart_row((search.getCur_page()-1)*search.getPage_rows());
				search.setArea_id(1);
				List<OverManVo> list = service.getOverMan(search);
				search.setTotal_rows(service.getOverManCount(search));
				List<OverManVo> list2 = service.getallOverMan(search);
				int count = 0;
				for(OverManVo l:list2){
					count = count+l.getOverNum();
				}
				result.put("data", list);
				result.put("search",search);
				result.put("count", count);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
