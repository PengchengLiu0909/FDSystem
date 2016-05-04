package com.fdsystem;

import android.app.Activity;
import android.os.Bundle;
/*
 *   手机功能一览
 *     换卡通知
 */
public class ChangeSIMCard extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.huanka);
	}
		
}
