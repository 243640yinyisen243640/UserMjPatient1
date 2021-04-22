package com.vice.bloodpressure.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yicheng on 2018/6/20.
 * <p>
 * 检查记录实体类
 */

public class ExamineBean implements Serializable {

    /**
     * id : 26
     * uid : 110
     * hydname :
     * picurl : ["http://ceshi.xiyuns.cn/public/uploads/20190301/f26f75b985fe79b383b82d3921f9364e.jpg","http://ceshi.xiyuns.cn/public/uploads/20190301/df747aac200dfc9dc7fdd37c6d362032.jpg","http://ceshi.xiyuns.cn/public/uploads/20190301/b03e261c02db4bc91bf1002fe22e1c92.jpg"]
     * datetime : 2019-03-01 14:16
     * remark : null
     */

    private int id;
    private int uid;
    private String hydname;
    private String datetime;
    private Object remark;
    private List<String> picurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHydname() {
        return hydname;
    }

    public void setHydname(String hydname) {
        this.hydname = hydname;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public List<String> getPicurl() {
        return picurl;
    }

    public void setPicurl(List<String> picurl) {
        this.picurl = picurl;
    }
}
