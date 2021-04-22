package com.vice.bloodpressure.bean;


public class LiverFilesRefreshBean {
    private int type;
    private int position;
    private String value;

    public LiverFilesRefreshBean(int type, int position, String value) {
        this.type = type;
        this.position = position;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
