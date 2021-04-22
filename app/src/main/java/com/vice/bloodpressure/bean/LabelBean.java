package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class LabelBean implements Serializable {
    /**
     * id : 1
     * remarkname : 血糖相关
     * keyword : 血糖
     */
    private int id;
    private String remarkname;
    private String keyword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemarkname() {
        return remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
