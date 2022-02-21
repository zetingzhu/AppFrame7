package com.zzt.appframe7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TestActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
        findViewById<Button>(R.id.button6).setOnClickListener {
            SnackbarHelper.getInstance().finish(this@TestActivity2 , "展示的内容消息是eeee")
        }
    }
}