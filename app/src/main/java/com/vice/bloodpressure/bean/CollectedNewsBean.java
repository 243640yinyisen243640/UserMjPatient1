package com.vice.bloodpressure.bean;

/**
 * @ProjectName: NewBloodPressure
 * @Package: com.vice.bloodpressure.bean
 * @ClassName: CollectedNewsBean
 * @Description: java类作用描述
 * @Author: zwk
 * @CreateDate: 2019/10/25 11:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/25 11:33
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */

public class CollectedNewsBean {

    /**
     * actid : 3
     * title : 糖尿病自我监测，一不小心就出错
     * acturl : http://xydoc.xiyuns.cn/Public/upload/20180821/201808210929221534814962.jpg
     * bronum : 2169
     * time : 2018-08-20
     */

    private String actid;
    private String title;
    private String acturl;
    private String bronum;
    private String time;
    private String contenturl;

    public CollectedNewsBean() {
    }


    public String getActid() {
        return actid;
    }

    public void setActid(String actid) {
        this.actid = actid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActurl() {
        return acturl;
    }

    public void setActurl(String acturl) {
        this.acturl = acturl;
    }

    public String getBronum() {
        return bronum;
    }

    public void setBronum(String bronum) {
        this.bronum = bronum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContenturl() {
        return contenturl;
    }

    public void setContenturl(String contenturl) {
        this.contenturl = contenturl;
    }
}
