package com.zzt.zt_gson;

import java.io.Serializable;

/**
 * @author: zeting
 * @date: 2022/12/29
 * 指标参数
 */
public class IndexValueObj implements Serializable {
    private String name;
    private String appKey;
    private String appValue;
    private String value;
    private String defaultValue;
    private String hintMin;// app 空提示最小值
    private String hintMax;// app 空提示最大值

    public IndexValueObj(String name, String appKey, String appValue, String value, String defaultValue, String hintMin, String hintMax) {
        this.name = name;
        this.appKey = appKey;
        this.appValue = appValue;
        this.value = value;
        this.defaultValue = defaultValue;
        this.hintMin = hintMin;
        this.hintMax = hintMax;
    }


    public String getAppValue() {
        return appValue;
    }

    public void setAppValue(String appValue) {
        this.appValue = appValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getHintMin() {
        return hintMin;
    }

    public void setHintMin(String hintMin) {
        this.hintMin = hintMin;
    }

    public String getHintMax() {
        return hintMax;
    }

    public void setHintMax(String hintMax) {
        this.hintMax = hintMax;
    }
}
