package com.vice.bloodpressure.bean;

import java.util.List;

public class TcmListBean {

    /**
     * data : [{"id":61,"addtime":"2019-08-13 18:05","url":"http://chronic.xiyuns.cn/index.php/case/indexapp/num/781104d0ab8eed18036318cf1fa962a1/secret/da317b17f20f2638f60364666e83b2b7/phid/61"}]
     * creaturl : http://chronic.xiyuns.cn/index.php/case/index/num/781104d0ab8eed18036318cf1fa962a1/secret/da317b17f20f2638f60364666e83b2b7/type/2
     * morepage : false
     */

    private String creaturl;
    private boolean morepage;
    private List<DataBean> data;

    public String getCreaturl() {
        return creaturl;
    }

    public void setCreaturl(String creaturl) {
        this.creaturl = creaturl;
    }

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
         * id : 61
         * addtime : 2019-08-13 18:05
         * url : http://chronic.xiyuns.cn/index.php/case/indexapp/num/781104d0ab8eed18036318cf1fa962a1/secret/da317b17f20f2638f60364666e83b2b7/phid/61
         */

        private int id;
        private String addtime;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
