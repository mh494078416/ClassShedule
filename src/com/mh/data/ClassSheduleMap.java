package com.mh.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.util.Log;

import com.mh.dao.ClassSheduleDAO;
import com.mh.dao.SemesterDAO;
import com.mh.entity.ClassSheduleDO;
import com.mh.entity.SemesterDO;
import com.mh.util.CalendarUtil;

public class ClassSheduleMap {
	private static final String TAG = ClassSheduleMap.class.getSimpleName();
	private static long semesterStartDate = 0l; // 学期的开始时间，用于判断如何展现单双周课程

	private static final int OFFSET = 2; // 展现向后2天的数据
	private static final Map<String, List<ClassSheduleDO>> classMap = new HashMap<String, List<ClassSheduleDO>>();

	public static void init(ClassSheduleDAO classSheduleDAO, SemesterDAO semesterDAO) {
		SemesterDO semesterDO = semesterDAO.queryALL().get(0);
		int semesterId = semesterDO.getId();
		ClassSheduleMap.semesterStartDate = semesterDO.getStartDate();
		ArrayList<ClassSheduleDO> classSheduleDOList = classSheduleDAO.queryALL(semesterId);
		if (classSheduleDOList == null || classSheduleDOList.isEmpty()) {
			return;
		}
		Log.v(TAG, "init class shedule : " + classSheduleDOList.size());
		for (ClassSheduleDO classSheduleDO : classSheduleDOList) {
			String week = classSheduleDO.getWeek();
			List<ClassSheduleDO> classList = classMap.get(week);
			if (classList == null) {
				classList = new ArrayList<ClassSheduleDO>();
				classMap.put(week, classList);
			}
			classList.add(classSheduleDO);
		}
	}

	/**
	 * 传入某一天，查询这一天的课程，过滤出单周或双周的课程，然后按照时间时间排序
	 * 
	 * @param key
	 *            格式：2013-12-01
	 * @return
	 */
	public static List<ClassSheduleDO> get(String dateStr) {
		Date date = CalendarUtil.toDate(dateStr, CalendarUtil.DATE_FMT_3);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);
		week = transerWeekName(week);
		List<ClassSheduleDO> resultList = classMap.get(week);
		if (resultList == null) {
			return new ArrayList<ClassSheduleDO>(0);
		}
		// 当前是单周，则排除双周的课程
		Iterator<ClassSheduleDO> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			ClassSheduleDO classSheduleDO = iterator.next();
			// 只有设置了学期开始时间，并且只有单周或双周的课程才进行是否排除的判断
			if (semesterStartDate > 0 && classSheduleDO.getWeekDiff() > 0
					&& classSheduleDO.getWeekDiff() != getWeekDiff(date)) {
				iterator.remove();
			}
		}
		Collections.sort(resultList, new Comparator<ClassSheduleDO>() {
			@Override
			public int compare(ClassSheduleDO o1, ClassSheduleDO o2) {
				return o1.getStartTime() < o2.getStartTime() ? -1 : (o1.getStartTime() > o2.getStartTime() ? 1 : 0);
			}
		});
		return resultList;
	}

	/**
	 * 返回格式：2013-12-01
	 * 
	 * @param i
	 * @return
	 */
	public static String getDay(int i) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int rollDay = i - OFFSET;
		calendar.roll(Calendar.DAY_OF_YEAR, rollDay);
		return CalendarUtil.toString(calendar.getTime(), CalendarUtil.DATE_FMT_3);
	}

	/**
	 * 返回格式：周六 11/29
	 * 
	 * @param i
	 * @return
	 */
	public static String getDisplayDay(int i) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int rollDay = i - OFFSET;
		calendar.roll(Calendar.DAY_OF_YEAR, rollDay);
		String dateStr = CalendarUtil.toString(calendar.getTime(), CalendarUtil.DATE_FMT_2);
		String temp = dateStr.split(" ")[0];
		String displayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.CHINA);
		return transerWeekName(displayName) + " " + temp.substring(5, temp.length());
	}

	private static String transerWeekName(String weekName) {
		return "周" + weekName.charAt(weekName.length() - 1);
	}

	/**
	 * 获取当前是单周还是双周，1是单周，2是双周
	 * 
	 * @param calendar
	 * @return
	 */
	private static int getWeekDiff(Date date) {
		if (semesterStartDate == 0l) {
			return 0;
		}
		long millTime = date.getTime() - semesterStartDate;
		int day = Long.valueOf(millTime / (1000 * 3600)).intValue();
		if (day % 14 > 7) {
			return 2;
		}
		return 1;
	}
}
