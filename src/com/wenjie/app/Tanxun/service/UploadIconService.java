package com.wenjie.app.Tanxun.service;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class UploadIconService extends IntentService {

	public UploadIconService() {
		super("uploadIconService");
		
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		String IconUrl=intent.getStringExtra("IconUrl");
		String fileName=intent.getStringExtra("fileName");
		BmobFile bmobFile=new BmobFile(fileName,"",IconUrl);
		bmobFile.download(this, new DownloadFileListener() {
			
			@Override
			public void onSuccess(String arg0) {
				Log.d("Upload", "下载保存成功!");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.d("Upload", "下载保存失败!");
			}
		});
	}

}
