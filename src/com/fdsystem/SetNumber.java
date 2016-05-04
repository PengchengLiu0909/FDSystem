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
 *   修改安全号码
 */
public class SetNumber extends Activity {
	
	SharedPreferences  sp = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.shezhi_number);
		
		sp = this.getSharedPreferences("com.fdsystem_preferences",Context.MODE_WORLD_WRITEABLE);
		  
		final String safe_number = sp.getString("safe_number", "").trim(); //得到保存好的安全手机号码
System.out.println("safe"+safe_number);		 
		final EditText edit_number = (EditText)findViewById(R.id.number1); //得到安全号码编辑框
		
		Button sure_button = (Button)findViewById(R.id.sure1);   //确认按钮
		Button exit_button = (Button)findViewById(R.id.cancel1); //取消按钮
		
		
		
		sure_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String number = edit_number.getText().toString(); //得到编辑框中输入的号码
System.out.println("number"+number);	
				
				if(number.trim().length() != 11 ){
					Toast.makeText(getApplication(), "你输入的安全号码不合法！", Toast.LENGTH_LONG).show();
				}else if(number.trim().equals(safe_number)){
					Toast.makeText(getApplication(),"输入的安全号码重复，请重新输入！",Toast.LENGTH_LONG).show();
				}else{
					Editor editor = sp.edit();
					editor.putString("safe_number", number); //将要设置的安全号码保存到键值对中
					editor.commit(); //将结果提交
					
					Toast.makeText(getApplication(), "新的安全号码设置成功！", Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent();
					intent.setClass(SetNumber.this,SheZhi.class); //将activity跳转到设置界面
					startActivity(intent);  //实现跳转
				}
			}	
			
		});
		
		//单击取消按钮，实现界面跳转，将界面跳转到设置
		exit_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				SetNumber.this.finish(); //将这个Activity  finish
			}
			
		});
	}
	
}
