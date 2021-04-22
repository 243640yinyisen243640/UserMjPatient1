package com.vice.bloodpressure.ui.activity.sport;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.horen.chart.linechart.ILineChartData;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.SportWeekPagerDetailBean;
import com.vice.bloodpressure.bean.TestLineData;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.LineChartHelperUtils;
import com.vice.bloodpressure.view.NewMarkerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  运动周报
 * 作者: LYD
 * 创建日期: 2020/11/18 14:32
 */
public class SportWeekPagerDetailActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    @BindView(R.id.tv_sport_desc)
    TextView tvSportDesc;
    @BindView(R.id.tv_sport_sum_up)
    TextView tvSportSumUp;
    @BindView(R.id.line_chart)
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("运动周报");
        String beginTime = getIntent().getStringExtra("beginTime");
        String endTime = getIntent().getStringExtra("endTime");
        getSportDetail(beginTime, endTime);
    }

    private void getSportDetail(String beginTime, String endTime) {
        HashMap map = new HashMap<>();
        map.put("begin_time", beginTime);
        map.put("end_time", endTime);
        XyUrl.okPost(XyUrl.SPORT_REPORT_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SportWeekPagerDetailBean detailBean = JSONObject.parseObject(value, SportWeekPagerDetailBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = detailBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_sport_week_pager_detail, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                SportWeekPagerDetailBean detailBean = (SportWeekPagerDetailBean) msg.obj;
                //设置描述
                setSportDesc(detailBean);
                //设置图表
                setChart(detailBean.getWeekTime());
                //设置总结
                String message = detailBean.getMessage();
                tvSportSumUp.setText(message);
                break;
        }
    }


    /***
     * 设置描述
     * @param detailBean
     */
    private void setSportDesc(SportWeekPagerDetailBean detailBean) {
        String ableDay = detailBean.getAbleDay() + "";
        String ableKcal = detailBean.getAbleKcal() + "";
        String sportDays = detailBean.getSportDays() + "";
        String sportNum = detailBean.getSportNum() + "";
        String sportTime = detailBean.getSportTime() + "";
        String sportKcals = detailBean.getSportKcals() + "";
        String stepsKcals = detailBean.getStepsKcals() + "";
        String totalKcal = detailBean.getTotalKcal() + "";
        SpanUtils.with(tvSportDesc)
                .append("您上周应运动").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(ableDay).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))

                .append("天，消耗").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(ableKcal).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))

                .append("Kcal。实际运动").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(sportDays).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))

                .append("天，共").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(sportNum).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))

                .append("次，运动时间").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(sportTime).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))


                .append("分钟，消耗").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(sportKcals).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))

                .append("Kcal，步行消耗").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(stepsKcals).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))

                .append("Kcal，共消耗").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .append(totalKcal).setForegroundColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning))

                .append("Kcal。").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .create();


    }


    /**
     * 设置图表
     *
     * @param list
     */
    private void setChart(List<Integer> list) {
        String[] typeArray = getResources().getStringArray(R.array.sport_detail_x);
        //单个柱状图数据
        ArrayList<ILineChartData> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new TestLineData(list.get(i), typeArray[i]));
        }
        LineChartHelperUtils lineChartHelper = new LineChartHelperUtils(lineChart);
        //创建一条折线的图表
        lineChartHelper.showSingleLineChart(entries, ColorUtils.getColor(R.color.main_home), 7);
        //设置makeView
        //设置圆点
        List<ILineDataSet> sets = lineChart.getData().getDataSets();
        for (ILineDataSet iSet : sets) {
            LineDataSet barDataSet = (LineDataSet) iSet;
            barDataSet.setDrawCircles(true);
            barDataSet.setColor(ColorUtils.getColor(R.color.main_home));
            barDataSet.setCircleColor(ColorUtils.getColor(R.color.main_home));
            barDataSet.setDrawCircleHole(false);
            barDataSet.setValueTextColor(ColorUtils.getColor(R.color.main_home));
            barDataSet.setValueTextSize(10f);
            barDataSet.setDrawCircles(true);
            barDataSet.setDrawFilled(true);
            barDataSet.setLineWidth(1);
            barDataSet.setFillDrawable(Utils.getApp().getResources().getDrawable(R.drawable.linechart_gradient_drawable));
        }
        int[] colors = {ColorUtils.getColor(R.color.main_home)};
        final NewMarkerView markerView = new NewMarkerView(getPageContext(), R.layout.custom_marker_view_layout, colors, 1);
        markerView.setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) (x);
                if (index < 0) {
                    return;
                }
                String xValue = typeArray[index];
                int yValue = list.get(index);
                String weightText = String.format(Utils.getApp().getString(R.string.sport_detail_chart_view), xValue, yValue + "");
                markerView.getTvContent().setText(weightText);
            }
        });
        markerView.setChartView(lineChart);
        lineChart.setMarker(markerView);
    }
}