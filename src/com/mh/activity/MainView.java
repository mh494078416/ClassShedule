package com.mh.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mh.R;
import com.mh.util.MyDBHelper;
import com.mh.util.NumHelper;
import com.mh.util.MyDateHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends Activity implements OnItemSelectedListener,
		OnCreateContextMenuListener, OnItemClickListener {
	private static final String TAG = "MainView";
	private static final int NUMCLASS_DAY = 12;
	private static final int EDIT = 332;
	private static final int COPY = 235;
	private static final int DEL = 8983;
	private static final int PASTE = 328;
	private static boolean isCalledAfterOnCreate;

	private static String subjectCOPY = "";
	private static String placeCOPY = "";
	private static String teacherCOPY = "";
	private static String infoCOPY = "";
	private static boolean COPY_OK = false;
	
	private MenuItem aboutMenuItem; // 关于
	private MenuItem reSetMenuItem; // 重置

	private Gallery gallery;
	private ListView listView;
	ArrayList<Map<String, String>> listItem;

	public int currentWeekDayNum;

	private String[] weekName = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",
			"星期日" };
	// 分别对应数字1 2 3 4 5 6 0
	MyDBHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gallery = (Gallery) findViewById(R.id.head_gallery);
		listView = (ListView) findViewById(R.id.classList);
		listView.setOnCreateContextMenuListener(this);
		listView.setOnItemClickListener(this);
		
		ButtonAdapter buttonadapter = new ButtonAdapter(this);
		gallery.setAdapter(buttonadapter);

		gallery.setBackgroundResource(R.drawable.gallerybg);
		gallery.setOnItemSelectedListener(this);
		Date todayDate = new Date();
		int dayOfWeek = MyDateHelper.getWeekNum(todayDate);
		currentWeekDayNum = dayOfWeek;
		Log.v(TAG, "currentWeekDayNum = " + currentWeekDayNum);
		dbHelper = new MyDBHelper(this);
		isCalledAfterOnCreate = true;
		// gallery.setSelection(currentWeekDayNum-1);
		// setDateInListView(currentWeekDayNum); // 显示星期几的课程

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "onResume()");
		if(isCalledAfterOnCreate) {
			Log.v(TAG, "currentWeekDayNum = " + currentWeekDayNum);
			gallery.setSelection(currentWeekDayNum - 1);
			Log.v(TAG, "isCalledAfterOnCreate");
		} else {
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
			Log.v(TAG, "isCalledAfterOnCreate else");
		}
		isCalledAfterOnCreate = false;
	}

	public void setDateInListView(int weekNum) {
		listItem = new ArrayList<Map<String, String>>();
		// 此处需要从数据库中读取课程表数据

		for (int i = 1; i <= NUMCLASS_DAY; i++) {
			Map<String, String> map = new HashMap<String, String>();
			String sequence = "第" + NumHelper.numToString(i) + "节";
			map.put("ItemTitle", sequence);

			Cursor cursor = dbHelper.open().query(NumHelper.numToWeek(weekNum),
					sequence);
			dbHelper.close();
			if (cursor != null && cursor.getCount() > 0) {
				int count = cursor.getCount();
				Log.v(TAG, "count = " + count);
				
				String subject = cursor.getString(3);
				String place = cursor.getString(4);
				String str = subject + "(" + place + ")";
				map.put("ItemText", str);
			}
			listItem.add(map);
		}

		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,
				R.layout.class_list, new String[] { "ItemTitle", "ItemText" },
				new int[] { R.id.ItemTitle, R.id.ItemText });
		listView.setAdapter(listItemAdapter);

	}

	public class ButtonAdapter extends BaseAdapter {
		LayoutInflater inflater;
		int mGalleryItemBackground;
		private Context mContext;
		TextView weekNameView;

		public ButtonAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return weekName.length; // 一周七天
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			inflater = LayoutInflater.from(mContext);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.toolbar_row, null);
				weekNameView = (TextView) convertView
						.findViewById(R.id.bar_title);
				convertView.setTag(weekNameView);
			} else {
				weekNameView = (TextView) convertView.getTag();
			}
			if (position >= 0 && position < 7) {
				weekNameView.setText(weekName[position]);
			}
			return convertView;

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Log.v(TAG, "position" + arg2);
		if (arg2 == 0) {
			// 星期一
			currentWeekDayNum = 1;
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
		}
		if (arg2 == 1) {
			// 星期二
			currentWeekDayNum = 2;
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
		}
		if (arg2 == 2) {
			// 星期三
			currentWeekDayNum = 3;
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
		}
		if (arg2 == 3) {
			// 星期四
			currentWeekDayNum = 4;
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
		}
		if (arg2 == 4) {
			// 星期五
			currentWeekDayNum = 5;
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
		}
		if (arg2 == 5) {
			// 星期六
			currentWeekDayNum = 6;
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
		}
		if (arg2 == 6) {
			// 星期日
			currentWeekDayNum = 7;
			setDateInListView(currentWeekDayNum); // 显示星期几的课程
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("请选择");
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        int selectPosition = info.position;
        String num = NumHelper.numToString(selectPosition + 1);
		String sequence = "第" + num + "节";
		String weekName = MyDateHelper.getWeekString(currentWeekDayNum);
		Cursor cursor = dbHelper.open().query(weekName, sequence);
		// 如果此记录有值，则另有复制和删除的选项
		if(cursor != null && cursor.getCount() > 0) {
			menu.add(0, COPY, 1, "复制");
			menu.add(0, DEL , 2, "删除");
		}

		menu.add(0, EDIT, 0, "编辑");
		// 如果COPY_OK是真，则有复制的选项
		if(COPY_OK) {
			menu.add(0, PASTE, 3, "粘贴");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		aboutMenuItem = menu.add("关于");
		reSetMenuItem = menu.add("重置");
		aboutMenuItem.setIcon(android.R.drawable.ic_menu_info_details);
		reSetMenuItem.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		String weekName = ""; // 星期几
		weekName = MyDateHelper.getWeekString(currentWeekDayNum);
		// 第几节
		int selectPosition = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
		String num = NumHelper.numToString(selectPosition + 1);
		String sequence = "第" + num + "节";
		if (item.getItemId() == EDIT) {
			Intent intent = new Intent();
			intent.setClass(MainView.this, EditClass.class);
			intent.putExtra("weekName", weekName);
			intent.putExtra("position", selectPosition);
			startActivity(intent);
		}
		
		if(item.getItemId() == COPY) {
			Cursor cursor = dbHelper.open().query(weekName, sequence);
			if(cursor != null && cursor.getCount() > 0) {
				subjectCOPY = cursor.getString(3);
				placeCOPY = cursor.getString(4);
				teacherCOPY = cursor.getString(5);
				infoCOPY = cursor.getString(7);
				COPY_OK = true;
				Toast.makeText(this, "已复制", Toast.LENGTH_SHORT).show();
				
			}
			
		}
		if(item.getItemId() == DEL) {
			Log.v(TAG, "weekName = " + weekName);
			Log.v(TAG, "sequence = " + sequence);
			dbHelper.open().delete(weekName, sequence);
			dbHelper.close();
			setDateInListView(currentWeekDayNum);
			Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
		}
		if(item.getItemId() == PASTE) {
			// 复制过来的时间不合适，所以startTime 为 空串
			String startTime = "";
			dbHelper.open().insert(weekName, sequence, subjectCOPY, placeCOPY, teacherCOPY, startTime, infoCOPY);
			dbHelper.close();
			Toast.makeText(this, "粘贴成功", Toast.LENGTH_SHORT).show();
			setDateInListView(currentWeekDayNum);
			COPY_OK = false;
			subjectCOPY = "";
			placeCOPY = "";
			teacherCOPY = "";
			infoCOPY = "";
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item == aboutMenuItem) {
			String str = "谢谢使用 大学课表 v1.0" + "\n" + "作者： 马航" + "\n"
					+ "如有问题和建议，欢迎指出，我会尽快改进" + "\n" + "QQ:494078416" + "\n"
					+ "msn:mahang2008@live.cn";
			new AlertDialog.Builder(this).setTitle("关于").setMessage(str)
					.create().show();
		}
		
		
		if (item == reSetMenuItem) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示").setMessage("此操作将会删除课程表的所有数据，您确认要重建课表吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dbHelper.open().onUpgrade();
									dbHelper.close();
									setDateInListView(currentWeekDayNum); // 显示星期几的课程
									Toast.makeText(MainView.this, "课表已经重建",
											Toast.LENGTH_LONG).show();
								}
							}).setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}

							}).create().show();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int location,
			long arg3) {
		Log.v(TAG, "click the " + location);
		String sequence = "第" + NumHelper.numToString(location + 1) + "节";
		String week = NumHelper.numToWeek(currentWeekDayNum);
		Cursor cursor = dbHelper.open().query(week, sequence);
		dbHelper.close();
		if (cursor != null && cursor.getCount() > 0) {
			String subject = cursor.getString(3);
			String place = cursor.getString(4);
			String teacher = cursor.getString(5);
			String time = cursor.getString(6);
			String info = cursor.getString(7);
			String str = sequence + "\n" + "课程名：" + subject + "\n" + "教室："
					+ place + "\n" + "老师：" + teacher + "\n" + "时间：" + time
					+ "\n" + "备注：" + info;
			new AlertDialog.Builder(this).setTitle(week).setMessage(str)
					.create().show();
		} else {
			Toast.makeText(this, "当前没有课程", Toast.LENGTH_SHORT).show();
		}

	}



}
