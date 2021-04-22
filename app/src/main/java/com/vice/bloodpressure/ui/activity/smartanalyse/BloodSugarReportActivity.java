package com.vice.bloodpressure.ui.activity.smartanalyse;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RecentThreeMonthListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.SugarReportBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.view.MyListView;
import com.vice.bloodpressure.view.XYMarkerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 血糖分析报告(区分首页和个人中心)
 * time:首页""   个人中心time
 * 作者: LYD
 * 创建日期: 2019/4/23 11:16
 */
public class BloodSugarReportActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final String TAG = "BloodSugarReportActivity";
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_bottom_reference_data)
    TextView tvBottomReferenceData;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    //近3个月糖化血红蛋白情况
    @BindView(R.id.ll_recent_three_month)
    LinearLayout llRecentThreeMonth;
    @BindView(R.id.ll_recent_three_month_no_data)
    LinearLayout llRecentThreeMonthNoData;
    @BindView(R.id.tv_recent_three_month_no_data)
    TextView tvRecentThreeMonthNoData;
    @BindView(R.id.tv_sugar_control_target)
    TextView tvSugarControlTarget;
    @BindView(R.id.lv_blood_sugar_report)
    MyListView lvBloodSugarReport;
    @BindView(R.id.tv_recent_three_month_desc)
    TextView tvRecentThreeMonthDesc;
    //3月份血糖概况
    @BindView(R.id.tv_month_sugar_title)
    TextView tvMonthSugarTitle;
    @BindView(R.id.pie_chart)
    PieChart mPieChart;
    @BindView(R.id.tv_month_sugar_desc)
    TextView tvMonthSugarDesc;
    @BindView(R.id.ll_pie_have_data)
    LinearLayout llPieData;
    @BindView(R.id.img_pie_no_data)
    ImageView imgPieNoData;
    //00空腹血糖趋势图
    @BindView(R.id.tv_empty_sugar_control_target)
    TextView tvEmptySugarControlTarget;
    @BindView(R.id.sc_empty)
    ScatterChart scEmpty;
    @BindView(R.id.tv_empty_desc)
    TextView tvEmptyDesc;
    @BindView(R.id.tv_after_break_sugar_control_target)
    TextView tvAfterBreakSugarControlTarget;
    @BindView(R.id.sc_after_break)
    ScatterChart scAfterBreak;
    @BindView(R.id.tv_after_break_desc)
    TextView tvAfterBreakDesc;
    //02午餐前
    @BindView(R.id.tv_before_lunch_sugar_control_target)
    TextView tvBeforeLunchSugarControlTarget;
    @BindView(R.id.sc_before_lunch)
    ScatterChart scBeforeLunch;
    @BindView(R.id.tv_before_lunch_desc)
    TextView tvBeforeLunchDesc;
    //03午餐后
    @BindView(R.id.tv_after_lunch_sugar_control_target)
    TextView tvAfterLunchSugarControlTarget;
    @BindView(R.id.sc_after_lunch)
    ScatterChart scAfterLunch;
    @BindView(R.id.tv_after_lunch_desc)
    TextView tvAfterLunchDesc;
    //04晚餐前
    @BindView(R.id.tv_before_dinner_sugar_control_target)
    TextView tvBeforeDinnerSugarControlTarget;
    @BindView(R.id.sc_before_dinner)
    ScatterChart scBeforeDinner;
    @BindView(R.id.tv_before_dinner_desc)
    TextView tvBeforeDinnerDesc;
    //05晚餐后
    @BindView(R.id.tv_after_dinner_sugar_control_target)
    TextView tvAfterDinnerSugarControlTarget;
    @BindView(R.id.sc_after_dinner)
    ScatterChart scAfterDinner;
    @BindView(R.id.tv_after_dinner_desc)
    TextView tvAfterDinnerDesc;
    //06睡前
    @BindView(R.id.tv_before_sleep_sugar_control_target)
    TextView tvBeforeSleepSugarControlTarget;
    @BindView(R.id.sc_before_sleep)
    ScatterChart scBeforeSleep;
    @BindView(R.id.tv_before_sleep_desc)
    TextView tvBeforeSleepDesc;
    //07凌晨
    @BindView(R.id.tv_night_sugar_control_target)
    TextView tvNightSugarControlTarget;
    @BindView(R.id.sc_night)
    ScatterChart scNight;
    @BindView(R.id.tv_night_desc)
    TextView tvNightDesc;
    //最后总结
    @BindView(R.id.tv_total_desc)
    TextView tvTotalDesc;
    @BindView(R.id.ll_total_desc)
    LinearLayout llTotalDesc;
    //.............................................
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.ll_after_break)
    LinearLayout llAfterBreak;
    @BindView(R.id.ll_before_lunch)
    LinearLayout llBeforeLunch;
    @BindView(R.id.ll_after_lunch)
    LinearLayout llAfterLunch;
    @BindView(R.id.ll_before_dinner)
    LinearLayout llBeforeDinner;
    @BindView(R.id.ll_after_dinner)
    LinearLayout llAfterDinner;
    @BindView(R.id.ll_before_sleep)
    LinearLayout llBeforeSleep;
    @BindView(R.id.ll_night)
    LinearLayout llNight;
    //.............................................


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血糖分析报告");
        getData();
    }

    /**
     * 获取血糖报告
     */
    private void getData() {
        HashMap map = new HashMap<>();
        map.put("randtime", getIntent().getStringExtra("time"));
        XyUrl.okPost(XyUrl.GET_PORT_ANALYSIS_BLOOD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SugarReportBean bloodPressureList = JSONObject.parseObject(value, SugarReportBean.class);
                Message msg = getHandlerMessage();
                msg.what = GET_DATA;
                msg.obj = bloodPressureList;
                getHandler().sendMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_blood_sugar_report, contentLayout, false);
        return view;
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                SugarReportBean data = (SugarReportBean) msg.obj;
                setTopReportDesc(data);
                setRecentThreeMonthBloodSugarEggWhiteCase(data);
                setBloodSugarPieChart(data);
                setEightBloodSugarScatterChart(data);
                break;
        }
    }

    /**
     * 顶部报告说明
     *
     * @param data
     */
    private void setTopReportDesc(SugarReportBean data) {
        tvName.setText(data.getNickname() + ",您好!");
        String year = data.getYear();
        String month = data.getMonth();
        //糖尿病类型
        String diabetesleim = data.getDiabetesleim();
        String begintime = data.getBegintime();
        String endtime = data.getEndtime();
        String desc = String.format(getString(R.string.sugar_desc), year, month, diabetesleim, begintime, endtime);
        tvDesc.setText(desc);
    }

    /**
     * 三个月的血红蛋白数据
     *
     * @param data
     */
    private void setRecentThreeMonthBloodSugarEggWhiteCase(SugarReportBean data) {
        //糖尿病类型
        String diabetesleim = data.getDiabetesleim();
        //共检测糖化血红蛋白次数
        int num = data.getNum();
        //糖化建议说明
        String tshuom = data.getTshuom();
        if (num > 0) {
            llRecentThreeMonthNoData.setVisibility(View.GONE);
            llRecentThreeMonth.setVisibility(View.VISIBLE);
            //糖化控制目标
            String thmb = data.getThmb();
            //糖化血红蛋白列表
            List<SugarReportBean.DanbaiBean> listDanBai = data.getDanbai();
            //达标次数
            int dabiao = data.getDabiao();
            //未达标次数
            int nobiao = data.getNobiao();
            tvSugarControlTarget.setText("糖化控制目标：" + thmb);
            String recentThreeMonthDesc = String.format(getString(R.string.sugar_control_target), num + "", dabiao + "", nobiao + "");
            tvRecentThreeMonthDesc.setText(recentThreeMonthDesc + tshuom);
            if (listDanBai != null && listDanBai.size() > 0) {
                lvBloodSugarReport.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_lv_blood_sugar_report, null));
                lvBloodSugarReport.setAdapter(new RecentThreeMonthListAdapter(getPageContext(), R.layout.item_lv_blood_sugar_report, listDanBai, diabetesleim));
            }
        } else {
            llRecentThreeMonthNoData.setVisibility(View.VISIBLE);
            llRecentThreeMonth.setVisibility(View.GONE);
            tvRecentThreeMonthNoData.setText(tshuom);
        }
    }

    /**
     * 设置血糖饼图
     *
     * @param data
     */
    private void setBloodSugarPieChart(SugarReportBean data) {
        String month = data.getMonth();
        tvMonthSugarTitle.setText(month + "月份血糖概况");
        //总次数
        int xuezci = data.getXuezci();
        //正常
        int xuezc = data.getXuezc();
        //偏高
        int xuepg = data.getXuepg();
        //偏低
        int xuepd = data.getXuepd();
        //说明
        String xuesm = data.getXuesm();
        String monthSugarDesc = String.format(Utils.getApp().getString(R.string.month_sugar_desc), xuezci + "", xuezc + "", xuepg + "", xuepd + "");
        tvMonthSugarDesc.setText(monthSugarDesc + xuesm);
        if (xuezci > 0) {
            llPieData.setVisibility(View.VISIBLE);
            imgPieNoData.setVisibility(View.GONE);
            llTotalDesc.setVisibility(View.VISIBLE);
            tvBottomReferenceData.setVisibility(View.VISIBLE);
            setPieChart(xuezc, xuepg, xuepd);
        } else {
            llPieData.setVisibility(View.GONE);
            imgPieNoData.setVisibility(View.VISIBLE);
            llTotalDesc.setVisibility(View.GONE);
            tvBottomReferenceData.setVisibility(View.GONE);
            tvMonthSugarDesc.setText("您当月还没进行血糖检测，健康容不得偷懒哦，快去测一下吧");
        }
    }

    /**
     * 设置饼图
     *
     * @param xuezc 正常次数
     * @param xuepg 偏高
     * @param xuepd 偏低
     */
    private void setPieChart(int xuezc, int xuepg, int xuepd) {
        //以百分比为单位
        mPieChart.setUsePercentValues(true);
        //描述
        mPieChart.getDescription().setEnabled(false);
        //图例
        mPieChart.getLegend().setEnabled(false);
        //是否将饼心绘制空心(ture:环形图 false:饼图)
        mPieChart.setDrawHoleEnabled(true);
        //设置饼图中心圆的颜色
        mPieChart.setHoleColor(Color.WHITE);
        //设置饼图中心圆的边颜色
        mPieChart.setTransparentCircleColor(Color.WHITE);
        //设置饼图中心圆的边的透明度，0--255 0表示完全透明 255完全不透明
        mPieChart.setTransparentCircleAlpha(255);
        //设置饼图中心圆的半径
        mPieChart.setHoleRadius(40f);
        //设置饼图中心圆的边的半径
        mPieChart.setTransparentCircleRadius(30f);
        //设置是否可以触摸旋转
        mPieChart.setRotationEnabled(true);
        //设置是否点击后将对应的区域进行突出
        mPieChart.setHighlightPerTapEnabled(false);
        //标签颜色
        mPieChart.setEntryLabelColor(ColorUtils.getColor(R.color.black_text));
        mPieChart.setEntryLabelTextSize(12f);
        //初始旋转90
        mPieChart.setRotationAngle(45f);
        mPieChart.setExtraOffsets(26, 5, 26, 5);
        mPieChart.setDrawEntryLabels(true);
        //设置数据//设置颜色
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if (0 == xuezc) {

        } else {
            colors.add(ColorUtils.getColor(R.color.sugar_normal));
            entries.add(new PieEntry(xuezc, xuezc + "次血糖正常"));
        }
        if (0 == xuepg) {

        } else {
            colors.add(ColorUtils.getColor(R.color.sugar_high));
            entries.add(new PieEntry(xuepg, xuepg + "次血糖偏高"));
        }
        if (0 == xuepd) {

        } else {
            colors.add(ColorUtils.getColor(R.color.sugar_low));
            entries.add(new PieEntry(xuepd, xuepd + "次血糖偏低"));
        }
        //添加数据
        PieDataSet dataSet = new PieDataSet(entries, "Label");
        //设置颜色
        dataSet.setColors(colors);
        //设置饼图每个区域的间隔
        dataSet.setSliceSpace(5f);
        //设置出界
        dataSet.setValueLinePart1OffsetPercentage(60.f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //添加数据
        PieData pieData = new PieData(dataSet);
        //自定义饼图每个区域显示的值
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new MonthlyIntegerYValueFormatter());
        //设置值的大小
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(ColorUtils.getColor(R.color.black_text));
        //设置数据
        mPieChart.setData(pieData);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }

    /**
     * 设置八大散点图
     *
     * @param data
     */
    private void setEightBloodSugarScatterChart(SugarReportBean data) {
        setEmpty(data);
        setAfterBreakfast(data);
        setBeforeLunch(data);
        setAfterLunch(data);
        setBeforeDinner(data);
        setAfterDinner(data);
        stBeforeSleep(data);
        setNightSugar(data);
        //设置总结性文字
        setTotalDesc();
    }

    private void setTotalDesc() {
        int gone = View.GONE;
        int empty = llEmpty.getVisibility();
        int afterBreak = llAfterBreak.getVisibility();
        int beforeLunch = llBeforeLunch.getVisibility();
        int afterLunch = llAfterLunch.getVisibility();
        int beforeDinner = llBeforeDinner.getVisibility();
        int afterDinner = llAfterDinner.getVisibility();
        int beforeSleep = llBeforeSleep.getVisibility();
        int night = llNight.getVisibility();
        List<String> listNoData = new ArrayList<>();
        if (empty == gone) {
            listNoData.add("空腹");
        }
        if (afterBreak == gone) {
            listNoData.add("早餐后");
        }
        if (beforeLunch == gone) {
            listNoData.add("午餐前");
        }
        if (afterLunch == gone) {
            listNoData.add("午餐后");
        }
        if (beforeDinner == gone) {
            listNoData.add("晚餐前");
        }
        if (afterDinner == gone) {
            listNoData.add("晚餐后");
        }
        if (beforeSleep == gone) {
            listNoData.add("睡前");
        }
        if (night == gone) {
            listNoData.add("凌晨");
        }
        //无 午餐前、 晚餐前 和 凌晨血糖记录，请坚持自我监测哦~
        if (listNoData.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("无");
            for (int i = 0; i < listNoData.size(); i++) {
                if (i == listNoData.size() - 2) {
                    sb.append(listNoData.get(i));
                    sb.append("和");
                } else if (i == listNoData.size() - 1) {
                    sb.append(listNoData.get(i));
                    sb.append("血糖记录，请坚持自我监测哦~");
                } else {
                    sb.append(listNoData.get(i));
                    sb.append("、");
                }
            }
            tvTotalDesc.setText(sb);
        } else if (listNoData.size() == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("无");
            String str0 = listNoData.get(0);
            sb.append(str0);
            sb.append("和");
            String str1 = listNoData.get(1);
            sb.append(str1);
            sb.append("血糖记录，请坚持自我监测哦~");
            tvTotalDesc.setText(sb);
        } else if (listNoData.size() == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("无");
            String str = listNoData.get(0);
            sb.append(str);
            sb.append("血糖记录，请坚持自我监测哦~");
            tvTotalDesc.setText(sb);
        } else if (listNoData.size() == 0) {
            llTotalDesc.setVisibility(View.GONE);
        }
    }

    private void setEmpty(SugarReportBean data) {
        //空腹
        String emptySugarControlTarget = "空腹血糖控制目标：";
        //空腹控制目标
        List<Double> kongfukzmb = data.getKongfukzmb();
        if (kongfukzmb != null && 2 == kongfukzmb.size()) {
            Double left = kongfukzmb.get(0);
            Double right = kongfukzmb.get(1);
            tvEmptySugarControlTarget.setText(emptySugarControlTarget + left + "-" + right + "mmol/L");
        }
        //图表
        //空腹说明
        String kongfusm = data.getKongfusm();
        if (data.getKongfuzhi() != null && data.getKongfuzhi().size() > 0) {
            llEmpty.setVisibility(View.VISIBLE);
            setScatterData(data.getKongfuzhi(), 0, kongfukzmb);
            //空腹测量总次数
            String kongfu = data.getKongfu();
            //空腹正常次数
            String kongfuzc = data.getKongfuzc();
            //空腹偏高次数
            String kongfupg = data.getKongfupg();
            //空腹偏低次数
            String kongfupd = data.getKongfupd();
            //空腹偏高值
            double kongfupgzhi = data.getKongfupgzhi();
            //空腹偏低值
            double kongfupdzhi = data.getKongfupdzhi();
            String emptyBloodSugarDesc = String.format(getString(R.string.blood_sugar_desc),
                    kongfu, "空腹", kongfuzc, kongfupg, kongfupd, kongfupgzhi + "", kongfupdzhi + "");
            tvEmptyDesc.setText(emptyBloodSugarDesc + kongfusm);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
    }

    private void setAfterBreakfast(SugarReportBean data) {
        //控制目标
        //早餐后
        String afterBreakfastSugarControlTarget = "早餐后血糖控制目标：";
        List<Double> zch2kzmb = data.getZch2kzmb();
        if (zch2kzmb != null && 2 == zch2kzmb.size()) {
            Double left = zch2kzmb.get(0);
            Double right = zch2kzmb.get(1);
            tvAfterBreakSugarControlTarget.setText(afterBreakfastSugarControlTarget + left + "-" + right + "mmol/L");
        }
        //图表
        //空腹说明
        String zch2sm = data.getZch2sm();
        if (data.getZch2zhi() != null && data.getZch2zhi().size() > 0) {
            llAfterBreak.setVisibility(View.VISIBLE);
            setScatterData(data.getZch2zhi(), 1, zch2kzmb);
            //说明
            String zch2 = data.getZch2();
            String zch2zc = data.getZch2zc();
            String zch2pg = data.getZch2pg();
            String zch2pd = data.getZch2pd();
            double zch2pgzhi = data.getZch2pgzhi();
            double zch2pdzhi = data.getZch2pdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    zch2, "早餐后", zch2zc, zch2pg, zch2pd, zch2pgzhi + "", zch2pdzhi + "");
            tvAfterBreakDesc.setText(emptyBloodSugarDesc + zch2sm);
        } else {
            llAfterBreak.setVisibility(View.GONE);
        }
    }

    private void setBeforeLunch(SugarReportBean data) {
        String beforeLunchControlTarget = "午餐前血糖控制目标：";//午餐前
        List<Double> wucqkzmb = data.getWucqkzmb();
        if (wucqkzmb != null && 2 == wucqkzmb.size()) {
            Double left = wucqkzmb.get(0);
            Double right = wucqkzmb.get(1);
            tvBeforeLunchSugarControlTarget.setText(beforeLunchControlTarget + left + "-" + right + "mmol/L");
        }
        //图标
        String wucqsm = data.getWucqsm();
        if (data.getWucqzhi() != null && data.getWucqzhi().size() > 0) {
            llBeforeLunch.setVisibility(View.VISIBLE);
            setScatterData(data.getWucqzhi(), 2, wucqkzmb);
            int wucq = data.getWucq();
            String wucqzc = data.getWucqzc();
            int wucqpg = data.getWucqpg();
            String wucqpd = data.getWucqpd();
            double wucqpgzhi = data.getWucqpgzhi();
            double wucqpdzhi = data.getWucqpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wucq + "", "午餐前", wucqzc + "", wucqpg + "", wucqpd + "", wucqpgzhi + "", wucqpdzhi + "");
            tvBeforeLunchDesc.setText(emptyBloodSugarDesc + wucqsm);
        } else {
            llBeforeLunch.setVisibility(View.GONE);
        }
    }

    private void setAfterLunch(SugarReportBean data) {
        String afterLunchSugarControlTarget = "午餐后血糖控制目标：";//午餐后
        List<Double> wuchkzmb = data.getWuchkzmb();
        if (wuchkzmb != null && 2 == wuchkzmb.size()) {
            Double left = wuchkzmb.get(0);
            Double right = wuchkzmb.get(1);
            tvAfterLunchSugarControlTarget.setText(afterLunchSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String wuchsm = data.getWuchsm();
        if (data.getWuchzhi() != null && data.getWuchzhi().size() > 0) {
            llAfterLunch.setVisibility(View.VISIBLE);
            setScatterData(data.getWuchzhi(), 3, wuchkzmb);
            String wuch = data.getWuch();
            String wuchzc = data.getWuchzc();
            String wuchpg = data.getWuchpg();
            String wuchpd = data.getWuchpd();
            double wuchpgzhi = data.getWuchpgzhi();
            double wuchpdzhi = data.getWuchpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wuch + "", "午餐后", wuchzc + "", wuchpg + "", wuchpd + "", wuchpgzhi + "", wuchpdzhi + "");
            tvAfterLunchDesc.setText(emptyBloodSugarDesc + wuchsm);
        } else {
            llAfterLunch.setVisibility(View.GONE);
        }
    }

    private void setBeforeDinner(SugarReportBean data) {
        String beforeDinnerSugarControlTarget = "晚餐前血糖控制目标：";//晚餐前
        List<Double> wancqkzmb = data.getWancqkzmb();
        if (wancqkzmb != null && 2 == wancqkzmb.size()) {
            Double left = wancqkzmb.get(0);
            Double right = wancqkzmb.get(1);
            tvBeforeDinnerSugarControlTarget.setText(beforeDinnerSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String wancqsm = data.getWancqsm();
        if (data.getWancqzhi() != null && data.getWancqzhi().size() > 0) {
            llBeforeDinner.setVisibility(View.VISIBLE);
            setScatterData(data.getWancqzhi(), 4, wancqkzmb);
            String wancq = data.getWancq();
            String wancqzc = data.getWancqzc();
            String wancqpg = data.getWancqpg();
            String wancqpd = data.getWancqpd();
            double wancqpgzhi = data.getWancqpgzhi();
            double wancqpdzhi = data.getWancqpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wancq + "", "晚饭前", wancqzc + "", wancqpg + "", wancqpd + "", wancqpgzhi + "", wancqpdzhi + "");
            tvBeforeDinnerDesc.setText(emptyBloodSugarDesc + wancqsm);
        } else {
            llBeforeDinner.setVisibility(View.GONE);
        }
    }

    private void setAfterDinner(SugarReportBean data) {
        String afterDinnerSugarControlTarget = "晚餐后血糖控制目标：";//晚餐后
        List<Double> wanchkzmb = data.getWanchkzmb();
        if (wanchkzmb != null && 2 == wanchkzmb.size()) {
            Double left = wanchkzmb.get(0);
            Double right = wanchkzmb.get(1);
            tvAfterDinnerSugarControlTarget.setText(afterDinnerSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String wanchsm = data.getWanchsm();
        if (data.getWanchzhi() != null && data.getWanchzhi().size() > 0) {
            llAfterDinner.setVisibility(View.VISIBLE);
            setScatterData(data.getWanchzhi(), 5, wanchkzmb);
            String wanch = data.getWanch();
            String wanchzc = data.getWanchzc();
            String wanchpg = data.getWanchpg();
            String wanchpd = data.getWanchpd();
            double wanchpgzhi = data.getWanchpgzhi();
            double wanchpdzhi = data.getWanchpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    wanch + "", "晚饭后", wanchzc + "", wanchpg + "", wanchpd + "", wanchpgzhi + "", wanchpdzhi + "");
            tvAfterDinnerDesc.setText(emptyBloodSugarDesc + wanchsm);
        } else {
            llAfterDinner.setVisibility(View.GONE);
        }
    }

    private void stBeforeSleep(SugarReportBean data) {
        String beforeSleepControlTarget = "睡前血糖控制目标：";//睡前
        List<Double> shuiqkzmb = data.getShuiqkzmb();
        if (shuiqkzmb != null && 2 == shuiqkzmb.size()) {
            Double left = shuiqkzmb.get(0);
            Double right = shuiqkzmb.get(1);
            tvBeforeSleepSugarControlTarget.setText(beforeSleepControlTarget + left + "-" + right + "mmol/L");
        }
        String shuiqsm = data.getShuiqsm();
        if (data.getShuiqzhi() != null && data.getShuiqzhi().size() > 0) {
            llBeforeSleep.setVisibility(View.VISIBLE);
            setScatterData(data.getShuiqzhi(), 6, shuiqkzmb);
            String shuiq = data.getShuiq();
            String shuiqzc = data.getShuiqzc();
            String shuiqpg = data.getShuiqpg();
            String shuiqpd = data.getShuiqpd();
            double shuiqpgzhi = data.getShuiqpgzhi();
            double shuiqpdzhi = data.getShuiqpdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    shuiq + "", "睡前", shuiqzc + "", shuiqpg + "", shuiqpd + "", shuiqpgzhi + "", shuiqpdzhi + "");
            tvBeforeSleepDesc.setText(emptyBloodSugarDesc + shuiqsm);
        } else {
            llBeforeSleep.setVisibility(View.GONE);
        }
    }

    private void setNightSugar(SugarReportBean data) {
        String nightSugarControlTarget = "凌晨血糖控制目标：";//凌晨
        List<Double> suijikzmb = data.getSuijikzmb();
        if (suijikzmb != null && 2 == suijikzmb.size()) {
            Double left = suijikzmb.get(0);
            Double right = suijikzmb.get(1);
            tvNightSugarControlTarget.setText(nightSugarControlTarget + left + "-" + right + "mmol/L");
        }
        String suijism = data.getSuijism();
        if (data.getSuijizhi() != null && data.getSuijizhi().size() > 0) {
            llNight.setVisibility(View.VISIBLE);
            setScatterData(data.getSuijizhi(), 7, suijikzmb);
            String suiji = data.getSuiji();
            String suijizc = data.getSuijizc();
            String suijipg = data.getSuijipg();
            String suijipd = data.getSuijipd();
            double suijipgzhi = data.getSuijipgzhi();
            double suijipdzhi = data.getSuijipdzhi();
            String emptyBloodSugarDesc = String.format(Utils.getApp().getString(R.string.blood_sugar_desc),
                    suiji + "", "凌晨", suijizc + "", suijipg + "", suijipd + "", suijipgzhi + "", suijipdzhi + "");
            tvNightDesc.setText(emptyBloodSugarDesc + suijism);
        } else {
            llNight.setVisibility(View.GONE);
        }
    }


    /**
     * 设置空腹散点图
     *
     * @param list
     */
    private void setScatterData(List<List<Float>> list, int type, List<Double> listTarget) {
        List<ScatterChart> listScatterChart = new ArrayList<>();
        listScatterChart.add(scEmpty);
        listScatterChart.add(scAfterBreak);
        listScatterChart.add(scBeforeLunch);
        listScatterChart.add(scAfterLunch);
        listScatterChart.add(scBeforeDinner);
        listScatterChart.add(scAfterDinner);
        listScatterChart.add(scBeforeSleep);
        listScatterChart.add(scNight);

        //禁用标签
        listScatterChart.get(type).getDescription().setEnabled(false);
        listScatterChart.get(type).getLegend().setEnabled(false);
        //支持缩放和拖动
        listScatterChart.get(type).setTouchEnabled(true);
        listScatterChart.get(type).setDragEnabled(false);
        listScatterChart.get(type).setScaleEnabled(false);
        listScatterChart.get(type).setPinchZoom(false);
        //添加MarkView
        XYMarkerView mv = new XYMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(listScatterChart.get(type));
        listScatterChart.get(type).setMarker(mv);
        //y轴
        YAxis yAxisLeft = listScatterChart.get(type).getAxisLeft();
        listScatterChart.get(type).getAxisRight().setEnabled(false);
        //设置最小值
        yAxisLeft.setAxisMinimum(0f);
        //设置最大值
        yAxisLeft.setAxisMaximum(32f);
        //网格线条间距
        yAxisLeft.setGranularity(1f);
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //x轴
        XAxis xl = listScatterChart.get(type).getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(false);
        xl.setDrawGridLines(false);
        //设置最小值
        xl.setAxisMinimum(1);
        //设置最大值
        xl.setAxisMaximum(31f);

        //填充数据
        //创建一个数据集,并给它一个类型
        List<Float> listX = new ArrayList<>();
        List<Float> listY = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Float> integers = list.get(i);
            for (int j = 0; j < integers.size(); j++) {
                Float integer = integers.get(j);
                if (0 == j) {
                    listX.add(integer);
                } else {
                    listY.add(integer);
                }
            }
        }
        Double targetLow = listTarget.get(0);
        Double targetHigh = listTarget.get(1);
        ArrayList<Float> listHighY = new ArrayList<>();
        ArrayList<Float> listHighX = new ArrayList<>();
        ArrayList<Float> listNormalY = new ArrayList<>();
        ArrayList<Float> listNormalX = new ArrayList<>();
        ArrayList<Float> listLowY = new ArrayList<>();
        ArrayList<Float> listLowX = new ArrayList<>();
        for (int i = 0; i < listY.size(); i++) {
            Float valueY = listY.get(i);
            Float valueX = listX.get(i);
            if (valueY > targetHigh) {
                listHighY.add(valueY);
                listHighX.add(valueX);
            } else if (valueY > targetLow) {
                listNormalY.add(valueY);
                listNormalX.add(valueX);
            } else {
                listLowY.add(valueY);
                listLowX.add(valueX);
            }
        }
        ArrayList<Entry> listDataHigh = new ArrayList<>();
        ArrayList<Entry> listDataNormal = new ArrayList<>();
        ArrayList<Entry> listDataLow = new ArrayList<>();
        for (int i = 0; i < listHighX.size(); i++) {
            listDataHigh.add(new Entry(listHighX.get(i), listHighY.get(i)));
        }
        for (int i = 0; i < listNormalX.size(); i++) {
            listDataNormal.add(new Entry(listNormalX.get(i), listNormalY.get(i)));
        }
        for (int i = 0; i < listLowX.size(); i++) {
            listDataLow.add(new Entry(listLowX.get(i), listLowY.get(i)));
        }
        //添加高值
        ScatterDataSet setHigh = new ScatterDataSet(listDataHigh, "");
        setHigh.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        setHigh.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_bp_high));
        setHigh.setScatterShapeHoleRadius(3f);
        setHigh.setScatterShapeSize(8f);
        //顶点显示值
        setHigh.setDrawValues(false);

        //添加正常值
        ScatterDataSet setNormal = new ScatterDataSet(listDataNormal, "");
        setNormal.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        setNormal.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_bp_normal));
        setNormal.setScatterShapeHoleRadius(3f);
        setNormal.setScatterShapeSize(8f);
        //顶点显示值
        setNormal.setDrawValues(false);


        ScatterDataSet setLow = new ScatterDataSet(listDataLow, "");
        setLow.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        setLow.setScatterShapeHoleColor(ColorUtils.getColor(R.color.sugar_bp_low));
        setLow.setScatterShapeHoleRadius(3f);
        setLow.setScatterShapeSize(8f);
        //顶点显示值
        setLow.setDrawValues(false);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(setHigh);
        dataSets.add(setNormal);
        dataSets.add(setLow);


        ScatterData data = new ScatterData(dataSets);
        listScatterChart.get(type).setData(data);
        listScatterChart.get(type).invalidate();
    }

    public static class MonthlyIntegerYValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return (int) (value) + "%";
        }
    }
}
