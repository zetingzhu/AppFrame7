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

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.zzt.zt_snackbar.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for lightweight transient bars that are displayed along the bottom edge of the
 * application window.
 *
 * @param <B> The transient bottom bar subclass.
 */
public abstract class ToastBaseTransientBottomBar<B extends ToastBaseTransientBottomBar<B>> {

    /**
     * Animation mode that corresponds to the slide in and out animations.
     */
    public static final int ANIMATION_MODE_SLIDE = 0;

    /**
     * Animation mode that corresponds to the fade in and out animations.
     */
    public static final int ANIMATION_MODE_FADE = 1;

    /**
     * Animation modes that can be set on the {@link ToastBaseTransientBottomBar}.
     *
     * @hide
     */
    @RestrictTo(LIBRARY_GROUP)
    @IntDef({ANIMATION_MODE_SLIDE, ANIMATION_MODE_FADE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationMode {
    }

    /**
     * Base class for {@link ToastBaseTransientBottomBar} callbacks.
     *
     * @param <B> The transient bottom bar subclass.
     * @see ToastBaseTransientBottomBar#addCallback(BaseCallback)
     */
    public abstract static class BaseCallback<B> {

        /**
         * Indicates that the Snackbar was dismissed via an action click.
         */
        public static final int DISMISS_EVENT_ACTION = 1;

        /**
         * Indicates that the Snackbar was dismissed via a call to {@link #dismiss()}.
         */
        public static final int DISMISS_EVENT_MANUAL = 3;


        /**
         * Annotation for types of Dismiss events.
         *
         * @hide
         */
        @RestrictTo(LIBRARY_GROUP)
        @IntDef({
                DISMISS_EVENT_ACTION,
                DISMISS_EVENT_MANUAL,
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        /**
         * Called when the given {@link ToastBaseTransientBottomBar} has been dismissed, either through a
         * time-out, having been manually dismissed, or an action being clicked.
         *
         * @param transientBottomBar The transient bottom bar which has been dismissed.
         * @param event              The event which caused the dismissal. One of either: {@link
         *                           {@link #DISMISS_EVENT_ACTION},
         *                           {@link #DISMISS_EVENT_MANUAL} or
         * @see ToastBaseTransientBottomBar#dismiss()
         */
        public void onDismissed(B transientBottomBar, @DismissEvent int event) {
            // empty
        }

        /**
         * Called when the given {@link ToastBaseTransientBottomBar} is visible.
         *
         * @param transientBottomBar The transient bottom bar which is now visible.
         * @see ToastBaseTransientBottomBar#show()
         */
        public void onShown(B transientBottomBar) {
            // empty
        }
    }

    /**
     * Interface that defines the behavior of the main content of a transient bottom bar.
     *
     * @deprecated Use {@link ToastContentViewCallback} instead.
     */
    @Deprecated
    public interface ContentViewCallback extends ToastContentViewCallback {
    }

    /**
     * @hide
     */
    @RestrictTo(LIBRARY_GROUP)
    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    /**
     * Show the Snackbar indefinitely. This means that the Snackbar will be displayed from the time
     * that is {@link #show() shown} until either it is dismissed, or another Snackbar is shown.
     *
     * @see #setDuration
     */
    public static final int LENGTH_INDEFINITE = -2;

    /**
     * Show the Snackbar for a short period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = -1;

    /**
     * Show the Snackbar for a long period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = 0;

    // Legacy slide animation duration constant.
    static final int ANIMATION_DURATION = 250;
    // Legacy slide animation content fade duration constant.
    static final int ANIMATION_FADE_DURATION = 180;

    // Fade and scale animation constants.
    private static final int ANIMATION_FADE_IN_DURATION = 150;
    private static final int ANIMATION_FADE_OUT_DURATION = 75;
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;

    @NonNull
    static final Handler handler;
    static final int MSG_SHOW = 0;
    static final int MSG_DISMISS = 1;

    // On JB/KK versions of the platform sometimes View.setTranslationY does not result in
    // layout / draw pass, and CoordinatorLayout relies on a draw pass to happen to sync vertical
    // positioning of all its child views
    private static final boolean USE_OFFSET_API =
            (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
                    && (VERSION.SDK_INT <= VERSION_CODES.KITKAT);

    private static final String TAG = ToastBaseTransientBottomBar.class.getSimpleName();

    // Interpolator
    public static final TimeInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final TimeInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    public static final TimeInterpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();

    static {
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case MSG_SHOW:
                        ((ToastBaseTransientBottomBar) message.obj).showView();
                        return true;
                    case MSG_DISMISS:
                        ((ToastBaseTransientBottomBar) message.obj).hideView(message.arg1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @NonNull
    private final ViewGroup targetParent;
    private final Context context;
    @NonNull
    protected final ToastSnackbarBaseLayout view;

    @NonNull
    private final ToastContentViewCallback contentViewCallback;

    private int duration;

    private List<BaseCallback<B>> callbacks;

    @Nullable
    private final AccessibilityManager accessibilityManager;

    /**
     * @hide
     */
    // TODO(b/76413401): make package private after the widget migration is finished
    @RestrictTo(LIBRARY_GROUP)
    protected interface OnLayoutChangeListener {
        void onLayoutChange(View view, int left, int top, int right, int bottom);
    }

    /**
     * @hide
     */
    // TODO(b/76413401): make package private after the widget migration is finished
    @RestrictTo(LIBRARY_GROUP)
    protected interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View v);

        void onViewDetachedFromWindow(View v);
    }

    /**
     * Constructor for the transient bottom bar.
     *
     * <p>Uses {@link Context} from {@code parent}.
     *
     * @param parent              The parent for this transient bottom bar.
     * @param content             The content view for this transient bottom bar.
     * @param contentViewCallback The content view callback for this transient bottom bar.
     */
    protected ToastBaseTransientBottomBar(
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ToastContentViewCallback contentViewCallback) {
        this(parent.getContext(), parent, content, contentViewCallback);
    }

    protected ToastBaseTransientBottomBar(
            @NonNull Context context,
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ToastContentViewCallback contentViewCallback) {
        if (parent == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        }
        if (content == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        }
        if (contentViewCallback == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }

        targetParent = parent;
        this.contentViewCallback = contentViewCallback;
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        // Note that for backwards compatibility reasons we inflate a layout that is defined
        // in the extending Snackbar class. This is to prevent breakage of apps that have custom
        // coordinator layout behaviors that depend on that layout.
        view = (ToastSnackbarBaseLayout) inflater.inflate(getSnackbarBaseLayoutResId(), targetParent, false);
        view.addView(content);

        ViewCompat.setAccessibilityLiveRegion(view, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
        ViewCompat.setImportantForAccessibility(view, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);

        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat() {
            @Override
            public void onInitializeAccessibilityNodeInfo(View host, @NonNull AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS);
                info.setDismissable(true);
            }

            @Override
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS) {
                    dismiss();
                    return true;
                }
                return super.performAccessibilityAction(host, action, args);
            }
        });

        accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }


    @LayoutRes
    protected int getSnackbarBaseLayoutResId() {
        return R.layout.toast_snackbar_content_layout;
    }


    /**
     * Set how long to show the view for.
     *
     * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
     *                 #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
     */
    @NonNull
    public B setDuration(@Duration int duration) {
        this.duration = duration;
        return (B) this;
    }

    /**
     * Return the duration.
     *
     * @see #setDuration
     */
    @Duration
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the animation mode.
     *
     * @see #setAnimationMode(int)
     */
    @AnimationMode
    public int getAnimationMode() {
        return view.getAnimationMode();
    }

    /**
     * Sets the animation mode.
     *
     * @param animationMode of {@link #ANIMATION_MODE_SLIDE} or {@link #ANIMATION_MODE_FADE}.
     * @see #getAnimationMode()
     */
    @NonNull
    public B setAnimationMode(@AnimationMode int animationMode) {
        view.setAnimationMode(animationMode);
        return (B) this;
    }

    /**
     * Returns the {@link ToastBaseTransientBottomBar}'s context.
     */
    @NonNull
    public Context getContext() {
        return context;
    }

    /**
     * Returns the {@link ToastBaseTransientBottomBar}'s view.
     */
    @NonNull
    public View getView() {
        return view;
    }

    /**
     * Show the {@link ToastBaseTransientBottomBar}.
     */
    public void show() {
        ToastSnackbarManager.getInstance().show(getDuration(), managerCallback);
    }

    /**
     * Dismiss the {@link ToastBaseTransientBottomBar}.
     */
    public void dismiss() {
        dispatchDismiss(BaseCallback.DISMISS_EVENT_MANUAL);
    }

    protected void dispatchDismiss(@BaseCallback.DismissEvent int event) {
        ToastSnackbarManager.getInstance().dismiss(managerCallback, event);
    }

    /**
     * Adds the specified callback to the list of callbacks that will be notified of transient bottom
     * bar events.
     *
     * @param callback Callback to notify when transient bottom bar events occur.
     * @see #removeCallback(BaseCallback)
     */
    @NonNull
    public B addCallback(@Nullable BaseCallback<B> callback) {
        if (callback == null) {
            return (B) this;
        }
        if (callbacks == null) {
            callbacks = new ArrayList<BaseCallback<B>>();
        }
        callbacks.add(callback);
        return (B) this;
    }

    /**
     * Removes the specified callback from the list of callbacks that will be notified of transient
     * bottom bar events.
     *
     * @param callback Callback to remove from being notified of transient bottom bar events
     * @see #addCallback(BaseCallback)
     */
    @NonNull
    public B removeCallback(@Nullable BaseCallback<B> callback) {
        if (callback == null) {
            return (B) this;
        }
        if (callbacks == null) {
            // This can happen if this method is called before the first call to addCallback
            return (B) this;
        }
        callbacks.remove(callback);
        return (B) this;
    }

    /**
     * Return whether this {@link ToastBaseTransientBottomBar} is currently being shown.
     */
    public boolean isShown() {
        return ToastSnackbarManager.getInstance().isCurrent(managerCallback);
    }

    /**
     * Returns whether this {@link ToastBaseTransientBottomBar} is currently being shown, or is queued to
     * be shown next.
     */
    public boolean isShownOrQueued() {
        return ToastSnackbarManager.getInstance().isCurrentOrNext(managerCallback);
    }

    @NonNull
    ToastSnackbarManager.Callback managerCallback = new ToastSnackbarManager.Callback() {
        @Override
        public void show() {
            handler.sendMessage(handler.obtainMessage(MSG_SHOW, ToastBaseTransientBottomBar.this));
        }

        @Override
        public void dismiss(int event) {
            handler.sendMessage(
                    handler.obtainMessage(MSG_DISMISS, event, 0, ToastBaseTransientBottomBar.this));
        }
    };


    final void showView() {
        this.view.setOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (isShownOrQueued()) {
                    // If we haven't already been dismissed then this event is coming from a
                    // non-user initiated action. Hence we need to make sure that we callback
                    // and keep our state up to date. We need to post the call since
                    // removeView() will call through to onDetachedFromWindow and thus overflow.
                    handler.post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    onViewHidden(BaseCallback.DISMISS_EVENT_MANUAL);
                                }
                            });
                }
            }
        });

        if (this.view.getParent() == null) {
            // Set view to INVISIBLE so it doesn't flash on the screen before the inset adjustment is
            // handled and the enter animation is started
            view.setVisibility(View.INVISIBLE);
            targetParent.addView(this.view);
        }

        if (ViewCompat.isLaidOut(this.view)) {
            showViewImpl();
            return;
        }

        // Otherwise, add one of our layout change listeners and show it in when laid out
        this.view.setOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                ToastBaseTransientBottomBar.this.view.setOnLayoutChangeListener(null);
                ToastBaseTransientBottomBar.this.showViewImpl();
            }
        });
    }

    private void showViewImpl() {
        if (shouldAnimate()) {
            // If animations are enabled, animate it in
            animateViewIn();
        } else {
            // Else if animations are disabled, just make view VISIBLE and call back now
            if (view.getParent() != null) {
                view.setVisibility(View.VISIBLE);
            }
            onViewShown();
        }
    }

    void animateViewIn() {
        // Post to make sure animation doesn't start until after all inset handling has completed
        view.post(new Runnable() {
            @Override
            public void run() {
                if (view == null) {
                    return;
                }
                // Make view VISIBLE now that we are about to start the enter animation
                if (view.getParent() != null) {
                    view.setVisibility(View.VISIBLE);
                }
                if (view.getAnimationMode() == ANIMATION_MODE_FADE) {
                    startFadeInAnimation();
                } else {
                    startSlideInAnimation();
                }
            }
        });
    }

    private void animateViewOut(int event) {
        if (view.getAnimationMode() == ANIMATION_MODE_FADE) {
            startFadeOutAnimation(event);
        } else {
            startSlideOutAnimation(event);
        }
    }

    private void startFadeInAnimation() {
        ValueAnimator alphaAnimator = getAlphaAnimator(0, 1);
        ValueAnimator scaleAnimator = getScaleAnimator(ANIMATION_SCALE_FROM_VALUE, 1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleAnimator);
        animatorSet.setDuration(ANIMATION_FADE_IN_DURATION);
        animatorSet.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        onViewShown();
                    }
                });
        animatorSet.start();
    }

    private void startFadeOutAnimation(final int event) {
        ValueAnimator animator = getAlphaAnimator(1, 0);
        animator.setDuration(ANIMATION_FADE_OUT_DURATION);
        animator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        onViewHidden(event);
                    }
                });
        animator.start();
    }

    private ValueAnimator getAlphaAnimator(float... alphaValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(alphaValues);
        animator.setInterpolator(LINEAR_INTERPOLATOR);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        return animator;
    }

    private ValueAnimator getScaleAnimator(float... scaleValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(scaleValues);
        animator.setInterpolator(LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        });
        return animator;
    }

    private void startSlideInAnimation() {
        final int translationYBottom = -getTranslationYBottom();
        if (USE_OFFSET_API) {
            ViewCompat.offsetTopAndBottom(view, translationYBottom);
        } else {
            view.setTranslationY(translationYBottom);
        }

        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(translationYBottom, 0);
        animator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(ANIMATION_DURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                contentViewCallback.animateContentIn(
                        ANIMATION_DURATION - ANIMATION_FADE_DURATION, ANIMATION_FADE_DURATION);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                onViewShown();
            }
        });
        animator.addUpdateListener(new AnimatorUpdateListener() {
            private int previousAnimatedIntValue = translationYBottom;

            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                int currentAnimatedIntValue = (int) animator.getAnimatedValue();
                if (USE_OFFSET_API) {
                    // On JB/KK versions of the platform sometimes View.setTranslationY does not
                    // result in layout / draw pass
                    ViewCompat.offsetTopAndBottom(
                            view, currentAnimatedIntValue - previousAnimatedIntValue);
                } else {
                    view.setTranslationY(currentAnimatedIntValue);
                }
                previousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();
    }

    private void startSlideOutAnimation(final int event) {
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0, -getTranslationYBottom());
        animator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(ANIMATION_DURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                contentViewCallback.animateContentOut(0, ANIMATION_FADE_DURATION);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                onViewHidden(event);
            }
        });
        animator.addUpdateListener(new AnimatorUpdateListener() {
            private int previousAnimatedIntValue = 0;

            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                int currentAnimatedIntValue = (int) animator.getAnimatedValue();
                if (USE_OFFSET_API) {
                    // On JB/KK versions of the platform sometimes View.setTranslationY does not
                    // result in layout / draw pass
                    ViewCompat.offsetTopAndBottom(view, currentAnimatedIntValue - previousAnimatedIntValue);
                } else {
                    view.setTranslationY(currentAnimatedIntValue);
                }
                previousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();
    }

    private int getTranslationYBottom() {
        int translationY = view.getHeight();
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            translationY += ((MarginLayoutParams) layoutParams).topMargin;
        }
        return translationY;
    }


    final void hideView(@BaseCallback.DismissEvent int event) {
        if (shouldAnimate() && view.getVisibility() == View.VISIBLE) {
            animateViewOut(event);
        } else {
            // If anims are disabled or the view isn't visible, just call back now
            onViewHidden(event);
        }
    }

    void onViewShown() {
        ToastSnackbarManager.getInstance().onShown(managerCallback);
        if (callbacks != null) {
            // Notify the callbacks. Do that from the end of the list so that if a callback
            // removes itself as the result of being called, it won't mess up with our iteration
            int callbackCount = callbacks.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                callbacks.get(i).onShown((B) this);
            }
        }
    }

    void onViewHidden(int event) {
        // First tell the SnackbarManager that it has been dismissed
        ToastSnackbarManager.getInstance().onDismissed(managerCallback);
        if (callbacks != null) {
            // Notify the callbacks. Do that from the end of the list so that if a callback
            // removes itself as the result of being called, it won't mess up with our iteration
            int callbackCount = callbacks.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                callbacks.get(i).onDismissed((B) this, event);
            }
        }
        // Lastly, hide and remove the view from the parent (if attached)
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    /**
     * Returns true if we should animate the Snackbar view in/out.
     */
    boolean shouldAnimate() {
        if (accessibilityManager == null) {
            return true;
        }
        int feedbackFlags = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        List<AccessibilityServiceInfo> serviceList =
                accessibilityManager.getEnabledAccessibilityServiceList(feedbackFlags);
        return serviceList != null && serviceList.isEmpty();
    }

}
