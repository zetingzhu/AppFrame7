package com.zzt.zt_gson;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zeting
 * @date: 2022/12/29
 * 指标参数信息
 */
public class IndexValueStyleObj implements Serializable {
    private String name;
    private String appKey;
    private String appValue;
    private String masterType;//1主图2副图
    private String targetType;//1趋势指标  2摆动指标  3能量指标  4压力支撑 5超买超卖指标 6反趋势指标 7 其他 8 量价
    private List<IndexValueObj> params;
    private List<IndexStyleObj> styles;

    public IndexValueStyleObj(String name, String appKey, String appValue, String masterType, String targetType, List<IndexValueObj> params, List<IndexStyleObj> styles) {
        this.name = name;
        this.appKey = appKey;
        this.appValue = appValue;
        this.masterType = masterType;
        this.targetType = targetType;
        this.params = params;
        this.styles = styles;
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

    public String getAppValue() {
        return appValue;
    }

    public void setAppValue(String appValue) {
        this.appValue = appValue;
    }

    public String getMasterType() {
        return masterType;
    }

    public void setMasterType(String masterType) {
        this.masterType = masterType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public List<IndexValueObj> getParams() {
        return params;
    }

    public void setParams(List<IndexValueObj> params) {
        this.params = params;
    }

    public List<IndexStyleObj> getStyles() {
        return styles;
    }

    public void setStyles(List<IndexStyleObj> styles) {
        this.styles = styles;
    }
}
