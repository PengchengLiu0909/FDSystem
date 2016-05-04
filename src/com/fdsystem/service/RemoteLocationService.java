package com.fdsystem.service;

import java.util.List;

import com.fdsystem.MyApplication;
import com.fdsystem.locate.Locate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class RemoteLocationService extends IntentService {


	TelephonyManager tm;

	public RemoteLocationService() {
		super("RemoteLocationService-Thread");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
//		SmsManager smsManager = SmsManager.getDefault();
		String number = intent.getStringExtra("number");

		Context context = getApplicationContext();
		// lm = (LocationManager)
		// context.getSystemService(Context.LOCATION_SERVICE);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// double lon = l.getLongitude(); //得到经度
		// double lat = l.getLatitude(); //得到纬度
		//
		// Geocoder coder = new Geocoder(context, Locale.getDefault());

		// try {
		// List<Address> list = coder.getFromLocation(lat, lon, 1);
		// Address a = list.get(0);
		// String address = a.getLocality();
		MyApplication app=(MyApplication)getApplication();
		String location=app.getLocation();
		Log.e("*******location:", location);
       
		SmsManager manager = SmsManager.getDefault();
		List<String >  message = manager.divideMessage("您的好友手机位置："+location);
		for(String msg: message){
			manager.sendTextMessage(number, null, msg, null, null);
		}
		
	}

}
