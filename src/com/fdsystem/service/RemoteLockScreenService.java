package com.fdsystem.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;




import android.widget.Toast;

//import com.amaker.pg.util.ConfigUtil;
import com.fdsystem.R;

public class RemoteLockScreenService extends Service {
	EditText et;
	WindowManager wm;
	View v;
	SharedPreferences sp;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@SuppressWarnings("deprecation")
	boolean check(){
		
		String password = et.getText().toString();   //输入的密码
		Context context = this.getApplicationContext();
		
		sp = context.getSharedPreferences("com.fdsystem_preferences", MODE_WORLD_READABLE);
		String password1 = sp.getString("password", "");  //得到保存的密码
		
		if(password.equals(password1)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Context context = getApplicationContext();
		wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater inflater = LayoutInflater.from(context);
		
		v = inflater.inflate(R.layout.lock_screen, null);
		
		Button btn = (Button)v.findViewById(R.id.ok_button1);
		et = (EditText)v.findViewById(R.id.password_editText1);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(check()){
					stopSelf();
				}else{
					Toast.makeText(getApplicationContext(), "密码错误!", Toast.LENGTH_LONG).show();
				}
			}
		});

		WindowManager.LayoutParams params = new
			WindowManager.LayoutParams();

		params.width=-1;
		params.height=-1;
		
		params.flags = 1280;
		params.type = 2002;
		

		wm.addView(v, params);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(wm!=null&&v!=null){
			wm.removeView(v);
		}
	}

}
