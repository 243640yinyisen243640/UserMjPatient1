package com.vice.bloodpressure.bean;

import java.util.List;

public class MedicineSelectDetailListBean {

    /**
     * drugtitle : 以下四种药物只选一种进行服用
     * drugs : [{"id":18,"drugname":"卡托普利","listid":81},{"id":22,"drugname":"普萘洛尔","listid":82},{"id":25,"drugname":"硝苯地平","listid":83},{"id":28,"drugname":"氢氯噻嗪","listid":84}]
     */

    private String drugtitle;
    private List<DrugsBean> drugs;

    public String getDrugtitle() {
        return drugtitle;
    }

    public void setDrugtitle(String drugtitle) {
        this.drugtitle = drugtitle;
    }

    public List<DrugsBean> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugsBean> drugs) {
        this.drugs = drugs;
    }

    public static class DrugsBean {
        /**
         * id : 18
         * drugname : 卡托普利
         * listid : 81
         */

        private int id;
        private String drugname;
        private int listid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDrugname() {
            return drugname;
        }

        public void setDrugname(String drugname) {
            this.drugname = drugname;
        }

        public int getListid() {
            return listid;
        }

        public void setListid(int listid) {
            this.listid = listid;
        }
    }
}
