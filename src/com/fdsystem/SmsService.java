package com.fdsystem;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
/*
 *   ��Ϣ����
 */
public class SmsService extends Service {
	
	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	public void onCreate() {
		// TODO �Զ����ɵķ������
		IntentFilter filter = new IntentFilter(ACTION);
		//��������Ȩ
		filter.setPriority(2147483647);
		MyBrocast myService = new MyBrocast();
		//ע����Ϣ���շ���
		registerReceiver(myService, filter);
	}
	
	private class MyBrocast extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO �Զ����ɵķ������
			System.out.println("receiver message --->>>");
			abortBroadcast();
		}
		
	}

}
