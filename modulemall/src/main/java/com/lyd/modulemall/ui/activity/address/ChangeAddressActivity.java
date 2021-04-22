package com.lyd.modulemall.ui.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ShoppingAddressDetailBean;
import com.lyd.modulemall.databinding.ActivityChangeAddressBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  修改地址页面
 * 作者: LYD
 * 创建日期: 2021/1/4 13:49
 */
public class ChangeAddressActivity extends BaseViewBindingActivity<ActivityChangeAddressBinding> implements View.OnClickListener {
    private static final int TO_SELECT_ADDRESS = 10010;
    private static final String TAG = "ChangeAddressActivity";
    private String user_address_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_address;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("修改地址");
        user_address_id = getIntent().getStringExtra("user_address_id");
        getOrderAddressDetail();
        binding.llAddress.llAddressRoot.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
    }

    private void getOrderAddressDetail() {
        HashMap map = new HashMap<>();
        map.put("id", user_address_id);
        RxHttp.postForm(MallUrl.GET_ADDRESS_DETAIL)
                .addAll(map)
                .asResponse(ShoppingAddressDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ShoppingAddressDetailBean>() {
                    @Override
                    public void accept(ShoppingAddressDetailBean data) throws Exception {
                        String receiver_name = data.getReceiver_name();
                        String receiver_mobile = data.getReceiver_mobile();
                        String province = data.getProvince();
                        String city = data.getCity();
                        String area = data.getArea();
                        String receiver_address = data.getReceiver_address();
                        binding.llAddress.tvName.setText(receiver_name);
                        binding.llAddress.imgDefaultAddress.setVisibility(View.GONE);
                        binding.llAddress.tvAddress.setText(province + city + area + receiver_address);
                        binding.llAddress.tvTel.setText(receiver_mobile);
                        binding.llAddress.llEdit.setVisibility(View.GONE);
                        binding.llAddress.imgRightArrow.setVisibility(View.VISIBLE);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    private void toSubmit() {
        int order_id = getIntent().getIntExtra("order_id", 0);
        HashMap map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("id", user_address_id);
        RxHttp.postForm(MallUrl.ORDER_CHANGE_ADDRESS)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        finish();
                        ToastUtils.showShort(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TO_SELECT_ADDRESS:
                    user_address_id = data.getIntExtra("id", 0) + "";
                    getOrderAddressDetail();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel) {
            finish();
        } else if (id == R.id.tv_submit) {
            toSubmit();
        } else if (id == R.id.ll_address_root) {
            Intent intent = new Intent(getPageContext(), ShoppingAddressListActivity.class);
            startActivityForResult(intent, TO_SELECT_ADDRESS);
        }
    }
}