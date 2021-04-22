package com.vice.bloodpressure.bean;

import java.util.ArrayList;

/**
 * Created by haoyuxi on 18/7/16.
 */

public class EcgListBean {

    /**
     * code : 200
     * msg : 获取成功
     * data : [{"id":2,"heartrate":0,"datetime":"1970-01-01 08:00:00"},{"id":3,"heartrate":1,"datetime":"1973-07-10 08:11:51"}]
     */

    private int code;
    private String msg;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * heartrate : 0
         * datetime : 1970-01-01 08:00:00
         */

        private int id;
        private int heartrate;
        private String datetime;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHeartrate() {
            return heartrate;
        }

        public void setHeartrate(int heartrate) {
            this.heartrate = heartrate;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }
}
