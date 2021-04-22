package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.DietPlanResultSevenDaysAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.DietPlanChangeBean;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.vice.bloodpressure.bean.DietPlanResultBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.utils.LoadingDialogUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.CustomFitViewTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 智能食谱 详情
 * 作者: LYD
 * 创建日期: 2020/3/16 9:16
 */
public class DietPlanResultActivity extends BaseHandlerEventBusActivity {
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_bmi)
    CustomFitViewTextView tvBmi;
    @BindView(R.id.ll_ring_bmi)
    LinearLayout llRingBmi;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_energy_tips)
    TextView tvEnergyTips;
    @BindView(R.id.tv_energy)
    TextView tvEnergy;
    @BindView(R.id.ll_breakfast)
    LinearLayout llBreakfast;
    @BindView(R.id.ll_lunch)
    LinearLayout llLunch;
    @BindView(R.id.ll_dinner)
    LinearLayout llDinner;
    @BindView(R.id.tv_breakfast)
    TextView tvBreakfast;
    @BindView(R.id.tv_lunch)
    TextView tvLunch;
    @BindView(R.id.tv_dinner)
    TextView tvDinner;

    @BindView(R.id.tv_breakfast_seven_days)
    TextView tvBreakfastSevenDays;
    @BindView(R.id.tv_lunch_seven_days)
    TextView tvLunchSevenDays;
    @BindView(R.id.tv_dinner_seven_days)
    TextView tvDinnerSevenDays;
    @BindView(R.id.tv_look_more)
    TextView tvLookMore;
    @BindView(R.id.rv_seven_days)
    RecyclerView rvSevenDays;
    @BindView(R.id.ll_refresh_seven_days)
    LinearLayout llRefreshSevenDays;
    @BindView(R.id.ll_create_success)
    LinearLayout llCreateSuccess;
    @BindView(R.id.ll_create_error)
    LinearLayout llCreateError;
    //饮食id
    private int id;
    //默认中间的那份 1第一天2第二天3第三天 7第七天
    private String positionFroSevenDays = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能食谱");
        id = getIntent().getIntExtra("id", 0);
        if (-1 == id) {
            llCreateError.setVisibility(View.VISIBLE);
            llCreateSuccess.setVisibility(View.GONE);
        } else {
            llCreateError.setVisibility(View.GONE);
            llCreateSuccess.setVisibility(View.VISIBLE);
            setMore();
            getData(id);
        }
    }

    private void setMore() {
        getTvSave().setText("重新获取");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getPageContext(), DietPlanQuestionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_diet_plan_result, contentLayout, false);
    }

    private void getData(int id) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getDietPlantDetail(token, id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<DietPlanResultBean>>() {
                    @Override
                    protected void onSuccess(BaseData<DietPlanResultBean> dietPlanDetailBeanBaseData) {
                        setDetail(dietPlanDetailBeanBaseData);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }

    /**
     * 设置详情
     *
     * @param dietPlanDetailBeanBaseData
     */
    private void setDetail(BaseData<DietPlanResultBean> dietPlanDetailBeanBaseData) {
        DietPlanResultBean dietPlanDetailBean = dietPlanDetailBeanBaseData.getData();
        String bmi = dietPlanDetailBean.getBmi();
        tvBmi.setText(bmi);
        tvHeight.setText(dietPlanDetailBean.getHeight());
        tvWeight.setText(dietPlanDetailBean.getWeight());
        tvEnergy.setText(dietPlanDetailBean.getHq() + "千卡");
        float bmiFloat = TurnsUtils.getFloat(bmi, 0);
        if (bmiFloat >= 28) {
            llRingBmi.setBackgroundResource(R.drawable.bmi_high);
            tvState.setText("肥胖");
        } else if (bmiFloat >= 24) {
            llRingBmi.setBackgroundResource(R.drawable.bmi_high);
            tvState.setText("超重");
        } else if (bmiFloat >= 18.5) {
            llRingBmi.setBackgroundResource(R.drawable.bmi_normal);
            tvState.setText("正常");
        } else {
            llRingBmi.setBackgroundResource(R.drawable.bmi_low);
            tvState.setText("偏瘦");
        }
        //设置专属方案
        DietPlanResultBean.EatinginfoBean mySpecialBean = dietPlanDetailBean.getEatinginfo();
        List<DietPlanResultBean.EatinginfoBean.BreakfastBean> listBreakFast = mySpecialBean.getBreakfast();
        List<DietPlanResultBean.EatinginfoBean.LunchBean> listLunch = mySpecialBean.getLunch();
        List<DietPlanResultBean.EatinginfoBean.DinnerBean> listDinner = mySpecialBean.getDinner();
        StringBuilder breakFast = new StringBuilder();
        StringBuilder lunch = new StringBuilder();
        StringBuilder dinner = new StringBuilder();
        for (int i = 0; i < listBreakFast.size(); i++) {
            String name = listBreakFast.get(i).getName();
            breakFast.append(name);
            breakFast.append("\n");
        }
        for (int i = 0; i < listLunch.size(); i++) {
            String name = listLunch.get(i).getName();
            lunch.append(name);
            lunch.append("\n");
        }
        for (int i = 0; i < listDinner.size(); i++) {
            String name = listDinner.get(i).getName();
            dinner.append(name);
            dinner.append("\n");
        }
        tvBreakfast.setText(breakFast);
        tvLunch.setText(lunch);
        tvDinner.setText(dinner);
        //七天设置日期
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(0 + "");
        }
        DietPlanResultSevenDaysAdapter adapter = new DietPlanResultSevenDaysAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvSevenDays.setLayoutManager(linearLayoutManager);
        rvSevenDays.setAdapter(adapter);
        //点击变色
        adapter.setGetListener(new DietPlanResultSevenDaysAdapter.GetListener() {
            @Override
            public void onClick(int position) {
                //把点击的下标回传给适配器 确定下标
                adapter.setmPosition(position);
                adapter.notifyDataSetChanged();
                //生成七天
                createDietPlan(position + 1 + "");
                positionFroSevenDays = position + 1 + "";
            }
        });
        //七天方案设置
        DietPlanResultBean.Eatinginfo1Bean mySpecialBeanSevenDays = dietPlanDetailBean.getEatinginfo1();
        List<DietPlanResultBean.Eatinginfo1Bean.BreakfastBeanX> listBreakFastSevenDays = mySpecialBeanSevenDays.getBreakfast();
        List<DietPlanResultBean.Eatinginfo1Bean.LunchBeanX> listLunchSevenDays = mySpecialBeanSevenDays.getLunch();
        List<DietPlanResultBean.Eatinginfo1Bean.DinnerBeanX> listDinnerSevenDays = mySpecialBeanSevenDays.getDinner();
        StringBuilder breakFastSevenDays = new StringBuilder();
        StringBuilder lunchSevenDays = new StringBuilder();
        StringBuilder dinnerSevenDays = new StringBuilder();
        for (int i = 0; i < listBreakFastSevenDays.size(); i++) {
            String name = listBreakFastSevenDays.get(i).getName();
            breakFastSevenDays.append(name);
            breakFastSevenDays.append("\n");
        }
        for (int i = 0; i < listLunchSevenDays.size(); i++) {
            String name = listLunchSevenDays.get(i).getName();
            lunchSevenDays.append(name);
            lunchSevenDays.append("\n");
        }
        for (int i = 0; i < listDinnerSevenDays.size(); i++) {
            String name = listDinnerSevenDays.get(i).getName();
            dinnerSevenDays.append(name);
            dinnerSevenDays.append("\n");
        }
        tvBreakfastSevenDays.setText(breakFastSevenDays);
        tvLunchSevenDays.setText(lunchSevenDays);
        tvDinnerSevenDays.setText(dinnerSevenDays);
    }


    /**
     * 生成七天
     *
     * @param position
     */
    private void createDietPlan(String position) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .dietPlanAdd(token, id, position)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<DietPlanChangeBean>>() {
                    @Override
                    protected void onSuccess(BaseData<DietPlanChangeBean> dietPlanDetailBeanBaseData) {
                        changeData(position, dietPlanDetailBeanBaseData);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.tv_special_refresh, R.id.ll_refresh_seven_days, R.id.tv_look_more, R.id.tv_look_more_seven_days, R.id.bt_back_adjust})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_special_refresh:
                refresh("");
                break;
            case R.id.ll_refresh_seven_days:
                refresh(positionFroSevenDays);
                break;
            case R.id.tv_look_more:
                intent = new Intent(this, DietPlanDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("day", "");
                startActivity(intent);
                break;
            case R.id.tv_look_more_seven_days:
                intent = new Intent(this, DietPlanDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("day", positionFroSevenDays);
                startActivity(intent);
                break;
            case R.id.bt_back_adjust:
                finish();
                break;
        }
    }


    /**
     * 刷新专属
     */
    private void refresh(String position) {
        LoadingDialogUtils.show(this);
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .dietPlanChange(token, id, position)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<DietPlanChangeBean>>() {
                    @Override
                    protected void onSuccess(BaseData<DietPlanChangeBean> dietPlanDetailBeanBaseData) {
                        LoadingDialogUtils.unInit();
                        changeData(position, dietPlanDetailBeanBaseData);
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        LoadingDialogUtils.unInit();
                    }
                });
    }

    private void changeData(String position, BaseData<DietPlanChangeBean> dietPlanDetailBeanBaseData) {
        DietPlanChangeBean data = dietPlanDetailBeanBaseData.getData();
        List<DietPlanFoodChildBean> listBreakfast = data.getBreakfast();
        List<DietPlanFoodChildBean> listLunch = data.getLunch();
        List<DietPlanFoodChildBean> listDinner = data.getDinner();
        StringBuilder breakFast = new StringBuilder();
        StringBuilder lunch = new StringBuilder();
        StringBuilder dinner = new StringBuilder();
        for (int i = 0; i < listBreakfast.size(); i++) {
            String name = listBreakfast.get(i).getName();
            breakFast.append(name);
            breakFast.append("\n");
        }
        for (int i = 0; i < listLunch.size(); i++) {
            String name = listLunch.get(i).getName();
            lunch.append(name);
            lunch.append("\n");
        }
        for (int i = 0; i < listDinner.size(); i++) {
            String name = listDinner.get(i).getName();
            dinner.append(name);
            dinner.append("\n");
        }
        if (TextUtils.isEmpty(position)) {
            tvBreakfast.setText(breakFast);
            tvLunch.setText(lunch);
            tvDinner.setText(dinner);
        } else {
            tvBreakfastSevenDays.setText(breakFast);
            tvLunchSevenDays.setText(lunch);
            tvDinnerSevenDays.setText(dinner);
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.DIET_PLAN_CHANGE_REFRESH:
                getData(id);
                break;
        }
    }
}
