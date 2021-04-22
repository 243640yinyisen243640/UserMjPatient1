package com.vice.bloodpressure.bean;

public class SystemMsgListBean {
    /**
     * pid : 250
     * title : 血糖记录提醒
     * notice : 您已经连续3天没有测量血糖了,保持良好监测习惯更能有效控制血糖。
     * type : 18
     * addtime : 昨天
     * isread : 2
     * sysmess : 1
     */

    private int pid;
    private String title;
    private String notice;
    private int type;
    private String addtime;
    private int isread;
    private int sysmess;
    //住院申请id
    private int inhosid;
    //随访id
    private int id;
    //降压 降糖 减重 中医体质Url
    //音视频Url
    private String url;
    //文章类型
    private int arttype;
    private String article_title;
    private String duration;
    //文章链接
    private String links;
    private int docid;
    private String starttime;
    private String endtime;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public int getSysmess() {
        return sysmess;
    }

    public void setSysmess(int sysmess) {
        this.sysmess = sysmess;
    }

    public int getInhosid() {
        return inhosid;
    }

    public void setInhosid(int inhosid) {
        this.inhosid = inhosid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getArttype() {
        return arttype;
    }

    public void setArttype(int arttype) {
        this.arttype = arttype;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
