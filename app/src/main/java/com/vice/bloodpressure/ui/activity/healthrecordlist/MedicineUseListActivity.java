package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.PharmacyAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.PharmacyBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.PharmacyAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicineUseListActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {
    private static final int GET_DATA = 0x001998;
    private static final int LORD_MORE = 0x002998;
    private static final int DEL_SUCCESS = 10010;
    private static final int GET_NO_DATA = 10011;

    private TextView tvBeginTime;
    private TextView tvEndTime;
    private SmartRefreshLayout srlPharmacy;
    private ListView lvSport;
    private LinearLayout llBottom;
    private PharmacyAdapter adapter;


    private List<PharmacyBean> bloodPressureList;//血压数据
    private List<PharmacyBean> bpList;//上拉加载数据
    private int i = 1;//上拉加载页数

    private String bTime = "";
    private String eTime = "";

    private Map<String, Object> map;

    private LoginBean user;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                bloodPressureList = (List<PharmacyBean>) msg.obj;
                adapter = new PharmacyAdapter(Utils.getApp(), R.layout.item_health_record_pharmacy, bloodPressureList);
                lvSport.setAdapter(adapter);
                srlPharmacy.finishRefresh();
                break;
            case LORD_MORE:
                adapter.notifyDataSetChanged();
                srlPharmacy.finishLoadMore();
                break;
            case DEL_SUCCESS:
                getData(bTime, eTime);
                break;
            case GET_NO_DATA:
                String errorMsg = (String) msg.obj;
                lvSport.setAdapter(null);
                ToastUtils.showShort(errorMsg);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("用药记录");
        initView();
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        getData(bTime, eTime);
        initLvOnLongClick();
    }

    private void initLvOnLongClick() {
        lvSport.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                    toDoDel(position);
                });
                return true;
            }
        });
    }

    private void toDoDel(int position) {
        //1血糖 2血压 3饮食 4运动 5用药 6bmi 7糖化血红蛋白 8检查 9肝病营养指标
        int id = bloodPressureList.get(position).getId();
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 5);
        XyUrl.okPostSave(XyUrl.HEALTH_RECORD_DEL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = DEL_SUCCESS;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, final String errorMsg) {

            }
        });
    }

    private String getUserId() {
        String userid = String.valueOf(getIntent().getIntExtra("user_id", -1));
        if ("-1".equals(userid)) {
            return user.getUserid();
        } else {
            llBottom.setVisibility(View.GONE);
            return userid;
        }
    }

    private void getData(String bTime, String eTime) {
        i = 1;
        map = new HashMap<>();
        map.put("begintime", bTime);
        map.put("endtime", eTime);
        map.put("page", i);
        XyUrl.okPost(XyUrl.QUERY_PHARMACY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bloodPressureList = JSONObject.parseArray(value, PharmacyBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = bloodPressureList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                if (ConstantParam.NO_DATA == errorCode) {
                    Message message = Message.obtain();
                    message.what = GET_NO_DATA;
                    message.obj = errorMsg;
                    sendHandlerMessage(message);
                }

            }
        });
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_health_record_base_two, contentLayout, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_begin_time:
                PickerUtils.showTime(MedicineUseListActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvBeginTime.setText(content);
                        eTime = tvEndTime.getText().toString().trim();
                        if ("选择结束时间".equals(eTime)) {
                            eTime = "";
                        }
                        bTime = tvBeginTime.getText().toString().trim();
                        getData(bTime, eTime);
                    }

                });
                break;
            case R.id.tv_end_time:
                PickerUtils.showTime(MedicineUseListActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvEndTime.setText(content);
                        bTime = tvBeginTime.getText().toString().trim();
                        if ("选择开始时间".equals(bTime)) {
                            bTime = "";
                        }
                        eTime = tvEndTime.getText().toString().trim();
                        getData(bTime, eTime);
                    }

                });
                break;
        }
    }

    private void initView() {
        llBottom = findViewById(R.id.ll_bottom);
        tvBeginTime = findViewById(R.id.tv_begin_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        srlPharmacy = findViewById(R.id.srl_health_record_base);
        lvSport = findViewById(R.id.lv_health_record_base);
        tvBeginTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);

        Button btnAdd = findViewById(R.id.btn_add_health_record_base);
        btnAdd.setText("添加用药记录   ");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicineUseListActivity.this, PharmacyAddActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
            }
        });

        //下拉刷新
        srlPharmacy.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                srlPharmacy.finishRefresh(2000);
                refresh();
            }
        });
        //上拉加载
        srlPharmacy.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlPharmacy.finishLoadMore(2000);
                i++;
                map = new HashMap<>();
                map.put("begintime", bTime);
                map.put("endtime", eTime);
                map.put("page", i);
                XyUrl.okPost(XyUrl.QUERY_PHARMACY, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        bpList = JSONObject.parseArray(value, PharmacyBean.class);
                        bloodPressureList.addAll(bpList);
                        Message message = Message.obtain();
                        message.what = LORD_MORE;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        i--;
                    }
                });
            }
        });
    }

    private void refresh() {
        tvBeginTime.setText(getString(R.string.start_date));
        tvEndTime.setText(getString(R.string.end_date));
        i = 1;
        map = new HashMap<>();
        map.put("begintime", bTime);
        map.put("endtime", eTime);
        map.put("page", i);
        XyUrl.okPost(XyUrl.QUERY_PHARMACY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bloodPressureList = JSONObject.parseArray(value, PharmacyBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = bloodPressureList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        if (event.getCode() == ConstantParam.PHARMACY_RECORD_ADD) {
            refresh();
        }
    }
}
