package com.mh.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateHelper {

	private static final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
	    "星期六" };
	public static String getWeekDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;	// 返回值 是 以星期日为星期的第一天 ，星期日为1， 之后依次递增
		return dayNames[dayOfWeek];
	}
	/**
	 * 返回数字，1 2 3 4 5 6 7 分别代表星期一，星期二....
	 * @param date
	 * @return
	 */
	public static int getWeekNum(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;	// 返回值 是 以星期日为星期的第一天 ，星期日为1， 之后依次递增
		// 如果是星期日，则返回7
		if(dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}
	public static String getDateString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(calendar.getTime());
		String yearString = str.substring(0, 4) + "年";
		String monthString = str.substring(5, 7) + "月";
		String dayString = str.substring(8, 10) + "日";
		return yearString + monthString + dayString;
	}
	public static String getWeekString(int n) {
		String weekName = "";
		switch (n) {
		case 1:
			weekName = "星期一";
			break;
		case 2:
			weekName = "星期二";
			break;
		case 3:
			weekName = "星期三";
			break;
		case 4:
			weekName = "星期四";
			break;
		case 5:
			weekName = "星期五";
			break;
		case 6:
			weekName = "星期六";
			break;
		case 7:
			weekName = "星期日";
			break;
		}
		return weekName;
	}
}
