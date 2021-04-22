package com.vice.bloodpressure.bean;
/*
 * 类名:     FamilyMemberBean
 * 描述:     家庭成员
 * 作者:     ZWK
 * 创建日期: 2020/1/9 14:09
 */

public class FamilyMemberBean {
    /**
     * id : 10
     * relation : 1
     * idcard : 410521199101155033
     * tel : 17698302799
     * userid : 643599
     * nickname : 李林峰
     */

    private int id;
    private int relation;
    private String idcard;
    private String tel;
    private int userid;
    private String nickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
