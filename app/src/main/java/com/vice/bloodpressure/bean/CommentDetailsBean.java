package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * Created by yicheng on 2018/12/12.
 * <p>
 * 评论-评论
 */

public class CommentDetailsBean implements Serializable {
    /**
     * 被评论文章id
     */
    private String topicid;
    /**
     * 被评论id
     */
    private String userid;
    /**
     * 被评论pingid
     */
    private String pingid;
    /**
     * 被评论内容
     */
    private String info;
    /**
     * 被评论图片地址
     */
    private String picurl;
    /**
     * 被评论发布时间
     */
    private String addtime;
    /**
     * 被评论者头像
     */
    private String picture;
    /**
     * 被评论者名称
     */
    private String nickname;

    /**
     * 评论个数组
     *
     * @return
     */
    private String ping;


    public String getTopicid() {
        return topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPingid() {
        return pingid;
    }

    public void setPingid(String pingid) {
        this.pingid = pingid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public static class CirclePing implements Serializable {
        /**
         * 评论id
         */
        private String pingid;
        /**
         * 评论者id
         */
        private String userid;
        /**
         * 评论内容
         */
        private String info;
        /**
         * 评论图片地址
         */
        private String picurl;
        /**
         * 评论时间
         */
        private String addtime;
        /**
         * 评论者头像
         */
        private String picture;
        /**
         * 评论者名称
         */
        private String nickname;
        /**
         * 被评论者名称
         */
        private String bnickname;

        /**
         * 回复的回复
         *
         * @return
         */
        private String pingname;

        /**
         * 回复的回复时间
         *
         * @return
         */
        private String pingtime;

        /**
         * 回复的回复信息
         *
         * @return
         */
        private String pinginfo;

        public String getPingname() {
            return pingname;
        }

        public void setPingname(String pingname) {
            this.pingname = pingname;
        }

        public String getPingtime() {
            return pingtime;
        }

        public void setPingtime(String pingtime) {
            this.pingtime = pingtime;
        }

        public String getPinginfo() {
            return pinginfo;
        }

        public void setPinginfo(String pinginfo) {
            this.pinginfo = pinginfo;
        }

        public String getPingid() {
            return pingid;
        }

        public void setPingid(String pingid) {
            this.pingid = pingid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBnickname() {
            return bnickname;
        }

        public void setBnickname(String bnickname) {
            this.bnickname = bnickname;
        }
    }
}
