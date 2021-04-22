package com.horen.chart.linechart;

import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/14:19
 * @description :折线图的封装
 * @github :https://github.com/chenyy0708
 */
public class LineChartHelper {
    private LineChart lineChart;
    private YAxis yAxis;
    private XAxis xAxis;

    public LineChartHelper(LineChart lineChart) {
        this.lineChart = lineChart;
        yAxis = lineChart.getAxisLeft();
        xAxis = lineChart.getXAxis();
        //背景颜色
        lineChart.setBackgroundColor(Color.WHITE);
        // 隐藏右边Y轴
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setScaleEnabled(false);
        //设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setDoubleTapToZoomEnabled(false);
        //设置描述
        lineChart.getDescription().setEnabled(false);
        //设置按比例放缩柱状图
        lineChart.setPinchZoom(true);
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
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
        // x轴对齐位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏网格线
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        //y轴设置
        yAxis.setDrawGridLines(true);
        yAxis.setGranularity(1);
        yAxis.setAxisMinimum(0);
        //保证Y轴从0开始，不然会上移一点
        lineChart.getXAxis().setAxisMinimum(0);
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
        // X轴真实显示lable
        List<String> xValue = new ArrayList<>();
        // Y轴图标数据
        // 单种柱状图数据集
        ArrayList<Entry> entries = new ArrayList<>();
        for (int j = 0; j < barChartData.size(); j++) {
            if (!xValue.contains(barChartData.get(j).getLabelName())) {
                xValue.add(barChartData.get(j).getLabelName());
            }
            entries.add(new Entry(j, barChartData.get(j).getValue()));
        }
        // 多折线图数据集
        LineData lineData = new LineData();
        //折线图例 标签 设置
        lineChart.getLegend().setEnabled(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        // 数据集合标签名
        LineDataSet barDataSet = new LineDataSet(entries, "");
        barDataSet.setColor(color);
        barDataSet.setValueTextColor(color);
        barDataSet.setValueTextSize(10f);
        barDataSet.setDrawCircles(true);
        barDataSet.setCircleRadius(3.5f);
        barDataSet.setCircleColor(Color.argb(255,0,194,127));
        barDataSet.setDrawValues(false);
        barDataSet.setLineWidth(3.0f);
        lineData.addDataSet(barDataSet);
        lineChart.setData(lineData);
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
}