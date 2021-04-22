package com.vice.bloodpressure.utils;

import android.graphics.Color;
import android.graphics.Matrix;

import com.blankj.utilcode.util.ColorUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.horen.chart.linechart.ILineChartData;
import com.vice.bloodpressure.R;

import java.util.ArrayList;
import java.util.List;

public class LineChartHelperUtils {


    private LineChart lineChart;

    public LineChartHelperUtils(LineChart lineChart) {
        this.lineChart = lineChart;
        //背景颜色
        lineChart.setBackgroundColor(Color.WHITE);
        //隐藏右边Y轴
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setScaleEnabled(false);
        //设置是否可以通过双击屏幕放大图表。
        lineChart.setDoubleTapToZoomEnabled(false);
        //设置描述
        lineChart.getDescription().setEnabled(false);
        //设置按比例放缩柱状图
        lineChart.setPinchZoom(true);
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置不可缩放
        lineChart.setDragEnabled(true);
        lineChart.setScaleXEnabled(true);
    }


    /**
     * 初始化LineChart
     */
    private void initLineChart() {
        //折线图例 标签 设置
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //x坐标轴设置
        XAxis xAxis = lineChart.getXAxis();
        //x轴对齐位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏网格线
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(ColorUtils.getColor(R.color.gray_text));
        xAxis.setTextSize(10f);
        //xAxis.setAxisLineWidth(2);
        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(ColorUtils.getColor(R.color.gray_text));
        leftAxis.setTextSize(10f);
        //leftAxis.setAxisLineWidth(2);
        //保证Y轴从0开始，不然会上移一点
        //设置范围
        leftAxis.setAxisMinimum(0f);
    }

    /**
     * 展示折线图(一条)
     *
     * @param barChartData 单个柱状图数据
     * @param color        柱状图颜色
     * @param displayCount 一页显示柱状图数
     */
    public void showSingleLineChart(List<ILineChartData> barChartData, int color, int displayCount) {
        initLineChart();
        //X轴真实显示lable
        List<String> xValue = new ArrayList<>();
        //Y轴图标数据
        //单种柱状图数据集
        ArrayList<Entry> entries = new ArrayList<>();
        for (int j = 0; j < barChartData.size(); j++) {
            xValue.add(barChartData.get(j).getLabelName());
            entries.add(new Entry(j, barChartData.get(j).getValue()));
        }
        //多折线图数据集
        LineData lineData = new LineData();
        //折线图例 标签 设置
        lineChart.getLegend().setEnabled(false);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValue));
        //数据集合标签名
        LineDataSet barDataSet = new LineDataSet(entries, "");
        //数据点
        lineData.addDataSet(barDataSet);
        lineChart.setData(lineData);
        //Y轴取整关键
        lineData.setValueFormatter(new MonthlyIntegerYValueFormatter());
        //设置动画效果
        Matrix m = new Matrix();
        //两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的2倍
        if (xValue.size() > displayCount) {
            m.postScale(xValue.size() / displayCount, 1f);
        }
        //将图表动画显示之前进行缩放
        lineChart.getViewPortHandler().refresh(m, lineChart, false);
        lineChart.animateY(1500, Easing.Linear);
        lineChart.animateX(1000, Easing.Linear);
    }

    public static class MonthlyIntegerYValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return (int) (value) + "";
        }
    }
}
