package com.fdsystem;

import android.app.Activity;
import android.os.Bundle;

/*
 *   �ֻ�����һ��
 *     �����ֻ�
 */
public class SuoDing extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.suoding);
	}
	
}