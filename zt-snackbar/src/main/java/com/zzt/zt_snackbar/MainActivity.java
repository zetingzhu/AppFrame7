package com.zzt.zt_snackbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.material.snackbar.ToastSnackbar;
import com.trade.utilcode.util.ToastUtils;
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
        mListDialog.add(new StartActivityDao("一直显示", "", "5"));
        mListDialog.add(new StartActivityDao("ToastSnackbar 测试", "", "6"));
        mListDialog.add(new StartActivityDao("成功的", "", "7"));
        mListDialog.add(new StartActivityDao("失败的", "", "8"));
        mListDialog.add(new StartActivityDao("订单的", "", "9"));

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
                    ToastSnackbar.make(recyclerview, "dddddddddd", ToastSnackbar.LENGTH_LONG)
//                            .setAnimationMode(MBaseTransientBottomBar.ANIMATION_MODE_FADE)
                            .setAction(null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showShort("这个按钮被点击了");
                                }
                            })
                            .show();
                    break;
                case "5":
                    ToastSnackbar.make(recyclerview, "The default garbage collector was used in this build running " +
                            "with JDK 11. Note that the default GC was changed starting with JDK 9. " +
                            "This could impact your build performance by as much as 10%. " +
                            "Recommendation: Fine tune your JVM .", ToastSnackbar.LENGTH_INDEFINITE)
                            .setAction(null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showShort("这个按钮被点击了");
                                }
                            })
                            .setBackgroundTint(Color.BLUE)
                            .show();
                    break;
                case "6":
                    ToastSnackbar.make(recyclerview, "The default garbage collector was used in this build running " +
                            "with JDK 11. Note that the default GC was changed starting with JDK 9. " +
                            "This could impact your build performance by as much as 10%. " +
                            "Recommendation: Fine tune your JVM . content parent ", ToastSnackbar.LENGTH_INDEFINITE)
                            .setAction(null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showShort("这个按钮被点击了");
                                }
                            })
                            .setBackgroundTint(Color.WHITE)
                            .margins(30, 50, 30, 30)
                            .padding(20, 20, 20, 20)
                            .elevation(10F)
                            .layoutHelper(30, 30, Color.parseColor("#FF0000"), 1.0f)
                            .disallowIntercept(true)
                            .show();
                    break;
                case "7":
                    SnackbarUtil.makeSuccess(recyclerview,
                            "成功",
                            ToastSnackbar.LENGTH_LONG)
                            .setAction(null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showShort("这个按钮被点击了");
                                }
                            })
                            .show();
                    break;
                case "8":
                    SnackbarUtil.makeFailed(recyclerview,
                            "失败",
                            ToastSnackbar.LENGTH_LONG)
                            .setAction(null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showShort("这个按钮被点击了");
                                }
                            })
                            .show();
                    break;
                case "9":
                    SnackbarUtil.makeOrder(recyclerview,
                            "订单",
                            ToastSnackbar.LENGTH_LONG)
                            .setAction(null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtils.showShort("这个按钮被点击了");
                                }
                            })
                            .show();
                    break;
            }
        });

    }
}