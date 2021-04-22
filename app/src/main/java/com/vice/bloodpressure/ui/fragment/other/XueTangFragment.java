package com.vice.bloodpressure.ui.fragment.other;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.BloodSugarBean;
import com.vice.bloodpressure.bean.LineChartEntity;
import com.vice.bloodpressure.bean.ScopeBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodSugarAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BloodSugarListActivity;
import com.vice.bloodpressure.view.LineChartInViewPager;
import com.vice.bloodpressure.view.NewMarkerView;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class XueTangFragment extends BaseEventBusFragment {
    private static final int GET_DATA = 0x002998;
    private static final int NO_DATA = 0x001998;
    private static final int GET_SCOPE = 0x000998;
    @BindView(R.id.tv_test_record)
    TextView tvTestRecord;
    @BindView(R.id.lc_new)
    LineChartInViewPager lineChart;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.tv_blood_sugar)
    TextView tvBloodSugar;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_add)
    ColorTextView tvAdd;

    private DecimalFormat mFormat;
    private ScopeBean scope;
    private List<BloodSugarBean> bloodSugarList;


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                lineChart.setVisibility(View.VISIBLE);
                llNoData.setVisibility(View.GONE);
                bloodSugarList = (List<BloodSugarBean>) msg.obj;
                if (bloodSugarList.size() >= 1) {
                    BloodSugarBean end = bloodSugarList.get(bloodSugarList.size() - 1);
                    setScope(end);
                }
                initViews(bloodSugarList);
                break;
            case GET_SCOPE:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.getDefault());
                String hour = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
                String timeDesc = "";
                int time = Integer.parseInt(hour);
                if (time >= 5 && time < 8) {
                    timeDesc = "空腹";
                } else if (time >= 8 && time < 10) {
                    timeDesc = "早餐后";
                } else if (time >= 10 && time < 12) {
                    timeDesc = "午餐前";
                } else if (time >= 12 && time < 15) {
                    timeDesc = "午餐后";
                } else if (time >= 15 && time < 18) {
                    timeDesc = "晚餐前";
                } else if (time >= 18 && time < 21) {
                    timeDesc = "晚餐后";
                } else if (time >= 21 && time <= 23) {
                    timeDesc = "睡前";
                } else if (time >= 0 && time < 5) {
                    timeDesc = "凌晨";
                }
                scope = (ScopeBean) msg.obj;
                SpanUtils.with(tvTarget)
                        .appendImage(R.drawable.blood_sugar)
                        .append("您的").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                        .append(timeDesc).setForegroundColor(ColorUtils.getColor(R.color.color_666))
                        .append("血糖应该控制在:").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                        .append(scope.getBloodtarget()).setForegroundColor(ColorUtils.getColor(R.color.main_red))
                        .create();
                break;
            case NO_DATA:
                lineChart.setVisibility(View.GONE);
                llNoData.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void setScope(BloodSugarBean scopeTemp) {
        String gl = "" + scopeTemp.getGlucosevalue();
        String dateStr = scopeTemp.getIndextime();
        tvBloodSugar.setText(gl);
        tvTime.setText(dateStr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xuetang;
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
     * 血糖折线图
     *
     * @param
     */
    private void getData() {
        HashMap map = new HashMap<>();
        map.put("type", 1);
        map.put("starttime", "");
        map.put("endtime", "");
        XyUrl.okPost(XyUrl.GET_INDEX_BUSGAR_INDEX, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bloodSugarList = JSONObject.parseArray(value, BloodSugarBean.class);
                Message msg = Message.obtain();
                msg.what = GET_DATA;
                msg.obj = bloodSugarList;
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


    private void initViews(List<BloodSugarBean> bloodSugarList) {
        mFormat = new DecimalFormat("#,###.##");//格式化数值（取整数部分）
        List<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < bloodSugarList.size(); i++) {
            BloodSugarBean bloodSugar = bloodSugarList.get(i);
            String amount = bloodSugar.getGlucosevalue() + "";
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
        Drawable[] drawables = {
                ContextCompat.getDrawable(getPageContext(), R.drawable.chart_thisyear_blue),
                ContextCompat.getDrawable(getPageContext(), R.drawable.chart_fragment_xuetang)
        };
        int[] callDurationColors = {ColorUtils.getColor(R.color.weight_chart_line), ColorUtils.getColor(R.color.weight_chart_line)};
        String lastYear = "";
        if (bloodSugarList.size() > 0) {
            lastYear = "血糖";
        }
        String[] labels = new String[]{lastYear};
        updateLineChart(bloodSugarList, lineChart, callDurationColors, drawables, "", values1, labels);
    }

    /*  *
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
     * @param labels*/
    private void updateLineChart(final List<BloodSugarBean> yoyList, LineChart lineChart, int[] colors, Drawable[] drawables,
                                 final String unit, List<Entry> values1, final String[] labels) {
        List<Entry>[] entries = new ArrayList[1];
        entries[0] = values1;
        LineChartEntity lineChartEntity = new LineChartEntity(lineChart, entries, labels, colors, ColorUtils.getColor(R.color.gray_text), 12f);
        lineChartEntity.drawCircle(true);
        lineChart.setScaleMinima(1.0f, 1.0f);
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
                        return mFormat.format(value) + unit;
                    }
                });

        lineChartEntity.setDataValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return mFormat.format(value) + unit;
                    }
                });

        final NewMarkerView markerView = new NewMarkerView(getPageContext(), R.layout.custom_marker_view_layout, colors, 1);
        markerView.setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) (x);
                if (index < 0) {
                    return;
                }
                if (index > bloodSugarList.size() && index > bloodSugarList.size()) {
                    return;
                }
                String textTemp = "";
                bloodSugarList.size();
                textTemp += bloodSugarList.get(index - 1).getDatetime() + "  " + mFormat.format(Float.parseFloat(bloodSugarList.get(index - 1).getGlucosevalue() + "")) + unit;
                markerView.getTvContent().setText(textTemp);
                setScope(bloodSugarList.get(index - 1));
            }
        });
        lineChartEntity.setMarkView(markerView);
        lineChart.getLineData().setDrawValues(true);
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
            case ConstantParam.BLOOD_SUGAR_ADD:
                getData();
                break;
        }
    }

    @OnClick({R.id.tv_test_record, R.id.tv_add})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_test_record:
                intent = new Intent(getActivity(), BloodSugarListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_add:
                intent = new Intent(getActivity(), BloodSugarAddActivity.class);
                intent.putExtra("position", 0);
                startActivity(intent);
                break;
        }
    }
}
