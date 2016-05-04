package com.fdsystem;

import android.app.Activity;
import android.os.Bundle;

/*
 *   手机功能一览
 *     锁定手机
 */
public class SuoDing extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.suoding);
	}
	
}
