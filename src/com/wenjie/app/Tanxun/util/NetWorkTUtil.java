package com.wenjie.app.Tanxun.util;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接工具类
 * @author dell
 *
 */
public class NetWorkTUtil {
	/**
	 * 网络连接是否有效
	 * @return
	 */
	public static boolean isNetAvailable(Activity activity){
		ConnectivityManager connectionManager=(ConnectivityManager)
				activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectionManager.getActiveNetworkInfo();
		if(networkInfo!=null && networkInfo.isAvailable()){
			return true;
		}else{
			return false;
		}
	}
}
