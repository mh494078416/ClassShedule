package com.mh.util;

import java.util.Map.Entry;
import java.util.TreeMap;

public class MyConstant {
	public static final TreeMap<Integer, String> WEEK_MAP = new TreeMap<Integer, String>();
	public static final TreeMap<String, Integer> WEEK_REVERSE_MAP = new TreeMap<String, Integer>();
	static {
		WEEK_MAP.put(1, "周一");
		WEEK_MAP.put(2, "周二");
		WEEK_MAP.put(3, "周三");
		WEEK_MAP.put(4, "周四");
		WEEK_MAP.put(5, "周五");
		WEEK_MAP.put(6, "周六");
		WEEK_MAP.put(7, "周日");
		for (Entry<Integer, String> entry : WEEK_MAP.entrySet()) {
			WEEK_REVERSE_MAP.put(entry.getValue(), entry.getKey());
		}
	}
}
