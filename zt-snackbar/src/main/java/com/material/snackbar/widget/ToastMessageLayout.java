package com.material.snackbar.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.material.snackbar.ToastSnackbarContentLayout;
import com.zzt.zt_snackbar.R;

/**
 * 这里是自定义的不同布局的弹框里面的内容，必须继承 {@link ToastSnackbarContentLayout}
 */
public class ToastMessageLayout extends ToastSnackbarContentLayout {

    private TextView messageView;
    private ImageButton actionView;

    public ToastMessageLayout(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ToastMessageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ToastMessageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.toast_snackbar_message_layout, this);
        messageView = findViewById(R.id.snackbar_text);
        actionView = findViewById(R.id.snackbar_right_image);
//        setBackgroundColor(Color.parseColor("#FFFFFF"));
//        getLayoutHelper().setRadiusAndShadow(20, 6, Color.parseColor("#FF0000"), 1.0f);

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
