package com.fdsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
 *   修改防盗密码
 */
public class SetPassword extends Activity {
	
	SharedPreferences sp = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.shezhi_password);
		
		sp = this.getSharedPreferences("com.fdsystem_preferences",Context.MODE_WORLD_WRITEABLE);
		final String password = sp.getString("password", "").trim();  //得到保存好的防盗密码
		//得到三个密码编辑框
		final EditText edit_old_password = (EditText)findViewById(R.id.old_password);
		final EditText edit_new_password = (EditText)findViewById(R.id.new_password);
		final EditText edit_sure_password = (EditText)findViewById(R.id.sure_password);
		//得到两个按钮
		Button sure = (Button)findViewById(R.id.sure);
		Button cancel = (Button)findViewById(R.id.cancel);
		
		sure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				String old_password = edit_old_password.getText().toString().trim();
				String new_password = edit_new_password.getText().toString().trim();
				String sure_password = edit_sure_password.getText().toString().trim();
				
				if(!old_password.equals(password)){
					Toast.makeText(getApplication(), "输入旧的防盗密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
					return ;
				}else if(new_password.equals("") || sure_password.equals("")){
					Toast.makeText(getApplication(), "新的防盗密码不能为空！", Toast.LENGTH_LONG).show();
					return ;
				}else if(!new_password.equals(sure_password)){
					Toast.makeText(getApplication(), "输入新的防盗密码不一致，请重新输入",Toast.LENGTH_LONG).show();
					return ;
				}else if(new_password.length() < 6 || new_password.length() > 12){
					Toast.makeText(getApplication(), "输入的防盗密码不合法,请重新设置！",Toast.LENGTH_LONG).show();
					return ;
				}else if(new_password.equals(old_password)){
					Toast.makeText(getApplication(),"新的密码与原来的密码一致，请重新设置！",Toast.LENGTH_LONG).show();
					return ;
				}
				else{
					Editor editor = sp.edit();
					editor.putString("password", new_password); //将新密码设置为防盗密码
					editor.commit();
					Toast.makeText(getApplication(),"设置新的防盗密码成功！",Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent();
					intent.setClass(SetPassword.this, MainActivity.class); //跳转到主界面
					SetPassword.this.startActivity(intent);
				}
			}
			
		});
		
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				intent.setClass(SetPassword.this,SheZhi.class); //跳转到设置界面
				SetPassword.this.startActivity(intent);
			}
			
		});
	}
	
}
