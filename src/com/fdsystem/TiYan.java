package com.fdsystem;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/*
 *   �ֻ���������һ��ģ��
 */
public class TiYan extends ListActivity{
	private static final int dingwei = 0;
	private static final int xiaohui = 1;
	private static final int suoding = 2;
	private static final int baojing = 3;
	private static final int huanka = 4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		
		((MyApplication)getApplication()).addActivity(this); //�����Activity��ӵ�Myapplication����ʵ��������
		setContentView(R.layout.tiyan);
		
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		HashMap<String,Object> map3 = new HashMap<String,Object>();
		HashMap<String,Object> map4 = new HashMap<String,Object>();
		HashMap<String,Object> map5 = new HashMap<String,Object>();
		
		map1.put("tiyan", "��λ�ֻ�");
		map1.put("jianjie", "��ȡ�ֻ�λ��");
		map1.put("img", R.drawable.tiyan_list_tubiao);
		map1.put("jiantou",R.drawable.jiantou);
		
		map2.put("tiyan", "ɾ������");
		map2.put("jianjie","ɾ���ֻ�����");
		map2.put("img", R.drawable.tiyan_list_tubiao);
		map2.put("jiantou",R.drawable.jiantou);
		
		map3.put("tiyan", "�����ֻ�");
		map3.put("jianjie","���ֻ���Ļ����");
		map3.put("img", R.drawable.tiyan_list_tubiao);
		map3.put("jiantou",R.drawable.jiantou);
		
		map4.put("tiyan", "�ֻ�����");
		map4.put("jianjie", "�ֻ���������");
		map4.put("img", R.drawable.tiyan_list_tubiao);
		map4.put("jiantou",R.drawable.jiantou);
		
		map5.put("tiyan","����֪ͨ");
		map5.put("jianjie", "�ֻ������󣬻Ὣ֪ͨ��ȫ�ֻ�����");
		map5.put("img", R.drawable.tiyan_list_tubiao);
		map5.put("jiantou",R.drawable.jiantou);
		
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,R.layout.tiyanlist,
				new String[]{"tiyan","jianjie","img","jiantou"},
				new int[]{R.id.tyxx,R.id.tyjj,R.id.tytb,R.id.tyjt});
		
		setListAdapter(listAdapter);
	}
	//Ϊ�����б�����¼���ÿ����һ��ͻ���ת������˵��ģ��
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO �Զ����ɵķ������
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent();
		if(position== dingwei){
			intent.setClass(TiYan.this, Location.class);  //��ת����λ����һ��
			TiYan.this.startActivity(intent);
		}else if(position == xiaohui){
			intent .setClass(TiYan.this, DeleteData.class); //��ת��ɾ�����ݹ���һ��
			TiYan.this.startActivity(intent);
		}else if(position == suoding){
			intent.setClass(TiYan.this,SuoDing.class);  //��ת����������һ��
			TiYan.this.startActivity(intent);
		}else if(position == baojing){ 
			intent.setClass(TiYan.this, Alarm.class);     //��ת����������һ��
			TiYan.this.startActivity(intent);
		}else if(position == huanka) {
			intent.setClass(TiYan.this,ChangeSIMCard.class);  //��ת������֪ͨ����һ��
			TiYan.this.startActivity(intent);
		}
		
	}	
}
