package com.vice.bloodpressure.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MySectionBean implements MultiItemEntity {
    public static final int HEAD = 1;
    public static final int CONTENT = 2;
    public HeadBean headBean;
    public DietPlanFoodChildBean data;
    private int itemType;

    public MySectionBean(int itemType) {
        this.itemType = itemType;
    }

    public HeadBean getHeadBean() {
        return headBean;
    }

    public void setHeadBean(HeadBean headBean) {
        this.headBean = headBean;
    }

    public DietPlanFoodChildBean getData() {
        return data;
    }

    public void setData(DietPlanFoodChildBean data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


    public static class HeadBean {
        private int imgId;
        private String name;

        public HeadBean(int imgId, String name) {
            this.imgId = imgId;
            this.name = name;
        }

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}