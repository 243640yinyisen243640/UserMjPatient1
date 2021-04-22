package com.vice.bloodpressure.ui.activity.nondrug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPStaticUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 药物处方选择
 * 作者: LYD
 * 创建日期: 2019/5/5 10:09
 */
public class SelectPrescriptionActivity extends BaseActivity {
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.cb_sport)
    CheckBox cbSport;
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    @BindView(R.id.cb_education)
    CheckBox cbEducation;
    @BindView(R.id.cb_complication)
    CheckBox cbComplication;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsWeight();
        setTitleAndBanner();
    }

    private void setTitleAndBanner() {
        String type = getIntent().getStringExtra("type");
        if ("weight".equals(type)) {
            setTitle("减重方案");
            imgBanner.setImageResource(R.drawable.select_prescription_banner_reduce_weight);
        } else {
            setTitle("处方选择");
            imgBanner.setImageResource(R.drawable.select_prescription_banner);
        }
    }

    private void setIsWeight() {
        String type = getIntent().getStringExtra("type");
        if ("weight".equals(type)) {
            llWeight.setVisibility(View.GONE);
        } else {
            llWeight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_select_prescription, contentLayout, false);
        return layout;
    }


    @OnClick({R.id.ll_sport, R.id.ll_check, R.id.ll_education, R.id.ll_complication, R.id.bt_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sport:
                if (cbSport.isChecked()) {
                    cbSport.setChecked(false);
                } else {
                    cbSport.setChecked(true);
                }
                break;
            case R.id.ll_check:
                if (cbCheck.isChecked()) {
                    cbCheck.setChecked(false);
                } else {
                    cbCheck.setChecked(true);
                }
                break;
            case R.id.ll_education:
                if (cbEducation.isChecked()) {
                    cbEducation.setChecked(false);
                } else {
                    cbEducation.setChecked(true);
                }
                break;
            case R.id.ll_complication:
                if (cbComplication.isChecked()) {
                    cbComplication.setChecked(false);
                } else {
                    cbComplication.setChecked(true);
                }
                break;
            case R.id.bt_start:
                boolean sportState = cbSport.isChecked();
                boolean checkState = cbCheck.isChecked();
                boolean educationState = cbEducation.isChecked();
                boolean complicationState = cbComplication.isChecked();
                if (sportState) {
                    SPStaticUtils.put("sportState", "1");
                } else {
                    SPStaticUtils.put("sportState", "0");
                }
                if (checkState) {
                    SPStaticUtils.put("checkState", "1");
                } else {
                    SPStaticUtils.put("checkState", "0");
                }
                if (educationState) {
                    SPStaticUtils.put("educationState", "1");
                } else {
                    SPStaticUtils.put("educationState", "0");
                }
                if (complicationState) {
                    SPStaticUtils.put("complicationState", "1");
                } else {
                    SPStaticUtils.put("complicationState", "0");
                }
                Intent intent = new Intent(getPageContext(), FoodPrescriptionFirstActivity.class);
                intent.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(intent);
                break;
        }
    }
}
