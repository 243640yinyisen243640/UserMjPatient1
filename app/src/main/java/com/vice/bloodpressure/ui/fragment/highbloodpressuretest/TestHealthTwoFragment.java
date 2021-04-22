package com.vice.bloodpressure.ui.fragment.highbloodpressuretest;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.TestOfHbpAdapter;
import com.vice.bloodpressure.adapter.TestOfHbpQuestionDescAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.TestBean;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.smartmakepolicy.HbpDetailActivity;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 健康信息之本地答题
 * 作者: LYD
 * 创建日期: 2020/6/18 14:54
 */
public class TestHealthTwoFragment extends BaseFragment {
    private static final String TAG = "TestHealthTwoFragment";
    private static final int OVER = 10010;
    @BindView(R.id.tv_top_desc)
    TextView tvTopDesc;
    @BindView(R.id.rv_desc)
    RecyclerView rvDesc;
    @BindView(R.id.rv_question)
    RecyclerView rvQuestion;
    @BindView(R.id.bt_next_question)
    ColorButton btNextQuestion;
    //问题排序
    private int questionNumber;
    private int selectPosition = -1;

    public TestHealthTwoFragment(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_two_of_hbp;
    }

    @Override
    protected void init(View rootView) {
        setTopDesc();
        setRvDesc();
        setRvQuestion();
    }


    /**
     * 设置头部标题
     */
    private void setTopDesc() {
        String[] stringArray = getResources().getStringArray(R.array.test_of_hbp_question_number);
        List<String> listStr = Arrays.asList(stringArray);
        tvTopDesc.setText(listStr.get(questionNumber - 1));
    }

    /**
     * 设置题目描述
     */
    private void setRvDesc() {
        String[] questionDesc = null;
        switch (questionNumber) {
            case 2:
                rvDesc.setVisibility(View.GONE);
                break;
            case 3:
                rvDesc.setVisibility(View.VISIBLE);
                questionDesc = getResources().getStringArray(R.array.test_three_of_hbp_question_top_desc);
                break;
            case 4:
                rvDesc.setVisibility(View.VISIBLE);
                questionDesc = getResources().getStringArray(R.array.test_four_of_hbp_question_top_desc);
                break;
            case 5:
                rvDesc.setVisibility(View.VISIBLE);
                questionDesc = getResources().getStringArray(R.array.test_five_of_hbp_question_top_desc);
                break;
        }
        if (questionDesc != null) {
            List<String> listQuestion = Arrays.asList(questionDesc);
            TestOfHbpQuestionDescAdapter adapter = new TestOfHbpQuestionDescAdapter(listQuestion);
            rvDesc.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvDesc.setAdapter(adapter);
        }
    }


    /**
     * 设置问题
     */
    private void setRvQuestion() {
        rvQuestion.setLayoutManager(new LinearLayoutManager(getPageContext()));
        List<String> listStr = new ArrayList<>();
        listStr.add("0");
        listStr.add("0");
        listStr.add("0");
        switch (questionNumber) {
            case 2:
                listStr.add("0");
                break;
            case 5:
                listStr.add("0");
                listStr.add("0");
                listStr.add("0");
                break;
        }
        TestOfHbpAdapter adapter = new TestOfHbpAdapter(listStr, questionNumber);
        rvQuestion.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectPosition = position;
                adapter.setSelection(position);
                //下一题选中
                btNextQuestion.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                btNextQuestion.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
            }
        });
    }


    @OnClick(R.id.bt_next_question)
    public void onViewClicked() {
        if (-1 == selectPosition) {
            ToastUtils.showShort("请选择");
        } else {
            if (5 == questionNumber) {
                toDoSubmit();
            } else {
                SPStaticUtils.put("question" + questionNumber, selectPosition + 1);
                TestHealthTwoFragment testTwoFragment = new TestHealthTwoFragment(questionNumber + 1);
                FragmentUtils.replace(getParentFragmentManager(), testTwoFragment, R.id.ll_fragment_root, false);
            }
        }
    }

    private void toDoSubmit() {
        int question2 = SPStaticUtils.getInt("question" + 2);
        int question3 = SPStaticUtils.getInt("question" + 3);
        int question4 = SPStaticUtils.getInt("question" + 4);
        int question5 = selectPosition + 1;
        String overOption = SPStaticUtils.getString("overOption");
        String overNumber = SPStaticUtils.getString("overNumber");
        String problem = question2 + "," + question3 + "," + question4 + "," + question5;
        HashMap map = new HashMap<>();
        map.put("number", overNumber);
        map.put("option", overOption);
        map.put("problem", problem);
        XyUrl.okPost(XyUrl.OBTAIN_PLAN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                TestBean test = JSONObject.parseObject(value, TestBean.class);
                Message message = getHandlerMessage();
                message.what = OVER;
                message.obj = test;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case OVER:
                TestBean testBean = (TestBean) msg.obj;
                String treatment = testBean.getTreatment();
                String id = testBean.getId();
                String prlid = testBean.getPrlid();
                if ("2".equals(treatment)) {
                    Intent intent = new Intent(getPageContext(), HbpDetailActivity.class);
                    intent.putExtra("id", prlid);
                    startActivity(intent);
                } else {
                    SPStaticUtils.put("hbpId", id);
                    SPStaticUtils.put("prlId", prlid);
                    //健康信息答题结束 跳转饮食信息
                    TestDietOneFragment dietOneFragment = new TestDietOneFragment();
                    FragmentUtils.replace(getParentFragmentManager(), dietOneFragment, R.id.ll_fragment_root, false);
                }
                break;
        }
    }

}
