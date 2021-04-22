package com.vice.bloodpressure.bean;

import java.util.List;

public class MedicineSelectListBean {


    /**
     * grouptitle : 请选择下面一组药物进行服用
     * groups : [{"id":80,"groupname":"分组一"}]
     */

    private String grouptitle;
    private List<GroupsBean> groups;

    public String getGrouptitle() {
        return grouptitle;
    }

    public void setGrouptitle(String grouptitle) {
        this.grouptitle = grouptitle;
    }

    public List<GroupsBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public static class GroupsBean {
        /**
         * id : 80
         * groupname : 分组一
         */

        private int id;
        private String groupname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }
    }
}
