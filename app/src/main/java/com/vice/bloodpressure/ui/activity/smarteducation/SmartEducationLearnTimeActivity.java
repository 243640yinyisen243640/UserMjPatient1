package com.vice.bloodpressure.ui.activity.smarteducation;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.SegmentTabLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.smarteducation.SmartEducationLearnTimeChartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 描述: 学习时长统计
 * 作者: LYD
 * 创建日期: 2020/8/12 17:00
 */
public class SmartEducationLearnTimeActivity extends BaseHandlerActivity {
    @BindView(R.id.stl)
    SegmentTabLayout stl;
    @BindView(R.id.fl_chart)
    FrameLayout flChart;
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] mTitles = {"近7天", "近半月", "近1月"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("学习时长统计");
        setTableLayout();
    }

    private void setTableLayout() {
        List<String> listStr = new ArrayList<>();
        listStr.add("7");
        listStr.add("15");
        listStr.add("30");
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("day", listStr.get(i));
            SmartEducationLearnTimeChartFragment fragment = new SmartEducationLearnTimeChartFragment();
            listFragment.add(fragment);
            fragment.setArguments(bundle);
        }
        stl.setTabData(mTitles, this, R.id.fl_chart, listFragment);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_education_learn_time, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}