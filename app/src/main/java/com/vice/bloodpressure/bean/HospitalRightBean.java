package com.vice.bloodpressure.bean;

public class HospitalRightBean {

    /**
     * docid : 101
     * docname : 盛医生
     * specialty : 糖尿病
     * imgurl : http://app.xiyuns.cn/Public/upload/20180711/201807112158351531317515.jpg
     * doczhc : 专科医生
     * contents : 阿斯蒂芬
     * hospitalname : 北京协和医院
     * depname : 慢病管理科
     */

    private int docid;
    private String docname;
    private String specialty;
    private String imgurl;
    private String doczhc;
    private String contents;
    private String hospitalname;
    private String depname;

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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    @Override
    public String toString() {
        return "HospitalRightBean{" +
                "docid=" + docid +
                ", docname='" + docname + '\'' +
                ", specialty='" + specialty + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", doczhc='" + doczhc + '\'' +
                ", contents='" + contents + '\'' +
                ", hospitalname='" + hospitalname + '\'' +
                ", depname='" + depname + '\'' +
                '}';
    }
}
