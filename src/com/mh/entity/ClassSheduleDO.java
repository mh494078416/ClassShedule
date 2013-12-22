package com.mh.entity;

public class ClassSheduleDO {
	public static final String TABLE_NAME = "shedule"; // 数据库表名

	public static final String KEY_ID = "id"; // 主键
	public static final String KEY_WEEK = "week"; // 表示周几
	public static final String KEY_WEEK_DIFF = "weekDiff"; // 单双周，0不区分，1单周，2双周
	public static final String KEY_SUBJECT = "subject"; // 课程名称
	public static final String KEY_PLACE = "place"; // 教室（上课地点）
	public static final String KEY_TEACHER = "teacher"; // 老师
	public static final String KEY_START_TIME = "startTime"; // 开始时间
	public static final String KEY_INFO = "info"; // 备注
	public static final String KEY_SEMESTER_ID = "semesterId"; // 课程所属的学期

	public static final String[] COLUMNS = new String[] { ClassSheduleDO.KEY_ID, ClassSheduleDO.KEY_WEEK,
			ClassSheduleDO.KEY_WEEK_DIFF, ClassSheduleDO.KEY_SUBJECT, ClassSheduleDO.KEY_PLACE,
			ClassSheduleDO.KEY_TEACHER, ClassSheduleDO.KEY_START_TIME, ClassSheduleDO.KEY_INFO,
			ClassSheduleDO.KEY_SEMESTER_ID };
	public static final String CREATE_SQL = "create table shedule (id integer primary key autoincrement, week varchar(10) not null, weekDiff int not null, subject varchar(30) not null, place varchar(30), teacher varchar(30), startTime long, endTime long, info text, semesterId int not null);";

	private int id;
	private String week;
	private int weekDiff;
	private String subject;
	private String place;
	private String teacher;
	private long startTime;
	private long endTime;
	private String info;
	private int semesterId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public int getWeekDiff() {
		return weekDiff;
	}

	public void setWeekDiff(int weekDiff) {
		this.weekDiff = weekDiff;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}
}
