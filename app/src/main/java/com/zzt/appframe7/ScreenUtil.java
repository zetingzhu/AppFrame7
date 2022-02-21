package com.zzt.appframe7;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;


/**
 * https://github.com/guoyoujin/MySnackBar
 */

public class ScreenUtil {

    private ScreenUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    private static int mStatusHeight = -1;

    public static int dp2px(Context ctx, float dpValue) {
        final float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int sp2px(Context ctx, float spValue) {
        final float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }



}
