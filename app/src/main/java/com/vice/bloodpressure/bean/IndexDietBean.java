package com.vice.bloodpressure.bean;

import java.util.List;

public class IndexDietBean {
    /**
     * breakfast : ["羊肉包子","111","拌海带"]
     * lunch : ["燕麦片焖米饭","肉片烧苦瓜","凉拌苦瓜"]
     * dinner : ["韭菜盒子","牛肉炖萝卜","炖大白菜"]
     * id : 226
     * nephropathylei : 1
     * kcal : {"tanshui":"50%-60%: 244g~293g","danbaizhi":"0.8g/(Kg.d):48g","zhifang":"65g","nephropathylei":1,"food":["260g","500g","225g","220g"]}
     */
    private int id;
    private int nephropathylei;
    private int profession;

    private KcalBean kcal;
    private List<String> breakfast;
    private List<String> lunch;
    private List<String> dinner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNephropathylei() {
        return nephropathylei;
    }

    public void setNephropathylei(int nephropathylei) {
        this.nephropathylei = nephropathylei;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }



    public KcalBean getKcal() {
        return kcal;
    }

    public void setKcal(KcalBean kcal) {
        this.kcal = kcal;
    }

    public List<String> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<String> breakfast) {
        this.breakfast = breakfast;
    }

    public List<String> getLunch() {
        return lunch;
    }

    public void setLunch(List<String> lunch) {
        this.lunch = lunch;
    }

    public List<String> getDinner() {
        return dinner;
    }

    public void setDinner(List<String> dinner) {
        this.dinner = dinner;
    }

    public static class KcalBean {
        /**
         * tanshui : 50%-60%: 244g~293g
         * danbaizhi : 0.8g/(Kg.d):48g
         * zhifang : 65g
         * nephropathylei : 1
         * food : ["260g","500g","225g","220g"]
         */

        private String tanshui;
        private String danbaizhi;
        private String zhifang;
        private int allKcal;
        private List<String> food;

        public String getTanshui() {
            return tanshui;
        }

        public void setTanshui(String tanshui) {
            this.tanshui = tanshui;
        }

        public String getDanbaizhi() {
            return danbaizhi;
        }

        public void setDanbaizhi(String danbaizhi) {
            this.danbaizhi = danbaizhi;
        }

        public String getZhifang() {
            return zhifang;
        }

        public void setZhifang(String zhifang) {
            this.zhifang = zhifang;
        }
        public int getAllKcal() {
            return allKcal;
        }

        public void setAllKcal(int allKcal) {
            this.allKcal = allKcal;
        }
        public List<String> getFood() {
            return food;
        }

        public void setFood(List<String> food) {
            this.food = food;
        }
    }
}
