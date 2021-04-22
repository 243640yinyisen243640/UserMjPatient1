package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.DietPlanTwoStepsThreeRvAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  选择我想吃的食物
 * 作者: LYD
 * 创建日期: 2020/3/23 10:41
 */
public class DietPlanOneStepsActivity extends BaseHandlerActivity {
    private static final int TO_SELECT_FOOD_BREAKFAST = 10010;
    private static final int TO_SELECT_FOOD_LUNCH = 10011;
    private static final int TO_SELECT_FOOD_DINNER = 10012;
    private static final int ADD_SUCCESS = 10014;
    @BindView(R.id.ll_breakfast)
    LinearLayout llBreakfast;
    @BindView(R.id.rv_breakfast)
    RecyclerView rvBreakfast;
    @BindView(R.id.ll_lunch)
    LinearLayout llLunch;
    @BindView(R.id.rv_lunch)
    RecyclerView rvLunch;
    @BindView(R.id.ll_dinner)
    LinearLayout llDinner;
    @BindView(R.id.rv_dinner)
    RecyclerView rvDinner;
    @BindView(R.id.bt_create)
    ColorButton btCreate;

    private List<DietPlanFoodChildBean> selectBreakFastList;
    private List<DietPlanFoodChildBean> selectLunchList;
    private List<DietPlanFoodChildBean> selectDinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能食谱");
        selectBreakFastList = (List<DietPlanFoodChildBean>) getIntent().getSerializableExtra("breakfast");
        selectLunchList = (List<DietPlanFoodChildBean>) getIntent().getSerializableExtra("lunch");
        selectDinnerList = (List<DietPlanFoodChildBean>) getIntent().getSerializableExtra("dinner");
        setAdapter(0);
        setAdapter(1);
        setAdapter(2);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_diet_plan_one_steps, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            case ADD_SUCCESS:
                int id = getIntent().getIntExtra("id", 0);
                //清空答题页面
                finish();
                EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_CHANGE_REFRESH));
                //跳转详情
                intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
    }

    @OnClick({R.id.ll_breakfast, R.id.ll_lunch, R.id.ll_dinner, R.id.bt_create})
    public void onViewClicked(View view) {
        Intent intent = null;
        String id = getIntent().getIntExtra("id", 0) + "";
        String day = getIntent().getStringExtra("day");
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

    private void toDoCreate() {
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
        int id = getIntent().getIntExtra("id", 0);
        String day = getIntent().getStringExtra("day");
        HashMap map = new HashMap<>();
        map.put("day", day);
        map.put("id", id);
        map.put("breakfast", sBreakFast);
        map.put("lunch", sLunch);
        map.put("dinner", sDinner);
        XyUrl.okPostSave(XyUrl.CHANGE_MY_DIET, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message message = getHandlerMessage();
                message.what = ADD_SUCCESS;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
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
                    setAdapter(0);
                    break;
                case TO_SELECT_FOOD_LUNCH:
                    selectLunchList.clear();
                    HashMap<Integer, DietPlanFoodChildBean> allLunchMap = (HashMap<Integer, DietPlanFoodChildBean>) data.getSerializableExtra("allMap");
                    Iterator<DietPlanFoodChildBean> itLunch = allLunchMap.values().iterator();
                    while (itLunch.hasNext()) {
                        DietPlanFoodChildBean next = itLunch.next();
                        selectLunchList.add(next);
                    }
                    setAdapter(1);
                    break;
                case TO_SELECT_FOOD_DINNER:
                    selectDinnerList.clear();
                    HashMap<Integer, DietPlanFoodChildBean> allDinnerMap = (HashMap<Integer, DietPlanFoodChildBean>) data.getSerializableExtra("allMap");
                    Iterator<DietPlanFoodChildBean> itDinner = allDinnerMap.values().iterator();
                    while (itDinner.hasNext()) {
                        DietPlanFoodChildBean next = itDinner.next();
                        selectDinnerList.add(next);
                    }
                    setAdapter(2);
                    break;
            }
        }
    }

    private void setAdapter(int i) {
        switch (i) {
            case 0:
                if (selectBreakFastList != null && selectBreakFastList.size() > 0) {
                    rvBreakfast.setVisibility(View.VISIBLE);
                    rvBreakfast.setLayoutManager(new LinearLayoutManager(this));
                    rvBreakfast.setAdapter(new DietPlanTwoStepsThreeRvAdapter(selectBreakFastList));
                }
                break;
            case 1:
                if (selectLunchList != null && selectLunchList.size() > 0) {
                    rvLunch.setVisibility(View.VISIBLE);
                    rvLunch.setLayoutManager(new LinearLayoutManager(this));
                    rvLunch.setAdapter(new DietPlanTwoStepsThreeRvAdapter(selectLunchList));
                }
                break;
            case 2:
                if (selectDinnerList != null && selectDinnerList.size() > 0) {
                    rvDinner.setVisibility(View.VISIBLE);
                    rvDinner.setLayoutManager(new LinearLayoutManager(this));
                    rvDinner.setAdapter(new DietPlanTwoStepsThreeRvAdapter(selectDinnerList));
                }
                break;
        }

    }
}
