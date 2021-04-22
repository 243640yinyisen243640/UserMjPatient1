package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MyRefundOrderListBean;
import com.lyd.modulemall.enumuse.MyRefundOrderDesc;
import com.lyd.modulemall.ui.activity.refund.RefundDetailActivity;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.List;

public class MyRefundOrderListAdapter extends BaseQuickAdapter<MyRefundOrderListBean, BaseViewHolder> {

    public MyRefundOrderListAdapter(@Nullable List<MyRefundOrderListBean> data) {
        super(R.layout.item_my_refund_order_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyRefundOrderListBean item) {
        int order_goods_id = item.getOrder_goods_id();
        String refund_real_money = item.getRefund_real_money();
        int refund_status = item.getRefund_status();
        String orderDesc = MyRefundOrderDesc.getOrderDescFromOrderStatus(refund_status);
        helper.setText(R.id.tv_desc, orderDesc);
        helper.setText(R.id.tv_total_price, orderDesc + "    退款金额:" + refund_real_money);
        String goods_img_url = item.getGoods_img_url();
        String goods_name = item.getGoods_name();
        String price = item.getPrice();
        int num = item.getNum();
        String sku_name = item.getSku_name();
        String shipping_money = item.getShipping_money();
        QMUIRadiusImageView imgPic = helper.getView(R.id.img_pic);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgPic);
        helper.setText(R.id.tv_name, goods_name);
        helper.setText(R.id.tv_price, price);
        helper.setText(R.id.tv_count, "X" + num);
        helper.setText(R.id.tv_specification, sku_name);
        helper.setText(R.id.tv_fees, shipping_money);

        helper.addOnClickListener(R.id.tv_left, R.id.tv_right);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), RefundDetailActivity.class);
                intent.putExtra("order_goods_id", order_goods_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
