package com.zzt.zt_gson;

import java.io.Serializable;

/**
 * @author: zeting
 * @date: 2022/12/29
 * 指标样式
 */
public class IndexStyleObj implements Serializable {
    private String name;//
    private String appKey;//
    private String color;//
    private String enable;//
    private String width;//

    public IndexStyleObj(  String appKey, String color, String enable, String width) {
        this.name = appKey;
        this.appKey = appKey;
        this.color = color;
        this.enable = enable;
        this.width = width;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
