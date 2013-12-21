package com.mh.entity;

public class SemesterDO {
	public static final String TABLE_NAME = "semester"; // 数据库表名

	public static final String KEY_ID = "id"; // 主键
	public static final String KEY_NAME = "name"; // 学期名字，默认第一学期，用户可更改为大三上学期
	public static final String KEY_START_DATE = "startDate"; // 学期开始日期
	public static final String KEY_END_DATE = "endDate"; // 学期结束日期

	public static final String[] COLUMNS = new String[] { SemesterDO.KEY_ID, SemesterDO.KEY_NAME,
			SemesterDO.KEY_START_DATE, SemesterDO.KEY_END_DATE };
	public static final String CREATE_SQL = "create table semester (id integer primary key autoincrement, name varchar(20) not null, startDate long, endDate long);";

	private int id; // 主键
	private String name;
	private long startDate; // 学期开始日期
	private long endDate; // 学期结束日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
}
