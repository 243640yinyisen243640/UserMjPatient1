package com.lyd.modulemall.ui.activity.order;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.LogisticsInfoAdapter;
import com.lyd.modulemall.bean.LogisticsInfoBean;
import com.lyd.modulemall.databinding.ActivityLogisticsInfoBinding;
import com.lyd.modulemall.enumuse.OrderDeliveryStatus;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 物流信息
 * 作者: LYD
 * 创建日期: 2021/1/5 15:13
 */
public class LogisticsInfoActivity extends BaseViewBindingActivity<ActivityLogisticsInfoBinding> {
    private static final String TAG = "LogisticsInfoActivity";
    private List<LogisticsInfoBean.ListBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("物流信息");
        getLogisticsInfo();
    }

    private void getLogisticsInfo() {
        String order_id = getIntent().getStringExtra("order_id");
        HashMap map = new HashMap<>();
        map.put("order_id", order_id);
        RxHttp.postForm(MallUrl.GET_ORDER_LOGISTICS)
                .addAll(map)
                .asResponse(LogisticsInfoBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<LogisticsInfoBean>() {
                    @Override
                    public void accept(LogisticsInfoBean data) throws Exception {
                        //设置商品信息
                        LogisticsInfoBean.OrderGoodsBean order_goods = data.getOrder_goods();
                        String goods_img_url = order_goods.getGoods_img_url();
                        String goods_name = order_goods.getGoods_name();
                        int goods_total_count = order_goods.getGoods_total_count();
                        Glide.with(Utils.getApp()).load(goods_img_url).into(binding.imgProductPic);
                        binding.tvProductName.setText(goods_name);
                        binding.tvTotalCount.setText("共" + goods_total_count + "件");
                        //设置快递信息
                        int deliverystatus = data.getDeliverystatus();
                        String logo = data.getLogo();
                        String expName = data.getExpName();
                        String number = data.getNumber();
                        String orderDescFromOrderStatus = OrderDeliveryStatus.getOrderDescFromOrderStatus(deliverystatus);
                        binding.tvProductState.setText(orderDescFromOrderStatus);
                        Glide.with(Utils.getApp()).load(logo).into(binding.imgExpressPic);
                        binding.tvExpressName.setText(expName);
                        binding.tvExpressNumber.setText(number);
                        //设置物流信息
                        list = data.getList();

                        String receiver_address = data.getReceiver_address();
                        LogisticsInfoBean.ListBean listBean = new LogisticsInfoBean.ListBean();
                        listBean.setStatus("[收货地址]" + receiver_address);
                        listBean.setTime("");
                        list.add(0, listBean);

                        binding.rvLogisticsInfo.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        LogisticsInfoAdapter logisticsInfoAdapter = new LogisticsInfoAdapter(list);
                        binding.rvLogisticsInfo.setAdapter(logisticsInfoAdapter);

                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}