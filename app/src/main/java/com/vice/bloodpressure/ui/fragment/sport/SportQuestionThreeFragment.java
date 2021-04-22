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
 * 描述:  运动答题之 第三道
 * 作者: LYD
 * 创建日期: 2020/9/25 16:43
 */
public class SportQuestionThreeFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ruler_height)
    RulerView rulerHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ruler_weight)
    RulerView rulerWeight;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_question_three;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "3"));
        setTitleAndDesc();
        setRulerListener();
    }


    private void setTitleAndDesc() {
        SpanUtils.with(tvTitle)
                .append("3").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/5").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setText("您的身高是？");
    }

    private void setRulerListener() {
        rulerHeight.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvHeight.setText(floatStringToIntString(result));
            }
        });
        rulerWeight.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvWeight.setText(floatStringToIntString(result));
            }
        });

    }


    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        HomeSportQuestionAddBean questionBean = (HomeSportQuestionAddBean) SPUtils.getBean("sportQuestion");
        questionBean.setHeight(tvHeight.getText().toString().trim());
        questionBean.setWeight(tvWeight.getText().toString().trim());
        SPUtils.putBean("sportQuestion", questionBean);

        SportQuestionFourFragment questionFourFragment = new SportQuestionFourFragment();
        FragmentUtils.replace(getParentFragmentManager(), questionFourFragment, R.id.ll_fragment, true);
    }


    /**
     * 666
     *
     * @param floatString
     * @return
     */
    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }


    @Override
    public void processHandlerMsg(Message msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "2"));
    }
}
