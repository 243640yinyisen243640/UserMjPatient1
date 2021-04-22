package com.vice.bloodpressure.ui.fragment.other;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SevenBottomAdapter;
import com.vice.bloodpressure.adapter.ThirtyBottomAdapter;
import com.vice.bloodpressure.adapter.ThirtyBottomSearchAdapter;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.SevenAndThirtyBloodSugarListBean;
import com.vice.bloodpressure.bean.SugarSearchBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.AdapterClickImp;
import com.vice.bloodpressure.imp.AdapterClickSearchImp;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodSugarAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.HealthRecordBloodSugarListActivity;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.rxhttputils.CustomDataObserver;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 7天和30天血糖记录
 * 作者: LYD
 * 创建日期: 2019/6/5 20:27
 */
public class SevenAndThirtyBloodSugarListFragment extends BaseEventBusFragment implements AdapterClickImp, AdapterClickSearchImp {
    private static final String TAG = "SevenBloodSugarListFragment";
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.tv_highest)
    TextView tvHighest;
    @BindView(R.id.tv_average)
    TextView tvAverage;
    @BindView(R.id.tv_lowest)
    TextView tvLowest;
    @BindView(R.id.rv_sugar_list)
    RecyclerView rvSugarList;
    @BindView(R.id.ll_shadow)
    LinearLayout llShadow;
    @BindView(R.id.tv_add)
    ColorTextView tvAdd;

    private List<SevenAndThirtyBloodSugarListBean.WeekBean.InfoBean> weekInfoList;
    private List<SevenAndThirtyBloodSugarListBean.MonthBean.InfoBeanX> monthInfoList;
    private List<SugarSearchBean.SearchBean.InfoBean> searchList;
    private String beginTime = "";
    private String endTime = "";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_seven_and_thirty_blood_sugar_list;
    }

    @Override
    protected void init(View rootView) {
        getData();
    }


    /**
     * 获取七天和三十天数据
     */
    private void getData() {
        String userid = getArguments().getString("userid");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getSevenAndThirtyBloodSugar(token, userid, ConstantParam.SERVER_VERSION)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CustomDataObserver<SevenAndThirtyBloodSugarListBean>() {
                    @Override
                    protected void onSuccess(SevenAndThirtyBloodSugarListBean bean) {
                        String type = getArguments().getString("type");
                        setSevenAndThirty(type, bean);
                    }
                });
    }


    /**
     * 设置数据
     *
     * @param type
     * @param bean
     */
    private void setSevenAndThirty(String type, SevenAndThirtyBloodSugarListBean bean) {
        setTop(type, bean);
        setSecondLv(type, bean);
    }

    /**
     * 设置顶部
     *
     * @param type
     * @param bean
     */
    private void setTop(String type, SevenAndThirtyBloodSugarListBean bean) {
        if ("0".equals(type)) {
            SevenAndThirtyBloodSugarListBean.WeekBean week = bean.getWeek();
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
            SevenAndThirtyBloodSugarListBean.MonthBean month = bean.getMonth();
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


    /**
     * 记录
     *
     * @param type
     * @param bean
     */
    private void setSecondLv(String type, SevenAndThirtyBloodSugarListBean bean) {
        if ("0".equals(type)) {
            weekInfoList = bean.getWeek().getInfo();
            rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvSugarList.setAdapter(new SevenBottomAdapter(weekInfoList, getPageContext()));
        } else {
            monthInfoList = bean.getMonth().getInfo();
            rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvSugarList.setAdapter(new ThirtyBottomAdapter(monthInfoList, getPageContext()));
        }
    }

    @OnClick({R.id.tv_time_start, R.id.tv_time_end, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_start://选择开始时间
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTimeStart.setText(content);
                        endTime = tvTimeEnd.getText().toString().trim();
                        if ("请选择结束时间".equals(endTime)) {
                            endTime = "";
                        }
                        beginTime = tvTimeStart.getText().toString().trim();
                        getSugarSearch(beginTime, endTime);
                    }
                });
                break;
            case R.id.tv_time_end://选择结束时间
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTimeEnd.setText(content);
                        beginTime = tvTimeStart.getText().toString().trim();
                        if ("请选择开始时间".equals(beginTime)) {
                            beginTime = "";
                        }
                        endTime = tvTimeEnd.getText().toString().trim();
                        getSugarSearch(beginTime, endTime);
                    }
                });
                break;
            case R.id.tv_add://选择结束时间
                startActivity(new Intent(getPageContext(), BloodSugarAddActivity.class));
                break;
        }
    }

    /**
     * 搜索
     *
     * @param startTime
     * @param endTime
     */
    public void getSugarSearch(String startTime, String endTime) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        String userid = getArguments().getString("userid");
        RxHttpUtils.createApi(Service.class)
                .getRxSugarSearchList(token, userid, startTime, endTime, ConstantParam.SERVER_VERSION)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CustomDataObserver<SugarSearchBean>() {
                    @Override
                    protected void onSuccess(SugarSearchBean bean) {
                        setSearch(bean);
                        searchList = bean.getSearch().getInfo();
                    }
                });
    }

    /**
     * 设置搜索
     *
     * @param data
     */
    private void setSearch(SugarSearchBean data) {
        setSearchTop(data.getSearch());
        setSearchSecondLv(data.getSearch().getInfo());
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


    private void setSearchSecondLv(List<SugarSearchBean.SearchBean.InfoBean> infoList) {
        rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvSugarList.setAdapter(new ThirtyBottomSearchAdapter(infoList, getPageContext()));
    }

    @Override
    public void onAdapterClick(View view, int position) {
        Intent intent = new Intent(getPageContext(), HealthRecordBloodSugarListActivity.class);
        switch (view.getId()) {
            case R.id.fl_before_dawn://凌晨
                intent.putExtra("type", "凌晨");
                break;
            case R.id.fl_before_breakfast://早餐前
                intent.putExtra("type", "早餐空腹");
                break;
            case R.id.fl_after_breakfast://早餐后
                intent.putExtra("type", "早餐后");
                break;
            case R.id.fl_before_lunch://午餐前
                intent.putExtra("type", "午餐前");
                break;
            case R.id.fl_after_launch://午餐后
                intent.putExtra("type", "午餐后");
                break;
            case R.id.fl_before_dinner://晚餐前
                intent.putExtra("type", "晚餐前");
                break;
            case R.id.fl_after_dinner://晚餐后
                intent.putExtra("type", "晚餐后");
                break;
            case R.id.fl_after_sleep://睡前
                intent.putExtra("type", "睡前");
                break;
        }
        intent.putExtra("userid", getArguments().getString("userid"));
        String type = getArguments().getString("type");
        if ("0".equals(type)) {
            intent.putExtra("time", weekInfoList.get(position).getTime());
        } else {
            intent.putExtra("time", monthInfoList.get(position).getTime());
        }
        startActivity(intent);
    }

    @Override
    public void onAdapterClickSearch(View view, int position) {
        Intent intent = new Intent(getPageContext(), HealthRecordBloodSugarListActivity.class);
        switch (view.getId()) {
            case R.id.fl_before_dawn://凌晨
                intent.putExtra("type", "凌晨");
                break;
            case R.id.fl_before_breakfast://早餐前
                intent.putExtra("type", "早餐空腹");
                break;
            case R.id.fl_after_breakfast://早餐后
                intent.putExtra("type", "早餐后");
                break;
            case R.id.fl_before_lunch://午餐前
                intent.putExtra("type", "午餐前");
                break;
            case R.id.fl_after_launch://午餐后
                intent.putExtra("type", "午餐后");
                break;
            case R.id.fl_before_dinner://晚餐前
                intent.putExtra("type", "晚餐前");
                break;
            case R.id.fl_after_dinner://晚餐后
                intent.putExtra("type", "晚餐后");
                break;
            case R.id.fl_after_sleep://睡前
                intent.putExtra("type", "睡前");
                break;
        }
        intent.putExtra("userid", getArguments().getString("userid"));
        intent.putExtra("time", searchList.get(position).getTime());
        startActivity(intent);
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.BLOOD_SUGAR_ADD:
            case ConstantParam.DEL_SUGAR_REFRESH:
                getData();
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
