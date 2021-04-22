package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/6/28.
 * 血压报告实体类
 */

public class BPReportBean {
    /**
     * 测量总次数
     */
    private String count;
    /**
     * 每天平均次数
     */
    private String avg;
    /**
     * 收缩压最高值
     */
    private String maxsbp;
    /**
     * 舒张压最低值
     */
    private String mindbp;
    /**
     * 脉压差
     */
    private String diff;
    /**
     * 偏高主要集中时间段
     */
    private String highdate;
    /**
     * 偏低主要集中时间段
     */
    private String lowdate;
    /**
     * 收缩压测量次数
     */
    private String sbpcount;
    /**
     * 舒张压测量次数
     */
    private String dbpcount;
    /**
     * 收缩压平均值
     */
    private String sbpavg;
    /**
     * 舒张压平均值
     */
    private String dbpavg;
    /**
     * 测量主要集中时间段
     */
    private String surveydate;
    /**
     * 正常次数
     */
    private String normal;
    /**
     * 异常次数
     */
    private String excep;
    /**
     * 偏高次数
     */
    private String high;
    /**
     * 偏低次数
     */
    private String low;
    /**
     * 血压控制率
     */
    private String factor;
    /**
     * 控制率评级
     */
    private String rank;
    /**
     * 评级建议
     */
    private String rankcontent;
    /**
     * 偏高建议
     */
    private String highcontent;
    /**
     * 检测次数提醒，每月低于8次时显示
     */
    private String content;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getHighdate() {
        return highdate;
    }

    public void setHighdate(String highdate) {
        this.highdate = highdate;
    }

    public String getLowdate() {
        return lowdate;
    }

    public void setLowdate(String lowdate) {
        this.lowdate = lowdate;
    }

    public String getSbpcount() {
        return sbpcount;
    }

    public void setSbpcount(String sbpcount) {
        this.sbpcount = sbpcount;
    }

    public String getDbpcount() {
        return dbpcount;
    }

    public void setDbpcount(String dbpcount) {
        this.dbpcount = dbpcount;
    }

    public String getSbpavg() {
        return sbpavg;
    }

    public void setSbpavg(String sbpavg) {
        this.sbpavg = sbpavg;
    }

    public String getDbpavg() {
        return dbpavg;
    }

    public void setDbpavg(String dbpavg) {
        this.dbpavg = dbpavg;
    }

    public String getSurveydate() {
        return surveydate;
    }

    public void setSurveydate(String surveydate) {
        this.surveydate = surveydate;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getExcep() {
        return excep;
    }

    public void setExcep(String excep) {
        this.excep = excep;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankcontent() {
        return rankcontent;
    }

    public void setRankcontent(String rankcontent) {
        this.rankcontent = rankcontent;
    }

    public String getHighcontent() {
        return highcontent;
    }

    public void setHighcontent(String highcontent) {
        this.highcontent = highcontent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaxsbp() {
        return maxsbp;
    }

    public void setMaxsbp(String maxsbp) {
        this.maxsbp = maxsbp;
    }

    public String getMindbp() {
        return mindbp;
    }

    public void setMindbp(String mindbp) {
        this.mindbp = mindbp;
    }
}
