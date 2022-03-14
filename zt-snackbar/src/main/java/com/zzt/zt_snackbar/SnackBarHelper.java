package com.zzt.zt_snackbar;

import android.app.Activity;
import android.view.View;

import com.trade.utilcode.util.ActivityUtils;
import com.trade.utilcode.util.ThreadUtils;

import java.util.List;

public class SnackBarHelper {


    public static void showSnackbar(View view, String message) {
        SnackbarUtils.Long(view, message).show();
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
