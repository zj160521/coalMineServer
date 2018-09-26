package com.cm.controller;

import com.cm.controller.extrun.IRunCmd;
import com.cm.controller.extrun.RunCheckDiskMemory;
import com.cm.controller.extrun.RunFindMaster;
import com.cm.entity.Coalmine_route;
import com.cm.entity.DbBackupLog;
import com.cm.entity.Static;
import com.cm.entity.WorkerInAreaRec;
import com.cm.entity.vo.Backup;
import com.cm.entity.vo.FileVo;
import com.cm.entity.vo.NameTime;
import com.cm.security.ConfigUtil;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.DbBackupLogService;
import com.cm.service.RecomputeService;
import com.cm.service.StaticService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import redis.clients.jedis.Jedis;
import util.LogOut;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Scope("prototype")
@Controller
@RequestMapping("/backup")
public class BackupController {
	
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private StaticService staticService;
	@Autowired
	private DbBackupLogService service;
	@Autowired
	private RecomputeService recomservice;
	
	private ConfigUtil cfg = ConfigUtil.getInstance();
	
	private Jedis j=RedisClient.getInstance().getJedis();
	
	public static final String path = "/opt/backupDB/";
	public static final String importpath = "/opt/importDB/";
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	private ScheduledFuture<?> future=null;
	
	public String gettable(String time){
		StringBuffer sb=new StringBuffer();
		sb.append(" t_analog_statistics_"+time);
		sb.append(" t_coalMine_"+time);
		sb.append(" t_coalmine_min_data_"+time);
		sb.append(" t_coalMine_route_"+time);
		sb.append(" t_feedback_"+time);
		sb.append(" t_gd5_"+time);
		
		return sb.toString();
	}
	
	@RequestMapping(value = "/getFiles", method = RequestMethod.GET)
	@ResponseBody
	public Object getFiles(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		RunFindMaster runcmd = new RunFindMaster();
		Map<String,String> m=new HashMap<String,String>();
		if (runcmd.init2(m) != 0) {
			result.put("remark", "查找命令初始化失败！");
		}else{
			if (runcmd.runCli() != 0) {
				result.put("remark", "查找命令运行失败！");
			}else{
				String r=(String) runcmd.doEnd2();
				if(r.equals("-1")){
					result.put("remark", "查找命令回复出错！");
				}else{
					List<FileVo> list=new ArrayList<FileVo>();
					String[] a = r.split("\\s{1,}");
					if(a.length>9){
						int num = (a.length-2)/9;
						for(int i=0;i<num;i++){
							FileVo file=new FileVo();
							file.setFilename(a[i*9+10]);
							double num2=Double.parseDouble(a[i*9+6])/(1024*1024);
							double value =new BigDecimal(num2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							file.setSize(value);
							file.setCreatTime(a[i*9+7]+a[i*9+8]);
							list.add(file);
						}
					}
					result.put("data", list);
				}
			}
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/data", method = RequestMethod.POST)
	@ResponseBody
	public Object data(@RequestBody Backup b,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		String ma=ConfigUtil.getInstance().getSys_peerip();
		String pe=ConfigUtil.getInstance().getSys_ip();
		createPath(path);
		backData(ma,pe,b.getStarttime());
		
		return result.setStatus(0, "ok");
	}
	
	public void backData(String ip,String ip2,String starttime){
		Map<String,String> m=new HashMap<String,String>();
		m.put("path", path);
		m.put("type", "1");
		m.put("password", cfg.getDb_pass());
		m.put("filename", starttime+".sql.gz");
		m.put("table", gettable(starttime));
		m.put("ip", ip);
		m.put("ip2", ip2);
		BackupDbThread back=BackupDbThread.getInstance(m,service,ip);
		int flag=BackupDbThread.getFlag();
		if(flag==0||flag==-1||flag==100){
			Thread t=new Thread(back);
			t.start();
		}else{
			result.put("flag", flag);
		}
	}
	
	@RequestMapping(value = "/importdb", method = RequestMethod.POST)
	@ResponseBody
	public Object importdb(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		try {
			createPath(importpath);
			String fileName = getfileName(request, "file");
			if (fileName.contains(".sql.gz")) {
				fileUpload(request, importpath, "file");
			}else{
				return result.setStatus(-2, "文件格式不合法！");
			}
			Map<String,String> m=new HashMap<String,String>();
			m.put("path", importpath);
			m.put("type", "1");
			m.put("password", cfg.getDb_pass());
			m.put("filename", fileName);
			ImportDbThread importdb=ImportDbThread.getInstance(m,service);
			int flag=importdb.getFlag();
			if(flag==0||flag==-1||flag==100){
				Thread t=new Thread(importdb);
				t.start();
			}else{
				result.put("flag", flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.info("导入数据异常：", e);
			return result.setStatus(-2, "文件导入错误！");
		}
		return result.setStatus(0, "ok");
	}
	
	private static void createPath(String path) {
		File fp = new File(path);
		// 创建目录
		if (!fp.exists()) {
			fp.mkdirs();// 目录不存在的情况下，创建目录。
		}
	}
	
	private void fileUpload(HttpServletRequest request, String path,
			String name)throws IllegalStateException, IOException {
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile excel = ((MultipartHttpServletRequest) request)
					.getFile(name);
			String originFileName = excel.getOriginalFilename();
			if (excel != null && originFileName != null
					&& originFileName.length() > 0) {
				File file = new File(path + originFileName);
				excel.transferTo(file);
			}
		}
	}

	private String getfileName(HttpServletRequest request, String name) {
		MultipartFile excel = ((MultipartHttpServletRequest) request)
				.getFile(name);
		String originFileName = excel.getOriginalFilename();
		return originFileName;
	}
	
	@RequestMapping(value = "/flag", method = RequestMethod.GET)
	@ResponseBody
	public Object flag(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		result.put("flag", BackupDbThread.getFlag());
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/importflag", method = RequestMethod.GET)
	@ResponseBody
	public Object importflag(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		Map<String,String> m=new HashMap<String,String>();
		ImportDbThread back=ImportDbThread.getInstance(m,service);
		result.put("flag", back.getFlag());
		
		return result.setStatus(0, "ok");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/startCron", method = RequestMethod.GET)
    @ResponseBody
    public Object startCron(){
		if(future==null||future.isCancelled()){
			LogOut.log.debug(">>>>>>开始自动备份");
			createPath(path);
			Static<T> s=staticService.getPositionByid(9527);
			Map<String,String> m=new HashMap<String,String>();
			m.put("password", cfg.getDb_pass());
			m.put("path", path);
			m.put("type", "0");
			ThreadPoolTaskScheduler tpts=new ThreadPoolTaskScheduler();
			tpts.initialize();
			String ip="";
			try {
				ip = InetAddress.getLocalHost().getHostAddress().toString();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	        future =tpts.schedule(BackupDbThread.getInstance(m,service,ip), new CronTrigger("0 0 "+s.getV()+" * * ?"));
	        return result.setStatus(0, "ok");
		}else{
			return result.setStatus(-2, "开始新备份任务前，请先关闭旧任务！");
		}
    }
     
    @RequestMapping(value = "/stopCron", method = RequestMethod.GET)
    @ResponseBody
    public Object stopCron(HttpServletRequest request){
    	if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
    	LogOut.log.debug("关闭自动备份>>>>>>");
        if(future != null) {
            future.cancel(true);
        }
        return result.setStatus(0, "ok");
    }
	
    @RequestMapping(value = "/diskMemory", method = RequestMethod.GET)
    @ResponseBody
    public Object diskMemory(){
    	try {
			String ma=ConfigUtil.getInstance().getSys_peerip();
			String pe=ConfigUtil.getInstance().getSys_ip();
			Map<String,String> hm1=getMemory(ma);
			Map<String,String> hm2=getMemory(pe);
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			list.add(hm1);
			list.add(hm2);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.info("异常：",e);
			return result.setStatus(-2, "错误！");
		}
		return result.setStatus(0, "ok");
    }
    
    public Map<String,String> getMemory(String ip){
    	IRunCmd runcmd = new RunCheckDiskMemory();
    	Map<String,String> m=new HashMap<String,String>();
    	Map<String,String> hm1=new HashMap<String,String>();
    	m.put("ip", ip);
		if (runcmd.init(m) != 0) {
			hm1.put("remark", "查询命令初始化失败！");
			hm1.put("status", "-2");
			return hm1;
		}else{
			if (runcmd.runCli() != 0) {
				hm1.put("remark", "查询命令运行失败！");
				hm1.put("status", "-2");
				return hm1;
			}else{
				String r=(String) runcmd.doEnd();
				if(r.equals("-1")){
					hm1.put("remark", "查询命令回复出错！");
					hm1.put("status", "-2");
					return hm1;
				}else{
					LogOut.log.debug("磁盘容量："+r);
					String[] a = r.split("\\s{1,}");
					String s=ip+"： 磁盘总容量："+a[1]+"，"+"已用："+a[2]+"，"+"可用："+a[3]+"，"+"用量："+a[4]+"，"+"挂载点："+a[5];
					hm1.put("remark", s);
					String s2=a[4].replace("%", "");
					double d=Double.parseDouble(s2);
					if(d>80||d==80){
						hm1.put("status", "1");
					}else{
						hm1.put("status", "0");
					}
					return hm1;
				}
			}
		}
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	@ResponseBody
	private ResponseEntity<byte[]> download1(String filename) {
		
		try {
			String fileName = filename;
			String path2 = path+fileName;
			File file = new File(path2);
			HttpHeaders headers = new HttpHeaders();
			String dfileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", dfileName);
			return new ResponseEntity(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/getProperty", method = RequestMethod.GET)
    @ResponseBody
    public Object getProperty(HttpServletRequest request){
    	if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
    	Static<T> s=staticService.getPositionByid(9527);
    	result.put("data", s);
        return result.setStatus(0, "ok");
    }
    
	@RequestMapping(value = "/updateProperty", method = RequestMethod.POST)
    @ResponseBody
    public Object updateProperty(@RequestBody Static<T> s,HttpServletRequest request){
    	if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
    	if(s.getId()==9527){
    		staticService.update(s);
    	}else{
    		return result.setStatus(-2, "参数错误！");
    	}
    	
        return result.setStatus(0, "ok");
    }
    
	@RequestMapping(value = "/getlogs", method = RequestMethod.POST)
	@ResponseBody
	public Object getnewCallinfo(HttpServletRequest request,@RequestBody NameTime nameTime) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			if(nameTime.getName()==null){
		    	nameTime.setName("");
		    }
		    nameTime.setName("%"+nameTime.getName()+"%");
		    int nowpage=nameTime.getCur_page();
			int rowsum=nameTime.getPage_rows();
			int start_row=nowpage*rowsum-rowsum;
			nameTime.setCur_page(start_row);
			nameTime.setPage_rows(rowsum);
			
			List<DbBackupLog> logs = service.getAll(nameTime);
			int pages=service.getAllPage(nameTime);
			
			result.put("cur_page", nowpage);
		    result.put("page_rows", rowsum);
		    result.put("total_rows", pages);
			result.put("data", logs);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Object test(HttpServletRequest request) {
		String[] cmd=new String[]{"/bin/sh","-c","/usr/bin/mysqldump -ucoalmine  -p'!@#qwe' coalmine >/opt/coalmine.sql"};
		try {
			Process process=Runtime.getRuntime().exec(cmd);
			int exitcode = process.waitFor();
			process.destroy();
			result.put("value", exitcode);
		} catch (Exception e) {
			result.put("exc", e.getMessage());
			e.printStackTrace();
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/recompute", method = RequestMethod.GET)
	@ResponseBody
	public Object recompute(HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
		Map<String,String> map2=new HashMap<String,String>();
		String time2=format.format(new Date());
		String sql2="select * from t_coalMine_route_"+time2+" where date(filltime) = curdate() GROUP BY date(filltime),card";
		map2.put("sql", sql2);
		List<Coalmine_route> list1=recomservice.getDayRoute(map2);
		for(Coalmine_route c:list1){
			try {
				compute(c);
			} catch (Exception e) {
				e.printStackTrace();
				LogOut.log.info("recompute异常", e);
			}
		}
		
		return result.setStatus(0, "ok");
	}
	
	public void compute(Coalmine_route c){
		String time=c.getResponsetime().split(" ")[0].replace("-", "_");
		String time2=c.getResponsetime().split(" ")[0];
		Map<String,String> map=new HashMap<String,String>();
		String sql="select * from t_coalMine_route_"+time+" where card="+c.getCard();
		map.put("sql", sql);
		List<Coalmine_route> list=recomservice.getDayRoute(map);
		
		//得到所有记录
		Collections.sort(list);
		Map<String,String> area = j.hgetAll("area");
		Map<String, String> smap = j.hgetAll("substation");
		Map<String,String> workinarea=new HashMap<String,String>();
		List<WorkerInAreaRec> wiars=new ArrayList<WorkerInAreaRec>();
		for(Coalmine_route cr:list){
//			if(cr.getCard()==7){
//				LogOut.log.debug("补传debug list排序："+cr.getCard()+" = "+cr.getDev_id()+" ; "+cr.getResponsetime());
//			}
			compute2(cr,wiars,workinarea,area,smap);
		}
		//删除
		recomservice.del(time2, c.getCard());
//		if(c.getCard()==7){
//			LogOut.log.debug("补传debug："+c.getCard()+" = "+wiars.size());
//		}
		//批量插入
		int count=0;
		List<WorkerInAreaRec> newlist=new ArrayList<WorkerInAreaRec>();
		for(WorkerInAreaRec sp:wiars) {
			if(count > 0 && count % 50 == 0) {
				newlist.add(sp);
				recomservice.addAll(newlist);
				newlist.clear();
			}
			else {
				newlist.add(sp);
			}
			count++;
		}
		if(newlist.size() > 0){
			recomservice.addAll(newlist);
		}
	}
	
	public void compute2(Coalmine_route cr,List<WorkerInAreaRec> wiars,Map<String,String> workinarea,Map<String,String> areaMap,Map<String, String> smap){
		String subid=smap.get(cr.getIp());
		String area=areaMap.get(subid+":"+cr.getDevid());
		if(area==null){
			area="-2";
		}
		String areaCard = workinarea.get(String.valueOf(cr.getCard()));
		String[] ac = null;
		if (areaCard == null) {
			workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
			+ ";" + cr.getBattary() + ";" + "0" + ";" + "0");
			return;
		}
		// 判断redis中是否有此人的配置信息
		if (areaCard != null) {
			ac = areaCard.split(";");
			// 有此人的信息，对比信息是否改变，一共三种情况

			String intime = null;
			if (ac[4].equals("0")) {
				intime = cr.getResponsetime();
			} else {
				intime = ac[4];
			}

			// 区域变化和读卡器变化
			if (!ac[0].equals(area)) {
				LogOut.log.debug("区域变化和读卡器变化");

				// 判断是否为入井数据
				if (ac[0].equals("-1") && ac[1].equals("0:0")) {

					LogOut.log.debug("入井前到达出入口------------");
					// 修改redis
					workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
							+ ";" + cr.getBattary() + ";" + "0" + ";" + "0");

					//不插入入口区域变化信息
					return;
				}
				WorkerInAreaRec w2=new WorkerInAreaRec();
				// 有改变的值，插入到mysql
				w2.setArea_id(Integer.parseInt(ac[0]));
				w2.setCard_id(String.valueOf(cr.getCard()));
				w2.setDev_id(Integer.parseInt(ac[1].split(":")[1]));
				w2.setStatus(1);
				w2.setSub_id(Integer.parseInt(ac[1].split(":")[0]));
				w2.setWorker_id(cr.getWorker_id());
				w2.setResponsetime(cr.getResponsetime());
				wiars.add(w2);

				WorkerInAreaRec w=new WorkerInAreaRec();
				w.setArea_id(Integer.parseInt(area));
				w.setCard_id(String.valueOf(cr.getCard()));
				w.setDev_id(cr.getDevid());
				w.setStatus(2);
				w.setSub_id(Integer.parseInt(subid));
				w.setWorker_id(cr.getWorker_id());
				w.setResponsetime(cr.getResponsetime());
				wiars.add(w);
				
//				if(cr.getCard()==7){
//					LogOut.log.debug("补传debug："+cr.getCard()+" = "+w2.getArea_id()+" ; "+w2.getDev_id()+" ; "+w2.getResponsetime()+" ; "+w2.getStatus());
//					LogOut.log.debug("补传debug："+cr.getCard()+" = "+w.getArea_id()+" ; "+w.getDev_id()+" ; "+w.getResponsetime()+" ; "+w.getStatus());
//				}
				
				// 修改redis
				workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
				+ ";" + cr.getBattary() + ";" + intime + ";" + "0");
				
				// 判断是否有读卡器变化，但区域没变
			} else if ((!ac[1].equals(subid+":"+cr.getDevid())) && (ac[0].equals(area))) {
				LogOut.log.debug("读卡器变化，但区域没变");
				WorkerInAreaRec w2=new WorkerInAreaRec();
				// 有改变的值，插入到mysql
				w2.setArea_id(Integer.parseInt(ac[0]));
				w2.setCard_id(String.valueOf(cr.getCard()));
				w2.setDev_id(Integer.parseInt(ac[1].split(":")[1]));
				w2.setStatus(1);
				w2.setSub_id(Integer.parseInt(ac[1].split(":")[0]));
				w2.setWorker_id(cr.getWorker_id());
				w2.setResponsetime(cr.getResponsetime());
				wiars.add(w2);

				WorkerInAreaRec w=new WorkerInAreaRec();
				w.setArea_id(Integer.parseInt(area));
				w.setCard_id(String.valueOf(cr.getCard()));
				w.setDev_id(cr.getDevid());
				w.setStatus(2);
				w.setSub_id(Integer.parseInt(subid));
				w.setWorker_id(cr.getWorker_id());
				w.setResponsetime(cr.getResponsetime());
				wiars.add(w);
				// 修改redis
				workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
				+ ";" + cr.getBattary() + ";" + intime + ";" + "0");
			} 
		}
	}
}
