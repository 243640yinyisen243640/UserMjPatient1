package com.vice.bloodpressure.bean;


public class MedicineStoreLevelOneBean {
    /**
     * id : 1
     * hosuserid : 0
     * classname : 降糖药
     * picurl : http://doctor.xiyuns.cn/Public/upload/20190327/201903271624521553675092.png
     * addtime : 1553675092
     * pid : 0
     */

    private int id;
    private int hosuserid;
    private String classname;
    private String picurl;
    private int addtime;
    private int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHosuserid() {
        return hosuserid;
    }

    public void setHosuserid(int hosuserid) {
        this.hosuserid = hosuserid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
