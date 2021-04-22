package com.vice.bloodpressure.bean;

import com.google.gson.annotations.SerializedName;

public class CheckAdviceBean {
    /**
     * content : 如有口渴疲乏无力，建议及时就诊
     * static : 2
     */

    private String content;
    /**
     * 属性重命名
     */
    @SerializedName("static")
    private int staticX;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStaticX() {
        return staticX;
    }

    public void setStaticX(int staticX) {
        this.staticX = staticX;
    }

}
