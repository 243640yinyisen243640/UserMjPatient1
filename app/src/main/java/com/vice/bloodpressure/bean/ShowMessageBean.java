package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/7/3.
 * 接收后台发送的消息实体类
 */

public class ShowMessageBean {
    /**
     * 信息id
     */
    private String id;
    /**
     * 信息标题
     */
    private String title;
    /**
     * 发送时间
     */
    private String sendtime;
    /**
     * 信息详情内容
     *
     * @return
     */
    private String content;
    /**
     * 已读：1，未读：2
     *
     * @return
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
