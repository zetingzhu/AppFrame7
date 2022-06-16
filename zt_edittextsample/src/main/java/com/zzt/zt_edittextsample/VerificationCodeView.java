package com.zzt.zt_edittextsample;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lin on 2018/10/12.
 */
public class VerificationCodeView extends LinearLayout {

    private int editWidth;
    private int codeLength;
    private List<EditText> editTexts = new ArrayList<>();
    private OnTextChangedListener textChangedListener;

    public VerificationCodeView(Context context) {
        super(context);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEditWidth(int width) {
        editWidth = width;
    }

    public void setCodeLength(int length) {
        codeLength = length;
        for (int i = 0; i < length; i++) {
            EditText et = new EditText(getContext());
            et.setBackgroundResource(R.drawable.login_sms_code_edit);
            et.setGravity(Gravity.CENTER);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.addTextChangedListener(new TextWatcher(i));
            et.setOnKeyListener(new OnKeyListener(i));
            et.setTextColor(Color.parseColor("#23ff32"));
            et.getPaint().setFakeBoldText(true);
            et.setSingleLine();
            et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            et.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
            // 设置光标
//            try {
//                Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
//                f.setAccessible(true);
//                f.set(et, R.drawable.login_cursor_drawable);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }

            int width = -2;
            if (editWidth != 0)
                width = editWidth;
            LayoutParams params = new LayoutParams(width, -1);
            addView(et, params);
            editTexts.add(et);
            if (i < length - 1) {
                View view = new View(getContext());
                params = new LayoutParams(-2, -2, 1);
                addView(view, params);
            }
        }
    }

    public void setTextChangedListener(OnTextChangedListener listener) {
        textChangedListener = listener;
    }

    private EditText getCurrentEdit(int num) {
        return editTexts.get(num);
    }

    private EditText getPreEdit(int num) {
        if (num > 0)
            return editTexts.get(num - 1);
        return null;
    }

    private EditText getNextEdit(int num) {
        if (num < codeLength - 1)
            return editTexts.get(num + 1);
        return null;
    }

    class TextWatcher implements android.text.TextWatcher {

        int num;

        public TextWatcher(int num) {
            this.num = num;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            handleTextChanged(s);
            if (textChangedListener != null)
                textChangedListener.onTextChanged(VerificationCodeView.this);
        }

        private void handleTextChanged(Editable s) {
            if (s.length() == 0)
                return;
            EditText et = getNextEdit(num);
            if (s.length() > 1) {
                EditText currentEdit = getCurrentEdit(num);
                currentEdit.setText(s.subSequence(0, 1));
                currentEdit.setSelection(1);
                if (et != null) {
                    et.requestFocus();
                    et.setText(s.subSequence(s.length() - 1, s.length()));
                    et.setSelection(1);
                }
                return;
            }
            if (et != null && et.getText().toString().length() == 0)
                et.requestFocus();
        }
    }

    class OnKeyListener implements View.OnKeyListener {

        int num;

        public OnKeyListener(int num) {
            this.num = num;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.getAction() == KeyEvent.ACTION_DOWN
                    && ((EditText) v).getText().length() == 0) {
                EditText et = getPreEdit(num);
                if (et != null) {
                    et.setText(null);
                    et.requestFocus();
                }
                return true;
            }
            return false;
        }
    }

    public String getText() {
        String text = "";
        for (EditText et : editTexts)
            text += et.getText().toString();
        return text;
    }

    public boolean isCompleted() {
        if (getText().length() == codeLength)
            return true;
        return false;
    }

    public void clear() {
//        for (EditText et : editTexts) {
//            et.setText(null);
//            et.setBackgroundResource(R.drawable.login_sms_code_edit);
//            et.setTextColor(getContext().getResources().getColor(R.color.app_main_black_v3));
//        }
        editTexts.get(0).requestFocus();
    }

    public void setEditTextColor(int color) {
        for (EditText et : editTexts)
            et.setTextColor(color);
    }

    public void setEditTextSize(int textSize) {
        for (EditText et : editTexts)
            et.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setEditBackground(int res) {
        for (EditText et : editTexts) {
            et.setBackgroundResource(res);
        }
    }

    public void setImeOptions(int imeOptions) {
        for (EditText et : editTexts) {
            et.setImeOptions(imeOptions);
        }
    }

    public interface OnTextChangedListener {
        void onTextChanged(VerificationCodeView v);
    }
}
