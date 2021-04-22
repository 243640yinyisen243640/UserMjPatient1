package com.vice.bloodpressure.ui.fragment.sport;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.lsp.RulerView;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.HomeSportQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  运动答题之 第二道
 * 作者: LYD
 * 创建日期: 2020/9/25 16:32
 */
public class SportQuestionTwoFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_age_desc)
    TextView tvAgeDesc;
    @BindView(R.id.ruler_age)
    RulerView rulerViewAge;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_question_two;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "2"));
        initRulerView();
        setTitleAndDesc("2");
        tvAgeDesc.setText("岁");
    }

    private void setTitleAndDesc(String type) {
        SpanUtils.with(tvTitle)
                .append(type).setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/5").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setText("您的年龄是？");
    }

    private void initRulerView() {
        rulerViewAge.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvAge.setText(floatStringToIntString(result));
            }
        });
    }

    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        HomeSportQuestionAddBean questionBean = new HomeSportQuestionAddBean();
        questionBean.setAge(tvAge.getText().toString().trim());
        SPUtils.putBean("sportQuestion", questionBean);
        SportQuestionThreeFragment questionThreeFragment = new SportQuestionThreeFragment();
        FragmentUtils.replace(getParentFragmentManager(), questionThreeFragment, R.id.ll_fragment, true);
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "1"));
    }
}
