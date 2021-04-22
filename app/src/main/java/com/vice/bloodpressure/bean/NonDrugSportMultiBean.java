package com.vice.bloodpressure.bean;

public class NonDrugSportMultiBean {

    private String content;
    private Type type;

    public NonDrugSportMultiBean(String content, Type type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 应用类型
     */
    public enum Type {
        TypeOne, TypeTwo
    }
}
