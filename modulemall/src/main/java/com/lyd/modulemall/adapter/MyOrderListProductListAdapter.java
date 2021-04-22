package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MyOrderListProductListBean;
import com.lyd.modulemall.ui.activity.order.MyOrderDetailActivity;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.List;

public class MyOrderListProductListAdapter extends BaseQuickAdapter<MyOrderListProductListBean, BaseViewHolder> {
    private int orderId;

    public MyOrderListProductListAdapter(@Nullable List<MyOrderListProductListBean> data, int orderId) {
        super(R.layout.item_my_order_list_product_list, data);
        this.orderId = orderId;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyOrderListProductListBean item) {
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
        //外层订单列表 退款状态
        TextView tvRefund = helper.getView(R.id.tv_refund_status);
        int refund_status = item.getRefund_status();
        if (4 == refund_status) {
            tvRefund.setVisibility(View.VISIBLE);
            tvRefund.setText("退款成功");
        } else if (1 == refund_status || 2 == refund_status || 3 == refund_status) {
            tvRefund.setVisibility(View.VISIBLE);
            tvRefund.setText("退款中");
        } else {
            tvRefund.setVisibility(View.GONE);
        }
        //跳转订单详情
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MyOrderDetailActivity.class);
                intent.putExtra("order_id", orderId + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
