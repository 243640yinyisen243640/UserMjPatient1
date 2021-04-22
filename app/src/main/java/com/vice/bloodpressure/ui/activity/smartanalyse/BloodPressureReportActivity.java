package com.vice.bloodpressure.ui.activity.smartanalyse;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.BPReportBean;
import com.vice.bloodpressure.bean.BloodPressureBean;
import com.vice.bloodpressure.bean.LineChartEntity;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.LineChartInViewPager;
import com.vice.bloodpressure.view.NewMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 描述: 血压分析报告(区分首页和个人中心)
 * type:首页2 个人中心3
 * 作者: LYD
 * 创建日期: 2019/4/19 9:53
 */
public class BloodPressureReportActivity extends BaseHandlerActivity {
    private final int GET_REPORT = 1001;
    private final int GET_BLOOD_PRESSURE = 1002;
    private final int NO_DATA = 1003;
    private View layout;
    private LineChartInViewPager lineChart;
    private DecimalFormat mFormat;
    private List<BloodPressureBean> bloodPressureList;
    private TextView tvCount;//测量总次数
    private TextView tvHigh;//偏高次数
    private TextView tvLow;//偏低次数
    private BPReportBean bpReport;
    private TextView tvMax;//血压最高值
    private TextView tvMix;//血压最低值
    private TextView tvFirst;
    private TextView tvDiastolicLeft, tvDiastolicRight;
    private TextView tvSystolicLeft, tvSystolicRight;
    private TextView tvPulsePressure;
    private TextView tvDiagnosticDescription;
    private TextView tvAdvice;


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_BLOOD_PRESSURE:
                bloodPressureList = (List<BloodPressureBean>) msg.obj;
                showLine();
                break;
            case GET_REPORT:
                bpReport = (BPReportBean) msg.obj;
                if (!bpReport.getCount().isEmpty() && !"0".equals(bpReport.getCount())) {
                    tvCount.setText(bpReport.getCount());
                    tvHigh.setText(bpReport.getHigh());
                    tvLow.setText(bpReport.getLow());
                    tvMax.setText(bpReport.getMaxsbp());
                    tvMix.setText(bpReport.getMindbp());
                }
                String first = "您近1月共测量血压" + bpReport.getCount() + "次，平均每日" + bpReport.getAvg() +
                        "次。测量时间主要集中在" + bpReport.getSurveydate() + "。";
                String second1 = "舒张压" + bpReport.getDbpcount() + "次，平均值：";
                String second2 = bpReport.getDbpavg() + "mmHg";
                String second3 = "收缩压" + bpReport.getSbpcount() + "次，平均值：";
                String second4 = bpReport.getSbpavg() + "mmHg";
                String second5 = bpReport.getDiff() + "mmHg";
                tvFirst.setText(first);
                tvDiastolicLeft.setText(second1);
                tvDiastolicRight.setText(second2);
                tvSystolicLeft.setText(second3);
                tvSystolicRight.setText(second4);
                tvPulsePressure.setText(second5);
                String third = "1.您本段时间血压监测情况：正常次数" + bpReport.getNormal() +
                        "次；异常总共" + bpReport.getExcep() + "次；" + "\n2.血压偏高" + bpReport.getHigh() + "次";
                if (!"0".equals(bpReport.getHigh())) {
                    third += "，血压偏高主要集中在" + bpReport.getHighdate() + "；";
                } else {
                    third += ";";
                }
                third += "\n3.血压偏低" + bpReport.getLow() + "次";
                if (!"0".equals(bpReport.getLow())) {
                    third += "，血压偏低主要集中在" + bpReport.getLowdate() + "；";
                } else {
                    third += ";";
                }
                //您的血压控制率为18%，评价为：合格
                third += "\n4.您的血压控制率为" + bpReport.getFactor() + "%，评价为：" + bpReport.getRank();
                tvDiagnosticDescription.setText(third);
                String rankcontent = bpReport.getRankcontent();
                String highcontent = bpReport.getHighcontent();
                String content = bpReport.getContent();
                String count = bpReport.getCount();
                //判断
                int countInt = TurnsUtils.getInt(count, 0);
                if (countInt >= 2) {
                    String analysis = "1、您的血压控制水平" + rankcontent + "。\n2、" + highcontent + "。";
                    tvAdvice.setText(analysis);
                } else {
                    StringBuilder analysis = new StringBuilder();
                    analysis.append("1、您的血压控制水平" + rankcontent + "");
                    if (TextUtils.isEmpty(highcontent)) {
                        if (!TextUtils.isEmpty(content)) {
                            analysis.append("\n2、" + content + ")");
                        }
                    } else {
                        analysis.append("\n2、" + highcontent + ")");
                        if (!TextUtils.isEmpty(content)) {
                            analysis.append("\n3、" + content + ")");
                        }
                    }
                    tvAdvice.setText(analysis);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血压分析报告");
        initViews();
        getData();
        getReport();
    }

    /**
     * 获取血压报告
     */
    private void getReport() {
        HashMap map = new HashMap<>();
        map.put("starttime", getIntent().getStringExtra("time"));
        XyUrl.okPost(XyUrl.GET_INDEX_BLOOD_REPORTBP, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bpReport = JSONObject.parseObject(value, BPReportBean.class);
                Message msg = Message.obtain();
                msg.what = GET_REPORT;
                msg.obj = bpReport;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    /**
     * 获取血压数据
     */
    private void getData() {
        HashMap map = new HashMap<>();
        map.put("type", getIntent().getStringExtra("type"));
        map.put("starttime", getIntent().getStringExtra("time"));
        map.put("endtime", "");
        XyUrl.okPost(XyUrl.GET_INDEX_BLOOD_INDEX, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bloodPressureList = JSONObject.parseArray(value, BloodPressureBean.class);
                Message msg = Message.obtain();
                msg.what = GET_BLOOD_PRESSURE;
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

    private void initViews() {
        lineChart = findViewById(R.id.lc_new);
        LinearLayout llChartMain = findViewById(R.id.ll_chart_main);
        tvCount = layout.findViewById(R.id.tv_report_one);
        tvHigh = layout.findViewById(R.id.tv_report_two);
        tvLow = layout.findViewById(R.id.tv_report_three);
        tvMax = layout.findViewById(R.id.tv_up_num);
        tvMix = layout.findViewById(R.id.tv_up_down);
        tvAdvice = layout.findViewById(R.id.tv_advice);
        LinearLayout llAnalysisContent = layout.findViewById(R.id.ll_analysis_content);
        LinearLayout llNumContent = layout.findViewById(R.id.ll_num_content);
        LinearLayout llAdviceContent = layout.findViewById(R.id.ll_advice_content);
        tvFirst = layout.findViewById(R.id.tv_first);
        tvDiastolicLeft = layout.findViewById(R.id.tv_diastolic_left);
        tvDiastolicRight = layout.findViewById(R.id.tv_diastolic_right);
        tvSystolicLeft = layout.findViewById(R.id.tv_systolic_left);
        tvSystolicRight = layout.findViewById(R.id.tv_systolic_right);
        tvPulsePressure = layout.findViewById(R.id.tv_pulse_pressure);
        tvDiagnosticDescription = layout.findViewById(R.id.tv_diagnostic_description);
    }

    @Override
    protected View addContentLayout() {
        layout = getLayoutInflater().inflate(R.layout.activity_blood_report, contentLayout, false);
        return layout;
    }

    private void showLine() {
        mFormat = new DecimalFormat("#,###.##");//格式化数值（取整数部分）
        List<Entry> values1 = new ArrayList<>();
        List<Entry> values2 = new ArrayList<>();
        BloodPressureBean bloodPressure;
        for (int i = 0; i < bloodPressureList.size(); i++) {
            bloodPressure = bloodPressureList.get(i);
            String amount = bloodPressure.getSystolic() + "";
            float f;
            try {
                f = Float.parseFloat(amount);
            } catch (Exception e) {
                e.printStackTrace();
                f = 0;
            }
            Entry entry = new Entry(i + 1, f);
            values1.add(entry);
        }
        for (int i = 0; i < bloodPressureList.size(); i++) {
            bloodPressure = bloodPressureList.get(i);
            String amount = bloodPressure.getDiastole() + "";
            float f;
            try {
                f = Float.parseFloat(amount);
            } catch (Exception e) {
                e.printStackTrace();
                f = 0;
            }
            Entry entry = new Entry(i + 1, f);
            values2.add(entry);
        }
        int[] callDurationColors = {Color.parseColor("#63C9AF"), Color.parseColor("#D77369")};
        String[] labels = new String[]{"舒张压", "收缩压"};
        updateLineChart(lineChart, callDurationColors, values1, values2, labels);
    }

    /**
     * 双平滑曲线传入数据，添加markview，添加实体类单位
     *
     * @param lineChart
     * @param colors
     * @param values2
     * @param values1
     * @param labels
     */
    private void updateLineChart(LineChart lineChart, int[] colors, List<Entry> values2, List<Entry> values1, final String[] labels) {
        List<Entry>[] entries = new ArrayList[2];
        entries[0] = values1;
        entries[1] = values2;
        LineChartEntity lineChartEntity = new LineChartEntity(lineChart, entries, labels, colors, ColorUtils.getColor(R.color.gray_text), 12f);
        lineChartEntity.drawCircle(true);
        lineChart.setScaleMinima(1.0f, 1.0f);
        lineChartEntity.setLineMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineChartEntity.initLegend(Legend.LegendForm.CIRCLE, 12f, ColorUtils.getColor(R.color.gray_text));
        lineChartEntity.updateLegendOrientation(Legend.LegendVerticalAlignment.TOP, Legend.LegendHorizontalAlignment.RIGHT, Legend.LegendOrientation.HORIZONTAL);
        lineChartEntity.setAxisFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "";
                    }
                },
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return mFormat.format(value);
                    }
                }
        );

        lineChartEntity.setDataValueFormatter(new ValueFormatter() {
                                                  @Override
                                                  public String getFormattedValue(float value) {
                                                      return mFormat.format(value);
                                                  }
                                              }
        );

        final NewMarkerView markerView = new NewMarkerView(this, R.layout.custom_marker_view_layout, colors, 2);
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
            }
        });
        lineChartEntity.setMarkView(markerView);
        lineChart.getData().setDrawValues(false);
        //隐藏图例
        lineChart.getLegend().setEnabled(false);
        //lineChart.getXAxis().setEnabled(false);
    }


}
