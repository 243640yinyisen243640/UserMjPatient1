package com.vice.bloodpressure.bean;

public class RongUserBean {
    //ID
    private String id;
    //名称
    private String name;
    //头像
    private String headImgUrl;


    public RongUserBean(String id, String name, String headImgUrl) {
        this.id = id;
        this.name = name;
        this.headImgUrl = headImgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
