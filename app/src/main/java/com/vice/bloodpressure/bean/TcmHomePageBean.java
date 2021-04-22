package com.vice.bloodpressure.bean;

public class TcmHomePageBean {


    /**
     * id : 54
     * addtime : 1565576917
     * url : http://chronic.xiyuns.cn/index.php/case/indexapp/num/56dcc01afa0c4a768d6f1e54e3e62asdf/secret/da317b17f20f2638f60364666e83b2b7/phid/54
     */

    private int id;
    private int addtime;
    private String url;
    private String creaturl;

    public String getCreatUrl() {
        return creaturl;
    }

    public void setCreatUrl(String creaturl) {
        this.creaturl = creaturl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
