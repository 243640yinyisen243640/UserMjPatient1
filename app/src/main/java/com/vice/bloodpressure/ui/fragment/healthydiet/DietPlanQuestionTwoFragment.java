package com.vice.bloodpressure.ui.fragment.healthydiet;

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
import com.vice.bloodpressure.bean.DietPlanQuestionBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  答题第二道(选择身高体重)
 * 作者: LYD
 * 创建日期: 2019/12/17 13:26
 */
public class DietPlanQuestionTwoFragment extends BaseFragment {
    private static final String TAG = "DietPlanQuestionTwoFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ruler_height)
    RulerView rulerHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ruler_weight)
    RulerView rulerWeight;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diet_plan_question_two;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_QUESTION_TITLE, "2"));
        setTitleAndDesc();
        setRulerListener();
    }

    private void setTitleAndDesc() {
        SpanUtils.with(tvTitle)
                .append("2").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/4").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setText("您的身高是？");
    }


    /**
     * 设置尺子滑动监听
     */
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

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        DietPlanQuestionBean bean = (DietPlanQuestionBean) SPUtils.getBean("Diet_Question");
        String height = tvHeight.getText().toString().trim();
        String weight = tvWeight.getText().toString().trim();
        bean.setHeight(height);
        bean.setWeight(weight);
        SPUtils.putBean("Diet_Question", bean);

        DietPlanQuestionThreeFragment questionThreeFragment = new DietPlanQuestionThreeFragment();
        FragmentUtils.replace(getParentFragmentManager(), questionThreeFragment, R.id.fl_question, true);
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
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_QUESTION_TITLE, "1"));
    }
}
