package com.vice.bloodpressure.bean;

import android.text.TextUtils;

public class PersonalRecordBean {


    /**
     * id : 434916
     * userid : 442690
     * nickname : 张三
     * sex : 1
     * age : 0
     * birthtime : 1551801600
     * duju : 2
     * diabeteslei : 3
     * diabetesleitime : 1548864000
     * smoke : 1
     * drink : 1
     * culture : 4
     * profession : 3
     * marriage : 2
     * tel : 13216071373
     * bedrid : 2
     * jbprov : null
     * jbcity : null
     * jbdist : null
     * address : 喜欢花枝招展
     * email : null
     * minzu : 半组
     * zhifufangshi : 1
     * idcard : 410783
     * jzkahao : 就斤斤计较
     * gxyzhenduan : 2
     * gxytime : 1551801600
     * gestation : 1
     * gestationtime : 1143388800
     * weight : 123
     * height : 踢正步
     * waistline : 哈哈哈
     * hipline : 哈哈哈
     * systolic : 收获
     * diastolic : 助战好呀
     * heartrate : 惊喜
     * MedicareCardID : null
     * AgriculturalCardID : null
     * diabetes : 1
     * angiocarpy : 1
     * uptime : 1553671340
     * imei :
     * petname : null
     * nativeplace : 哈哈哈
     * xthbalc : 4
     * xtogtt2h : 3
     * xtsuiji : 5
     * xtkongfu : 6
     * xtcaihou : 8
     * xtyejian : null
     * xtshuiqian : 9
     * xtdi : null
     * xtcishuo : null
     * sytc : 5
     * sytg : 6
     * syldl : 7
     * syhdl : 11
     * syalt : 8
     * syast : 10
     * sytbil : null
     * syalp : 12
     * syncg :
     * syuae : 16
     * syhuae : 14
     * syacr : 15
     * syxqjg : 19
     * syxqjgg : null
     * synsjg : 18
     * synsd : 17
     * syxqgfr : null
     * nephropathy : 1
     * nephropathylei : null
     * nephropathybing : null
     * nephropathytime : null
     * retina : 1
     * retinaxue : null
     * retinabing : null
     * retinatime : null
     * nerve : 1
     * nerve1 : null
     * nerve2 : null
     * nerve3 : null
     * nerve4 : null
     * nerve5 : null
     * nervetime : null
     * legs : 1
     * legs1 : null
     * legs2 : null
     * legs3 : null
     * legs4 : null
     * legs5 : null
     * legs6 : null
     * legstime : null
     * diabeticfoot : 1
     * diabeticfootji : null
     * diabeticfoottime : null
     * ketoacidosis : 1
     * ketoacidosistime : null
     * hypertonic : 1
     * hypertonictime : null
     * highblood : null
     * highbloodtime : null
     * hlp : null
     * hlptime : null
     * coronary : null
     * coronarytime : null
     * cerebrovascular : 1
     * cerebrovasculartime : null
     */

    private int id;
    private int userid;
    private String nickname;
    private int sex;
    private int age;
    private long birthtime;
    private int duju;
    private int diabeteslei;
    private long diabetesleitime;
    private int smoke;
    private int drink;
    private int culture;
    private int profession;
    private int marriage;
    private String tel;
    private int bedrid;
    private String jbprov;
    private String jbcity;
    private String jbdist;
    private String address;
    private String email;
    private String minzu;
    private int zhifufangshi;
    private String idcard;
    private String jzkahao;
    private int gxyzhenduan;
    private long gxytime;
    private int gestation;
    private long gestationtime;
    private String weight;
    private String height;
    private String waistline;
    private String hipline;
    private String systolic;
    private String diastolic;
    private String heartrate;
    private String MedicareCardID;
    private String AgriculturalCardID;
    private int diabetes;
    private int angiocarpy;
    private long uptime;
    private String imei;
    private String petname;
    private String nativeplace;
    private String xthbalc;
    private String xtogtt2h;
    private String xtsuiji;
    private String xtkongfu;
    private String xtcaihou;
    private String xtyejian;
    private String xtshuiqian;
    private String xtdi;
    private String xtcishuo;
    private String sytc;
    private String sytg;
    private String syldl;
    private String syhdl;
    private String syalt;
    private String syast;
    private String sytbil;
    private String syalp;
    private String syncg;
    private String syuae;
    private String syhuae;
    private String syacr;
    private String syxqjg;
    private String syxqjgg;
    private String synsjg;
    private String synsd;
    private String syxqgfr;
    private int nephropathy;
    private String nephropathylei;
    private String nephropathybing;
    private long nephropathytime;
    private int retina;
    private String retinaxue;
    private String retinabing;
    private long retinatime;
    private int nerve;
    private String nerve1;
    private String nerve2;
    private String nerve3;
    private String nerve4;
    private String nerve5;
    private long nervetime;
    private int legs;
    private String legs1;
    private String legs2;
    private String legs3;
    private String legs4;
    private String legs5;
    private String legs6;
    private long legstime;
    private int diabeticfoot;
    private String diabeticfootji;
    private long diabeticfoottime;
    private int ketoacidosis;
    private long ketoacidosistime;
    private int hypertonic;
    private long hypertonictime;
    private int highblood;
    private long highbloodtime;
    private String hlp;
    private long hlptime;
    private String coronary;
    private long coronarytime;
    private int cerebrovascular;
    private long cerebrovasculartime;
    private String picture;
    private String guid;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(long birthtime) {
        this.birthtime = birthtime;
    }

    public int getDuju() {
        return duju;
    }

    public void setDuju(int duju) {
        this.duju = duju;
    }

    public int getDiabeteslei() {
        return diabeteslei;
    }

    public void setDiabeteslei(int diabeteslei) {
        this.diabeteslei = diabeteslei;
    }

    public int getSmoke() {
        return smoke;
    }

    public void setSmoke(int smoke) {
        this.smoke = smoke;
    }

    public int getDrink() {
        return drink;
    }

    public void setDrink(int drink) {
        this.drink = drink;
    }

    public int getCulture() {
        return culture;
    }

    public void setCulture(int culture) {
        this.culture = culture;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public int getMarriage() {
        return marriage;
    }

    public void setMarriage(int marriage) {
        this.marriage = marriage;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getBedrid() {
        return bedrid;
    }

    public void setBedrid(int bedrid) {
        this.bedrid = bedrid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public int getZhifufangshi() {
        return zhifufangshi;
    }

    public void setZhifufangshi(int zhifufangshi) {
        this.zhifufangshi = zhifufangshi;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getJzkahao() {
        return jzkahao;
    }

    public void setJzkahao(String jzkahao) {
        this.jzkahao = jzkahao;
    }

    public int getGxyzhenduan() {
        return gxyzhenduan;
    }

    public void setGxyzhenduan(int gxyzhenduan) {
        this.gxyzhenduan = gxyzhenduan;
    }

    public int getGestation() {
        return gestation;
    }

    public void setGestation(int gestation) {
        this.gestation = gestation;
    }

    public String getWeight() {
        return isNull(weight);
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return isNull(height);
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWaistline() {
        return isNull(waistline);
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getHipline() {
        return isNull(hipline);
    }

    public void setHipline(String hipline) {
        this.hipline = hipline;
    }

    public String getSystolic() {
        return isNull(systolic);
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return isNull(diastolic);
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getHeartrate() {
        return isNull(heartrate);
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public int getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(int diabetes) {
        this.diabetes = diabetes;
    }

    public int getAngiocarpy() {
        return angiocarpy;
    }

    public void setAngiocarpy(int angiocarpy) {
        this.angiocarpy = angiocarpy;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    public String getXthbalc() {
        return isNull(xthbalc);
    }

    public void setXthbalc(String xthbalc) {
        this.xthbalc = xthbalc;
    }

    public String getXtogtt2h() {
        return isNull(xtogtt2h);
    }

    public void setXtogtt2h(String xtogtt2h) {
        this.xtogtt2h = xtogtt2h;
    }

    public String getXtsuiji() {
        return isNull(xtsuiji);
    }

    public void setXtsuiji(String xtsuiji) {
        this.xtsuiji = xtsuiji;
    }

    public String getXtkongfu() {
        return isNull(xtkongfu);
    }

    public void setXtkongfu(String xtkongfu) {
        this.xtkongfu = xtkongfu;
    }

    public String getXtcaihou() {
        return isNull(xtcaihou);
    }

    public void setXtcaihou(String xtcaihou) {
        this.xtcaihou = xtcaihou;
    }

    public String getXtyejian() {
        return isNull(xtyejian);
    }

    public void setXtyejian(String xtyejian) {
        this.xtyejian = xtyejian;
    }

    public String getXtshuiqian() {
        return isNull(xtshuiqian);
    }

    public void setXtshuiqian(String xtshuiqian) {
        this.xtshuiqian = xtshuiqian;
    }

    public String getSytc() {
        return isNull(sytc);
    }

    public void setSytc(String sytc) {
        this.sytc = sytc;
    }

    public String getSytg() {
        return isNull(sytg);
    }

    public void setSytg(String sytg) {
        this.sytg = sytg;
    }

    public String getSyldl() {
        return isNull(syldl);
    }

    public void setSyldl(String syldl) {
        this.syldl = syldl;
    }

    public String getSyhdl() {
        return isNull(syhdl);
    }

    public void setSyhdl(String syhdl) {
        this.syhdl = syhdl;
    }

    public String getSyalt() {
        return isNull(syalt);
    }

    public void setSyalt(String syalt) {
        this.syalt = syalt;
    }

    public String getSyast() {
        return isNull(syast);
    }

    public void setSyast(String syast) {
        this.syast = syast;
    }

    public String getSyalp() {
        return isNull(syalp);
    }

    public void setSyalp(String syalp) {
        this.syalp = syalp;
    }

    public String getSyncg() {
        return isNull(syncg);
    }

    public void setSyncg(String syncg) {
        this.syncg = syncg;
    }

    public String getSyuae() {
        return isNull(syuae);
    }

    public void setSyuae(String syuae) {
        this.syuae = syuae;
    }

    public String getSyhuae() {
        return isNull(syhuae);
    }

    public void setSyhuae(String syhuae) {
        this.syhuae = syhuae;
    }

    public String getSyacr() {
        return isNull(syacr);
    }

    public void setSyacr(String syacr) {
        this.syacr = syacr;
    }

    public String getSyxqjg() {
        return isNull(syxqjg);
    }

    public void setSyxqjg(String syxqjg) {
        this.syxqjg = syxqjg;
    }

    public String getSynsjg() {
        return isNull(synsjg);
    }

    public void setSynsjg(String synsjg) {
        this.synsjg = synsjg;
    }

    public String getSynsd() {
        return isNull(synsd);
    }

    public void setSynsd(String synsd) {
        this.synsd = synsd;
    }

    public int getNephropathy() {
        return nephropathy;
    }

    public void setNephropathy(int nephropathy) {
        this.nephropathy = nephropathy;
    }

    public int getRetina() {
        return retina;
    }

    public void setRetina(int retina) {
        this.retina = retina;
    }

    public int getNerve() {
        return nerve;
    }

    public void setNerve(int nerve) {
        this.nerve = nerve;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public int getDiabeticfoot() {
        return diabeticfoot;
    }

    public void setDiabeticfoot(int diabeticfoot) {
        this.diabeticfoot = diabeticfoot;
    }

    public int getKetoacidosis() {
        return ketoacidosis;
    }

    public void setKetoacidosis(int ketoacidosis) {
        this.ketoacidosis = ketoacidosis;
    }

    public int getHypertonic() {
        return hypertonic;
    }

    public void setHypertonic(int hypertonic) {
        this.hypertonic = hypertonic;
    }

    public int getCerebrovascular() {
        return cerebrovascular;
    }

    public void setCerebrovascular(int cerebrovascular) {
        this.cerebrovascular = cerebrovascular;
    }

    public long getDiabetesleitime() {
        return diabetesleitime;
    }

    public void setDiabetesleitime(long diabetesleitime) {
        this.diabetesleitime = diabetesleitime;
    }

    public long getGxytime() {
        return gxytime;
    }

    public void setGxytime(long gxytime) {
        this.gxytime = gxytime;
    }

    public long getGestationtime() {
        return gestationtime;
    }

    public void setGestationtime(long gestationtime) {
        this.gestationtime = gestationtime;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public long getNephropathytime() {
        return nephropathytime;
    }

    public void setNephropathytime(long nephropathytime) {
        this.nephropathytime = nephropathytime;
    }

    public long getRetinatime() {
        return retinatime;
    }

    public void setRetinatime(long retinatime) {
        this.retinatime = retinatime;
    }

    public long getNervetime() {
        return nervetime;
    }

    public void setNervetime(long nervetime) {
        this.nervetime = nervetime;
    }

    public long getLegstime() {
        return legstime;
    }

    public void setLegstime(long legstime) {
        this.legstime = legstime;
    }

    public long getDiabeticfoottime() {
        return diabeticfoottime;
    }

    public void setDiabeticfoottime(long diabeticfoottime) {
        this.diabeticfoottime = diabeticfoottime;
    }

    public long getKetoacidosistime() {
        return ketoacidosistime;
    }

    public void setKetoacidosistime(long ketoacidosistime) {
        this.ketoacidosistime = ketoacidosistime;
    }

    public long getHypertonictime() {
        return hypertonictime;
    }

    public void setHypertonictime(long hypertonictime) {
        this.hypertonictime = hypertonictime;
    }

    public long getHighbloodtime() {
        return highbloodtime;
    }

    public void setHighbloodtime(long highbloodtime) {
        this.highbloodtime = highbloodtime;
    }

    public long getHlptime() {
        return hlptime;
    }

    public void setHlptime(long hlptime) {
        this.hlptime = hlptime;
    }

    public long getCoronarytime() {
        return coronarytime;
    }

    public void setCoronarytime(long coronarytime) {
        this.coronarytime = coronarytime;
    }

    public long getCerebrovasculartime() {
        return cerebrovasculartime;
    }

    public void setCerebrovasculartime(long cerebrovasculartime) {
        this.cerebrovasculartime = cerebrovasculartime;
    }

    public String getJbprov() {
        return jbprov;
    }

    public void setJbprov(String jbprov) {
        this.jbprov = jbprov;
    }

    public String getJbcity() {
        return jbcity;
    }

    public void setJbcity(String jbcity) {
        this.jbcity = jbcity;
    }

    public String getJbdist() {
        return jbdist;
    }

    public void setJbdist(String jbdist) {
        this.jbdist = jbdist;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedicareCardID() {
        return MedicareCardID;
    }

    public void setMedicareCardID(String medicareCardID) {
        MedicareCardID = medicareCardID;
    }

    public String getAgriculturalCardID() {
        return AgriculturalCardID;
    }

    public void setAgriculturalCardID(String agriculturalCardID) {
        AgriculturalCardID = agriculturalCardID;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getXtdi() {
        return isNull(xtdi);
    }

    public void setXtdi(String xtdi) {
        this.xtdi = xtdi;
    }

    public String getXtcishuo() {
        return xtcishuo;
    }

    public void setXtcishuo(String xtcishuo) {
        this.xtcishuo = xtcishuo;
    }

    public String getSytbil() {
        return isNull(sytbil);
    }

    public void setSytbil(String sytbil) {
        this.sytbil = sytbil;
    }

    public String getSyxqjgg() {
        return isNull(syxqjgg);
    }

    public void setSyxqjgg(String syxqjgg) {
        this.syxqjgg = syxqjgg;
    }

    public String getSyxqgfr() {
        return syxqgfr;
    }

    public void setSyxqgfr(String syxqgfr) {
        this.syxqgfr = syxqgfr;
    }

    public String getNephropathylei() {
        return nephropathylei;
    }

    public void setNephropathylei(String nephropathylei) {
        this.nephropathylei = nephropathylei;
    }

    public String getNephropathybing() {
        return nephropathybing;
    }

    public void setNephropathybing(String nephropathybing) {
        this.nephropathybing = nephropathybing;
    }

    public String getRetinaxue() {
        return retinaxue;
    }

    public void setRetinaxue(String retinaxue) {
        this.retinaxue = retinaxue;
    }

    public String getRetinabing() {
        return retinabing;
    }

    public void setRetinabing(String retinabing) {
        this.retinabing = retinabing;
    }

    public String getNerve1() {
        return nerve1;
    }

    public void setNerve1(String nerve1) {
        this.nerve1 = nerve1;
    }

    public String getNerve2() {
        return nerve2;
    }

    public void setNerve2(String nerve2) {
        this.nerve2 = nerve2;
    }

    public String getNerve3() {
        return nerve3;
    }

    public void setNerve3(String nerve3) {
        this.nerve3 = nerve3;
    }

    public String getNerve4() {
        return nerve4;
    }

    public void setNerve4(String nerve4) {
        this.nerve4 = nerve4;
    }

    public String getNerve5() {
        return nerve5;
    }

    public void setNerve5(String nerve5) {
        this.nerve5 = nerve5;
    }

    public String getLegs1() {
        return legs1;
    }

    public void setLegs1(String legs1) {
        this.legs1 = legs1;
    }

    public String getLegs2() {
        return legs2;
    }

    public void setLegs2(String legs2) {
        this.legs2 = legs2;
    }

    public String getLegs3() {
        return legs3;
    }

    public void setLegs3(String legs3) {
        this.legs3 = legs3;
    }

    public String getLegs4() {
        return legs4;
    }

    public void setLegs4(String legs4) {
        this.legs4 = legs4;
    }

    public String getLegs5() {
        return legs5;
    }

    public void setLegs5(String legs5) {
        this.legs5 = legs5;
    }

    public String getLegs6() {
        return legs6;
    }

    public void setLegs6(String legs6) {
        this.legs6 = legs6;
    }

    public String getDiabeticfootji() {
        return diabeticfootji;
    }

    public void setDiabeticfootji(String diabeticfootji) {
        this.diabeticfootji = diabeticfootji;
    }

    public int getHighblood() {
        return highblood;
    }

    public void setHighblood(int highblood) {
        this.highblood = highblood;
    }

    public String getHlp() {
        return hlp;
    }

    public void setHlp(String hlp) {
        this.hlp = hlp;
    }

    public String getCoronary() {
        return coronary;
    }

    public void setCoronary(String coronary) {
        this.coronary = coronary;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * 是否是空,
     *
     * @param str
     * @return
     */
    private String isNull(String str) {
        return TextUtils.isEmpty(str) ? "请输入  " : str;
    }
}
