package com.zzt.magnifier;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements View.OnTouchListener {

    MagnifierBottomLayout magnifier_bottom_layout;
    FrameLayout ll_fragment_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        magnifier_bottom_layout = findViewById(R.id.magnifier_bottom_layout);
        ll_fragment_content = findViewById(R.id.ll_fragment_content);
        ll_fragment_content.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                magnifier_bottom_layout.setShowMagnifier(event, ll_fragment_content);
                break;
            case MotionEvent.ACTION_UP:
                magnifier_bottom_layout.hideMagnifier(ll_fragment_content);
                break;
        }
        return true;
    }
}