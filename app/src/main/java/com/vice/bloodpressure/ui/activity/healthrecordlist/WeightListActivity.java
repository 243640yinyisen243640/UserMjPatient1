package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.healthweight.WeightChartFragment;
import com.vice.bloodpressure.ui.fragment.healthweight.WeightListFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  体重列表
 * 作者: LYD
 * 创建日期: 2020/5/9 14:12
 */
public class WeightListActivity extends BaseHandlerActivity {


    @BindView(R.id.bt_back_weight)
    Button btBack;
    @BindView(R.id.tv_title_weight)
    TextView tvTitle;
    @BindView(R.id.img_weight_list)
    ImageView imgWeightList;
    @BindView(R.id.img_weight_chart)
    ImageView imgWeightChart;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        tvTitle.setText("体重记录");
        addFirstFragment();
    }

    private void addFirstFragment() {
        WeightListFragment weightListFragment = new WeightListFragment();
        FragmentUtils.replace(getSupportFragmentManager(), weightListFragment, R.id.fl_content, false);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_weight_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.bt_back_weight, R.id.img_weight_list, R.id.img_weight_chart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back_weight:
                finish();
                break;
            case R.id.img_weight_list:
                imgWeightList.setImageResource(R.drawable.weight_list_check);
                imgWeightChart.setImageResource(R.drawable.weight_chart_uncheck);
                WeightListFragment weightListFragment = new WeightListFragment();
                FragmentUtils.replace(getSupportFragmentManager(), weightListFragment, R.id.fl_content, false);
                break;
            case R.id.img_weight_chart:
                imgWeightList.setImageResource(R.drawable.weight_list_uncheck);
                imgWeightChart.setImageResource(R.drawable.weight_chart_check);
                WeightChartFragment weightChartFragment = new WeightChartFragment();
                FragmentUtils.replace(getSupportFragmentManager(), weightChartFragment, R.id.fl_content, false);
                break;
        }
    }
}
