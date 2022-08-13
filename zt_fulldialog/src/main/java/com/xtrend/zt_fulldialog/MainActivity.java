package com.xtrend.zt_fulldialog;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tv_title;

    com.google.android.material.textfield.TextInputLayout googleText;
    com.xtrend.zt_fulldialog.googlecopy.TextInputLayout myText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initRegex();
    }

    private void initRegex() {
        String value = "fdaflkjl    www.baidu.com   jfkdajfd skjfldsa f  fdka  http://www.xtrendspeed.com jkfldjakl fjdlafkajf  https://www.qq.com";

        String regexHttp1 = "^*((https|http)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)*$";
        Pattern patternHttp1 = Pattern.compile(regexHttp1);
        Matcher matcherHttp = patternHttp1.matcher(value);

        int start = 0;
        int end = 0;
        while (matcherHttp.find()) {
            start = matcherHttp.start();
            end = matcherHttp.end();

            String atagString = value.substring(start, end);
            Log.d(TAG, "html 解析，111 查找内容：" + atagString);

        }
        String regexHttp2 = "^*((https|http)?://)?" +
                "(([0-9a-z_!~*'()-]+\\.)*)?" +
                "((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)*$";
        Pattern patternHttp2 = Pattern.compile(regexHttp2);
        Matcher matcherHttp2 = patternHttp2.matcher(value);
        while (matcherHttp.find()) {
            start = matcherHttp.start();
            end = matcherHttp.end();

            String atagString = value.substring(start, end);
            Log.d(TAG, "html 解析，222 查找内容：" + atagString);

        }
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