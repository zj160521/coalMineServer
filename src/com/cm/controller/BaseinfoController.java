package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.*;
import com.cm.entity.vo.All_Senser;
import com.cm.entity.vo.DevlinkSensor;
import com.cm.entity.vo.KafkaMessage;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.*;
import com.cm.service.kafka.ConfigSyncThread;
import com.cm.service.kafka.KafkaMsgQueue;
import com.cm.service.kafka.MessageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Scope
@Controller
@RequestMapping("/baseinfo")
public class BaseinfoController {

    @Autowired
    private ResultObj result;

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private BaseinfoService baseinfoService;

    @Autowired
    private CardrederService cardrederService;

    @Autowired
    private SubstationService stationService;

    @Autowired
    private StaticService staticService;

    @Autowired
    private SwitchSensorService switchSensorService;

    @Autowired
    private RadioService radioService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private DistrictSensorService disService;

    @Autowired
    private DevLinkService devService;

    @Autowired
    private EquipmentsService equService;

    @Autowired
    private VentilateService ventilateService;

    @Autowired
    private PositionTypeService positionTypeService;

    @Autowired
    private EnvAreaService areaService;

    private static final String RADIO_ACTION = "broadcast";
    private static final String CUTOUT_ACTION = "cutout";
    private static final String CARDREADER_ACTION = "call";


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getById(@PathVariable int id, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        try {
            Sensor sensor = baseinfoService.getById(id);
            result.clean();
            result.put("data", sensor);
        } catch (Exception e) {
            e.printStackTrace();
            result.clean();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

    /**
     * 添加配置信息
     *
     * @param sensor
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/addup", method = RequestMethod.POST)
    @ResponseBody
    public Object configBaseinfo(@RequestBody Sensor sensor, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            result.clean();
            return result.setStatus(-1, "no login");
        }
        Object per = loginManage.checkPermission(request);
        if (per != null)
            return per;

        Jedis jedis = null;
        try {
            jedis = RedisClient.getInstance().getJedis();
        } catch (Exception e1) {
            e1.printStackTrace();
            return result.setStatus(-2, e1.getMessage());
        }
        if(sensor.getLimit_power() != 0 && Math.abs(sensor.getLimit_power()) != 999999){
        	if(sensor.getSensorList()==null){
        		return result.setStatus(-4, "配有断电值的时候必须要设置联动设备并配置断馈电仪");
        	}else{
        		List<DevlinkSensor> devlinkSensors = sensor.getSensorList();
        		int count = 0;
        		for(DevlinkSensor d:devlinkSensors){
        			if(d.getSensor_type()==56||d.getSensor_type()==53){
        				count++;
        			}
        		}
        		if(count == 0){
        			return result.setStatus(-4, "配有断电值的时候必须要设置联动设备并配置断馈电仪");
        		}
        	}
        }
        if(sensor.getFloor_power() != 0 && Math.abs(sensor.getFloor_power()) != 999999){
        	if(sensor.getSensorList()==null){
        		return result.setStatus(-4, "配有断电值的时候必须要设置联动设备并配置断馈电仪");
        	}else{
        		List<DevlinkSensor> devlinkSensors = sensor.getSensorList();
        		int count = 0;
        		for(DevlinkSensor d:devlinkSensors){
        			if(d.getSensor_type()==56||d.getSensor_type()==53){
        				count++;
        			}
        		}
        		if(count == 0){
        			return result.setStatus(-4, "配有断电值的时候必须要设置联动设备并配置断馈电仪");
        		}
        	}
        }
        
        Sensor sensor3 = new Sensor();
        sensor.setControl(1);
        try {
            if (sensor.getId() > 0) {
                // 判断设备id是否重复
                if (sensor.getSensorId() == 0 || sensor.getSensorId() == 255) {
                    return result.setStatus(-4, "设备地址不能为0或255");
                }
                Sensor sensor2 = baseinfoService.getById(sensor.getId());
                if (sensor2.getSensorId() != sensor.getSensorId() || sensor2.getStation() != sensor.getStation()) {
                    String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                    if (null != isuse) {
                        result.clean();
                        result.put("isuse", 1);
                        return result.setStatus(-2, isuse);
                    } else {
                        result.clean();
                        result.put("isuse", 0);
                    }
                }
                // 添加位置信息
                if (null == sensor.getPosition()) {
                    result.clean();
                    return result.setStatus(-2, "位置信息不能为空");
                }
                Static sta = staticService.getByPosition(sensor.getPosition());
                if (null == sta) {
                    sta = staticService.addPosition(sensor.getPosition());
                    sensor.setSensor_position(sta.getId());
                } else {
                    sensor.setSensor_position(sta.getId());
                }
                if (sensor.getSensor_type() != 69){
                    if (sensor.getPosition_type_id() > 0) {
                        PositionType positionType = positionTypeService.getById(sensor.getPosition_type_id());
                        if (positionType.getId() == 136 || positionType.getId() == 137){
                            if (sensor.getUpper_level1() > positionType.getAlarm()){
                                return result.setStatus(-4, "保存失败，设备报警值不能大于系统设定值");
                            }
                        } else {
                            if (positionType.getAlarm() > 0){
                                if (sensor.getUpper_level1() > positionType.getAlarm()){
                                    return result.setStatus(-4, "保存失败，设备报警值不能大于系统设定值");
                                }
                            }
                            if (positionType.getCut() > 0){
                                if (sensor.getLimit_power() > positionType.getCut()){
                                    return result.setStatus(-4, "保存失败，设备断电值不能大于系统设定值");
                                }
                            }
                            if (positionType.getRepower() > 0){
                                if (sensor.getLimit_repower() >= positionType.getRepower()){
                                    return result.setStatus(-4, "保存失败，设备复电值不能大于或等于系统设定值");
                                }
                            }
                        }
                    }
                }
                addDevLink(sensor);
                // 更新设备
                baseinfoService.updataSensor(sensor);
                // 更新设备之后将configsync置为1,1表示未同步设备
                baseinfoService.updateConfigById(1, sensor.getId());
                String remark = JSONObject.toJSONString(sensor);
                String operation2 = "修改传感器" + sensor.getAlais();
                loginManage.addLog(request, remark, operation2, 15130);
                result.clean();
                result.put("success", 0);
            } else {
                // 判断设备id是否重复
                String isuse = stationService.isuse(sensor.getStation(), sensor.getSensorId());
                if (null != isuse) {
                    result.clean();
                    result.put("isuse", 1);
                    return result.setStatus(-2, isuse);
                } else {
                    result.clean();
                    result.put("isuse", 0);
                }
                // 添加位置信息
                if (null == sensor.getPosition()) {
                    result.clean();
                    return result.setStatus(-2, "位置信息不能为空");
                }
                Static sta = staticService.getByPosition(sensor.getPosition());
                if (null == sta) {
                    sta = staticService.addPosition(sensor.getPosition());
                    sensor.setSensor_position(sta.getId());
                } else {
                    sensor.setSensor_position(sta.getId());
                }

                if (sensor.getSensor_type() != 69){
                    if (sensor.getPosition_type_id() > 0) {
                        PositionType positionType = positionTypeService.getById(sensor.getPosition_type_id());
                        if (positionType.getId() == 136 || positionType.getId() == 137){
                            if (sensor.getUpper_level1() > positionType.getAlarm()){
                                return result.setStatus(-4, "保存失败，设备报警值不能大于系统设定值");
                            }
                        } else {
                            if (positionType.getAlarm() > 0){
                                if (sensor.getUpper_level1() > positionType.getAlarm()){
                                    return result.setStatus(-4, "保存失败，设备报警值不能大于系统设定值");
                                }
                            }
                            if (positionType.getCut() > 0){
                                if (sensor.getLimit_power() > positionType.getCut()){
                                    return result.setStatus(-4, "保存失败，设备断电值不能大于系统设定值");
                                }
                            }
                            if (positionType.getRepower() > 0){
                                if (sensor.getLimit_repower() >= positionType.getRepower()){
                                    return result.setStatus(-4, "保存失败，设备复电值不能大于或等于系统设定值");
                                }
                            }
                        }
                    }
                }
                //添加别名
                Substation sub = stationService.getSubbyid(sensor.getStation());
                String ipaddr = sub.getIpaddr();
                int i = ipaddr.lastIndexOf(".");
                String string = ipaddr.substring(i + 1);
                String alais = "G" + string + "S" + sensor.getSensorId();
                sensor.setAlais(alais);
                // 添加设备
                baseinfoService.addBaseinfo(sensor);
                String uid = "SE" + sensor.getId() + alais;
                sensor.setUid(uid);
                baseinfoService.updateUid(sensor.getId(), uid);
                baseinfoService.updateConfigById(1, sensor.getId());
                addDevLink(sensor);
                String remark = JSONObject.toJSONString(sensor);
                String operation2 = "修改传感器" + sensor.getAlais();
                loginManage.addLog(request, remark, operation2, 15130);
                int id = switchSensorService.getaddedId(sensor.getStation(), sensor.getSensorId(),
                        sensor.getSensor_type());
                sensor3.setId(id);
                // 修改redis
                if (!jedis.hexists("sensorWarn", ipaddr + ":" + sensor.getSensorId())) {
                    jedis.hset("sensorWarn", ipaddr + ":" + sensor.getSensorId(), "0;0");
                }
                result.put("id", sensor.getId());
                result.put("success", 0);
            }
            String value = "{\"id\":" + sensor.getId() + ",\"ip\":\"" + sensor.getIpaddr()
                    + "\",\"devid\":" + sensor.getSensorId() + ",\"type\":"
                    + sensor.getSensor_type() + ",\"typename\":\"" + sensor.getType() + "\"}";
            ConfigSyncThread.SendMessage(value);
        } catch (Exception e) {
            e.printStackTrace();
            result.clean();
            return result.setStatus(-4, "exception");
        }
        result.clean();
        return result.setStatus(0, "ok");
    }

    /**
     * 查询全部配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllBaseinfo(Integer typeid, HttpServletRequest request) {
        /*if (!loginManage.isUserLogin(request)) {
            result.clean();
            return result.setStatus(-1, "no login");
        } */
        try {
            List<Sensor> list = null;
            if (null == typeid || typeid == 0) {
                list = baseinfoService.getAllSensor(0);
            } else {
                list = baseinfoService.getAllSensor(typeid);
            }
            result.clean();
            result.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            result.clean();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

    /**
     * 根据id删除某一个传感器
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object deleteSensor(@PathVariable int id, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        } else {
            Object per = loginManage.checkPermission(request);
            if (per != null)
                return per;
            try {
                Sensor sensor = baseinfoService.getById(id);
                if (null != sensor) {
                    String value = "{\"id\":" + sensor.getId() + ",\"ip\":\"" + sensor.getIpaddr()
                            + "\",\"devid\":" + sensor.getSensorId() + ",\"type\":" + sensor.getSensor_type()
                            + ",\"typename\":\"" + sensor.getType() + "\",\"op\":\"del\"}";
                    ConfigSyncThread.SendMessage(value);
                }
                DevLink link = new DevLink();
                link.setLogic_uid(sensor.getUid());
                devService.deleteBasisDevlink(link);
                if (sensor.getSensor_type() == 37) {
                    Sensor co = baseinfoService.getByCoId(id);
                    if (null != co) {
                        return result.setStatus(-4, "该设备为" + co.getAlais() + "设备的外接一氧化碳传感器，不能直接进行此操作");
                    }
                }
                if (sensor.getSensor_type() == 32 || sensor.getSensor_type() == 33 || sensor.getSensor_type() == 34 || sensor.getSensor_type() == 35 || sensor.getSensor_type() == 70 || sensor.getSensor_type() == 80) {
                    Sensor methane = baseinfoService.getByMethaneId(id);
                    if (null != methane) {
                        return result.setStatus(-4, "该设备为" + methane.getAlais() + "设备的外接甲烷传感器，不能直接进行此操作");
                    }
                }

                String remark = JSONObject.toJSONString(sensor);
                String operation2 = "删除传感器" + sensor.getAlais();
                loginManage.addLog(request, remark, operation2, 15131);
                baseinfoService.deleteSensor(id);
            } catch (Exception e) {
                e.printStackTrace();
                return result.setStatus(-4, "exception");
            }
        }
        result.clean();
        return result.setStatus(0, "ok");

    }

    /**
     * 修改传感器分站信息
     *
     * @param ipaddr
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/station", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSubstation(String ipaddr, int id, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        Object per = loginManage.checkPermission(request);
        if (null != per)
            return per;
        try {
            baseinfoService.updateSubstation(ipaddr, id);
        } catch (Exception e) {
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getAllSensor", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllSensor(@RequestBody Sensor sr,HttpServletRequest request) {
        /*if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }*/

        try{
            Cardreder cardreder = new Cardreder();
            List<Cardreder> list1 = cardrederService.getallCardreder(cardreder);
            for (Cardreder cardreder2 : list1) {
                cardreder2.setPid(64);
                cardreder2.setPath("people.svg");
                cardreder2.setList_type(1);
            }
            List<SwitchSensor> list2 = switchSensorService.AllSwitchSensor();
            for (SwitchSensor switchSensor : list2) {
                if (switchSensor.getSensor_type() == 53) {
                    switchSensor.setHasfloor(1);//1表示断电控制器
                } else if (switchSensor.getSensor_type() == 54) {
                    switchSensor.setHasfloor(2);//2表示馈电传感器
                } else if (switchSensor.getSensor_type() == 56) {
                    switchSensor.setHasfloor(3);//3表示断馈电仪器
                } else {
                    switchSensor.setHasfloor(0);
                }
            }

            List<Sensor> slist = baseinfoService.getAllSensor2();
            List<Object> list6 = new ArrayList<Object>();
            List<Sensor> list3 = new ArrayList<Sensor>();

            for (SwitchSensor switchSensor : list2) {
                switchSensor.setPid(25);
                switchSensor.setList_type(2);
                if (switchSensor.getIsDrainage() == 1) {
                    list6.add(switchSensor);
                }
            }
            for (Sensor sensor : slist) {
                sensor.setPid(100);
                sensor.setList_type(3);
                if (sensor.getSensor_type() == 32 || sensor.getSensor_type() == 33 || sensor.getSensor_type() == 34 || sensor.getSensor_type() == 35 || sensor.getSensor_type() == 70 || sensor.getSensor_type() == 80) {
                    sensor.setIsmethane(1);
                }
                if (sensor.getDrainageId() > 0) {
                    list6.add(sensor);
                }
                list3.add(sensor);
            }
            List<Radio> list4 = radioService.getAll();
            for (Radio r : list4) {
                r.setPid(65);
                r.setList_type(4);
            }
            List<Video> list5 = videoService.getAllVideo();
            for (Video v : list5) {
                v.setPid(70);
                v.setList_type(5);
            }
            List<Equipments> list7 = equService.getAllElec();
            List<Object> list8 = new ArrayList<>();
            List<Sensor> allVentilateSensor = ventilateService.getAllVentilateSensor();
            for (Sensor sensor : allVentilateSensor) {
                list8.add(sensor);
            }
            List<SwitchSensor> allVentilateSwitchSensor = ventilateService.getAllVentilateSwitchSensor();
            for (SwitchSensor switchSensor : allVentilateSwitchSensor) {
                list8.add(switchSensor);
            }

            List<EnvArea> list10 = areaService.getAll();
            for (EnvArea area : list10) {
                area.setType(900);
            }

            List<Substation> list9 = stationService.getAll();
            All_Senser all = new All_Senser();

            filterSensor(list1,sr);
            all.setList1(list1);

            filterSensor(list2,sr);
            all.setList2(list2);

            filterSensor(list3,sr);
            all.setList3(list3);

            all.setList4(list4);
            all.setList5(list5);
            all.setList6(list6);
            all.setList7(list7);
            all.setList8(list8);
            all.setList9(list9);
            all.setList10(list10);
            result.put("data", all);

        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0, "ok");
    }

    public void filterSensor(List<?> list,Sensor sr){
    	String key=sr.getKeywords();
    	if(null==key || "".equals(key)){
    		return;
    	}
    	Iterator<?> it =list.iterator();
    	while(it.hasNext()){
    		Object ob=it.next();
    		if(ob instanceof Cardreder){
    			Cardreder tmp = (Cardreder) ob;
    			if(!(tmp.getAreaname()+tmp.getPosition()+tmp.getAlais()+tmp.getType()).contains(key)){
        			it.remove();
        		}
    		}else if(ob instanceof SwitchSensor){
    			SwitchSensor tmp = (SwitchSensor) ob;
    			if(!(tmp.getAreaname()+tmp.getPosition()+tmp.getAlais()+tmp.getType()).contains(key)){
        			it.remove();
        		}
    		}else if(ob instanceof Sensor){
    			Sensor tmp = (Sensor) ob;
    			if(!(tmp.getAreaname()+tmp.getPosition()+tmp.getAlais()+tmp.getType()).contains(key)){
        			it.remove();
        		}
    		}
    	}
    }
    
    @RequestMapping(value = "/updateSensor", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSensor(@RequestBody Sensor sensor, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        String uid = sensor.getUid();
        if (uid.startsWith("RD")) {
            Cardreder c = new Cardreder();
            c.setId(sensor.getId());
            c.setE_point(sensor.getE_point());
            c.setN_point(sensor.getN_point());
            c.setX_point(sensor.getX_point());
            c.setY_point(sensor.getY_point());
            disService.updateCardreader(c);
        } else if (uid.startsWith("SW")) {
            SwitchSensor ss = new SwitchSensor();
            ss.setId(sensor.getId());
            ss.setE_point(sensor.getE_point());
            ss.setN_point(sensor.getN_point());
            ss.setX_point(sensor.getX_point());
            ss.setY_point(sensor.getY_point());
            ss.setE2_point(sensor.getE2_point());
            ss.setN2_point(sensor.getN2_point());
            ss.setX2_point(sensor.getX2_point());
            ss.setY2_point(sensor.getY2_point());
            ss.setE3_point(sensor.getE3_point());
            ss.setN3_point(sensor.getN3_point());
            ss.setX3_point(sensor.getX3_point());
            ss.setY3_point(sensor.getY3_point());
            disService.updateSwitchSensor(ss);
        } else if (uid.startsWith("SE")) {
            Sensor s = new Sensor();
            s.setId(sensor.getId());
            s.setE_point(sensor.getE_point());
            s.setN_point(sensor.getN_point());
            s.setX_point(sensor.getX_point());
            s.setY_point(sensor.getY_point());
            s.setE2_point(sensor.getE2_point());
            s.setN2_point(sensor.getN2_point());
            s.setX2_point(sensor.getX2_point());
            s.setY2_point(sensor.getY2_point());
            s.setE3_point(sensor.getE3_point());
            s.setN3_point(sensor.getN3_point());
            s.setX3_point(sensor.getX3_point());
            s.setY3_point(sensor.getY3_point());
            disService.updateSensor(s);
        } else if (uid.startsWith("AU")) {
            Radio r = new Radio();
            r.setId(sensor.getId());
            r.setE_point(sensor.getE_point());
            r.setN_point(sensor.getN_point());
            r.setX_point(sensor.getX_point());
            r.setY_point(sensor.getY_point());
            disService.updateRadio(r);
        } else if (uid.startsWith("VD")) {
            Video v = new Video();
            v.setId(sensor.getId());
            v.setE_point(sensor.getE_point());
            v.setN_point(sensor.getN_point());
            disService.updateVideo(v);
        } else if (uid.startsWith("OT")){
            Equipments equipments = new Equipments();
            equipments.setId(sensor.getId());
            equipments.setN_point(sensor.getN_point());
            equipments.setE_point(sensor.getE_point());
            equipments.setX_point(sensor.getX_point());
            equipments.setY_point(sensor.getY_point());
            equService.updatePoint(equipments);
        } else if (uid.startsWith("ST")){
            Substation station = new Substation();
            station.setId(sensor.getId());
            station.setN_point(sensor.getN_point());
            station.setE_point(sensor.getE_point());
            station.setX_point(sensor.getX_point());
            station.setY_point(sensor.getY_point());
            stationService.update(station);
        }

        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/synSensor", method = RequestMethod.POST)
    @ResponseBody
    public Object synSensor(@RequestBody Sensor sensor, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        KafkaMessage message = MessageUtil.makeMessage(sensor, "sensor");
        KafkaMsgQueue.getInstance().SendMessage(message);
        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/synSwitchSensor", method = RequestMethod.POST)
    @ResponseBody
    public Object synSensor(@RequestBody SwitchSensor sensor, HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)) {
            return result.setStatus(-1, "no login");
        }
        KafkaMessage message = MessageUtil.makeMessage(sensor, "switchsensor");
        KafkaMsgQueue.getInstance().SendMessage(message);
        return result.setStatus(0, "ok");
    }

    public void addDevLink(Sensor sensor) {
        DevLink link1 = new DevLink();
        link1.setLogic_uid(sensor.getUid());
        devService.deleteBasisDevlink(link1);
        List<DevlinkSensor> devlinkSensors = sensor.getSensorList();
        if (devlinkSensors != null && devlinkSensors.size() > 0) {
            List<DevLink> links = new ArrayList<DevLink>();
            for (DevlinkSensor d : devlinkSensors) {
                DevLink link = new DevLink();
                link.setLogic_uid(sensor.getUid());
                link.setLgcDevId(sensor.getSensorId());
                link.setLogic_type(sensor.getSensor_type());
                link.setLogic_ip(sensor.getIpaddr());
                if (sensor.getHasfloor() == 0) {
                    link.setLogic_exps(sensor.getUid() + ">=" + sensor.getLimit_power());
                    link.setRepower_exps(sensor.getUid() + "<" + sensor.getLimit_repower());
                }
                if (sensor.getHasfloor() == 1) {
                    link.setLogic_exps(sensor.getUid() + "<=" + sensor.getFloor_power());
                    link.setRepower_exps(sensor.getUid() + ">" + sensor.getFloor_repower());
                }
                link.setAction_uid(d.getUid());
                link.setActDevId(d.getSensorId());
                link.setAction_ip(d.getIp());
                link.setAction_type(d.getSensor_type());
                if (d.getSensor_type() == 56 || d.getSensor_type() == 71 || d.getSensor_type() == 53) {
                    link.setAction(CUTOUT_ACTION);
                    link.setParam("" + d.getAction());
                }
                if (d.getSensor_type() == 65) {
                    link.setAction(RADIO_ACTION);
                    link.setParam("" + d.getAction());
                }
                if (d.getSensor_type() == 64) {
                	link.setAction(CARDREADER_ACTION);
                	link.setParam("" + d.getAction());
                }
                links.add(link);
            }
            devService.addBasisDevLink(links);
        }

    }
}
