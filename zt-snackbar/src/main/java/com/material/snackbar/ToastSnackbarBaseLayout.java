package com.material.snackbar;

import static com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.color.MaterialColors;
import com.help.TopLayoutHelper;
import com.zzt.zt_snackbar.R;

/**
 * Note: this class is here to provide backwards-compatible way for apps written before the
 * existence of the base {@link ToastBaseTransientBottomBar} class.
 */
public class ToastSnackbarBaseLayout extends FrameLayout {

    private ToastBaseTransientBottomBar.OnLayoutChangeListener onLayoutChangeListener;
    private ToastBaseTransientBottomBar.OnAttachStateChangeListener onAttachStateChangeListener;
    @ToastBaseTransientBottomBar.AnimationMode
    private int animationMode;

    public ToastSnackbarBaseLayout(@NonNull Context context) {
        this(context, null);
    }

    public ToastSnackbarBaseLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToastSnackbarBaseLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(wrap(context, attrs, 0, 0), attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        // Ensure we are using the correctly themed context rather than the context that was passed
        // in.
        context = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
        animationMode = a.getInt(R.styleable.SnackbarLayout_animationMode, ToastBaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        a.recycle();
        setFocusable(true);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (onLayoutChangeListener != null) {
            onLayoutChangeListener.onLayoutChange(this, l, t, r, b);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (onAttachStateChangeListener != null) {
            onAttachStateChangeListener.onViewAttachedToWindow(this);
        }

        ViewCompat.requestApplyInsets(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onAttachStateChangeListener != null) {
            onAttachStateChangeListener.onViewDetachedFromWindow(this);
        }
    }

    void setOnLayoutChangeListener(
            ToastBaseTransientBottomBar.OnLayoutChangeListener onLayoutChangeListener) {
        this.onLayoutChangeListener = onLayoutChangeListener;
    }

    void setOnAttachStateChangeListener(
            ToastBaseTransientBottomBar.OnAttachStateChangeListener listener) {
        onAttachStateChangeListener = listener;
    }

    @ToastBaseTransientBottomBar.AnimationMode
    int getAnimationMode() {
        return animationMode;
    }

    void setAnimationMode(@ToastBaseTransientBottomBar.AnimationMode int animationMode) {
        this.animationMode = animationMode;
    }
}
