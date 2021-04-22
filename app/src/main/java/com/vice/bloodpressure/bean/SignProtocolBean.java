package com.vice.bloodpressure.bean;
/*
 * 类名:     SignProtocolBean
 * 描述:     签约协议
 * 作者:     ZWK
 * 创建日期: 2020/1/9 11:46
 */

public class SignProtocolBean {

    /**
     * user_sign : http://ceshi.xiyuns.cn/public/uploads/20200108/119816adf35e6eb909bc657efb804ffb.jpg
     * doc_sign :
     * group_photo :
     * addtime : 2020-01-08
     */

    private String user_sign;
    private String doc_sign;
    private String group_photo;
    private String addtime;

    public String getUser_sign() {
        return user_sign;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }

    public String getDoc_sign() {
        return doc_sign;
    }

    public void setDoc_sign(String doc_sign) {
        this.doc_sign = doc_sign;
    }

    public String getGroup_photo() {
        return group_photo;
    }

    public void setGroup_photo(String group_photo) {
        this.group_photo = group_photo;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
