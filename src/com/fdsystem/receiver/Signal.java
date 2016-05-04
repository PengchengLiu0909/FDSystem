package com.fdsystem.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/*
 *  ����֪ͨ�㲥
 */
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class Signal extends BroadcastReceiver {

	SharedPreferences sp = null;
	TelephonyManager tm = null;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("Signal.onReceive()");
		// TODO �Զ����ɵķ������
		sp = context.getSharedPreferences("com.fdsystem_preferences",
				Context.MODE_WORLD_WRITEABLE);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		boolean isprotect = true;// ����ϵͳ����
		System.out.println("Signal<---�ź�");
		System.out.println("isprotect:" + isprotect);

		String safeIMSI = sp.getString("IMSI", "");
		String safe_number = sp.getString("safe_number", "");

		String IMSI = tm.getSubscriberId();
		// �ж�SIM���Ƿ����
		if (IMSI.trim().equals(safeIMSI)) { // û�и���
			return;
		} else { // SIM���Ѿ�������
			SmsManager manager = SmsManager.getDefault();
			List<String> message = manager.divideMessage("�ֻ���IMSI��Ϊ:"
					+ safeIMSI + "���ֻ���SIM���Ѿ����������µ�IMSI��Ϊ��" + IMSI);
			for (String msg : message) {
				manager.sendTextMessage(safe_number, null, msg, null, null);
			}
		}
	}

}
