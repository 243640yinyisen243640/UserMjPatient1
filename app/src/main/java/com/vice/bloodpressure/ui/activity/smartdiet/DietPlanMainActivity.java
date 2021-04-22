package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.wei.android.lib.colorview.view.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  智能食谱
 * 作者: LYD
 * 创建日期: 2020/3/13 9:48
 */
public class DietPlanMainActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10086;
    private static final int GET_DATA_ERROR = 10087;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能食谱");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_diet_main, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.tv_next)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                Intent intent = new Intent(getPageContext(), DietPlanQuestionActivity.class);
                startActivity(intent);
                break;
        }
    }


}
