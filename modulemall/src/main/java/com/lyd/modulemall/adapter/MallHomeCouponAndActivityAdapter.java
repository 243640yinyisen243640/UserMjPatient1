package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MallHomeIndexBean;
import com.lyd.modulemall.ui.activity.productlist.ActivityProductListActivity;
import com.lyd.modulemall.ui.activity.user.MyCouponListActivity;

import java.util.List;

public class MallHomeCouponAndActivityAdapter extends BaseQuickAdapter<MallHomeIndexBean.ActivityListBean, BaseViewHolder> {
    private List<MallHomeIndexBean.ActivityListBean> list;

    public MallHomeCouponAndActivityAdapter(@Nullable List<MallHomeIndexBean.ActivityListBean> list) {
        super(R.layout.item_mall_coupon_and_activity, list);
        this.list = list;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallHomeIndexBean.ActivityListBean item) {
        ImageView imgPic = helper.getView(R.id.img_coupon_and_activity);
        //        int position = helper.getLayoutPosition();
        //        int size = list.size();
        //        if (size % 2 == 0) {
        //            //偶数
        //            Glide.with(Utils.getApp()).load(item.getBig_img()).into(imgPic);
        //        } else {
        //            //奇数
        //            if (0 == position) {
        //                Glide.with(Utils.getApp()).load(item.getBig_img()).into(imgPic);
        //            } else {
        //                Glide.with(Utils.getApp()).load(item.getBig_img()).into(imgPic);
        //            }
        //        }
        Glide.with(Utils.getApp()).load(item.getBig_img()).into(imgPic);
        int activity_id = item.getActivity_id();
        String activity_img = item.getActivity_img();
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == activity_id) {
                    //优惠券
                    Intent intent = new Intent(Utils.getApp(), MyCouponListActivity.class);
                    intent.putExtra("activity_id", activity_id + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    //活动
                    Intent intent = new Intent(Utils.getApp(), ActivityProductListActivity.class);
                    intent.putExtra("activity_id", activity_id + "");
                    intent.putExtra("activity_img", activity_img);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            }
        });
    }
}
