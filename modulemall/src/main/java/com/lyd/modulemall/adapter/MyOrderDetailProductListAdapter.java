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
import com.lyd.modulemall.bean.MyOrderDetailBean;
import com.lyd.modulemall.ui.activity.refund.SelectServiceTypeActivity;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

public class MyOrderDetailProductListAdapter extends BaseQuickAdapter<MyOrderDetailBean.OrderGoodsBean, BaseViewHolder> {
    private int orderStatus;

    public MyOrderDetailProductListAdapter(@Nullable List<MyOrderDetailBean.OrderGoodsBean> data, int orderStatus) {
        super(R.layout.item_order_detail_product_list, data);
        this.orderStatus = orderStatus;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyOrderDetailBean.OrderGoodsBean item) {
        //是否可申请售后 1可以 2不可以 3正在售后
        int sales_return = item.getSales_return();
        //退款状态 1买家申请退货卖家等待处理 2.卖家同意（需要退货的发送退货地址） 3.等待卖家收货 4.退款成功 5拒绝退款 6.买家撤销退款
        int refund_status = item.getRefund_status();
        //订单状态 1待付款 2待发货 3待收货 5已完成 6已取消(已关闭)
        ColorTextView tvRefund = helper.getView(R.id.tv_refund);
        switch (orderStatus) {
            case 1:
            case 6:
                tvRefund.setVisibility(View.GONE);
                break;
            case 2:
            case 3:
                if (2 == sales_return) {
                    //2不可以
                    tvRefund.setVisibility(View.GONE);
                } else {
                    //1可以 3正在售后
                    tvRefund.setVisibility(View.VISIBLE);
                    tvRefund.setText("申请退款");
                    tvRefund.setEnabled(true);
                    if (4 == refund_status) {
                        tvRefund.setEnabled(false);
                        tvRefund.setText("退款成功");
                    } else if (1 == refund_status || 2 == refund_status || 3 == refund_status) {
                        tvRefund.setEnabled(false);
                        tvRefund.setText("退款中");
                    }
                }
                break;
            case 5:
                if (2 == sales_return) {
                    //2不可以
                    tvRefund.setVisibility(View.GONE);
                } else {
                    //1可以 3正在售后
                    tvRefund.setVisibility(View.VISIBLE);
                    tvRefund.setText("申请售后");
                    tvRefund.setEnabled(true);
                    if (4 == refund_status) {
                        tvRefund.setEnabled(false);
                        tvRefund.setText("退款成功");
                    } else if (1 == refund_status || 2 == refund_status || 3 == refund_status) {
                        tvRefund.setEnabled(false);
                        tvRefund.setText("退款中");
                    }
                }
                break;
        }
        int order_goods_id = item.getOrder_goods_id();
        String goods_img_url = item.getGoods_img_url();
        String goods_name = item.getGoods_name();
        String price = item.getPrice();
        int num = item.getNum();
        String sku_name = item.getSku_name();

        QMUIRadiusImageView imgProductPic = helper.getView(R.id.img_product_pic);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgProductPic);
        helper.setText(R.id.tv_product_name, goods_name);
        helper.setText(R.id.tv_type_state_price, price);
        helper.setText(R.id.tv_type_state_count, "X" + num);
        helper.setText(R.id.tv_product_desc, sku_name);
        //退款点击
        tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), SelectServiceTypeActivity.class);
                intent.putExtra("order_goods_id", order_goods_id);
                intent.putExtra("order_status", orderStatus);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
