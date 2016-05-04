package com.fdsystem.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/*
 *   检查SIM卡是否被更换  服务
 */

public class CheckSimChangedService extends IntentService{


	private TelephonyManager tm;
	private SmsManager smsManager;
	private SharedPreferences sp;
	public CheckSimChangedService() {
		super("CheckSimChangedService-Thread");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onHandleIntent(Intent intent) {
		System.out.println("CheckSimChangedService.onHandleIntent()");
		Context context = getApplicationContext();
		sp = context.getSharedPreferences("com.fdsystem_preferences", Context.MODE_WORLD_WRITEABLE);
		   
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		//String oldSimNumber = util.getSimNumber();
		String old_IMSI = sp.getString("IMSI", "");  //旧的IMSI号码
		String new_IMSI = tm.getSimSerialNumber();  //新的IMSI号码
		String safe_number = sp.getString("safe_number","");  //得到安全手机号码
		
		
		
		if(!old_IMSI.equals(new_IMSI)){
			sendSms(safe_number);
		}
		stopSelf();
	}
	
	private void sendSms(String number){
		smsManager = SmsManager.getDefault();
		
		String msg = "您的好友手机SIM卡已经被更换，你需要联系好友进行确认，否则手机可能丢失，请协助！！！";
		smsManager.sendTextMessage(number, null, msg, null, null);
	}

}
