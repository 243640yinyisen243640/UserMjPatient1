package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SevenBottomAdapter;
import com.vice.bloodpressure.adapter.ThirtyBottomAdapter;
import com.vice.bloodpressure.adapter.ThirtyBottomSearchAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.SevenAndThirtyBloodSugarListBean;
import com.vice.bloodpressure.bean.SugarSearchBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodSugarAddActivity;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.rxhttputils.CustomDataObserver;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 新的血糖记录
 * 作者: LYD
 * 创建日期: 2020/12/2 14:37
 */
public class BloodSugarListActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {
    @BindView(R.id.rv_sugar_list)
    RecyclerView rvSugarList;
    @BindView(R.id.tv_add)
    ColorTextView tvAdd;
    //HeadView 开始
    private TextView tvTimeStart;
    private TextView tvTimeEnd;
    private TextView tvHigh;
    private TextView tvNormal;
    private TextView tvLow;
    private TextView tvHighest;
    private TextView tvAverage;
    private TextView tvLowest;
    private View line7;
    private View line30;
    private LinearLayout ll7;
    private LinearLayout ll30;
    //HeadView 结束

    private String beginTime = "";
    private String endTime = "";
    private String type = "0";
    private SevenAndThirtyBloodSugarListBean listBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血糖记录");
        getList();
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_new_blood_sugar_list, contentLayout, false);
        return view;
    }

    private void getList() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getSevenAndThirtyBloodSugar(token, userid,ConstantParam.SERVER_VERSION)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CustomDataObserver<SevenAndThirtyBloodSugarListBean>() {
                    @Override
                    protected void onSuccess(SevenAndThirtyBloodSugarListBean data) {
                        listBean = data;
                        setSevenAndThirty(type);
                    }
                });
    }

    /**
     * 设置数据
     *
     * @param type
     */
    private void setSevenAndThirty(String type) {
        setSecondLv(type);
        setTop(type);
    }

    /**
     * 记录
     *
     * @param type
     */
    private void setSecondLv(String type) {
        rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        if ("0".equals(type)) {
            View headView = getLayoutInflater().inflate(R.layout.header_new_blood_sugar_list_7, (ViewGroup) rvSugarList.getParent(), false);
            tvTimeStart = headView.findViewById(R.id.tv_time_start);
            tvTimeEnd = headView.findViewById(R.id.tv_time_end);
            tvHigh = headView.findViewById(R.id.tv_high);
            tvNormal = headView.findViewById(R.id.tv_normal);
            tvLow = headView.findViewById(R.id.tv_low);
            tvHighest = headView.findViewById(R.id.tv_highest);
            tvAverage = headView.findViewById(R.id.tv_average);
            tvLowest = headView.findViewById(R.id.tv_lowest);
            line7 = headView.findViewById(R.id.line_7);
            line30 = headView.findViewById(R.id.line_30);
            ll7 = headView.findViewById(R.id.ll_7);
            line30 = headView.findViewById(R.id.ll_30);
            ll7.setOnClickListener(this);
            line30.setOnClickListener(this);
            tvTimeStart.setOnClickListener(this);
            tvTimeEnd.setOnClickListener(this);
            List<SevenAndThirtyBloodSugarListBean.WeekBean.InfoBean> weekInfoList = listBean.getWeek().getInfo();
            SevenBottomAdapter sevenBottomAdapter = new SevenBottomAdapter(weekInfoList, getPageContext());
            //添加头布局 开始
            sevenBottomAdapter.addHeaderView(headView);
            //添加头布局 结束
            rvSugarList.setAdapter(sevenBottomAdapter);
        } else {
            //添加头布局 开始
            View headView = getLayoutInflater().inflate(R.layout.header_new_blood_sugar_list_30, (ViewGroup) rvSugarList.getParent(), false);
            tvTimeStart = headView.findViewById(R.id.tv_time_start);
            tvTimeEnd = headView.findViewById(R.id.tv_time_end);
            tvHigh = headView.findViewById(R.id.tv_high);
            tvNormal = headView.findViewById(R.id.tv_normal);
            tvLow = headView.findViewById(R.id.tv_low);
            tvHighest = headView.findViewById(R.id.tv_highest);
            tvAverage = headView.findViewById(R.id.tv_average);
            tvLowest = headView.findViewById(R.id.tv_lowest);
            line7 = headView.findViewById(R.id.line_7);
            line30 = headView.findViewById(R.id.line_30);
            ll7 = headView.findViewById(R.id.ll_7);
            line30 = headView.findViewById(R.id.ll_30);
            ll7.setOnClickListener(this);
            line30.setOnClickListener(this);
            tvTimeStart.setOnClickListener(this);
            tvTimeEnd.setOnClickListener(this);
            //添加头布局 结束
            List<SevenAndThirtyBloodSugarListBean.MonthBean.InfoBeanX> monthInfoList = listBean.getMonth().getInfo();
            ThirtyBottomAdapter thirtyBottomAdapter = new ThirtyBottomAdapter(monthInfoList, getPageContext());
            thirtyBottomAdapter.addHeaderView(headView);
            rvSugarList.setAdapter(thirtyBottomAdapter);
        }
    }

    /**
     * 设置顶部
     *
     * @param type
     */
    private void setTop(String type) {
        if ("0".equals(type)) {
            SevenAndThirtyBloodSugarListBean.WeekBean week = listBean.getWeek();
            int high = week.getXtpg();
            int normal = week.getXtzc();
            int low = week.getXtpd();
            double highest = week.getZgxt();
            double average = week.getPjxt();
            double lowest = week.getZdxt();
            tvTimeStart.setText("请选择开始时间");
            tvTimeEnd.setText("请选择结束时间");
            tvHigh.setText(high + "次");
            tvNormal.setText(normal + "次");
            tvLow.setText(low + "次");
            tvHighest.setText(highest + "");
            tvAverage.setText(average + "");
            tvLowest.setText(lowest + "");
        } else {
            SevenAndThirtyBloodSugarListBean.MonthBean month = listBean.getMonth();
            int high = month.getXtpg();
            int normal = month.getXtzc();
            int low = month.getXtpd();
            double highest = month.getZgxt();
            double average = month.getPjxt();
            double lowest = month.getZdxt();
            tvTimeStart.setText("请选择开始时间");
            tvTimeEnd.setText("请选择结束时间");
            tvHigh.setText(high + "次");
            tvNormal.setText(normal + "次");
            tvLow.setText(low + "次");
            tvHighest.setText(highest + "");
            tvAverage.setText(average + "");
            tvLowest.setText(lowest + "");
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        startActivity(new Intent(getPageContext(), BloodSugarAddActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_7:
                line7.setVisibility(View.VISIBLE);
                line30.setVisibility(View.INVISIBLE);
                type = "0";
                setSevenAndThirty(type);
                break;
            case R.id.ll_30:
                line7.setVisibility(View.INVISIBLE);
                line30.setVisibility(View.VISIBLE);
                type = "30";
                setSevenAndThirty(type);
                break;
            case R.id.tv_time_start://选择开始时间
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        toJudgeBeginTime(content);
                    }
                });
                break;
            case R.id.tv_time_end://选择结束时间
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        toJudgeEndTime(content);
                    }
                });
                break;
        }
    }

    private void toJudgeBeginTime(String content) {
        tvTimeStart.setText(content);
    }

    private void toJudgeEndTime(String content) {
        beginTime = tvTimeStart.getText().toString().trim();
        if (content.compareTo(beginTime) < 0) {
            ToastUtils.showShort("结束时间" + "小于" + "开始时间");
            return;
        }
        tvTimeEnd.setText(content);
        endTime = tvTimeEnd.getText().toString().trim();
        getSugarSearch(beginTime, endTime);
    }


    /**
     * 搜索
     *
     * @param startTime
     * @param endTime
     */
    public void getSugarSearch(String startTime, String endTime) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getRxSugarSearchList(token, userid, startTime, endTime,ConstantParam.SERVER_VERSION)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CustomDataObserver<SugarSearchBean>() {
                    @Override
                    protected void onSuccess(SugarSearchBean bean) {
                        setSearch(bean, type);
                    }
                });
    }


    /**
     * 设置搜索
     *
     * @param data
     */
    private void setSearch(SugarSearchBean data, String type) {
        setSearchSecondLv(data.getSearch().getInfo(), type);
        setSearchTop(data.getSearch());
    }


    private void setSearchTop(SugarSearchBean.SearchBean search) {
        String starttime = search.getStarttime();
        String endtime = search.getEndtime();
        int high = search.getXtpg();
        int normal = search.getXtzc();
        int low = search.getXtpd();
        double highest = search.getZgxt();
        double average = search.getPjxt();
        double lowest = search.getZdxt();
        tvTimeStart.setText(starttime);
        tvTimeEnd.setText(endtime);
        tvHigh.setText(high + "次");
        tvNormal.setText(normal + "次");
        tvLow.setText(low + "次");
        tvHighest.setText(highest + "");
        tvAverage.setText(average + "");
        tvLowest.setText(lowest + "");
    }


    private void setSearchSecondLv(List<SugarSearchBean.SearchBean.InfoBean> infoList, String type) {
        rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        if ("0".equals(type)) {
            //添加头布局 开始
            View headView = getLayoutInflater().inflate(R.layout.header_new_blood_sugar_list_7, (ViewGroup) rvSugarList.getParent(), false);
            tvTimeStart = headView.findViewById(R.id.tv_time_start);
            tvTimeEnd = headView.findViewById(R.id.tv_time_end);
            tvHigh = headView.findViewById(R.id.tv_high);
            tvNormal = headView.findViewById(R.id.tv_normal);
            tvLow = headView.findViewById(R.id.tv_low);
            tvHighest = headView.findViewById(R.id.tv_highest);
            tvAverage = headView.findViewById(R.id.tv_average);
            tvLowest = headView.findViewById(R.id.tv_lowest);
            line7 = headView.findViewById(R.id.line_7);
            line30 = headView.findViewById(R.id.line_30);
            ll7 = headView.findViewById(R.id.ll_7);
            line30 = headView.findViewById(R.id.ll_30);
            ll7.setOnClickListener(this);
            line30.setOnClickListener(this);
            tvTimeStart.setOnClickListener(this);
            tvTimeEnd.setOnClickListener(this);
            //添加头布局 结束
            ThirtyBottomSearchAdapter thirtyBottomSearchAdapter = new ThirtyBottomSearchAdapter(infoList, getPageContext());
            thirtyBottomSearchAdapter.addHeaderView(headView);
            rvSugarList.setAdapter(thirtyBottomSearchAdapter);
        } else {
            //添加头布局 开始
            View headView = getLayoutInflater().inflate(R.layout.header_new_blood_sugar_list_30, (ViewGroup) rvSugarList.getParent(), false);
            tvTimeStart = headView.findViewById(R.id.tv_time_start);
            tvTimeEnd = headView.findViewById(R.id.tv_time_end);
            tvHigh = headView.findViewById(R.id.tv_high);
            tvNormal = headView.findViewById(R.id.tv_normal);
            tvLow = headView.findViewById(R.id.tv_low);
            tvHighest = headView.findViewById(R.id.tv_highest);
            tvAverage = headView.findViewById(R.id.tv_average);
            tvLowest = headView.findViewById(R.id.tv_lowest);
            line7 = headView.findViewById(R.id.line_7);
            line30 = headView.findViewById(R.id.line_30);
            ll7 = headView.findViewById(R.id.ll_7);
            line30 = headView.findViewById(R.id.ll_30);
            ll7.setOnClickListener(this);
            line30.setOnClickListener(this);
            tvTimeStart.setOnClickListener(this);
            tvTimeEnd.setOnClickListener(this);
            //添加头布局 结束
            ThirtyBottomSearchAdapter thirtyBottomSearchAdapter = new ThirtyBottomSearchAdapter(infoList, getPageContext());
            thirtyBottomSearchAdapter.addHeaderView(headView);
            rvSugarList.setAdapter(thirtyBottomSearchAdapter);
        }
    }


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.BLOOD_SUGAR_ADD:
            case ConstantParam.DEL_SUGAR_REFRESH:
                getList();
                break;
        }
    }
}