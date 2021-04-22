package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * 描述:  医生实体类
 * 作者: LYD
 * 创建日期: 2019/9/21 14:02
 */
public class MyDoctorBean implements Serializable {
    /**
     * docid : 105
     * docname : 盛医生
     * imgurl : http://xydoc.xiyuns.cn/Public/upload/20190219/201902191648521550566132.jpg
     * doczhc : 专业医生
     * hospitalname : 首都医科大学附属北京世纪坛医院
     * depname : 糖尿病中心
     * isfamily : 1
     */

    private int docid;
    private String docname;
    private String imgurl;
    private String doczhc;
    private String hospitalname;
    private String depname;
    private int isfamily;
    private String specialty;
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDoczhc() {
        return doczhc;
    }

    public void setDoczhc(String doczhc) {
        this.doczhc = doczhc;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }
}
