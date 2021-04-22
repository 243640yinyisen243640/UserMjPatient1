package com.vice.bloodpressure.bean;

public class PushBean {
    /**
     * display_type : notification
     * extra : {"id":"42"}
     * msg_id : uau26ve156696077872001
     * body : {"after_open":"go_custom","play_lights":"true","ticker":"患者住院申请","play_vibrate":"true","custom":"2","text":"放得开国际住院申请","title":"患者住院申请","play_sound":"true"}
     * random_min : 0
     */

    private String display_type;
    private ExtraBean extra;
    private String msg_id;
    private BodyBean body;
    private int random_min;

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public static class ExtraBean {
        /**
         * id : 42
         */
        private String id;
        private String type;
        private String docid;
        private String typename;
        private String val;
        private String url;

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class BodyBean {
        /**
         * after_open : go_custom
         * play_lights : true
         * ticker : 患者住院申请
         * play_vibrate : true
         * custom : 2
         * text : 放得开国际住院申请
         * title : 患者住院申请
         * play_sound : true
         */

        private String after_open;
        private String play_lights;
        private String ticker;
        private String play_vibrate;
        private String custom;
        private String text;
        private String title;
        private String play_sound;

        public String getAfter_open() {
            return after_open;
        }

        public void setAfter_open(String after_open) {
            this.after_open = after_open;
        }

        public String getPlay_lights() {
            return play_lights;
        }

        public void setPlay_lights(String play_lights) {
            this.play_lights = play_lights;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public String getPlay_vibrate() {
            return play_vibrate;
        }

        public void setPlay_vibrate(String play_vibrate) {
            this.play_vibrate = play_vibrate;
        }

        public String getCustom() {
            return custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlay_sound() {
            return play_sound;
        }

        public void setPlay_sound(String play_sound) {
            this.play_sound = play_sound;
        }
    }
}
