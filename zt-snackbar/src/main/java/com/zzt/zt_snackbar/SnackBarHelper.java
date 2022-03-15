package com.zzt.zt_snackbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.common.lib.util.Log;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.trade.utilcode.util.ActivityUtils;
import com.trade.utilcode.util.AppUtils;
import com.trade.utilcode.util.ThreadUtils;
import com.trade.utilcode.util.Utils;

import java.util.List;

public class SnackBarHelper {
    public static final String TAG = SnackBarHelper.class.getSimpleName();

    private static final SnackBarHelper instance = new SnackBarHelper();

    SnackbarUtils snackbarUtils;

    private SnackBarHelper() {
        AppUtils.registerAppStatusChangedListener(new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground(Activity activity) {
                Log.d(TAG, "Activity onForeground:" + activity.getClass().getSimpleName());
            }

            @Override
            public void onBackground(Activity activity) {
                Log.d(TAG, "Activity onBackground:" + activity.getClass().getSimpleName());
            }
        });

        ActivityUtils.addActivityLifecycleCallbacks(new Utils.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                super.onActivityResumed(activity);
                if (snackbarUtils != null) {
                    Context context = snackbarUtils.getSnackbar().getContext();
                    Activity activityByContext = ActivityUtils.getActivityByContext(context);
                    boolean shown = snackbarUtils.getSnackbar().isShown();
                    boolean shownOrQueued = snackbarUtils.getSnackbar().isShownOrQueued();
                    int duration = snackbarUtils.getSnackbar().getDuration();
                    Log.d(TAG, "---onActivityResumed---" + activity.getClass().getSimpleName());
                    Log.d(TAG, " snackActivity:" + activityByContext.getClass().getSimpleName());
                    Log.d(TAG, " shown:" + shown);
                    Log.d(TAG, " shownOrQueued:" + shownOrQueued);
                    Log.d(TAG, " duration:" + duration);


                    View content = activity.findViewById(android.R.id.content);

                    showSnackbar(content, "第二个页面接着展示内容");

                    ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean shown = snackbarUtils.getSnackbar().isShown();
                            boolean shownOrQueued = snackbarUtils.getSnackbar().isShownOrQueued();
                            int duration = snackbarUtils.getSnackbar().getDuration();
                            Log.d(TAG, "---runOnUiThreadDelayed---"
                                    + "\n shown:" + shown
                                    + "\n shownOrQueued:" + shownOrQueued
                                    + "\n duration:" + duration);

                            snackbarUtils.getSnackbar().dismiss();
                        }
                    }, 8000);
                } else {
                    Log.d(TAG, "---onActivityResumed---" + activity.getClass().getSimpleName());
                }

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                super.onActivityPaused(activity);
                Log.d(TAG, "---onActivityPaused---" + activity.getClass().getSimpleName());

            }
        });
    }

    public static SnackBarHelper getInstance() {
        return instance;
    }

    public void showSnackbar(View view, String message) {
        snackbarUtils = SnackbarUtils.Custom(view, message, 5000).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                Log.d(TAG, "---onDismissed---" + message);

            }

            @Override
            public void onShown(Snackbar transientBottomBar) {
                super.onShown(transientBottomBar);
                Log.d(TAG, "---onShown---" + message + " duration:" + transientBottomBar.getDuration());
            }
        });
        snackbarUtils.show();

    }


    public static void finishSnackbar(String message) {
        ThreadUtils.runOnUiThreadDelayed(() -> {
            Activity nextActivity = getNextActivity();
            if (nextActivity != null) {
                View content = nextActivity.findViewById(android.R.id.content);
                SnackbarUtils.Long(content, message).show();
            }
        }, 200);
    }

    public static void startSnackbar(String message) {
        ThreadUtils.runOnUiThreadDelayed(() -> {
            Activity topActivity = ActivityUtils.getTopActivity();
            if (topActivity != null) {
                View content = topActivity.findViewById(android.R.id.content);
                SnackbarUtils.Long(content, message).show();
            }
        }, 200);
    }

    /**
     * 下一层 Activity
     */
    public static Activity getNextActivity() {
        List<Activity> activityList = ActivityUtils.getActivityList();
        if (activityList.size() > 1) {
            Activity activity = activityList.get(1);
            if (ActivityUtils.isActivityAlive(activity)) {
                return activity;
            }
        }
        for (Activity activity : activityList) {
            if (!ActivityUtils.isActivityAlive(activity)) {
                continue;
            }
            return activity;
        }
        return null;
    }


}
