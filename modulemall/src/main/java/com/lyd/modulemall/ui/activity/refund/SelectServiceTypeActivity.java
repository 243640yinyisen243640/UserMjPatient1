package com.lyd.modulemall.ui.activity.refund;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.RefundOrderGoodsBean;
import com.lyd.modulemall.databinding.ActivitySelectServiceTypeBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  选择服务类型 (点击订单详情里的 退款 或者 申请售后)
 * 注:
 * 当订单状态为 待发货时，申请售后只显示“我要退款（无需退货）”
 * 当订单状态为 待收货和已完成，只显示“我要退款退货”
 * 作者: LYD
 * 创建日期: 2021/1/18 13:30
 */
public class SelectServiceTypeActivity extends BaseViewBindingActivity<ActivitySelectServiceTypeBinding> implements View.OnClickListener {
    private static final String TAG = "SelectServiceTypeActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_service_type;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("选择服务类型");
        setShow();
        getGoodsInfo();
        binding.llOne.setOnClickListener(this);
        binding.llTwo.setOnClickListener(this);
    }

    private void setShow() {
        //订单状态 1待付款 2待发货 3待收货 5已完成 6已取消(已关闭) 7退款
        int order_status = getIntent().getIntExtra("order_status", 0);
        Log.e(TAG, "order_status==" + order_status);
        if (2 == order_status) {
            binding.llOne.setVisibility(View.VISIBLE);
            binding.llTwo.setVisibility(View.GONE);
        } else {
            binding.llOne.setVisibility(View.GONE);
            binding.llTwo.setVisibility(View.VISIBLE);
        }
    }

    private void getGoodsInfo() {
        int order_goods_id = getIntent().getIntExtra("order_goods_id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        RxHttp.postForm(MallUrl.REFUND_ORDER_GOODS)
                .addAll(map)
                .asResponse(RefundOrderGoodsBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<RefundOrderGoodsBean>() {
                    @Override
                    public void accept(RefundOrderGoodsBean data) throws Exception {
                        String goods_name = data.getGoods_name();
                        String goods_img_url = data.getGoods_img_url();
                        String sku_name = data.getSku_name();
                        Glide.with(Utils.getApp()).load(goods_img_url).into(binding.imgPic);
                        binding.tvProductName.setText(goods_name);
                        binding.tvSpecification.setText(sku_name);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int order_goods_id = getIntent().getIntExtra("order_goods_id", 0);
        if (id == R.id.ll_one) {
            //申请 退款
            Intent intent = new Intent(getPageContext(), ApplyRefundActivity.class);
            intent.putExtra("type", "onlyRefund");
            intent.putExtra("order_goods_id", order_goods_id);
            startActivity(intent);
        } else if (id == R.id.ll_two) {
            //申请 退货退款
            Intent intent = new Intent(getPageContext(), ApplyRefundActivity.class);
            intent.putExtra("type", "productReturnAndRefund");
            intent.putExtra("order_goods_id", order_goods_id);
            startActivity(intent);
        }
    }
}