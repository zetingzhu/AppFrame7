package com.zzt.appframe7;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Gravity;
import android.view.View;

public class SnackbarHelper {
    public static String RECEIVER_ACTION_SNACK_BAR = "snack_bar";
    private static volatile SnackbarHelper instance;


    private SnackbarHelper() {
    }

    public static SnackbarHelper getInstance() {
        if (instance == null) {
            synchronized (SnackbarHelper.class) {
                if (instance == null) {
                    instance = new SnackbarHelper();
                }
            }
        }
        return instance;
    }

    MyBr netWorkChangeReceiver;

    public void startActivity(Activity activity) {

        Intent intent = new Intent(activity, TestActivity2.class);
        activity.startActivity(intent);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_SNACK_BAR);
        netWorkChangeReceiver = new MyBr(new MyBr.SnackListener() {
            @Override
            public void onSnackShow(String msg) {
                View viewById = activity.findViewById(android.R.id.content);
                SnackbarUtils.Short(viewById, msg).info().gravityFrameLayout(Gravity.TOP)
                        .show();
                activity.unregisterReceiver(netWorkChangeReceiver);
            }
        });
        activity.registerReceiver(netWorkChangeReceiver, intentFilter);

    }


    public void finish(Activity activity, String msg) {
        Intent intent = new Intent(RECEIVER_ACTION_SNACK_BAR);
        intent.putExtra("aa", msg);
        activity.sendBroadcast(intent); // 发送广播
        activity.finish();
    }


}
