package com.fdsystem;

import android.app.Activity;
import android.os.Bundle;
/*
 *   ����ϵͳ
 *   �鿴ģ�飨����ָ�
 */
public class ChaKan extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.chakan);//���ز鿴xml�ļ�
	}
	
}
