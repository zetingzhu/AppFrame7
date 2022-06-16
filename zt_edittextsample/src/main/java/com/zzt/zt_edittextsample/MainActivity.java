package com.zzt.zt_edittextsample;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    VerificationCodeView codeView;
    EditText te_11;
    LinearLayout ll_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeView = findViewById(R.id.view_verification_code);
        te_11 = findViewById(R.id.te_11);
        ll_room = findViewById(R.id.ll_room);
        te_11.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        codeView.setEditWidth(120);
        codeView.setCodeLength(4);
        codeView.setEditTextSize(16);
        codeView.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);


        EditText editText = new EditText(this);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        editText.setHint("4444");
        ll_room.addView(editText);


        EditText et = new EditText(this);
        et.setBackgroundResource(R.drawable.login_sms_code_edit);
        et.setGravity(Gravity.CENTER);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.addTextChangedListener(new TextWatcher(0));
        et.setOnKeyListener(new OnKeyListener(0));
        et.setTextColor(Color.parseColor("#23ff32"));
        et.getPaint().setFakeBoldText(true);
        et.setSingleLine();
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        et.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        et.setHint("5555");
        ll_room.addView(et);

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
//            if (textChangedListener != null)
//                textChangedListener.onTextChanged(VerificationCodeView.this);
        }

        private void handleTextChanged(Editable s) {
            if (s.length() == 0)
                return;
//            EditText et = getNextEdit(num);
//            if (s.length() > 1) {
//                EditText currentEdit = getCurrentEdit(num);
//                currentEdit.setText(s.subSequence(0, 1));
//                currentEdit.setSelection(1);
//                if (et != null) {
//                    et.requestFocus();
//                    et.setText(s.subSequence(s.length() - 1, s.length()));
//                    et.setSelection(1);
//                }
//                return;
//            }
//            if (et != null && et.getText().toString().length() == 0)
//                et.requestFocus();
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
//                EditText et = getPreEdit(num);
//                if (et != null) {
//                    et.setText(null);
//                    et.requestFocus();
//                }
                return true;
            }
            return false;
        }
    }
}