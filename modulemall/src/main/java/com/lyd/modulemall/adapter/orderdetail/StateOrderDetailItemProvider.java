package com.lyd.modulemall.adapter.orderdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.BottomSingleChoiceStringAdapter;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.activity.address.ChangeAddressActivity;
import com.lyd.modulemall.ui.activity.order.LogisticsInfoActivity;
import com.lyd.modulemall.ui.activity.pay.OrderPayActivity;
import com.lyd.modulemall.view.popup.BottomSingleChoicePopup;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class StateOrderDetailItemProvider extends BaseItemProvider<OrderDetailMultipleEntity, BaseViewHolder> implements View.OnClickListener {
    private String cancel_remark;
    private BottomSingleChoicePopup bottomSingleChoicePopup;
    private int order_id;
    private String user_address_id;

    @Override
    public int viewType() {
        return MyOrderDetailAdapter.TYPE_ORDER_DETAIL;
    }

    @Override
    public int layout() {
        return R.layout.item_order_detail_type_order_detail;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, OrderDetailMultipleEntity data, int position) {
        order_id = data.getMyModel().getOrder_id();
        user_address_id = data.getMyModel().getUser_address_id();

        int orderStatus = data.getMyModel().getOrder_status();
        String order_no = data.getMyModel().getOrder_no();
        String create_time = data.getMyModel().getCreate_time();
        String pay_time = data.getMyModel().getPay_time();
        String delivery_time = data.getMyModel().getDelivery_time();
        helper.setText(R.id.tv_order_number, order_no);
        helper.setText(R.id.tv_order_create_time, create_time);
        helper.setText(R.id.tv_pay_info, pay_time);
        helper.setText(R.id.tv_send_time, delivery_time);
        //订单状态 1待付款 2待发货 3待收货 5已完成 6已关闭
        LinearLayout llPayInfo = helper.getView(R.id.ll_pay_info);
        LinearLayout llSendTime = helper.getView(R.id.ll_send_time);
        ColorTextView tvLeft = helper.getView(R.id.tv_left);
        ColorTextView tvRight = helper.getView(R.id.tv_right);
        switch (orderStatus) {
            case 1:
                llPayInfo.setVisibility(View.GONE);
                llSendTime.setVisibility(View.GONE);
                tvLeft.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvLeft.setText("取消订单");
                tvRight.setText("立即付款");
                break;
            case 2:
                llSendTime.setVisibility(View.GONE);
                tvLeft.setVisibility(View.GONE);
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("修改地址");
                break;
            case 3:
                llPayInfo.setVisibility(View.VISIBLE);
                llSendTime.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvLeft.setText("查看物流");
                tvRight.setText("确认收货");
                break;
            case 5:
                llPayInfo.setVisibility(View.VISIBLE);
                llSendTime.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvLeft.setText("删除订单");
                tvRight.setText("查看物流");
                break;
            case 6:
                llPayInfo.setVisibility(View.GONE);
                llSendTime.setVisibility(View.GONE);
                tvLeft.setVisibility(View.GONE);
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("删除订单");
                break;
        }

        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorTextView tvText = (ColorTextView) v;
                String text = tvText.getText().toString().trim();
                switch (text) {
                    case "取消订单":
                        toCancelOrder(v.getContext());
                        break;
                    case "查看物流":
                        lookLogistics();
                        break;
                    case "删除订单":
                        toDelOrder();
                        break;
                }
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorTextView tvText = (ColorTextView) v;
                String text = tvText.getText().toString().trim();
                switch (text) {
                    case "立即付款":
                        toPay();
                        break;
                    case "修改地址":
                        toChangeAddress();
                        break;
                    case "确认收货":
                        toSureOrder();
                        break;
                    case "查看物流":
                        lookLogistics();
                        break;
                    case "删除订单":
                        toDelOrder();
                        break;
                }
            }
        });
    }

    private void toSureOrder() {
        toOperateOrder("confirm");
    }

    private void toChangeAddress() {
        //收货地址Id
        Intent intent = new Intent(Utils.getApp(), ChangeAddressActivity.class);
        intent.putExtra("user_address_id", user_address_id);
        intent.putExtra("order_id", order_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApp().startActivity(intent);
    }

    private void toPay() {
        Intent intent = new Intent(Utils.getApp(), OrderPayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", "2");
        bundle.putString("order_id", order_id + "");
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApp().startActivity(intent);
    }

    private void toDelOrder() {
        toOperateOrder("del");
    }

    private void lookLogistics() {
        Intent intent = new Intent(Utils.getApp(), LogisticsInfoActivity.class);
        intent.putExtra("order_id", order_id + "");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApp().startActivity(intent);
    }

    private void toCancelOrder(Context context) {
        bottomSingleChoicePopup = new BottomSingleChoicePopup(context);
        TextView tvTitle = bottomSingleChoicePopup.findViewById(R.id.tv_title);
        tvTitle.setText("订单取消");
        TextView tvDesc = bottomSingleChoicePopup.findViewById(R.id.tv_desc);
        tvDesc.setText("请选择取消原因");
        //设置Rv
        RecyclerView rvCheckList = bottomSingleChoicePopup.findViewById(R.id.rv_check_list);
        String[] str = Utils.getApp().getResources().getStringArray(R.array.bottom_popup_cancel_order_and_refund_reason);
        List<String> list = Arrays.asList(str);
        //注册选择器
        SingleCheckHelper singleCheckHelper = new SingleCheckHelper();
        singleCheckHelper.register(String.class, new CheckHelper.Checker<String, BaseViewHolder>() {
            @Override
            public void check(String bean, BaseViewHolder holder) {
                cancel_remark = bean;
                CheckBox cbCheck = holder.getView(R.id.cb_check);
                cbCheck.setChecked(true);
            }

            @Override
            public void unCheck(String bean, BaseViewHolder holder) {
                CheckBox cbCheck = holder.getView(R.id.cb_check);
                cbCheck.setChecked(false);
            }
        });
        BottomSingleChoiceStringAdapter bottomSingleChoiceStringAdapter = new BottomSingleChoiceStringAdapter(list, singleCheckHelper);
        rvCheckList.setLayoutManager(new LinearLayoutManager(context));
        rvCheckList.setAdapter(bottomSingleChoiceStringAdapter);
        //
        TextView tvSure = bottomSingleChoicePopup.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(this);
        bottomSingleChoicePopup.showPopupWindow();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sure) {
            toOperateOrder("cancel");
            bottomSingleChoicePopup.dismiss();
        }
    }

    private void toOperateOrder(String type) {
        HashMap map = new HashMap<>();
        String url = "";
        if ("cancel".equals(type)) {
            url = MallUrl.CANCEL_ORDER;
            map.put("cancel_remark", cancel_remark);
        } else if ("del".equals(type)) {
            url = MallUrl.DEL_ORDER;
        } else if ("confirm".equals(type)) {
            url = MallUrl.CONFIRM_ORDER;
        }
        map.put("order_id", order_id);
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(String.class)
                //.to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {

                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
