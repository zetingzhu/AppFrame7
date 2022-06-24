package com.zzt.magnifier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



/**
 * @author: zeting
 * @date: 2022/6/24
 * 放大镜，放在底部
 */
public class MagnifierBottomLayout extends RelativeLayout {
    private static final String TAG = "Bottom.Magnifier";
    private Bitmap mBitmap;
    private boolean mIsShowMagnifier = false;//是否显示放大镜
    private float mX = 0;
    private float mY = 0;
    private Paint paintBg; // 绘制背景画笔
    private Bitmap cutBitmap;// 裁剪图片
    private Bitmap scaledBitmap;// 裁剪后放大图片
    private Bitmap bigShowBitmap; // 放大变形展示图片

    // 设置控制属性
    private float magnifierRadius;//放大镜的半径
    private float mScaleRate;//放大比例
    private float strokeWidth;//左上角放大镜边大小
    private float leftSpace;//放大镜距离左边距离
    private float bottomSpace;//放大镜距离下方距离


    public MagnifierBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public MagnifierBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnifierBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultData();
        init();
    }

    /**
     * 初始化默认属性
     */
    private void initDefaultData() {
        setMagnifierRadius(dip2px(getContext(), 50));
        setScaleRate(1.3f);
        setStrokeWidth(dip2px(getContext(), 5));
        setLeftSpace(dip2px(getContext(), 10));
        setBottomSpace(dip2px(getContext(), 10));

    }

    /**
     * 初始化画笔
     */
    private void init() {
        paintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBg.setColor(Color.WHITE);
        paintBg.setStyle(Paint.Style.FILL_AND_STROKE);
        paintBg.setStrokeWidth(getStrokeWidth());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG, "DrawCanvas -  " + mIsShowMagnifier);
        if (mIsShowMagnifier && getMagnifierRadius() > 0) {
            // 裁剪图原始放大区域
            cutBitmap = Bitmap.createBitmap(mBitmap,
                    Math.max(0, Math.min((int) (mBitmap.getWidth() - getMagnifierRadius() * 2), (int) (mX - getMagnifierRadius()))),
                    Math.max(0, Math.min((int) (mBitmap.getHeight() - getMagnifierRadius() * 2), (int) (mY - getMagnifierRadius()))),
                    Math.min((int) (getMagnifierRadius() * 2), Math.max(mBitmap.getWidth(), (int) (mX + getMagnifierRadius()))),
                    Math.min((int) (getMagnifierRadius() * 2), Math.max(mBitmap.getHeight(), (int) (mY + getMagnifierRadius()))));
            Log.w(TAG, "裁剪的图片：" + toString(cutBitmap));
            // 放大
            scaledBitmap = Bitmap.createScaledBitmap(cutBitmap, (int) (cutBitmap.getWidth() * getScaleRate()), (int) (cutBitmap.getHeight() * getScaleRate()), true);
            // 变形，搞成圆的
            bigShowBitmap = MagnifierBitmapHelper.GetCircleBitmap(scaledBitmap);
            Log.w(TAG, "放大后的图片：" + toString(bigShowBitmap));

            // 上面
            float rectTop = getHeight() - bigShowBitmap.getHeight() - getBottomSpace();
            // 下面
            float rectBottom = getHeight() - getBottomSpace();
            // 显示在左边位置
            float rectLeft = getLeftSpace();

            // 左右互斥显示
            if (mX < mBitmap.getWidth() * 0.5f) {
                rectLeft = getWidth() - bigShowBitmap.getWidth() - getLeftSpace();
            } else if (mX > mBitmap.getWidth() * 0.5f) {
                rectLeft = getLeftSpace();
            }

            // 画个同心圆背景圆
            RectF rectf = new RectF(rectLeft, rectTop, rectLeft + bigShowBitmap.getWidth(), rectBottom);
//            paintBg.setColor(Color.WHITE);
//            paintBg.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(rectf.centerX(), rectf.centerY(), bigShowBitmap.getWidth() * 0.5F, paintBg);
//            paintBg.setColor(Color.BLACK);
//            paintBg.setStyle(Paint.Style.FILL);
//            canvas.drawCircle(rectf.centerX(), rectf.centerY(), bigShowBitmap.getWidth() * 0.5F, paintBg);

            // 在画个放大的图片
            canvas.drawBitmap(bigShowBitmap, rectLeft, rectTop, null);
        } else {
            super.dispatchDraw(canvas);
        }
    }

    /**
     * 显示放大镜
     *
     * @param event
     * @param viewGroup
     */
    public void setShowMagnifier(MotionEvent event, ViewGroup viewGroup) {
        release();
        MagnifierBitmapHelper.recycler(mBitmap);

        mIsShowMagnifier = true;
        mX = event.getX();
        mY = event.getY();

        if (!MagnifierBitmapHelper.isNotEmpty(mBitmap)) {
            mBitmap = Bitmap.createBitmap(viewGroup.getWidth(), (int) (viewGroup.getHeight()), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(mBitmap);
            canvas.clipRect(0 - getMagnifierRadius(), 0 - getMagnifierRadius(),
                    viewGroup.getWidth() + getMagnifierRadius(), viewGroup.getHeight() + getMagnifierRadius());
            viewGroup.draw(canvas);
        }
        postInvalidate();
    }

    /**
     * 隐藏
     */
    public void hideMagnifier(ViewGroup view) {
        release();
        MagnifierBitmapHelper.recycler(mBitmap);
        mIsShowMagnifier = false;
        postInvalidate();
    }


    public void release() {
        MagnifierBitmapHelper.recycler(cutBitmap, scaledBitmap, bigShowBitmap, mBitmap);
    }

    public String toString(Bitmap bitmap) {
        return "bitmap- W:" + bitmap.getWidth() + " H:" + bitmap.getHeight();
    }

    public float getMagnifierRadius() {
        return magnifierRadius;
    }

    public void setMagnifierRadius(float magnifierRadius) {
        this.magnifierRadius = magnifierRadius;
    }

    public float getScaleRate() {
        return mScaleRate;
    }

    public void setScaleRate(float mScaleRate) {
        this.mScaleRate = mScaleRate;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public float getLeftSpace() {
        return leftSpace;
    }

    public void setLeftSpace(float leftSpace) {
        this.leftSpace = leftSpace;
    }

    public float getBottomSpace() {
        return bottomSpace;
    }

    public void setBottomSpace(float bottomSpace) {
        this.bottomSpace = bottomSpace;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
