package com.vice.bloodpressure.bean;

public class SportTypeBean {
    /**
     * id : 1
     * sportname : 步行去工作/上学
     * imgurl : http://xydoc.xiyuns.cn/Public/upload/20181127/201811271812111543313531.jpg
     * remark : 一分钟50步
     * ishidden : 1
     */

    private int id;
    private String sportname;
    private String imgurl;
    private String remark;
    private String ishidden;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSportname() {
        return sportname;
    }

    public void setSportname(String sportname) {
        this.sportname = sportname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIshidden() {
        return ishidden;
    }

    public void setIshidden(String ishidden) {
        this.ishidden = ishidden;
    }
}
