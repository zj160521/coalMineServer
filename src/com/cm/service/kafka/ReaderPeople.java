package com.cm.service.kafka;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.cm.entity.vo.CardReader;
import com.cm.entity.vo.People;
import com.cm.security.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.LogOut;


public class ReaderPeople {

	private HashMap<Integer, People> peopleMap = new HashMap<Integer, People>();
	private HashMap<String, CardReader> readerMap = new HashMap<String, CardReader>();
	
	public String parseReader(JSONObject json) {
		try {
			JSONArray cards = json.getJSONArray("card");
			JSONArray battary = json.getJSONArray("battary");
			JSONArray emerge = json.getJSONArray("emerge");
			int status = Integer.parseInt(""+json.getString("status"));
			int id = Integer.parseInt(""+json.getString("id"));
			int devid = Integer.parseInt(""+json.getString("devid"));
			int type = Integer.parseInt(""+json.getString("type"));
			int can1 = Integer.parseInt(""+json.getString("can1"));
			int can2 = Integer.parseInt(""+json.getString("can2"));
			int level = Integer.parseInt(""+json.getString("level"));
			int debug = Integer.parseInt(""+json.getString("debug"));
			String ip = json.getString("ip");
			String time = json.getString("time");
			String response = json.getString("response");
			String desp = json.getString("desp");
			
			String key = ip + devid + type;
			long newTime = Tools.date2TimeStamp(time, null);
			if (System.currentTimeMillis() - newTime > 300) {
				return null;
			}
			
			if (cards == null || cards.size() != 2 ||
				battary == null || battary.size() != 2 ||
				emerge == null || emerge.size() != 2) {
				return null;
			}
			
			for (int i = 0; i < 2; i++) {
				int card = cards.getInt(i);
				if (card <= 0) continue;

				People people = peopleMap.get(card);
				if (people == null) {
					people = new People();
					peopleMap.put(card, people);
				}
				people.setCard(card);
				people.setBattary(battary.getInt(i));
				people.setEmerge(emerge.getInt(i));
				people.setReader(key);
				//LogOut.log.debug("Add card: " + card);
			}
			
			long lastTime = 0;
			int lastStatus = 0;
			int lastDebug = 0;
			CardReader reader = readerMap.get(key);
			if (reader == null) {
				reader = new CardReader();
				readerMap.put(key, reader);
			} else {
				lastTime = Tools.date2TimeStamp(reader.getTime(), null);
				lastStatus = reader.getStatus();
				lastDebug = reader.getDebug();
			}
			reader.setId(id);
			reader.setIp(ip);
			reader.setType(type);
			reader.setDevid(devid);
			reader.setStatus(status);
			reader.setCan1(can1);
			reader.setCan2(can2);
			reader.setLevel(level);
			reader.setDebug(debug);
			reader.setResponse(response);
			reader.setDesp(desp);
			
			HashSet<Integer> lastCards = new HashSet<Integer>(reader.getCard());
			reader.getCard().clear();
			reader.getBattary().clear();
			reader.getEmerge().clear();

			Collection<People> plist = peopleMap.values();
			for (People p: plist) {
				if (p.getReader().equals(key)) {
					reader.getCard().add(p.getCard());
					reader.getBattary().add(p.getBattary());
					reader.getEmerge().add(p.getEmerge());
				}
			}
			
			HashSet<Integer> newCards = new HashSet<Integer>(reader.getCard());
			if (lastStatus == status && lastDebug == debug && lastCards.equals(newCards) && newTime - lastTime < 5000) {
				return null;
			}

			reader.setTime(time);

			//LogOut.log.debug(json.toString());
			String ret = JSONObject.fromObject(reader).toString();
			LogOut.log.debug("People size: " + peopleMap.size() + ", " + ret);
			return ret;

		} catch(Exception e) {
			LogOut.log.error(json.toString());
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				sb.append(element.toString()+"\n");
			}
			LogOut.log.error("ReaderPeople Exception-"+sb.toString());
			e.printStackTrace();
			return null;
		}
	}
}
