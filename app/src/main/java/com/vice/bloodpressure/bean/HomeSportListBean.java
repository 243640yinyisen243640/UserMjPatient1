package com.vice.bloodpressure.bean;

import java.io.Serializable;
import java.util.List;

public class HomeSportListBean implements Serializable {
    /**
     * time : 20170730
     * sports : [{"sportType":"步行","minutes":50,"kcals":33}]
     */
    private String time;
    private String totalkcal;
    private String message;
    private String result;
    private List<SportsBean> sports;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalkcal() {
        return totalkcal;
    }

    public void setTotalkcal(String totalkcal) {
        this.totalkcal = totalkcal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<SportsBean> getSports() {
        return sports;
    }

    public void setSports(List<SportsBean> sports) {
        this.sports = sports;
    }

    public static class SportsBean implements Serializable {
        /**
         * sportType : 步行
         * minutes : 50
         * kcals : 33
         */

        private String sportType;
        private String minutes;
        private int kcals;

        public String getSportType() {
            return sportType;
        }

        public void setSportType(String sportType) {
            this.sportType = sportType;
        }

        public String getMinutes() {
            return minutes;
        }

        public void setMinutes(String minutes) {
            this.minutes = minutes;
        }

        public int getKcals() {
            return kcals;
        }

        public void setKcals(int kcals) {
            this.kcals = kcals;
        }
    }
}
