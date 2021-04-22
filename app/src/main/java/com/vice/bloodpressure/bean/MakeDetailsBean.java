package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/7/10.
 * 预约详情
 */

public class MakeDetailsBean {
    /**
     * 患者姓名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;
    /**
     * 住院类型 1：初次 2：再次
     */
    private String type;
    /**
     * 性别 1：男 2：女
     */
    private String sex;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 病情诊断描述
     */
    private String describe;
    /**
     * 住院目的
     */
    private String result;
    /**
     * 诊断图片
     */
    private String[] blpic;
    /**
     * 预约时间
     */
    private String time;
    /**
     * 结果反馈
     */
    private String reason;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String[] getBlpic() {
        return blpic;
    }

    public void setBlpic(String[] blpic) {
        this.blpic = blpic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
