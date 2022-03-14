package com.zzt.zt_snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestAct2 extends AppCompatActivity {

    TextView tv_text;
    Button btn_show, btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_act2);
        initView();
    }

    private void initView() {
        tv_text = findViewById(R.id.tv_text);
        btn_show = findViewById(R.id.btn_show);
        btn_skip = findViewById(R.id.btn_skip);
        btn_show.setOnClickListener(v -> {
            SnackbarUtils.Long(tv_text, "qqqqqqqqqqqq").show();
        });

        btn_skip.setOnClickListener(v -> {
            SnackBarHelper.finishSnackbar("地下页面显示");
            finish();
        });
    }
}