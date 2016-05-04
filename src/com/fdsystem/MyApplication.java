package com.fdsystem;

import java.util.LinkedList;
import java.util.List;

import com.fdsystem.locate.Locate;

import android.app.Application;
import android.app.Activity;

public class MyApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>(); // �������Activity����������������

	private Locate locate;
	private String location;
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity); // ��ӵ�list��
	}

	// �����洢��list�е�activity����finish
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("��ʼ��λ");
		locate = new Locate(this);
		locate.locate();
		System.out.println("������λ");
	}
	
	


}
