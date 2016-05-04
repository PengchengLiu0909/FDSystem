package com.fdsystem.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/*
 *   ���SIM���Ƿ񱻸���  ����
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
		String old_IMSI = sp.getString("IMSI", "");  //�ɵ�IMSI����
		String new_IMSI = tm.getSimSerialNumber();  //�µ�IMSI����
		String safe_number = sp.getString("safe_number","");  //�õ���ȫ�ֻ�����
		
		
		
		if(!old_IMSI.equals(new_IMSI)){
			sendSms(safe_number);
		}
		stopSelf();
	}
	
	private void sendSms(String number){
		smsManager = SmsManager.getDefault();
		
		String msg = "���ĺ����ֻ�SIM���Ѿ�������������Ҫ��ϵ���ѽ���ȷ�ϣ������ֻ����ܶ�ʧ����Э��������";
		smsManager.sendTextMessage(number, null, msg, null, null);
	}

}
