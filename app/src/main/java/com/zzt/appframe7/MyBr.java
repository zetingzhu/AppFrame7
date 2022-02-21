package com.zzt.appframe7;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBr extends BroadcastReceiver {
    SnackListener listener ;

    public MyBr(SnackListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String aa = intent.getStringExtra("aa");
        Toast.makeText(context , aa , Toast.LENGTH_SHORT).show();
        if (listener !=null){
            listener.onSnackShow(aa);
        }
    }

    interface  SnackListener{
        void onSnackShow(String msg);
    }
}
