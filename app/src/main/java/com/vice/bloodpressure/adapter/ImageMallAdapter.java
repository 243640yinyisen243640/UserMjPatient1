package com.vice.bloodpressure.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MallNativeChildBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;


/**
 * 自定义布局，图片
 */
public class ImageMallAdapter extends BannerAdapter<MallNativeChildBean.BannerBean, ImageHolder> {

    public ImageMallAdapter(List<MallNativeChildBean.BannerBean> list) {
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
    public void onBindView(ImageHolder holder, MallNativeChildBean.BannerBean data, int position, int size) {
        Glide.with(Utils.getApp())
                .load(data.getBannerurl())
                .placeholder(R.drawable.banner)
                .error(R.drawable.banner)
                .into(holder.imageView);
    }
}
