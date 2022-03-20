package com.zzt.appframe7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class InputFilterAct extends AppCompatActivity {
    String TAG = "InputFilterAct";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_filter);
        initView();
    }

    private void initView() {
        editText = findViewById(R.id.editText);
        NumberAmountUtils.setAmountThousEdittext(editText, true, 8, 4);
    }
}