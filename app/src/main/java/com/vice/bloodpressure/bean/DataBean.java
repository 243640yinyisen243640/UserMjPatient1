package com.vice.bloodpressure.bean;

/**
 * Created by qjx on 2018/5/10.
 */

public class DataBean {
    private int url;
    private String describe;
    private int imgs;
    private String name;

    public DataBean(int url) {
        this.url = url;
    }

    public DataBean(int url, String describe, String name) {
        this.url = url;
        this.describe = describe;
        this.name = name;
    }

    public int getImgs() {
        return imgs;
    }

    public void setImgs(int imgs) {
        this.imgs = imgs;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
