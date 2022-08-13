package com.zzt.zt_pingutil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);

        PingNetworkUtils pingNetworkUtils = new PingNetworkUtils();
        pingNetworkUtils.startPing("www.baidu.com");
        pingNetworkUtils.startPing("m.xtspd.com");
        pingNetworkUtils.startPing("q.xtspd.com");

    }

}