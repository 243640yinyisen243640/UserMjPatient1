package com.vice.bloodpressure.bean;

import java.util.List;

public class SmartEducationClassifyLeftBean {
    /**
     * id : 1
     * classname : 1型
     * childs : [{"id":22,"classname":"基础知识1型"}]
     */

    private int id;
    private String classname;
    private List<ChildsBean> childs;

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

    public List<ChildsBean> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildsBean> childs) {
        this.childs = childs;
    }

    public static class ChildsBean {
        /**
         * id : 22
         * classname : 基础知识1型
         */

        private int id;
        private String classname;

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
    }
}
