package com.cm.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cm.service.CreateTableService;

/**
 * 
 * @author zhouj
 * 
 */
@Service
public class TimerCreateTableService {

	@Autowired
	private CreateTableService ctService;
	@Autowired
	private DataSource dataSource;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	private HashMap<String, String> tableMap;
	private String thisYear;
	private String lastYear;
	private String tomorrow;
	private final String[] strArray = { "TABLE" };
	private Connection con;
	private DatabaseMetaData metaData;
	private final int monthNumber = -3;// 删除3个月以前数据表

	/**
	 * 定时创建表格及删除3个月前表格 添加表格在getTableTypes(String date)方法中添加
	 * 更改删除时间，修改monthNumber属性
	 * 
	 * @throws ParseException
	 */
	@Scheduled(cron = "0 0 0,9,16 * * ?")
	public void createTable() throws ParseException {
		List<String> dayList = getDayList();
		try {
			// 获取数据库连接
			con = getConnection();
			metaData = con.getMetaData();
			// 检查近93天的表是否存在，不存在创建
			for (String day : dayList) {
				checkAllTable(day);
			}
			// 删除三个月以前的表
			dropTable();
			// 关闭连接
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alterTable(String day) {
		String sql = "ALTER TABLE t_coalmine_min_data_" + day
				+ " ADD status INTEGER DEFAULT 0;";
		ctService.createTble(getMap(sql));
	}

	public List<String> getDayList() {
		List<String> dayList = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		thisYear = year + "";
		lastYear = (year - 1) + "";
		cal.add(Calendar.DATE, 1);
		tomorrow = df.format(cal.getTime());// 创建明天所需表格
		dayList.add(tomorrow);
		for (int i = 0; i < 1; i++) {
			cal.add(Calendar.DATE, -1);
			dayList.add(df.format(cal.getTime()));
		}
		return dayList;
	}

	// 加表，在tableMap中,将表名+"_"作为key,将建表语句作为value即可
	public void getTableTypes(String date) {
		tableMap = new HashMap<String, String>();
		tableMap.put("t_coalMine_route_", getT_coalMine_route(date));
		tableMap.put("t_coalMine_", getT_coalmine(date));
		tableMap.put("t_coalmine_min_data_", getT_coalmine_min_data(date));
		tableMap.put("t_feedback_", getT_feedback(date));
		tableMap.put("t_gd5_", getT_gd5(date));
		tableMap.put("t_analog_statistics_", getT_analog_statistics(date));
	}

	public void dropTable() {
		// 删除3个月以前的表
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, monthNumber);
			long threeMonthAgo = cal.getTime().getTime();
			ResultSet tables = metaData.getTables(null, null, null, strArray);// 获取数据库所有表
			while (tables.next()) {
				String tableName = tables.getString("TABLE_NAME");
				for (Entry<String, String> tableType : tableMap.entrySet()) {
					if (tableName.contains(tableType.getKey())) {
						boolean tableIsExist = tableIsExist(tableName);
						if (tableIsExist) {
							dropTable(tableName, threeMonthAgo);
						}
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void checkAllTable(String date) {
		// 获取表名
		getTableTypes(date);
		// 创表
		for (Entry<String, String> tableType : tableMap.entrySet()) {
			boolean tableIsExist = tableIsExist(tableType.getKey() + date);
			if (!tableIsExist)
				ctService.createTble(getMap(tableType.getValue()));
		}
	}

	public boolean tableIsExist(String table) {
		boolean isExist = false;
		try {
			ResultSet tables = metaData.getTables(null, null, table, strArray);// 获取数据库指定表
			while (tables.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExist;
	}

	public Connection getConnection() {
		try {
			Connection connection = dataSource.getConnection();
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void dropTable(String tableName, long threeMonthAgo) {
		String year = null;
		String thisYearTableSeparator = "_" + thisYear;
		String lastYearTableSeparator = "_" + lastYear;
		String[] split = null;
		if (tableName.contains(thisYearTableSeparator)) {
			split = tableName.split(thisYearTableSeparator);
			year = thisYear;
		} else if (tableName.contains(lastYearTableSeparator)) {
			split = tableName.split(lastYearTableSeparator);
			year = lastYear;
		}

		if (split != null) {
			try {
				Long tableTime = df.parse(year + split[1]).getTime();
				if (tableTime < threeMonthAgo) {
					String sql = "drop table " + tableName;
					ctService.createTble(getMap(sql));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, String> getMap(String sql) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		return map;
	}

	private String getT_coalmine(String date) {
		String t_coalMine = "create table t_coalMine_" + date
				+ " like t_coalMine";
		return t_coalMine;
	}

	private String getT_coalMine_route(String date) {
		String t_coalMine_route = "create table t_coalMine_route_" + date
				+ " like t_coalMine_route";
		return t_coalMine_route;
	}

	private String getT_coalmine_min_data(String date) {
		String t_coalmine_min_data = "create table t_coalmine_min_data_" + date
				+ " like t_coalmine_min_data";
		return t_coalmine_min_data;
	}

	public String getT_feedback(String date) {
		String t_feedback = "create table t_feedback_" + date
				+ " like t_feedback";
		return t_feedback;
	}

	public String getT_gd5(String date) {
		String t_gd5 = "create table t_gd5_" + date + " like t_gd5";
		return t_gd5;
	}

	public String getT_analog_statistics(String date) {
		String t_analog_statistics = "create table t_analog_statistics_" + date
				+ " like t_analog_statistics";
		return t_analog_statistics;
	}
}
