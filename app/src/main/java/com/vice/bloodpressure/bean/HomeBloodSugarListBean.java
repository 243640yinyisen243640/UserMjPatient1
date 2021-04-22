package com.vice.bloodpressure.bean;

import java.util.List;

public class HomeBloodSugarListBean {


        private List<InfoBean> info;

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * lch : ["6.0",3]
             * zcq : ["6.0",3]
             * zch : ["6.0",3]
             * wcq : ["6.0",3]
             * wch : ["6.0",3]
             * wfq : ["6.0",3]
             * wfh : ["6.0",3]
             * shq : ["6.0",3]
             * lchmore : 0
             * zcqmore : 0
             * zchmore : 0
             * wcqmore : 0
             * wchmore : 0
             * wfqmore : 0
             * wfhmore : 0
             * shqmore : 0
             * time : 2020-09-23
             */

            private int lchmore;
            private int zcqmore;
            private int zchmore;
            private int wcqmore;
            private int wchmore;
            private int wfqmore;
            private int wfhmore;
            private int shqmore;
            private String time;
            private List<String> lch;
            private List<String> zcq;
            private List<String> zch;
            private List<String> wcq;
            private List<String> wch;
            private List<String> wfq;
            private List<String> wfh;
            private List<String> shq;

            public int getLchmore() {
                return lchmore;
            }

            public void setLchmore(int lchmore) {
                this.lchmore = lchmore;
            }

            public int getZcqmore() {
                return zcqmore;
            }

            public void setZcqmore(int zcqmore) {
                this.zcqmore = zcqmore;
            }

            public int getZchmore() {
                return zchmore;
            }

            public void setZchmore(int zchmore) {
                this.zchmore = zchmore;
            }

            public int getWcqmore() {
                return wcqmore;
            }

            public void setWcqmore(int wcqmore) {
                this.wcqmore = wcqmore;
            }

            public int getWchmore() {
                return wchmore;
            }

            public void setWchmore(int wchmore) {
                this.wchmore = wchmore;
            }

            public int getWfqmore() {
                return wfqmore;
            }

            public void setWfqmore(int wfqmore) {
                this.wfqmore = wfqmore;
            }

            public int getWfhmore() {
                return wfhmore;
            }

            public void setWfhmore(int wfhmore) {
                this.wfhmore = wfhmore;
            }

            public int getShqmore() {
                return shqmore;
            }

            public void setShqmore(int shqmore) {
                this.shqmore = shqmore;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public List<String> getLch() {
                return lch;
            }

            public void setLch(List<String> lch) {
                this.lch = lch;
            }

            public List<String> getZcq() {
                return zcq;
            }

            public void setZcq(List<String> zcq) {
                this.zcq = zcq;
            }

            public List<String> getZch() {
                return zch;
            }

            public void setZch(List<String> zch) {
                this.zch = zch;
            }

            public List<String> getWcq() {
                return wcq;
            }

            public void setWcq(List<String> wcq) {
                this.wcq = wcq;
            }

            public List<String> getWch() {
                return wch;
            }

            public void setWch(List<String> wch) {
                this.wch = wch;
            }

            public List<String> getWfq() {
                return wfq;
            }

            public void setWfq(List<String> wfq) {
                this.wfq = wfq;
            }

            public List<String> getWfh() {
                return wfh;
            }

            public void setWfh(List<String> wfh) {
                this.wfh = wfh;
            }

            public List<String> getShq() {
                return shq;
            }

            public void setShq(List<String> shq) {
                this.shq = shq;
            }
        }
}
