package com.zzt.appframe7;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import androidx.appcompat.app.AppCompatActivity;

public class TextActV2 extends AppCompatActivity {
    private static final String TAG = TextActV2.class.getSimpleName();

    private SparseArray<Integer> tradeAnswer = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_act_v2);
        initData();
    }

    private void initData() {
        setTradeAnswer(20, 20);
        setTradeAnswer(21, 21);
        setTradeAnswer(21, 22);
        setTradeAnswer(22, 23);

        Log.e(TAG, "打印数据：" + tradeAnswer.toString());

        Integer tradeAnswer = getTradeAnswer(21);
        Log.e(TAG, "获取值 21 ：" + tradeAnswer);


        Integer tradeAnswer33 = getTradeAnswer(33);
        Log.e(TAG, "获取值 33 ：" + tradeAnswer33);


    }

    public Integer getTradeAnswer(int queId) {
        if (tradeAnswer != null) {
            return tradeAnswer.get(queId, -1);
        }
        return -1;
    }

    public void setTradeAnswer(int queId, int queOptionId) {
        if (tradeAnswer == null) {
            tradeAnswer = new SparseArray<>();
        }
        tradeAnswer.put(queId, queOptionId);
    }
}