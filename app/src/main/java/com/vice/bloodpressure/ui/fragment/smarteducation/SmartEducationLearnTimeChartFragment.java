package com.vice.bloodpressure.ui.fragment.smarteducation;

import android.graphics.Color;
import android.os.Message;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.horen.chart.linechart.ILineChartData;
import com.horen.chart.linechart.LineChartHelper;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.SmartEducationLearnTimeListBean;
import com.vice.bloodpressure.bean.TestLineData;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.view.NewMarkerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class SmartEducationLearnTimeChartFragment extends BaseFragment {
    private static final int GET_LEARN_TIME_LIST = 10010;
    @BindView(R.id.lc_chart)
    LineChart lcChart;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_education_learn_time_chart;
    }

    @Override
    protected void init(View rootView) {
        getLearnTime();
    }

    private void getLearnTime() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("days", getArguments().getString("day"));
        XyUrl.okPost(XyUrl.SMART_EDUCATION_LEARN_TIME, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationLearnTimeListBean> list = JSONObject.parseArray(value, SmartEducationLearnTimeListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_LEARN_TIME_LIST;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_LEARN_TIME_LIST:
                List<SmartEducationLearnTimeListBean> list = (List<SmartEducationLearnTimeListBean>) msg.obj;
                if (list != null && list.size() > 0) {
                    //数据填充
                    ArrayList<ILineChartData> entries = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        double timenum = list.get(i).getTimenum();
                        String addtime = list.get(i).getAddtime();
                        entries.add(new TestLineData(timenum, addtime));
                    }
                    LineChartHelper barChartHelper = new LineChartHelper(lcChart);
                    lcChart.getAxisLeft().setTextColor(ColorUtils.getColor(R.color.smart_education_line_chart));
                    //创建单条折线的图表
                    barChartHelper.showSingleLineChart(entries, ColorUtils.getColor(R.color.main_home), 31);
                    //设置MarkerView
                    int[] colors = {Color.parseColor("#43B8BC")};
                    final NewMarkerView markerView = new NewMarkerView(getPageContext(), R.layout.custom_marker_view_layout, colors, 1);
                    markerView.setCallBack(new NewMarkerView.CallBack() {
                        @Override
                        public void onCallBack(float x, String value) {
                            int index = (int) (x);
                            if (index < 0) {
                                return;
                            }
                            SmartEducationLearnTimeListBean weightListBean = list.get(index);
                            String addtime = weightListBean.getAddtime();
                            double timenum = weightListBean.getTimenum();
                            String weightText = "日期: " + addtime;
                            weightText += "\n";
                            weightText += "学习时间: " + timenum;
                            markerView.getTvContent().setText(weightText);
                        }
                    });
                    markerView.setChartView(lcChart);
                    lcChart.setMarker(markerView);
                }
                break;
        }
    }
}
