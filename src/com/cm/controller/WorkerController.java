package com.cm.controller;

import com.cm.entity.Department;
import com.cm.entity.Static;
import com.cm.entity.Workbasic;
import com.cm.entity.Worker;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import util.JedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 * 
 * @author hetaiyun
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private WorkbasicService workbasicService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private AreaCardService as;

	@Autowired
	private StaticService staticService;

	/**
	 * 添加修改员工信息
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addupWorker(@RequestBody Worker worker, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		Jedis j=null;
		try {
			j = RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		try {
			if (worker.getDuty() != null&&worker.getDuty()!="") {
				List<Static> static1 = staticService.getDutyByDuty(worker.getDuty());
				if (static1 !=null&&static1.size()>0) {
					worker.setDuty_id(static1.get(0).getId());
				} else {
					List<Static> static2 = staticService.addDuty(worker.getDuty());
					worker.setDuty_id(static2.get(0).getId());
				}
			}
			/*if (worker.getWorkplace() != null) {
				List<Static> static3 = staticService.getWorkplacebyWorkplace(worker.getWorkplace());
				if (static3 != null) {
					worker.setWorkplace_id(static3.get(0).getId());
				} else {
					List<Static> static4 = staticService.addworkplace(worker.getWorkplace());
					worker.setWorkplace_id(static4.get(0).getId());
				}
			}*/
			if(worker.getIsuse()==2){
				worker.setRfcard_id("0");
			}
			if (worker.getIds() != null) {
				if (worker.getIds().length > 1) {
					workerService.updateWorkers(worker);
					RedisClient.getInstance().setCardWorker(as);
					RedisClient.getInstance().setEntranceCard(as);
					RedisClient.getInstance().setAttendance(as);
					RedisClient.getInstance().setWorkerName(as);
				} else {
					workerService.updateWorker(worker);
					RedisClient.getInstance().setCardWorker(as);
					RedisClient.getInstance().setEntranceCard(as);
					RedisClient.getInstance().setAttendance(as);
					RedisClient.getInstance().setWorkerName(as);
				}
			} else {
				int a = workerService.getWorker(worker);
				if (a > 0) {
					return result.setStatus(-2, "卡号已存在");
				} else {
					if (worker.getIds() != null) {
						if (worker.getIds().length > 1) {
							workerService.updateWorkers(worker);
							RedisClient.getInstance().setCardWorker(as);
							RedisClient.getInstance().setEntranceCard(as);
							RedisClient.getInstance().setAttendance(as);
							RedisClient.getInstance().setWorkerName(as);
						} else {
							workerService.updateWorker(worker);
							RedisClient.getInstance().setCardWorker(as);
							RedisClient.getInstance().setEntranceCard(as);
							RedisClient.getInstance().setAttendance(as);
							RedisClient.getInstance().setWorkerName(as);
						}
					} else {
						workerService.addWorker(worker);
						// 加入到redis表中

						RedisClient.getInstance().checkWorkerInAreaTable(as);
						RedisClient.getInstance().setCardWorker(as);
						j.hset("workInArea", worker.getRfcard_id(), "-1;0:0;0;0;0;0");
						j.hset("workerExitMap", worker.getRfcard_id(), "0");
						j.hset("workerLatestTime", worker.getRfcard_id(), "0");
						j.hset("workerAtt", worker.getRfcard_id(), "0");
						j.hset("workerOvertime", worker.getRfcard_id(), "0;0");
						JedisUtil.returnResource(j);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JedisUtil.returnResource(j);
			return result.setStatus(-2, "添加员工出错！");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 查询员工信息
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getall", method = RequestMethod.POST)
	@ResponseBody
	public Object getAllWorker(@RequestBody Worker worker, HttpServletRequest request) {
		/*if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}*/
		try {
			if (worker.getDepart_id() > 0) {
				List<Integer> ds = new ArrayList<Integer>();
				ds = getDepartid(worker.getDepart_id(), ds);
				worker.getDeparts().add(worker.getDepart_id());
				for (Integer d : ds) {
					worker.getDeparts().add(d);
				}
				worker.setIsuse(1);
				List<Worker> workers = workerService.getAllWorker(worker);
				if (worker.getId() > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					List<Workbasic> list = workbasicService.getAllWorkbasic();
					map.put("worker", workers);
					map.put("workbasic", list);
					result.put("data", map);
				} else {
					result.put("data", workers);
				}
			} else {
				List<Worker> workers = workerService.getAllWorker(worker);
				if (worker.getId() > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					List<Workbasic> list = workbasicService.getAllWorkbasic();
					map.put("worker", workers);
					map.put("workbasic", list);
					result.put("data", map);
				} else {
					result.put("data", workers);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "excption");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 修改更新员工信息
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateWorker(@RequestBody Worker worker, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		try {
			 RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		try {
			workerService.updateWorker(worker);
			RedisClient.getInstance().setCardWorker(as);
			RedisClient.getInstance().setAttendance(as);
			RedisClient.getInstance().setWorkerName(as);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "excption");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteWorker(@RequestBody Worker worker, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (null != per)
			return per;
		
		Jedis jedis=null;
		try {
			jedis = RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		
		try {
			workerService.deleteLeaveWorker(worker.getIds());
			if(worker.getRfcard_ids()!=null&&worker.getRfcard_ids().length>0){
				for(String s:worker.getRfcard_ids()){
					jedis.hdel("workInArea", s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	/**
	 * 验证卡号是否已存在
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/validation", method = RequestMethod.POST)
	@ResponseBody
	public Object getcard(@RequestBody Worker worker, HttpServletRequest request) {
		int a = workerService.getWorker(worker);
		if (a > 0) {
			return result.setStatus(-2, "卡号已存在");
		} else {
			return result.setStatus(0, "验证通过");
		}
	}

	private List<Integer> getDepartid(int id, List<Integer> list) {
		List<Integer> dp = list;
		List<Department> lists = departmentService.getAllDepartmentByid(id);
		if (lists.size() > 0) {
			for (Department l : lists) {
				getDepartid(l.getId(), dp);
				dp.add(l.getId());
			}
		}
		return dp;
	}
}
