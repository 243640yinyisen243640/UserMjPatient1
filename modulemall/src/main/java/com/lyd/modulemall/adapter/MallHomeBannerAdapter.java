package com.lyd.modulemall.adapter;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MallHomeIndexBean;
import com.lyd.modulemall.databinding.ItemMallBannerBinding;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;


public class MallHomeBannerAdapter extends BaseBannerAdapter<MallHomeIndexBean.SlideshowListBean> {

    @Override
    protected void bindData(BaseViewHolder<MallHomeIndexBean.SlideshowListBean> holder, MallHomeIndexBean.SlideshowListBean data, int position, int pageSize) {
        //示例使用ViewBinding
        ItemMallBannerBinding viewBinding = ItemMallBannerBinding.bind(holder.itemView);
        //viewBinding.imgBanner.setImageResource(data.getSlideshow_img());
        Glide.with(Utils.getApp()).load(data.getSlideshow_img()).into(viewBinding.imgBanner);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_mall_banner;
    }
}
