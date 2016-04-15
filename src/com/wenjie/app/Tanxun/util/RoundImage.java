package com.wenjie.app.Tanxun.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
/**
 * 图片效果处理工具类
 * @author dell
 *
 */
public class RoundImage {
	/**
     * 圆型.
     *
     * @param bitmap
     * 
     * @return
     */
    public static Bitmap roundImage(Bitmap bitmap) {
        Bitmap bi = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cs = new Canvas(bi);
        final int color = 0xff424242;
        final Paint pa = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        pa.setAntiAlias(true);
        cs.drawARGB(0, 0, 0, 0);
        pa.setColor(color);
        cs.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, pa);
        pa.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        cs.drawBitmap(bitmap, rect, rect, pa);
        return bi;
    }

}
