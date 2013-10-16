package com.mh.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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

		shedulePagerAdapter = new ShedulePagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(shedulePagerAdapter);
	}

	public static class ShedulePagerAdapter extends
			FragmentStatePagerAdapter {

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
