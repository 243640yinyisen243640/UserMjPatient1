package com.vice.bloodpressure.ui.fragment.highbloodpressuretest;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.TestBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorButton;
import com.wei.android.lib.colorview.view.ColorLinearLayout;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 健康信息之接口答题
 * 作者: LYD
 * 创建日期: 2019/11/26 14:59
 */


public class TestHealthOneFragment extends BaseFragment {
    private static final String TAG = "TestFragment";
    private static final int NEXT = 101;
    private static final int OVER = 102;
    @BindView(R.id.tv_top_desc)
    TextView tvTopDesc;
    @BindView(R.id.ll_top)
    ColorLinearLayout llTop;
    @BindView(R.id.tv_top_number)
    ColorTextView tvTopNumber;
    @BindView(R.id.tv_top_question_desc)
    TextView tvTopQuestionDesc;
    @BindView(R.id.ll_bottom)
    ColorLinearLayout llBottom;
    @BindView(R.id.tv_bottom_number)
    ColorTextView tvBottomNumber;
    @BindView(R.id.tv_bottom_question__desc)
    TextView tvBottomQuestionDesc;
    @BindView(R.id.bt_next_question)
    ColorButton btNextQuestion;
    //本题序号，第一题可不传
    private String number = "";
    //题目序号及答案，第一题可不传，多题用逗号隔开：1a，2b
    private String option = "";
    private String opt = "";
    private List<ColorLinearLayout> listCll = new ArrayList<>();
    private List<ColorTextView> listCtv = new ArrayList<>();
    private List<TextView> listTv = new ArrayList<>();

    /**
     * 获取题目
     */
    private void getPlan(String num, String opt) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("number", num);
        map.put("option", opt);
        XyUrl.okPost(XyUrl.OBTAIN_PLAN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                TestBean test = JSONObject.parseObject(value, TestBean.class);
                String status = test.getStatus();
                Message message = getHandlerMessage();
                if (status.equals("plan")) {
                    message.what = NEXT;
                    message.obj = test;
                } else if (status.equals("rule")) {
                    message.what = OVER;
                    //保存答题
                    SPStaticUtils.put("overNumber", num);
                    SPStaticUtils.put("overOption", opt);
                }
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init(View rootView) {
        getPlan(number, option);
        setView();
    }

    private void setView() {
        llTop.setTag(0);
        llBottom.setTag(0);
        listCll.add(llTop);
        listCll.add(llBottom);
        listCtv.add(tvTopNumber);
        listCtv.add(tvBottomNumber);
        listTv.add(tvTopQuestionDesc);
        listTv.add(tvBottomQuestionDesc);
    }

    @OnClick({R.id.ll_top, R.id.ll_bottom, R.id.bt_next_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_top:
                setCheck(0, 1);
                break;
            case R.id.ll_bottom:
                setCheck(1, 0);
                break;
            case R.id.bt_next_question:
                getNextQuestion();
                break;
        }
    }


    /**
     * 设置选中
     *
     * @param checkIndex
     * @param unCheckIndex
     */
    private void setCheck(int checkIndex, int unCheckIndex) {
        //下一题选中
        btNextQuestion.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
        btNextQuestion.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));

        listCll.get(checkIndex).setTag(1);
        listCll.get(unCheckIndex).setTag(0);
        //选中
        //root
        listCll.get(checkIndex).getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.main_home));
        listCll.get(checkIndex).getColorHelper().setBorderWidthNormal(1);
        listCll.get(checkIndex).getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.white));
        //题号
        listCtv.get(checkIndex).getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
        listCtv.get(checkIndex).getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
        listCtv.get(checkIndex).getColorHelper().setBorderWidthNormal(0);
        //题目
        listTv.get(checkIndex).setTextColor(ColorUtils.getColor(R.color.main_home));

        //未选中
        //root
        listCll.get(unCheckIndex).getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_f2));
        listCll.get(unCheckIndex).getColorHelper().setBorderWidthNormal(0);
        //题号
        listCtv.get(unCheckIndex).getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.black_text));
        listCtv.get(unCheckIndex).getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_f2));
        listCtv.get(unCheckIndex).getColorHelper().setBorderWidthNormal(1);
        listCtv.get(unCheckIndex).getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.color_cb));
        //题目
        listTv.get(unCheckIndex).setTextColor(ColorUtils.getColor(R.color.color_69));
    }

    private void getNextQuestion() {
        int topCheck = (int) llTop.getTag();
        int bottomCheck = (int) llBottom.getTag();
        if (1 == topCheck) {
            option = number + "a";
        } else if (1 == bottomCheck) {
            option = number + "b";
        } else {
            ToastUtils.showShort("请您选择");
            return;
        }
        //opt
        if (opt.isEmpty()) {
            opt += option;
        } else {
            opt += "," + option;
        }
        getPlan(number, opt);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case NEXT:
                TestBean test = (TestBean) msg.obj;
                TestBean.InfoBean info = test.getInfo();
                if (info != null) {
                    number = info.getNumber() + "";
                    tvTopDesc.setText("1." + info.getTitle() + ":");
                    tvTopQuestionDesc.setText(info.getOp_a());
                    tvBottomQuestionDesc.setText(info.getOp_b());
                }
                break;
            case OVER:
                TestHealthTwoFragment testTwoFragment = new TestHealthTwoFragment(2);
                FragmentUtils.replace(getParentFragmentManager(), testTwoFragment, R.id.ll_fragment_root, false);
                break;
        }
    }
}
