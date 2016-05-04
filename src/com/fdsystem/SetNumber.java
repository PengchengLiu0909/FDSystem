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
 *   �޸İ�ȫ����
 */
public class SetNumber extends Activity {
	
	SharedPreferences  sp = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.shezhi_number);
		
		sp = this.getSharedPreferences("com.fdsystem_preferences",Context.MODE_WORLD_WRITEABLE);
		  
		final String safe_number = sp.getString("safe_number", "").trim(); //�õ�����õİ�ȫ�ֻ�����
System.out.println("safe"+safe_number);		 
		final EditText edit_number = (EditText)findViewById(R.id.number1); //�õ���ȫ����༭��
		
		Button sure_button = (Button)findViewById(R.id.sure1);   //ȷ�ϰ�ť
		Button exit_button = (Button)findViewById(R.id.cancel1); //ȡ����ť
		
		
		
		sure_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				String number = edit_number.getText().toString(); //�õ��༭��������ĺ���
System.out.println("number"+number);	
				
				if(number.trim().length() != 11 ){
					Toast.makeText(getApplication(), "������İ�ȫ���벻�Ϸ���", Toast.LENGTH_LONG).show();
				}else if(number.trim().equals(safe_number)){
					Toast.makeText(getApplication(),"����İ�ȫ�����ظ������������룡",Toast.LENGTH_LONG).show();
				}else{
					Editor editor = sp.edit();
					editor.putString("safe_number", number); //��Ҫ���õİ�ȫ���뱣�浽��ֵ����
					editor.commit(); //������ύ
					
					Toast.makeText(getApplication(), "�µİ�ȫ�������óɹ���", Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent();
					intent.setClass(SetNumber.this,SheZhi.class); //��activity��ת�����ý���
					startActivity(intent);  //ʵ����ת
				}
			}	
			
		});
		
		//����ȡ����ť��ʵ�ֽ�����ת����������ת������
		exit_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				SetNumber.this.finish(); //�����Activity  finish
			}
			
		});
	}
	
}
