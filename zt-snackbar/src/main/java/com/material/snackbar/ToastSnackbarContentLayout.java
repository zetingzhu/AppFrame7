/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.material.snackbar;

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

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.color.MaterialColors;
import com.help.TopLayoutHelper;
import com.zzt.zt_snackbar.R;

/**
 * 需要被继承的自定义弹框内容父类 eg{@link com.material.snackbar.widget.ToastMessageLayout}
 * lightweight view that are displayed along the bottom edge of the application window.
 */
public abstract class ToastSnackbarContentLayout extends ConstraintLayout implements ToastContentViewCallback {
    private static final OnTouchListener consumeAllTouchListener =
            new OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Prevent touches from passing through this view.
                    return true;
                }
            };

    // 禁止页面操作
    protected boolean disallowIntercept;

    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    protected TopLayoutHelper mLayoutHelper;

    protected abstract View[] getMessageViewArras();

    protected abstract void setText(@NonNull CharSequence message);

    protected abstract void setTextColor(ColorStateList colors);

    protected abstract void setTextColor(@ColorInt int color);

    protected abstract void setAction(Drawable drawable, @Nullable final View.OnClickListener listener);

    protected abstract void setAction(@DrawableRes int resId, @Nullable final View.OnClickListener listener);

    public ToastSnackbarContentLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public ToastSnackbarContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ToastSnackbarContentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOnTouchListener(consumeAllTouchListener);
        if (getBackground() == null) {
            ViewCompat.setBackground(this, createThemedBackground());
        }
        mLayoutHelper = new TopLayoutHelper(context, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.AT_MOST));
    }

    @Override
    public void animateContentIn(int delay, int duration) {
        if (getMessageViewArras() != null && getMessageViewArras().length > 0) {
            for (View view : getMessageViewArras()) {
                if (view.getVisibility() == VISIBLE) {
                    view.setAlpha(0f);
                    view.animate().alpha(1f).setDuration(duration).setStartDelay(delay).start();
                }
            }
        }
    }

    @Override
    public void animateContentOut(int delay, int duration) {
        if (getMessageViewArras() != null && getMessageViewArras().length > 0) {
            for (View view : getMessageViewArras()) {
                if (view.getVisibility() == VISIBLE) {
                    view.setAlpha(1f);
                    view.animate().alpha(0f).setDuration(duration).setStartDelay(delay).start();
                }
            }
        }
    }

    @Override
    public boolean requestDisallowInterceptTouchEvent() {
        return disallowIntercept;
    }

    public void setDisallowIntercept(boolean disallowIntercept) {
        this.disallowIntercept = disallowIntercept;
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

    @NonNull
    private Drawable createThemedBackground() {
        float cornerRadius = getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(cornerRadius);

        int backgroundColor = MaterialColors.layer(this, R.attr.colorSurface, R.attr.colorOnSurface, 1);
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
