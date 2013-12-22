package com.mh.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mh.R;
import com.mh.dao.ClassSheduleDAO;
import com.mh.dao.SemesterDAO;
import com.mh.data.ClassSheduleMap;

public class MainView extends FragmentActivity {
	private static final String TAG = MainView.class.getClass().getSimpleName();
	private ClassSheduleDAO classSheduleDAO;
	private SemesterDAO semesterDAO;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final ActionBar bar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable();
		colorDrawable.setColor(0xff000000);
		bar.setSplitBackgroundDrawable(colorDrawable);
		ShedulePagerAdapter shedulePagerAdapter = new ShedulePagerAdapter(this.getSupportFragmentManager());
		classSheduleDAO = new ClassSheduleDAO(MainView.this);
		semesterDAO = new SemesterDAO(MainView.this);
		ClassSheduleMap.init(classSheduleDAO, semesterDAO);
		ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(shedulePagerAdapter);
		mViewPager.setCurrentItem(2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_list_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new:
			Intent intent = new Intent();
			intent.setClass(this, EditShedule.class);
			this.startActivity(intent);
			break;
		case R.id.action_semester:
			break;
		case R.id.action_setting:
			break;
		case R.id.action_about:
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class ShedulePagerAdapter extends FragmentStatePagerAdapter {
		public ShedulePagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new SheduleFragment();
			Bundle args = new Bundle();
			String day = ClassSheduleMap.getDay(i);
			args.putString(SheduleFragment.ARG_DAY, day);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 14; // 最多显示两周的课程
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return ClassSheduleMap.getDisplayDay(position);
		}
	}

}
