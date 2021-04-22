package com.vice.bloodpressure.ui.activity.sport;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsp.RulerView;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.libsteps.StepUtil;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.TurnsUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  手动添加
 * 作者: LYD
 * 创建日期: 2020/10/14 10:07
 */
public class SportTypeManualAddActivity extends BaseHandlerActivity {
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ruler_time)
    RulerView rulerTime;
    @BindView(R.id.rl_sure)
    RelativeLayout rlSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("手动记录");
        initRuler();
        double weight = getIntent().getExtras().getDouble("weight");
        double sportCoefficient = getIntent().getExtras().getDouble("sportCoefficient");
        int reduceKcal = (int) (weight * sportCoefficient * 30);
        tvDesc.setText("完成" + 30 + "分钟" + "\u3000\u3000" + "消耗" + reduceKcal + "千卡");
    }

    private void initRuler() {
        rulerTime.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {
                //体重*分钟*系数
                double weight = getIntent().getExtras().getDouble("weight");
                double sportCoefficient = getIntent().getExtras().getDouble("sportCoefficient");
                String minuteStr = floatStringToIntString(result);
                int minute = TurnsUtils.getInt(minuteStr, 0);
                int reduceKcal = (int) (weight * sportCoefficient * minute);
                tvDesc.setText("完成" + minuteStr + "分钟" + "\u3000\u3000" + "消耗" + reduceKcal + "千卡");
            }

            @Override
            public void onScrollResult(String result) {
                tvTime.setText(floatStringToIntString(result));
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.fragment_sport_type_right, contentLayout, false);
        return view;
    }

    @OnClick(R.id.rl_sure)
    public void onViewClicked() {
        toDoSubmit();
    }

    /**
     * 提交数据
     */
    private void toDoSubmit() {
        int sportType = getIntent().getExtras().getInt("sportType");
        String minutes = tvTime.getText().toString().trim();
        int intMinutes = TurnsUtils.getInt(minutes, 0);
        int todayStep = StepUtil.getTodayStep(getPageContext());
        HashMap map = new HashMap<>();
        map.put("sportType", sportType);
        map.put("seconds", intMinutes * 60);
        map.put("steps", todayStep);
        XyUrl.okPostSave(XyUrl.INDEX_ADD_SPORT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                ActivityUtils.finishToActivity(MainActivity.class, false);
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_SUBMIT));
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }


    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }
}