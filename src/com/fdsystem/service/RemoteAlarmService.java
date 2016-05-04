package com.fdsystem.service;

//import com.amaker.pg.R;
//import com.amaker.pg.R.raw;
import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.fdsystem.R;

public class RemoteAlarmService extends IntentService{

	MediaPlayer mp;
	public static final long TIMEOUT = 1000*60*2;
	public RemoteAlarmService() {
		super("RemoteAlarmService-Tread");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		mp = MediaPlayer.create(getApplicationContext(), R.raw.alarmsound3);
		mp.setLooping(true);
		mp.setVolume(1.0f, 1.0f);
		mp.start();
		Log.e("start:",System.currentTimeMillis()+"");
		Log.e("stop:",System.currentTimeMillis()+TIMEOUT+"");
		
		long stopTime = System.currentTimeMillis()+TIMEOUT;
		System.out.println(System.currentTimeMillis()+"---"+stopTime);
		while(System.currentTimeMillis()<stopTime){
		}
		System.out.println(System.currentTimeMillis()+"---"+stopTime);
		mp.setLooping(false);
		mp.stop();
	}

}
