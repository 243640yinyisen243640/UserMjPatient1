package com.lyd.modulemall.bean;

import com.lyd.baselib.widget.layout.pictureupload.PictureUploadModel;


/**
 * 描述：
 *
 * @author zhangqin
 * @date 2018/5/3，1
 */
public class PictureModel implements PictureUploadModel {

    private String imgPath;

    public PictureModel(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "PictureModel{" +
                "imgPath='" + imgPath + '\'' +
                ", image=" + imgPath +
                '}';
    }

    @Override
    public Object getPictureImage() {
        return imgPath;
    }
}
