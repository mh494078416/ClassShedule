package com.mh.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mh.entity.ClassSheduleDO;
import com.mh.util.DbHelper;

public class ClassSheduleDAO {
	private Context context;

	public ClassSheduleDAO(Context context) {
		this.context = context;
	}

	public long insert(ClassSheduleDO classSheduleDO) {
		ContentValues value = new ContentValues();
		value.put(ClassSheduleDO.KEY_WEEK, classSheduleDO.getWeek());
		value.put(ClassSheduleDO.KEY_WEEK_DIFF, classSheduleDO.getWeekDiff());
		value.put(ClassSheduleDO.KEY_SUBJECT, classSheduleDO.getSubject());
		value.put(ClassSheduleDO.KEY_PLACE, classSheduleDO.getPlace());
		value.put(ClassSheduleDO.KEY_TEACHER, classSheduleDO.getTeacher());
		value.put(ClassSheduleDO.KEY_START_TIME, classSheduleDO.getStartTime());
		value.put(ClassSheduleDO.KEY_INFO, classSheduleDO.getInfo());
		value.put(ClassSheduleDO.KEY_SEMESTER_ID, classSheduleDO.getSemesterId());
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		long result = sqlite.insert(ClassSheduleDO.TABLE_NAME, null, value);
		sqlite.close();
		return result;
	}

	public boolean delete(int id) {
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		int result = sqlite.delete(ClassSheduleDO.TABLE_NAME, "id =" + id, null);
		sqlite.close();
		return result > 0;
	}

	public ArrayList<ClassSheduleDO> queryALL(int semesterId) {
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		Cursor cursor = sqlite.query(ClassSheduleDO.TABLE_NAME, ClassSheduleDO.COLUMNS, "semesterId = " + semesterId,
				null, null, null, null);
		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}
		ArrayList<ClassSheduleDO> resultList = new ArrayList<ClassSheduleDO>();
		do {
			ClassSheduleDO classSheduleDO = new ClassSheduleDO();
			this.wrapValueFromCursor(cursor, classSheduleDO);
			resultList.add(classSheduleDO);
		} while (cursor.moveToNext());
		cursor.close();
		sqlite.close();
		return resultList;
	}

	public boolean update(ClassSheduleDO classSheduleDO) {
		int id = classSheduleDO.getId();
		ContentValues value = new ContentValues();
		value.put(ClassSheduleDO.KEY_WEEK, classSheduleDO.getWeek());
		value.put(ClassSheduleDO.KEY_WEEK_DIFF, classSheduleDO.getWeekDiff());
		value.put(ClassSheduleDO.KEY_SUBJECT, classSheduleDO.getSubject());
		value.put(ClassSheduleDO.KEY_PLACE, classSheduleDO.getPlace());
		value.put(ClassSheduleDO.KEY_TEACHER, classSheduleDO.getTeacher());
		value.put(ClassSheduleDO.KEY_START_TIME, classSheduleDO.getStartTime());
		value.put(ClassSheduleDO.KEY_INFO, classSheduleDO.getInfo());
		value.put(ClassSheduleDO.KEY_SEMESTER_ID, classSheduleDO.getSemesterId());
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		int result = sqlite.update(ClassSheduleDO.TABLE_NAME, value, "id = " + id, null);
		sqlite.close();
		return result > 0;
	}

	private void wrapValueFromCursor(Cursor cursor, ClassSheduleDO classSheduleDO) {
		classSheduleDO.setId(cursor.getInt(cursor.getColumnIndex(ClassSheduleDO.KEY_ID)));
		classSheduleDO.setInfo(cursor.getString(cursor.getColumnIndex(ClassSheduleDO.KEY_INFO)));
		classSheduleDO.setPlace(cursor.getString(cursor.getColumnIndex(ClassSheduleDO.KEY_PLACE)));
		classSheduleDO.setStartTime(cursor.getLong(cursor.getColumnIndex(ClassSheduleDO.KEY_START_TIME)));
		classSheduleDO.setSubject(cursor.getString(cursor.getColumnIndex(ClassSheduleDO.KEY_SUBJECT)));
		classSheduleDO.setTeacher(cursor.getString(cursor.getColumnIndex(ClassSheduleDO.KEY_TEACHER)));
		classSheduleDO.setWeek(cursor.getString(cursor.getColumnIndex(ClassSheduleDO.KEY_WEEK)));
		classSheduleDO.setWeekDiff(cursor.getInt(cursor.getColumnIndex(ClassSheduleDO.KEY_WEEK_DIFF)));
		classSheduleDO.setSemesterId(cursor.getInt(cursor.getColumnIndex(ClassSheduleDO.KEY_SEMESTER_ID)));
	}
}