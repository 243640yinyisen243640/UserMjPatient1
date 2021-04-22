package com.lyd.modulemall.ui.activity.address;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.AddressBean;
import com.lyd.modulemall.bean.ShoppingAddressDetailBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.ActivityAddressAddOrEditBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.view.popup.BottomAddressSelectPopup;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述:   添加新地址 和 编辑收货地址 页面
 * type :add 添加 edit 编辑
 * 作者: LYD
 * 创建日期: 2021/1/4 13:47
 */
public class AddressAddOrEditActivity extends BaseViewBindingActivity<ActivityAddressAddOrEditBinding> implements View.OnClickListener {

    private int id;
    private BottomAddressSelectPopup popup;

    private String provinceAdcode;
    private String cityAdcode;
    private String districtAdcode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_add_or_edit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String type = getIntent().getStringExtra("type");
        if ("add".equals(type)) {
            setTitle("添加新地址");
            binding.llDel.setVisibility(View.GONE);
        } else {
            setTitle("编辑收货地址");
            binding.llDel.setVisibility(View.VISIBLE);
            id = getIntent().getIntExtra("id", 0);
            getAddressDetail();
        }
        binding.llSelectAddress.setOnClickListener(this);
        binding.llDel.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
    }


    /**
     *
     */
    private void getAddressDetail() {
        HashMap map = new HashMap<>();
        map.put("id", id);
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
                        provinceAdcode = data.getProvince_code();
                        cityAdcode = data.getCity_code();
                        districtAdcode = data.getArea_code();

                        String receiver_address = data.getReceiver_address();
                        int is_default = data.getIs_default();
                        binding.etPersonName.setText(receiver_name);
                        binding.etPersonName.setSelection(receiver_name.length());
                        binding.etTel.setText(receiver_mobile);
                        binding.tvAddress.setText(province + city + area);
                        binding.etAddressDetail.setText(receiver_address);
                        if (1 == is_default) {
                            binding.sbDefault.setChecked(true);
                        } else {
                            binding.sbDefault.setChecked(false);
                        }
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
        if (id == R.id.ll_select_address) {
            popup = new BottomAddressSelectPopup(getPageContext());
            popup.showPopupWindow();
            popup.setOnCityItemClickListener(new BottomAddressSelectPopup.OnCityItemClickListener() {
                @Override
                public void onSelected(AddressBean provinceBean, AddressBean cityBean, AddressBean districtBean) {
                    provinceAdcode = provinceBean.getAdcode();
                    String provinceName = provinceBean.getCityname();

                    cityAdcode = cityBean.getAdcode();
                    String cityName = cityBean.getCityname();

                    districtAdcode = districtBean.getAdcode();
                    String districtCityname = districtBean.getCityname();


                    binding.tvAddress.setText(provinceName + cityName + districtCityname);
                }
            });
        } else if (id == R.id.ll_del) {
            toDel();
        } else if (id == R.id.tv_save) {
            toSaveAddress();
        }
    }

    /**
     * 删除
     */
    private void toDel() {
        HashMap map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(MallUrl.DEL_ADDRESS)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        finish();
                        EventBusUtils.postSticky(new EventMessage<>(MallConstantParam.ADD_ADDRESS_REFRESH));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void toSaveAddress() {
        String personName = binding.etPersonName.getText().toString().trim();
        if (TextUtils.isEmpty(personName)) {
            ToastUtils.showShort("请输入收货人姓名");
            return;
        }
        String tel = binding.etTel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            ToastUtils.showShort("请输入联系电话");
            return;
        }
        if (!RegexUtils.isMobileSimple(tel)) {
            ToastUtils.showShort("请输入合法手机号");
            return;
        }
        String address = binding.tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShort("请选择所在地区");
            return;
        }
        String addressDetail = binding.etAddressDetail.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            ToastUtils.showShort("请输入详细地址");
            return;
        }
        toDoSaveAddress();
    }

    private void toDoSaveAddress() {
        String personName = binding.etPersonName.getText().toString().trim();
        String tel = binding.etTel.getText().toString().trim();
        String addressDetail = binding.etAddressDetail.getText().toString().trim();
        //获取是否默认就诊人
        boolean checked = binding.sbDefault.isChecked();
        String isDefault = "";
        if (checked) {
            isDefault = "1";
        } else {
            isDefault = "2";
        }
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("receiver_name", personName);
        map.put("receiver_mobile", tel);
        map.put("receiver_address", addressDetail);
        map.put("is_default", isDefault);
        //省市县三级代码
        map.put("province_code", provinceAdcode);
        map.put("city_code", cityAdcode);
        map.put("area_code", districtAdcode);
        RxHttp.postForm(MallUrl.ADD_OR_EDIT_ADDRESS)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        finish();
                        EventBusUtils.postSticky(new EventMessage<>(MallConstantParam.ADD_ADDRESS_REFRESH));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}