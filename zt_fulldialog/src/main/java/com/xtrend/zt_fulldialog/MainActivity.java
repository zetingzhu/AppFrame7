package com.xtrend.zt_fulldialog;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(Html.fromHtml(getResources().getString(R.string.s9_151, "22222")));


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockClosedDialog dialog = new StockClosedDialog(MainActivity.this);
                dialog.show();
            }
        });
    }
}