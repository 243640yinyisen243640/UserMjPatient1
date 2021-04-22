package com.vice.bloodpressure.bean;

import java.util.List;

public class SmartEducationHaveLearnClassListBean {
    /**
     * id : 14
     * classname : 基础知识系列2
     * seriesintro : 基础知识系列2
     * seriesimg : http://ceshi.xiyuns.cn/public/upload/20200819/2020081910254971142.jpg
     * classes : [{"id":2,"artname":"糖尿病患者引发低血糖的常见原因"},{"id":3,"artname":"改善糖尿病患者高密度脂蛋白胆固醇的方法"}]
     */
    private int id;
    private String classname;
    private String seriesintro;
    private String seriesimg;
    private int learning;
    private List<ClassesBean> classes;

    public int getLearning() {
        return learning;
    }

    public void setLearning(int learning) {
        this.learning = learning;
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

    public String getSeriesintro() {
        return seriesintro;
    }

    public void setSeriesintro(String seriesintro) {
        this.seriesintro = seriesintro;
    }

    public String getSeriesimg() {
        return seriesimg;
    }

    public void setSeriesimg(String seriesimg) {
        this.seriesimg = seriesimg;
    }

    public List<ClassesBean> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesBean> classes) {
        this.classes = classes;
    }

    public static class ClassesBean {
        /**
         * id : 2
         * artname : 糖尿病患者引发低血糖的常见原因
         * type : 1
         * links :
         * readtime : 0
         * introduc : 剂量过大、注入血管内胰岛素吸收过快
         * status : 1
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getArtname() {
            return artname;
        }

        public void setArtname(String artname) {
            this.artname = artname;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
    }
}
