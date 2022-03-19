/*
 * Copyright (C) 2015 The Android Open Source Project
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
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.material.snackbar.widget.ToastMessageLayout;

/**
 * Snackbars provide lightweight feedback about an operation. They show a brief message at the
 * bottom of the screen on mobile and lower left on larger devices. Snackbars appear above all other
 * elements on screen and only one can be displayed at a time.
 *
 * <p>They automatically disappear after a timeout or after user interaction elsewhere on the
 * screen, particularly after interactions that summon a new surface or activity. Snackbars can be
 * swiped off screen.
 *
 * <p>To be notified when a snackbar has been shown or dismissed, you can provide a {@link Callback}
 * via {@link ToastBaseTransientBottomBar#addCallback(BaseCallback)}.
 */
public class ToastSnackbar extends ToastBaseTransientBottomBar<ToastSnackbar> {


    /**
     * Callback class for {@link ToastSnackbar} instances.
     *
     * <p>Note: this class is here to provide backwards-compatible way for apps written before the
     * existence of the base {@link ToastBaseTransientBottomBar} class.
     *
     * @see ToastBaseTransientBottomBar#addCallback(BaseCallback)
     */
    public static class Callback extends BaseCallback<ToastSnackbar> {
        @Override
        public void onShown(ToastSnackbar sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(ToastSnackbar transientBottomBar, @DismissEvent int event) {
            // Stub implementation to make API check happy.
        }
    }

    @Nullable
    private BaseCallback<ToastSnackbar> callback;

    private ToastSnackbar(
            @NonNull Context context,
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ToastContentViewCallback contentViewCallback) {
        super(context, parent, content, contentViewCallback);
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize MBaseTransientBottomBar hierarchy).
    @Override
    public void show() {
        super.show();
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize MBaseTransientBottomBar hierarchy).
    @Override
    public void dismiss() {
        super.dismiss();
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize MBaseTransientBottomBar hierarchy).
    @Override
    public boolean isShown() {
        return super.isShown();
    }

    /**
     * Make a Snackbar to display a message
     *
     * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given to
     * {@code view}. Snackbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     *
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     *
     * @param view     The view to find a parent from
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
     *                 #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
     */
    @NonNull
    public static ToastSnackbar make(
            @NonNull View view, @NonNull CharSequence text, @Duration int duration) {
        return makeInternal(/* context= */ null, view, text, duration, null);
    }

    /**
     * Make a Snackbar to display a message
     *
     * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given to
     * {@code view}. Snackbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     *
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     *
     * @param context  The context to use to create the Snackbar view.
     * @param view     The view to find a parent from
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
     *                 #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
     */
    @NonNull
    public static ToastSnackbar make(
            @NonNull Context context,
            @NonNull View view,
            @NonNull CharSequence text,
            @Duration int duration) {
        return makeInternal(context, view, text, duration, null);
    }

    /**
     * Makes a Snackbar using the given context if non-null, otherwise uses the parent view context.
     */
    @NonNull
    public static ToastSnackbar make(
            @Nullable Context context,
            @NonNull View view,
            @NonNull CharSequence text,
            @Duration int duration,
            ToastSnackbarContentLayout contentView) {
        return makeInternal(context, view, text, duration, contentView);
    }

    /**
     * @param context
     * @param view
     * @param text
     * @param duration
     * @param contentView final ToastSnackbarContentLayout content = (ToastSnackbarContentLayout) inflater.inflate(R.layout.m_snackbar_layout, parent, false);
     * @return
     */
    @NonNull
    public static ToastSnackbar makeInternal(
            @Nullable Context context,
            @NonNull View view,
            @NonNull CharSequence text,
            @Duration int duration, ToastSnackbarContentLayout contentView) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view.");
        }

        if (context == null) {
            context = parent.getContext();
        }
        if (contentView == null) {
            contentView = new ToastMessageLayout(context);
        }
        contentView.setText(text);
        final ToastSnackbar snackbar = new ToastSnackbar(context, parent, contentView, contentView);
        snackbar.setDuration(duration);
        return snackbar;
    }


    /**
     * Make a Snackbar to display a message.
     *
     * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given to
     * {@code view}. Snackbar will walk up the view tree trying to find a suitable parent, which is
     * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
     * first.
     *
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable certain
     * features, such as swipe-to-dismiss and automatically moving of widgets.
     *
     * @param view     The view to find a parent from.
     * @param resId    The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
     *                 #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
     */
    @NonNull
    public static ToastSnackbar make(@NonNull View view, @StringRes int resId, @Duration int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    @Nullable
    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                // We've found a CoordinatorLayout, use it
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }

    /**
     * Sets the tint color of the background Drawable.
     */
    @NonNull
    public ToastSnackbar setBackgroundTint(@ColorInt int color) {
        return setBackgroundTintList(ColorStateList.valueOf(color));
    }

    /**
     * Sets the tint color state list of the background Drawable.
     */
    @NonNull
    public ToastSnackbar setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
        return this;
    }

    @NonNull
    public ToastSnackbar setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
        return this;
    }

    /**
     * Update the text in this
     *
     * @param message The new text for this {@link ToastBaseTransientBottomBar}.
     */
    @NonNull
    public ToastSnackbar setText(@NonNull CharSequence message) {
        final View contentLayout = this.view.getChildAt(0);
        if (contentLayout instanceof ToastSnackbarContentLayout) {
            ((ToastSnackbarContentLayout) contentLayout).setText(message);
        }
        return this;
    }

    /**
     * Update the text in this
     *
     * @param resId The new text for this {@link ToastBaseTransientBottomBar}.
     */
    @NonNull
    public ToastSnackbar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    /**
     * Sets the text color of the message specified in {@link #setText(CharSequence)} and {@link
     * #setText(int)}.
     */
    @NonNull
    public ToastSnackbar setTextColor(ColorStateList colors) {
        final View contentLayout = this.view.getChildAt(0);
        if (contentLayout instanceof ToastSnackbarContentLayout) {
            ((ToastSnackbarContentLayout) contentLayout).setTextColor(colors);
        }
        return this;
    }

    /**
     * Sets the text color of the message specified in {@link #setText(CharSequence)} and {@link
     * #setText(int)}.
     */
    @NonNull
    public ToastSnackbar setTextColor(@ColorInt int color) {
        final View contentLayout = this.view.getChildAt(0);
        if (contentLayout instanceof ToastSnackbarContentLayout) {
            ((ToastSnackbarContentLayout) contentLayout).setTextColor(color);
        }
        return this;
    }

    /**
     * Set the action to be displayed in this {@link ToastBaseTransientBottomBar}.
     *
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public ToastSnackbar setAction(Drawable drawable, @Nullable final View.OnClickListener listener) {
        final View contentLayout = this.view.getChildAt(0);
        if (contentLayout instanceof ToastSnackbarContentLayout) {
            ((ToastSnackbarContentLayout) contentLayout).setAction(drawable, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
                }
            });
        }
        return this;
    }

    @NonNull
    public ToastSnackbar setAction(@DrawableRes int resId, @Nullable final View.OnClickListener listener) {
        final View contentLayout = this.view.getChildAt(0);
        if (contentLayout instanceof ToastSnackbarContentLayout) {
            ((ToastSnackbarContentLayout) contentLayout).setAction(resId, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
                }
            });
        }
        return this;
    }

    /**
     * 设置边距
     *
     * @param left   left
     * @param top    top
     * @param right  right
     * @param bottom bottom
     * @return
     */
    public ToastSnackbar margins(int left, int top, int right, int bottom) {
        if (getView() != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(left, top, right, bottom);
                getView().setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    /**
     * 设置背景属性
     *
     * @param radius          圆角
     * @param shadowElevation 阴影
     * @param shadowColor     阴影颜色
     * @param shadowAlpha     阴影透明的
     * @return
     */
    public ToastSnackbar layoutHelper(int radius, int shadowElevation, int shadowColor, float shadowAlpha) {
        if (view.getLayoutHelper() != null) {
            view.getLayoutHelper().setRadiusAndShadow(radius, shadowElevation, shadowColor, shadowAlpha);
        }
        return this;
    }

    /**
     * Set a callback to be called when this the visibility of this {@link ToastSnackbar} changes. Note
     * that this method is deprecated and you should use {@link #addCallback(BaseCallback)} to add a
     * callback and {@link #removeCallback(BaseCallback)} to remove a registered callback.
     *
     * @param callback Callback to notify when transient bottom bar events occur.
     * @see Callback
     * @see #addCallback(BaseCallback)
     * @see #removeCallback(BaseCallback)
     * @deprecated Use {@link #addCallback(BaseCallback)}
     */
    @Deprecated
    @NonNull
    public ToastSnackbar setCallback(@Nullable Callback callback) {
        // The logic in this method emulates what we had before support for multiple
        // registered callbacks.
        if (this.callback != null) {
            removeCallback(this.callback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        // Update the deprecated field so that we can remove the passed callback the next
        // time we're called
        this.callback = callback;
        return this;
    }


}
