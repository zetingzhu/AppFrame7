package com.zzt.zt_gson;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initData();
    }

    //1趋势指标  2摆动指标  3能量指标  4压力支撑 5超买超卖指标 6反趋势指标 7 其他 8 量价

    /**
     * 所有指标默认色值按以下顺序：
     * #C0001C 100%
     * #0087FF 100%
     * #7AC700 100%
     * #FF9C00 100%
     * #702F00 100%
     * #C400ED 100%
     */
    private void initData() {
        List<IndexValueStyleObj> allList = new ArrayList<>();


        List<IndexValueObj> SMAp = new ArrayList<>();
        SMAp.add(new IndexValueObj("MA1", "5_280", "1", "", "5", "0", "1111"));
        SMAp.add(new IndexValueObj("MA2", "5_280", "2", "", "13", "0", "1111"));
        SMAp.add(new IndexValueObj("MA3", "5_280", "3", "", "55", "0", "1111"));
        SMAp.add(new IndexValueObj("MA4", "5_280", "4", "", "144", "0", "1111"));
        SMAp.add(new IndexValueObj("MA5", "5_280", "5", "", "233", "0", "1111"));

        List<IndexStyleObj> SMAs = new ArrayList<>();
        SMAs.add(new IndexStyleObj("MA1", "#C0001C", "1", "1"));
        SMAs.add(new IndexStyleObj("MA2", "#0087FF", "1", "1"));
        SMAs.add(new IndexStyleObj("MA3", "#7AC700", "1", "1"));
        SMAs.add(new IndexStyleObj("MA4", "#FF9C00", "1", "1"));
        SMAs.add(new IndexStyleObj("MA5", "#702F00", "1", "1"));
        IndexValueStyleObj SMAObj = new IndexValueStyleObj("SMA", "5_299", "", "1", "1", SMAp, SMAs);
        allList.add(SMAObj);

        List<IndexValueObj> params2 = new ArrayList<>();
        params2.add(new IndexValueObj("MA1", "5_280", "1", "", "5", "1", "300"));
        params2.add(new IndexValueObj("MA2", "5_280", "2", "", "10", "1", "300"));
        params2.add(new IndexValueObj("MA3", "5_280", "3", "", "20", "1", "300"));
        params2.add(new IndexValueObj("MA4", "5_280", "4", "", "60", "1", "300"));

        List<IndexStyleObj> style2 = new ArrayList<>();
        style2.add(new IndexStyleObj("MA1", "#C0001C", "1", "1"));
        style2.add(new IndexStyleObj("MA2", "#0087FF", "1", "1"));
        style2.add(new IndexStyleObj("MA3", "#7AC700", "1", "1"));
        style2.add(new IndexStyleObj("MA4", "#FF9C00", "1", "1"));
        IndexValueStyleObj obj2 = new IndexValueStyleObj("EMA", "5_300", "", "1", "1", params2, style2);
        allList.add(obj2);


        List<IndexValueObj> params3 = new ArrayList<>();
        params3.add(new IndexValueObj("周期", "5_281", "", "", "26", "1", "300"));
        params3.add(new IndexValueObj("标准离差", "5_282", "", "", "20", "0", "300"));

        List<IndexStyleObj> style3 = new ArrayList<>();
        style3.add(new IndexStyleObj("MID", "#C0001C", "1", "1"));
        style3.add(new IndexStyleObj("UPPER", "#0087FF", "1", "1"));
        style3.add(new IndexStyleObj("LOWER", "#7AC700", "1", "1"));
        IndexValueStyleObj obj3 = new IndexValueStyleObj("BOLL", "5_301", "", "1", "4", params3, style3);
        allList.add(obj3);


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期", "5_283", "", "", "10", "1", "300"));
        params3.add(new IndexValueObj("步长", "5_284", "", "", "2", "1", "300"));
        params3.add(new IndexValueObj("极限值", "5_285", "", "", "20", "1", "300"));

        style3 = new ArrayList<>();
        style3.add(new IndexStyleObj("BB", "#C0001C", "1", "1"));
        allList.add(new IndexValueStyleObj("SAR", "5_302", "", "1", "1", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("移动平均周期1", "5_289", "", "", "20", "1", "300"));
        params3.add(new IndexValueObj("移动平均周期2", "5_289", "", "", "14", "1", "300"));
        style3 = new ArrayList<>();
        style3.add(new IndexStyleObj("ML", "#C0001C", "1", "1"));
        style3.add(new IndexStyleObj("UB", "#0087FF", "1", "1"));
        allList.add(new IndexValueStyleObj("KC", "5_302", "", "1", "4", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("长线周期", "5_286", "", "", "44", "1", "300"));
        params3.add(new IndexValueObj("中线周期", "5_287", "", "", "22", "1", "300"));
        params3.add(new IndexValueObj("短线周期", "5_288", "", "", "7", "1", "300"));
        style3 = new ArrayList<>();
        style3.add(new IndexStyleObj("CL", "#C0001C", "1", "1"));
        style3.add(new IndexStyleObj("DL", "#0087FF", "1", "1"));
        style3.add(new IndexStyleObj("LL", "#AC700", "1", "1"));
        style3.add(new IndexStyleObj("A", "#FF9C00", "1", "1"));
        style3.add(new IndexStyleObj("B", "#702F", "1", "1"));
        style3.add(new IndexStyleObj("(A-B)", "#C400ED", "1", "1"));
        style3.add(new IndexStyleObj("(B-A)", "#C400ED", "1", "1"));
        allList.add(new IndexValueStyleObj("IC", "5_304", "", "1", "7", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期1", "5_283", "1", "", "3", "1", "100"));
        params3.add(new IndexValueObj("计算周期2", "5_283", "2", "", "6", "1", "100"));
        params3.add(new IndexValueObj("计算周期3", "5_283", "3", "", "12", "1", "100"));
        params3.add(new IndexValueObj("计算周期4", "5_283", "4", "", "24", "1", "100"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{"BBI"}));
        allList.add(new IndexValueStyleObj("BBI", "5_305", "", "1", "1", params3, style3));


        params3 = new ArrayList<>();
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{"CDP"
                , "AH"
                , "AL"
                , "NH"
                , "NL"}));
        allList.add(new IndexValueStyleObj("CDP", "5_306", "", "1", "4", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期1", "5_283", "1", "", "25", "2", "120"));
        params3.add(new IndexValueObj("计算周期2", "5_283", "2", "", "6", "2", "120"));
        params3.add(new IndexValueObj("计算周期3", "5_283", "3", "", "6", "2", "120"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "UPPER",
                "LOWER",
                "ENE",
        }));
        allList.add(new IndexValueStyleObj("ENE", "5_307", "", "1", "4", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期", "5_295", "", "", "12", "1", "200"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "WR",
                "MR",
                "SR",
                "WS",
                "MS",
                "SS",
        }));
        allList.add(new IndexValueStyleObj("MIKE", "5_309", "", "1", "4", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("移动平均周期", "5_296", "", "", "11", "2", "120"));
        params3.add(new IndexValueObj("计算周期", "5_295", "", "", "6", "2", "120"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "BBIBOLL",
                "UPR",
                "DWN",
        }));
        allList.add(new IndexValueStyleObj("BBIBOLL", "5_309", "", "1", "4", params3, style3));


        params3 = new ArrayList<>();
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "DOWN(1-8)",
                "DOWN(9)",
                "UP(1-8)",
                "UP(9)"
        }));
        allList.add(new IndexValueStyleObj("NINE", "5_310", "", "1", "7", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("MA1", "5_280", "1", "", "5", "1", "300"));
        params3.add(new IndexValueObj("MA2", "5_280", "2", "", "10", "1", "300"));
        params3.add(new IndexValueObj("MA3", "5_280", "3", "", "20", "1", "300"));
        params3.add(new IndexValueObj("MA4", "5_280", "4", "", "60", "1", "300"));
        params3.add(new IndexValueObj("MA5", "5_280", "5", "", "120", "1", "300"));
        params3.add(new IndexValueObj("MA6", "5_280", "5", "", "250", "1", "300"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "MA1",
                "MA2",
                "MA3",
                "MA4",
                "MA5",
                "MA6",
        }));
        allList.add(new IndexValueStyleObj("MA", "5_350", "", "1", "1", params3, style3));


        /**幅图************************************************/

        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期", "5_295", "", "", "23", "1", "100"));
        params3.add(new IndexValueObj("移动平均周期", "5_296", "", "", "8", "1", "100"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "ADTM",
                "MA1"
        }));
        allList.add(new IndexValueStyleObj("ADTM", "5_340", "", "2", "6", params3, style3));


        params3 = new ArrayList<>();
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "B36",
                "B612"
        }));
        allList.add(new IndexValueStyleObj("B3612", "5_341", "", "2", "6", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期", "5_295", "", "", "9", "1", "100"));
        params3.add(new IndexValueObj("移动平均周期", "5_292", "1", "", "3", "2", "50"));
        params3.add(new IndexValueObj("移动平均周期", "5_292", "2", "", "3", "2", "50"));
        params3.add(new IndexValueObj("移动平均周期", "5_292", "3", "", "3", "1", "20"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "K", "D",
        }));
        allList.add(new IndexValueStyleObj("SLOWKD", "5_343", "", "2", "6", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期", "5_295", "", "", "16", "1", "100"));
        params3.add(new IndexValueObj("移动平均周期", "5_292", "1", "", "5", "1", "100"));
        params3.add(new IndexValueObj("移动平均周期", "5_292", "2", "", "76", "1", "100"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "DBCD",
                "MM"
        }));
        allList.add(new IndexValueStyleObj("DBCD", "5_343", "", "2", "6", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期", "5_295", "", "", "12", "1", "100"));
        params3.add(new IndexValueObj("移动平均周期", "5_296", "", "", "6", "1", "50"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "ROC",
                "ROCMA"
        }));
        allList.add(new IndexValueStyleObj("ROC", "5_345", "", "2", "6", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("移动平均周期", "5_296", "", "", "30", "1", "100"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "ASRDM",
                "SRDM"
        }));
        allList.add(new IndexValueStyleObj("DPO", "5_347", "", "2", "6", params3, style3));


        params3 = new ArrayList<>();
        params3.add(new IndexValueObj("计算周期", "5_295", "", "", "20", "2", "90"));
        params3.add(new IndexValueObj("移动平均周期", "5_296", "", "", "6", "2", "60"));
        style3 = new ArrayList<>();
        style3.addAll(getStyleList(new String[]{
                "DPO",
                "MADPO"
        }));
        allList.add(new IndexValueStyleObj("SRDM", "5_346", "", "2", "6", params3, style3));


        Gson gson = new Gson();

        String s = gson.toJson(allList);
        Log.d("Gson", "Gson:" + s);

    }


    private String[] colors = new String[]{"#C0001C",
            "#0087FF",
            "#7AC700",
            "#FF9C00",
            "#702F00",
            "#C400ED", "#C400ED",};

    public List<IndexStyleObj> getStyleList(String[] name) {
        List<IndexStyleObj> styles = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            styles.add(new IndexStyleObj(name[i], colors[i], "1", "1"));
        }

        return styles;
    }


}