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
 * 描述:  选择年龄范围
 * 作者: LYD
 * 创建日期: 2020/3/30 13:41
 */
public class SelectAgeRangeFragment extends BaseFragment {
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
        return R.layout.fragment_select_age_range;
    }

    @Override
    protected void init(View rootView) {
        tvQuestionDesc.setText("请选择年龄范围...");
        String type = getArguments().getString("type");
        setAdapter(type);
    }

    private void setAdapter(String type) {
        String[] stringArray;
        if ("2".equals(type)) {
            stringArray = Utils.getApp().getResources().getStringArray(R.array.register_age_range_1);
        } else {
            stringArray = Utils.getApp().getResources().getStringArray(R.array.register_age_range_2);
        }
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
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.bt_next:
                toNext();
                break;
        }
    }

    private void toNext() {
        if (-1 == selectPosition) {
            ToastUtils.showShort("请选择年龄范围...");
            return;
        } else {
            saveSPPostBean();
            judgeToJump();
        }
    }

    private void saveSPPostBean() {
        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        RegisterQuestionAddBean.LabelBean lableBean = bean.getLabells();
        String type = getArguments().getString("type");
        if ("2".equals(type)) {
            if (0 == selectPosition) {
                lableBean.setAge("5");
            } else {
                lableBean.setAge("6");
            }
        } else {
            switch (selectPosition) {
                case 0:
                    lableBean.setAge("8");
                    break;
                case 1:
                    lableBean.setAge("10");
                    break;
                case 2:
                    lableBean.setAge("7");
                    break;
            }
        }
        bean.setLabells(lableBean);
        SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
    }

    private void judgeToJump() {
        //先判断有无糖尿病
        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        int gltype = bean.getGltype();
        switch (gltype) {
            //没有糖尿病
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("type", "5");
                IsHaveOtherDiseaseFragment otherFragment = new IsHaveOtherDiseaseFragment();
                otherFragment.setArguments(bundle);
                FragmentUtils.replace(getParentFragmentManager(), otherFragment, R.id.fl_question, true);
                break;
            //有糖尿病
            //1型糖尿病
            case 1:
                Bundle b1 = new Bundle();
                b1.putString("type", "25");
                SelectCourseOfDiseaseFragment c1Fragment = new SelectCourseOfDiseaseFragment();
                c1Fragment.setArguments(b1);
                FragmentUtils.replace(getParentFragmentManager(), c1Fragment, R.id.fl_question, true);
                break;
            //2型糖尿病
            case 2:
                Bundle b2 = new Bundle();
                b2.putString("type", "15");
                SelectCourseOfDiseaseFragment c2Fragment = new SelectCourseOfDiseaseFragment();
                c2Fragment.setArguments(b2);
                FragmentUtils.replace(getParentFragmentManager(), c2Fragment, R.id.fl_question, true);
                break;
            //妊娠糖尿病
            case 3:
                RegisterQuestionAddBean.LabelBean labelBean = bean.getLabells();
                int tnbrshq = labelBean.getTnbrshq();
                switch (tnbrshq) {
                    //没有妊娠糖尿病
                    case 0:
                        Bundle b35 = new Bundle();
                        b35.putString("type", "5");
                        IsHaveOtherDiseaseFragment other35Fragment = new IsHaveOtherDiseaseFragment();
                        other35Fragment.setArguments(b35);
                        FragmentUtils.replace(getParentFragmentManager(), other35Fragment, R.id.fl_question, true);
                        break;
                    //1型
                    case 1:
                        Bundle b11 = new Bundle();
                        b11.putString("type", "25");
                        SelectCourseOfDiseaseFragment c11Fragment = new SelectCourseOfDiseaseFragment();
                        c11Fragment.setArguments(b11);
                        FragmentUtils.replace(getParentFragmentManager(), c11Fragment, R.id.fl_question, true);
                        break;
                    //2型
                    case 2:
                        Bundle b22 = new Bundle();
                        b22.putString("type", "15");
                        SelectCourseOfDiseaseFragment c22Fragment = new SelectCourseOfDiseaseFragment();
                        c22Fragment.setArguments(b22);
                        FragmentUtils.replace(getParentFragmentManager(), c22Fragment, R.id.fl_question, true);
                        break;
                }
                break;

        }
    }


}
