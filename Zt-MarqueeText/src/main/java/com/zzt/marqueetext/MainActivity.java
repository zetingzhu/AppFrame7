package com.zzt.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
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
//        initView();

        initTestLinkedList();

    }

    /**
     *
     */
    private void initTestLinkedList() {
        LinkedList<String> mList = new LinkedList<>();

        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");
        mList.addFirst("5");
        mList.add("6");
        mList.addLast("7");
        Log.e(TAG, ">>>>" + mList.toString());

        mList.addFirst(mList.remove(3));
        Log.e(TAG, ">>>>" + mList.toString());

        Collections.swap(mList, 6, 0);
        Log.e(TAG, ">>>>" + mList.toString());

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