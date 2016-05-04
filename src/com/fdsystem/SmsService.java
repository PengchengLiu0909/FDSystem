package com.fdsystem;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
/*
 *   消息服务
 */
public class SmsService extends Service {
	
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		IntentFilter filter = new IntentFilter(ACTION);
		//设置优先权
		filter.setPriority(2147483647);
		MyBrocast myService = new MyBrocast();
		//注册消息接收服务
		registerReceiver(myService, filter);
	}
	
	private class MyBrocast extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 自动生成的方法存根
			System.out.println("receiver message --->>>");
			abortBroadcast();
		}
		
	}

}
