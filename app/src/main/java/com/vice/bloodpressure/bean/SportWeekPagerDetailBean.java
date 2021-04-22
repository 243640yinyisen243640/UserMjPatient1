package com.vice.bloodpressure.bean;

import java.util.List;

public class SportWeekPagerDetailBean {


    /**
     * sportDays : 1
     * sportNum : 1
     * sportTime : 32
     * sportKcals : 230
     * stepsKcals : 0
     * totalKcal : 230
     * ableDay : 3
     * ableKcal : 648
     * weekTime : [230,0,0,0,0,0,0]
     * message : 您未能完成上周的运动计划，仍需再接再厉！运动锻炼是糖尿病患者自我管理的重要部分，有利于控制血糖、减轻体重、减少并发症的发生发展。
     */

    private int sportDays;
    private int sportNum;
    private int sportTime;
    private int sportKcals;
    private int stepsKcals;
    private int totalKcal;
    private int ableDay;
    private int ableKcal;
    private String message;
    private List<Integer> weekTime;

    public int getSportDays() {
        return sportDays;
    }

    public void setSportDays(int sportDays) {
        this.sportDays = sportDays;
    }

    public int getSportNum() {
        return sportNum;
    }

    public void setSportNum(int sportNum) {
        this.sportNum = sportNum;
    }

    public int getSportTime() {
        return sportTime;
    }

    public void setSportTime(int sportTime) {
        this.sportTime = sportTime;
    }

    public int getSportKcals() {
        return sportKcals;
    }

    public void setSportKcals(int sportKcals) {
        this.sportKcals = sportKcals;
    }

    public int getStepsKcals() {
        return stepsKcals;
    }

    public void setStepsKcals(int stepsKcals) {
        this.stepsKcals = stepsKcals;
    }

    public int getTotalKcal() {
        return totalKcal;
    }

    public void setTotalKcal(int totalKcal) {
        this.totalKcal = totalKcal;
    }

    public int getAbleDay() {
        return ableDay;
    }

    public void setAbleDay(int ableDay) {
        this.ableDay = ableDay;
    }

    public int getAbleKcal() {
        return ableKcal;
    }

    public void setAbleKcal(int ableKcal) {
        this.ableKcal = ableKcal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getWeekTime() {
        return weekTime;
    }

    public void setWeekTime(List<Integer> weekTime) {
        this.weekTime = weekTime;
    }
}
