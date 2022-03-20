package com.zzt.appframe7

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    var mformat: DecimalFormat = DecimalFormat("#,###,###.##")

    var formatPre = ""

    //  Overriding method should call super.onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editText = findViewById<EditText>(R.id.editText)
//        editText.inputType = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
//        editText.filters = arrayOf<InputFilter>(
//            NumberAmountLengthFilter(8, 2)
////            MLengthFilter(6, 2)
////            , NumberFilter()
//        )
//
//        val formatAmount = NumberAmountUtils.formatAmount("44433332")
//        Log.e(TAG, "formatAmount:" + formatAmount)
//        val moneyType = NumberAmountUtils.formatAmount("44433332.4444")
//        Log.e(TAG, "formatAmount:" + moneyType)
//        val number = NumberAmountUtils.isNumber("44,433,332.01")
//        Log.e(TAG, "isNumber:" + number)

//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                Log.w(TAG, "text:" + s.toString())
//                s?.let {
//                    if (s.isNotEmpty() && !s.toString().endsWith(".")) {
//                        val moneyType = NumberAmountUtils.formatAmount(s.toString())
//                        Log.e(TAG, "text 转换：$moneyType")
//                        if (!moneyType.equals(s.toString())) {
//                            editText.setText(moneyType)
//                            editText.setSelection(moneyType.length)
//                        }
//                    }
//                }
//            }
//        })


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
            SnackbarUtils.Custom(mContentView, "10s+左右drawable+背景色+圆角带边框+指定View下方", 1000 * 10)
                .leftAndRightDrawable(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .backColor(Color.parseColor("#668899"))
                .radius(16, 1, Color.BLUE)
//                .bellow(bt_margins,total,16,16)
                .show();

//            SnackbarUtils.Long(mContentView, "wwwwwwwwwwww").show()


        }
    }
}