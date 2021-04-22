package com.vice.bloodpressure.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class PatientOfTreatListBean {


    /**
     * patients : [{"id":24,"userid":302,"username":"Lin","tel":"18697320800","idcard":"410185199203151017","birthtime":700588800,"age":27,"sex":1,"default":2,"ishidden":2,"addtime":1572423198,"edittime":1572423198},{"id":25,"userid":302,"username":"111","tel":"15617873768","idcard":"513436200010261608","birthtime":972489600,"age":19,"sex":2,"default":1,"ishidden":2,"addtime":1572071931,"edittime":1572071941}]
     * hasdefult : 1
     */

    private int hasdefult;
    private List<PatientsBean> patients;

    public int getHasdefult() {
        return hasdefult;
    }

    public void setHasdefult(int hasdefult) {
        this.hasdefult = hasdefult;
    }

    public List<PatientsBean> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientsBean> patients) {
        this.patients = patients;
    }

    public static class PatientsBean extends SelectedBean {
        /**
         * id : 24
         * userid : 302
         * username : Lin
         * tel : 18697320800
         * idcard : 410185199203151017
         * birthtime : 700588800
         * age : 27
         * sex : 1
         * default : 2
         * ishidden : 2
         * addtime : 1572423198
         * edittime : 1572423198
         */

        private int id;
        private int userid;
        private String username;
        private String tel;
        private String idcard;
        private int birthtime;
        private int age;
        private int sex;
        @JSONField(name = "default")
        private int defaultX;
        private int ishidden;
        private int addtime;
        private int edittime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public int getBirthtime() {
            return birthtime;
        }

        public void setBirthtime(int birthtime) {
            this.birthtime = birthtime;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public int getIshidden() {
            return ishidden;
        }

        public void setIshidden(int ishidden) {
            this.ishidden = ishidden;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getEdittime() {
            return edittime;
        }

        public void setEdittime(int edittime) {
            this.edittime = edittime;
        }
    }
}
