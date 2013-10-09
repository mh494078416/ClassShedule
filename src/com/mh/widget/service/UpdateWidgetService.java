package com.mh.widget.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

import com.mh.widget.ClassSheduleWidget;

public class UpdateWidgetService extends Service {
	private static final String TAG = "UpdateWidgetService";
	private static final int NOTIFER = 333;
	public static boolean FLAG = false;	// 服务是否运行的旗帜,初始值没有运行
	private static Handler myHandler;
	private LooperThread looperThread;

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.v(TAG, "onStart()");
		FLAG = true;	// 服务开始运行
		
		// 通过Handler来接受进程所传递的信息并更新TextView
		myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == NOTIFER) {
					// 创建一个AppWidgetManager的实例
					AppWidgetManager mAppWidgetManager = AppWidgetManager
							.getInstance(UpdateWidgetService.this);

					int[] appIds = mAppWidgetManager.getAppWidgetIds(new ComponentName(
							UpdateWidgetService.this, ClassSheduleWidget.class));
					// 获得RemoteViews
					RemoteViews mRemoteViews = ClassSheduleWidget
							.setClockInView(UpdateWidgetService.this);
					mAppWidgetManager.updateAppWidget(appIds, mRemoteViews);
				}
			}

		};
		looperThread = new LooperThread();
		looperThread.start();
	}

	
	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestory()1");
		FLAG = false;
		Log.v(TAG, "onDestory()2");
	}


	private static class LooperThread extends Thread {

		@Override
		public void run() {
			
			try {
				Time nowTime = new Time();
				// 时间设置为系统当前时间
				nowTime.setToNow();
				int second = nowTime.second;
				while (second != 0) {
					Thread.sleep(1000);
					nowTime.setToNow();
					second = nowTime.second;
					Log.d(TAG, "second = " + second);
				}
				while (FLAG) {
					// 发送消息给Handler对象
					Message m = new Message();
					m.what = NOTIFER;
					myHandler.sendMessage(m);
					Thread.sleep(1000 * 60);
					Log.d(TAG, "lopper running");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
