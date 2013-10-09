package com.mh.widget;

import java.util.Date;

import com.mh.R;
import com.mh.activity.MainView;
import com.mh.util.MyDBHelper;
import com.mh.util.MyDateHelper;
import com.mh.util.NumHelper;
import com.mh.widget.service.UpdateWidgetService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ClassSheduleWidget extends AppWidgetProvider {

	private static final String TAG = "ClassSheduleWidget";
	private static final String INTENTACTION = "com.mh.updateWidgetAction";

	@Override
	public void onEnabled(Context context) {
		// 开启一个服务，更新widget时间
		context.startService(new Intent(context, UpdateWidgetService.class));
		Log.v(TAG, "onEnabled()");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.v(TAG, "onUpdate()");
		// 设置时钟，并获得RemoteViews
		RemoteViews remoteView = setClockInView(context);
		// 设置widget下方的文字的日期
		setWidgetDate(remoteView);

		// 在widget上设置课程表信息
		setCurrentClass(remoteView, context);

		// 设置右侧点击区域的响应
		Intent intent = new Intent(context, MainView.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent mainViewIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		remoteView.setOnClickPendingIntent(R.id.TextInfoRightHotArea,
				mainViewIntent);

		// 设置左侧点击区域的响应
		Intent sendActionIntent = new Intent(INTENTACTION);
		PendingIntent updatePendingIntent = PendingIntent.getBroadcast(context,
				0, sendActionIntent, 0);
		remoteView.setOnClickPendingIntent(R.id.TextInfoLeftHotArea,
				updatePendingIntent);

		appWidgetManager.updateAppWidget(appWidgetIds, remoteView);

	}

	// 设置widget下方的日期和星期
	private void setWidgetDate(RemoteViews remoteView) {
		Date today = new Date();
		String dateString = MyDateHelper.getDateString(today);
		remoteView.setTextViewText(R.id.widgetDateTV, dateString);
		String weekNameString = MyDateHelper.getWeekDate(today);
		remoteView.setTextViewText(R.id.widgetWeekTV, weekNameString);
	}
	
	// 在widget 上设置当前的课程
	private void setCurrentClass(RemoteViews view, Context context) {
		String weekName = MyDateHelper.getWeekDate(new Date()); // 星期几
		
		MyDBHelper dbHelper = new MyDBHelper(context);
		// 开启数据库链接
		dbHelper.open();
		Cursor cursor1 = dbHelper.findCurrentClass(weekName);
		if(cursor1 != null && cursor1.getCount() > 0) {
			String sequence1 = cursor1.getString(2);	//根据现在时间得到现在上的课是第几节
			String subject1 = cursor1.getString(3);
			String place1 = cursor1.getString(4);
			view.setTextViewText(R.id.sequenceTV1, sequence1);
			view.setTextViewText(R.id.subjectTV1, subject1);
			view.setTextViewText(R.id.placeTV1, place1);
			// 如果当前有课，则widget上下方显示下一节课程
			String numStr = sequence1.substring(1, 2);
			Log.v(TAG, "numStr = " + numStr);
			int seqNum = NumHelper.stringToNum(numStr);
			String nextClassStr = "第" + NumHelper.numToString(seqNum + 1) + "节";
			Log.v(TAG, "nextClass = " + nextClassStr);
			Cursor cursor2 = dbHelper.query(weekName, nextClassStr);
			if(cursor2 != null && cursor2.getCount() > 0) {
				String sequence2 = cursor2.getString(2);
				String subject2 = cursor2.getString(3);
				String place2 = cursor2.getString(4);
				view.setTextViewText(R.id.sequenceTV2, sequence2);
				view.setTextViewText(R.id.subjectTV2, subject2);
				view.setTextViewText(R.id.placeTV2, place2);
			} else {
				view.setTextViewText(R.id.sequenceTV2, "");
				view.setTextViewText(R.id.subjectTV2, "没有课程");
				view.setTextViewText(R.id.placeTV2, "");
			}
		} else {
			view.setTextViewText(R.id.sequenceTV1, "");
			view.setTextViewText(R.id.placeTV1, "");
			view.setTextViewText(R.id.subjectTV1, "没有课程");
			// 如果当前没有课，则显示今天的最近一节课
			Cursor aCursor = dbHelper.getTodayLeftClass(weekName);
			if(aCursor != null && aCursor.getCount() > 0) {
				String sequence2 = aCursor.getString(2);
				String subject2 = aCursor.getString(3);
				String place2 = aCursor.getString(4);
				view.setTextViewText(R.id.sequenceTV2, sequence2);
				view.setTextViewText(R.id.subjectTV2, subject2);
				view.setTextViewText(R.id.placeTV2, place2);
			} else {
				view.setTextViewText(R.id.sequenceTV2, "");
				view.setTextViewText(R.id.subjectTV2, "没有课程");
				view.setTextViewText(R.id.placeTV2, "");
			}
		}
		// 关闭数据库链接
		dbHelper.close();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction().equals(INTENTACTION)) {
			// 设置时钟时间
			RemoteViews views = setClockInView(context);
			// 设置课程
			setCurrentClass(views, context);
			// 设置widget下方的日期和星期
			setWidgetDate(views);
			
			AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(context);
			//获得RemoteViews
			int[] appIds = mAppWidgetManager.getAppWidgetIds(new ComponentName(context, ClassSheduleWidget.class));
			mAppWidgetManager.updateAppWidget(appIds, views);		//点击widget的左侧区域，更新widget
			
			// 检查更新widget时间的服务是否开启
			if(UpdateWidgetService.FLAG) {
				
			} else {
				// 开启一个服务，更新widget时间
				context.startService(new Intent(context, UpdateWidgetService.class));
			}
			
		}
		
	}

	@Override
	public void onDisabled(Context context) {
		context.stopService(new Intent(context, UpdateWidgetService.class));
		Log.v(TAG, "onDisabled()");
	}

	public static RemoteViews setClockInView(Context context) {
		Time nowTime = new Time();
		// 时间设置为系统当前时间
		nowTime.setToNow();
		int hour = nowTime.hour;
		int minute = nowTime.minute;
		int leftHour = hour / 10;
		int rightHour = hour % 10;
		int leftMinute = minute / 10;
		int rightMinute = minute % 10;
		String packageName = context.getPackageName();
		// 时间显示在Widget上
		RemoteViews mRemoteViews = new RemoteViews(packageName,
				R.layout.widget_4x1);
		switch (leftHour) {
		case 0:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n0);
			break;
		case 1:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n1);
			break;
		case 2:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n2);
			break;
		case 3:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n3);
			break;
		case 4:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n4);
			break;
		case 5:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n5);
			break;
		case 6:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n6);
			break;
		case 7:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n7);
			break;
		case 8:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n8);
			break;
		case 9:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n9);
			break;
		default:
			mRemoteViews.setImageViewResource(R.id.HourLeftNumber,
					R.drawable.n0);
		}

		switch (rightHour) {
		case 0:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n0);
			break;
		case 1:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n1);
			break;
		case 2:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n2);
			break;
		case 3:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n3);
			break;
		case 4:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n4);
			break;
		case 5:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n5);
			break;
		case 6:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n6);
			break;
		case 7:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n7);
			break;
		case 8:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n8);
			break;
		case 9:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n9);
			break;
		default:
			mRemoteViews.setImageViewResource(R.id.HourRightNumber,
					R.drawable.n0);
		}

		switch (leftMinute) {
		case 0:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n0);
			break;
		case 1:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n1);
			break;
		case 2:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n2);
			break;
		case 3:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n3);
			break;
		case 4:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n4);
			break;
		case 5:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n5);
			break;
		case 6:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n6);
			break;
		case 7:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n7);
			break;
		case 8:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n8);
			break;
		case 9:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n9);
			break;
		default:
			mRemoteViews.setImageViewResource(R.id.MinuteLeftNumber,
					R.drawable.n0);
		}

		switch (rightMinute) {
		case 0:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n0);
			break;
		case 1:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n1);
			break;
		case 2:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n2);
			break;
		case 3:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n3);
			break;
		case 4:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n4);
			break;
		case 5:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n5);
			break;
		case 6:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n6);
			break;
		case 7:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n7);
			break;
		case 8:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n8);
			break;
		case 9:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n9);
			break;
		default:
			mRemoteViews.setImageViewResource(R.id.MinuteRightNumber,
					R.drawable.n0);
		}

		return mRemoteViews;

	}

}