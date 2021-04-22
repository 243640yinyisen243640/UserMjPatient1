package com.vice.bloodpressure.bean;

/**
 * 描述:  测试答题Bean
 * 作者: LYD
 * 创建日期: 2019/11/26 15:23
 */

public class TestBean {
    /**
     * status : plan
     * info : {"id":1,"number":1,"title":"是否用药","op_a":"已用药","op_b":"未用药"}
     */

    private String status;
    private InfoBean info;
    private String id;
    private String prlid;
    private String treatment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getPrlid() {
        return prlid;
    }

    public void setPrlid(String prlid) {
        this.prlid = prlid;
    }

    public static class InfoBean {
        /**
         * id : 1
         * number : 1
         * title : 是否用药
         * op_a : 已用药
         * op_b : 未用药
         */

        private int id;
        private int number;
        private String title;
        private String op_a;
        private String op_b;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOp_a() {
            return op_a;
        }

        public void setOp_a(String op_a) {
            this.op_a = op_a;
        }

        public String getOp_b() {
            return op_b;
        }

        public void setOp_b(String op_b) {
            this.op_b = op_b;
        }
    }
}
