package com.horen.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/02/13:00
 * @description :自定义X轴
 * @github :https://github.com/chenyy0708
 */
public class HRXFormatter implements IAxisValueFormatter {

    private List<String> values;

    public HRXFormatter(List<String> values) {
        this.values = values;
    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return values.get((int) value % values.size());
    }
}
