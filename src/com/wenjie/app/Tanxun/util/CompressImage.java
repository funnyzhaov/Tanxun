package com.wenjie.app.Tanxun.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 压缩图片工具类
 * @author dell
 *
 */
public class CompressImage {
	/**
	 * 压缩图片
	 * @param bitmap
	 * @return
	 */
	public static Bitmap compressImage(Bitmap bitmap){
		//得到图片原始的高宽
		int rawHeight = bitmap.getHeight();
		int rawWidth = bitmap.getWidth();
		// 设定图片新的高宽
		int newHeight = 90;
		int newWidth = 90;
		// 计算缩放因子
		float heightScale = ((float) newHeight) / rawHeight;
		float widthScale = ((float) newWidth) / rawWidth;
		// 新建立矩阵
		Matrix matrix = new Matrix();
		matrix.postScale(heightScale, widthScale);
		// 压缩后图片的宽和高以及kB大小均会变化
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, rawWidth,
				rawHeight, matrix, true);
		//回收大图的对象
		if(!bitmap.isRecycled())
		{
			bitmap.recycle();
		}     
		return newBitmap;
		
	}
}
