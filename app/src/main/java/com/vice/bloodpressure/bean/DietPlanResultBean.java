package com.vice.bloodpressure.bean;

import java.util.List;

public class DietPlanResultBean {


    /**
     * weight : 68
     * height : 174
     * hq : 2100
     * bmi : 22.5
     * eatinginfo : {"breakfast":[{"name":"米饭","str":"大米69g","greensid":1,"hq":250},{"name":"番茄炒鸡蛋","str":"番茄100g,鸡蛋60g","greensid":182,"hq":108.8},{"name":"豆浆","str":"豆浆266g","greensid":191,"hq":61.2}],"lunch":[{"name":"小米饭","str":"小米139g","greensid":2,"hq":500},{"name":"番茄牛肉","str":"番茄31g,牛肉25g","greensid":171,"hq":50},{"name":"拍黄瓜","str":"黄瓜233g","greensid":135,"hq":63},{"name":"番茄","str":"番茄233g","greensid":92,"hq":270}],"dinner":[{"name":"牛肉胡萝卜包子","str":"面粉139g,牛肉69g,白萝卜278g","greensid":15,"hq":748.64854353829},{"name":"番茄牛肉","str":"番茄42g,牛肉33g","greensid":171,"hq":67.5},{"name":"拍黄瓜","str":"黄瓜133g","greensid":135,"hq":23.851456461712}]}
     * eatinginfo1 : {"breakfast":[{"name":"米饭","str":"大米69g","greensid":1,"hq":250},{"name":"牛奶","str":"牛奶252g","greensid":190,"hq":141},{"name":"拍黄瓜","str":"黄瓜161g","greensid":135,"hq":29}],"lunch":[{"name":"米饭","str":"大米139g","greensid":1,"hq":500},{"name":"番茄牛肉","str":"番茄31g,牛肉25g","greensid":171,"hq":50},{"name":"番茄","str":"番茄233g","greensid":92,"hq":63},{"name":"拍黄瓜","str":"黄瓜233g","greensid":135,"hq":270}],"dinner":[{"name":"小米饭","str":"小米139g","greensid":2,"hq":500},{"name":"番茄牛肉","str":"番茄42g,牛肉33g","greensid":171,"hq":67.5},{"name":"番茄","str":"番茄233g","greensid":92,"hq":63},{"name":"拍黄瓜","str":"黄瓜233g","greensid":135,"hq":270}]}
     */

    private String weight;
    private String height;
    private String hq;
    private String bmi;
    private EatinginfoBean eatinginfo;
    private Eatinginfo1Bean eatinginfo1;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public EatinginfoBean getEatinginfo() {
        return eatinginfo;
    }

    public void setEatinginfo(EatinginfoBean eatinginfo) {
        this.eatinginfo = eatinginfo;
    }

    public Eatinginfo1Bean getEatinginfo1() {
        return eatinginfo1;
    }

    public void setEatinginfo1(Eatinginfo1Bean eatinginfo1) {
        this.eatinginfo1 = eatinginfo1;
    }

    public static class EatinginfoBean {
        private List<BreakfastBean> breakfast;
        private List<LunchBean> lunch;
        private List<DinnerBean> dinner;

        public List<BreakfastBean> getBreakfast() {
            return breakfast;
        }

        public void setBreakfast(List<BreakfastBean> breakfast) {
            this.breakfast = breakfast;
        }

        public List<LunchBean> getLunch() {
            return lunch;
        }

        public void setLunch(List<LunchBean> lunch) {
            this.lunch = lunch;
        }

        public List<DinnerBean> getDinner() {
            return dinner;
        }

        public void setDinner(List<DinnerBean> dinner) {
            this.dinner = dinner;
        }

        public static class BreakfastBean {
            /**
             * name : 米饭
             * str : 大米69g
             * greensid : 1
             * hq : 250
             */

            private String name;
            private String str;
            private int greensid;
            private int hq;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public int getGreensid() {
                return greensid;
            }

            public void setGreensid(int greensid) {
                this.greensid = greensid;
            }

            public int getHq() {
                return hq;
            }

            public void setHq(int hq) {
                this.hq = hq;
            }
        }

        public static class LunchBean {
            /**
             * name : 小米饭
             * str : 小米139g
             * greensid : 2
             * hq : 500
             */

            private String name;
            private String str;
            private int greensid;
            private int hq;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public int getGreensid() {
                return greensid;
            }

            public void setGreensid(int greensid) {
                this.greensid = greensid;
            }

            public int getHq() {
                return hq;
            }

            public void setHq(int hq) {
                this.hq = hq;
            }
        }

        public static class DinnerBean {
            /**
             * name : 牛肉胡萝卜包子
             * str : 面粉139g,牛肉69g,白萝卜278g
             * greensid : 15
             * hq : 748.64854353829
             */

            private String name;
            private String str;
            private int greensid;
            private double hq;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public int getGreensid() {
                return greensid;
            }

            public void setGreensid(int greensid) {
                this.greensid = greensid;
            }

            public double getHq() {
                return hq;
            }

            public void setHq(double hq) {
                this.hq = hq;
            }
        }
    }

    public static class Eatinginfo1Bean {
        private List<BreakfastBeanX> breakfast;
        private List<LunchBeanX> lunch;
        private List<DinnerBeanX> dinner;

        public List<BreakfastBeanX> getBreakfast() {
            return breakfast;
        }

        public void setBreakfast(List<BreakfastBeanX> breakfast) {
            this.breakfast = breakfast;
        }

        public List<LunchBeanX> getLunch() {
            return lunch;
        }

        public void setLunch(List<LunchBeanX> lunch) {
            this.lunch = lunch;
        }

        public List<DinnerBeanX> getDinner() {
            return dinner;
        }

        public void setDinner(List<DinnerBeanX> dinner) {
            this.dinner = dinner;
        }

        public static class BreakfastBeanX {
            /**
             * name : 米饭
             * str : 大米69g
             * greensid : 1
             * hq : 250
             */

            private String name;
            private String str;
            private int greensid;
            private int hq;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public int getGreensid() {
                return greensid;
            }

            public void setGreensid(int greensid) {
                this.greensid = greensid;
            }

            public int getHq() {
                return hq;
            }

            public void setHq(int hq) {
                this.hq = hq;
            }
        }

        public static class LunchBeanX {
            /**
             * name : 米饭
             * str : 大米139g
             * greensid : 1
             * hq : 500
             */

            private String name;
            private String str;
            private int greensid;
            private int hq;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public int getGreensid() {
                return greensid;
            }

            public void setGreensid(int greensid) {
                this.greensid = greensid;
            }

            public int getHq() {
                return hq;
            }

            public void setHq(int hq) {
                this.hq = hq;
            }
        }

        public static class DinnerBeanX {
            /**
             * name : 小米饭
             * str : 小米139g
             * greensid : 2
             * hq : 500
             */

            private String name;
            private String str;
            private int greensid;
            private int hq;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public int getGreensid() {
                return greensid;
            }

            public void setGreensid(int greensid) {
                this.greensid = greensid;
            }

            public int getHq() {
                return hq;
            }

            public void setHq(int hq) {
                this.hq = hq;
            }
        }
    }
}
