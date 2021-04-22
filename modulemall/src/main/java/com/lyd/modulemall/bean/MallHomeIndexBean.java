package com.lyd.modulemall.bean;

import java.util.List;

public class MallHomeIndexBean {
    private List<SlideshowListBean> slideshow_list;
    private List<CategoryListBean> category_list;
    private List<ActivityListBean> activity_list;

    public List<SlideshowListBean> getSlideshow_list() {
        return slideshow_list;
    }

    public void setSlideshow_list(List<SlideshowListBean> slideshow_list) {
        this.slideshow_list = slideshow_list;
    }

    public List<CategoryListBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryListBean> category_list) {
        this.category_list = category_list;
    }

    public List<ActivityListBean> getActivity_list() {
        return activity_list;
    }

    public void setActivity_list(List<ActivityListBean> activity_list) {
        this.activity_list = activity_list;
    }

    public static class SlideshowListBean {
        /**
         * slideshow_id : 4
         * slideshow_img : http://d.xiyuns.cn/publics/shopimg/lb1.png
         * goods_id : 4
         */

        private int slideshow_id;
        private String slideshow_img;
        private int goods_id;

        public int getSlideshow_id() {
            return slideshow_id;
        }

        public void setSlideshow_id(int slideshow_id) {
            this.slideshow_id = slideshow_id;
        }

        public String getSlideshow_img() {
            return slideshow_img;
        }

        public void setSlideshow_img(String slideshow_img) {
            this.slideshow_img = slideshow_img;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }
    }

    public static class CategoryListBean {
        /**
         * category_id : 1
         * category_name : 分类一
         * category_image : http://d.xiyuns.cn/publics/shopimg/fl.png
         */

        private int category_id;
        private String category_name;
        private String category_image;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_image() {
            return category_image;
        }

        public void setCategory_image(String category_image) {
            this.category_image = category_image;
        }
    }

    public static class ActivityListBean {
        /**
         * activity_id : 1
         * activity_name : 优惠券
         * big_img : http://d.xiyuns.cn/publics/shopimg/hd1big.png
         * small_img : http://d.xiyuns.cn/publics/shopimg/hd1small.png
         * activity_img : http://d.xiyuns.cn/publics/shopimg/hd1.png
         * date_ids : ;1;2;
         */

        private int activity_id;
        private String activity_name;
        private String big_img;
        private String small_img;
        //详情图
        private String activity_img;
        private String date_ids;

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getBig_img() {
            return big_img;
        }

        public void setBig_img(String big_img) {
            this.big_img = big_img;
        }

        public String getSmall_img() {
            return small_img;
        }

        public void setSmall_img(String small_img) {
            this.small_img = small_img;
        }

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }

        public String getDate_ids() {
            return date_ids;
        }

        public void setDate_ids(String date_ids) {
            this.date_ids = date_ids;
        }
    }
}
