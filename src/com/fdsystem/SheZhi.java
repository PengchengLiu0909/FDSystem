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
 *   设置主界面
 */
public class SheZhi extends ListActivity{
	private static final int setPassword = 0;
	private static final int setNumber = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		((MyApplication)getApplication()).addActivity(this);
		setContentView(R.layout.shezhi);
		
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		
		map1.put("shezhi", "修改防盗密码");
		map1.put("img", R.drawable.password_setting);
		map1.put("jiantou",R.drawable.jiantou);
		
		map2.put("shezhi", "修改安全号码");
		map2.put("img", R.drawable.number_setting);
		map2.put("jiantou", R.drawable.jiantou);
		
		list.add(map1);
		list.add(map2);
		
		SimpleAdapter listAdapter = new SimpleAdapter(this,list,R.layout.shezhilist,
				new String[]{"shezhi","img","jiantou"},
				new int[] {R.id.szxx,R.id.sztb,R.id.szjt});
		setListAdapter(listAdapter);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO 自动生成的方法存根
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent();
		if(position == setPassword){
			intent.setClass(SheZhi.this, SetPassword.class);  //跳转到密码修改
			SheZhi.this.startActivity(intent);
		}else if(position == setNumber){
			intent.setClass(SheZhi.this,SetNumber.class);     //跳转到安全号码修改
			SheZhi.this.startActivity(intent);
		}
	}
	
	

}
