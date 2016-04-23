package com.wenjie.app.Tanxun.util;

import java.io.File;
import java.math.BigDecimal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * 清除缓存管理器
 * @author dell
 *
 */
@SuppressLint("SdCardPath") public class DataCleanManager {
	/**
	 * 清除缓存
	 * @param context
	 */
	 public static void clearAllCache(Context context) {
	        deleteDir(context.getCacheDir());
	        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
	            deleteDir(context.getCacheDir());
	        }  
	    }
	   
	   private static boolean deleteDir(File dir) {
		   boolean success = true;
	        if (dir != null && dir.isDirectory() ) {
	            String[] children = dir.list();
	            for (int i = 0; i < children.length; i++) {
	            	//不清除bmob文件
	            	if(!children[i].equals("bmob")){
	            		success = deleteDir(new File(dir, children[i]));
	                }else{
	                	success=false;
	                }
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        return dir.delete();
	    }
    /**
     * 获取缓存大小
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }  
        return getFormatSize(cacheSize);
    }
    public static long getFolderSize(File file) throws Exception {  
        long size = 0;  
        try {  
        	//fileList得到的是当前目录下的文件/文件夹，路径名不包括当前目录名
            File[] fileList = file.listFiles();  
            	for (int i = 0; i < fileList.length; i++) { 
            			// 如果下面还有文件  
            			if (fileList[i].isDirectory()) {
            				//判断当前目录是否为bmob,不计算头像文件的大小
            				if(!fileList[i].getName().equals("bmob")){
            					size = size + getFolderSize(fileList[i]);  
            				}else{
            					size=size+0;
            				}
            			} else {  
            				size = size + fileList[i].length();  
            			}  
            		
                }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return size;  
    }  
    /** 
     * 格式化单位 
     * @param size 
     * @return 
     */ 
    public static String getFormatSize(double size) {  
        double kiloByte = size / 1024;  
        if (kiloByte < 1) {  
            return "0KB";
        }  
   
        double megaByte = kiloByte / 1024;  
        if (megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "KB";  
        }  
   
        double gigaByte = megaByte / 1024;  
        if (gigaByte < 1) {  
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "MB";  
        }  
   
        double teraBytes = gigaByte / 1024;  
        if (teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()  
                + "TB";  
    }  
       
       
}
