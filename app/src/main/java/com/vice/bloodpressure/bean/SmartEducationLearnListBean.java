package com.vice.bloodpressure.bean;

import java.util.List;

public class SmartEducationLearnListBean {

    /**
     * list : [{"id":14,"classname":"基础知识系列2","seriesintro":"基础知识系列2","seriesimg":"http://ceshi.xiyuns.cn/public/upload/20200819/2020081910254971142.jpg","learning":1,"unlearning":3,"classes":[{"id":2,"artname":"糖尿病患者引发低血糖的常见原因","type":1,"links":"","readtime":0,"introduc":"剂量过大、注入血管内胰岛素吸收过快","status":1},{"id":3,"artname":"改善糖尿病患者高密度脂蛋白胆固醇的方法","type":2,"links":"","readtime":0,"introduc":"均衡膳食：饮食中胆固醇摄入量低于300 mg/d","status":2}]},{"id":15,"classname":"基础知识系列2","seriesintro":"基础知识系列2","seriesimg":"http://ceshi.xiyuns.cn/public/upload/20200819/2020081910264078623.jpg","classes":[{"id":2,"artname":"糖尿病患者引发低血糖的常见原因","type":1,"links":"","readtime":0,"introduc":"剂量过大、注入血管内胰岛素吸收过快","status":1},{"id":3,"artname":"改善糖尿病患者高密度脂蛋白胆固醇的方法","type":2,"links":"","readtime":0,"introduc":"均衡膳食：饮食中胆固醇摄入量低于300 mg/d","status":2}]}]
     * statistical : {"learntime":10,"classnum":12}
     */

    private StatisticalBean statistical;
    private List<ListBean> list;

    public StatisticalBean getStatistical() {
        return statistical;
    }

    public void setStatistical(StatisticalBean statistical) {
        this.statistical = statistical;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class StatisticalBean {
        /**
         * learntime : 10
         * classnum : 12
         */

        private int learntime;
        private int classnum;

        public int getLearntime() {
            return learntime;
        }

        public void setLearntime(int learntime) {
            this.learntime = learntime;
        }

        public int getClassnum() {
            return classnum;
        }

        public void setClassnum(int classnum) {
            this.classnum = classnum;
        }
    }

    public static class ListBean {
        /**
         * id : 14
         * classname : 基础知识系列2
         * seriesintro : 基础知识系列2
         * seriesimg : http://ceshi.xiyuns.cn/public/upload/20200819/2020081910254971142.jpg
         * learning : 1
         * unlearning : 3
         * classes : [{"id":2,"artname":"糖尿病患者引发低血糖的常见原因","type":1,"links":"","readtime":0,"introduc":"剂量过大、注入血管内胰岛素吸收过快","status":1},{"id":3,"artname":"改善糖尿病患者高密度脂蛋白胆固醇的方法","type":2,"links":"","readtime":0,"introduc":"均衡膳食：饮食中胆固醇摄入量低于300 mg/d","status":2}]
         */

        private int id;
        private String classname;
        private String seriesintro;
        private String seriesimg;
        private int learning;
        private int unlearning;
        private List<ClassesBean> classes;

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
}
