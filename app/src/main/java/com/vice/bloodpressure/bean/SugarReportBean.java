package com.vice.bloodpressure.bean;

import java.util.List;

public class SugarReportBean {


    /**
     * nickname : 李飞
     * year : 2019
     * month : 04
     * diabetesleim : Ⅰ
     * begintime : 2019.04.01
     * endtime : 2019.04.24
     * danbai : [{"diastaticvalue":"10","datetime":"2019.03.22","biao":"未达标"},{"diastaticvalue":"12","datetime":"2019.03.22","biao":"未达标"},{"diastaticvalue":"36","datetime":"2019.03.23","biao":"未达标"},{"diastaticvalue":"5","datetime":"2019.04.15","biao":"未达标"}]
     * num : 4
     * thmb : 6.0%~7.0%
     * dabiao : 0
     * nobiao : 4
     * tshuom : 您最近血糖水平控制未达标，但近期血糖控制的较好，血糖水平有所改善。当前血糖控制方案有效，您要继续加油哦。
     * xuezci : 4
     * xuezc : 2
     * xuepg : 2
     * xuepd : 0
     * xuesm : 本月血糖监测次数较少，请坚持自我监测（至少2条/周）。
     * kongfu : 1
     * kongfuzc : 1
     * kongfupg :
     * kongfupd :
     * kongfupgzhi : 5.8
     * kongfupdzhi : 5.8
     * zch2 : 1
     * zch2zc : 1
     * zch2pg :
     * zch2pd :
     * zch2pgzhi : 8
     * zch2pdzhi : 8
     * wucq : 2
     * wucqzc :
     * wucqpg : 2
     * wucqpd :
     * wucqpgzhi : 12
     * wucqpdzhi : 9
     * wuch :
     * wuchzc :
     * wuchpg :
     * wuchpd :
     * wuchpgzhi : 0
     * wuchpdzhi : 0
     * wancq :
     * wancqzc :
     * wancqpg :
     * wancqpd :
     * wancqpgzhi : 0
     * wancqpdzhi : 0
     * wanch :
     * wanchzc :
     * wanchpg :
     * wanchpd :
     * wanchpgzhi : 0
     * wanchpdzhi : 0
     * shuiq :
     * shuiqzc :
     * shuiqpg :
     * shuiqpd :
     * shuiqpgzhi : 0
     * shuiqpdzhi : 0
     * suiji :
     * suijizc :
     * suijipg :
     * suijipd :
     * suijipgzhi : 0
     * suijipdzhi : 0
     * kongfuzhi : [[9,5.8]]
     * zch2zhi : [[14,8]]
     * wucqzhi : [[15,12],[15,9]]
     * wancqkzmb : [3.3,5.3]
     * wucqkzmb : [3.3,5.3]
     * kongfukzmb : [3.3,5.3]
     * shuiqkzmb : [3.3,6.7]
     * wanchkzmb : [3.3,6.7]
     * wuchkzmb : [3.3,6.7]
     * zch2kzmb : [3.3,6.7]
     * suijikzmb : [3.3,5.6]
     * wucqsm : 午餐前出现一次低血糖记录，有低血糖风险，请咨询医生。
     * wuchsm : 无午餐后血糖记录，请坚持自我监测哦。
     * wancqsm : 无晚餐前血糖记录，请坚持自我监测哦。
     * wanchsm : 无晚餐后血糖记录，请坚持自我监测哦。
     * shuiqsm : 无睡前血糖记录，请坚持自我监测哦。
     * suijism : 无随机血糖记录，请坚持自我监测哦。
     */

    private String nickname;
    private String year;
    private String month;
    private String diabetesleim;
    private String begintime;
    private String endtime;
    private int num;
    private String thmb;
    private int dabiao;
    private int nobiao;
    private String tshuom;
    private int xuezci;
    private int xuezc;
    private int xuepg;
    private int xuepd;
    private String xuesm;
    private String kongfu;
    private String kongfuzc;
    private String kongfupg;
    private String kongfupd;
    private double kongfupgzhi;
    private double kongfupdzhi;
    private String zch2;
    private String zch2zc;
    private String zch2pg;
    private String zch2pd;
    private double zch2pgzhi;
    private double zch2pdzhi;
    private int wucq;
    private String wucqzc;
    private int wucqpg;
    private String wucqpd;
    private double wucqpgzhi;
    private double wucqpdzhi;
    private String wuch;
    private String wuchzc;
    private String wuchpg;
    private String wuchpd;
    private double wuchpgzhi;
    private double wuchpdzhi;
    private String wancq;
    private String wancqzc;
    private String wancqpg;
    private String wancqpd;
    private double wancqpgzhi;
    private double wancqpdzhi;
    private String wanch;
    private String wanchzc;
    private String wanchpg;
    private String wanchpd;
    private double wanchpgzhi;
    private double wanchpdzhi;
    private String shuiq;
    private String shuiqzc;
    private String shuiqpg;
    private String shuiqpd;
    private double shuiqpgzhi;
    private double shuiqpdzhi;
    private String suiji;
    private String suijizc;
    private String suijipg;
    private String suijipd;
    private double suijipgzhi;
    private double suijipdzhi;
    private String wucqsm;
    private String wuchsm;
    private String wancqsm;
    private String wanchsm;
    private String shuiqsm;
    private String suijism;
    private List<DanbaiBean> danbai;
    private List<List<Float>> kongfuzhi;
    private List<List<Float>> zch2zhi;
    private List<List<Float>> wucqzhi;
    private List<List<Float>> wuchzhi;
    private List<List<Float>> wancqzhi;
    private List<List<Float>> wanchzhi;
    private List<List<Float>> shuiqzhi;
    private List<List<Float>> suijizhi;
    private List<Double> wancqkzmb;
    private List<Double> wucqkzmb;
    private List<Double> kongfukzmb;
    private List<Double> shuiqkzmb;
    private List<Double> wanchkzmb;
    private List<Double> wuchkzmb;
    private List<Double> zch2kzmb;
    private List<Double> suijikzmb;

    private String kongfusm;
    private String zch2sm;


    public List<List<Float>> getKongfuzhi() {
        return kongfuzhi;
    }

    public void setKongfuzhi(List<List<Float>> kongfuzhi) {
        this.kongfuzhi = kongfuzhi;
    }

    public List<List<Float>> getZch2zhi() {
        return zch2zhi;
    }

    public void setZch2zhi(List<List<Float>> zch2zhi) {
        this.zch2zhi = zch2zhi;
    }

    public List<List<Float>> getWucqzhi() {
        return wucqzhi;
    }

    public void setWucqzhi(List<List<Float>> wucqzhi) {
        this.wucqzhi = wucqzhi;
    }

    public List<List<Float>> getWuchzhi() {
        return wuchzhi;
    }

    public void setWuchzhi(List<List<Float>> wuchzhi) {
        this.wuchzhi = wuchzhi;
    }

    public List<List<Float>> getWancqzhi() {
        return wancqzhi;
    }

    public void setWancqzhi(List<List<Float>> wancqzhi) {
        this.wancqzhi = wancqzhi;
    }

    public List<List<Float>> getWanchzhi() {
        return wanchzhi;
    }

    public void setWanchzhi(List<List<Float>> wanchzhi) {
        this.wanchzhi = wanchzhi;
    }

    public List<List<Float>> getShuiqzhi() {
        return shuiqzhi;
    }

    public void setShuiqzhi(List<List<Float>> shuiqzhi) {
        this.shuiqzhi = shuiqzhi;
    }

    public List<List<Float>> getSuijizhi() {
        return suijizhi;
    }

    public void setSuijizhi(List<List<Float>> suijizhi) {
        this.suijizhi = suijizhi;
    }

    public String getZch2sm() {
        return zch2sm;
    }

    public void setZch2sm(String zch2sm) {
        this.zch2sm = zch2sm;
    }

    public String getKongfusm() {
        return kongfusm;
    }

    public void setKongfusm(String kongfusm) {
        this.kongfusm = kongfusm;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDiabetesleim() {
        return diabetesleim;
    }

    public void setDiabetesleim(String diabetesleim) {
        this.diabetesleim = diabetesleim;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getThmb() {
        return thmb;
    }

    public void setThmb(String thmb) {
        this.thmb = thmb;
    }

    public int getDabiao() {
        return dabiao;
    }

    public void setDabiao(int dabiao) {
        this.dabiao = dabiao;
    }

    public int getNobiao() {
        return nobiao;
    }

    public void setNobiao(int nobiao) {
        this.nobiao = nobiao;
    }

    public String getTshuom() {
        return tshuom;
    }

    public void setTshuom(String tshuom) {
        this.tshuom = tshuom;
    }

    public int getXuezci() {
        return xuezci;
    }

    public void setXuezci(int xuezci) {
        this.xuezci = xuezci;
    }

    public int getXuezc() {
        return xuezc;
    }

    public void setXuezc(int xuezc) {
        this.xuezc = xuezc;
    }

    public int getXuepg() {
        return xuepg;
    }

    public void setXuepg(int xuepg) {
        this.xuepg = xuepg;
    }

    public int getXuepd() {
        return xuepd;
    }

    public void setXuepd(int xuepd) {
        this.xuepd = xuepd;
    }

    public String getXuesm() {
        return xuesm;
    }

    public void setXuesm(String xuesm) {
        this.xuesm = xuesm;
    }

    public String getKongfu() {
        return kongfu;
    }

    public void setKongfu(String kongfu) {
        this.kongfu = kongfu;
    }

    public String getKongfuzc() {
        return kongfuzc;
    }

    public void setKongfuzc(String kongfuzc) {
        this.kongfuzc = kongfuzc;
    }

    public String getKongfupg() {
        return kongfupg;
    }

    public void setKongfupg(String kongfupg) {
        this.kongfupg = kongfupg;
    }

    public String getKongfupd() {
        return kongfupd;
    }

    public void setKongfupd(String kongfupd) {
        this.kongfupd = kongfupd;
    }

    public double getKongfupgzhi() {
        return kongfupgzhi;
    }

    public void setKongfupgzhi(double kongfupgzhi) {
        this.kongfupgzhi = kongfupgzhi;
    }

    public double getKongfupdzhi() {
        return kongfupdzhi;
    }

    public void setKongfupdzhi(double kongfupdzhi) {
        this.kongfupdzhi = kongfupdzhi;
    }

    public String getZch2() {
        return zch2;
    }

    public void setZch2(String zch2) {
        this.zch2 = zch2;
    }

    public String getZch2zc() {
        return zch2zc;
    }

    public void setZch2zc(String zch2zc) {
        this.zch2zc = zch2zc;
    }

    public String getZch2pg() {
        return zch2pg;
    }

    public void setZch2pg(String zch2pg) {
        this.zch2pg = zch2pg;
    }

    public String getZch2pd() {
        return zch2pd;
    }

    public void setZch2pd(String zch2pd) {
        this.zch2pd = zch2pd;
    }

    public double getZch2pgzhi() {
        return zch2pgzhi;
    }

    public void setZch2pgzhi(double zch2pgzhi) {
        this.zch2pgzhi = zch2pgzhi;
    }

    public double getZch2pdzhi() {
        return zch2pdzhi;
    }

    public void setZch2pdzhi(double zch2pdzhi) {
        this.zch2pdzhi = zch2pdzhi;
    }

    public int getWucq() {
        return wucq;
    }

    public void setWucq(int wucq) {
        this.wucq = wucq;
    }

    public String getWucqzc() {
        return wucqzc;
    }

    public void setWucqzc(String wucqzc) {
        this.wucqzc = wucqzc;
    }

    public int getWucqpg() {
        return wucqpg;
    }

    public void setWucqpg(int wucqpg) {
        this.wucqpg = wucqpg;
    }

    public String getWucqpd() {
        return wucqpd;
    }

    public void setWucqpd(String wucqpd) {
        this.wucqpd = wucqpd;
    }

    public double getWucqpgzhi() {
        return wucqpgzhi;
    }

    public void setWucqpgzhi(double wucqpgzhi) {
        this.wucqpgzhi = wucqpgzhi;
    }

    public double getWucqpdzhi() {
        return wucqpdzhi;
    }

    public void setWucqpdzhi(double wucqpdzhi) {
        this.wucqpdzhi = wucqpdzhi;
    }

    public String getWuch() {
        return wuch;
    }

    public void setWuch(String wuch) {
        this.wuch = wuch;
    }

    public String getWuchzc() {
        return wuchzc;
    }

    public void setWuchzc(String wuchzc) {
        this.wuchzc = wuchzc;
    }

    public String getWuchpg() {
        return wuchpg;
    }

    public void setWuchpg(String wuchpg) {
        this.wuchpg = wuchpg;
    }

    public String getWuchpd() {
        return wuchpd;
    }

    public void setWuchpd(String wuchpd) {
        this.wuchpd = wuchpd;
    }

    public double getWuchpgzhi() {
        return wuchpgzhi;
    }

    public void setWuchpgzhi(double wuchpgzhi) {
        this.wuchpgzhi = wuchpgzhi;
    }

    public double getWuchpdzhi() {
        return wuchpdzhi;
    }

    public void setWuchpdzhi(double wuchpdzhi) {
        this.wuchpdzhi = wuchpdzhi;
    }

    public String getWancq() {
        return wancq;
    }

    public void setWancq(String wancq) {
        this.wancq = wancq;
    }

    public String getWancqzc() {
        return wancqzc;
    }

    public void setWancqzc(String wancqzc) {
        this.wancqzc = wancqzc;
    }

    public String getWancqpg() {
        return wancqpg;
    }

    public void setWancqpg(String wancqpg) {
        this.wancqpg = wancqpg;
    }

    public String getWancqpd() {
        return wancqpd;
    }

    public void setWancqpd(String wancqpd) {
        this.wancqpd = wancqpd;
    }

    public double getWancqpgzhi() {
        return wancqpgzhi;
    }

    public void setWancqpgzhi(double wancqpgzhi) {
        this.wancqpgzhi = wancqpgzhi;
    }

    public double getWancqpdzhi() {
        return wancqpdzhi;
    }

    public void setWancqpdzhi(double wancqpdzhi) {
        this.wancqpdzhi = wancqpdzhi;
    }

    public String getWanch() {
        return wanch;
    }

    public void setWanch(String wanch) {
        this.wanch = wanch;
    }

    public String getWanchzc() {
        return wanchzc;
    }

    public void setWanchzc(String wanchzc) {
        this.wanchzc = wanchzc;
    }

    public String getWanchpg() {
        return wanchpg;
    }

    public void setWanchpg(String wanchpg) {
        this.wanchpg = wanchpg;
    }

    public String getWanchpd() {
        return wanchpd;
    }

    public void setWanchpd(String wanchpd) {
        this.wanchpd = wanchpd;
    }

    public double getWanchpgzhi() {
        return wanchpgzhi;
    }

    public void setWanchpgzhi(double wanchpgzhi) {
        this.wanchpgzhi = wanchpgzhi;
    }

    public double getWanchpdzhi() {
        return wanchpdzhi;
    }

    public void setWanchpdzhi(double wanchpdzhi) {
        this.wanchpdzhi = wanchpdzhi;
    }

    public String getShuiq() {
        return shuiq;
    }

    public void setShuiq(String shuiq) {
        this.shuiq = shuiq;
    }

    public String getShuiqzc() {
        return shuiqzc;
    }

    public void setShuiqzc(String shuiqzc) {
        this.shuiqzc = shuiqzc;
    }

    public String getShuiqpg() {
        return shuiqpg;
    }

    public void setShuiqpg(String shuiqpg) {
        this.shuiqpg = shuiqpg;
    }

    public String getShuiqpd() {
        return shuiqpd;
    }

    public void setShuiqpd(String shuiqpd) {
        this.shuiqpd = shuiqpd;
    }

    public double getShuiqpgzhi() {
        return shuiqpgzhi;
    }

    public void setShuiqpgzhi(double shuiqpgzhi) {
        this.shuiqpgzhi = shuiqpgzhi;
    }

    public double getShuiqpdzhi() {
        return shuiqpdzhi;
    }

    public void setShuiqpdzhi(double shuiqpdzhi) {
        this.shuiqpdzhi = shuiqpdzhi;
    }

    public String getSuiji() {
        return suiji;
    }

    public void setSuiji(String suiji) {
        this.suiji = suiji;
    }

    public String getSuijizc() {
        return suijizc;
    }

    public void setSuijizc(String suijizc) {
        this.suijizc = suijizc;
    }

    public String getSuijipg() {
        return suijipg;
    }

    public void setSuijipg(String suijipg) {
        this.suijipg = suijipg;
    }

    public String getSuijipd() {
        return suijipd;
    }

    public void setSuijipd(String suijipd) {
        this.suijipd = suijipd;
    }

    public double getSuijipgzhi() {
        return suijipgzhi;
    }

    public void setSuijipgzhi(double suijipgzhi) {
        this.suijipgzhi = suijipgzhi;
    }

    public double getSuijipdzhi() {
        return suijipdzhi;
    }

    public void setSuijipdzhi(double suijipdzhi) {
        this.suijipdzhi = suijipdzhi;
    }

    public String getWucqsm() {
        return wucqsm;
    }

    public void setWucqsm(String wucqsm) {
        this.wucqsm = wucqsm;
    }

    public String getWuchsm() {
        return wuchsm;
    }

    public void setWuchsm(String wuchsm) {
        this.wuchsm = wuchsm;
    }

    public String getWancqsm() {
        return wancqsm;
    }

    public void setWancqsm(String wancqsm) {
        this.wancqsm = wancqsm;
    }

    public String getWanchsm() {
        return wanchsm;
    }

    public void setWanchsm(String wanchsm) {
        this.wanchsm = wanchsm;
    }

    public String getShuiqsm() {
        return shuiqsm;
    }

    public void setShuiqsm(String shuiqsm) {
        this.shuiqsm = shuiqsm;
    }

    public String getSuijism() {
        return suijism;
    }

    public void setSuijism(String suijism) {
        this.suijism = suijism;
    }

    public List<DanbaiBean> getDanbai() {
        return danbai;
    }

    public void setDanbai(List<DanbaiBean> danbai) {
        this.danbai = danbai;
    }


    public List<Double> getWancqkzmb() {
        return wancqkzmb;
    }

    public void setWancqkzmb(List<Double> wancqkzmb) {
        this.wancqkzmb = wancqkzmb;
    }

    public List<Double> getWucqkzmb() {
        return wucqkzmb;
    }

    public void setWucqkzmb(List<Double> wucqkzmb) {
        this.wucqkzmb = wucqkzmb;
    }

    public List<Double> getKongfukzmb() {
        return kongfukzmb;
    }

    public void setKongfukzmb(List<Double> kongfukzmb) {
        this.kongfukzmb = kongfukzmb;
    }

    public List<Double> getShuiqkzmb() {
        return shuiqkzmb;
    }

    public void setShuiqkzmb(List<Double> shuiqkzmb) {
        this.shuiqkzmb = shuiqkzmb;
    }

    public List<Double> getWanchkzmb() {
        return wanchkzmb;
    }

    public void setWanchkzmb(List<Double> wanchkzmb) {
        this.wanchkzmb = wanchkzmb;
    }

    public List<Double> getWuchkzmb() {
        return wuchkzmb;
    }

    public void setWuchkzmb(List<Double> wuchkzmb) {
        this.wuchkzmb = wuchkzmb;
    }

    public List<Double> getZch2kzmb() {
        return zch2kzmb;
    }

    public void setZch2kzmb(List<Double> zch2kzmb) {
        this.zch2kzmb = zch2kzmb;
    }

    public List<Double> getSuijikzmb() {
        return suijikzmb;
    }

    public void setSuijikzmb(List<Double> suijikzmb) {
        this.suijikzmb = suijikzmb;
    }

    public static class DanbaiBean {
        /**
         * diastaticvalue : 10
         * datetime : 2019.03.22
         * biao : 未达标
         */

        private String diastaticvalue;
        private String datetime;
        private String biao;

        public String getDiastaticvalue() {
            return diastaticvalue;
        }

        public void setDiastaticvalue(String diastaticvalue) {
            this.diastaticvalue = diastaticvalue;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getBiao() {
            return biao;
        }

        public void setBiao(String biao) {
            this.biao = biao;
        }
    }
}
