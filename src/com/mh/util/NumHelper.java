package com.mh.util;

public class NumHelper {
	public static String numToString(int num) {
		switch (num) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		case 8:
			return "八";
		case 9:
			return "九";
		case 10:
			return "十";
		case 11:
			return "十一";
		case 12:
			return "十二";
		case 13:
			return "十三";
		case 14:
			return "十四";
		case 15:
			return "十五";
		case 16:
			return "十六";
		case 17:
			return "十七";
		case 18:
			return "十八";
		case 19:
			return "十九";
		case 20:
			return "二十";
		default:
			return "to big";
		}
	}

	public static int stringToNum(String str) {
		if ("一".equals(str)) {
			return 1;
		}
		if ("二".equals(str)) {
			return 2;
		}
		if ("三".equals(str)) {
			return 3;
		}
		if ("四".equals(str)) {
			return 4;
		}
		if ("五".equals(str)) {
			return 5;
		}
		if ("六".equals(str)) {
			return 6;
		}
		if ("七".equals(str)) {
			return 7;
		}
		if ("八".equals(str)) {
			return 8;
		}
		if ("九".equals(str)) {
			return 9;
		}
		if ("十".equals(str)) {
			return 10;
		}
		if ("十一".equals(str)) {
			return 11;
		}
		if ("十二".equals(str)) {
			return 12;
		}
		if ("十三".equals(str)) {
			return 13;
		}
		if ("十四".equals(str)) {
			return 14;
		}
		if ("十五".equals(str)) {
			return 15;
		}
		if ("十六".equals(str)) {
			return 16;
		}
		if ("十七".equals(str)) {
			return 17;
		}
		if ("十八".equals(str)) {
			return 18;
		}
		if ("十九".equals(str)) {
			return 19;
		}
		if ("二十".equals(str)) {
			return 20;
		} else {
			return 0;
		}
	}

	public static String numToWeek(int weekNum) {
		switch (weekNum) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 7:
			return "星期日";
		default:
			return "";
		}

	}
}
