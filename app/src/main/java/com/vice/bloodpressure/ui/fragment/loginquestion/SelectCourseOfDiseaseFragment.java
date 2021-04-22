package com.vice.bloodpressure.ui.fragment.loginquestion;

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
 * 描述:  选择病程
 * 作者: LYD
 * 创建日期: 2020/3/30 13:41
 */
public class SelectCourseOfDiseaseFragment extends BaseFragment {
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
        return R.layout.fragment_select_course_of_disease;
    }

    @Override
    protected void init(View rootView) {
        tvQuestionDesc.setText("请选择病程...");
        String type = getArguments().getString("type");
        setAdapter(type);
    }

    private void setAdapter(String type) {
        String[] stringArray;
        if ("15".equals(type)) {
            stringArray = Utils.getApp().getResources().getStringArray(R.array.register_course_of_disease_2);
        } else {
            stringArray = Utils.getApp().getResources().getStringArray(R.array.register_course_of_disease_1);
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
            ToastUtils.showShort("请选择病程...");
            return;
        } else {
            saveSPPostBean();
            //judgeToJump();
            toJumpToSugarUnit();
        }
    }

    private void saveSPPostBean() {

        RegisterQuestionAddBean bean = (RegisterQuestionAddBean) SPUtils.getBean(ConstantParam.REGISTER_QUESTION_KEY);
        RegisterQuestionAddBean.LabelBean lableBean = bean.getLabells();
        String type = getArguments().getString("type");
        if ("15".equals(type)) {
            switch (selectPosition) {
                case 0:
                    lableBean.setTnbbch("11");
                    break;
                case 1:
                    lableBean.setTnbbch("12");
                    break;
                case 2:
                    lableBean.setTnbbch("13");
                    break;
            }
        } else {
            switch (selectPosition) {
                case 0:
                    lableBean.setTnbbch("14");
                    break;
                case 1:
                    lableBean.setTnbbch("15");
                    break;
                case 2:
                    lableBean.setTnbbch("13");
                    break;
            }
        }
        bean.setLabells(lableBean);
        SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
    }


    /**
     * 跳转并发症
     */
    private void toJumpToSugarUnit() {
        IsHaveSugarUnitFragment sugarUnitFragment = new IsHaveSugarUnitFragment();
        FragmentUtils.replace(getParentFragmentManager(), sugarUnitFragment, R.id.fl_question, true);
    }


}
