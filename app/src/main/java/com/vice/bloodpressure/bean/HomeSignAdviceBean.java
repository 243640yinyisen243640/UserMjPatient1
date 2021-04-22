package com.vice.bloodpressure.bean;
/*
 * 类名:     HomeSignAdviceBean
 * 描述:     家签医生建议
 * 作者:     ZWK
 * 创建日期: 2020/1/13 9:47
 */

import androidx.annotation.NonNull;

public class HomeSignAdviceBean {

    /**
     * title : 220222
     * content : <p>222</p>
     * image : http://www.mydoc.com/Public/upload/20190601/201906011116491559359009.png
     * creattime : 2019-06-13 09:53
     * type : 1
     */

    private String title;
    private String content;
    private String image;
    private String creattime;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "HomeSignAdviceBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", creattime='" + creattime + '\'' +
                ", type=" + type +
                '}';
    }
}
