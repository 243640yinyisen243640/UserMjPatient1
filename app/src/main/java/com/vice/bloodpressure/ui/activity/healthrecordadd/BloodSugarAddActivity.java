package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsp.RulerView;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.GvSugarAddAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.ResetTargetBean;
import com.vice.bloodpressure.bean.ScopeBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.popu.CenterPopup;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * 描述: 添加血糖
 * 作者: LYD
 * 创建日期: 2019/6/18 9:49
 */
public class BloodSugarAddActivity extends BaseHandlerActivity implements View.OnClickListener {
    private static final int GET_SCOPE = 10010;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.gv_time)
    GridView gvTime;
    @BindView(R.id.tv_check_time)
    TextView tvCheckTime;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.ruler_blood_sugar)
    RulerView rulerBloodSugar;
    @BindView(R.id.tv_left)
    ColorTextView tvLeft;
    @BindView(R.id.tv_center)
    ColorTextView tvCenter;
    @BindView(R.id.tv_right)
    ColorTextView tvRight;
    private GvSugarAddAdapter adapter;
    private int selectPosition;
    //popu开始
    private CenterPopup popup;
    private TextView tvTitle;
    private TextView tvDesc;
    //popu结束
    private ScopeBean targetBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBar();
        initPopu();
        getTarget();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_bloodsugar_add, contentLayout, false);
    }

    /**
     * 设置标题栏
     */
    private void setTitleBar() {
        setTitle("记录血糖");
        showTvSave();
        getTvSave().setOnClickListener(this);
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopu() {
        popup = new CenterPopup(getPageContext());
        tvTitle = popup.findViewById(R.id.tv_title);
        tvDesc = popup.findViewById(R.id.tv_desc);
        ColorTextView tvKnow = popup.findViewById(R.id.tv_know);
        tvKnow.setOnClickListener(this);
    }

    /**
     * 获取控制目标
     */
    private void getTarget() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_USERDATE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ScopeBean scope = JSONObject.parseObject(value, ScopeBean.class);
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


    private String getBean(List<Double> list) {
        String min = list.get(0) + "";
        String max = list.get(1) + "";
        return min + "-" + max;
    }

    private void setTvCheck(int type) {
        switch (type) {
            case 0:
                tvLeft.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.blood_sugar_low));
                tvCenter.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.background));
                tvRight.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.background));
                break;
            case 1:
                tvLeft.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.background));
                tvCenter.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.blood_sugar_normal));
                tvRight.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.background));
                break;
            case 2:
                tvLeft.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.background));
                tvCenter.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.background));
                tvRight.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.red_bright));
                break;
        }
    }

    /**
     * 保存判断
     */
    private void toSave() {
        String gllater = null;
        ResetTargetBean target = targetBean.getTarget();
        String bean0 = getBean(target.getEmpstomach());
        String bean1 = getBean(target.getAftbreakfast());
        String bean2 = getBean(target.getBeflunch());
        String bean3 = getBean(target.getAftlunch());
        String bean4 = getBean(target.getBefdinner());
        String bean5 = getBean(target.getAftdinner());
        String bean6 = getBean(target.getBefsleep());
        String bean7 = getBean(target.getInmorning());
        switch (selectPosition) {
            //餐前
            case 0:
                gllater = bean0;
                break;
            case 1:
                gllater = bean1;
                break;
            case 2:
                gllater = bean2;
                break;
            case 3:
                gllater = bean3;
                break;
            case 4:
                gllater = bean4;
                break;
            case 5:
                gllater = bean5;
                break;
            case 6:
                gllater = bean6;
                break;
            case 7:
                gllater = bean7;
                break;
        }


        String time = tvCheckTime.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            ToastUtils.showShort("请选择时间");
            return;
        }
        String sugarValue = tvResult.getText().toString().trim();
        if (TextUtils.isEmpty(sugarValue)) {
            ToastUtils.showShort("请输入血糖值");
            return;
        }
        if (gllater != null && gllater.contains("-")) {
            //判断血糖值
            String[] sugarArray = gllater.split("-");
            String sugarLow = sugarArray[0];
            String sugarHigh = sugarArray[1];
            double sugarLowDouble = TurnsUtils.getDouble(sugarLow, 0);
            double sugarHighDouble = TurnsUtils.getDouble(sugarHigh, 0);
            double contentDouble = TurnsUtils.getDouble(sugarValue, 0);

            if (contentDouble > sugarHighDouble) {
                popup.showPopupWindow();
                tvDesc.setText(String.format("您上传%s高于正常范围", contentDouble));
                tvTitle.setText("血糖高了");
            } else if (contentDouble < sugarLowDouble) {
                tvTitle.setText("血糖低了");
                tvDesc.setText(String.format("您上传%s低于正常范围", contentDouble));
                popup.showPopupWindow();
            } else {
                toDoSave();
            }
        }
    }

    /**
     * 保存
     */
    private void toDoSave() {
        String time = tvCheckTime.getText().toString().trim();
        String sugarValue = tvResult.getText().toString().trim();
        HashMap<String, Object> map = new HashMap<>();
        map.put("glucosevalue", sugarValue);
        map.put("category", selectPosition + 1);
        map.put("datetime", time);
        XyUrl.okPostSave(XyUrl.ADD_SUGAR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(R.string.save_ok);
                EventBusUtils.post(new EventMessage<>(ConstantParam.BLOOD_SUGAR_ADD));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 设置时间
     */
    private void setTimeAndSelection() {
        SimpleDateFormat allFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String hour = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), allFormat);
        tvCheckTime.setText(allTimeString);
        int time = Integer.parseInt(hour);
        if (time >= 5 && time < 8) {
            selectPosition = 0;
        } else if (time >= 8 && time < 10) {
            selectPosition = 1;
        } else if (time >= 10 && time < 12) {
            selectPosition = 2;
        } else if (time >= 12 && time < 15) {
            selectPosition = 3;
        } else if (time >= 15 && time < 18) {
            selectPosition = 4;
        } else if (time >= 18 && time < 21) {
            selectPosition = 5;
        } else if (time >= 21 && time <= 23) {
            selectPosition = 6;
        } else if (time >= 0 && time < 5) {
            selectPosition = 7;
        }
        setTvTargetAndTvResult();
    }

    /**
     * 设置GridView
     */
    private void setGv() {
        setTimeAndSelection();
        String[] stringArray = getResources().getStringArray(R.array.data_sugar_time);
        List<String> listString = Arrays.asList(stringArray);
        adapter = new GvSugarAddAdapter(getPageContext(), R.layout.item_sugar_add, listString, selectPosition);
        gvTime.setAdapter(adapter);
    }


    @OnClick(R.id.ll_select_time)
    public void onViewClicked() {
        PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvCheckTime.setText(content);
            }
        });
    }


    @OnItemClick(R.id.gv_time)
    void OnItemClick(int position) {
        adapter.setSelect(position);
        selectPosition = position;
        setTvTargetAndTvResult();
    }

    private void setTvTargetAndTvResult() {
        ResetTargetBean target = targetBean.getTarget();
        String bean0 = getBean(target.getEmpstomach());
        String bean1 = getBean(target.getAftbreakfast());
        String bean2 = getBean(target.getBeflunch());
        String bean3 = getBean(target.getAftlunch());
        String bean4 = getBean(target.getBefdinner());
        String bean5 = getBean(target.getAftdinner());
        String bean6 = getBean(target.getBefsleep());
        String bean7 = getBean(target.getInmorning());

        String gllater = null;
        switch (selectPosition) {
            //餐前
            case 0:
                gllater = bean0;
                break;
            case 1:
                gllater = bean1;
                break;
            case 2:
                gllater = bean2;
                break;
            case 3:
                gllater = bean3;
                break;
            case 4:
                gllater = bean4;
                break;
            case 5:
                gllater = bean5;
                break;
            case 6:
                gllater = bean6;
                break;
            case 7:
                gllater = bean7;
                break;
        }
        tvTarget.setText("控制目标:" + gllater);


        String sugarValue = tvResult.getText().toString().trim();
        if (gllater != null && gllater.contains("-")) {
            //判断血糖值
            String[] sugarArray = gllater.split("-");
            String sugarLow = sugarArray[0];
            String sugarHigh = sugarArray[1];
            double sugarLowDouble = TurnsUtils.getDouble(sugarLow, 0);
            double sugarHighDouble = TurnsUtils.getDouble(sugarHigh, 0);
            double contentDouble = TurnsUtils.getDouble(sugarValue, 0);

            if (contentDouble > sugarHighDouble) {
                tvResult.setTextColor(ColorUtils.getColor(R.color.red_bright));
                setTvCheck(2);
            } else if (contentDouble < sugarLowDouble) {
                tvResult.setTextColor(ColorUtils.getColor(R.color.blood_sugar_low));
                setTvCheck(0);
            } else {
                tvResult.setTextColor(ColorUtils.getColor(R.color.blood_sugar_normal));
                setTvCheck(1);
            }
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SCOPE:
                targetBean = (ScopeBean) msg.obj;
                String bloodtarget = targetBean.getBloodtarget();
                //tvTarget.setText("控制目标:" + bloodtarget);
                initRuleListener();
                setGv();
                break;
        }
    }

    private void initRuleListener() {
        rulerBloodSugar.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvResult.setText(result);
                setTvTargetAndTvResult();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more:
                toSave();
                break;
            case R.id.tv_know:
                popup.dismiss();
                toDoSave();
                break;
        }
    }
}
