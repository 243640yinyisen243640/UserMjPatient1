package com.vice.bloodpressure.bean.im;

/**
 * 描述:  消息内容
 * 作者: LYD
 * 创建日期: 2019/6/11 15:43
 */
public class ImWarningMessageContentBean {
    /**
     * wid : 942
     * val : 11.7
     * unit : mmol/L
     * title : 血糖
     * style : color:red
     * result : 未处理
     * cname : 点击处理
     * recolor : red
     * type : 2
     * status : 偏高
     * wtime : 2019-06-08 17:59:55
     * typename : 晚餐后
     */
    private int wid;
    private String val;
    private String unit;
    private String title;
    private String style;
    private String result;
    private String cname;
    private String recolor;
    private int type;
    private String status;
    private String wtime;
    private String typename;

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getRecolor() {
        return recolor;
    }

    public void setRecolor(String recolor) {
        this.recolor = recolor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWtime() {
        return wtime;
    }

    public void setWtime(String wtime) {
        this.wtime = wtime;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
