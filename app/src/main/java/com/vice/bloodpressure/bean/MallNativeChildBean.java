package com.vice.bloodpressure.bean;

import java.util.List;

public class MallNativeChildBean {
    private List<BannerBean> banner;
    private List<ListBean> list;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class BannerBean {
        /**
         * name : 降特-医用主食（即食版）
         * bannerurl : http://doctor.xiyuns.cn/Public/upload/20191025/201910251544421571989482.jpg
         * gid : http://d.xiyuns.cn/shop.php/index/details/gid/1571989482/guid/60181135decfb0f6072682ad31add1a8
         */

        private String name;
        private String bannerurl;
        private String gid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBannerurl() {
            return bannerurl;
        }

        public void setBannerurl(String bannerurl) {
            this.bannerurl = bannerurl;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }
    }

    public static class ListBean {
        /**
         * name : 降特-医用主食（蒸笼版）
         * imgurl : http://doctor.xiyuns.cn/Public/upload/20191025/201910251538501571989130.jpg
         * original : 550.00
         * price : 450.00
         * sales : 46
         * gid : http://d.xiyuns.cn/shop.php/index/details/gid/1571989130/guid/60181135decfb0f6072682ad31add1a8
         */

        private String name;
        private String imgurl;
        private String original;
        private String price;
        private int sales;
        private String gid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }
    }
}
