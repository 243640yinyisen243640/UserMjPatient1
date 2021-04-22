package com.lyd.modulemall.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.baselib.utils.engine.GlideImageEngine;
import com.lyd.modulemall.R;
import com.maning.imagebrowserlibrary.MNImageBrowser;

import java.util.ArrayList;

public class OtherQualificationPicDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private ArrayList<String> list;

    public OtherQualificationPicDetailAdapter(@Nullable ArrayList<String> data) {
        super(R.layout.item_other_qualification_pic_list, data);
        this.list = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        int position = helper.getLayoutPosition();

        ImageView imgPic = helper.getView(R.id.img_pic);
        Glide.with(Utils.getApp()).load(item).into(imgPic);
        imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MNImageBrowser.with(v.getContext())
                        .setCurrentPosition(position)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList(list)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(imgPic);
            }
        });
    }
}
