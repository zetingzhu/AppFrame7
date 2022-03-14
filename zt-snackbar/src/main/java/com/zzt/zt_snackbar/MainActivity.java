package com.zzt.zt_snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    String TAG = "ZZZZ";
    TextView tv_text;
    Button btn_show, btn_skip, btn_skip_test;
    CoordinatorLayout coorl_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_text = findViewById(R.id.tv_text);
        btn_show = findViewById(R.id.btn_show);
        btn_skip = findViewById(R.id.btn_skip);
        btn_skip_test = findViewById(R.id.btn_skip_test);
        coorl_layout = findViewById(R.id.coorl_layout);
        btn_skip_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestSnackbarUtilsActivity.class));
            }
        });
        btn_skip.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, TestAct2.class);
            startActivity(intent);
            SnackBarHelper.startSnackbar("下个页面显示");

//            ViewParent parent = btn_skip.getParent();
//            SnackbarUtils.Indefinite(btn_skip, "上面")
//                    .gravityFrameLayout(Gravity.TOP)
//                    .above(btn_show, 0, 0, 0)
//                    .setCallback(new Snackbar.Callback(){
//                        @Override
//                        public void onShown(Snackbar sb) {
//                            super.onShown(sb);
//                            Log.d(TAG , "显示") ;
//                            ViewParent parent1 = sb.getView().getParent();
//                            Log.d(TAG , parent1.toString());
//                        }
//
//                        @Override
//                        public void onDismissed(Snackbar transientBottomBar, int event) {
//                            super.onDismissed(transientBottomBar, event);
//
//                            Log.d(TAG , "消失") ;
//                        }
//                    })
//                    .show();
        });
        btn_show.setOnClickListener(v -> {
            int total = 0;
            int[] locations = new int[2];
            getWindow().findViewById(android.R.id.content).getLocationInWindow(locations);
            total = locations[1];
            SnackbarUtils.Custom(tv_text, "10s+左右drawable+背景色+圆角带边框+指定View下方", 1000 * 10)
                    .leftAndRightDrawable(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .backColor(0XFF668899)
                    .radius(16, 1, Color.BLUE)
                    .above(btn_show, total, 16, 16)
                    .show();
        });

    }
}