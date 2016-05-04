package com.fdsystem;

import java.util.LinkedList;
import java.util.List;

import com.fdsystem.locate.Locate;

import android.app.Application;
import android.app.Activity;

public class MyApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>(); // 用来存放Activity对象或者其子类对象

	private Locate locate;
	private String location;
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity); // 添加到list中
	}

	// 遍历存储在list中的activity，并finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("开始定位");
		locate = new Locate(this);
		locate.locate();
		System.out.println("结束定位");
	}
	
	


}
