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
 *   ��Ϣ����
 */
public class SMSReceiver extends BroadcastReceiver {

	protected AudioManager audioManager = null;

	SharedPreferences sp = null;
	TelephonyManager tm = null;
	String Tsafenumber = null;
	int Inta;

	// �����յ�����ʱ��onReceive�����ᱻ����
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO �Զ����ɵķ������
		sp = context.getSharedPreferences("com.fdsystem_preferences",
				Context.MODE_WORLD_WRITEABLE);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// �жϷ���ϵͳ�Ƿ���
		boolean isProtect = true;
		// �õ���ȫ����
		String safenumber = sp.getString("safe_number", "");
		safenumber = safenumber.trim();
		// �õ���������
		String password = sp.getString("password", "");
		System.out.println(isProtect);

		// ������ϵͳ�Ƿ���
		if (isProtect) {
			Inta = 0;
			System.out.println("����������");
			// ������������ʱ�򣬾Ϳ��Խ��ն���
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
				String from_number = msgs[i].getDisplayOriginatingAddress();// �õ�������Դ�ĺ���
				System.out.println("safanumber:" + safenumber);
				System.out.println("from_number:" + from_number);
				System.out.println("from_number.trim():" + from_number.trim());
				System.out.println(safenumber.trim().contains(from_number)); // �����Ƿ���Դ�ڰ�ȫ����

				// ������Դ���� �밲ȫ��������ж�
				if (from_number.trim().equals("+86" + safenumber)) {
					System.out.println("+86����һ��");
					Tsafenumber = "+86" + safenumber;
					Inta = 1;
				} else if (from_number.trim().equals(safenumber)) {
					System.out.println("ԭ����һ��");
					Tsafenumber = safenumber;
					Inta = 1;
				}

				System.out.println(Inta);

				Editor editor = sp.edit();
				editor.putString("Ssafenumber", Tsafenumber);
				editor.commit();
				String body = msgs[i].getDisplayMessageBody(); // �õ���������

				if (body.contains("dingwei#" + password)) { // ��Ϣ�����а�����λָ��Ļ�
					System.out.println("dingwei...");
					delete(context, intent, password);
					// ����һ����Ǳ������������Ӧ����ֹ��ǰ�㲥��ֻ��ͨ������͵Ĺ㲥�ŻṤ��
					abortBroadcast();
					// GPSService gps = new GPSService();
					// gps.getGPS(context);
					Intent intent1 = new Intent(context,
							RemoteLocationService.class);
					intent1.putExtra("number", from_number);
					context.startService(intent1);
				} else if (body.contains("suoding#" + password)) { // ��������ָ��
					System.out.println("suoding...");
					delete(context, intent, password);
					abortBroadcast();

					context.startService(new Intent(context,
							RemoteLockScreenService.class));
				} else if (body.contains("baojing#" + password)) { // ��������ָ��
					System.out.println("baojing...");
					delete(context, intent, password);
					abortBroadcast();

					context.startService(new Intent(context,
							RemoteAlarmService.class));
					System.out.println("������ɣ�");
				}

				if (Inta == 1) {

					if (body.contains("xiaohui#" + password)) { // ��������ָ��
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
		Log.e("log", "ɾ�����ţ�" + smscontent);
		try {
			// ׼��ϵͳ�����������uri��ַ
			Uri uri = Uri.parse("content://sms/inbox");// ������
			// ��ѯ�����������еĶ���
			Cursor isRead = context.getContentResolver().query(uri, null,
					"read=" + 0, null, null);
			while (isRead.moveToNext()) {
				// String phone =
				// isRead.getString(isRead.getColumnIndex("address")).trim();//��ȡ������
				String body = isRead.getString(isRead.getColumnIndex("body"))
						.trim();// ��ȡ��Ϣ����
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
