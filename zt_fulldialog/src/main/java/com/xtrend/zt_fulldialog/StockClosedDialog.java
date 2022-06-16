package com.xtrend.zt_fulldialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * @author: zeting
 * @date: 2022/2/15
 * 股票休市
 */
public class StockClosedDialog extends Dialog {
    public static final String TAG = StockClosedDialog.class.getSimpleName();
    public Context mContext;
    // 背景
    private ConstraintLayout z_dialog_col_bg;
    // 标题
    private AppCompatTextView z_dialog_tv_title;
    // 右上角删除图片
    private AppCompatImageView z_dialog_iv_close;
    AppCompatTextView tv_stock_closed_time;
    // 休市结束时间，开始时间
    public long endTime;


    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public StockClosedDialog(Context context) {
        super(context, R.style.BaseDialog1);
    }

    public StockClosedDialog(Context context, int theme) {
        super(context, theme);
    }

    protected StockClosedDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_stock_closed_layout);
        mContext = getContext();
//        Window w = getWindow();
//        WindowManager.LayoutParams params = w.getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.MATCH_PARENT;
//        getWindow().setGravity(Gravity.BOTTOM);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.TOP;
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(attributes);

        initView();
    }

    @Override
    public void show() {
        super.show();

//        Window window = getWindow();
//        window.setGravity(Gravity.CENTER);
//        window.getDecorView().setPadding(0, 0, 0, 0);
//
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);

//
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.TOP;
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(attributes);


        //设置全屏
//        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
////设置dialog沉浸式效果
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        } else {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
    }

    private void initView() {
        z_dialog_col_bg = findViewById(R.id.z_dialog_col_bg);
        z_dialog_tv_title = findViewById(R.id.z_dialog_tv_title);
        z_dialog_iv_close = findViewById(R.id.z_dialog_iv_close);
        tv_stock_closed_time = findViewById(R.id.tv_stock_closed_time);
        z_dialog_iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        if (endTime != 0) {
//            tv_stock_closed_time.setText("Open at " + DateUtil.formatMDHM(endTime));
        }
    }


    /**
     * 设置标题展示
     *
     * @param text
     */
    public void setTitleText(String text) {
        if (!TextUtils.isEmpty(text)) {
            z_dialog_tv_title.setText(text);
        }
    }

    public Context getMContext() {
        return mContext;
    }
}
