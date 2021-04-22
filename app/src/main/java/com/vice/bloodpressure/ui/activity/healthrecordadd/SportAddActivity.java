package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lsp.RulerView;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordlist.SportTypeActivity;
import com.vice.bloodpressure.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 添加运动
 * 作者: LYD
 * 创建日期: 2020/4/21 14:30
 */
public class SportAddActivity extends BaseHandlerActivity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.img_sport_type)
    ImageView imgSportType;
    @BindView(R.id.tv_sport_type_content)
    TextView tvSportTypeContent;
    @BindView(R.id.rl_sport_type)
    RelativeLayout rlSportType;
    @BindView(R.id.tv_sport_time_length)
    TextView tvSportTimeLength;
    @BindView(R.id.ruler_blood_sugar)
    RulerView rulerBloodSugar;
    @BindView(R.id.tv_sport_time)
    TextView tvSportTime;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    private int cardNumber = -1;


    private String[] sportTypes = Utils.getApp().getResources().getStringArray(R.array.sport_type);

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("记录运动");
        initTvSave();
        setTime();
        initRuler();
    }

    private void initRuler() {
        rulerBloodSugar.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvSportTimeLength.setText(floatStringToIntString(result));
            }
        });
    }

    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }

    private void initTvSave() {
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardNumber == -1) {
                    ToastUtils.showShort("请选择运动类型");
                    return;
                }
                String duration = tvSportTimeLength.getText().toString().trim();
                if (TextUtils.isEmpty(duration)) {
                    ToastUtils.showShort("请输入运动时长");
                    return;
                }
                String time = tvSportTime.getText().toString().trim();
                if (TextUtils.isEmpty(time)) {
                    ToastUtils.showShort("请选择时间");
                    return;
                }
                saveData(duration, time);
            }
        });
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_sport_add, contentLayout, false);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void saveData(String duration, String time) {
        Map<String, Object> map = new HashMap<>();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String type = sportTypes[cardNumber];
        map.put("type", type);
        map.put("duration", duration);
        map.put("datetime", time);
        XyUrl.okPostSave(XyUrl.ADD_SPORT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                EventBusUtils.post(new EventMessage<>(ConstantParam.SPORT_RECORD_ADD));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (data != null) {
                TypedArray sportImages = Utils.getApp().getResources().obtainTypedArray(R.array.sport_img);
                String type = data.getStringExtra("type");
                tvSportTypeContent.setText(type);
                cardNumber = data.getIntExtra("position", -1);
                imgSportType.setImageResource(sportImages.getResourceId(cardNumber, 0));
            }
        }
    }


    private void setTime() {
        SimpleDateFormat Allformat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), Allformat);
        tvSportTime.setText(allTimeString);
    }

    @OnClick({R.id.rl_sport_type, R.id.rl_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_sport_type:
                startActivityForResult(new Intent(getPageContext(), SportTypeActivity.class), 0);
                break;
            case R.id.rl_time:
                PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvSportTime.setText(content);
                    }
                });
                break;
        }
    }
}
