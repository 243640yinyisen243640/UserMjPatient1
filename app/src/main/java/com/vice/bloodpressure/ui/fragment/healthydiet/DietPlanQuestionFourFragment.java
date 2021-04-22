package com.vice.bloodpressure.ui.fragment.healthydiet;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.GvDietPlanSelectAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.DietPlanAddSuccessBean;
import com.vice.bloodpressure.bean.DietPlanQuestionBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanBaseInfoActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanResultActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanTwoStepsActivity;
import com.vice.bloodpressure.utils.RxTimerUtils;
import com.vice.bloodpressure.utils.SPUtils;
import com.vice.bloodpressure.view.popu.SmartDietCreatePopup;
import com.vice.bloodpressure.view.popu.SmartDietSelectPopup;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 描述:  答题第四道(糖尿病肾病)
 * 作者: LYD
 * 创建日期: 2019/12/17 17:38
 */
public class DietPlanQuestionFourFragment extends BaseFragment implements View.OnClickListener {
    private static final int ADD_SUCCESS = 10086;
    private static final int ADD_FAILED = 10087;
    private static final int ADD_SUCCESS_345 = 10088;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @BindView(R.id.tv_next)
    ColorTextView tvNext;
    @BindView(R.id.gv_time)
    GridView gvTime;
    private GvDietPlanSelectAdapter adapter;
    private int periodPosition;

    private SmartDietSelectPopup selectPopup;
    private SmartDietCreatePopup createPopup;
    private DietPlanQuestionBean bean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_diet_plan_question_four;
    }

    @Override
    protected void init(View rootView) {
        EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_QUESTION_TITLE, "4"));
        setTitleAndDesc();
        initGv();
        initPopup();
    }

    private void initGv() {
        String[] stringArray = getResources().getStringArray(R.array.data_sugar_have_symptom_more);
        List<String> listString = Arrays.asList(stringArray);
        adapter = new GvDietPlanSelectAdapter(getPageContext(), R.layout.item_diet_plan_select, listString);
        gvTime.setAdapter(adapter);
    }


    private void initPopup() {
        selectPopup = new SmartDietSelectPopup(getPageContext());
        createPopup = new SmartDietCreatePopup(getPageContext());
        ColorTextView tvSmart = selectPopup.findViewById(R.id.tv_smart);
        TextView tvMySelect = selectPopup.findViewById(R.id.tv_my_select);
        tvSmart.setOnClickListener(this);
        tvMySelect.setOnClickListener(this);
    }


    /**
     * 题目标题
     */
    private void setTitleAndDesc() {
        SpanUtils.with(tvTitle)
                .append("4").setForegroundColor(ColorUtils.getColor(R.color.main_home))
                .append("/4").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .create();
        tvDesc.setText("您是否患有慢性肾病？");
    }


    @OnClick({R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                bean = (DietPlanQuestionBean) SPUtils.getBean("Diet_Question");
                if (0 == periodPosition) {
                    bean.setDn("2");
                } else {
                    bean.setDn("1");
                }
                bean.setDn_type(periodPosition + "");
                //判断三四五
                if (3 == periodPosition || 4 == periodPosition || 5 == periodPosition) {
                    Intent intent = new Intent(getPageContext(), DietPlanBaseInfoActivity.class);
                    intent.putExtra("type", "special");
                    startActivity(intent);
                    toDoSubmit345();
                } else {
                    //提交题目
                    selectPopup.showPopupWindow();
                }
                break;
        }
    }

    private void toDoSubmit345() {
        String sex = bean.getSex();
        String height = bean.getHeight();
        String weight = bean.getWeight();
        String profession = bean.getProfession();
        String dn = bean.getDn();
        String dn_type = bean.getDn_type();
        HashMap map = new HashMap<>();
        map.put("sex", sex);
        map.put("height", height);
        map.put("weight", weight);
        map.put("profession", profession);
        map.put("dn", dn);
        map.put("dn_type", dn_type);
        XyUrl.okPost(XyUrl.DIET_PLAN_ADD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(ADD_SUCCESS_345);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                sendHandlerMessage(ADD_SUCCESS_345);
            }
        });
    }

    private void toDoSubmit(DietPlanQuestionBean bean) {
        selectPopup.dismiss();
        createPopup.showPopupWindow();
        String sex = bean.getSex();
        String height = bean.getHeight();
        String weight = bean.getWeight();
        String profession = bean.getProfession();
        String dn = bean.getDn();
        String dn_type = bean.getDn_type();
        HashMap map = new HashMap<>();
        map.put("sex", sex);
        map.put("height", height);
        map.put("weight", weight);
        map.put("profession", profession);
        map.put("dn", dn);
        map.put("dn_type", dn_type);
        XyUrl.okPost(XyUrl.DIET_PLAN_ADD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                DietPlanAddSuccessBean bean = JSONObject.parseObject(value, DietPlanAddSuccessBean.class);
                Message message = getHandlerMessage();
                message.what = ADD_SUCCESS;
                message.obj = bean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = ADD_FAILED;
                sendHandlerMessage(message);
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        RxTimerUtils rxTimer = new RxTimerUtils();
        switch (msg.what) {
            case ADD_SUCCESS_345:
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_DIET_QUESTION_SUBMIT));
                break;
            case ADD_SUCCESS:
                DietPlanAddSuccessBean data = (DietPlanAddSuccessBean) msg.obj;
                rxTimer.timer(3 * 1000, new RxTimerUtils.RxAction() {
                    @Override
                    public void action(long number) {
                        createPopup.dismiss();
                        int id = data.getId();
                        //清空答题页面
                        getActivity().finish();
                        //跳转详情
                        Intent intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_DIET_QUESTION_SUBMIT));
                break;
            case ADD_FAILED:
                rxTimer.timer(3 * 1000, new RxTimerUtils.RxAction() {
                    @Override
                    public void action(long number) {
                        createPopup.dismiss();
                        Intent intent = new Intent(getPageContext(), DietPlanBaseInfoActivity.class);
                        intent.putExtra("type", "notMatch");
                        startActivity(intent);
                    }
                });
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_DIET_QUESTION_SUBMIT));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_QUESTION_TITLE, "3"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_smart:
                toDoSubmit(bean);
                break;
            case R.id.tv_my_select:
                toMySelect();
                break;
        }
    }

    @OnItemClick(R.id.gv_time)
    void OnItemClick(int position) {
        adapter.setSelect(position);
        periodPosition = position;
    }

    /***
     * 清除保存id
     */
    private void toMySelect() {
        HashMap<String, Object> map = new HashMap<>();
        XyUrl.okPostSave(XyUrl.CLEAR_IDS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SPUtils.putBean("Diet_Question", bean);
                Intent intent = new Intent(getPageContext(), DietPlanTwoStepsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }
}
