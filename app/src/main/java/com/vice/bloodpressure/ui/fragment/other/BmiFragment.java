package com.vice.bloodpressure.ui.fragment.other;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.BmiBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BmiAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BmiListActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  BmiFragment
 * 作者: LYD
 * 创建日期: 2019/8/19 15:30
 * <p>
 * BMI＜18.5 偏低
 * 18.5≤BMI≤23.9 正常
 * 24≤BMI≤27.9 超重
 * BMI≥28 肥胖
 */
public class BmiFragment extends BaseEventBusFragment {
    private static final int GET_BMI_LIST = 10010;
    private static final int GET_BMI_NO_DATA = 10011;
    @BindView(R.id.img_bmi)
    ImageView imgBmi;
    @BindView(R.id.tv_first_bmi_record)
    TextView tvFirstBmiRecord;
    @BindView(R.id.tv_second_bmi_record)
    TextView tvSecondBmiRecord;
    @BindView(R.id.tv_third_bmi_record)
    TextView tvThirdBmiRecord;
    @BindView(R.id.ll_bmi_have_data)
    LinearLayout llBmiHaveData;
    @BindView(R.id.ll_bmi_no_data)
    LinearLayout llBmiNoData;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;
    @BindView(R.id.tv_bmi_target)
    TextView tvBmiTarget;
    @BindView(R.id.tv_time)
    TextView tvTime;


    private List<BmiBean> bmiList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bmi;
    }

    @Override
    protected void init(View view) {
        setBmiTarget();
        getBmiData();
    }


    /**
     * 设置Bmi控制目标
     */
    private void setBmiTarget() {
        SpanUtils.with(tvBmiTarget)
                .appendImage(R.drawable.home_bmi_body)
                .append("您的BMI应该控制在").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                .append("18.5-23.9").setForegroundColor(ColorUtils.getColor(R.color.red_bright))
                .create();
    }


    /**
     * 获取Bmi数据
     */
    private void getBmiData() {
        HashMap map = new HashMap<>();
        map.put("type", 1);
        map.put("starttime", "");
        map.put("endtime", "");
        XyUrl.okPost(XyUrl.GET_INDEX_BMI_INDEX, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<BmiBean> bmiList = JSONObject.parseArray(value, BmiBean.class);
                Message msg = Message.obtain();
                msg.what = GET_BMI_LIST;
                msg.obj = bmiList;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = Message.obtain();
                msg.what = GET_BMI_NO_DATA;
                sendHandlerMessage(msg);
            }
        });
    }


    @OnClick({R.id.tv_test_record, R.id.tv_first_bmi_record, R.id.tv_second_bmi_record, R.id.tv_third_bmi_record, R.id.tv_add_bmi})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_test_record:
                intent = new Intent(getActivity(), BmiListActivity.class);
                intent.putExtra("key", 5);
                startActivity(intent);
                break;
            case R.id.tv_first_bmi_record:
                setBmi(0);
                break;
            case R.id.tv_second_bmi_record:
                setBmi(1);
                break;
            case R.id.tv_third_bmi_record:
                setBmi(2);
                break;
            case R.id.tv_add_bmi:
                intent = new Intent(getActivity(), BmiAddActivity.class);
                intent.putExtra("position", 5);
                startActivity(intent);
                break;
        }
    }


    /**
     * 设置Bmi
     *
     * @param i
     */
    private void setBmi(int i) {
        switch (i) {
            case 0:
                tvFirstBmiRecord.setTextColor(ColorUtils.getColor(R.color.main_home));
                tvSecondBmiRecord.setTextColor(ColorUtils.getColor(R.color.black_text));
                tvThirdBmiRecord.setTextColor(ColorUtils.getColor(R.color.black_text));
                break;
            case 1:
                tvFirstBmiRecord.setTextColor(ColorUtils.getColor(R.color.black_text));
                tvSecondBmiRecord.setTextColor(ColorUtils.getColor(R.color.main_home));
                tvThirdBmiRecord.setTextColor(ColorUtils.getColor(R.color.black_text));
                break;
            case 2:
                tvFirstBmiRecord.setTextColor(ColorUtils.getColor(R.color.black_text));
                tvSecondBmiRecord.setTextColor(ColorUtils.getColor(R.color.black_text));
                tvThirdBmiRecord.setTextColor(ColorUtils.getColor(R.color.main_home));
                break;
        }
        if (bmiList != null && bmiList.size() > i) {
            double bmi = bmiList.get(i).getBmi();
            String bmiTime = bmiList.get(i).getIndextime();
            tvBmi.setText(bmi + "");
            tvTime.setText(bmiTime);
            setBmiImgResource(bmi);
        }
    }

    /**
     * 设置Bmi图片
     *
     * @param bmi
     */
    private void setBmiImgResource(double bmi) {
        if (bmi >= 28) {
            imgBmi.setImageResource(R.drawable.home_blood_pressure_more_high);
        } else if (bmi >= 24) {
            imgBmi.setImageResource(R.drawable.home_blood_pressure_high);
        } else if (bmi >= 18.5) {
            imgBmi.setImageResource(R.drawable.home_blood_pressure_common);
        } else {
            imgBmi.setImageResource(R.drawable.home_blood_pressure_low);
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_BMI_LIST:
                llBmiHaveData.setVisibility(View.VISIBLE);
                llBmiNoData.setVisibility(View.GONE);
                bmiList = (List<BmiBean>) msg.obj;
                if (bmiList != null && bmiList.size() > 0) {
                    double bmi = bmiList.get(0).getBmi();
                    String bmiTime = bmiList.get(0).getIndextime();
                    tvBmi.setText(bmi + "");
                    tvTime.setText(bmiTime);
                    setBmiImgResource(bmi);
                    if (bmiList.size() == 1) {
                        tvFirstBmiRecord.setVisibility(View.VISIBLE);
                        tvSecondBmiRecord.setVisibility(View.INVISIBLE);
                        tvThirdBmiRecord.setVisibility(View.INVISIBLE);
                    } else if (bmiList.size() == 2) {
                        tvFirstBmiRecord.setVisibility(View.VISIBLE);
                        tvSecondBmiRecord.setVisibility(View.VISIBLE);
                        tvThirdBmiRecord.setVisibility(View.INVISIBLE);
                    } else {
                        tvFirstBmiRecord.setVisibility(View.VISIBLE);
                        tvSecondBmiRecord.setVisibility(View.VISIBLE);
                        tvThirdBmiRecord.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case GET_BMI_NO_DATA:
                llBmiHaveData.setVisibility(View.GONE);
                llBmiNoData.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.BMI_RECORD_ADD:
                getBmiData();
                break;
        }
    }
}
