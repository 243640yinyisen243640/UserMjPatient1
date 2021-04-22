package com.vice.bloodpressure.bean;

import android.text.TextUtils;

public class SignPatientInfoBean {

    /**
     * nickname : 李亚东
     * idcard : null
     * tel : 13213071373
     * jbprov : null
     * jbcity : null
     * jbdist : null
     * address : null
     * nativeplace : null
     */

    private String nickname;
    private String idcard;
    private String tel;
    private String jbprov;
    private String jbcity;
    private String jbdist;
    private String address;
    private String nativeplace;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getJbprov() {
        return isEmpty(jbprov);
    }

    public void setJbprov(String jbprov) {
        this.jbprov = jbprov;
    }

    public String getJbcity() {
        return isEmpty(jbcity);
    }

    public void setJbcity(String jbcity) {
        this.jbcity = jbcity;
    }

    public String getJbdist() {
        return isEmpty(jbdist);
    }

    public void setJbdist(String jbdist) {
        this.jbdist = jbdist;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    private String isEmpty(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }
}
