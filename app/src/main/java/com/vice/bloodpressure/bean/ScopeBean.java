package com.vice.bloodpressure.bean;

import java.io.Serializable;
import java.util.List;

public class ScopeBean implements Serializable {
    /**
     * bp : <130/80
     * glbefore : 3.9-7.2
     * gllater : 5-10
     * gltype : 4
     * target : {"empstomach":[4.4,7],"aftbreakfast":[4.4,10],"beflunch":[4.4,10],"aftlunch":[4.4,10],"befdinner":[4.4,10],"aftdinner":[4.4,10],"befsleep":[4.4,10],"inmorning":[4.4,10]}
     * sugarpoint : {"empstomach":["05:01","08:00"],"aftbreakfast":["08:01","10:00"],"beflunch":["10:01","12:00"],"aftlunch":["12:01","15:00"],"befdinner":["15:01","18:00"],"aftdinner":["18:01","21:00"],"befsleep":["21:01","00:00"],"inmorning":["00:01","05:00"]}
     * bloodtarget : 4.4-10
     */

    private String bp;
    private String glbefore;
    private String gllater;
    private int gltype;
    private ResetTargetBean target;
    private SugarpointBean sugarpoint;
    private String bloodtarget;

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getGlbefore() {
        return glbefore;
    }

    public void setGlbefore(String glbefore) {
        this.glbefore = glbefore;
    }

    public String getGllater() {
        return gllater;
    }

    public void setGllater(String gllater) {
        this.gllater = gllater;
    }

    public int getGltype() {
        return gltype;
    }

    public void setGltype(int gltype) {
        this.gltype = gltype;
    }

    public ResetTargetBean getTarget() {
        return target;
    }

    public void setTarget(ResetTargetBean target) {
        this.target = target;
    }

    public SugarpointBean getSugarpoint() {
        return sugarpoint;
    }

    public void setSugarpoint(SugarpointBean sugarpoint) {
        this.sugarpoint = sugarpoint;
    }

    public String getBloodtarget() {
        return bloodtarget;
    }

    public void setBloodtarget(String bloodtarget) {
        this.bloodtarget = bloodtarget;
    }


    public static class SugarpointBean {
        private List<String> empstomach;
        private List<String> aftbreakfast;
        private List<String> beflunch;
        private List<String> aftlunch;
        private List<String> befdinner;
        private List<String> aftdinner;
        private List<String> befsleep;
        private List<String> inmorning;

        public List<String> getEmpstomach() {
            return empstomach;
        }

        public void setEmpstomach(List<String> empstomach) {
            this.empstomach = empstomach;
        }

        public List<String> getAftbreakfast() {
            return aftbreakfast;
        }

        public void setAftbreakfast(List<String> aftbreakfast) {
            this.aftbreakfast = aftbreakfast;
        }

        public List<String> getBeflunch() {
            return beflunch;
        }

        public void setBeflunch(List<String> beflunch) {
            this.beflunch = beflunch;
        }

        public List<String> getAftlunch() {
            return aftlunch;
        }

        public void setAftlunch(List<String> aftlunch) {
            this.aftlunch = aftlunch;
        }

        public List<String> getBefdinner() {
            return befdinner;
        }

        public void setBefdinner(List<String> befdinner) {
            this.befdinner = befdinner;
        }

        public List<String> getAftdinner() {
            return aftdinner;
        }

        public void setAftdinner(List<String> aftdinner) {
            this.aftdinner = aftdinner;
        }

        public List<String> getBefsleep() {
            return befsleep;
        }

        public void setBefsleep(List<String> befsleep) {
            this.befsleep = befsleep;
        }

        public List<String> getInmorning() {
            return inmorning;
        }

        public void setInmorning(List<String> inmorning) {
            this.inmorning = inmorning;
        }
    }
}
