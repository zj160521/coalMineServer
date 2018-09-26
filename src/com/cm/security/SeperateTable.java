package com.cm.security;

import java.util.ArrayList;

public class SeperateTable {

	private static ArrayList<String> tables = new ArrayList<String>();

	private SeperateTable() {
		tables.clear();
		tables.add("t_coalMine");
		tables.add("t_coalMine_route");
		tables.add("t_coalmine_min_data");
		tables.add("t_feedback");
		tables.add("t_gd5");
	}

	private static SeperateTable self = null;

	public static SeperateTable instance() {

		if (self == null) {
			self = new SeperateTable();
		}

		return self;
	}

	public ArrayList<String> get() {
		return tables;
	}
	
}
