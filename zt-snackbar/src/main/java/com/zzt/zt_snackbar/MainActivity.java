package com.zzt.zt_snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.google.android.material.internal.ViewUtils;
import com.google.android.material.snackbar.Snackbar;
import com.material.snackbar.MBaseTransientBottomBar;
import com.material.snackbar.MSnackbar;
import com.zzt.adapter.StartActivityRecyclerAdapter;
import com.zzt.entity.StartActivityDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG = "ZZZZ";

    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        List<StartActivityDao> mListDialog = new ArrayList<>();
        mListDialog.add(new StartActivityDao("跳通用设置页面", "", "1"));
        mListDialog.add(new StartActivityDao("跳test2 页面", "", "2"));
        mListDialog.add(new StartActivityDao("SnackbarUtils 常用设置", "", "3"));
        mListDialog.add(new StartActivityDao("MSnackbar 使用", "", "4"));

        StartActivityRecyclerAdapter.setAdapterData(recyclerview, RecyclerView.VERTICAL, mListDialog, (itemView, position, data) -> {
            switch (data.getArouter()) {
                case "1":
                    startActivity(new Intent(MainActivity.this, TestSnackbarUtilsActivity.class));
                    break;
                case "2":
                    SnackBarHelper.getInstance().showSnackbar(recyclerview, "看看这个能不能跨页面来");
                    Intent intent = new Intent(MainActivity.this, TestAct2.class);
                    startActivity(intent);
                    break;
                case "3":
                    int total = 0;
                    int[] locations = new int[2];
                    getWindow().findViewById(android.R.id.content).getLocationInWindow(locations);
                    total = locations[1];
                    SnackbarUtils.Custom(recyclerview, "10s+左右drawable+背景色+圆角带边框+指定View下方", 1000 * 10)
                            .leftAndRightDrawable(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                            .backColor(0XFF668899)
                            .radius(16, 1, Color.BLUE)
                            .above(recyclerview, total, 16, 16)
                            .show();
                    break;
                case "4":
                    MSnackbar.make(recyclerview, "dddddddddd", MSnackbar.LENGTH_LONG)
//                            .setAnimationMode(MBaseTransientBottomBar.ANIMATION_MODE_FADE)
                            .show();
                    break;
            }
        });

    }
}