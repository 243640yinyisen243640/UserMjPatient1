package com.lyd.modulemall.adapter.orderdetail;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.MyOrderDetailProductListAdapter;
import com.lyd.modulemall.bean.MyOrderDetailBean;

import java.util.List;

public class StateProductDescItemProvider extends BaseItemProvider<OrderDetailMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return MyOrderDetailAdapter.TYPE_PRODUCT_DESC;
    }

    @Override
    public int layout() {
        return R.layout.item_order_detail_type_product_desc;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, OrderDetailMultipleEntity data, int position) {
        int order_status = data.getMyModel().getOrder_status();

        String order_money = data.getMyModel().getOrder_money();
        String shipping_money = data.getMyModel().getShipping_money();
        String coupon_money = data.getMyModel().getCoupon_money();
        String pay_money = data.getMyModel().getPay_money();
        helper.setText(R.id.tv_price, order_money);
        helper.setText(R.id.tv_freight_charge, shipping_money);
        helper.setText(R.id.tv_discount, coupon_money);
        helper.setText(R.id.tv_pay_money, pay_money);

        List<MyOrderDetailBean.OrderGoodsBean> order_goods = data.getMyModel().getOrder_goods();
        RecyclerView rvProductList = helper.getView(R.id.rv_product_list);
        MyOrderDetailProductListAdapter adapter = new MyOrderDetailProductListAdapter(order_goods, order_status);
        rvProductList.setLayoutManager(new LinearLayoutManager(rvProductList.getContext()));
        rvProductList.setAdapter(adapter);

    }
}
