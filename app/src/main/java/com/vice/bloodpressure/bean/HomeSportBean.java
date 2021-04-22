package com.vice.bloodpressure.bean;

import java.util.List;

public class HomeSportBean {
    /**
     * issport : 1
     * weight : 60
     * activity : 1
     * message : 您目前的运动现状分析如下：体重正常、运动时长不足、空腹运动。
     * 建议：每天低等强度运动30~60分钟；每周运动3-5天；运动时对应心率范围85~102次/分。
     * kcals : 216
     * totalKcal : 0
     * stepKcal : 0
     * sportType : 游泳
     * sportArr : [["1","步行","0.06"]]
     * sportTime : 01:00:00
     * remainingKcal : 216
     * progress : 0
     */

    private int issport;
    private int weight;
    private int activity;
    private String message;
    private int kcals;
    private int totalKcal;
    private int stepKcal;
    private String sportType;
    private String sportTime;
    private int remainingKcal;
    private int progress;
    private List<List<String>> sportArr;

    public int getIssport() {
        return issport;
    }

    public void setIssport(int issport) {
        this.issport = issport;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getKcals() {
        return kcals;
    }

    public void setKcals(int kcals) {
        this.kcals = kcals;
    }

    public int getTotalKcal() {
        return totalKcal;
    }

    public void setTotalKcal(int totalKcal) {
        this.totalKcal = totalKcal;
    }

    public int getStepKcal() {
        return stepKcal;
    }

    public void setStepKcal(int stepKcal) {
        this.stepKcal = stepKcal;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getSportTime() {
        return sportTime;
    }

    public void setSportTime(String sportTime) {
        this.sportTime = sportTime;
    }

    public int getRemainingKcal() {
        return remainingKcal;
    }

    public void setRemainingKcal(int remainingKcal) {
        this.remainingKcal = remainingKcal;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public List<List<String>> getSportArr() {
        return sportArr;
    }

    public void setSportArr(List<List<String>> sportArr) {
        this.sportArr = sportArr;
    }
}
