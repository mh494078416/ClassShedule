package com.mh.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mh.entity.ClassSheduleDO;
import com.mh.entity.SemesterDO;

public class DbHelper {

	private static final String DATABASE_NAME = "class_shedule"; // 数据库名

	private static final int DATABASE_VERSION = 1; // 数据库版本号

	private Context context;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SemesterDO.CREATE_SQL);
			db.execSQL(ClassSheduleDO.CREATE_SQL);

			ContentValues value = new ContentValues();
			value.put(SemesterDO.KEY_NAME, "第一学期");
			db.insert(SemesterDO.TABLE_NAME, null, value);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (newVersion != oldVersion) {
				db.execSQL("drop table if exists " + SemesterDO.TABLE_NAME);
				db.execSQL("drop table if exists " + ClassSheduleDO.TABLE_NAME);
				onCreate(db);
			}
		}
	}

	public DbHelper(Context context) {
		this.context = context;
	}

	public SQLiteDatabase getDatabase() {
		return new DatabaseHelper(context).getWritableDatabase();
	}
}