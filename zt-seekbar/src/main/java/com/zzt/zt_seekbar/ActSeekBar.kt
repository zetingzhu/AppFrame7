package com.zzt.zt_seekbar

import android.graphics.Rect
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ActSeekBar : AppCompatActivity() {
    var sb_bar1: SeekBar? = null
    var sb_bar2: SeekBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_seek_bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    private fun initView() {
        sb_bar1 = findViewById(R.id.sb_bar1)
        sb_bar2 = findViewById(R.id.sb_bar2)

        sb_bar1?.setProgress(50, true)
        sb_bar1?.max = 100

        sb_bar2?.setProgress(30, true)
        sb_bar2?.max = 100

//        sb_bar2?.progressTintList =   ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.deeppink))

//        sb_bar2?.getThumb()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);//滑块
//        sb_bar2?.getProgressDrawable()?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);//进度条

//        val colorFilter =
//            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
//                Color.YELLOW,
//                BlendModeCompat.SRC_ATOP
//            )
//
//        sb_bar2?.getProgressDrawable()?.setColorFilter(colorFilter);

        sb_bar2?.progressDrawable = ColorUtil.genSeekProgressDrawable(baseContext)

//        sb_bar2?.progressTintList =   ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.color_opt_lt))
//        sb_bar2?.progressBackgroundTintList =   ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.color_E0E2F0_or_485266))
//        val bounds  = sb_bar2?.getThumb()?.getBounds()
        val thumb = ContextCompat.getDrawable(baseContext, R.drawable.seekbar_thum_red_style)
//        bounds?.let {
//            thumb?.setBounds(0,0,22,22) };
        sb_bar2?.setThumb(thumb)

        val post = sb_bar2?.post {
            val thumb = sb_bar2?.getThumb()
            thumb?.setBounds(0,0,33,33)
            sb_bar2?.thumb = thumb
        }

//        sb_bar2?.setThumbTintList(ColorStateList.valueOf( ContextCompat.getColor(baseContext, R.color.yellow)))
        sb_bar2?.setPadding(0, 0, 0, 0)
        sb_bar2?.setThumbOffset(20)
        sb_bar2?.splitTrack = false

//        sb_bar2?.setOnSeekBarChangeListener(object :OnSeekBarChangeListener{
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                val thumb = ContextCompat.getDrawable(baseContext, R.drawable.seekbar_thum_red_style)
//                    thumb?.setBounds(0,0,11,11) ;
//                seekBar?.setThumb(thumb)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            }
//        })

//        <!--进度条样式-->
//                android:progressDrawable="@drawable/seekbar_style"
//        <!--进度条颜色-->
//                android:progressTint="@android:color/red"
//        <!--进度条背景颜色-->
//                android:progressBackgroundTint="@android:color/white"
//
//        <!--滑块样式-->
//                android:thumb="@drawable/ic_thumb_bicycle"
//        <!--滑块颜色-->
//                android:thumbTint="@color/red"
//
//        PS: 当同时使用自定义的滑块样式和进度条样式时，若需保持进度条为线状（否则高度将于滑块高度一致），可增加以下属性：
//        <!--限制进度条高度，不影响滑块大小-->
//        android:maxHeight="2dp"


    }
}