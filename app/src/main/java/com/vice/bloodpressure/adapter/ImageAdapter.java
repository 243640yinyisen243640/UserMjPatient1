package com.vice.bloodpressure.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.BannerBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;


/**
 * 自定义布局，图片
 */
public class ImageAdapter extends BannerAdapter<BannerBean, ImageHolder> {

    public ImageAdapter(List<BannerBean> list) {
        super(list);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, BannerBean data, int position, int size) {
        Glide.with(Utils.getApp())
                .load(data.getImgurl())
                .placeholder(R.drawable.banner)
                .error(R.drawable.banner)
                .into(holder.imageView);
    }
}
