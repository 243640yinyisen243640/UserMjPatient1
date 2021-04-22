package com.vice.bloodpressure.bean;

public class NewsListBean {
    /**
     * 资讯文章id
     */
    private String actid;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章图片
     */
    private String acturl;
    /**
     * 浏览量
     */
    private String bronum;
    /**
     * 浏览量是否可见：1可见，2隐藏
     */
    private String islock;
    /**
     * 日期
     */
    private String time;
    /**
     * 症状
     *
     * @return
     */
    private String content;
    /**
     * 是否已收藏 1：已收藏、2：为收藏
     */
    private String iscollection;


    //查看更多---资讯分类
    /**
     * 资讯类id
     *
     * @return
     */
    private String id;
    /**
     * 分类名
     *
     * @return
     */
    private String catename;
    /**
     * 分类图片
     *
     * @return
     */
    private String cateurl;

    /**
     * 修改
     *
     * @return
     */
    private int picurl;
    private String articlebase;//简介
    //资讯地址
    private String contenturl;

    public String getArticlebase() {
        return articlebase;
    }

    public void setArticlebase(String articlebase) {
        this.articlebase = articlebase;
    }

    public String getActid() {
        return actid;
    }

    public void setActid(String actid) {
        this.actid = actid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActurl() {
        return acturl;
    }

    public void setActurl(String acturl) {
        this.acturl = acturl;
    }

    public String getBronum() {
        return bronum;
    }

    public void setBronum(String bronum) {
        this.bronum = bronum;
    }

    public String getIslock() {
        return islock;
    }

    public void setIslock(String islock) {
        this.islock = islock;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getCateurl() {
        return cateurl;
    }

    public void setCateurl(String cateurl) {
        this.cateurl = cateurl;
    }

    public String getIscollection() {
        return iscollection;
    }

    public void setIscollection(String iscollection) {
        this.iscollection = iscollection;
    }

    public int getPicurl() {
        return picurl;
    }

    public void setPicurl(int picurl) {
        this.picurl = picurl;
    }


    public String getContenturl() {
        return contenturl;
    }

    public void setContenturl(String contenturl) {
        this.contenturl = contenturl;
    }
}

