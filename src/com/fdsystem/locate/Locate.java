package com.fdsystem.locate;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.fdsystem.MyApplication;

public class Locate {

	public Locate(Context context) {
		this.context=context;
		onCreate();
	}

	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();

	private Context context;
	

	public void onCreate() {
		mLocationClient = new LocationClient(context); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option=new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);  //将定位模式设置为高精度
		option.setCoorType("bd0911");   //返回的定位结果是百度经纬度
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(false);
		mLocationClient.setLocOption(option);
		
	}

	private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			System.out.println("开始回调");
			if (location == null){
				return;
			}
			StringBuffer sb = new StringBuffer();
			sb.append("时间 : ");
			sb.append(location.getTime());
			sb.append("  错误码 : ");
			sb.append(location.getLocType());
			sb.append("  纬度 : ");
			sb.append(location.getLatitude());
			sb.append("  经度 : ");
			sb.append(location.getLongitude());
			sb.append("  半径 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {   //采用GPS进行定位
				sb.append("  速度 : ");
				sb.append(location.getSpeed());
				sb.append("  卫星 : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {   //采用网络进行定位
				sb.append("  地址 : ");
				sb.append(location.getAddrStr());
			}
			MyApplication app=(MyApplication)context.getApplicationContext();
			app.setLocation(sb.toString());;
			Log.e("mLocation", app.getLocation());
			Log.e("locate", sb.toString());
			System.out.println(sb.toString());
			System.out.println("结束回调");
		}

	}
	//发起定位请求
	public void start() {
		if (mLocationClient == null) {
			onCreate();
		}
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
			mLocationClient.requestLocation();
		}
	}
	//停止定位
	public void stop() {
		if (mLocationClient != null)
			mLocationClient.stop();
	}

	public void locate() {
		start();
		
		mLocationClient.requestLocation();  //发起定位，异步获取当前位置
		
	}
}
