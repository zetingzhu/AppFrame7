package com.zzt.appframe7

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mContentView: LinearLayout = findViewById(R.id.ll_content)
        findViewById<Button>(R.id.button)
            .setOnClickListener {
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                SnackbarUtilsV1.with(mContentView)
                    .setBgColor(Color.TRANSPARENT)
                    .setDuration(SnackbarUtilsV1.LENGTH_INDEFINITE)
                    .show(true)
                SnackbarUtilsV1.addView(R.layout.snackbar_custom, params)
            }
        findViewById<Button>(R.id.button1).setOnClickListener {
            SnackbarUtilsV1.with(mContentView)
                .setMessage("ffffffffffffffffff")
                .setMessageColor(Color.WHITE)
                .setBgResource(R.drawable.snackbar_custom_bg)
                .show(true)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            SnackbarUtilsV1.with(mContentView)
                .setMessage("gggggggdfafdsafafdaf")
                .setMessageColor(Color.WHITE)
                .setBgResource(R.drawable.snackbar_custom_bg)
                .setAction("点击", Color.YELLOW) {
                    Toast.makeText(this@MainActivity, "点击了", Toast.LENGTH_SHORT).show()
                }
                .show(true)
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            SnackbarUtils.Short(mContentView, "文字位置:默认").info().gravityFrameLayout(Gravity.CENTER)
                .show();
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            SnackbarHelper.getInstance().startActivity(this@MainActivity)
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            val imageView = ImageView(mContentView.context)
            imageView.setImageResource(R.mipmap.ic_launcher)
            SnackbarUtils.Short(mContentView, "向Snackbar布局中添加View").addView(imageView, 0)
                .gravityFrameLayout(Gravity.CENTER)
                .show()
        }
        findViewById<Button>(R.id.button8).setOnClickListener {
            SnackbarUtils.Custom(mContentView,"10s+左右drawable+背景色+圆角带边框+指定View下方",1000*10)
                .leftAndRightDrawable(R.mipmap.ic_launcher,R.mipmap.ic_launcher)
                .backColor(Color.parseColor("#668899"))
                .radius(16,1,Color.BLUE)
//                .bellow(bt_margins,total,16,16)
                .show();

//            SnackbarUtils.Long(mContentView, "wwwwwwwwwwww").show()
        }
    }
}