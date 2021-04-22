package com.vice.bloodpressure.ui.fragment.sport;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.TestOfHbpQuestionDescAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  运动答题之 第一道
 * 作者: LYD
 * 创建日期: 2020/9/25 15:57
 */
public class SportQuestionOneFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.rv_question_one)
    RecyclerView rvQuestionOne;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_question_one;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_TITLE, "1"));
        setTitleAndDesc();
        setRv();
    }

    private void setTitleAndDesc() {
        SpanUtils.with(tvTitle)
                .append("1").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/5").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setText("是否符合制备每日运动的条件？");
    }

    private void setRv() {
        String[] questionDesc = getResources().getStringArray(R.array.sport_question_one);
        List<String> listQuestion = Arrays.asList(questionDesc);
        TestOfHbpQuestionDescAdapter adapter = new TestOfHbpQuestionDescAdapter(listQuestion);
        rvQuestionOne.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvQuestionOne.setAdapter(adapter);
    }



    @OnClick({R.id.tv_not_conform, R.id.tv_conform})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_not_conform:
                toSubmitNotConform();
                break;
            case R.id.tv_conform:
                SportQuestionTwoFragment questionTwoFragment = new SportQuestionTwoFragment();
                FragmentUtils.replace(getParentFragmentManager(), questionTwoFragment, R.id.ll_fragment, true);
                break;
        }
    }


    /**
     * 不适合运动提交
     */
    private void toSubmitNotConform() {
        Map<String, Object> map = new HashMap<>();
        map.put("issport", "2");
        XyUrl.okPostSave(XyUrl.INDEX_ADD_SPORT_QUESTION, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                ActivityUtils.finishToActivity(MainActivity.class, false);
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_SUBMIT));
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
