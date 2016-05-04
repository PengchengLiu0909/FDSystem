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
 *   手机防盗功能一览模块
 */
public class TiYan extends ListActivity{
	private static final int dingwei = 0;
	private static final int xiaohui = 1;
	private static final int suoding = 2;
	private static final int baojing = 3;
	private static final int huanka = 4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		
		((MyApplication)getApplication()).addActivity(this); //将这个Activity添加到Myapplication对象实例容器中
		setContentView(R.layout.tiyan);
		
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		HashMap<String,Object> map3 = new HashMap<String,Object>();
		HashMap<String,Object> map4 = new HashMap<String,Object>();
		HashMap<String,Object> map5 = new HashMap<String,Object>();
		
		map1.put("tiyan", "定位手机");
		map1.put("jianjie", "获取手机位置");
		map1.put("img", R.drawable.tiyan_list_tubiao);
		map1.put("jiantou",R.drawable.jiantou);
		
		map2.put("tiyan", "删除数据");
		map2.put("jianjie","删除手机数据");
		map2.put("img", R.drawable.tiyan_list_tubiao);
		map2.put("jiantou",R.drawable.jiantou);
		
		map3.put("tiyan", "锁定手机");
		map3.put("jianjie","将手机屏幕锁定");
		map3.put("img", R.drawable.tiyan_list_tubiao);
		map3.put("jiantou",R.drawable.jiantou);
		
		map4.put("tiyan", "手机报警");
		map4.put("jianjie", "手机发出警报");
		map4.put("img", R.drawable.tiyan_list_tubiao);
		map4.put("jiantou",R.drawable.jiantou);
		
		map5.put("tiyan","换卡通知");
		map5.put("jianjie", "手机换卡后，会将通知安全手机号码");
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
	//为体验列表添加事件，每单击一项就会跳转到功能说明模块
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO 自动生成的方法存根
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent();
		if(position== dingwei){
			intent.setClass(TiYan.this, Location.class);  //跳转到定位功能一览
			TiYan.this.startActivity(intent);
		}else if(position == xiaohui){
			intent .setClass(TiYan.this, DeleteData.class); //跳转到删除数据功能一览
			TiYan.this.startActivity(intent);
		}else if(position == suoding){
			intent.setClass(TiYan.this,SuoDing.class);  //跳转到锁定功能一览
			TiYan.this.startActivity(intent);
		}else if(position == baojing){ 
			intent.setClass(TiYan.this, Alarm.class);     //跳转到警报功能一览
			TiYan.this.startActivity(intent);
		}else if(position == huanka) {
			intent.setClass(TiYan.this,ChangeSIMCard.class);  //跳转到换卡通知功能一览
			TiYan.this.startActivity(intent);
		}
		
	}	
}
