package com.mh.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mh.entity.SemesterDO;
import com.mh.util.DbHelper;

public class SemesterDAO {
	private Context context;

	public SemesterDAO(Context context) {
		this.context = context;
	}

	public long insert(SemesterDO semesterDO) {
		ContentValues value = new ContentValues();
		value.put(SemesterDO.KEY_NAME, semesterDO.getName());
		value.put(SemesterDO.KEY_START_DATE, semesterDO.getStartDate());
		value.put(SemesterDO.KEY_END_DATE, semesterDO.getEndDate());
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		long result = sqlite.insert(SemesterDO.TABLE_NAME, null, value);
		sqlite.close();
		return result;
	}

	public boolean delete(int id) {
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		int result = sqlite.delete(SemesterDO.TABLE_NAME, "id =" + id, null);
		sqlite.close();
		return result > 0;
	}

	public ArrayList<SemesterDO> queryALL() {
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		Cursor cursor = sqlite.query(SemesterDO.TABLE_NAME, SemesterDO.COLUMNS, null, null, null, null, "id desc");
		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}
		ArrayList<SemesterDO> resultList = new ArrayList<SemesterDO>();
		while (cursor.moveToNext()) {
			SemesterDO semesterDO = new SemesterDO();
			this.wrapValueFromCursor(cursor, semesterDO);
			resultList.add(semesterDO);
		}
		cursor.close();
		sqlite.close();
		return resultList;
	}

	public boolean update(SemesterDO semesterDO) {
		int id = semesterDO.getId();
		ContentValues value = new ContentValues();
		value.put(SemesterDO.KEY_NAME, semesterDO.getName());
		value.put(SemesterDO.KEY_START_DATE, semesterDO.getStartDate());
		value.put(SemesterDO.KEY_END_DATE, semesterDO.getEndDate());
		SQLiteDatabase sqlite = new DbHelper(context).getDatabase();
		int result = sqlite.update(SemesterDO.TABLE_NAME, value, "id = " + id, null);
		sqlite.close();
		return result > 0;
	}

	private void wrapValueFromCursor(Cursor cursor, SemesterDO semesterDO) {
		semesterDO.setId(cursor.getInt(cursor.getColumnIndex(SemesterDO.KEY_ID)));
		semesterDO.setName(cursor.getString(cursor.getColumnIndex(SemesterDO.KEY_NAME)));
		semesterDO.setStartDate(cursor.getLong(cursor.getColumnIndex(SemesterDO.KEY_START_DATE)));
		semesterDO.setEndDate(cursor.getLong(cursor.getColumnIndex(SemesterDO.KEY_END_DATE)));
	}
}