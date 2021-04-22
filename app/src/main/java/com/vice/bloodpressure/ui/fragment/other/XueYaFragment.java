package com.vice.bloodpressure.ui.fragment.other;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.BloodPressureBean;
import com.vice.bloodpressure.bean.LineChartEntity;
import com.vice.bloodpressure.bean.ScopeBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodPressureAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BloodPressureListActivity;
import com.vice.bloodpressure.utils.SPUtils;
import com.vice.bloodpressure.view.LineChartInViewPager;
import com.vice.bloodpressure.view.NewMarkerView;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class XueYaFragment extends BaseEventBusFragment {
    private static final int GET_DATA = 0x002998;
    private static final int NO_DATA = 0x001998;
    private static final int GET_SCOPE = 0x000998;
    @BindView(R.id.tv_test_record)
    TextView tvTestRecord;
    @BindView(R.id.lc_new)
    LineChartInViewPager lineChart;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.tv_blood_pressure)
    TextView tvBloodPressure;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_add)
    ColorTextView tvAdd;

    private DecimalFormat mFormat;
    private ScopeBean scope;
    private List<BloodPressureBean> bloodPressureList;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                lineChart.setVisibility(View.VISIBLE);
                llNoData.setVisibility(View.GONE);
                bloodPressureList = (List<BloodPressureBean>) msg.obj;
                initViews(bloodPressureList);
                if (bloodPressureList.size() >= 1) {
                    BloodPressureBean end = bloodPressureList.get(bloodPressureList.size() - 1);
                    setScope(end);
                }
                break;
            case GET_SCOPE:
                scope = (ScopeBean) msg.obj;
                SPUtils.putBean("target", scope);
                SpanUtils.with(tvTarget)
                        .appendImage(R.drawable.xinlijiankang)
                        .append("您的血压应该控制在:").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                        .append(scope.getBp()).setForegroundColor(ColorUtils.getColor(R.color.main_red))
                        .create();
                break;
            case NO_DATA:
                lineChart.setVisibility(View.GONE);
                llNoData.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setScope(BloodPressureBean scopeTemp) {
        String dateStr = scopeTemp.getIndextime();
        String xueyaStr = scopeTemp.getSystolic() + "/" + scopeTemp.getDiastole();
        tvBloodPressure.setText(xueyaStr);
        tvTime.setText(dateStr);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xueya;
    }

    @Override
    protected void init(View view) {
        getScope();
        getData();
    }

    /**
     * 获取控制目标
     */
    private void getScope() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_USERDATE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                scope = JSONObject.parseObject(value, ScopeBean.class);
                Message message = Message.obtain();
                message.what = GET_SCOPE;
                message.obj = scope;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取折线图数据
     *
     * @param
     */
    private void getData() {
        HashMap map = new HashMap<>();
        map.put("type", 1);
        map.put("starttime", "");
        map.put("endtime", "");
        XyUrl.okPost(XyUrl.GET_INDEX_BLOOD_INDEX, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bloodPressureList = JSONObject.parseArray(value, BloodPressureBean.class);
                Message msg = Message.obtain();
                msg.what = GET_DATA;
                msg.obj = bloodPressureList;
                sendHandlerMessage(msg);

            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = Message.obtain();
                msg.what = NO_DATA;
                sendHandlerMessage(msg);
            }
        });
    }

    private void initViews(List<BloodPressureBean> bloodPressureList) {
        mFormat = new DecimalFormat("#,###.##");//格式化数值（取整数部分）
        List<Entry> values1 = new ArrayList<>();
        List<Entry> values2 = new ArrayList<>();
        BloodPressureBean bloodPressure;
        for (int i = 0; i < bloodPressureList.size(); i++) {
            bloodPressure = bloodPressureList.get(i);
            String amount = bloodPressure.getSystolic() + "";
            if (amount != null) {
                float f = 0;
                try {
                    f = Float.parseFloat(amount);
                } catch (Exception e) {
                    e.printStackTrace();
                    f = 0;
                }
                Entry entry = new Entry(i + 1, f);
                values1.add(entry);
            }
        }

        for (int i = 0; i < bloodPressureList.size(); i++) {
            bloodPressure = bloodPressureList.get(i);
            String amount = bloodPressure.getDiastole() + "";
            if (amount != null) {
                float f = 0;
                try {
                    f = Float.parseFloat(amount);
                } catch (Exception e) {
                    e.printStackTrace();
                    f = 0;
                }
                Entry entry = new Entry(i + 1, f);
                values2.add(entry);
            }
        }
        int[] callDurationColors = {Color.parseColor("#63CAB0"), Color.parseColor("#D77369")};
        String thisYear = "";
        if (bloodPressureList.size() > 0) {
            thisYear = "舒张压";
        }
        String lastYear = "";
        if (bloodPressureList.size() > 0) {
            lastYear = "收缩压";
        }
        String[] labels = new String[]{lastYear, thisYear};
        updateLinehart(bloodPressureList, bloodPressureList, lineChart, callDurationColors, null, "", values1, values2, labels);
    }

    /**
     * 双平滑曲线传入数据，添加markview，添加实体类单位
     *
     * @param yoyList
     * @param realList
     * @param lineChart
     * @param colors
     * @param drawables
     * @param unit
     * @param values2
     * @param values1
     * @param labels
     */
    private void updateLinehart(final List<BloodPressureBean> yoyList, final List<BloodPressureBean> realList, LineChart lineChart, int[] colors, Drawable[] drawables,
                                final String unit, List<Entry> values2, List<Entry> values1, final String[] labels) {
        List<Entry>[] entries = new ArrayList[2];
        entries[0] = values1;
        entries[1] = values2;
        LineChartEntity lineChartEntity = new LineChartEntity(lineChart, entries, labels, colors, ColorUtils.getColor(R.color.gray_text), 12f);
        lineChartEntity.drawCircle(true);
        lineChart.setScaleMinima(1.0f, 1.0f);
        /**
         * 这里切换平滑曲线或者折现图
         */
        lineChartEntity.setLineMode(LineDataSet.Mode.LINEAR);
        lineChartEntity.initLegend(Legend.LegendForm.CIRCLE, 12f, ColorUtils.getColor(R.color.gray_text));
        lineChartEntity.updateLegendOrientation(Legend.LegendVerticalAlignment.TOP, Legend.LegendHorizontalAlignment.LEFT, Legend.LegendOrientation.HORIZONTAL);
        lineChartEntity.setAxisFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 1.0f) {
                            return mFormat.format(value);
                        }
                        String monthStr = mFormat.format(value);
                        if (monthStr.contains(".")) {
                            return "";
                        } else {
                            return monthStr;
                        }
                    }
                },
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return mFormat.format(value);
                    }
                });
        lineChartEntity.setDataValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return mFormat.format(value);
                    }
                });
        final NewMarkerView markerView = new NewMarkerView(getPageContext(), R.layout.custom_marker_view_layout, colors, 2);
        markerView.setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) (x);
                if (index < 0) {
                    return;
                }
                if (index > bloodPressureList.size()) {
                    bloodPressureList.size();
                    return;
                }
                String textTemp = "";
                bloodPressureList.size();
                textTemp += bloodPressureList.get(index - 1).getDatetime() + "  " + mFormat.format(Float.parseFloat(bloodPressureList.get(index - 1).getSystolic() + ""));
                textTemp += "/" + mFormat.format(Float.parseFloat(bloodPressureList.get(index - 1).getDiastole() + ""));
                markerView.getTvContent().setText(textTemp);
                setScope(bloodPressureList.get(index - 1));
            }
        });
        lineChartEntity.setMarkView(markerView);
        lineChart.getData().setDrawValues(true);
        lineChart.getData().setValueTextSize(9f);

        //隐藏x轴值
        lineChart.getXAxis().setDrawLabels(false);
        //隐藏左边Y轴值
        lineChart.getAxisLeft().setDrawLabels(false);
        //隐藏左边坐标轴横网格线
        lineChart.getAxisLeft().setDrawGridLines(false);
        //隐藏右边坐标轴横网格线
        lineChart.getAxisRight().setDrawGridLines(false);
        //隐藏X轴竖网格线
        lineChart.getXAxis().setDrawGridLines(false);

        Legend l = lineChart.getLegend();
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.BLOOD_PRESSURE_RECORD_ADD:
                getData();
                break;
        }
    }

    @OnClick({R.id.tv_test_record, R.id.tv_add})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_test_record:
                intent = new Intent(getActivity(), BloodPressureListActivity.class);
                intent.putExtra("key", 1);
                startActivity(intent);
                break;
            case R.id.tv_add:
                intent = new Intent(getActivity(), BloodPressureAddActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                break;
        }
    }
}
