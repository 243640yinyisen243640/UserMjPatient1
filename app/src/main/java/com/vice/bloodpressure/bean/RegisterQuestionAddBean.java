package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class RegisterQuestionAddBean implements Serializable {
    //bp	是	string	血压范围
    //glbefore	是	float	餐前血糖范围
    //gllater	是	float	餐后血糖范围
    //gltype	是	int	糖尿病类型 1 :1型糖尿病 2 :2型糖尿病 3：妊娠糖尿病 4：其他
    private String access_token;
    private String bp;
    private String glbefore;
    private String gllater;
    private int gltype;
    //1是2否
    private int is_know;
    private LabelBean labells;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

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

    public int getIs_know() {
        return is_know;
    }

    public void setIs_know(int is_know) {
        this.is_know = is_know;
    }

    public LabelBean getLabells() {
        return labells;
    }

    public void setLabells(LabelBean labells) {
        this.labells = labells;
    }

    public static class LabelBean implements Serializable {
        //age	否	int	年龄 8：年龄＜40岁 10：年龄40～65岁 7：年龄＞65岁 5：年龄＜20岁 6：年龄＞20岁
        //tnbbch	否	int	糖尿病病程 11 :病程< 1年 12 :病程1~5年 13 :病程>5年 14 :病程< 2年 15 :病程2~5年
        //tnbsb	否	int	糖尿病肾病 17
        //tnbyb	否	int	糖尿病眼病 18
        //tnbsjb	否	int	糖尿病神经病变 9

        //tnbbz	否	int	糖尿病足 19
        //tnbxz	否	int	糖尿病下肢血管病变 20
        //gxy	否	int	高血压 21
        //gxb	否	int	冠心病 22
        //nzzh	否	int	脑卒中 23

        //mxzs	否	int	慢性阻塞性肺疾病 24
        //tnbqq	否	int	16：糖尿病前期

        private String age;
        private String tnbbch;
        private String tnbsb;
        private String tnbyb;
        private String tnbsjb;

        private String tnbbz;
        private String tnbxz;
        private String gxy;
        private String gxb;
        private String nzzh;

        private String mxzs;
        private String tnbqq;
        //妊娠
        private int tnbrshq;
        //时间戳
        private String tnbrshtime;


        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getTnbbch() {
            return tnbbch;
        }

        public void setTnbbch(String tnbbch) {
            this.tnbbch = tnbbch;
        }

        public String getTnbsb() {
            return tnbsb;
        }

        public void setTnbsb(String tnbsb) {
            this.tnbsb = tnbsb;
        }

        public String getTnbyb() {
            return tnbyb;
        }

        public void setTnbyb(String tnbyb) {
            this.tnbyb = tnbyb;
        }

        public String getTnbsjb() {
            return tnbsjb;
        }

        public void setTnbsjb(String tnbsjb) {
            this.tnbsjb = tnbsjb;
        }

        public String getTnbbz() {
            return tnbbz;
        }

        public void setTnbbz(String tnbbz) {
            this.tnbbz = tnbbz;
        }

        public String getTnbxz() {
            return tnbxz;
        }

        public void setTnbxz(String tnbxz) {
            this.tnbxz = tnbxz;
        }

        public String getGxy() {
            return gxy;
        }

        public void setGxy(String gxy) {
            this.gxy = gxy;
        }

        public String getGxb() {
            return gxb;
        }

        public void setGxb(String gxb) {
            this.gxb = gxb;
        }

        public String getNzzh() {
            return nzzh;
        }

        public void setNzzh(String nzzh) {
            this.nzzh = nzzh;
        }

        public String getMxzs() {
            return mxzs;
        }

        public void setMxzs(String mxzs) {
            this.mxzs = mxzs;
        }

        public String getTnbqq() {
            return tnbqq;
        }

        public void setTnbqq(String tnbqq) {
            this.tnbqq = tnbqq;
        }

        public int getTnbrshq() {
            return tnbrshq;
        }

        public void setTnbrshq(int tnbrshq) {
            this.tnbrshq = tnbrshq;
        }

        public String getTnbrshtime() {
            return tnbrshtime;
        }

        public void setTnbrshtime(String tnbrshtime) {
            this.tnbrshtime = tnbrshtime;
        }
    }


}
