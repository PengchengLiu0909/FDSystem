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
 *   应用程序的入口
 */

public class MainActivity extends ListActivity {

	boolean isFirst; // 判断是否第一次登入系统
	boolean isDialog;
	SharedPreferences sp;
	AlertDialog showPutPWDialog; // 显示输入密码的对话框

	private static final int tiyan = 0; // 防盗体验
	private static final int chakan = 1; // 功能查看
	private static final int shezhi = 2; // 系统设置



	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		((MyApplication)getApplication()).addActivity(this);

		sp = this.getSharedPreferences("com.fdsystem_preferences",
				MODE_WORLD_READABLE);// 可读
		isFirst = sp.getBoolean("first", true); // first 不存在时返回true ，说明这是第一次进入系统
		isDialog = sp.getBoolean("Dialog", false); // isDialog == false

		if (isFirst) {
			Intent intent = new Intent(); // 建立一个Intent对象
			intent.setClass(MainActivity.this, FirstIn.class); // 如果是第一次使用该系统的话，首先跳转到FirstIn.class
			startActivity(intent); // 跳转
		} else {
			if (isDialog) {
				// 展现输入输入密码对话框
				putPWDialog();
			} else {
				init();
				Editor editor = sp.edit(); // 调用edit()方法获取Editor对象。用来写数据
				editor.putBoolean("Dialog", true);
				editor.commit(); // 将更改保存到首选项文件中
			}
			setContentView(R.layout.activity_main); // 加载main界面
		}
		

	}

	private void putPWDialog() {
		// TODO 自动生成的方法存根
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.putpwdialog, null);

		final EditText edit_password = (EditText) view
				.findViewById(R.id.password3); // 获得对话框中的密码输入框
		Button sure_button = (Button) view.findViewById(R.id.sure1); // 得到对话框中的密码确认按钮
		Button exit_button = (Button) view.findViewById(R.id.exit1); // 得到对话框中的密码取消按钮

		AlertDialog.Builder builder = new AlertDialog.Builder(this); //
		builder.setTitle("请输入防盗密码！");
		builder.setView(view); // 设置对话框的视图
		showPutPWDialog = builder.create();
		showPutPWDialog.show(); // 将对话框显示出来
		// 为确认按钮添加事件监听器
		sure_button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String password = edit_password.getText().toString();// 得到密码输入框中的内容
				String savaPassword = sp.getString("password", "");// 得到设置好的密码
				if (password.trim().equals("")) { // 如果密码输入框为空
					Toast.makeText(MainActivity.this, "密码不能为空，请输入！",
							Toast.LENGTH_LONG).show(); // 显示提示信息
					return;
				} else if (password.trim().equals(savaPassword)) { // 密码一致
					showPutPWDialog.dismiss(); //
					init();
				} else {
					Toast.makeText(MainActivity.this, "密码错误,请重新输入！",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		// 为退出按钮添加事件监听器
		exit_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				((MyApplication)getApplication()).exit();// 退出程序
			}
		});
	}

	private void init() {
		// TODO 自动生成的方法存根
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// 生成三个HashMap对象，用来存储键值对
		HashMap<String, Object> map1 = new HashMap<String, Object>(); // 用来存储体验功能的键值对
		HashMap<String, Object> map2 = new HashMap<String, Object>(); // 用来存储命令查看功能的键值对
		HashMap<String, Object> map3 = new HashMap<String, Object>(); // 用来存储设置

		map1.put("fangdao", "防盗功能一览");
		map1.put("jianjie", "了解防盗系统提供哪些功能");
		map1.put("img", R.drawable.tiyan);
		map1.put("jiantou", R.drawable.jiantou);

		map2.put("fangdao", "防盗指令查看");
		map2.put("jianjie", "查看防盗系统的远程控制指令");
		map2.put("img", R.drawable.chakan);
		map2.put("jiantou", R.drawable.jiantou);

		map3.put("fangdao", "防盗系统设置");
		map3.put("jianjie", "设置新的防盗密码和安全号码");
		map3.put("img", R.drawable.shezhi);
		map3.put("jiantou", R.drawable.jiantou);
		// 将三个HashMap对象存储到list中
		list.add(map1);
		list.add(map2);
		list.add(map3);

		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.mainlist, new String[] { "fangdao", "jianjie", "img",
						"jiantou" }, new int[] { R.id.fdxx, R.id.fdjj,
						R.id.fdtb, R.id.fdjt });

		setListAdapter(listAdapter);
	}

	// 为每一项添加事件
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO 自动生成的方法存根
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent();
		if (position == tiyan) {
			intent.setClass(MainActivity.this, TiYan.class); // 跳转到防盗体验模块
			MainActivity.this.startActivity(intent);
		} else if (position == chakan) {
			intent.setClass(MainActivity.this, ChaKan.class); // 跳转到查看指令模块
			MainActivity.this.startActivity(intent);
		} else if (position == shezhi) {
			intent.setClass(MainActivity.this, SheZhi.class); // 跳转到设置模块
			MainActivity.this.startActivity(intent);
		}
	}



}
