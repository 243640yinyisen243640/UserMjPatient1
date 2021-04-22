package com.lyd.modulemall.ui.activity.supplier;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.utils.engine.GlideImageEngine;
import com.lyd.modulemall.R;
import com.lyd.modulemall.databinding.ActivityBusinessQualificationPicDetailBinding;
import com.maning.imagebrowserlibrary.MNImageBrowser;

/**
 * 描述:  营业资质照片详情页
 * 作者: LYD
 * 创建日期: 2021/1/4 10:41
 */
public class BusinessQualificationPicDetailActivity extends BaseViewBindingActivity<ActivityBusinessQualificationPicDetailBinding> {
    private static final String TAG = "BusinessQualificationPicDetailActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_qualification_pic_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("营业资质");
        String business_license_img = getIntent().getStringExtra("business_license_img");
        Glide.with(Utils.getApp()).load(business_license_img).into(binding.imgPic);
        binding.imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MNImageBrowser.with(BusinessQualificationPicDetailActivity.this)
                        .setCurrentPosition(0)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(business_license_img)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(binding.imgPic);
            }
        });
    }
}