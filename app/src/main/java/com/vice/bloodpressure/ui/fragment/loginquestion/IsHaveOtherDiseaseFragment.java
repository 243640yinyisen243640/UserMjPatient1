package com.vice.bloodpressure.ui.fragment.loginquestion;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.IsHaveOtherDiseaseMultiCheckAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  其它疾病?
 * 作者: LYD
 * 创建日期: 2020/3/30 13:44
 */
public class IsHaveOtherDiseaseFragment extends BaseFragment {
    private static final String TAG = "控制目标提交";
    @BindView(R.id.img_sugar_disease_back)
    ImageView imgSugarDiseaseBack;
    @BindView(R.id.tv_question_desc)
    TextView tvQuestionDesc;
    @BindView(R.id.img_check)
    ImageView imgCheck;
    @BindView(R.id.ll_check)
    LinearLayout llCheck;
    @BindView(R.id.rv_question)
    RecyclerView rvQuestion;
    @BindView(R.id.bt_next)
    ColorButton btNext;
    private List<String> selectDatas;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_is_have_other_disease;
    }

    @Override
    protected void init(View rootView) {
        tvQuestionDesc.setText("是否患有以下疾病？");
        llCheck.setTag(1);
        String type = getArguments().getString("type");
        setAdapter(type);
    }

    private void setAdapter(String type) {
        selectDatas = new ArrayList<>();
        String[] stringArray;
        if ("4".equals(type)) {
            stringArray = Utils.getApp().getResources().getStringArray(R.array.register_other_4);
        } else {
            stringArray = Utils.getApp().getResources().getStringArray(R.array.register_other_5);
        }
        List<String> list = Arrays.asList(stringArray);
        IsHaveOtherDiseaseMultiCheckAdapter adapter = new IsHaveOtherDiseaseMultiCheckAdapter(list);
        rvQuestion.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvQuestion.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                llCheck.setTag(1);
                imgCheck.setImageResource(R.drawable.sport_level_uncheck);
                if (!adapter.isSelected.get(position)) {
                    //修改map的值保存状态
                    adapter.isSelected.put(position, true);
                    adapter.notifyItemChanged(position);
                    selectDatas.add(position + "");
                } else {
                    //修改map的值保存状态
                    adapter.isSelected.put(position, false);
                    adapter.notifyItemChanged(position);
                    selectDatas.remove(position + "");
                }
                if (selectDatas.size() > 0) {
                    setBtCheck(1);
                } else {
                    setBtCheck(0);
                }
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.img_sugar_disease_back, R.id.ll_check, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_sugar_disease_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.ll_check:
                setCheck();
                break;
            case R.id.bt_next:
                toNext();
                break;
        }
    }

    private void setCheck() {
        String type = getArguments().getString("type");
        llCheck.setTag(2);
        imgCheck.setImageResource(R.drawable.sport_level_checked);
        setAdapter(type);
        setBtCheck(1);
    }


    private void setBtCheck(int type) {
        if (1 == type) {
            //设置未选中颜色
            btNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
            btNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
        } else {
            //设置选中颜色
            btNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
            btNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.color_666));
        }
    }

    private void toNext() {
        int tag = (int) llCheck.getTag();
        int size = selectDatas.size();
        if (1 == tag && 0 == size) {
            ToastUtils.showShort("请选择是否患有以下疾病？");
        } else {
            saveSPPostBean();
            //ToastUtils.showShort("调接口 进首页");
            toSetUserData();
        }
    }


    /**
     * 最后提交数据
     */
    private void toSetUserData() {
        String before = "";
        String after = "";
        String bp = "";
        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        //控制目标判断
        int gltype = bean.getGltype();
        switch (gltype) {
            //无
            case 0:
                before = BloodSugarControlTarget.getBeforeFromType(4);
                after = BloodSugarControlTarget.getAfterFromType(4);
                int size = selectDatas.size();
                if (size > 0) {
                    bp = BloodPressureControlTarget.getBpFromType(13);
                } else {
                    RegisterQuestionAddBean.LabelBean labelBean = bean.getLabells();
                    String nzzh = labelBean.getNzzh();
                    if ("23".equals(nzzh)) {
                        bp = BloodPressureControlTarget.getBpFromType(14);
                    } else {
                        String age = labelBean.getAge();
                        if ("7".equals(age)) {
                            bp = BloodPressureControlTarget.getBpFromType(15);
                        } else {
                            bp = BloodPressureControlTarget.getBpFromType(14);
                        }
                    }
                }
                break;
            //1型
            case 1:
                before = BloodSugarControlTarget.getBeforeFromType(1);
                after = BloodSugarControlTarget.getAfterFromType(1);
                bp = BloodPressureControlTarget.getBpFromType(13);
                break;
            //2型
            case 2:
                before = BloodSugarControlTarget.getBeforeFromType(2);
                after = BloodSugarControlTarget.getAfterFromType(2);
                bp = BloodPressureControlTarget.getBpFromType(13);
                break;
            //妊娠糖尿病
            case 3:
                before = BloodSugarControlTarget.getBeforeFromType(3);
                after = BloodSugarControlTarget.getAfterFromType(3);
                bp = BloodPressureControlTarget.getBpFromType(13);
                break;
        }

        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
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

    private void saveSPPostBean() {
        if (selectDatas.size() > 0) {
            RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
            RegisterQuestionAddBean.LabelBean labelBean = bean.getLabells();
            for (int i = 0; i < selectDatas.size(); i++) {
                String checkPosition = selectDatas.get(i);
                switch (checkPosition) {
                    case "0":
                        labelBean.setGxy("21");
                        break;
                    case "1":
                        labelBean.setGxb("22");
                        break;
                    case "2":
                        labelBean.setMxzs("24");
                        break;
                    case "3":
                        labelBean.setNzzh("23");
                        break;
                    case "4":
                        labelBean.setTnbqq("16");
                        break;
                }
            }
            bean.setLabells(labelBean);
            SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
        }
    }
}
