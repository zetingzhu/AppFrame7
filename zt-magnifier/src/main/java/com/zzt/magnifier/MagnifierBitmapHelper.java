package com.zzt.magnifier;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @author: zeting
 * @date: 2022/6/24
 * 处理放大镜图片
 */
public class MagnifierBitmapHelper {

    /**
     * 获取圆形图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap GetCircleBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(rectF.centerX(), rectF.centerX(), rectF.width() * 0.5f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }


    /**
     * 释放资源
     */
    public static void recycler(Bitmap... bitmap) {
        if (bitmap != null && bitmap.length > 0) {
            for (int i = 0; i < bitmap.length; i++) {
                if (bitmap[i] != null && !bitmap[i].isRecycled()) {
                    bitmap[i].recycle();
                }
            }
        }
    }

    public static boolean isNotEmpty(Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled();
    }

} 