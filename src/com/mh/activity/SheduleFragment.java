package com.mh.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mh.R;
import com.mh.dao.ClassSheduleDAO;
import com.mh.data.ClassSheduleMap;
import com.mh.entity.ClassSheduleDO;
import com.mh.util.NumHelper;

public class SheduleFragment extends Fragment implements OnItemClickListener {
	private static final String TAG = SheduleFragment.class.getClass().getSimpleName();
	private static final int EDIT = 332;
	private static final int DEL = 8983;
	public static final String ARG_DAY = "day";
	private ListView listView;
	public String currentDay;
	private ArrayList<Map<String, String>> listItem = new ArrayList<Map<String, String>>();
	private SimpleAdapter listItemAdapter;
	private ClassSheduleDAO classSheduleDAO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
		Bundle args = getArguments();
		String day = args.getString(ARG_DAY);
		listView = (ListView) rootView.findViewById(R.id.classList);
		listView.setOnCreateContextMenuListener(this);
		listView.setOnItemClickListener(this);
		currentDay = day;
		listItemAdapter = new SimpleAdapter(this.getActivity(), listItem, R.layout.class_list, new String[] {
				"ItemTitle", "ItemText" }, new int[] { R.id.ItemTitle, R.id.ItemText });
		listView.setAdapter(listItemAdapter);
		classSheduleDAO = new ClassSheduleDAO(this.getActivity());
		this.refreshDateInListView(currentDay);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int location, long arg3) {
		String sequence = "第" + NumHelper.numToString(location + 1) + "节";
		String week = "";
		String subject = "";
		String place = "";
		String teacher = "";
		String time = "";
		String info = "";
		String str = sequence + "\n" + "课程名：" + subject + "\n" + "教室：" + place + "\n" + "老师：" + teacher + "\n" + "时间："
				+ time + "\n" + "备注：" + info;
		new AlertDialog.Builder(this.getActivity()).setTitle(week).setMessage(str).create().show();
	}

	public void refreshDateInListView(String day) {
		Log.v(TAG, "refreshDateInListView " + day);
		listItem.clear();
		List<ClassSheduleDO> classSheduleDOList = ClassSheduleMap.get(day);
		for (ClassSheduleDO classSheduleDO : classSheduleDOList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ItemTitle", "");
			listItem.add(map);
		}
		listItemAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.v(TAG, "onContextItemSelected, currentWeekDayNum : " + currentDay);
		if (currentDay.hashCode() == item.getGroupId()) {
			String weekName = "";
			// 第几节
			int selectPosition = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
			if (item.getItemId() == EDIT) {
				Intent intent = new Intent();
				intent.setClass(this.getActivity(), EditShedule.class);
				intent.putExtra("weekName", weekName);
				intent.putExtra("position", selectPosition);
				startActivity(intent);
			}
			if (item.getItemId() == DEL) {
				classSheduleDAO.delete(0);
				refreshDateInListView(currentDay);
				Toast.makeText(this.getActivity(), "删除成功", Toast.LENGTH_LONG).show();
			}
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		Log.v(TAG, "onCreateContextMenu");
		menu.setHeaderTitle("请选择");
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int selectPosition = info.position;
		String num = NumHelper.numToString(selectPosition + 1);
		menu.add(currentDay.hashCode(), DEL, 2, "删除");
		menu.add(currentDay.hashCode(), EDIT, 0, "编辑");
	}

}
