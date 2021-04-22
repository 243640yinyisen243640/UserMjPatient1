package com.vice.bloodpressure.bean;

import java.util.List;

public class CheckDataDetailListBean {


    /**
     * data : [{"id":6,"addtime":"2019-09-11 11:41","picurl":"http://www.api.com/public/uploads/20190911\\8779c7e159c188ad9f9730ff2664db61.jpg"},{"id":5,"addtime":"2019-09-11 11:41","picurl":"http://www.api.com/public/uploads/20190911\\917ab0f197742108f92910cd83786ddb.jpg"},{"id":4,"addtime":"2019-09-11 11:32","picurl":"http://localhost/public/uploads/20190911\\555e6030581ee727c01770b1c1dbe071.jpg"},{"id":3,"addtime":"2019-09-11 11:27","picurl":"http://localhost/public/uploads/20190911\\8010355855cc4e14a9a6027a9408a1f8.jpg"},{"id":2,"addtime":"2019-09-11 11:08","picurl":null},{"id":1,"addtime":"2019-09-11 11:06","picurl":null}]
     * morepage : false
     */

    private boolean morepage;
    private List<DataBean> data;

    public boolean isMorepage() {
        return morepage;
    }

    public void setMorepage(boolean morepage) {
        this.morepage = morepage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * addtime : 2019-09-11 11:41
         * picurl : http://www.api.com/public/uploads/20190911\8779c7e159c188ad9f9730ff2664db61.jpg
         */

        private int id;
        private String addtime;
        private String picurl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }
    }
}
