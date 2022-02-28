package com.xtrend.zt_threeprogress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.xtrend.com.zzt.tp.ThreeProgressBar;

public class MainActivity extends AppCompatActivity {

    ThreeProgressBar tpb_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                tpb_test.setCurrentCount(count);
                count++;
                mHandler.sendEmptyMessageDelayed(1, 500);
            }
            return false;
        }
    });

    int count = 0;

    private void initView() {
        tpb_test = findViewById(R.id.tpb_test);
        tpb_test.setCurrentCount(100);
//        mHandler.sendEmptyMessageDelayed(1, 1000);
    }
}