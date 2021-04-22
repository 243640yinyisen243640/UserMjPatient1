package com.vice.bloodpressure.bean;


import java.io.Serializable;

public class GalleryListBean implements Serializable {


    private String thumb_img;//string	缩略图
    private String gallery_id;//int	图集id

    public String getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(String gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }


}
