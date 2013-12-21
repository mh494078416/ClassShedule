package com.mh.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;

public class CalendarUtil {
	public static final String DATE_FMT_2 = "yyyy/MM/dd hh:mm:ss";
	public static final String DATE_FMT_3 = "yyyy-MM-dd";

	@SuppressLint("SimpleDateFormat")
	public static Date toDate(String sDate, String sFmt) {
		SimpleDateFormat sdfFrom = null;
		java.util.Date dt = null;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			dt = sdfFrom.parse(sDate);
		} catch (Exception ex) {
			return null;
		} finally {
			sdfFrom = null;
		}

		return dt;
	}

	@SuppressLint("SimpleDateFormat")
	public static String toString(java.util.Date dt, String sFmt) {
		SimpleDateFormat sdfFrom = null;
		String sRet = null;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			sRet = sdfFrom.format(dt).toString();
		} catch (Exception ex) {
			return null;
		} finally {
			sdfFrom = null;
		}

		return sRet;
	}
}
