package com.lyd.modulemall.bean;

import com.google.gson.annotations.SerializedName;

public class WeChatPayBean {
    private PayParametersBean payParameters;

    public PayParametersBean getPayParameters() {
        return payParameters;
    }

    public void setPayParameters(PayParametersBean payParameters) {
        this.payParameters = payParameters;
    }

    public static class PayParametersBean {
        /**
         * appid : wxbedc85b967f57dc8
         * partnerid : 1384755302
         * prepayid : wx29170916582024e9b2be89ed11815a0000
         * package : Sign=WXPay
         * noncestr : gnppnm59757wkekil196lmvtzxrk1xx3
         * timestamp : 1611911356
         * sign : 8DFFBB10D0FD1425F133256012313C4C
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
