package com.vice.bloodpressure.bean;

public class AdviceBean {
    /**
     * content : 多吃肉，多喝牛奶，会挂的更快
     * type : 1
     * time : 2019-06-15 17:20
     * info : {"systolic":199,"diastole":100}
     * inid : 1986
     */
    private String title;
    private String content;
    private String image;
    private String type;
    private String creattime;
    private InfoBean info;


    //private int inid;


    public boolean isOperate() {
        return "1".equals(type);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //    public int getInid() {
    //        return inid;
    //    }
    //
    //    public void setInid(int inid) {
    //        this.inid = inid;
    //    }

    public static class InfoBean {
        /**
         * systolic : 199
         * diastole : 100
         */

        private int systolic;
        private int diastole;

        public int getSystolic() {
            return systolic;
        }

        public void setSystolic(int systolic) {
            this.systolic = systolic;
        }

        public int getDiastole() {
            return diastole;
        }

        public void setDiastole(int diastole) {
            this.diastole = diastole;
        }
    }
}
