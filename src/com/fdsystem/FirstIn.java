package com.fdsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
 *   第一次登陆系统时进入的模块
 *   提供： 防盗密码设置 和 安全号码设置
 */
public class FirstIn extends Activity {
	
	//boolean isFirst;  //用来判断是不是第一次进入系统
	SharedPreferences sp;  //用来保存数据
	TelephonyManager tm;    //提供进入电话服务的信息
	private SmsManager smsManager ;   //当用户设置安全号码后，会向安全号码发送提示信息
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_in); //加载first_in.xml文件
		//将该activity添加到MyApplication对象的实例容器中
		((MyApplication)getApplication()).addActivity(this);
		
		sp = this.getSharedPreferences("com.fdsystem_preferences", MODE_WORLD_READABLE); //得到sharedPreference对象
		//得到设备电话服务的管理
		tm = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
		//得到国际移动用户识别码
		final String IMSI = tm.getSubscriberId();
		
		/*
		 * 取得界面中的各个组件
		 */
		final EditText edit_password_01 = (EditText)findViewById(R.id.edittext_password01);//得到第一个密码输入框
		final EditText edit_password_02 = (EditText)findViewById(R.id.edittext_password02);//得到第二个密码输入框
		final EditText edit_safe_number = (EditText)findViewById(R.id.edittext_number);  //得到安全号码输入框
		
		Button button_sure = (Button)findViewById(R.id.sure_button); //得到确认按钮
		Button button_exit = (Button)findViewById(R.id.exit_button); //得到退出按钮
		
		//  为确定按钮绑定事件监听器
		button_sure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String password1 = edit_password_01.getText().toString();
				String password2 = edit_password_02.getText().toString();
				String safe_number = edit_safe_number.getText().toString();
				//对防盗密码和安全号码进行一系列判断
				if(password1.trim().equals("")||password2.trim().equals("")){
					Toast.makeText(FirstIn.this, "密码不能为空！",Toast.LENGTH_LONG).show();
					return ;
				}else if(password1.length() < 6 || password1.length() > 12){
					Toast.makeText(FirstIn.this, "请输入6--12位的密码",Toast.LENGTH_LONG).show();
					return ;
				}else if(!password1.trim().equals(password2.trim())){
					Toast.makeText(FirstIn.this,"输入的密码不一致,请再次输入！", Toast.LENGTH_LONG).show();
					return ;
				}else if(safe_number.trim().equals("")){
					Toast.makeText(FirstIn.this,"安全号码不能为空！请输入！", Toast.LENGTH_LONG).show();
					return ;
				}else if(safe_number.trim().length()!=11){
					Toast.makeText(FirstIn.this,"输入的安全号码有误！", Toast.LENGTH_LONG).show();
					return ;
				}else{
					Editor editor = sp.edit();
					editor.putString("IMSI", IMSI); //将IMSI存储到sharedPreferences
					editor.putString("password",password1); //将密码存储
					editor.putString("safe_number", safe_number);//将安全号码存储
					//改变isFirst，设为false
					editor.putBoolean("first", false); //非第一次打开系统时不会进入此activity
					
					TelephonyManager mTelephonyMgr = (TelephonyManager)
							 getSystemService(Context.TELEPHONY_SERVICE); 
							String num = mTelephonyMgr.getLine1Number();
					editor.putString("my_number", num);//将当前号码存储
							
					editor.commit();  //将保存的数据进行提交
					//向安全号码发出提示信息，告知好友其号码已被设置为安全号码
					smsManager = SmsManager.getDefault();
					String msg = "您的好友已经将您的号码设置为安全号码，您可以协助好友保护隐私信息甚至找回手机！";
					smsManager.sendTextMessage(safe_number, null, msg, null, null);
					
					Intent intent = new Intent();
					intent.setClass(FirstIn.this,MainActivity.class); //将界面跳转到主界面
					FirstIn.this.startActivity(intent);
					
				}
			}
			
		});
		
		//退出按钮绑定事件监听器
		button_exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				((MyApplication)getApplication()).exit();   //退出程序
			}
		});
	}
	
}
