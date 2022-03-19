package com.zzt.zt_snackbar.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.Snackbar;
import com.material.snackbar.ToastSnackbarContentLayout;
import com.zzt.zt_snackbar.R;
import com.zzt.zt_snackbar.SnackbarUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 这个就是展示成功，失败的弹框
 */
public class ToastSuccessFailedLayout extends ToastSnackbarContentLayout {
    public static final int TOAST_TYPE_SUCCESS = 1;
    public static final int TOAST_TYPE_FAILED = 2;
    public static final int TOAST_TYPE_ORDER = 3;

    @IntDef({TOAST_TYPE_SUCCESS, TOAST_TYPE_FAILED, TOAST_TYPE_ORDER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ToastType {
    }

    private TextView messageView;
    private ImageButton actionView;
    private ImageView leftImageView;


    public ToastSuccessFailedLayout(@NonNull Context context, @ToastType int type) {
        super(context);
        initView(context, type);
    }


    private void initView(Context context, @ToastType int type) {
        if (type == TOAST_TYPE_ORDER) {
            inflate(context, R.layout.toast_snackbar_order_layout, this);
        } else {
            inflate(context, R.layout.toast_snackbar_message_layout, this);

        }
        messageView = findViewById(R.id.snackbar_text);
        actionView = findViewById(R.id.snackbar_right_image);
        leftImageView = findViewById(R.id.snackbar_left_image);

        switch (type) {
            case TOAST_TYPE_SUCCESS:
                leftDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_input_add));
                break;
            case TOAST_TYPE_FAILED:
                leftDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_delete));
                break;
            case TOAST_TYPE_ORDER:
                if (leftImageView != null) {
                    ViewCompat.setBackground(leftImageView, ContextCompat.getDrawable(getContext(), android.R.drawable.sym_action_call));
                }
                break;
        }

    }

    /**
     * 设置左边图片
     *
     * @param leftDrawable
     */
    public void leftDrawable(@Nullable Drawable leftDrawable) {
        if (messageView != null) {
            messageView.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, null, null, null);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected View[] getMessageViewArras() {
        return new View[0];
    }

    @Override
    public void setText(@NonNull CharSequence message) {
        if (messageView != null) {
            messageView.setText(message);
        }
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        if (messageView != null) {
            messageView.setTextColor(colors);
        }
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        if (messageView != null) {
            messageView.setTextColor(color);
        }
    }

    @Override
    protected void setAction(Drawable drawable, @Nullable OnClickListener listener) {
        if (actionView != null) {
            actionView.setVisibility(VISIBLE);
            if (drawable != null) {
                actionView.setBackground(drawable);
            }
            actionView.setOnClickListener(listener);
        }
    }

    @Override
    protected void setAction(@DrawableRes int resId, @Nullable OnClickListener listener) {
        if (actionView != null) {
            actionView.setVisibility(VISIBLE);
            actionView.setBackgroundResource(resId);
            actionView.setOnClickListener(listener);
        }
    }

}
