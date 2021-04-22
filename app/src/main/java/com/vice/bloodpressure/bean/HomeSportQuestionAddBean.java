package com.vice.bloodpressure.bean;

import java.io.Serializable;
import java.util.List;

public class HomeSportQuestionAddBean implements Serializable {
    //private String issport;
    private String age;
    private String height;
    private String weight;
    //private String activity;
    //private String heartrate;
    //合并并发症 0无 1冠心病 2高血压 3合并神经病变 4合并视网膜病变 5合并肾病 多个用英文逗号拼接 例如1,2
    private List<String> complications;
    //    private String habit;
    //    private String empty;
    //    private String sportTime;
    //    private String sportFrequency;

    //    public String getIssport() {
    //        return issport;
    //    }
    //
    //    public void setIssport(String issport) {
    //        this.issport = issport;
    //    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    //    public String getActivity() {
    //        return activity;
    //    }
    //
    //    public void setActivity(String activity) {
    //        this.activity = activity;
    //    }
    //
    //    public String getHeartrate() {
    //        return heartrate;
    //    }
    //
    //    public void setHeartrate(String heartrate) {
    //        this.heartrate = heartrate;
    //    }


    public List<String> getComplications() {
        return complications;
    }

    public void setComplications(List<String> complications) {
        this.complications = complications;
    }

    //    public String getHabit() {
    //        return habit;
    //    }
    //
    //    public void setHabit(String habit) {
    //        this.habit = habit;
    //    }
    //
    //    public String getEmpty() {
    //        return empty;
    //    }
    //
    //    public void setEmpty(String empty) {
    //        this.empty = empty;
    //    }
    //
    //    public String getSportTime() {
    //        return sportTime;
    //    }
    //
    //    public void setSportTime(String sportTime) {
    //        this.sportTime = sportTime;
    //    }
    //
    //    public String getSportFrequency() {
    //        return sportFrequency;
    //    }
    //
    //    public void setSportFrequency(String sportFrequency) {
    //        this.sportFrequency = sportFrequency;
    //    }
}
