package com.fdsystem.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.fdsystem.service.RemoteAlarmService;
import com.fdsystem.service.RemoteDeleteService;
import com.fdsystem.service.RemoteLocationService;
import com.fdsystem.service.RemoteLockScreenService;

/*
 *   消息接收
 */
public class SMSReceiver extends BroadcastReceiver {

	protected AudioManager audioManager = null;

	SharedPreferences sp = null;
	TelephonyManager tm = null;
	String Tsafenumber = null;
	int Inta;

	// 当接收到短信时，onReceive方法会被调用
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO 自动生成的方法存根
		sp = context.getSharedPreferences("com.fdsystem_preferences",
				Context.MODE_WORLD_WRITEABLE);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// 判断防盗系统是否开启
		boolean isProtect = true;
		// 得到安全号码
		String safenumber = sp.getString("safe_number", "");
		safenumber = safenumber.trim();
		// 得到防盗密码
		String password = sp.getString("password", "");
		System.out.println(isProtect);

		// 检测防盗系统是否开启
		if (isProtect) {
			Inta = 0;
			System.out.println("防盗开启！");
			// 当防盗开启的时候，就可以接收短信
			Object[] object = (Object[]) intent.getSerializableExtra("pdus");
			byte[][] pdus = new byte[object.length][];
			for (int i = 0; i < pdus.length; i++) {
				pdus[i] = (byte[]) object[i];
			}

			SmsMessage[] msgs = new SmsMessage[object.length];
			for (int i = 0; i < pdus.length; i++) {
				msgs[i] = SmsMessage.createFromPdu(pdus[i]);
			}

			for (int i = 0; i < msgs.length; i++) {
				String from_number = msgs[i].getDisplayOriginatingAddress();// 得到短信来源的号码
				System.out.println("safanumber:" + safenumber);
				System.out.println("from_number:" + from_number);
				System.out.println("from_number.trim():" + from_number.trim());
				System.out.println(safenumber.trim().contains(from_number)); // 短信是否来源于安全号码

				// 短信来源号码 与安全号码进行判断
				if (from_number.trim().equals("+86" + safenumber)) {
					System.out.println("+86号码一致");
					Tsafenumber = "+86" + safenumber;
					Inta = 1;
				} else if (from_number.trim().equals(safenumber)) {
					System.out.println("原号码一致");
					Tsafenumber = safenumber;
					Inta = 1;
				}

				System.out.println(Inta);

				Editor editor = sp.edit();
				editor.putString("Ssafenumber", Tsafenumber);
				editor.commit();
				String body = msgs[i].getDisplayMessageBody(); // 得到短信内容

				if (body.contains("dingwei#" + password)) { // 消息内容中包含定位指令的话
					System.out.println("dingwei...");
					delete(context, intent, password);
					// 设置一个标记表明这个接收者应该中止当前广播，只有通过命令发送的广播才会工作
					abortBroadcast();
					// GPSService gps = new GPSService();
					// gps.getGPS(context);
					Intent intent1 = new Intent(context,
							RemoteLocationService.class);
					intent1.putExtra("number", from_number);
					context.startService(intent1);
				} else if (body.contains("suoding#" + password)) { // 包含锁定指令
					System.out.println("suoding...");
					delete(context, intent, password);
					abortBroadcast();

					context.startService(new Intent(context,
							RemoteLockScreenService.class));
				} else if (body.contains("baojing#" + password)) { // 包含报警指令
					System.out.println("baojing...");
					delete(context, intent, password);
					abortBroadcast();

					context.startService(new Intent(context,
							RemoteAlarmService.class));
					System.out.println("报警完成！");
				}

				if (Inta == 1) {

					if (body.contains("xiaohui#" + password)) { // 包含销毁指令
						System.out.println("xiaohui...");
						delete(context, intent, password);

						abortBroadcast();

						context.startService(new Intent(context,
								RemoteDeleteService.class));
					}

				}
			}
		}

	}

	public void delete(Context context, Intent intent, String password) {
		String msgContent = "";
		Bundle bundle = intent.getExtras();

		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		for (int n = 0; n < messages.length; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
			msgContent = smsMessage[n].getMessageBody();
			Log.e("msg", msgContent);
			Log.e("psw", password);
			Log.e("contain", msgContent.contains(password) + "");
			if (msgContent.contains(password)) {
				deleteSMS(context, msgContent);
			}
		}
	}

	public void deleteSMS(Context context, String smscontent) {
		Log.e("log", "删除短信：" + smscontent);
		try {
			// 准备系统短信收信箱的uri地址
			Uri uri = Uri.parse("content://sms/inbox");// 收信箱
			// 查询收信箱里所有的短信
			Cursor isRead = context.getContentResolver().query(uri, null,
					"read=" + 0, null, null);
			while (isRead.moveToNext()) {
				// String phone =
				// isRead.getString(isRead.getColumnIndex("address")).trim();//获取发信人
				String body = isRead.getString(isRead.getColumnIndex("body"))
						.trim();// 获取信息内容
				if (body.equals(smscontent)) {
					int id = isRead.getInt(isRead.getColumnIndex("_id"));

					context.getContentResolver().delete(
							Uri.parse("content://sms"), "_id=" + id, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
