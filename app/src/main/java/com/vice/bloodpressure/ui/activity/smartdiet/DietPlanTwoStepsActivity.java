package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.DietPlanTwoStepsThreeRvAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.DietPlanAddSuccessBean;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.vice.bloodpressure.bean.DietPlanQuestionBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.RxTimerUtils;
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.view.popu.SmartDietCreatePopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 智能食谱 两步走
 * 作者: LYD
 * 创建日期: 2020/3/17 13:26
 */
public class DietPlanTwoStepsActivity extends BaseHandlerActivity {
    private static final String TAG = "DietPlanTwoStepsActivity";
    private static final int TO_SELECT_FOOD_BREAKFAST = 10010;
    private static final int TO_SELECT_FOOD_LUNCH = 10011;
    private static final int TO_SELECT_FOOD_DINNER = 10012;
    private static final int ADD_SUCCESS = 10014;
    private static final int ADD_FAILED = 10015;
    @BindView(R.id.rb_way_one)
    RadioButton rbWayOne;
    @BindView(R.id.rb_way_two)
    RadioButton rbWayTwo;
    @BindView(R.id.rg_select_eat_way)
    RadioGroup rgSelectEatWay;

    @BindView(R.id.ll_breakfast)
    LinearLayout llBreakfast;
    @BindView(R.id.rv_breakfast)
    RecyclerView rvBreakFast;

    @BindView(R.id.ll_lunch)
    LinearLayout llLunch;
    @BindView(R.id.rv_lunch)
    RecyclerView rvLunch;

    @BindView(R.id.ll_dinner)
    LinearLayout llDinner;
    @BindView(R.id.rv_dinner)
    RecyclerView rvDinner;

    private List<DietPlanFoodChildBean> selectBreakFastList;
    private List<DietPlanFoodChildBean> selectLunchList;
    private List<DietPlanFoodChildBean> selectDinnerList;
    private SmartDietCreatePopup createPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能食谱");
        selectBreakFastList = new ArrayList<>();
        selectLunchList = new ArrayList<>();
        selectDinnerList = new ArrayList<>();
        createPopup = new SmartDietCreatePopup(getPageContext());
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_diet_plan_two_steps, contentLayout, false);
        return view;
    }

    @OnClick({R.id.ll_breakfast, R.id.ll_lunch, R.id.ll_dinner, R.id.bt_create})
    public void onViewClicked(View view) {
        Intent intent = null;
        String day = getIntent().getStringExtra("day");
        String id = getIntent().getStringExtra("id");
        switch (view.getId()) {
            case R.id.ll_breakfast:
                intent = new Intent(getPageContext(), DietPlanFoodsSelectActivity.class);
                intent.putExtra("position", 1);
                intent.putExtra("day", day);
                intent.putExtra("id", id);
                startActivityForResult(intent, TO_SELECT_FOOD_BREAKFAST);
                break;
            case R.id.ll_lunch:
                intent = new Intent(getPageContext(), DietPlanFoodsSelectActivity.class);
                intent.putExtra("position", 2);
                intent.putExtra("day", day);
                intent.putExtra("id", id);
                startActivityForResult(intent, TO_SELECT_FOOD_LUNCH);
                break;
            case R.id.ll_dinner:
                intent = new Intent(getPageContext(), DietPlanFoodsSelectActivity.class);
                intent.putExtra("position", 3);
                intent.putExtra("day", day);
                intent.putExtra("id", id);
                startActivityForResult(intent, TO_SELECT_FOOD_DINNER);
                break;
            case R.id.bt_create:
                toDoCreate();
                break;
        }
    }


    /**
     * 提交
     */
    private void toDoCreate() {
        String type = "";
        boolean checked0 = rbWayOne.isChecked();
        boolean checked1 = rbWayTwo.isChecked();
        if (checked0) {
            type = "2";
        } else if (checked1) {
            type = "1";
        } else {
            ToastUtils.showShort("请选择饮食比例");
            return;
        }
        StringBuilder sBreakFast = new StringBuilder();
        StringBuilder sLunch = new StringBuilder();
        StringBuilder sDinner = new StringBuilder();
        if (selectBreakFastList != null && selectBreakFastList.size() > 0) {
            for (int i = 0; i < selectBreakFastList.size(); i++) {
                int id = selectBreakFastList.get(i).getId();
                sBreakFast.append(id);
                sBreakFast.append(",");
            }
            sBreakFast.deleteCharAt(sBreakFast.length() - 1);
        }

        if (selectLunchList != null && selectLunchList.size() > 0) {
            for (int i = 0; i < selectLunchList.size(); i++) {
                int id = selectLunchList.get(i).getId();
                sLunch.append(id);
                sLunch.append(",");
            }
            sLunch.deleteCharAt(sLunch.length() - 1);
        }
        if (selectDinnerList != null && selectDinnerList.size() > 0) {
            for (int i = 0; i < selectDinnerList.size(); i++) {
                int id = selectDinnerList.get(i).getId();
                sDinner.append(id);
                sDinner.append(",");
            }
            sDinner.deleteCharAt(sDinner.length() - 1);
        }

        createPopup.showPopupWindow();
        DietPlanQuestionBean bean = (DietPlanQuestionBean) SPUtils.getBean("Diet_Question");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
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
        map.put("type", type);
        map.put("breakfast", sBreakFast);
        map.put("lunch", sLunch);
        map.put("dinner", sDinner);
        XyUrl.okPost(XyUrl.MY_DIET_PLAN_ADD, map, new OkHttpCallBack<String>() {
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
            case ADD_SUCCESS:
                DietPlanAddSuccessBean data = (DietPlanAddSuccessBean) msg.obj;
                rxTimer.timer(3 * 1000, new RxTimerUtils.RxAction() {
                    @Override
                    public void action(long number) {
                        createPopup.dismiss();
                        int id = data.getId();
                        ActivityUtils.finishToActivity(DietPlanMainActivity.class, false);
                        //跳转详情
                        Intent intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
                break;
            case ADD_FAILED:
                rxTimer.timer(3 * 1000, new RxTimerUtils.RxAction() {
                    @Override
                    public void action(long number) {
                        createPopup.dismiss();
                        Intent intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                        intent.putExtra("id", -1);
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TO_SELECT_FOOD_BREAKFAST:
                    selectBreakFastList.clear();
                    HashMap<Integer, DietPlanFoodChildBean> allBreakFastMap = (HashMap<Integer, DietPlanFoodChildBean>) data.getSerializableExtra("allMap");
                    Iterator<DietPlanFoodChildBean> itBreakFast = allBreakFastMap.values().iterator();
                    while (itBreakFast.hasNext()) {
                        DietPlanFoodChildBean next = itBreakFast.next();
                        selectBreakFastList.add(next);
                    }
                    if (selectBreakFastList != null && selectBreakFastList.size() > 0) {
                        rvBreakFast.setVisibility(View.VISIBLE);
                        rvBreakFast.setLayoutManager(new LinearLayoutManager(this));
                        rvBreakFast.setAdapter(new DietPlanTwoStepsThreeRvAdapter(selectBreakFastList));
                    }
                    break;
                case TO_SELECT_FOOD_LUNCH:
                    selectLunchList.clear();
                    HashMap<Integer, DietPlanFoodChildBean> allLunchMap = (HashMap<Integer, DietPlanFoodChildBean>) data.getSerializableExtra("allMap");
                    Iterator<DietPlanFoodChildBean> itLunch = allLunchMap.values().iterator();
                    while (itLunch.hasNext()) {
                        DietPlanFoodChildBean next = itLunch.next();
                        selectLunchList.add(next);
                    }
                    if (selectLunchList != null && selectLunchList.size() > 0) {
                        rvLunch.setVisibility(View.VISIBLE);
                        rvLunch.setLayoutManager(new LinearLayoutManager(this));
                        rvLunch.setAdapter(new DietPlanTwoStepsThreeRvAdapter(selectLunchList));
                    }
                    break;
                case TO_SELECT_FOOD_DINNER:
                    selectDinnerList.clear();
                    HashMap<Integer, DietPlanFoodChildBean> allDinnerMap = (HashMap<Integer, DietPlanFoodChildBean>) data.getSerializableExtra("allMap");
                    Iterator<DietPlanFoodChildBean> itDinner = allDinnerMap.values().iterator();
                    while (itDinner.hasNext()) {
                        DietPlanFoodChildBean next = itDinner.next();
                        selectDinnerList.add(next);
                    }
                    if (selectDinnerList != null && selectDinnerList.size() > 0) {
                        rvDinner.setVisibility(View.VISIBLE);
                        rvDinner.setLayoutManager(new LinearLayoutManager(this));
                        rvDinner.setAdapter(new DietPlanTwoStepsThreeRvAdapter(selectDinnerList));
                    }
                    break;
            }
        }
    }
}
