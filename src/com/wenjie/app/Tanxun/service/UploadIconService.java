package com.wenjie.app.Tanxun.service;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
/**
 * 下载头像的服务
 * @author dell
 *
 */
public class UploadIconService extends IntentService {

	public UploadIconService() {
		super("uploadIconService");
		
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		String IconUrl=intent.getStringExtra("IconUrl");
		String fileName=intent.getStringExtra("fileName");
		//先在本地查找是否有头像图片,如果没有，从网络下载
		Bitmap rawBitmap=BitmapFactory.decodeFile(getApplicationContext().getCacheDir()+"/bmob/"+fileName);
		if(rawBitmap==null){
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
		}else{
			Log.d("beidi", "本地存在");
		}
	}

}
