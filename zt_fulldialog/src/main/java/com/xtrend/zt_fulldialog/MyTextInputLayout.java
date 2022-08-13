package com.xtrend.zt_fulldialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.textfield.TextInputLayout;


import java.lang.reflect.Field;

/**
 * @author: zeting
 * @date: 2022/7/12
 * 定义 TextInputLayout 文案多行显示
 */
public class MyTextInputLayout extends TextInputLayout {
    private static final String TAG = MyTextInputLayout.class.getSimpleName();

    public MyTextInputLayout(@NonNull Context context) {
        super(context);
    }

    public MyTextInputLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextInputLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("RestrictedApi")
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, ">>>  onFinishInflate ");
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childAt = getChildAt(i);
//            Log.d(TAG, ">>>:" + childAt.getClass().getSimpleName());
//            int hintId = R.id.textinput_counter;
//            Log.d(TAG, ">>> 是否id相同：" + (hintId == childAt.getId()));
//        }
//        setHelperText("fkdlajf jkfldajfl jkfldajkf jkldajdkfs jklfdjaklfj jkfdlajdf ");
//        setHelperTextTextAppearance(R.style.TextInputCounter);
//
//        setHintTextAppearance(R.style.TextInputCounter);
//        collapsingTextHelper.setMaxLines(2);

        try {
            Field f = getClass().getSuperclass().getDeclaredField("collapsingTextHelper");
            f.setAccessible(true);
            CollapsingTextHelper helper1 = (CollapsingTextHelper) f.get(this);
            if (helper1 != null) {
                helper1.setMaxLines(2);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, ">>>  onAttachedToWindow " + getChildCount());
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, ">>>  onWindowFocusChanged " + getChildCount());
    }


    @Override
    public void dispatchProvideAutofillStructure(@NonNull ViewStructure structure, int flags) {
        super.dispatchProvideAutofillStructure(structure, flags);

//        String string = getResources().getString(R.string.s8_109);
//
//        if (this.getEditText() != null) {
//            this.getEditText().setSingleLine(false);
//            if (this.getEditText().getMaxLines() == 1) {
//                this.getEditText().setMaxLines(Integer.MAX_VALUE);
//            }
//            this.getEditText().setHint(string);
//        }
    }
}
