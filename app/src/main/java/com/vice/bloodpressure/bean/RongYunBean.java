package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/7/2.
 */

public class RongYunBean {
    /**
     * 用户id
     */
    private String userid;
    /**
     * 融云token
     */
    private String token;
    /**
     * 用户名
     */
    private String username;
    /**
     * 绑定医生id
     */
    private String docid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
}
