package com.vice.bloodpressure.bean;

import java.util.List;

/**
 * 描述:
 * 1:草稿未发送
 * 2：保存已发送，患者未打开
 * 3：患者已查看
 * 4：患者完成问卷
 * 5：医生完成建议保存
 * 作者: LYD
 * 创建日期: 2019/11/6 20:54
 */
public class FollowUpVisitBloodSugarDataBean {
    /**
     * vid : 164
     * height :
     * weight :
     * systolic :
     * diastolic :
     * drug : null
     * smok :
     * drink :
     * psychological : 0
     * paquestion :
     * measures :
     * target :
     * symptom : []
     * bmi :
     * pulsation : 0
     * other :
     * behavior : 0
     * fastingbloodsugar :
     * hemoglobin :
     * examinetime : 1970-01-01
     * reaction : 0
     * followstyle : 0
     * medicdetail : [["10","10","10"],["20","20","20"],["30","30","30"]]
     * insulin :
     * insulinnum :
     * heartrate : 0
     * saltrelated : 0
     * sportnum : 0
     * sporttime : 0
     * mainfood :
     * compliance : 0
     * drugreactions : 0
     * liverfun : [""]
     * routine : ["","","","","","","","","","",""]
     * bloodfat : ["","","",""]
     * urinemicro :
     * creatinine :
     * stimulating :
     * heartpic : [""]
     * heartcontent :
     * eyespic : [""]
     * eyescontent :
     * neuropathypic : [""]
     * neuropathycontent :
     * sugars : [["","","","","","","",""],["","","","","","","",""],["","","","","","","",""],["","","","","","","",""],["","","","","","","",""],["","","","","","","",""],["","","","","","","",""]]
     * questionstr : ["5"]
     * stime : 2019-10-31
     * etime : 2019-11-06
     * addtime : 2019-11-06
     * status : 3
     */

    private int vid;
    private String height;
    private String weight;
    private String systolic;
    private String diastolic;
    private String drug;
    private String smok;
    private String drink;
    private int psychological;
    private String paquestion;
    private String measures;
    private String target;
    private String bmi;
    private int pulsation;
    private String other;
    private int behavior;
    private String fastingbloodsugar;
    private String hemoglobin;
    private String examinetime;
    private int reaction;
    private int followstyle;
    private String insulin;
    private String insulinnum;
    private int heartrate;
    private int saltrelated;
    private String sportnum;
    private String sporttime;
    private String mainfood;
    private int compliance;
    private int drugreactions;
    private String urinemicro;
    private String creatinine;
    private String stimulating;
    private String heartcontent;
    private String eyescontent;
    private String neuropathycontent;
    private String stime;
    private String etime;
    private String addtime;
    private int status;
    private List<String> symptom;
    private List<List<String>> medicdetail;
    private String liverfun1;
    private String liverfun2;
    private String liverfun3;
    private String livercon;
    private List<String> routine;
    private List<String> bloodfat;
    private String heartpic1;
    private String heartpic2;
    private String heartpic3;
    private String eyespic1;
    private String eyespic2;
    private String eyespic3;
    private String neuropathypic1;
    private String neuropathypic2;
    private String neuropathypic3;
    private List<List<String>> sugars;
    private List<String> questionstr;


    //status为2


    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

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

    public String getPaquestion() {
        return paquestion;
    }

    public void setPaquestion(String paquestion) {
        this.paquestion = paquestion;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public int getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(int heartrate) {
        this.heartrate = heartrate;
    }

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

    public String getUrinemicro() {
        return urinemicro;
    }

    public void setUrinemicro(String urinemicro) {
        this.urinemicro = urinemicro;
    }

    public String getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(String creatinine) {
        this.creatinine = creatinine;
    }

    public String getStimulating() {
        return stimulating;
    }

    public void setStimulating(String stimulating) {
        this.stimulating = stimulating;
    }

    public String getHeartcontent() {
        return heartcontent;
    }

    public void setHeartcontent(String heartcontent) {
        this.heartcontent = heartcontent;
    }

    public String getEyescontent() {
        return eyescontent;
    }

    public void setEyescontent(String eyescontent) {
        this.eyescontent = eyescontent;
    }

    public String getNeuropathycontent() {
        return neuropathycontent;
    }

    public void setNeuropathycontent(String neuropathycontent) {
        this.neuropathycontent = neuropathycontent;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getSymptom() {
        return symptom;
    }

    public void setSymptom(List<String> symptom) {
        this.symptom = symptom;
    }

    public List<List<String>> getMedicdetail() {
        return medicdetail;
    }

    public void setMedicdetail(List<List<String>> medicdetail) {
        this.medicdetail = medicdetail;
    }

    public String getLiverfun1() {
        return liverfun1;
    }

    public void setLiverfun1(String liverfun1) {
        this.liverfun1 = liverfun1;
    }

    public String getLiverfun2() {
        return liverfun2;
    }

    public void setLiverfun2(String liverfun2) {
        this.liverfun2 = liverfun2;
    }

    public String getLiverfun3() {
        return liverfun3;
    }

    public void setLiverfun3(String liverfun3) {
        this.liverfun3 = liverfun3;
    }

    public List<String> getRoutine() {
        return routine;
    }

    public void setRoutine(List<String> routine) {
        this.routine = routine;
    }

    public List<String> getBloodfat() {
        return bloodfat;
    }

    public void setBloodfat(List<String> bloodfat) {
        this.bloodfat = bloodfat;
    }

    public String getHeartpic1() {
        return heartpic1;
    }

    public void setHeartpic1(String heartpic1) {
        this.heartpic1 = heartpic1;
    }

    public String getHeartpic2() {
        return heartpic2;
    }

    public void setHeartpic2(String heartpic2) {
        this.heartpic2 = heartpic2;
    }

    public String getHeartpic3() {
        return heartpic3;
    }

    public void setHeartpic3(String heartpic3) {
        this.heartpic3 = heartpic3;
    }

    public String getEyespic1() {
        return eyespic1;
    }

    public void setEyespic1(String eyespic1) {
        this.eyespic1 = eyespic1;
    }

    public String getEyespic2() {
        return eyespic2;
    }

    public void setEyespic2(String eyespic2) {
        this.eyespic2 = eyespic2;
    }

    public String getEyespic3() {
        return eyespic3;
    }

    public void setEyespic3(String eyespic3) {
        this.eyespic3 = eyespic3;
    }

    public String getNeuropathypic1() {
        return neuropathypic1;
    }

    public void setNeuropathypic1(String neuropathypic1) {
        this.neuropathypic1 = neuropathypic1;
    }

    public String getNeuropathypic2() {
        return neuropathypic2;
    }

    public void setNeuropathypic2(String neuropathypic2) {
        this.neuropathypic2 = neuropathypic2;
    }

    public String getNeuropathypic3() {
        return neuropathypic3;
    }

    public void setNeuropathypic3(String neuropathypic3) {
        this.neuropathypic3 = neuropathypic3;
    }

    public List<List<String>> getSugars() {
        return sugars;
    }

    public void setSugars(List<List<String>> sugars) {
        this.sugars = sugars;
    }

    public List<String> getQuestionstr() {
        return questionstr;
    }

    public void setQuestionstr(List<String> questionstr) {
        this.questionstr = questionstr;
    }

    public String getLivercon() {
        return livercon;
    }

    public void setLivercon(String livercon) {
        this.livercon = livercon;
    }
}
