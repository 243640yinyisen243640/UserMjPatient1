package com.vice.bloodpressure.ui.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.PhysicalExaminationDoctorInfoListBean;
import com.vice.bloodpressure.bean.ScheduleInfoPostBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.TimeUtils;
import com.wei.android.lib.colorview.helper.ColorTextViewHelper;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 医生排班详情页面
 * 作者: LYD
 * 创建日期: 2019/10/26 15:59
 */
public class PhysicalExaminationDoctorInfoActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final int GET_NO_DATA = 30002;
    private static final String TAG = "PhysicalExaminationDoctorInfoActivity";
    @BindView(R.id.iv_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_doctor_profession)
    TextView tvDoctorProfession;
    @BindView(R.id.tv_doctor_desc)
    TextView tvDoctorDesc;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_week_one)
    TextView tvWeekOne;
    @BindView(R.id.tv_day_one)
    TextView tvDayOne;
    @BindView(R.id.tv_week_two)
    TextView tvWeekTwo;
    @BindView(R.id.tv_day_two)
    TextView tvDayTwo;
    @BindView(R.id.tv_week_three)
    TextView tvWeekThree;
    @BindView(R.id.tv_day_three)
    TextView tvDayThree;
    @BindView(R.id.tv_week_four)
    TextView tvWeekFour;
    @BindView(R.id.tv_day_four)
    TextView tvDayFour;
    @BindView(R.id.tv_week_five)
    TextView tvWeekFive;
    @BindView(R.id.tv_day_five)
    TextView tvDayFive;
    @BindView(R.id.tv_week_six)
    TextView tvWeekSix;
    @BindView(R.id.tv_day_six)
    TextView tvDaySix;
    @BindView(R.id.tv_week_seven)
    TextView tvWeekSeven;
    @BindView(R.id.tv_day_seven)
    TextView tvDaySeven;
    @BindView(R.id.tv_am_state_one)
    ColorTextView tvAmStateOne;
    @BindView(R.id.tv_am_state_two)
    ColorTextView tvAmStateTwo;
    @BindView(R.id.tv_am_state_three)
    ColorTextView tvAmStateThree;
    @BindView(R.id.tv_am_state_four)
    ColorTextView tvAmStateFour;
    @BindView(R.id.tv_am_state_five)
    ColorTextView tvAmStateFive;
    @BindView(R.id.tv_am_state_six)
    ColorTextView tvAmStateSix;
    @BindView(R.id.tv_am_state_seven)
    ColorTextView tvAmStateSeven;
    @BindView(R.id.tv_pm_state_one)
    ColorTextView tvPmStateOne;
    @BindView(R.id.tv_pm_state_two)
    ColorTextView tvPmStateTwo;
    @BindView(R.id.tv_pm_state_three)
    ColorTextView tvPmStateThree;
    @BindView(R.id.tv_pm_state_four)
    ColorTextView tvPmStateFour;
    @BindView(R.id.tv_pm_state_five)
    ColorTextView tvPmStateFive;
    @BindView(R.id.tv_pm_state_six)
    ColorTextView tvPmStateSix;
    @BindView(R.id.tv_pm_state_seven)
    ColorTextView tvPmStateSeven;
    //设置阴影
    @BindView(R.id.ll_shadow_top)
    LinearLayout llShadowTop;
    @BindView(R.id.ll_shadow_bottom)
    LinearLayout llShadowBottom;

    //设置阴影
    private List<Date> dateList;
    private List<PhysicalExaminationDoctorInfoListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String docname = getIntent().getStringExtra("docname");
        setTitle(docname);
        int docuserid = getIntent().getIntExtra("docuserid", 0);
        getDoctorDetail(docuserid);
        initTime();
    }




    /**
     * 初始化七天时间
     */
    private void initTime() {
        List<TextView> listTvWeeks = new ArrayList<>();
        listTvWeeks.add(tvWeekOne);
        listTvWeeks.add(tvWeekTwo);
        listTvWeeks.add(tvWeekThree);
        listTvWeeks.add(tvWeekFour);
        listTvWeeks.add(tvWeekFive);
        listTvWeeks.add(tvWeekSix);
        listTvWeeks.add(tvWeekSeven);
        List<TextView> listTvDays = new ArrayList<>();
        listTvDays.add(tvDayOne);
        listTvDays.add(tvDayTwo);
        listTvDays.add(tvDayThree);
        listTvDays.add(tvDayFour);
        listTvDays.add(tvDayFive);
        listTvDays.add(tvDaySix);
        listTvDays.add(tvDaySeven);
        //获取当前月份
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        tvMonth.setText(month + "月");
        //获取周几和时间
        String startDateString = TimeUtils.plusDay(1);
        String endDateString = TimeUtils.plusDay(7);
        DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dd = new SimpleDateFormat("dd");
        Date startDate = com.blankj.utilcode.util.TimeUtils.string2Date(startDateString, ymd);
        Date endDate = com.blankj.utilcode.util.TimeUtils.string2Date(endDateString, ymd);
        dateList = TimeUtils.getBetweenDates(startDate, endDate);
        for (int i = 0; i < dateList.size(); i++) {
            Date date = dateList.get(i);
            String ymdStr = ymd.format(date);
            String week = TimeUtils.dateToWeek(ymdStr);
            listTvWeeks.get(i).setText(week);
            String day = dd.format(date);
            listTvDays.get(i).setText(day);
        }
    }

    /**
     * 获取医生排班详情
     *
     * @param docuserid
     */
    private void getDoctorDetail(int docuserid) {
        HashMap map = new HashMap<>();
        map.put("docuserid", docuserid);
        XyUrl.okPost(XyUrl.GET_SCHEDULE_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<PhysicalExaminationDoctorInfoListBean> list = JSONObject.parseArray(value, PhysicalExaminationDoctorInfoListBean.class);
                if (list != null && list.size() > 0) {
                    Message msg = getHandlerMessage();
                    msg.obj = list;
                    msg.what = GET_DATA;
                    sendHandlerMessage(msg);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                msg.what = GET_NO_DATA;
                sendHandlerMessage(msg);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_physical_examination_doctor_info, contentLayout, false);
        return view;
    }


    @OnClick({R.id.tv_refresh, R.id.tv_am_state_one, R.id.tv_am_state_two, R.id.tv_am_state_three, R.id.tv_am_state_four, R.id.tv_am_state_five, R.id.tv_am_state_six, R.id.tv_am_state_seven,
            R.id.tv_pm_state_one, R.id.tv_pm_state_two, R.id.tv_pm_state_three, R.id.tv_pm_state_four, R.id.tv_pm_state_five, R.id.tv_pm_state_six, R.id.tv_pm_state_seven})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                int docuserid = getIntent().getIntExtra("docuserid", 0);
                getDoctorDetail(docuserid);
                break;
            //上午
            case R.id.tv_am_state_one:
                checkIsCanGoAppointment(1, 0);
                break;
            case R.id.tv_am_state_two:
                checkIsCanGoAppointment(1, 1);
                break;
            case R.id.tv_am_state_three:
                checkIsCanGoAppointment(1, 2);
                break;
            case R.id.tv_am_state_four:
                checkIsCanGoAppointment(1, 3);
                break;
            case R.id.tv_am_state_five:
                checkIsCanGoAppointment(1, 4);
                break;
            case R.id.tv_am_state_six:
                checkIsCanGoAppointment(1, 5);
                break;
            case R.id.tv_am_state_seven:
                checkIsCanGoAppointment(1, 6);
                break;
            //下午
            case R.id.tv_pm_state_one:
                checkIsCanGoAppointment(2, 0);
                break;
            case R.id.tv_pm_state_two:
                checkIsCanGoAppointment(2, 1);
                break;
            case R.id.tv_pm_state_three:
                checkIsCanGoAppointment(2, 2);
                break;
            case R.id.tv_pm_state_four:
                checkIsCanGoAppointment(2, 3);
                break;
            case R.id.tv_pm_state_five:
                checkIsCanGoAppointment(2, 4);
                break;
            case R.id.tv_pm_state_six:
                checkIsCanGoAppointment(2, 5);
                break;
            case R.id.tv_pm_state_seven:
                checkIsCanGoAppointment(2, 6);
                break;
        }
    }


    /**
     * 校验是否可以跳转
     *
     * @param type  1:上午  2:下午
     * @param index
     */
    private void checkIsCanGoAppointment(int type, int index) {
        PhysicalExaminationDoctorInfoListBean data = list.get(index);
        switch (type) {
            case 1:
                int am = data.getAm();
                if (1 == am) {
                    goToAppointmentActivity(type, index);
                } else {
                    ToastUtils.showShort("不可预约");
                }
                break;
            case 2:
                int pm = data.getPm();
                if (1 == pm) {
                    goToAppointmentActivity(type, index);
                } else {
                    ToastUtils.showShort("不可预约");
                }
                break;
        }
    }


    /**
     * 跳转页面
     *
     * @param type
     * @param index
     */
    private void goToAppointmentActivity(int type, int index) {
        PhysicalExaminationDoctorInfoListBean data = list.get(index);
        //格式化时间
        DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateList.get(index);
        String day = ymd.format(date);
        //获取sid
        String sid = data.getSid() + "";
        //设置
        ScheduleInfoPostBean postBean = new ScheduleInfoPostBean();
        postBean.setSchday(day);
        postBean.setSid(sid);
        postBean.setType(type + "");
        Intent intent = new Intent(Utils.getApp(), AppointmentCheckActivity.class);
        intent.putExtra("data", postBean);
        startActivity(intent);
    }


    /**
     * 设置状态
     *
     * @param tv
     * @param state 1可预约  2不可预约
     */
    private void setTvState(ColorTextView tv, int state) {
        ColorTextViewHelper helper = tv.getColorHelper();
        if (1 == state) {
            tv.setText("预约");
            tv.setTag(1);
            helper.setTextColorNormal(ColorUtils.getColor(R.color.white));
            helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.color_week_selected));
        } else {
            tv.setText("约满");
            tv.setTag(2);
            helper.setTextColorNormal(ColorUtils.getColor(R.color.color_666));
            helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.color_e5));
        }
    }

    /**
     * 设置医生排班信息
     *
     * @param msg
     */
    private void setDoctorInfo(Message msg) {
        list = (List<PhysicalExaminationDoctorInfoListBean>) msg.obj;
        if (list != null && 7 == list.size()) {
            //设置除时间
            PhysicalExaminationDoctorInfoListBean data = list.get(0);
            String imgurl = data.getImgurl();
            String docname = data.getDocname();
            String doczhc = data.getDoczhc();
            String contents = data.getContents();
            Glide.with(Utils.getApp())
                    .load(imgurl)
                    .error(R.drawable.default_doctor_head)
                    .placeholder(R.drawable.default_doctor_head)
                    .into(imgHead);
            tvDoctorName.setText(docname);
            tvDoctorProfession.setText(doczhc);
            tvDoctorDesc.setText(contents);
            //设置预约状态
            for (int i = 0; i < 7; i++) {
                data = list.get(i);
                int am = data.getAm();
                int pm = data.getPm();
                switch (i) {
                    case 0:
                        setTvState(tvAmStateOne, am);
                        setTvState(tvPmStateOne, pm);
                        break;
                    case 1:
                        setTvState(tvAmStateTwo, am);
                        setTvState(tvPmStateTwo, pm);
                        break;
                    case 2:
                        setTvState(tvAmStateThree, am);
                        setTvState(tvPmStateThree, pm);
                        break;
                    case 3:
                        setTvState(tvAmStateFour, am);
                        setTvState(tvPmStateFour, pm);
                        break;
                    case 4:
                        setTvState(tvAmStateFive, am);
                        setTvState(tvPmStateFive, pm);
                        break;
                    case 5:
                        setTvState(tvAmStateSix, am);
                        setTvState(tvPmStateSix, pm);
                        break;
                    case 6:
                        setTvState(tvAmStateSeven, am);
                        setTvState(tvPmStateSeven, pm);
                        break;

                }
            }
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                setDoctorInfo(msg);
                break;
            case GET_NO_DATA:
                break;
        }
    }
}
