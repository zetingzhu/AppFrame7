package com.zzt.zt_snackbar;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.material.snackbar.ToastBaseTransientBottomBar;
import com.material.snackbar.ToastSnackbar;
import com.zzt.zt_snackbar.widget.ToastSuccessFailedLayout;

import java.lang.ref.WeakReference;

public class SnackbarUtil {
    private WeakReference<ToastSnackbar> snackbarWeakReference;

    private SnackbarUtil(@Nullable WeakReference<ToastSnackbar> snackbarWeakReference) {
        this.snackbarWeakReference = snackbarWeakReference;
    }

    /**
     * 获取 mSnackbar
     */
    public ToastSnackbar getSnackbar() {
        if (this.snackbarWeakReference != null && this.snackbarWeakReference.get() != null) {
            return this.snackbarWeakReference.get();
        } else {
            return null;
        }
    }

    public static ToastSnackbar makeSuccess(@NonNull View view, @NonNull CharSequence text, @ToastBaseTransientBottomBar.Duration int duration) {
        if (view == null || view.getContext() == null) {
            throw new RuntimeException("设置的随意页面视图有问题，请重新设置");
        }
        ToastSnackbar toastSnackbar = ToastSnackbar.make(null, view, text, duration,
                new ToastSuccessFailedLayout(view.getContext(), ToastSuccessFailedLayout.TOAST_TYPE_SUCCESS))
                .setBackgroundTint(Color.WHITE)
                .margins(30, 30, 30, 30);
        return new SnackbarUtil(new WeakReference<>(toastSnackbar)).getSnackbar();
    }

    public static ToastSnackbar makeFailed(@NonNull View view, @NonNull CharSequence text, @ToastBaseTransientBottomBar.Duration int duration) {
        if (view == null || view.getContext() == null) {
            throw new RuntimeException("设置的随意页面视图有问题，请重新设置");
        }
        ToastSnackbar toastSnackbar = ToastSnackbar.make(null, view, text, duration,
                new ToastSuccessFailedLayout(view.getContext(), ToastSuccessFailedLayout.TOAST_TYPE_FAILED))
                .setBackgroundTint(Color.WHITE)
                .margins(30, 30, 30, 30);
        return new SnackbarUtil(new WeakReference<>(toastSnackbar)).getSnackbar();
    }

    public static ToastSnackbar makeOrder(@NonNull View view, @NonNull CharSequence text, @ToastBaseTransientBottomBar.Duration int duration) {
        if (view == null || view.getContext() == null) {
            throw new RuntimeException("设置的随意页面视图有问题，请重新设置");
        }
        ToastSnackbar toastSnackbar = ToastSnackbar.make(null, view, text, duration,
                new ToastSuccessFailedLayout(view.getContext(), ToastSuccessFailedLayout.TOAST_TYPE_ORDER))
                .setBackgroundTint(Color.WHITE)
                .margins(30, 30, 30, 30);
        return new SnackbarUtil(new WeakReference<>(toastSnackbar)).getSnackbar();
    }

}
