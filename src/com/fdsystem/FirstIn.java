package com.fdsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
 *   ��һ�ε�½ϵͳʱ�����ģ��
 *   �ṩ�� ������������ �� ��ȫ��������
 */
public class FirstIn extends Activity {
	
	//boolean isFirst;  //�����ж��ǲ��ǵ�һ�ν���ϵͳ
	SharedPreferences sp;  //������������
	TelephonyManager tm;    //�ṩ����绰�������Ϣ
	private SmsManager smsManager ;   //���û����ð�ȫ����󣬻���ȫ���뷢����ʾ��Ϣ
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_in); //����first_in.xml�ļ�
		//����activity��ӵ�MyApplication�����ʵ��������
		((MyApplication)getApplication()).addActivity(this);
		
		sp = this.getSharedPreferences("com.fdsystem_preferences", MODE_WORLD_READABLE); //�õ�sharedPreference����
		//�õ��豸�绰����Ĺ���
		tm = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
		//�õ������ƶ��û�ʶ����
		final String IMSI = tm.getSubscriberId();
		
		/*
		 * ȡ�ý����еĸ������
		 */
		final EditText edit_password_01 = (EditText)findViewById(R.id.edittext_password01);//�õ���һ�����������
		final EditText edit_password_02 = (EditText)findViewById(R.id.edittext_password02);//�õ��ڶ������������
		final EditText edit_safe_number = (EditText)findViewById(R.id.edittext_number);  //�õ���ȫ���������
		
		Button button_sure = (Button)findViewById(R.id.sure_button); //�õ�ȷ�ϰ�ť
		Button button_exit = (Button)findViewById(R.id.exit_button); //�õ��˳���ť
		
		//  Ϊȷ����ť���¼�������
		button_sure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				String password1 = edit_password_01.getText().toString();
				String password2 = edit_password_02.getText().toString();
				String safe_number = edit_safe_number.getText().toString();
				//�Է�������Ͱ�ȫ�������һϵ���ж�
				if(password1.trim().equals("")||password2.trim().equals("")){
					Toast.makeText(FirstIn.this, "���벻��Ϊ�գ�",Toast.LENGTH_LONG).show();
					return ;
				}else if(password1.length() < 6 || password1.length() > 12){
					Toast.makeText(FirstIn.this, "������6--12λ������",Toast.LENGTH_LONG).show();
					return ;
				}else if(!password1.trim().equals(password2.trim())){
					Toast.makeText(FirstIn.this,"��������벻һ��,���ٴ����룡", Toast.LENGTH_LONG).show();
					return ;
				}else if(safe_number.trim().equals("")){
					Toast.makeText(FirstIn.this,"��ȫ���벻��Ϊ�գ������룡", Toast.LENGTH_LONG).show();
					return ;
				}else if(safe_number.trim().length()!=11){
					Toast.makeText(FirstIn.this,"����İ�ȫ��������", Toast.LENGTH_LONG).show();
					return ;
				}else{
					Editor editor = sp.edit();
					editor.putString("IMSI", IMSI); //��IMSI�洢��sharedPreferences
					editor.putString("password",password1); //������洢
					editor.putString("safe_number", safe_number);//����ȫ����洢
					//�ı�isFirst����Ϊfalse
					editor.putBoolean("first", false); //�ǵ�һ�δ�ϵͳʱ��������activity
					
					TelephonyManager mTelephonyMgr = (TelephonyManager)
							 getSystemService(Context.TELEPHONY_SERVICE); 
							String num = mTelephonyMgr.getLine1Number();
					editor.putString("my_number", num);//����ǰ����洢
							
					editor.commit();  //����������ݽ����ύ
					//��ȫ���뷢����ʾ��Ϣ����֪����������ѱ�����Ϊ��ȫ����
					smsManager = SmsManager.getDefault();
					String msg = "���ĺ����Ѿ������ĺ�������Ϊ��ȫ���룬������Э�����ѱ�����˽��Ϣ�����һ��ֻ���";
					smsManager.sendTextMessage(safe_number, null, msg, null, null);
					
					Intent intent = new Intent();
					intent.setClass(FirstIn.this,MainActivity.class); //��������ת��������
					FirstIn.this.startActivity(intent);
					
				}
			}
			
		});
		
		//�˳���ť���¼�������
		button_exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				((MyApplication)getApplication()).exit();   //�˳�����
			}
		});
	}
	
}
