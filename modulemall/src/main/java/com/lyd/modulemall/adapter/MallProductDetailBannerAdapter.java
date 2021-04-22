package com.lyd.modulemall.adapter;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.modulemall.R;
import com.lyd.modulemall.databinding.ItemMallBannerBinding;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public class MallProductDetailBannerAdapter extends BaseBannerAdapter<String> {
    @Override
    protected void bindData(BaseViewHolder<String> holder, String data, int position, int pageSize) {
        //示例使用ViewBinding
        ItemMallBannerBinding viewBinding = ItemMallBannerBinding.bind(holder.itemView);
        //viewBinding.imgBanner.setImageResource(data.getSlideshow_img());
        Glide.with(Utils.getApp()).load(data).into(viewBinding.imgBanner);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_mall_banner;
    }
}
