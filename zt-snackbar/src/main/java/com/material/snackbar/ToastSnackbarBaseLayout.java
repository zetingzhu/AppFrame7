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
  class ToastSnackbarBaseLayout extends FrameLayout {
    private static final OnTouchListener consumeAllTouchListener =
            new OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Prevent touches from passing through this view.
                    return true;
                }
            };

    private ToastBaseTransientBottomBar.OnLayoutChangeListener onLayoutChangeListener;
    private ToastBaseTransientBottomBar.OnAttachStateChangeListener onAttachStateChangeListener;
    @ToastBaseTransientBottomBar.AnimationMode
    private int animationMode;
    private final float backgroundOverlayColorAlpha = 1;
    private final float actionTextColorAlpha = 1;
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    protected TopLayoutHelper mLayoutHelper;

    protected ToastSnackbarBaseLayout(@NonNull Context context) {
        this(context, null);
    }

    protected ToastSnackbarBaseLayout(@NonNull Context context, AttributeSet attrs) {
        super(wrap(context, attrs, 0, 0), attrs);
        // Ensure we are using the correctly themed context rather than the context that was passed
        // in.
        context = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
        if (a.hasValue(R.styleable.SnackbarLayout_elevation)) {
            ViewCompat.setElevation(
                    this, a.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
        }
        animationMode = a.getInt(R.styleable.SnackbarLayout_animationMode, ToastBaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        a.recycle();

        setOnTouchListener(consumeAllTouchListener);
        setFocusable(true);

        if (getBackground() == null) {
            ViewCompat.setBackground(this, createThemedBackground());
        }
        mLayoutHelper = new TopLayoutHelper(context, this);
    }

    @Override
    public void setBackground(@Nullable Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override
    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        if (drawable != null && backgroundTint != null) {
            drawable = DrawableCompat.wrap(drawable.mutate());
            DrawableCompat.setTintList(drawable, backgroundTint);
            DrawableCompat.setTintMode(drawable, backgroundTintMode);
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override
    public void setBackgroundTintList(@Nullable ColorStateList backgroundTint) {
        this.backgroundTint = backgroundTint;
        if (getBackground() != null) {
            Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
            DrawableCompat.setTintList(wrappedBackground, backgroundTint);
            DrawableCompat.setTintMode(wrappedBackground, backgroundTintMode);
            if (wrappedBackground != getBackground()) {
                super.setBackgroundDrawable(wrappedBackground);
            }
        }
    }

    @Override
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode backgroundTintMode) {
        this.backgroundTintMode = backgroundTintMode;
        if (getBackground() != null) {
            Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
            DrawableCompat.setTintMode(wrappedBackground, backgroundTintMode);
            if (wrappedBackground != getBackground()) {
                super.setBackgroundDrawable(wrappedBackground);
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        // Clear touch listener that consumes all touches if there is a custom click listener.
        setOnTouchListener(onClickListener != null ? null : consumeAllTouchListener);
        super.setOnClickListener(onClickListener);
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

    float getBackgroundOverlayColorAlpha() {
        return backgroundOverlayColorAlpha;
    }

    float getActionTextColorAlpha() {
        return actionTextColorAlpha;
    }

    @NonNull
    private Drawable createThemedBackground() {
        float cornerRadius = getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);

        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(cornerRadius);

        int backgroundColor = MaterialColors.layer(this, R.attr.colorSurface, R.attr.colorOnSurface, getBackgroundOverlayColorAlpha());
        background.setColor(backgroundColor);
        if (backgroundTint != null) {
            Drawable wrappedDrawable = DrawableCompat.wrap(background);
            DrawableCompat.setTintList(wrappedDrawable, backgroundTint);
            return wrappedDrawable;
        } else {
            return DrawableCompat.wrap(background);
        }
    }


    public TopLayoutHelper getLayoutHelper() {
        return mLayoutHelper;
    }
}
