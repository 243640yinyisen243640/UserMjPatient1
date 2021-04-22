package com.vice.bloodpressure.bean;

import java.util.List;

public class FollowUpVisitListBean {
    /**
     * morepage : true
     * data : [{"plan_name":"随访问卷","plan_list":[{"id":772,"times":1,"addtime":"2021-03-18 11:18","status":5,"visittime":1615996800,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":768,"times":1,"addtime":"2021-03-18 10:57","status":2,"visittime":1615996800,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":769,"times":0,"addtime":"2021-03-18 10:57","status":2,"visittime":1615996800,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":766,"times":1,"addtime":"2021-03-17 09:54","status":5,"visittime":1615910400,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":724,"times":0,"addtime":"2020-12-28 11:16","status":4,"visittime":1640620800,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":723,"times":0,"addtime":"2020-12-28 11:11","status":3,"visittime":1640620800,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":625,"times":12,"addtime":"2020-12-10 17:47","status":3,"visittime":1607529600,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":624,"times":11,"addtime":"2020-12-10 16:37","status":3,"visittime":1607529600,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":615,"times":10,"addtime":"2020-12-10 13:33","status":3,"visittime":1607529600,"plan_id":0,"plan_name":"随访问卷"}]},{"plan_name":"随访问卷","plan_list":[{"id":614,"times":9,"addtime":"2020-12-10 13:20","status":3,"visittime":1607529600,"plan_id":0,"plan_name":"随访问卷"}]}]
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
         * plan_name : 随访问卷
         * plan_list : [{"id":772,"times":1,"addtime":"2021-03-18 11:18","status":5,"visittime":1615996800,"plan_id":0,"plan_name":"随访问卷"}]
         */

        private String plan_name;
        private List<PlanListBean> plan_list;

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public List<PlanListBean> getPlan_list() {
            return plan_list;
        }

        public void setPlan_list(List<PlanListBean> plan_list) {
            this.plan_list = plan_list;
        }

        public static class PlanListBean {
            /**
             * id : 772
             * times : 1
             * addtime : 2021-03-18 11:18
             * status : 5
             * visittime : 1615996800
             * plan_id : 0
             * plan_name : 随访问卷
             */

            private int id;
            private int times;
            private String addtime;
            private int status;
            private int visittime;
            private int plan_id;
            private String plan_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getVisittime() {
                return visittime;
            }

            public void setVisittime(int visittime) {
                this.visittime = visittime;
            }

            public int getPlan_id() {
                return plan_id;
            }

            public void setPlan_id(int plan_id) {
                this.plan_id = plan_id;
            }

            public String getPlan_name() {
                return plan_name;
            }

            public void setPlan_name(String plan_name) {
                this.plan_name = plan_name;
            }
        }
    }
}
