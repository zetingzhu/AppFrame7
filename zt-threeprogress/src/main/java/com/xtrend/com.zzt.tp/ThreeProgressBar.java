package com.xtrend.com.zzt.tp;


import android.content.Context;

import android.graphics.Canvas;

import android.graphics.LinearGradient;

import android.graphics.Paint;

import android.graphics.RectF;

import android.graphics.Shader;

import android.nfc.Tag;
import android.util.AttributeSet;

import android.util.Log;

import android.view.View;

import com.xtrend.zt_threeprogress.R;

/**
 * @author: zeting
 * @date: 2022/2/28
 */

public class ThreeProgressBar extends View {
    private static final String TAG = ThreeProgressBar.class.getSimpleName();

    private float maxCount = 100; //进度条最大值

    private float currentCount; //进度条当前值

// private Paint mPaint ;

    private int mWidth, mHeight;

    // 间隔
    private int spaceWidth;

    private Context mContext;

    public ThreeProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {


        super(context, attrs, defStyleAttr);

        initView(context);

    }

    public ThreeProgressBar(Context context, AttributeSet attrs) {


        super(context, attrs);

        initView(context);

    }

    public ThreeProgressBar(Context context) {


        super(context);

        initView(context);

    }

    private void initView(Context context) {


        mContext = context;

    }

    @Override

    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);

        Paint mPaint = new Paint();

        mPaint.setAntiAlias(true);

        int round = mHeight / 2; //半径

        mPaint.setColor(getResources().getColor(R.color.color_876666)); //设置边框背景颜色

        RectF rectBg1 = new RectF(0, 0, mWidth / 3 - spaceWidth / 2, mHeight);
        canvas.drawRoundRect(rectBg1, round, round, mPaint);//绘制 最外面的大 圆角矩形，背景为白色

        RectF rectBg2 = new RectF(mWidth / 3 + spaceWidth / 2, 0, mWidth * 2 / 3 - spaceWidth / 2, mHeight);
        canvas.drawRoundRect(rectBg2, round, round, mPaint);//绘制 最外面的大 圆角矩形，背景为白色

        RectF rectBg3 = new RectF(mWidth * 2 / 3 + spaceWidth / 2, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg3, round, round, mPaint);//绘制 最外面的大 圆角矩形，背景为白色
        mPaint.setColor(getResources().getColor(R.color.color_f57293));
        RectF rectProgressBg = null;
        float section = 0;


        float maxWidth = (mWidth - (spaceWidth * 2));
        if (currentCount <= maxCount / 3) {
            section = currentCount / maxCount; //进度条的比例

            if ((maxWidth * section - 0) >= mHeight / 2) {
                rectProgressBg = new RectF(0, 0, maxWidth * section, mHeight);
                canvas.drawRoundRect(rectProgressBg, round, round, mPaint); //最左边的圆角矩形
            } else {
                float wf = (maxWidth * section - 0) / (mHeight / 2);
                Log.d(TAG, "进度和半圆弧比值" + wf);
                float addAngle = 90 * wf;
                Log.d(TAG, "进度和90度弧度的比值" + addAngle);
                float startAngle = 180 - addAngle;
                Log.d(TAG, "半圆弧开始角度" + addAngle);

                RectF rectF = new RectF(0, 0, mHeight, mHeight);
                canvas.drawArc(rectF, startAngle, addAngle * 2, false, mPaint);

            }
        } else if (currentCount <= maxCount * 2 / 3) {
            float ss = (currentCount - maxCount / 3) / maxCount;
            float addWidthS = maxWidth * ss;
            float maxWidthLeft2 = mWidth / 3 + (spaceWidth / 2);
            rectProgressBg = new RectF(0, 0, mWidth / 3 - spaceWidth / 2, mHeight);
            canvas.drawRoundRect(rectProgressBg, round, round, mPaint); //最左边的圆角矩形

            if (addWidthS >= mHeight / 2) {
                rectProgressBg = new RectF(maxWidthLeft2, 0, maxWidthLeft2 + addWidthS, mHeight);
                canvas.drawRoundRect(rectProgressBg, round, round, mPaint); //最左边的圆角矩形
            } else {
                float wf = addWidthS / (mHeight / 2);
                Log.d(TAG, "进度和半圆弧比值" + wf);
                float addAngle = 90 * wf;
                Log.d(TAG, "进度和90度弧度的比值" + addAngle);
                float startAngle = 180 - addAngle;
                Log.d(TAG, "半圆弧开始角度" + addAngle);

                RectF rectF = new RectF(maxWidthLeft2, 0, maxWidthLeft2 + mHeight, mHeight);
                canvas.drawArc(rectF, startAngle, addAngle * 2, false, mPaint);
            }
        } else {
            float st = (currentCount - (maxCount * 2 / 3)) / maxCount;
            float addWidthT = maxWidth * st;
            float maxWidthLeft2 = mWidth / 3 + (spaceWidth / 2);
            float maxWidthRight2 = mWidth * 2 / 3 - (spaceWidth / 2);
            float maxWidthLeft3 = mWidth * 2 / 3 + (spaceWidth / 2);

            rectProgressBg = new RectF(0, 0, mWidth / 3 - spaceWidth / 2, mHeight);
            canvas.drawRoundRect(rectProgressBg, round, round, mPaint); //最左边的圆角矩形

            rectProgressBg = new RectF(maxWidthLeft2, 0, maxWidthRight2, mHeight);
            canvas.drawRoundRect(rectProgressBg, round, round, mPaint); //最左边的圆角矩形
            if (addWidthT >= mHeight / 2) {
                if (currentCount == maxCount) {
                    rectProgressBg = new RectF(maxWidthLeft3, 0, mWidth, mHeight);
                } else {
                    rectProgressBg = new RectF(maxWidthLeft3, 0, maxWidthLeft3 + addWidthT, mHeight);
                }
                canvas.drawRoundRect(rectProgressBg, round, round, mPaint); //最左边的圆角矩形
            } else {
                float wf = addWidthT / (mHeight / 2);
                Log.d(TAG, "进度和半圆弧比值" + wf);
                float addAngle = 90 * wf;
                Log.d(TAG, "进度和90度弧度的比值" + addAngle);
                float startAngle = 180 - addAngle;
                Log.d(TAG, "半圆弧开始角度" + addAngle);

                RectF rectF = new RectF(maxWidthLeft3, 0, maxWidthLeft3 + mHeight, mHeight);
                canvas.drawArc(rectF, startAngle, addAngle * 2, false, mPaint);
            }
        }
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /***
     * 设置最大的进度值
     * @param maxCount 最大的进度值
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     * @param currentCount 当前进度值
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        spaceWidth = mWidth / 20;
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(18);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

}