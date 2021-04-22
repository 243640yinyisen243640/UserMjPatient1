package com.vice.bloodpressure.bean;

public class NoticeBean {

    /**
     * id : 10
     * affiche : 3333333333333333333333333333333333333
     * imgurl : null
     * content : 33333333333333333333333333333333333333333333
     * time : 2019-03-17 16:14
     */

    private int id;
    private String affiche;
    private String imgurl;
    private String content;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
