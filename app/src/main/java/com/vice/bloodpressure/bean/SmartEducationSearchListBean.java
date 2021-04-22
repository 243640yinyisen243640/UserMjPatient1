package com.vice.bloodpressure.bean;

import java.util.List;

public class SmartEducationSearchListBean {
    /**
     * id : 18
     * classname : 血糖高系列1
     * seriesimg : http://ceshi.xiyuns.cn/public/upload/20200819/2020081910280017011.jpg
     * seriesintro : 血糖高系列1
     * artlist : [{"id":4,"cid":18,"artname":"糖尿病患者每年要做的11项监测项目"},{"id":5,"cid":18,"artname":"糖尿病合并高血压患者的运动"}]
     */

    private int id;
    private String classname;
    private String seriesimg;
    private String seriesintro;
    private List<ArtlistBean> artlist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSeriesimg() {
        return seriesimg;
    }

    public void setSeriesimg(String seriesimg) {
        this.seriesimg = seriesimg;
    }

    public String getSeriesintro() {
        return seriesintro;
    }

    public void setSeriesintro(String seriesintro) {
        this.seriesintro = seriesintro;
    }

    public List<ArtlistBean> getArtlist() {
        return artlist;
    }

    public void setArtlist(List<ArtlistBean> artlist) {
        this.artlist = artlist;
    }

    public static class ArtlistBean {
        /**
         * id : 4
         * cid : 18
         * artname : 糖尿病患者每年要做的11项监测项目
         */

        private int id;
        private int cid;
        private String artname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getArtname() {
            return artname;
        }

        public void setArtname(String artname) {
            this.artname = artname;
        }
    }
}
