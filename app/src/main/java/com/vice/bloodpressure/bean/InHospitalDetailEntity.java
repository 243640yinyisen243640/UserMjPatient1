package com.vice.bloodpressure.bean;
/*
 * 类名:     InHospitalDetailEntity
 * 描述:     预约住院详情
 * 作者:     ZWK
 * 创建日期: 2020/1/10 17:01
 */

import java.util.List;

public class InHospitalDetailEntity {

    /**
     * id : 1
     * name : 李林峰
     * type : 1
     * age : 20
     * describe : 描述
     * sex : 1
     * telephone : 17698302799
     * result :
     * pic : ["http://ceshi.xiyuns.cn/public/uploads/20191121/f4e57ee1b968fdf3871c65cf0afd1ac0.png","http://ceshi.xiyuns.cn/public/uploads/20191121/02e30b0af9feaf58381dd7aa0187b436.png","http://ceshi.xiyuns.cn/public/uploads/20191121/ad1d1af94b5da069f5804eb0dc02d7e1.png"]
     * intime : 2020-02-12 00:00
     */

    private int id;
    private String name;
    private String type;
    private String age;
    private String describe;
    private String sex;
    private String telephone;
    private String result;
    private String reason;
    private String status;
    private String intime;
    private List<String> pic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}
