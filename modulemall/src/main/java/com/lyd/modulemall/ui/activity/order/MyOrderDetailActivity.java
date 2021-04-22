package com.lyd.modulemall.ui.activity.order;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MyOrderDetailBean;
import com.lyd.modulemall.databinding.ActivityMyOrderDetailBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.adapter.orderdetail.MyOrderDetailAdapter;
import com.lyd.modulemall.adapter.orderdetail.OrderDetailMultipleEntity;
import com.rxjava.rxlife.RxLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: 订单详情页面
 * 四大状态: (待付款 待发货 待收货 待收货(又分为交易完成和交易关闭))
 * 作者: LYD
 * 创建日期: 2021/1/7 9:16
 */
public class MyOrderDetailActivity extends BaseViewBindingActivity<ActivityMyOrderDetailBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("订单详情");
        getOrderDetail();
    }

    private void getOrderDetail() {
        String order_id = getIntent().getStringExtra("order_id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("order_id", order_id);
        RxHttp.postForm(MallUrl.GET_ORDER_DETAIL)
                .addAll(map)
                .asResponse(MyOrderDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<MyOrderDetailBean>() {
                    @Override
                    public void accept(MyOrderDetailBean data) throws Exception {
                        int order_status = data.getOrder_status();
                        //订单状态 1待付款 2待发货 3待收货 5已完成 6已关闭
                        List<OrderDetailMultipleEntity> list = new ArrayList<>();
                        switch (order_status) {
                            case 1:
                            case 2:
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.STATE_YES, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.RECEIVE_PRODUCT, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.PRODUCT_DESC, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.ORDER_DETAIL, data));
                                break;
                            case 3:
                            case 5:
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.STATE_YES, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.LOGISTICS, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.RECEIVE_PRODUCT, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.PRODUCT_DESC, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.ORDER_DETAIL, data));
                                break;
                            case 6:
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.STATE_NO, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.RECEIVE_PRODUCT, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.PRODUCT_DESC, data));
                                list.add(new OrderDetailMultipleEntity(OrderDetailMultipleEntity.ORDER_DETAIL, data));
                                break;
                        }
                        MyOrderDetailAdapter adapter = new MyOrderDetailAdapter(list);
                        binding.rvOrderDetail.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        binding.rvOrderDetail.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

}