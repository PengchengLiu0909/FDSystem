package com.fdsystem;

import java.util.ArrayList;
import java.util.HashMap;

import com.fdsystem.locate.Locate;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/*
 *   Ӧ�ó�������
 */

public class MainActivity extends ListActivity {

	boolean isFirst; // �ж��Ƿ��һ�ε���ϵͳ
	boolean isDialog;
	SharedPreferences sp;
	AlertDialog showPutPWDialog; // ��ʾ��������ĶԻ���

	private static final int tiyan = 0; // ��������
	private static final int chakan = 1; // ���ܲ鿴
	private static final int shezhi = 2; // ϵͳ����



	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		((MyApplication)getApplication()).addActivity(this);

		sp = this.getSharedPreferences("com.fdsystem_preferences",
				MODE_WORLD_READABLE);// �ɶ�
		isFirst = sp.getBoolean("first", true); // first ������ʱ����true ��˵�����ǵ�һ�ν���ϵͳ
		isDialog = sp.getBoolean("Dialog", false); // isDialog == false

		if (isFirst) {
			Intent intent = new Intent(); // ����һ��Intent����
			intent.setClass(MainActivity.this, FirstIn.class); // ����ǵ�һ��ʹ�ø�ϵͳ�Ļ���������ת��FirstIn.class
			startActivity(intent); // ��ת
		} else {
			if (isDialog) {
				// չ��������������Ի���
				putPWDialog();
			} else {
				init();
				Editor editor = sp.edit(); // ����edit()������ȡEditor��������д����
				editor.putBoolean("Dialog", true);
				editor.commit(); // �����ı��浽��ѡ���ļ���
			}
			setContentView(R.layout.activity_main); // ����main����
		}
		

	}

	private void putPWDialog() {
		// TODO �Զ����ɵķ������
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.putpwdialog, null);

		final EditText edit_password = (EditText) view
				.findViewById(R.id.password3); // ��öԻ����е����������
		Button sure_button = (Button) view.findViewById(R.id.sure1); // �õ��Ի����е�����ȷ�ϰ�ť
		Button exit_button = (Button) view.findViewById(R.id.exit1); // �õ��Ի����е�����ȡ����ť

		AlertDialog.Builder builder = new AlertDialog.Builder(this); //
		builder.setTitle("������������룡");
		builder.setView(view); // ���öԻ������ͼ
		showPutPWDialog = builder.create();
		showPutPWDialog.show(); // ���Ի�����ʾ����
		// Ϊȷ�ϰ�ť����¼�������
		sure_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				String password = edit_password.getText().toString();// �õ�����������е�����
				String savaPassword = sp.getString("password", "");// �õ����úõ�����
				if (password.trim().equals("")) { // ������������Ϊ��
					Toast.makeText(MainActivity.this, "���벻��Ϊ�գ������룡",
							Toast.LENGTH_LONG).show(); // ��ʾ��ʾ��Ϣ
					return;
				} else if (password.trim().equals(savaPassword)) { // ����һ��
					showPutPWDialog.dismiss(); //
					init();
				} else {
					Toast.makeText(MainActivity.this, "�������,���������룡",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		// Ϊ�˳���ť����¼�������
		exit_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				((MyApplication)getApplication()).exit();// �˳�����
			}
		});
	}

	private void init() {
		// TODO �Զ����ɵķ������
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// ��������HashMap���������洢��ֵ��
		HashMap<String, Object> map1 = new HashMap<String, Object>(); // �����洢���鹦�ܵļ�ֵ��
		HashMap<String, Object> map2 = new HashMap<String, Object>(); // �����洢����鿴���ܵļ�ֵ��
		HashMap<String, Object> map3 = new HashMap<String, Object>(); // �����洢����

		map1.put("fangdao", "��������һ��");
		map1.put("jianjie", "�˽����ϵͳ�ṩ��Щ����");
		map1.put("img", R.drawable.tiyan);
		map1.put("jiantou", R.drawable.jiantou);

		map2.put("fangdao", "����ָ��鿴");
		map2.put("jianjie", "�鿴����ϵͳ��Զ�̿���ָ��");
		map2.put("img", R.drawable.chakan);
		map2.put("jiantou", R.drawable.jiantou);

		map3.put("fangdao", "����ϵͳ����");
		map3.put("jianjie", "�����µķ�������Ͱ�ȫ����");
		map3.put("img", R.drawable.shezhi);
		map3.put("jiantou", R.drawable.jiantou);
		// ������HashMap����洢��list��
		list.add(map1);
		list.add(map2);
		list.add(map3);

		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.mainlist, new String[] { "fangdao", "jianjie", "img",
						"jiantou" }, new int[] { R.id.fdxx, R.id.fdjj,
						R.id.fdtb, R.id.fdjt });

		setListAdapter(listAdapter);
	}

	// Ϊÿһ������¼�
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO �Զ����ɵķ������
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent();
		if (position == tiyan) {
			intent.setClass(MainActivity.this, TiYan.class); // ��ת����������ģ��
			MainActivity.this.startActivity(intent);
		} else if (position == chakan) {
			intent.setClass(MainActivity.this, ChaKan.class); // ��ת���鿴ָ��ģ��
			MainActivity.this.startActivity(intent);
		} else if (position == shezhi) {
			intent.setClass(MainActivity.this, SheZhi.class); // ��ת������ģ��
			MainActivity.this.startActivity(intent);
		}
	}



}
