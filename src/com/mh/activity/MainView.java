package com.mh.activity;

import android.app.ActionBar;
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
import com.mh.util.MyConstant;

public class MainView extends FragmentActivity {
	private static final String TAG = MainView.class.getClass().getSimpleName();

	ViewPager mViewPager;
	ShedulePagerAdapter shedulePagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final ActionBar bar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable();
		colorDrawable.setColor(0xff000000);
		bar.setSplitBackgroundDrawable(colorDrawable);
		shedulePagerAdapter = new ShedulePagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(shedulePagerAdapter);
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
		case R.id.menu_new:
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class ShedulePagerAdapter extends FragmentStatePagerAdapter {

		public ShedulePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new SheduleFragment();
			Bundle args = new Bundle();
			args.putInt(SheduleFragment.ARG_WEEK, i + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return MyConstant.WEEKNAME.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return MyConstant.WEEKNAME[position];
		}
	}

}
