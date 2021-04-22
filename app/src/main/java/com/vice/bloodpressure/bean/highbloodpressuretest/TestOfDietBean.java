package com.vice.bloodpressure.bean.highbloodpressuretest;


import java.io.Serializable;

/**
 * 描述: 饮食bean
 * 作者: LYD
 * 创建日期: 2020/6/22 9:12
 */
public class TestOfDietBean implements Serializable {
    //身高
    private String tgshengao;
    //体重
    private String tgtizhong;
    //年龄
    private String age;
    //空腹
    private String xtkongfu;
    //餐后
    private String xtcaihou;
    //糖化
    private String xthbalc;
    //高压
    private String systolic;
    //低压
    private String diastolic;
    //tc
    private String sytc;
    //tg
    private String sytg;
    //ldl
    private String syldl;
    //hdl
    private String syhdl;
    //劳动强度
    private String jbzhiye;
    //吸烟
    private String jbxiyan;
    //喝酒
    private String drink;
    //是否少数民族
    private String minorities;
    //是否 糖尿病肾病
    private String nephropathy;
    //糖尿病肾病 分期
    private String nephropathylei;
    //糖尿病肾病 确诊时间
    private String bf2time;
    private FoodsBean foods;

    public String getJbzhiye() {
        return jbzhiye;
    }

    public void setJbzhiye(String jbzhiye) {
        this.jbzhiye = jbzhiye;
    }

    public String getJbxiyan() {
        return jbxiyan;
    }

    public void setJbxiyan(String jbxiyan) {
        this.jbxiyan = jbxiyan;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getMinorities() {
        return minorities;
    }

    public void setMinorities(String minorities) {
        this.minorities = minorities;
    }


    //    public String getSmoke() {
    //        return smoke;
    //    }
    //
    //    public void setSmoke(String smoke) {
    //        this.smoke = smoke;
    //    }

    public String getTgshengao() {
        return tgshengao;
    }

    public void setTgshengao(String tgshengao) {
        this.tgshengao = tgshengao;
    }

    public String getTgtizhong() {
        return tgtizhong;
    }

    public void setTgtizhong(String tgtizhong) {
        this.tgtizhong = tgtizhong;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getXtcaihou() {
        return xtcaihou;
    }

    public void setXtcaihou(String xtcaihou) {
        this.xtcaihou = xtcaihou;
    }

    public String getXtkongfu() {
        return xtkongfu;
    }

    public void setXtkongfu(String xtkongfu) {
        this.xtkongfu = xtkongfu;
    }

    public String getXthbalc() {
        return xthbalc;
    }

    public void setXthbalc(String xthbalc) {
        this.xthbalc = xthbalc;
    }

    public String getSytc() {
        return sytc;
    }

    public void setSytc(String sytc) {
        this.sytc = sytc;
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

    public String getSyhdl() {
        return syhdl;
    }

    public void setSyhdl(String syhdl) {
        this.syhdl = syhdl;
    }

    public String getSyldl() {
        return syldl;
    }

    public void setSyldl(String syldl) {
        this.syldl = syldl;
    }

    public String getSytg() {
        return sytg;
    }

    public void setSytg(String sytg) {
        this.sytg = sytg;
    }

    public String getNephropathy() {
        return nephropathy;
    }

    public void setNephropathy(String nephropathy) {
        this.nephropathy = nephropathy;
    }

    public String getNephropathylei() {
        return nephropathylei;
    }

    public void setNephropathylei(String nephropathylei) {
        this.nephropathylei = nephropathylei;
    }

    public String getBf2time() {
        return bf2time;
    }

    public void setBf2time(String bf2time) {
        this.bf2time = bf2time;
    }

    public FoodsBean getFoods() {
        return foods;
    }

    public void setFoods(FoodsBean foods) {
        this.foods = foods;
    }

    public static class FoodsBean implements Serializable {
        private VapotatoBean vapotato;
        private String vegetables;
        private FruitsBean fruits;
        private SoyproductsBean soyproducts;
        private MeateggsBean meateggs;
        private MilksBean milks;
        private String oils;
        private NutsBean nuts;
        private WinesBean wines;

        public VapotatoBean getVapotato() {
            return vapotato;
        }

        public void setVapotato(VapotatoBean vapotato) {
            this.vapotato = vapotato;
        }

        public String getVegetables() {
            return vegetables;
        }

        public void setVegetables(String vegetables) {
            this.vegetables = vegetables;
        }

        public FruitsBean getFruits() {
            return fruits;
        }

        public void setFruits(FruitsBean fruits) {
            this.fruits = fruits;
        }

        public SoyproductsBean getSoyproducts() {
            return soyproducts;
        }

        public void setSoyproducts(SoyproductsBean soyproducts) {
            this.soyproducts = soyproducts;
        }

        public MeateggsBean getMeateggs() {
            return meateggs;
        }

        public void setMeateggs(MeateggsBean meateggs) {
            this.meateggs = meateggs;
        }

        public MilksBean getMilks() {
            return milks;
        }

        public void setMilks(MilksBean milks) {
            this.milks = milks;
        }

        public String getOils() {
            return oils;
        }

        public void setOils(String oils) {
            this.oils = oils;
        }

        public NutsBean getNuts() {
            return nuts;
        }

        public void setNuts(NutsBean nuts) {
            this.nuts = nuts;
        }

        public WinesBean getWines() {
            return wines;
        }

        public void setWines(WinesBean wines) {
            this.wines = wines;
        }


        public static class VapotatoBean implements Serializable {
            private String dami;
            private String mianfen;
            private String hunhe;
            private String guamian;
            private String lvdou;
            private String youbing;
            private String youtiao;
            private String malingshu;
            private String mantou;
            private String laobing;
            private String mianbing;
            private String shaobing;
            private String mianbao;
            private String wowo;
            private String miantiao;

            public String getDami() {
                return dami;
            }

            public void setDami(String dami) {
                this.dami = dami;
            }

            public String getMianfen() {
                return mianfen;
            }

            public void setMianfen(String mianfen) {
                this.mianfen = mianfen;
            }

            public String getHunhe() {
                return hunhe;
            }

            public void setHunhe(String hunhe) {
                this.hunhe = hunhe;
            }

            public String getGuamian() {
                return guamian;
            }

            public void setGuamian(String guamian) {
                this.guamian = guamian;
            }

            public String getLvdou() {
                return lvdou;
            }

            public void setLvdou(String lvdou) {
                this.lvdou = lvdou;
            }

            public String getYoubing() {
                return youbing;
            }

            public void setYoubing(String youbing) {
                this.youbing = youbing;
            }

            public String getYoutiao() {
                return youtiao;
            }

            public void setYoutiao(String youtiao) {
                this.youtiao = youtiao;
            }

            public String getMalingshu() {
                return malingshu;
            }

            public void setMalingshu(String malingshu) {
                this.malingshu = malingshu;
            }

            public String getMantou() {
                return mantou;
            }

            public void setMantou(String mantou) {
                this.mantou = mantou;
            }

            public String getLaobing() {
                return laobing;
            }

            public void setLaobing(String laobing) {
                this.laobing = laobing;
            }

            public String getMianbing() {
                return mianbing;
            }

            public void setMianbing(String mianbing) {
                this.mianbing = mianbing;
            }

            public String getShaobing() {
                return shaobing;
            }

            public void setShaobing(String shaobing) {
                this.shaobing = shaobing;
            }

            public String getMianbao() {
                return mianbao;
            }

            public void setMianbao(String mianbao) {
                this.mianbao = mianbao;
            }

            public String getWowo() {
                return wowo;
            }

            public void setWowo(String wowo) {
                this.wowo = wowo;
            }

            public String getMiantiao() {
                return miantiao;
            }

            public void setMiantiao(String miantiao) {
                this.miantiao = miantiao;
            }
        }

        public static class FruitsBean implements Serializable {
            private String xing;
            private String li;
            private String jvzi;
            private String chengzi;
            private String youzi;
            private String tao;
            private String putao;
            private String pingguo;
            private String mihoutao;
            private String shi;
            private String xiangjiao;
            private String lizhi;
            private String xigua;

            public String getXing() {
                return xing;
            }

            public void setXing(String xing) {
                this.xing = xing;
            }

            public String getLi() {
                return li;
            }

            public void setLi(String li) {
                this.li = li;
            }

            public String getJvzi() {
                return jvzi;
            }

            public void setJvzi(String jvzi) {
                this.jvzi = jvzi;
            }

            public String getChengzi() {
                return chengzi;
            }

            public void setChengzi(String chengzi) {
                this.chengzi = chengzi;
            }

            public String getYouzi() {
                return youzi;
            }

            public void setYouzi(String youzi) {
                this.youzi = youzi;
            }

            public String getTao() {
                return tao;
            }

            public void setTao(String tao) {
                this.tao = tao;
            }

            public String getPutao() {
                return putao;
            }

            public void setPutao(String putao) {
                this.putao = putao;
            }

            public String getPingguo() {
                return pingguo;
            }

            public void setPingguo(String pingguo) {
                this.pingguo = pingguo;
            }

            public String getMihoutao() {
                return mihoutao;
            }

            public void setMihoutao(String mihoutao) {
                this.mihoutao = mihoutao;
            }

            public String getShi() {
                return shi;
            }

            public void setShi(String shi) {
                this.shi = shi;
            }

            public String getXiangjiao() {
                return xiangjiao;
            }

            public void setXiangjiao(String xiangjiao) {
                this.xiangjiao = xiangjiao;
            }

            public String getLizhi() {
                return lizhi;
            }

            public void setLizhi(String lizhi) {
                this.lizhi = lizhi;
            }

            public String getXigua() {
                return xigua;
            }

            public void setXigua(String xigua) {
                this.xigua = xigua;
            }
        }

        public static class SoyproductsBean implements Serializable {
            private String fuzhu;
            private String beidoufu;
            private String nandoufu;
            private String doujiang;

            public String getFuzhu() {
                return fuzhu;
            }

            public void setFuzhu(String fuzhu) {
                this.fuzhu = fuzhu;
            }

            public String getBeidoufu() {
                return beidoufu;
            }

            public void setBeidoufu(String beidoufu) {
                this.beidoufu = beidoufu;
            }

            public String getNandoufu() {
                return nandoufu;
            }

            public void setNandoufu(String nandoufu) {
                this.nandoufu = nandoufu;
            }

            public String getDoujiang() {
                return doujiang;
            }

            public void setDoujiang(String doujiang) {
                this.doujiang = doujiang;
            }
        }

        public static class MeateggsBean implements Serializable {
            private String shouzhurou;
            private String niurou;
            private String yangrou;
            private String yarou;
            private String paigu;
            private String jidan;
            private String yadan;
            private String anchun;
            private String xia;
            private String yu;

            public String getShouzhurou() {
                return shouzhurou;
            }

            public void setShouzhurou(String shouzhurou) {
                this.shouzhurou = shouzhurou;
            }

            public String getNiurou() {
                return niurou;
            }

            public void setNiurou(String niurou) {
                this.niurou = niurou;
            }

            public String getYangrou() {
                return yangrou;
            }

            public void setYangrou(String yangrou) {
                this.yangrou = yangrou;
            }

            public String getYarou() {
                return yarou;
            }

            public void setYarou(String yarou) {
                this.yarou = yarou;
            }

            public String getPaigu() {
                return paigu;
            }

            public void setPaigu(String paigu) {
                this.paigu = paigu;
            }

            public String getJidan() {
                return jidan;
            }

            public void setJidan(String jidan) {
                this.jidan = jidan;
            }

            public String getYadan() {
                return yadan;
            }

            public void setYadan(String yadan) {
                this.yadan = yadan;
            }

            public String getAnchun() {
                return anchun;
            }

            public void setAnchun(String anchun) {
                this.anchun = anchun;
            }

            public String getXia() {
                return xia;
            }

            public void setXia(String xia) {
                this.xia = xia;
            }

            public String getYu() {
                return yu;
            }

            public void setYu(String yu) {
                this.yu = yu;
            }
        }

        public static class MilksBean implements Serializable {
            private String naifen;
            private String niunai;
            private String yangnai;
            private String qita;

            public String getNaifen() {
                return naifen;
            }

            public void setNaifen(String naifen) {
                this.naifen = naifen;
            }

            public String getNiunai() {
                return niunai;
            }

            public void setNiunai(String niunai) {
                this.niunai = niunai;
            }

            public String getYangnai() {
                return yangnai;
            }

            public void setYangnai(String yangnai) {
                this.yangnai = yangnai;
            }

            public String getQita() {
                return qita;
            }

            public void setQita(String qita) {
                this.qita = qita;
            }
        }

        public static class NutsBean implements Serializable {
            private String xingren;
            private String huashengmi;
            private String hetao;
            private String nutqita;

            public String getXingren() {
                return xingren;
            }

            public void setXingren(String xingren) {
                this.xingren = xingren;
            }

            public String getHuashengmi() {
                return huashengmi;
            }

            public void setHuashengmi(String huashengmi) {
                this.huashengmi = huashengmi;
            }

            public String getHetao() {
                return hetao;
            }

            public void setHetao(String hetao) {
                this.hetao = hetao;
            }

            public String getNutqita() {
                return nutqita;
            }

            public void setNutqita(String nutqita) {
                this.nutqita = nutqita;
            }
        }

        public static class WinesBean implements Serializable {
            private String hongjiu;
            private String pijiu;
            private String baijiu;
            private String huangjiu;
            private String winenum;

            public String getHongjiu() {
                return hongjiu;
            }

            public void setHongjiu(String hongjiu) {
                this.hongjiu = hongjiu;
            }

            public String getPijiu() {
                return pijiu;
            }

            public void setPijiu(String pijiu) {
                this.pijiu = pijiu;
            }

            public String getBaijiu() {
                return baijiu;
            }

            public void setBaijiu(String baijiu) {
                this.baijiu = baijiu;
            }

            public String getHuangjiu() {
                return huangjiu;
            }

            public void setHuangjiu(String huangjiu) {
                this.huangjiu = huangjiu;
            }

            public String getWinenum() {
                return winenum;
            }

            public void setWinenum(String winenum) {
                this.winenum = winenum;
            }
        }
    }
}
