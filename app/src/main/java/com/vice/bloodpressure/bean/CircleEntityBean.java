package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/8.
 * 获取朋友圈实体类
 */

public class CircleEntityBean implements Serializable {
    /**
     * 文章id
     */
    private String topicid;
    /**
     * 发布者id
     */
    private String userid;
    /**
     * 问题标题
     */
    private String title;
    /**
     * 图片地址
     */
    private String pic;
    /**
     * 浏览次数
     */
    private String num;
    /**
     * 回答条数
     */
    private String reply;
    /**
     * 发布时间
     */
    private String addtime;
    /**
     * 发布者头像
     */
    private String picture;
    /**
     * 发布者名称
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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
         * 回复数
         */
        private String reply;
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

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
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


        @Override
        public String toString() {
            return "CirclePing{" +
                    "pingid='" + pingid + '\'' +
                    ", userid='" + userid + '\'' +
                    ", info='" + info + '\'' +
                    ", picurl='" + picurl + '\'' +
                    ", reply='" + reply + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", picture='" + picture + '\'' +
                    ", nickname='" + nickname + '\'' +
                    '}';
        }
    }


    public static class CP implements Serializable {
        /**
         *
         */
        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        @Override
        public String toString() {
            return "CP{" +
                    "userid='" + userid + '\'' +
                    '}';
        }
    }
}
