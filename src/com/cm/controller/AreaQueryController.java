package com.cm.controller;

import com.cm.entity.Area;
import com.cm.entity.Sensor;
import com.cm.entity.vo.Area2VO;
import com.cm.security.LoginManage;
import com.cm.service.AdjoinAreaService;
import com.cm.service.AreaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/area")
public class AreaQueryController {

	@Autowired
	private AreaQueryService areaQueryService;
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;

	@Autowired
	private AdjoinAreaService adjoinAreaService;
	
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllArea(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Area2VO> list = new ArrayList<Area2VO>();
			List<Area> emphasisArea = areaQueryService.EmphasisArea(2);
			Area2VO area = new Area2VO();
			area.setEmphasis(2);
			area.setAreaname("重点区域");
			area.setList(emphasisArea);
			list.add(area);
			List<Area> generalArea = areaQueryService.GeneralArea(1);
			Area2VO area2 = new Area2VO();
			area2.setEmphasis(1);
			area2.setAreaname("普通区域");
			area2.setList(generalArea);
			list.add(area2);
			List<Area> astrictArea = areaQueryService.AstrictArea(2);
			Area2VO area3 = new Area2VO();
			area3.setDefault_allow(2);
			area3.setAreaname("限制区域");
			area3.setList(astrictArea);
			list.add(area3);
			result.clean();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/adjoinsensor", method = RequestMethod.POST)
    @ResponseBody
    public Object getSensorByAdjoinArea(@RequestBody Area area, HttpServletRequest request){
	    if (!loginManage.isUserLogin(request)){
	        return result.setStatus(-1, "no login");
        }
        try{
            List<Sensor> list = adjoinAreaService.getSensorByAdjoinArea(area.getAdjoinAreaIds());
            result.clean();
            result.put("data", list);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
	    return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/neighbor", method = RequestMethod.POST)
    @ResponseBody
    public Object addNeighborSensor(@RequestBody Area area, HttpServletRequest request){
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            List<Sensor> sensors = area.getSensors();
            if (null != sensors && !sensors.isEmpty()){
                if (sensors.get(0).getS_area_id() == 0){
                    adjoinAreaService.deleteNeighborArea(sensors.get(0).getM_area_id());
                } else {
                    adjoinAreaService.addNeighborSensor(sensors);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0,"ok");
    }
}
