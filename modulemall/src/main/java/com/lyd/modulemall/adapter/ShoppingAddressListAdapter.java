package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ShoppingAddressListBean;
import com.lyd.modulemall.ui.activity.address.AddressAddOrEditActivity;

import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;

public class ShoppingAddressListAdapter extends BaseQuickAdapter<ShoppingAddressListBean, BaseViewHolder> {
    public ShoppingAddressListAdapter(@Nullable List<ShoppingAddressListBean> data) {
        super(R.layout.item_shopping_address_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShoppingAddressListBean item) {
        String receiver_name = item.getReceiver_name();
        String province = item.getProvince();
        String city = item.getCity();
        String area = item.getArea();
        String receiver_address = item.getReceiver_address();
        String receiver_mobile = item.getReceiver_mobile();
        int is_default = item.getIs_default();
        helper.setText(R.id.tv_name, receiver_name);
        helper.setText(R.id.tv_address, province + city + area + receiver_address);
        helper.setText(R.id.tv_tel, receiver_mobile);
        if (is_default == 1) {
            helper.setVisible(R.id.img_default_address, true);
        } else {
            helper.setVisible(R.id.img_default_address, false);
        }
        //点击去编辑
        helper.getView(R.id.ll_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), AddressAddOrEditActivity.class);
                intent.putExtra("type", "edit");
                intent.putExtra("id", item.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });

    }

    public void setOnItemLongClickListener(Consumer<List<ShoppingAddressListBean>> listConsumer) {


    }
}
