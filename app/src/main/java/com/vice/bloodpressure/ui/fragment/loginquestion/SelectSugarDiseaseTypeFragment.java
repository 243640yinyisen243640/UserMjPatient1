package com.vice.bloodpressure.ui.fragment.loginquestion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RegisterQuestionSingleCheckAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.RegisterQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.enumuse.BloodPressureControlTarget;
import com.vice.bloodpressure.enumuse.BloodSugarControlTarget;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.user.CompletePersonalInfoActivity;
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  选择糖尿病类型?
 * 作者: LYD
 * 创建日期: 2020/3/30 13:36
 */
public class SelectSugarDiseaseTypeFragment extends BaseFragment {
    private static final String TAG = "控制目标提交";
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
        return R.layout.fragment_select_sugar_disease_type;
    }

    @Override
    protected void init(View rootView) {
        imgSugarDiseaseBack.setVisibility(View.INVISIBLE);
        tvQuestionDesc.setText("请选择糖尿病类型？");
        setAdapter();
    }

    private void setAdapter() {
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.register_sugar_type);
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

    /**
     * 下一步操作
     */
    private void toNext() {
        if (-1 == selectPosition) {
            ToastUtils.showShort("请选择糖尿病类型?");
            return;
        } else {
            saveSPPostBean();
            //判断跳转
            judgeToJump();
        }
    }

    /**
     * 保存SP
     */
    private void saveSPPostBean() {
        RegisterQuestionAddBean bean = new RegisterQuestionAddBean();
        RegisterQuestionAddBean.LabelBean lableBean = new RegisterQuestionAddBean.LabelBean();
        bean.setLabells(lableBean);
        if (4 == selectPosition) {
            bean.setGltype(0);
        } else {
            bean.setGltype(selectPosition + 1);
        }
        SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
    }

    /**
     * 判断跳转
     */
    private void judgeToJump() {
        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        int gltype = bean.getGltype();
        switch (gltype) {
            //无  年龄3
            case 0:
                toJumpToAge(3);
                break;
            //1型 年龄2
            case 1:
                toJumpToAge(2);
                break;
            //2型 年龄3
            case 2:
                toJumpToAge(3);
                break;
            //妊娠糖尿病  进选择是否有孕前
            case 3:
                IsHavePregnantFragment pregnantFragment = new IsHavePregnantFragment();
                FragmentUtils.replace(getParentFragmentManager(), pregnantFragment, R.id.fl_question, true);
                break;
            //其他 进首页
            case 4:
                toSetUserData();
                break;
        }
    }


    /**
     * 去跳转
     *
     * @param type
     */
    private void toJumpToAge(int type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type + "");
        SelectAgeRangeFragment ageFragment = new SelectAgeRangeFragment();
        ageFragment.setArguments(bundle);
        FragmentUtils.replace(getParentFragmentManager(), ageFragment, R.id.fl_question, true);
    }


    /**
     * 保存用户数据
     */
    private void toSetUserData() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);

        String before = BloodSugarControlTarget.getBeforeFromType(1);
        String after = BloodSugarControlTarget.getAfterFromType(1);
        String bp = BloodPressureControlTarget.getBpFromType(13);


        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        bean.setAccess_token(user.getToken());
        bean.setGlbefore(before);
        bean.setGllater(after);
        bean.setBp(bp);

        String jsonResult = JSON.toJSONString(bean);
        XyUrl.okPostJson(XyUrl.SET_USERDATA, jsonResult, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                user.setIs_one("2");
                SharedPreferencesUtils.putBean(getActivity(), SharedPreferencesUtils.USER_INFO, user);
                startActivity(new Intent(getPageContext(), CompletePersonalInfoActivity.class));
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }
}
