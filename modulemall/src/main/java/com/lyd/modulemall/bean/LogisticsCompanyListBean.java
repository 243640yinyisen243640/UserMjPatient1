package com.lyd.modulemall.bean;

import java.util.List;

public class LogisticsCompanyListBean {
    private List<LogisticsBean> logistics;
    public List<LogisticsBean> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<LogisticsBean> logistics) {
        this.logistics = logistics;
    }

    public static class LogisticsBean {
        /**
         * type : SFEXPRESS
         * name : 顺丰快递
         */

        private String type;
        private String name;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
