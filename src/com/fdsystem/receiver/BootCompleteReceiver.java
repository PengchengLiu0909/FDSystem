package com.fdsystem.receiver;

import java.util.List;

import com.fdsystem.service.CheckSimChangedService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.telephony.SmsManager;

/*
 *  �ֻ�
 */
public class BootCompleteReceiver extends BroadcastReceiver {
	
	SharedPreferences sp = null;
	TelephonyManager tm = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("BootCompleteReceiver.onReceive()");
		// TODO �Զ����ɵķ������     

		
		context.startService(new 
				Intent(context,CheckSimChangedService.class));
	}

}
