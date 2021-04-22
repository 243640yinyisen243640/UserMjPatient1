package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MyOrderListBean;
import com.lyd.modulemall.enumuse.MyOrderDesc;
import com.lyd.modulemall.ui.activity.order.MyOrderDetailActivity;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

public class MyOrderListAdapter extends BaseQuickAdapter<MyOrderListBean, BaseViewHolder> {
    public MyOrderListAdapter(@Nullable List<MyOrderListBean> data) {
        super(R.layout.item_my_order_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyOrderListBean item) {
        //订单状态文字
        int order_status = item.getOrder_status();
        String orderDesc = MyOrderDesc.getOrderDescFromOrderStatus(order_status);
        helper.setText(R.id.tv_desc, orderDesc);
        //总价
        String order_money = item.getOrder_money();
        String coupon_money = item.getCoupon_money();
        String pay_money = item.getPay_money();
        String totalPrice = String.format(Utils.getApp().getString(R.string.order_total_money), order_money, coupon_money, pay_money);
        helper.setText(R.id.tv_total_price, totalPrice);
        //商品信息
        int orderId = item.getOrder_id();

        RecyclerView rvProductList = helper.getView(R.id.rv_product_list);
        rvProductList.setLayoutManager(new LinearLayoutManager(rvProductList.getContext()));
        MyOrderListProductListAdapter myOrderListProductListAdapter = new MyOrderListProductListAdapter(item.getOrder_goods(), orderId);
        rvProductList.setAdapter(myOrderListProductListAdapter);
        //底部按钮根据状态判断显示隐藏展示
        ColorTextView tvLeft = helper.getView(R.id.tv_left);
        ColorTextView tvCenter = helper.getView(R.id.tv_center);
        ColorTextView tvRight = helper.getView(R.id.tv_right);
        //1待付款 2待发货 3待收货 5已完成 6已取消 （交易关闭）
        //待付款: 修改地址 取消订单 付款
        //待发货: 修改地址
        //待收货: 加入购物车 查看物流 确认收货
        //已完成: 加入购物车 删除订单 查看物流(交易关闭的话隐藏)
        switch (order_status) {
            case 1:
                tvLeft.setVisibility(View.VISIBLE);
                tvCenter.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvLeft.setText(R.string.order_change_address);
                tvCenter.setText(R.string.order_cancel);
                tvRight.setText(R.string.order_pay);
                break;
            case 2:
                tvLeft.setVisibility(View.GONE);
                tvCenter.setVisibility(View.GONE);
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText(R.string.order_change_address);
                break;
            case 3:
                tvLeft.setVisibility(View.VISIBLE);
                tvCenter.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvLeft.setText(R.string.order_add_cart);
                tvCenter.setText(R.string.order_check_the_logistics);
                tvRight.setText(R.string.order_confirm);
                break;
            case 5:
                tvLeft.setVisibility(View.VISIBLE);
                tvCenter.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvLeft.setText(R.string.order_add_cart);
                tvCenter.setText(R.string.order_del);
                tvRight.setText(R.string.order_check_the_logistics);
                break;
            case 6:
                tvLeft.setVisibility(View.GONE);
                tvCenter.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_add_cart);
                tvRight.setText(R.string.order_del);
                break;
        }
        helper.addOnClickListener(R.id.tv_left, R.id.tv_center, R.id.tv_right);
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
