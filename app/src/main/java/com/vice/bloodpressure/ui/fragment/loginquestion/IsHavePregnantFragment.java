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
import com.blankj.utilcode.util.TimeUtils;
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
import com.vice.bloodpressure.utils.TimeFormatUtils;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  孕前是否有糖尿病?
 * 作者: LYD
 * 创建日期: 2020/3/30 13:36
 */
public class IsHavePregnantFragment extends BaseFragment {
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
        return R.layout.fragment_is_have_pregnant;
    }

    @Override
    protected void init(View rootView) {
        tvQuestionDesc.setText("孕前是否有糖尿病？");
        setAdapter();
    }

    private void setAdapter() {
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.register_pregnant_sugar_type);
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
            ToastUtils.showShort("请选择孕前糖尿病类型");
            return;
        } else {
            saveSPPostBean();
            judgeToJump();
        }
    }

    private void saveSPPostBean() {
        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        RegisterQuestionAddBean.LabelBean labelBean = bean.getLabells();
        if (3 == selectPosition) {
            labelBean.setTnbrshq(4);
        } else {
            labelBean.setTnbrshq(selectPosition);
        }
        //获取当前时间
        long timeMs = TimeUtils.string2Millis(TimeUtils.getNowString(), TimeFormatUtils.getDefaultFormat());
        long timeS = timeMs / 1000;
        labelBean.setTnbrshtime(timeS + "");
        bean.setLabells(labelBean);
        SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
    }


    private void judgeToJump() {
        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        RegisterQuestionAddBean.LabelBean labelBean = bean.getLabells();
        int tnbrshq = labelBean.getTnbrshq();
        switch (tnbrshq) {
            case 0:
                toJumpToAge(3);
                break;
            case 1:
                toJumpToAge(2);
                break;
            case 2:
                toJumpToAge(3);
                break;
            case 4:
                toSetUserData();
                //ToastUtils.showShort("调接口 进首页");
                break;
        }
    }

    private void toSetUserData() {

        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);

        String before = BloodSugarControlTarget.getBeforeFromType(3);
        String after = BloodSugarControlTarget.getAfterFromType(3);
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

    private void toJumpToAge(int type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type + "");
        SelectAgeRangeFragment ageFragment = new SelectAgeRangeFragment();
        ageFragment.setArguments(bundle);
        FragmentUtils.replace(getParentFragmentManager(), ageFragment, R.id.fl_question, true);
    }
}
