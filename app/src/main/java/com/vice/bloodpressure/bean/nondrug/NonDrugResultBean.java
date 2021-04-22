package com.vice.bloodpressure.bean.nondrug;

public class NonDrugResultBean {
    private String userid;
    //饮食
    private DietaryBean dietary;
    //运动
    private SportsBean sports;
    //检测
    private String monitor;
    //教育
    private String education;
    //并发症
    private ComplicationsBean complications;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public DietaryBean getDietary() {
        return dietary;
    }

    public void setDietary(DietaryBean dietary) {
        this.dietary = dietary;
    }

    public SportsBean getSports() {
        return sports;
    }

    public void setSports(SportsBean sports) {
        this.sports = sports;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public ComplicationsBean getComplications() {
        return complications;
    }

    public void setComplications(ComplicationsBean complications) {
        this.complications = complications;
    }

    public static class DietaryBean {
        private String tgshengao;
        private String tgtizhong;
        private String age;
        private String waistline;
        private String jbzhiye;
        private String sssh;
        private String smoke;
        private String syacr;
        private String syxqjg;
        private String syxqgfr;
        private String xtcaihou;
        private String xtkongfu;
        private String xthbalc;
        private String sytc;
        private String systolic;
        private String diastolic;
        private String syhdl;
        private String syldl;
        private String sytg;
        private String nephropathy;
        private String nephropathylei;
        private String bf2time;
        private String xtpg;
        private String bzjhys;
        private String xtpgdian;
        private String jiacanis;
        private String jiacanci;
        private String jiacansj1;
        private String jiacansj2;
        private String jiacansj3;
        private FoodsBean foods;

        public String getSmoke() {
            return smoke;
        }

        public void setSmoke(String smoke) {
            this.smoke = smoke;
        }

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

        public String getWaistline() {
            return waistline;
        }

        public void setWaistline(String waistline) {
            this.waistline = waistline;
        }

        public String getJbzhiye() {
            return jbzhiye;
        }

        public void setJbzhiye(String jbzhiye) {
            this.jbzhiye = jbzhiye;
        }

        public String getSssh() {
            return sssh;
        }

        public void setSssh(String sssh) {
            this.sssh = sssh;
        }

        public String getSyacr() {
            return syacr;
        }

        public void setSyacr(String syacr) {
            this.syacr = syacr;
        }

        public String getSyxqjg() {
            return syxqjg;
        }

        public void setSyxqjg(String syxqjg) {
            this.syxqjg = syxqjg;
        }

        public String getSyxqgfr() {
            return syxqgfr;
        }

        public void setSyxqgfr(String syxqgfr) {
            this.syxqgfr = syxqgfr;
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

        public String getXtpg() {
            return xtpg;
        }

        public void setXtpg(String xtpg) {
            this.xtpg = xtpg;
        }

        public String getBzjhys() {
            return bzjhys;
        }

        public void setBzjhys(String bzjhys) {
            this.bzjhys = bzjhys;
        }

        public String getXtpgdian() {
            return xtpgdian;
        }

        public void setXtpgdian(String xtpgdian) {
            this.xtpgdian = xtpgdian;
        }

        public String getJiacanis() {
            return jiacanis;
        }

        public void setJiacanis(String jiacanis) {
            this.jiacanis = jiacanis;
        }

        public String getJiacanci() {
            return jiacanci;
        }

        public void setJiacanci(String jiacanci) {
            this.jiacanci = jiacanci;
        }

        public String getJiacansj1() {
            return jiacansj1;
        }

        public void setJiacansj1(String jiacansj1) {
            this.jiacansj1 = jiacansj1;
        }

        public String getJiacansj2() {
            return jiacansj2;
        }

        public void setJiacansj2(String jiacansj2) {
            this.jiacansj2 = jiacansj2;
        }

        public String getJiacansj3() {
            return jiacansj3;
        }

        public void setJiacansj3(String jiacansj3) {
            this.jiacansj3 = jiacansj3;
        }

        public FoodsBean getFoods() {
            return foods;
        }

        public void setFoods(FoodsBean foods) {
            this.foods = foods;
        }

        public static class FoodsBean {
            /**
             * vapotato : {"dami":70,"mianfen":100}
             * vegetables : 500
             * fruits : {"xing":100,"jvzi":100}
             * soyproducts : {"fuzhu":100,"beidoufu":100}
             * meateggs : {"shouzhurou":100,"niurou":100}
             * milks : {"naifen":100,"niunai":100}
             * oils : 100
             * nuts : {"xingren":100,"huashengmi":100}
             * wines : {"hongjiu":100,"winenum":2}
             */
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


            public static class VapotatoBean {
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

            public static class FruitsBean {
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

            public static class SoyproductsBean {
                /**
                 * fuzhu : 100
                 * beidoufu : 100
                 */

                //                腐竹 fuzhu 7
                //                北豆腐 beidoufu 8
                //                南豆腐 nandoufu 8
                //                豆浆 doujiang 8

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

            public static class MeateggsBean {
                /**
                 * shouzhurou : 100
                 * niurou : 100
                 */

                //                瘦猪肉 shouzhurou 9
                //                牛肉 niurou 9
                //                羊肉 yangrou 9
                //                鸭肉 yarou 9
                //                带骨排骨 paigu 9
                //                鸡蛋（一大个带壳） jidan 10
                //                鸭蛋、松花蛋（一大个带壳）yadan 10
                //                鹌鹑蛋（六个带壳） anchun 10
                //                虾类 xia 11
                //                鱼类 yu 11

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

            public static class MilksBean {
                /**
                 * naifen : 100
                 * niunai : 100
                 */

                //                奶粉：naifen 12
                //                牛奶 niunai 13
                //                羊奶 yangnai 13
                //                其他 qita 13

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

            public static class NutsBean {
                /**
                 * xingren : 100
                 * huashengmi : 100
                 */

                //                杏仁 xingren 15
                //                花生米 huashengmi 15
                //                核桃 hetao 15
                //                其他 nutqita 15

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

            public static class WinesBean {
                /**
                 * hongjiu : 100
                 * winenum : 2
                 */
                //                红酒 hongjiu 16
                //                啤酒 pijiu 16
                //                白酒 baijiu 16
                //                黄酒 huangjiu 16
                //                酒次数 winenum
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

    public static class SportsBean {
        /**
         * ydbu : 1
         * ydzhuan : 1
         */
        private String ydbu;
        private String ydzhuan;
        private String steps;
        private String kong;
        private String ydcitime;
        private String ydzhouci;

        private String ydls1;
        private String ydls2;
        private String ydls3;
        private String ydls4;
        private String ydls5;
        private String ydls6;
        private String ydls7;
        private String bf10;
        private String ydls8;
        private String ydls9;
        private String ydls10;

        private String ydls11;
        private String ydls12;
        private String ydls13;
        private String ydls14;
        private String ydls15;

        public String getBf10() {
            return bf10;
        }

        public void setBf10(String bf10) {
            this.bf10 = bf10;
        }

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }

        public String getKong() {
            return kong;
        }

        public void setKong(String kong) {
            this.kong = kong;
        }

        public String getYdcitime() {
            return ydcitime;
        }

        public void setYdcitime(String ydcitime) {
            this.ydcitime = ydcitime;
        }

        public String getYdzhouci() {
            return ydzhouci;
        }

        public void setYdzhouci(String ydzhouci) {
            this.ydzhouci = ydzhouci;
        }

        public String getYdls1() {
            return ydls1;
        }

        public void setYdls1(String ydls1) {
            this.ydls1 = ydls1;
        }

        public String getYdls2() {
            return ydls2;
        }

        public void setYdls2(String ydls2) {
            this.ydls2 = ydls2;
        }

        public String getYdls3() {
            return ydls3;
        }

        public void setYdls3(String ydls3) {
            this.ydls3 = ydls3;
        }

        public String getYdls4() {
            return ydls4;
        }

        public void setYdls4(String ydls4) {
            this.ydls4 = ydls4;
        }

        public String getYdls5() {
            return ydls5;
        }

        public void setYdls5(String ydls5) {
            this.ydls5 = ydls5;
        }

        public String getYdls6() {
            return ydls6;
        }

        public void setYdls6(String ydls6) {
            this.ydls6 = ydls6;
        }

        public String getYdls7() {
            return ydls7;
        }

        public void setYdls7(String ydls7) {
            this.ydls7 = ydls7;
        }

        public String getYdls8() {
            return ydls8;
        }

        public void setYdls8(String ydls8) {
            this.ydls8 = ydls8;
        }

        public String getYdls9() {
            return ydls9;
        }

        public void setYdls9(String ydls9) {
            this.ydls9 = ydls9;
        }

        public String getYdls10() {
            return ydls10;
        }

        public void setYdls10(String ydls10) {
            this.ydls10 = ydls10;
        }

        public String getYdls11() {
            return ydls11;
        }

        public void setYdls11(String ydls11) {
            this.ydls11 = ydls11;
        }

        public String getYdls12() {
            return ydls12;
        }

        public void setYdls12(String ydls12) {
            this.ydls12 = ydls12;
        }

        public String getYdls13() {
            return ydls13;
        }

        public void setYdls13(String ydls13) {
            this.ydls13 = ydls13;
        }

        public String getYdls14() {
            return ydls14;
        }

        public void setYdls14(String ydls14) {
            this.ydls14 = ydls14;
        }

        public String getYdls15() {
            return ydls15;
        }

        public void setYdls15(String ydls15) {
            this.ydls15 = ydls15;
        }

        public String getYdbu() {
            return ydbu;
        }

        public void setYdbu(String ydbu) {
            this.ydbu = ydbu;
        }

        public String getYdzhuan() {
            return ydzhuan;
        }

        public void setYdzhuan(String ydzhuan) {
            this.ydzhuan = ydzhuan;
        }
    }

    public static class ComplicationsBean {
        /**
         * bf3 : 1
         * bf32 : 1
         */
        private String bf1;
        private String nephropathylei;
        private String bf1z3;
        private String bf1time;
        private String bf3;
        private String bf32;
        private String bf33;
        private String bf3time;
        private String bf4;
        private String bf41;
        private String bf42;
        private String bf43;
        private String bf44;
        private String bf45;
        private String bf4time;
        private String bf9;
        private String bf91;
        private String bf92;
        private String bf93;
        private String bf94;
        private String bf95;
        private String bf96;
        private String bf9time;
        private String jw1;
        private String jw1time;
        private String jw2;
        private String jw2time;
        private String jw3;
        private String jw3time;
        private String jw4;
        private String jw4time;
        private String jbxiyan;
        private String jbtanglei;
        private String jbquetime;

        public String getJw1time() {
            return jw1time;
        }

        public void setJw1time(String jw1time) {
            this.jw1time = jw1time;
        }

        public String getBf1() {
            return bf1;
        }

        public void setBf1(String bf1) {
            this.bf1 = bf1;
        }

        public String getNephropathylei() {
            return nephropathylei;
        }

        public void setNephropathylei(String nephropathylei) {
            this.nephropathylei = nephropathylei;
        }

        public String getBf1z3() {
            return bf1z3;
        }

        public void setBf1z3(String bf1z3) {
            this.bf1z3 = bf1z3;
        }

        public String getBf1time() {
            return bf1time;
        }

        public void setBf1time(String bf1time) {
            this.bf1time = bf1time;
        }

        public String getBf3() {
            return bf3;
        }

        public void setBf3(String bf3) {
            this.bf3 = bf3;
        }

        public String getBf32() {
            return bf32;
        }

        public void setBf32(String bf32) {
            this.bf32 = bf32;
        }

        public String getBf33() {
            return bf33;
        }

        public void setBf33(String bf33) {
            this.bf33 = bf33;
        }

        public String getBf3time() {
            return bf3time;
        }

        public void setBf3time(String bf3time) {
            this.bf3time = bf3time;
        }

        public String getBf4() {
            return bf4;
        }

        public void setBf4(String bf4) {
            this.bf4 = bf4;
        }

        public String getBf41() {
            return bf41;
        }

        public void setBf41(String bf41) {
            this.bf41 = bf41;
        }

        public String getBf42() {
            return bf42;
        }

        public void setBf42(String bf42) {
            this.bf42 = bf42;
        }

        public String getBf43() {
            return bf43;
        }

        public void setBf43(String bf43) {
            this.bf43 = bf43;
        }

        public String getBf44() {
            return bf44;
        }

        public void setBf44(String bf44) {
            this.bf44 = bf44;
        }

        public String getBf45() {
            return bf45;
        }

        public void setBf45(String bf45) {
            this.bf45 = bf45;
        }

        public String getBf4time() {
            return bf4time;
        }

        public void setBf4time(String bf4time) {
            this.bf4time = bf4time;
        }

        public String getBf9() {
            return bf9;
        }

        public void setBf9(String bf9) {
            this.bf9 = bf9;
        }

        public String getBf91() {
            return bf91;
        }

        public void setBf91(String bf91) {
            this.bf91 = bf91;
        }

        public String getBf92() {
            return bf92;
        }

        public void setBf92(String bf92) {
            this.bf92 = bf92;
        }

        public String getBf93() {
            return bf93;
        }

        public void setBf93(String bf93) {
            this.bf93 = bf93;
        }

        public String getBf94() {
            return bf94;
        }

        public void setBf94(String bf94) {
            this.bf94 = bf94;
        }

        public String getBf95() {
            return bf95;
        }

        public void setBf95(String bf95) {
            this.bf95 = bf95;
        }

        public String getBf96() {
            return bf96;
        }

        public void setBf96(String bf96) {
            this.bf96 = bf96;
        }

        public String getBf9time() {
            return bf9time;
        }

        public void setBf9time(String bf9time) {
            this.bf9time = bf9time;
        }

        public String getJw1() {
            return jw1;
        }

        public void setJw1(String jw1) {
            this.jw1 = jw1;
        }

        public String getJw2() {
            return jw2;
        }

        public void setJw2(String jw2) {
            this.jw2 = jw2;
        }

        public String getJw2time() {
            return jw2time;
        }

        public void setJw2time(String jw2time) {
            this.jw2time = jw2time;
        }

        public String getJw3() {
            return jw3;
        }

        public void setJw3(String jw3) {
            this.jw3 = jw3;
        }

        public String getJw3time() {
            return jw3time;
        }

        public void setJw3time(String jw3time) {
            this.jw3time = jw3time;
        }

        public String getJw4() {
            return jw4;
        }

        public void setJw4(String jw4) {
            this.jw4 = jw4;
        }

        public String getJw4time() {
            return jw4time;
        }

        public void setJw4time(String jw4time) {
            this.jw4time = jw4time;
        }

        public String getJbxiyan() {
            return jbxiyan;
        }

        public void setJbxiyan(String jbxiyan) {
            this.jbxiyan = jbxiyan;
        }

        public String getJbtanglei() {
            return jbtanglei;
        }

        public void setJbtanglei(String jbtanglei) {
            this.jbtanglei = jbtanglei;
        }

        public String getJbquetime() {
            return jbquetime;
        }

        public void setJbquetime(String jbquetime) {
            this.jbquetime = jbquetime;
        }
    }
}
