package com.zzt.zt_seekbar;

import static android.widget.LinearLayout.VERTICAL;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.Gravity;

public class ColorUtil {

    public static Drawable genSeekProgressDrawable(Context context) {
        Drawable[] drawables = new Drawable[3];
        GradientDrawable roundRect = new GradientDrawable();
        roundRect.setShape(GradientDrawable.RECTANGLE);
        roundRect.setColor(Color.parseColor("#FFD700"));
        roundRect.setSize(1, dip2px(1, context));
        roundRect.setCornerRadius(dip2px(1.5f, context));
        drawables[0] = roundRect;

        roundRect = new GradientDrawable();
        roundRect.setShape(GradientDrawable.RECTANGLE);
        roundRect.setColor(Color.parseColor("#EE82EE"));
        roundRect.setSize(1, dip2px(1, context));
        roundRect.setCornerRadius(dip2px(1.5f, context));
        ClipDrawable clipDrawable = new ClipDrawable(roundRect, Gravity.LEFT, VERTICAL);
        drawables[1] = clipDrawable;

        roundRect = new GradientDrawable();
        roundRect.setShape(GradientDrawable.RECTANGLE);
        roundRect.setColor(Color.parseColor("#800080"));
        roundRect.setSize(1, dip2px(1, context));
        roundRect.setCornerRadius(dip2px(1.5f, context));
        clipDrawable = new ClipDrawable(roundRect, Gravity.LEFT, VERTICAL);
        drawables[2] = clipDrawable;


        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.secondaryProgress);
        layerDrawable.setId(2, android.R.id.progress);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            layerDrawable.setLayerHeight(0, dip2px(2, context));
//            layerDrawable.setLayerHeight(1, dip2px(2, context));
//            layerDrawable.setLayerHeight(2, dip2px(2, context));
//            layerDrawable.setLayerGravity(0, Gravity.CENTER_VERTICAL);
//            layerDrawable.setLayerGravity(1, Gravity.CENTER_VERTICAL);
//            layerDrawable.setLayerGravity(2, Gravity.CENTER_VERTICAL);
//        }

        return layerDrawable;
    }

    public static int dip2px(float value, Context context) {
        return (int) value;
    }
}
