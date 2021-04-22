package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MedicineAddPopupAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MedicineAddPopupListBean;
import com.vice.bloodpressure.bean.PersonalRecordMedicineHistoryBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.view.SearchEditText;
import com.vice.bloodpressure.view.popu.MedicineAddPopup;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:type:0 记录用药 1:添加用药
 * 作者: LYD
 * 创建日期: 2019/6/19 17:06
 */
public class PharmacyAddActivity extends BaseHandlerActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "PharmacyAddActivity";
    private static final String[] dosageNameList = {"请选择剂量单位", "mg", "g", "iu", "ml", "ug"};
    private static final int GET_SEARCH_DATA = 10010;
    private static final int GET_SEARCH_NO_DATA = 10011;
    @BindView(R.id.rl_medicine_name)
    RelativeLayout rlMedicineName;
    @BindView(R.id.tv_pharmacy_name)
    TextView tvPharmacyName;
    @BindView(R.id.et_pharmacy_name)
    SearchEditText etPharmacyName;
    @BindView(R.id.tv_pharmacy_count)
    TextView tvPharmacyCount;
    @BindView(R.id.et_pharmacy_count)
    EditText etPharmacyCount;
    @BindView(R.id.tv_pharmacy_unit)
    TextView tvPharmacyUnit;
    @BindView(R.id.tv_pharmacy_dosage)
    TextView tvPharmacyDosage;
    @BindView(R.id.et_pharmacy_dosage)
    EditText etPharmacyDosage;
    @BindView(R.id.spinner_pharmacy_dosage)
    Spinner spinnerPharmacyDosage;

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.img_start_time)
    ImageView imgStartTime;
    @BindView(R.id.rl_start_time)
    LinearLayout rlStartTime;

    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.img_end_time)
    ImageView imgEndTime;
    @BindView(R.id.rl_end_time)
    LinearLayout rlEndTime;

    @BindView(R.id.ll_spinner)
    LinearLayout llSpinner;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_start_end_time)
    LinearLayout llStartEndTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private int cardNumber;
    private int labellid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");
        PersonalRecordMedicineHistoryBean detailBean = (PersonalRecordMedicineHistoryBean) getIntent().getSerializableExtra("detailBean");
        if (detailBean != null) {
            String name = detailBean.getDrugname();
            etPharmacyName.setText(name);
            etPharmacyName.setEnabled(false);
            String center = detailBean.getTimes();
            etPharmacyCount.setText(center);
            etPharmacyCount.setEnabled(false);
            String content = detailBean.getDosage();
            etPharmacyDosage.setText(content);
            etPharmacyDosage.setEnabled(false);
            String startTime = detailBean.getStarttime();
            String endTime = detailBean.getEndtime();
            tvStartTime.setText(startTime);
            tvEndTime.setText(endTime);
            hideTvSave();
            rlStartTime.setEnabled(false);
            rlEndTime.setEnabled(false);
            llSpinner.setVisibility(View.GONE);
            imgStartTime.setVisibility(View.GONE);
            imgEndTime.setVisibility(View.GONE);
        } else {
            showTvSave();
            TextView tvSave = getTvSave();
            tvSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            rlStartTime.setEnabled(true);
            rlEndTime.setEnabled(true);
            llSpinner.setVisibility(View.VISIBLE);
            imgStartTime.setVisibility(View.VISIBLE);
            imgEndTime.setVisibility(View.VISIBLE);
        }
        if ("0".equals(type)) {
            setTitle("记录用药");
            llStartEndTime.setVisibility(View.GONE);
            llTime.setVisibility(View.VISIBLE);
        } else {
            setTitle("添加用药");
            llStartEndTime.setVisibility(View.VISIBLE);
            llTime.setVisibility(View.GONE);
        }
        initView();
        initTime();
        setEt();
    }

    private void setEt() {
        etPharmacyName.setOnTextChangedListener(new SearchEditText.OnTextChangedListener() {
            @Override
            public void onTextChanged(String keyWord) {
                if (!TextUtils.isEmpty(keyWord)) {
                    toSearch(keyWord);
                }
            }
        });
    }

    private void toSearch(String keyWord) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("drugname", keyWord);
        XyUrl.okPost(XyUrl.GET_MEDICINE_SEARCH, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List list = JSONObject.parseArray(value, MedicineAddPopupListBean.class);
                Message msg = getHandlerMessage();
                msg.obj = list;
                msg.what = GET_SEARCH_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (ConstantParam.NO_DATA == error) {
                    sendHandlerMessage(GET_SEARCH_NO_DATA);
                }
            }
        });

    }

    private void initTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), format);
        tvTime.setText(allTimeString);
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_pharmacy_add, contentLayout, false);
    }


    private void saveData() {
        String name = etPharmacyName.getText().toString().trim();
        String count = etPharmacyCount.getText().toString().trim();
        String dosage = etPharmacyDosage.getText().toString().trim();
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();
        String time = tvTime.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入药品名称");
            return;
        }
        if (TextUtils.isEmpty(count)) {
            ToastUtils.showShort("请输入服药次数");
            return;
        }
        if (TextUtils.isEmpty(dosage)) {
            ToastUtils.showShort("请输入服药剂量");
            return;
        }
        if (cardNumber == 0) {
            ToastUtils.showShort("请选择剂量单位");
            return;
        }


        Map<String, Object> map = new HashMap<>();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        map.put("times", count);
        map.put("drugname", name);
        map.put("remark", 2);
        map.put("dosage", dosage + dosageNameList[cardNumber]);
        map.put("labellid", labellid);
        String type = getIntent().getStringExtra("type");
        String url;
        if ("0".equals(type)) {
            if (TextUtils.isEmpty(time)) {
                ToastUtils.showShort("请选择用药时间");
                return;
            }
            url = XyUrl.ADD_PHARMACY;
            map.put("datetime", time);
        } else {
            //            if (TextUtils.isEmpty(startTime)) {
            //                ToastUtils.showShort("请选择开始用药时间");
            //                return;
            //            }
            //            if (TextUtils.isEmpty(endTime)) {
            //                ToastUtils.showShort("请选择结束用药时间");
            //                return;
            //            }
            url = XyUrl.ADD_PHARMACY_RECORD;
            map.put("starttime", startTime);
            map.put("endtime", endTime);
        }
        XyUrl.okPostSave(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                finish();
                if ("1".equals(type)) {
                    //刷新
                    EventBusUtils.post(new EventMessage<>(ConstantParam.ADD_MEDICINE));
                } else {
                    EventBusUtils.post(new EventMessage<>(ConstantParam.PHARMACY_RECORD_ADD));
                }
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    private void initView() {
        spinnerPharmacyDosage.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_spinner_pharmacy, dosageNameList);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown_pharmacy);
        spinnerPharmacyDosage.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cardNumber = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @OnClick({R.id.rl_start_time, R.id.rl_end_time, R.id.ll_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_start_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvStartTime.setText(content);
                    }
                });
                break;
            case R.id.rl_end_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvEndTime.setText(content);
                    }
                });
                break;
            case R.id.ll_time:
                PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTime.setText(content);
                    }
                });
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SEARCH_DATA:
                List<MedicineAddPopupListBean> list = (List<MedicineAddPopupListBean>) msg.obj;
                MedicineAddPopup medicineAddPopup = new MedicineAddPopup(getPageContext());
                RecyclerView rvList = medicineAddPopup.findViewById(R.id.rv_medicine_add);
                MedicineAddPopupAdapter popupAdapter = new MedicineAddPopupAdapter(list);
                rvList.setAdapter(popupAdapter);
                rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                popupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                        labellid = list.get(i).getLabellid();
                        String medicine = list.get(i).getMedicine();
                        Log.e(TAG, "medicine==" + medicine);
                        SPStaticUtils.put("searchKeyWord", medicine);
                        medicineAddPopup.dismiss();
                        etPharmacyName.setText(medicine);
                        etPharmacyName.setSelection(medicine.length());
                    }
                });
                medicineAddPopup.showPopupWindow(rlMedicineName);
                break;
            case GET_SEARCH_NO_DATA:
                labellid = 0;
                break;
        }
    }
}
