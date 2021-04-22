package com.lyd.baselib.bean;

import java.io.Serializable;

/**
 * Created by qjx on 2018/5/21.
 */

public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token : 725cf54a93c63efed7ade57daa0ee2c0
     * userid : 643660
     * username : 13213071373
     * picture : http://ceshi.xiyuns.cn/public/uploads/20191224/f9dd44b4f33fe34e031b4f003947e8e7.jpg
     * is_one : 2
     * imei :
     * idcard :
     * docid : 643717
     * signid : 0
     * is_allow : 0
     * hosname : 熙云科技智慧医疗
     * usercode : e42dd289de888b4eda2d968dd8fbf8ea
     * password : 2
     */

    private String token;
    private String userid;
    private String username;
    private String picture;
    private String is_one;
    private String imei;
    private String idcard;
    private int docid;
    //签约ID
    private String signid;
    private int is_allow;
    private String hosname;
    private String usercode;
    private String password;
    private String nickname;
    //1是0不是
    private String is_youan;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIs_one() {
        return is_one;
    }

    public void setIs_one(String is_one) {
        this.is_one = is_one;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public String getSignid() {
        return signid;
    }

    public void setSignid(String signid) {
        this.signid = signid;
    }

    public int getIs_allow() {
        return is_allow;
    }

    public void setIs_allow(int is_allow) {
        this.is_allow = is_allow;
    }

    public String getHosname() {
        return hosname;
    }

    public void setHosname(String hosname) {
        this.hosname = hosname;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIs_youan() {
        return is_youan;
    }

    public void setIs_youan(String is_youan) {
        this.is_youan = is_youan;
    }
}
