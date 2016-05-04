package com.fdsystem.service;

import java.io.File;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class RemoteDeleteService extends IntentService{

	ContentResolver cr;
	public RemoteDeleteService() {
		super("RemoteDeleteService-Thread");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		cr = getApplicationContext().getContentResolver();
		deleteContact();   
		deleteAudio();     
		deleteVideo();     
		deleteImage();     
	}
	
	// 删除电话本
	private void deleteContact() {
		Uri deleteUri = ContactsContract.RawContacts.CONTENT_URI
				.buildUpon()
				.appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER,
						"true").build();
		cr.delete(deleteUri, null, null);
	}
	
	// 删除音乐文件
	private void deleteAudio() {
		Cursor c = cr.query(
				android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { android.provider.MediaStore.Audio.Media.DATA },
				null, null, null);

		int count = c.getCount();
		for (int i = 0; i < count; i++) {
			c.moveToPosition(i);
			String path = c
					.getString(c
							.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA));
			File f = new File(path);
			f.delete();
		}
	}
	// 删除视频文件
	private void deleteVideo() {
		Cursor c = cr.query(
				android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { android.provider.MediaStore.Video.Media.DATA },
				null, null, null);

		int count = c.getCount();
		for (int i = 0; i < count; i++) {
			c.moveToPosition(i);
			String path = c
					.getString(c
							.getColumnIndex(android.provider.MediaStore.Video.Media.DATA));
			File f = new File(path);
			f.delete();
		}
	}
	// 删除图片
	private void deleteImage() {
		Cursor c = cr.query(android.provider.MediaStore.Images
				.Media.EXTERNAL_CONTENT_URI,
				new String[] {android.provider.MediaStore.Images.Media.DATA }, null, null, null);
		int count = c.getCount();
		for (int i = 0; i < count; i++) {
			c.moveToPosition(i);
			String path = c.getString(c.getColumnIndex(android.provider.MediaStore.Images.Media.DATA));
			File f = new File(path);
			f.delete();
		}
	}
}
