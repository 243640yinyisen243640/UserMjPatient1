package com.vice.bloodpressure.ui.fragment.healthydiet;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RvSportLevelAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.DietPlanQuestionBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 答题第三道(职业情况)
 * 作者: LYD
 * 创建日期: 2019/12/17 16:28
 */
public class DietPlanQuestionThreeFragment extends BaseFragment {
    private static final String TAG = "DietPlanQuestionThreeFragment";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;
    @BindView(R.id.rv_sport_level)
    RecyclerView rvSportLevel;
    private RvSportLevelAdapter adapter;
    private String selectPosition = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diet_plan_question_three;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_QUESTION_TITLE, "3"));
        setTitleAndDesc();
        setRv();
    }

    private void setRv() {
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.sport_level_title);
        List<String> list = Arrays.asList(listString);
        adapter = new RvSportLevelAdapter(list);
        rvSportLevel.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSportLevel.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                selectPosition = position + 1 + "";
                //设置选中颜色
                tvNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                tvNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
            }
        });
    }

    private void setTitleAndDesc() {
        SpanUtils.with(tvTitle)
                .append("3").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/4").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setVisibility(View.GONE);
    }


    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        if (TextUtils.isEmpty(selectPosition)) {
            ToastUtils.showShort("请选择活动强度");
            return;
        }
        DietPlanQuestionBean bean = (DietPlanQuestionBean) SPUtils.getBean("Diet_Question");
        bean.setProfession(selectPosition);
        SPUtils.putBean("Diet_Question", bean);
        DietPlanQuestionFourFragment questionFourFragment = new DietPlanQuestionFourFragment();
        FragmentUtils.replace(getParentFragmentManager(), questionFourFragment, R.id.fl_question, true);
    }


    @Override
    public void processHandlerMsg(Message msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_QUESTION_TITLE, "2"));
    }
}
