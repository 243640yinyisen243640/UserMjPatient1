package com.vice.bloodpressure.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhon.appupdate.utils.DensityUtil;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.edittext.TextWatcherForBloodSugarAdd;

import java.util.Calendar;
import java.util.HashMap;

public class BloodSugarDialog extends Dialog {
    private EditText etBlood;
    private TextView tvTime;
    private String Time, sugarBlood;
    private Context context;
    private int position;
    private String day;


    public BloodSugarDialog(Context context, int themeResId, int position, String day) {
        super(context, themeResId);
        this.context = context;
        this.position = position;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        this.day = "" + year + "-" + day;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bloodsuger, null);
        initViews(view);
        setContentView(view);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = DensityUtil.dip2px(context, 350);
        lp.width = DensityUtil.dip2px(context, 300);
        win.setAttributes(lp);
    }


    private void initViews(View view) {
        tvTime = view.findViewById(R.id.timeTextView);
        Button btnSave = view.findViewById(R.id.dialog_saveButton);
        etBlood = view.findViewById(R.id.bloodEdit);
        LinearLayout llTime = view.findViewById(R.id.timeLin);
        etBlood.addTextChangedListener(new TextWatcherForBloodSugarAdd(etBlood).setDigits(40));
        llTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerUtils.showTimeHourAndMin(context, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTime.setText(content);
                        if (!TextUtils.isEmpty(etBlood.getText().toString().trim())) {
                            btnSave.setBackgroundColor(ColorUtils.getColor(R.color.main_home));
                        }
                    }
                });
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sugarBlood = etBlood.getText().toString();
                Time = tvTime.getText().toString();
                if (TextUtils.isEmpty(sugarBlood)) {
                    ToastUtils.showShort("请选输入血糖值");
                    return;
                }
                if (TextUtils.isEmpty(Time)) {
                    ToastUtils.showShort("请选择时间");
                    return;
                }
                saveData();
            }
        });
    }


    /**
     * 时间点 ‘凌晨’,’8’,
     * ’睡前’,’7’,
     * ’晚餐后2小时’,’6’,
     * ’晚餐前’,’5’,’
     * 午餐后2小时’,’4’,
     * ’午餐前’,’3’,
     * ’早餐后2小时’,’2’,
     * ’空腹’,’1’
     */
    private void saveData() {
        HashMap map = new HashMap<>();
        map.put("glucosevalue", sugarBlood);
        map.put("category", position);
        map.put("datetime", day + " " + Time);
        XyUrl.okPostSave(XyUrl.ADD_SUGAR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(R.string.save_ok);
                EventBusUtils.post(new EventMessage(ConstantParam.BLOOD_SUGAR_ADD));
                dismiss();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }
}
