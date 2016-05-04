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
		mLocationClient = new LocationClient(context); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������
		LocationClientOption option=new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);  //����λģʽ����Ϊ�߾���
		option.setCoorType("bd0911");   //���صĶ�λ����ǰٶȾ�γ��
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(false);
		mLocationClient.setLocOption(option);
		
	}

	private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			System.out.println("��ʼ�ص�");
			if (location == null){
				return;
			}
			StringBuffer sb = new StringBuffer();
			sb.append("ʱ�� : ");
			sb.append(location.getTime());
			sb.append("  ������ : ");
			sb.append(location.getLocType());
			sb.append("  γ�� : ");
			sb.append(location.getLatitude());
			sb.append("  ���� : ");
			sb.append(location.getLongitude());
			sb.append("  �뾶 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {   //����GPS���ж�λ
				sb.append("  �ٶ� : ");
				sb.append(location.getSpeed());
				sb.append("  ���� : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {   //����������ж�λ
				sb.append("  ��ַ : ");
				sb.append(location.getAddrStr());
			}
			MyApplication app=(MyApplication)context.getApplicationContext();
			app.setLocation(sb.toString());;
			Log.e("mLocation", app.getLocation());
			Log.e("locate", sb.toString());
			System.out.println(sb.toString());
			System.out.println("�����ص�");
		}

	}
	//����λ����
	public void start() {
		if (mLocationClient == null) {
			onCreate();
		}
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
			mLocationClient.requestLocation();
		}
	}
	//ֹͣ��λ
	public void stop() {
		if (mLocationClient != null)
			mLocationClient.stop();
	}

	public void locate() {
		start();
		
		mLocationClient.requestLocation();  //����λ���첽��ȡ��ǰλ��
		
	}
}
