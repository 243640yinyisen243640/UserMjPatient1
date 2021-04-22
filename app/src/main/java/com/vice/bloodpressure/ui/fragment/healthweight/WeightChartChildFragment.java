package com.vice.bloodpressure.ui.fragment.healthweight;

import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.horen.chart.linechart.ILineChartData;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.TestLineData;
import com.vice.bloodpressure.bean.WeightListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.LineChartHelperUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.NewMarkerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class WeightChartChildFragment extends BaseFragment {
    private static final int GET_DATA_SUCCESS = 10010;
    private static final int GET_DATA_ERROR = 10011;
    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.tv_chart_desc)
    TextView tvChartDesc;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weight_chart_child;
    }

    @Override
    protected void init(View rootView) {
        String position = getArguments().getString("position");
        switch (position) {
            case "0":
                getWeightChart(7);
                tvChartDesc.setText("近7次体重记录");
                break;
            case "1":
                getWeightChart(30);
                tvChartDesc.setText("近30次体重记录");
                break;
            case "2":
                getWeightChart(60);
                tvChartDesc.setText("近60次体重记录");
                break;
        }
    }


    /**
     * 获取图表
     *
     * @param num
     */
    private void getWeightChart(int num) {
        HashMap map = new HashMap<>();
        map.put("num", num);
        XyUrl.okPost(XyUrl.GET_WEIGHT_CHART, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<WeightListBean> list = JSONObject.parseArray(value, WeightListBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA_SUCCESS;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                Message message = Message.obtain();
                message.what = GET_DATA_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    private void initLineChart(List<WeightListBean> list) {
        //单个柱状图数据
        ArrayList<ILineChartData> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            WeightListBean bean = list.get(i);
            double doubleY = TurnsUtils.getDouble(bean.getWeight(), 0);
            entries.add(new TestLineData(doubleY, bean.getDatetime()));
        }
        LineChartHelperUtils lineChartHelper = new LineChartHelperUtils(lineChart);
        //创建一条折线的图表
        lineChartHelper.showSingleLineChart(entries, ColorUtils.getColor(R.color.main_home), 7);
        //设置圆点
        List<ILineDataSet> sets = lineChart.getData().getDataSets();
        for (ILineDataSet iSet : sets) {
            LineDataSet set = (LineDataSet) iSet;
            set.setDrawCircles(true);
        }
        //设置makeView
        int[] colors = {ColorUtils.getColor(R.color.main_home)};
        final NewMarkerView markerView = new NewMarkerView(getPageContext(), R.layout.custom_marker_view_layout, colors, 1);
        markerView.setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) (x);
                if (index < 0) {
                    return;
                }
                WeightListBean weightListBean = list.get(index);
                String weight = weightListBean.getWeight();
                String weightText = String.format(Utils.getApp().getString(R.string.weight_chart), weight);
                weightText += "\n";
                weightText += weightListBean.getAddtime();
                markerView.getTvContent().setText(weightText);
            }
        });
        markerView.setChartView(lineChart);
        lineChart.setMarker(markerView);
        //设置Y轴最大值
        String count = list.get(0).getCount();
        float floatCount = TurnsUtils.getFloat(count, 0);
        lineChart.getAxisLeft().setAxisMaximum(floatCount);
        //获取x轴线
        //XAxis xAxis = lineChart.getXAxis();
        //xAxis.setLabelCount(list.size());
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA_SUCCESS:
                lineChart.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                List<WeightListBean> list = (List<WeightListBean>) msg.obj;
                initLineChart(list);
                break;
            case GET_DATA_ERROR:
                lineChart.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
                break;
        }
    }
}
