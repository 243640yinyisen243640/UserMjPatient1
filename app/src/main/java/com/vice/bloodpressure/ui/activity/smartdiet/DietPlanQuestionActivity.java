package com.vice.bloodpressure.ui.activity.smartdiet;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.FragmentUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.enumuse.DietPlanQuestionTitle;
import com.vice.bloodpressure.ui.fragment.healthydiet.DietPlanQuestionOneFragment;

/**
 * 描述: 饮食方案答题
 * 作者: LYD
 * 创建日期: 2019/12/17 10:51
 */
public class DietPlanQuestionActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("饮食方案");
        addFirstFragment();
        getBack().setOnClickListener(this);
    }


    /**
     * 添加第一个fragment
     */
    private void addFirstFragment() {
        DietPlanQuestionOneFragment questionOneFragment = new DietPlanQuestionOneFragment();
        FragmentUtils.replace(getSupportFragmentManager(), questionOneFragment, R.id.fl_question, false);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_diet_plan_question, contentLayout, false);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                toBack();
                break;
        }

    }

    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toBack() {
        //这里是取出我们返回栈存在Fragment的个数
        if (getSupportFragmentManager().getBackStackEntryCount() <= 0)
            finish();
        else {
            //取出我们返回栈保存的Fragment,这里会从栈顶开始弹栈
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.DIET_PLAN_QUESTION_TITLE:
                String position = event.getMsg();
                int number = Integer.parseInt(position);
                String title = DietPlanQuestionTitle.getTitleFromNumber(number);
                setTitle(title);
                break;
        }
    }
}
