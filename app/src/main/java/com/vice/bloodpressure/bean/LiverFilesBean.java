package com.vice.bloodpressure.bean;

import android.text.TextUtils;

public class LiverFilesBean {
    /**
     * userid : 643878
     * physical : {"height":"200","weight":"60","waistline":"23","hipline":"32","tizhi":"","stun":"","syao":"","woli":""}
     * laboratory : {"alts":"","bai":"","xuet":"","xueh":"","zong":"","qian":"","ning":"","xuea":""}
     * livershis : {"textarea0":"12`342","textarea1":"`213254","textarea2":"`156"}
     * diagnosis : 11
     * medchinehis : 11
     * addtime : 1585705666
     * edittime : 1585705666
     * syalt :
     * sytbil :
     */
    private int userid;
    private String height;
    private String weight;
    private String waistline;
    private String hipline;
    private String tizhi;
    private String stun;
    private String syao;
    private String woli;
    private String alts;
    private String bai;
    private String xuet;
    private String xueh;
    private String zong;
    private String qian;
    private String ning;
    private String xuea;
    private String textarea0;
    private String textarea1;
    private String textarea2;
    private String nickname;
    private String sex;
    private String minzu;
    private String culture;
    private String drink;
    private String birthtime;
    private String profession;
    private String nativeplace;
    private String diagnosis;
    private String medchinehis;
    private int addtime;
    private int edittime;
    private String syalt;
    private String sytbil;


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHipline() {
        return hipline;
    }

    public void setHipline(String hipline) {
        this.hipline = hipline;
    }

    public String getTizhi() {
        return tizhi;
    }

    public void setTizhi(String tizhi) {
        this.tizhi = tizhi;
    }

    public String getStun() {
        return stun;
    }

    public void setStun(String stun) {
        this.stun = stun;
    }

    public String getSyao() {
        return syao;
    }

    public void setSyao(String syao) {
        this.syao = syao;
    }

    public String getWoli() {
        return woli;
    }

    public void setWoli(String woli) {
        this.woli = woli;
    }

    public String getAlts() {
        return alts;
    }

    public void setAlts(String alts) {
        this.alts = alts;
    }

    public String getBai() {
        return bai;
    }

    public void setBai(String bai) {
        this.bai = bai;
    }

    public String getXuet() {
        return xuet;
    }

    public void setXuet(String xuet) {
        this.xuet = xuet;
    }

    public String getXueh() {
        return xueh;
    }

    public void setXueh(String xueh) {
        this.xueh = xueh;
    }

    public String getZong() {
        return zong;
    }

    public void setZong(String zong) {
        this.zong = zong;
    }

    public String getQian() {
        return qian;
    }

    public void setQian(String qian) {
        this.qian = qian;
    }

    public String getNing() {
        return ning;
    }

    public void setNing(String ning) {
        this.ning = ning;
    }

    public String getXuea() {
        return xuea;
    }

    public void setXuea(String xuea) {
        this.xuea = xuea;
    }

    public String getTextarea0() {
        return textarea0;
    }

    public void setTextarea0(String textarea0) {
        this.textarea0 = textarea0;
    }

    public String getTextarea1() {
        return textarea1;
    }

    public void setTextarea1(String textarea1) {
        this.textarea1 = textarea1;
    }

    public String getTextarea2() {
        return textarea2;
    }

    public void setTextarea2(String textarea2) {
        this.textarea2 = textarea2;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        if ("1".equals(sex)) {
            return "男";
        } else if ("2".equals(sex)) {
            return "女";
        }
        return "";
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public String getCulture() {
        if ("1".equals(culture)) {
            return "小学及以下";
        } else if ("2".equals(culture)) {
            return "初中";
        } else if ("3".equals(culture)) {
            return "高中";
        } else if ("4".equals(culture)) {
            return "大学及以上";
        }
        return "";
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getDrink() {
        if ("1".equals(drink)) {
            return "是";
        } else if ("2".equals(drink)) {
            return "否";
        }
        return "";
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public String getProfession() {
        if ("1".equals(profession)) {
            return "轻体力";
        } else if ("2".equals(profession)) {
            return "中体力";
        } else if ("3".equals(profession)) {
            return "重体力";
        }
        return "";
    }


    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedchinehis() {
        return medchinehis;
    }

    public void setMedchinehis(String medchinehis) {
        this.medchinehis = medchinehis;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public int getEdittime() {
        return edittime;
    }

    public void setEdittime(int edittime) {
        this.edittime = edittime;
    }

    public String getSyalt() {
        return syalt;
    }

    public void setSyalt(String syalt) {
        this.syalt = syalt;
    }

    public String getSytbil() {
        return sytbil;
    }

    public void setSytbil(String sytbil) {
        this.sytbil = sytbil;
    }


    /**
     * 是否是空
     *
     * @param str
     * @return
     */
    private String isNull(String str) {
        return TextUtils.isEmpty(str) ? "请输入" : str;
    }

}
