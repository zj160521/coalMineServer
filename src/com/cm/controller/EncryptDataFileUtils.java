package com.cm.controller;

import com.cm.entity.Sensor;
import com.cm.entity.Static;
import com.cm.entity.Substation;
import com.cm.entity.SwitchSensor;
import com.cm.service.BaseinfoService;
import com.cm.service.StaticService;
import com.cm.service.SubstationService;
import com.cm.service.SwitchSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class EncryptDataFileUtils {

    @Autowired
    private StaticService staticService2;

    @Autowired
    private BaseinfoService baseinfoService2;

    @Autowired
    private SwitchSensorService sensorService2;

    @Autowired
    private SubstationService substationService2;

    private static StaticService staticService;

    private static BaseinfoService baseinfoService;

    private static SwitchSensorService sensorService;

    private static SubstationService substationService;

    @PostConstruct
    public void init() {
        staticService = staticService2;
        baseinfoService = baseinfoService2;
        sensorService = sensorService2;
        substationService = substationService2;
    }



    /**
     *
     * @param sensor_type 设备类型
     * @param sensor_id 设备数据库id
     * @return
     */
    public static StringBuffer definitionOfMeasurePoints(int sensor_type,int sensor_id){
        StringBuffer sb = new StringBuffer();
        String v = staticService.getV(8);
        sb.append(v);
        sb.append("01");
        if(sensor_type == 16){
            sb.append("DT");
            sb.append("4001");
            Substation substation = substationService.getSubbyid(sensor_id);
            sb.append(null != substation.getUid() ? substation.getUid() : "");
        } else {
            Static sta = staticService.getPositionByid(sensor_type);
            String uid = "";
            if (null != sta){
                if(sta.getPid()==25) {
                    sb.append("KG");
                    SwitchSensor sensor = sensorService.getById(sensor_id);
                    if(null != sensor){
                        uid = sensor.getUid();
                    }
                } else if (sta.getPid() == 100) {
                    sb.append("MN");
                    Sensor sensor = baseinfoService.getById2(sensor_id);
                    uid = sensor.getUid();
                }
            }
            sb.append(sta.getEncode());
            sb.append(uid);
        }
        return sb;
    }
}