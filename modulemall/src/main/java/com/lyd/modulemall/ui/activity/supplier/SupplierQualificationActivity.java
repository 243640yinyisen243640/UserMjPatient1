package com.lyd.modulemall.ui.activity.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.SupplierQualificationBean;
import com.lyd.modulemall.databinding.ActivitySupplierQualificationBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  供应商资质页面
 * 作者: LYD
 * 创建日期: 2021/1/4 10:22
 */
public class SupplierQualificationActivity extends BaseViewBindingActivity<ActivitySupplierQualificationBinding> implements View.OnClickListener {
    private String business_license_img;
    private ArrayList<String> qualification_img;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_supplier_qualification;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("供应商资质");
        binding.tvBusinessQualification.setOnClickListener(this);
        binding.tvOtherQualification.setOnClickListener(this);
        getProductSupplier();
    }


    /**
     * 获取供应商资质
     */
    private void getProductSupplier() {
        HashMap map = new HashMap<>();
        map.put("supplier_id", getIntent().getIntExtra("supplier_id", 0));
        RxHttp.postForm(MallUrl.GET_PRODUCT_SUPPLIER)
                .addAll(map)
                .asResponse(SupplierQualificationBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SupplierQualificationBean>() {
                    @Override
                    public void accept(SupplierQualificationBean data) throws Exception {
                        String company_name = data.getCompany_name();
                        business_license_img = data.getBusiness_license_img();
                        qualification_img = data.getQualification_img();
                        binding.tvSupplierName.setText(company_name);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_business_qualification) {
            Intent intent = new Intent(getPageContext(), BusinessQualificationPicDetailActivity.class);
            intent.putExtra("business_license_img", business_license_img);
            startActivity(intent);
        } else if (id == R.id.tv_other_qualification) {
            Intent intent = new Intent(getPageContext(), OtherQualificationPicDetailActivity.class);
            intent.putStringArrayListExtra("qualification_img", qualification_img);
            startActivity(intent);
        }
    }
}