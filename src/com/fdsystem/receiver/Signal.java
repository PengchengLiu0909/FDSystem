package com.fdsystem.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/*
 *  换卡通知广播
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
		// TODO 自动生成的方法存根
		sp = context.getSharedPreferences("com.fdsystem_preferences",
				Context.MODE_WORLD_WRITEABLE);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		boolean isprotect = true;// 防盗系统开关
		System.out.println("Signal<---信号");
		System.out.println("isprotect:" + isprotect);

		String safeIMSI = sp.getString("IMSI", "");
		String safe_number = sp.getString("safe_number", "");

		String IMSI = tm.getSubscriberId();
		// 判断SIM卡是否更换
		if (IMSI.trim().equals(safeIMSI)) { // 没有更换
			return;
		} else { // SIM卡已经被更换
			SmsManager manager = SmsManager.getDefault();
			List<String> message = manager.divideMessage("手机的IMSI码为:"
					+ safeIMSI + "的手机，SIM卡已经被更换，新的IMSI码为：" + IMSI);
			for (String msg : message) {
				manager.sendTextMessage(safe_number, null, msg, null, null);
			}
		}
	}

}
