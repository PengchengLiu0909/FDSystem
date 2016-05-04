package com.fdsystem;

import android.app.Activity;
import android.os.Bundle;
/*
 *   防盗系统
 *   查看模块（防盗指令）
 */
public class ChaKan extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.chakan);//加载查看xml文件
	}
	
}
