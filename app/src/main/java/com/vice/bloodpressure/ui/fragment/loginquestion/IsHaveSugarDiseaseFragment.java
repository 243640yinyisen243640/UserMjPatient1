package com.vice.bloodpressure.ui.fragment.loginquestion;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RegisterQuestionSingleCheckAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.RegisterQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 是否患有糖尿病？
 * 作者: LYD
 * 创建日期: 2020/8/11 13:37
 */
public class IsHaveSugarDiseaseFragment extends BaseFragment {
    @BindView(R.id.img_sugar_disease_back)
    ImageView imgSugarDiseaseBack;
    @BindView(R.id.tv_question_desc)
    TextView tvQuestionDesc;
    @BindView(R.id.rv_question)
    RecyclerView rvQuestion;
    @BindView(R.id.bt_next)
    ColorButton btNext;


    private int selectPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_is_have_sugar_disease;
    }

    @Override
    protected void init(View rootView) {
        imgSugarDiseaseBack.setVisibility(View.INVISIBLE);
        tvQuestionDesc.setText("是否患有糖尿病？");
        setAdapter();
    }

    private void setAdapter() {
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.is_have_sugar_disease);
        List<String> list = Arrays.asList(stringArray);
        RegisterQuestionSingleCheckAdapter adapter = new RegisterQuestionSingleCheckAdapter(list);
        rvQuestion.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvQuestion.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                selectPosition = position;
                //设置选中颜色
                btNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                btNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }


    @OnClick({R.id.img_sugar_disease_back, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_sugar_disease_back:
                break;
            case R.id.bt_next:
                toNext();
                break;
        }
    }

    private void toNext() {
        if (-1 == selectPosition) {
            ToastUtils.showShort("请选择是否患有糖尿病？");
            return;
        } else {
            saveSPPostBean();
            //判断跳转
            judgeToJump();
        }
    }

    private void judgeToJump() {
        if (1 == selectPosition) {
            //否 患有
            Bundle bundle = new Bundle();
            bundle.putString("type", 3 + "");
            SelectAgeRangeFragment ageFragment = new SelectAgeRangeFragment();
            ageFragment.setArguments(bundle);
            FragmentUtils.replace(getParentFragmentManager(), ageFragment, R.id.fl_question, true);
        } else {
            //是 患有
            IsHaveKnowSugarDiseaseKnowLedgeFragment ageFragment = new IsHaveKnowSugarDiseaseKnowLedgeFragment();
            FragmentUtils.replace(getParentFragmentManager(), ageFragment, R.id.fl_question, true);
        }
    }

    private void saveSPPostBean() {
        RegisterQuestionAddBean bean = new RegisterQuestionAddBean();
        RegisterQuestionAddBean.LabelBean lableBean = new RegisterQuestionAddBean.LabelBean();
        bean.setLabells(lableBean);
        if (1 == selectPosition) {
            bean.setGltype(0);
        }
        SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
    }
}
