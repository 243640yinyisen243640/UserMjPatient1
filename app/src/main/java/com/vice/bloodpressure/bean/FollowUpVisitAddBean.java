package com.vice.bloodpressure.bean;

import java.util.List;

public class FollowUpVisitAddBean {
    /**
     * id : 54
     * data : {"height":"153","weight":"60","systolic":"12","diastolic":"10","drug":"12312","smok":"12","drink":"30","psychological":1,"symptom":"1,2","bmi":"10","pulsation":1,"else":"其他","behavior":1,"fastingbloodsugar":"20","hemoglobin":"30","reaction":0,"followstyle":0,"medicdetail":[["名称一",2,150],["名称2",2,150],["名称3",2,150]],"insulin":"32","insulinnum":"342","heartrate":23,"saltrelated":23,"sportnum":1,"sporttime":32,"mainfood":"大米","compliance":0,"drugreactions":0,"questionstr":["1","2","3"],"status":1}
     */

    //id
    private String id;
    //药品
    private List<String> medicdetail;
    //血糖
    private List<String> sugars;
    //内层
    private DataBean data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<String> getMedicdetail() {
        return medicdetail;
    }

    public void setMedicdetail(List<String> medicdetail) {
        this.medicdetail = medicdetail;
    }

    public List<String> getSugars() {
        return sugars;
    }

    public void setSugars(List<String> sugars) {
        this.sugars = sugars;
    }

    public static class DataBean {
        /**
         * height : 153
         * weight : 60
         * systolic : 12
         * diastolic : 10
         * drug : 12312
         * smok : 12
         * drink : 30
         * psychological : 1
         * symptom : 1,2
         * bmi : 10
         * pulsation : 1
         * else : 其他
         * behavior : 1
         * fastingbloodsugar : 20
         * hemoglobin : 30
         * reaction : 0
         * followstyle : 0
         * medicdetail : [["名称一",2,150],["名称2",2,150],["名称3",2,150]]
         * insulin : 32
         * insulinnum : 342
         * heartrate : 23
         * saltrelated : 23
         * sportnum : 1
         * sporttime : 32
         * mainfood : 大米
         * compliance : 0
         * drugreactions : 0
         * questionstr : ["1","2","3"]
         * status : 1
         */

        private String height;
        private String weight;
        private String systolic;
        private String diastolic;
        private String drug;
        private String smok;
        private String drink;
        private int psychological;
        private String symptom;
        private String bmi;
        private int pulsation;
        private String other;
        private int behavior;
        private String fastingbloodsugar;
        private String hemoglobin;
        private String examinetime;
        private int reaction;
        private int followstyle;
        //private List<List<String>> medicdetail;
        private String insulin;
        private String insulinnum;
        private String heartrate;
        private int saltrelated;
        private String sportnum;
        private String sporttime;
        private String mainfood;
        private int compliance;
        private int drugreactions;
        //以下
        private String status;
        private List<String> questionstr;
        //添加血糖
        //添加尿常规
        private List<String> routine;
        //添加血脂
        private List<String> bloodfat;
        //添加尿微量蛋白
        private String urinemicro;
        //血清肌酐
        private String creatinine;
        //促甲状腺激素
        private String stimulating;

        private String alt;
        private String total_bilirubin;
        private String albumin;
        private String prealbumin;

        private String blood_sugar;
        private String prothrombin;
        private String haemoglobin;
        private String blood_ammonia;

        private String sas;
        private String dietary_survey;
        private String physical_examination;


        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getTotal_bilirubin() {
            return total_bilirubin;
        }

        public void setTotal_bilirubin(String total_bilirubin) {
            this.total_bilirubin = total_bilirubin;
        }

        public String getAlbumin() {
            return albumin;
        }

        public void setAlbumin(String albumin) {
            this.albumin = albumin;
        }

        public String getPrealbumin() {
            return prealbumin;
        }

        public void setPrealbumin(String prealbumin) {
            this.prealbumin = prealbumin;
        }

        public String getBlood_sugar() {
            return blood_sugar;
        }

        public void setBlood_sugar(String blood_sugar) {
            this.blood_sugar = blood_sugar;
        }

        public String getProthrombin() {
            return prothrombin;
        }

        public void setProthrombin(String prothrombin) {
            this.prothrombin = prothrombin;
        }

        public String getHaemoglobin() {
            return haemoglobin;
        }

        public void setHaemoglobin(String haemoglobin) {
            this.haemoglobin = haemoglobin;
        }

        public String getBlood_ammonia() {
            return blood_ammonia;
        }

        public void setBlood_ammonia(String blood_ammonia) {
            this.blood_ammonia = blood_ammonia;
        }

        public String getSas() {
            return sas;
        }

        public void setSas(String sas) {
            this.sas = sas;
        }

        public String getDietary_survey() {
            return dietary_survey;
        }

        public void setDietary_survey(String dietary_survey) {
            this.dietary_survey = dietary_survey;
        }

        public String getPhysical_examination() {
            return physical_examination;
        }

        public void setPhysical_examination(String physical_examination) {
            this.physical_examination = physical_examination;
        }

        public String getStimulating() {
            return stimulating;
        }

        public void setStimulating(String stimulating) {
            this.stimulating = stimulating;
        }

        public String getCreatinine() {
            return creatinine;
        }

        public void setCreatinine(String creatinine) {
            this.creatinine = creatinine;
        }

        public String getUrinemicro() {
            return urinemicro;
        }

        public void setUrinemicro(String urinemicro) {
            this.urinemicro = urinemicro;
        }

        public List<String> getBloodfat() {
            return bloodfat;
        }

        public void setBloodfat(List<String> bloodfat) {
            this.bloodfat = bloodfat;
        }

        public List<String> getRoutine() {
            return routine;
        }

        public void setRoutine(List<String> routine) {
            this.routine = routine;
        }

        //        public List<List<String>> getSugars() {
        //            return sugars;
        //        }
        //
        //        public void setSugars(List<List<String>> sugars) {
        //            this.sugars = sugars;
        //        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getSystolic() {
            return systolic;
        }

        public void setSystolic(String systolic) {
            this.systolic = systolic;
        }

        public String getDiastolic() {
            return diastolic;
        }

        public void setDiastolic(String diastolic) {
            this.diastolic = diastolic;
        }

        public String getDrug() {
            return drug;
        }

        public void setDrug(String drug) {
            this.drug = drug;
        }

        public String getSmok() {
            return smok;
        }

        public void setSmok(String smok) {
            this.smok = smok;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public int getPsychological() {
            return psychological;
        }

        public void setPsychological(int psychological) {
            this.psychological = psychological;
        }

        public String getSymptom() {
            return symptom;
        }

        public void setSymptom(String symptom) {
            this.symptom = symptom;
        }

        public String getBmi() {
            return bmi;
        }

        public void setBmi(String bmi) {
            this.bmi = bmi;
        }

        public int getPulsation() {
            return pulsation;
        }

        public void setPulsation(int pulsation) {
            this.pulsation = pulsation;
        }

        public String getHeartrate() {
            return heartrate;
        }

        public void setHeartrate(String heartrate) {
            this.heartrate = heartrate;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public int getBehavior() {
            return behavior;
        }

        public void setBehavior(int behavior) {
            this.behavior = behavior;
        }

        public String getFastingbloodsugar() {
            return fastingbloodsugar;
        }

        public void setFastingbloodsugar(String fastingbloodsugar) {
            this.fastingbloodsugar = fastingbloodsugar;
        }

        public String getHemoglobin() {
            return hemoglobin;
        }

        public void setHemoglobin(String hemoglobin) {
            this.hemoglobin = hemoglobin;
        }

        public String getExaminetime() {
            return examinetime;
        }

        public void setExaminetime(String examinetime) {
            this.examinetime = examinetime;
        }

        public int getReaction() {
            return reaction;
        }

        public void setReaction(int reaction) {
            this.reaction = reaction;
        }

        public int getFollowstyle() {
            return followstyle;
        }

        public void setFollowstyle(int followstyle) {
            this.followstyle = followstyle;
        }

        public String getInsulin() {
            return insulin;
        }

        public void setInsulin(String insulin) {
            this.insulin = insulin;
        }

        public String getInsulinnum() {
            return insulinnum;
        }

        public void setInsulinnum(String insulinnum) {
            this.insulinnum = insulinnum;
        }

        //        public int getHeartrate() {
        //            return heartrate;
        //        }
        //
        //        public void setHeartrate(int heartrate) {
        //            this.heartrate = heartrate;
        //        }

        public int getSaltrelated() {
            return saltrelated;
        }

        public void setSaltrelated(int saltrelated) {
            this.saltrelated = saltrelated;
        }

        public String getSportnum() {
            return sportnum;
        }

        public void setSportnum(String sportnum) {
            this.sportnum = sportnum;
        }

        public String getSporttime() {
            return sporttime;
        }

        public void setSporttime(String sporttime) {
            this.sporttime = sporttime;
        }

        public String getMainfood() {
            return mainfood;
        }

        public void setMainfood(String mainfood) {
            this.mainfood = mainfood;
        }

        public int getCompliance() {
            return compliance;
        }

        public void setCompliance(int compliance) {
            this.compliance = compliance;
        }

        public int getDrugreactions() {
            return drugreactions;
        }

        public void setDrugreactions(int drugreactions) {
            this.drugreactions = drugreactions;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        //        public List<List<String>> getMedicdetail() {
        //            return medicdetail;
        //        }
        //
        //        public void setMedicdetail(List<List<String>> medicdetail) {
        //            this.medicdetail = medicdetail;
        //        }

        public List<String> getQuestionstr() {
            return questionstr;
        }

        public void setQuestionstr(List<String> questionstr) {
            this.questionstr = questionstr;
        }
    }
}
