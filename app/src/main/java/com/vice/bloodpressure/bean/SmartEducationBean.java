package com.vice.bloodpressure.bean;

import java.util.List;

public class SmartEducationBean {
    /**
     * id : 19
     * classname : 血糖高系列1
     * seriesimg : http://ceshi.xiyuns.cn/public/upload/20200819/2020081910281011342.jpg
     * seriesintro : 血糖高系列1
     * artlist : [{"id":6,"cid":19,"artname":"糖尿病患者如何预防运动中低血糖","orderint":1},{"id":7,"cid":19,"artname":"二甲双胍的作用机制和不良反应","orderint":2}]
     */

    private int id;
    private String classname;
    private String seriesimg;
    private String seriesintro;
    private int learning;
    private int unlearning;
    private List<ArtlistBean> artlist;

    public int getLearning() {
        return learning;
    }

    public void setLearning(int learning) {
        this.learning = learning;
    }

    public int getUnlearning() {
        return unlearning;
    }

    public void setUnlearning(int unlearning) {
        this.unlearning = unlearning;
    }

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
         * id : 6
         * cid : 19
         * artname : 糖尿病患者如何预防运动中低血糖
         * orderint : 1
         */

        private int id;
        private int cid;
        private String artname;
        private int type;
        private String links;
        private int readtime;
        private String introduc;
        private int status;
        private String weblink;
        private String duration;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLinks() {
            return links;
        }

        public void setLinks(String links) {
            this.links = links;
        }

        public int getReadtime() {
            return readtime;
        }

        public void setReadtime(int readtime) {
            this.readtime = readtime;
        }

        public String getIntroduc() {
            return introduc;
        }

        public void setIntroduc(String introduc) {
            this.introduc = introduc;
        }

        public String getWeblink() {
            return weblink;
        }

        public void setWeblink(String weblink) {
            this.weblink = weblink;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int staus) {
            this.status = staus;
        }

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
