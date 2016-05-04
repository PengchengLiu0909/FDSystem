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
 *   �޸ķ�������
 */
public class SetPassword extends Activity {
	
	SharedPreferences sp = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.shezhi_password);
		
		sp = this.getSharedPreferences("com.fdsystem_preferences",Context.MODE_WORLD_WRITEABLE);
		final String password = sp.getString("password", "").trim();  //�õ�����õķ�������
		//�õ���������༭��
		final EditText edit_old_password = (EditText)findViewById(R.id.old_password);
		final EditText edit_new_password = (EditText)findViewById(R.id.new_password);
		final EditText edit_sure_password = (EditText)findViewById(R.id.sure_password);
		//�õ�������ť
		Button sure = (Button)findViewById(R.id.sure);
		Button cancel = (Button)findViewById(R.id.cancel);
		
		sure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				String old_password = edit_old_password.getText().toString().trim();
				String new_password = edit_new_password.getText().toString().trim();
				String sure_password = edit_sure_password.getText().toString().trim();
				
				if(!old_password.equals(password)){
					Toast.makeText(getApplication(), "����ɵķ������벻һ�£����������룡", Toast.LENGTH_LONG).show();
					return ;
				}else if(new_password.equals("") || sure_password.equals("")){
					Toast.makeText(getApplication(), "�µķ������벻��Ϊ�գ�", Toast.LENGTH_LONG).show();
					return ;
				}else if(!new_password.equals(sure_password)){
					Toast.makeText(getApplication(), "�����µķ������벻һ�£�����������",Toast.LENGTH_LONG).show();
					return ;
				}else if(new_password.length() < 6 || new_password.length() > 12){
					Toast.makeText(getApplication(), "����ķ������벻�Ϸ�,���������ã�",Toast.LENGTH_LONG).show();
					return ;
				}else if(new_password.equals(old_password)){
					Toast.makeText(getApplication(),"�µ�������ԭ��������һ�£����������ã�",Toast.LENGTH_LONG).show();
					return ;
				}
				else{
					Editor editor = sp.edit();
					editor.putString("password", new_password); //������������Ϊ��������
					editor.commit();
					Toast.makeText(getApplication(),"�����µķ�������ɹ���",Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent();
					intent.setClass(SetPassword.this, MainActivity.class); //��ת��������
					SetPassword.this.startActivity(intent);
				}
			}
			
		});
		
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent();
				intent.setClass(SetPassword.this,SheZhi.class); //��ת�����ý���
				SetPassword.this.startActivity(intent);
			}
			
		});
	}
	
}
