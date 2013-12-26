package com.mh.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mh.R;
import com.mh.dao.ClassSheduleDAO;
import com.mh.data.SemesterStore;
import com.mh.entity.ClassSheduleDO;

public class EditShedule extends Activity implements OnClickListener {
	private static final String TAG = "EditClass";
	private EditText etSubject;
	private EditText etPlace;
	private EditText etTeacher;
	private EditText etTime;
	private EditText etInfo;

	private ClassSheduleDAO classSheduleDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_shedule);
		final ActionBar bar = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable();
		colorDrawable.setColor(0xff000000);
		bar.setSplitBackgroundDrawable(colorDrawable);

		classSheduleDAO = new ClassSheduleDAO(EditShedule.this);

		this.initView();
	}

	private void initView() {
		etSubject = (EditText) findViewById(R.id.etSubject);
		etPlace = (EditText) findViewById(R.id.etPlace);
		etTeacher = (EditText) findViewById(R.id.etTeacher);
		etTime = (EditText) findViewById(R.id.etTime);
		etInfo = (EditText) findViewById(R.id.etInfo);
		Spinner weekSpinner = (Spinner) findViewById(R.id.week_spinner);
		ArrayAdapter<CharSequence> weekAdapter = ArrayAdapter.createFromResource(this, R.array.week_array,
				android.R.layout.simple_spinner_item);
		weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		weekSpinner.setAdapter(weekAdapter);
		weekSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView textView = (TextView) view;
				textView.setTextSize(18);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		Spinner weekDiffSpinner = (Spinner) findViewById(R.id.week_diff_spinner);
		ArrayAdapter<CharSequence> weekDiffAdapter = ArrayAdapter.createFromResource(this, R.array.week_diff_array,
				android.R.layout.simple_spinner_item);
		weekDiffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		weekDiffSpinner.setAdapter(weekDiffAdapter);
		weekDiffSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView textView = (TextView) view;
				textView.setTextSize(18);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_action_bar_list_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_done:
			ClassSheduleDO classSheduleDO = new ClassSheduleDO();
			classSheduleDO.setSubject(etSubject.getText().toString());
			classSheduleDO.setInfo(etInfo.getText().toString());
			classSheduleDO.setPlace(etPlace.getText().toString());
			classSheduleDO.setSemesterId(SemesterStore.semesterId);
			classSheduleDO.setStartTime(0);
			classSheduleDO.setTeacher(etTeacher.getText().toString());
			classSheduleDO.setWeek("");
			classSheduleDO.setWeekDiff(0);
			classSheduleDAO.insert(classSheduleDO);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
