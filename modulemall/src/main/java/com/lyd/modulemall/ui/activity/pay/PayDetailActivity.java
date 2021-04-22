package com.lyd.modulemall.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.databinding.ActivityPayDetailBinding;
import com.lyd.modulemall.ui.activity.order.MyOrderListActivity;

import liys.click.AClickStr;

/**
 * 描述: 支付详情
 * 作者: LYD
 * 创建日期: 2021/1/28 16:25
 */
public class PayDetailActivity extends BaseViewBindingActivity<ActivityPayDetailBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("支付详情");
        String type = getIntent().getStringExtra("type");
        if ("success".equals(type)) {
            binding.llSuccess.setVisibility(View.VISIBLE);
            binding.rlFailed.setVisibility(View.GONE);

        } else {
            binding.llSuccess.setVisibility(View.GONE);
            binding.rlFailed.setVisibility(View.VISIBLE);
        }
    }

    @AClickStr({"tv_success_back", "tv_success_to_order_list", "tv_failed_back", "tv_failed_pay"})
    public void click(View view, String viewId) {
        switch (viewId) {
            case "tv_success_back":
            case "tv_failed_back":
                EventBusUtils.post(new EventMessage<>(BaseConstantParam.EventCode.MALL_FINISH_RO_MAIN_ACTIVITY));
                break;
            case "tv_success_to_order_list":
            case "tv_failed_pay":
                startActivity(new Intent(getPageContext(), MyOrderListActivity.class));
                break;
        }
    }
}