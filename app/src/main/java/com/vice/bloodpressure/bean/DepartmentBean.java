package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * Created by yicheng on 2018/6/12.
 * <p>
 * 公告相关实体类，医生建议实体类
 */

public class DepartmentBean implements Serializable {
    /**
     * 公告id
     */
    private String id;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 时间
     */
    private String time;
    /**
     * 类型，1：血压  2：血糖
     *
     * @return
     */
    private String type;
    /**
     * json=测量值
     *
     * @return
     */
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
