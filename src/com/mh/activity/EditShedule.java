package com.mh.activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mh.R;
import com.mh.dao.ClassSheduleDAO;

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
		Log.v(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_shedule);
		classSheduleDAO = new ClassSheduleDAO(EditShedule.this);

		TextView tvBaseInfo = (TextView) this.findViewById(R.id.tvBaseInfo);
	}

	private void showTimePickerDialog() {
		TimePickerDialog dlg = new TimePickerDialog(this, timeSetListener, 00, 00, true);
		dlg.setTitle("请输入时间");
		dlg.show();
	}

	@Override
	public void onClick(View v) {
	}

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String timeStr = "";
			String minuteStr = "";
			String hourStr = "";
			if (hourOfDay < 10) {
				hourStr = "0" + hourOfDay;
			} else {
				hourStr = "" + hourOfDay;
			}
			if (minute < 10) {
				minuteStr = "0" + minute;
			} else {
				minuteStr = "" + minute;
			}
			timeStr = hourStr + ":" + minuteStr;
			etTime.setText(timeStr);
		}
	};
}
