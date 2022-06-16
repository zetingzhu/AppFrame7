package com.zzt.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String textLang = "开始，这是一个长文本1 ， 这是一个长文本2 ， 结束";
    private TextView tv_m1, tv_m2;
    private AutoScrollTextView tv_m3;
    private MarqueeHorizontalTextView tv_m4;
    private MarqueeForeverTextView tv_m5;
    private MarqueeTextViewV2 tv_m6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_m1 = findViewById(R.id.tv_m1);
        tv_m2 = findViewById(R.id.tv_m2);
        tv_m3 = findViewById(R.id.tv_m3);
        tv_m4 = findViewById(R.id.tv_m4);
        tv_m5 = findViewById(R.id.tv_m5);
        tv_m6 = findViewById(R.id.tv_m6);

        tv_m1.setText(textLang);
        tv_m1.setSelected(true);
        tv_m2.setText(textLang);
        tv_m3.setText(textLang);
        tv_m3.init(getWindowManager());
        tv_m3.startScroll();
        tv_m4.setText(textLang, TextView.BufferType.NORMAL);
//        tv_m5.setMarquee(true);
//        tv_m5.setText(textLang);

        tv_m6.startScroll();

    }
}