package com.vice.bloodpressure.ui.fragment.loginquestion;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.IsHaveSugarUnitMultiCheckAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.RegisterQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  糖尿病并发症
 * 作者: LYD
 * 创建日期: 2020/3/30 13:44
 */
public class IsHaveSugarUnitFragment extends BaseFragment {
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
        return R.layout.fragment_is_have_sugar_unit;
    }

    @Override
    protected void init(View rootView) {
        tvQuestionDesc.setText("是否患有糖尿病并发症？");
        llCheck.setTag(1);
        setAdapter();
    }

    private void setAdapter() {
        selectDatas = new ArrayList<>();
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.register_sugar_unit);
        List<String> list = Arrays.asList(stringArray);
        IsHaveSugarUnitMultiCheckAdapter adapter = new IsHaveSugarUnitMultiCheckAdapter(list);
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


    private void setBtCheck(int type) {
        if (1 == type) {
            //设置选中颜色
            btNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
            btNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
        } else {
            //设置未选中颜色
            btNext.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
            btNext.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.color_666));
        }
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


    private void toNext() {
        int tag = (int) llCheck.getTag();
        int size = selectDatas.size();
        if (1 == tag && 0 == size) {
            ToastUtils.showShort("请选择是否患有糖尿病并发症？");
        } else {
            saveSPPostBean();
            toJumpToIsHaveOther();
        }
    }

    private void saveSPPostBean() {
        //设置糖尿病并发症
        if (selectDatas.size() > 0) {
            RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
            RegisterQuestionAddBean.LabelBean labelBean = bean.getLabells();
            for (int i = 0; i < selectDatas.size(); i++) {
                String checkPosition = selectDatas.get(i);
                switch (checkPosition) {
                    //糖尿病病足
                    case "0":
                        labelBean.setTnbbz("19");
                        break;
                    //糖尿病肾病
                    case "1":
                        labelBean.setTnbsb("17");
                        break;
                    //糖尿病眼病
                    case "2":
                        labelBean.setTnbyb("18");
                        break;
                    //糖尿病神经病变
                    case "3":
                        labelBean.setTnbsjb("9");
                        break;
                    //糖尿病下肢血管病变
                    case "4":
                        labelBean.setTnbxz("20");
                        break;
                }
            }
            bean.setLabells(labelBean);
            SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
        }
    }

    private void toJumpToIsHaveOther() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "4");
        IsHaveOtherDiseaseFragment otherFragment = new IsHaveOtherDiseaseFragment();
        otherFragment.setArguments(bundle);
        FragmentUtils.replace(getParentFragmentManager(), otherFragment, R.id.fl_question, true);
    }

    /**
     *
     */
    private void setCheck() {
        llCheck.setTag(2);
        imgCheck.setImageResource(R.drawable.sport_level_checked);
        setAdapter();
        //设置选中颜色
        setBtCheck(1);
        //清除本地
        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        RegisterQuestionAddBean.LabelBean labelBean = bean.getLabells();
        //糖尿病病足
        labelBean.setTnbbz(null);
        //糖尿病肾病
        labelBean.setTnbsb(null);
        //糖尿病眼病
        labelBean.setTnbyb(null);
        //糖尿病神经病变
        labelBean.setTnbsjb(null);
        //糖尿病下肢血管病变
        labelBean.setTnbxz(null);
        SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
    }
}
