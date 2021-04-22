package com.vice.bloodpressure.bean;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.vice.bloodpressure.R;

import java.util.List;

/**
 * Created by jin
 */

public class LineChartEntity extends BaseChartEntityBean<Entry> {

    public LineChartEntity(LineChart lineChart, List<Entry>[] entries, String[] labels,
                           int[] chartColor, int valueColor, float textSize) {
        super(lineChart, entries, labels, chartColor, valueColor, textSize);
    }


    @Override
    protected void initChart() {
        super.initChart();
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().enableGridDashedLine(10f, 15f, 0f);
        mChart.getAxisLeft().setGridColor(Color.parseColor("#f5f5f5"));
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getAxisLeft().setDrawZeroLine(false);
        mChart.getAxisLeft().setZeroLineWidth(0f);

        mChart.getAxisRight().setDrawZeroLine(false);
        mChart.getAxisRight().setZeroLineWidth(0f);

        mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setDrawGridLines(true);
    }

    @Override
    protected void setChartData() {
        LineDataSet[] lineDataSet = new LineDataSet[mEntries.length];
        if (mChart.getData() != null && mChart.getData().getDataSetCount() == mEntries.length) {
            for (int index = 0, len = mEntries.length; index < len; index++) {
                List<Entry> list = mEntries[index];
                lineDataSet[index] = (LineDataSet) mChart.getData().getDataSetByIndex(index);
                lineDataSet[index].setValues(list);
            }
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            for (int index = 0, len = mEntries.length; index < len; index++) {
                lineDataSet[index] = new LineDataSet(mEntries[index], labels[index]);
                lineDataSet[index].setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSet[index].setColor(mChartColors[index]);
                lineDataSet[index].setLineWidth(3.0f);
                lineDataSet[index].setCircleRadius(3.5f);
                lineDataSet[index].setCircleColor(mChartColors[index]);

                lineDataSet[index].setValueTextColor(mChartColors[index]);

                if (hasDotted != null && hasDotted[index]) {
                    lineDataSet[index].setDrawCircles(false);
                    lineDataSet[index].setCircleColor(R.color.white);
                    lineDataSet[index].enableDashedLine(10f, 15f, 0f);
                    lineDataSet[index].enableDashedHighlightLine(10f, 15f, 0f);
                }

            }
            // create a data object with the datasets
            LineData data = new LineData(lineDataSet);
            data.setValueTextSize(mTextSize);
            // set data
            mChart.setData(data);
            mChart.animateX(2000, Easing.EaseInOutQuad);
        }
    }

    /**
     * <p>绘制曲线上点</p>
     *
     * @param draw true:绘制
     */
    public void drawCircle(boolean draw) {
        List<ILineDataSet> sets = ((LineChart) mChart).getData().getDataSets();
        for (ILineDataSet iSet : sets) {
            LineDataSet set = (LineDataSet) iSet;
            set.setDrawCircles(draw);
        }
        mChart.invalidate();
    }

    /**
     * 设置图表颜色值
     *
     * @param mode LineDataSet.Mode
     */
    public void setLineMode(LineDataSet.Mode mode) {
        List<ILineDataSet> sets = ((LineChart) mChart).getData().getDataSets();
        for (ILineDataSet iSet : sets) {
            LineDataSet set = (LineDataSet) iSet;
            set.setMode(mode);
        }
        mChart.invalidate();
    }
}
