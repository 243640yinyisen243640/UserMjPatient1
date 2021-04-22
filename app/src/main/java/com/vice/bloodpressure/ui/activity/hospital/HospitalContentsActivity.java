package com.vice.bloodpressure.ui.activity.hospital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.bean.HospitalDetailsBean;


/**
 * 描述: 医院介绍
 * 作者: LYD
 * 创建日期: 2019/3/19 19:37
 */

public class HospitalContentsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgHospital;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvTel;
    private TextView tvDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.hospital_contents));

        initView();
        setData();
    }

    private void setData() {
        HospitalDetailsBean data = (HospitalDetailsBean) getIntent().getSerializableExtra("hsp");

        int width = ScreenUtils.getScreenWidth();
        double height = ScreenUtils.getScreenWidth() * 0.6;
        //动态设置宽高
        ViewGroup.LayoutParams linearParams = imgHospital.getLayoutParams();
        linearParams.height = (int) height;
        linearParams.width = width;
        imgHospital.setLayoutParams(linearParams);

        Glide.with(Utils.getApp()).load(data.getImgurl())
                .error(R.drawable.default_hospital)
                .placeholder(R.drawable.default_hospital).into(imgHospital);
        tvName.setText(data.getHospitalname());
        tvAddress.setText("地址:" + data.getAddress());
        tvTel.setText(data.getTelephone());
        tvDesc.setText(data.getContents());


    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hospital_contents, contentLayout, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tel:
                String phoneNumber = tvTel.getText().toString().trim();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
                break;
        }
    }


    private void initView() {
        imgHospital = findViewById(R.id.iv_hospital);
        tvName = findViewById(R.id.tv_name);
        tvAddress = findViewById(R.id.tv_address);
        LinearLayout llTel = findViewById(R.id.ll_tel);
        tvTel = findViewById(R.id.tv_tel);
        tvDesc = findViewById(R.id.tv_desc);
        llTel.setOnClickListener(this);
    }
}
