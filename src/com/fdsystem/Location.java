package com.fdsystem;

import android.app.Activity;
import android.os.Bundle;
/*
 *   �ֻ�����һ��   
 *    ��λ�ֻ�
 */
public class Location extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.dingwei);
	}
	
}
