package com.vice.bloodpressure.ui.activity.smarteducation;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.SegmentTabLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.smarteducation.SmartEducationClassifyFragment;
import com.vice.bloodpressure.ui.fragment.smarteducation.SmartEducationLearnListFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述:  智能教育 学习列表和分类页面
 * 作者: LYD
 * 创建日期: 2020/8/11 17:30
 */
public class SmartEducationMainActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    @BindView(R.id.stl)
    SegmentTabLayout stl;
    @BindView(R.id.fl_fragment_container)
    FrameLayout fragmentContainer;
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] mTitles = {"学习列表", "分类"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能教育");
        setTableLayout();
    }


    private void setTableLayout() {
        listFragment.add(new SmartEducationLearnListFragment());
        listFragment.add(new SmartEducationClassifyFragment());
        stl.setTabData(mTitles, this, R.id.fl_fragment_container, listFragment);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_education_main, contentLayout, false);
        return view;
    }


    @Override
    public void processHandlerMsg(Message msg) {

    }
}