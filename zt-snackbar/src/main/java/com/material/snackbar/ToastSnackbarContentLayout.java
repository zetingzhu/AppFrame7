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

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.help.TopLayoutHelper;

/**
 * 需要被继承的自定义弹框内容父类 eg{@link com.material.snackbar.widget.ToastMessageLayout}
 * lightweight view that are displayed along the bottom edge of the application window.
 */
public abstract class ToastSnackbarContentLayout extends ConstraintLayout implements ToastContentViewCallback {


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

}
