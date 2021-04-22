package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/7/2.
 * 具体药物
 */

public class MedicineBean {
    /**
     * id
     */
    private String id;
    /**
     * 药品名
     */
    private String title;
    /**
     * 类型
     */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MedicineBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
