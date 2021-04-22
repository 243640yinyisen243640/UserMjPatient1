package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.TYAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.HemoglobinBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.HemoglobinAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yicheng on 2018/6/4.
 * 血红蛋白记录
 */
public class HemoglobinListActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {
    private static final int GET_DATA = 0x001998;
    private static final int LORD_MORE = 0x002998;
    private static final int DEL_SUCCESS = 10010;
    private static final int GET_NO_DATA = 10011;
    private int key;
    private int i = 1;//上拉加载页数
    private String bTime = "";
    private String eTime = "";
    private LoginBean user;
    private TYAdapter<HemoglobinBean> adapter;
    private Map<String, Object> map;
    private List<HemoglobinBean> hemoglobinList;
    private List<HemoglobinBean> bpList;//下拉加载数据

    private RecyclerView rvHemoglobin;
    private SmartRefreshLayout srlHemoglobin;
    private TextView tvBegin;//开始时间
    private TextView tvEnd;//结束时间
    private LinearLayout llBottom;


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                List<HemoglobinBean> bsList = (List<HemoglobinBean>) msg.obj;
                adapter = new TYAdapter<>(HemoglobinListActivity.this, bsList, key);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(HemoglobinListActivity.this);
                linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                rvHemoglobin.setLayoutManager(linearLayoutManager1);
                rvHemoglobin.setAdapter(adapter);
                srlHemoglobin.finishRefresh();
                adapter.setLongClickListener(new TYAdapter.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(int position) {
                        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                            toDoDel(position);
                        });
                        return false;
                    }
                });
                break;
            case LORD_MORE:
                adapter.notifyDataSetChanged();
                srlHemoglobin.finishLoadMore();
                break;
            case DEL_SUCCESS:
                getData(bTime, eTime);
                break;
            case GET_NO_DATA:
                String errorMsg = (String) msg.obj;
                rvHemoglobin.setAdapter(null);
                ToastUtils.showShort(errorMsg);
                break;
        }
    }

    private void toDoDel(int position) {
        //1血糖 2血压 3饮食 4运动 5用药 6bmi 7糖化血红蛋白 8检查 9肝病营养指标
        int id = hemoglobinList.get(position).getId();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 7);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("糖化血红蛋白记录");
        key = getIntent().getIntExtra("key", 0);
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        initViews();
        getData(bTime, eTime);
    }

    private void getData(String b, String e) {
        i = 1;
        map = new HashMap<>();
        map.put("begintime", b);
        map.put("endtime", e);
        map.put("page", i);
        XyUrl.okPost(XyUrl.QUERY_HEMOGLOBIN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                hemoglobinList = JSONObject.parseArray(value, HemoglobinBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = hemoglobinList;
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


    private void initViews() {
        llBottom = findViewById(R.id.ll_bottom);
        tvBegin = findViewById(R.id.tv_begin_time);
        tvEnd = findViewById(R.id.tv_end_time);
        //添加数据
        Button btnAddRecord = findViewById(R.id.btn_add_record);
        btnAddRecord.setText("添加糖化血红蛋白   ");
        btnAddRecord.setOnClickListener(this);
        srlHemoglobin = findViewById(R.id.srl_health_record_base);
        rvHemoglobin = findViewById(R.id.rv_health_record_base);
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);

        //下拉刷新
        srlHemoglobin.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                srlHemoglobin.finishRefresh(2000);
                refresh();
            }
        });
        //上拉加载
        srlHemoglobin.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlHemoglobin.finishLoadMore(2000);
                i++;
                map = new HashMap<>();
                map.put("begintime", bTime);
                map.put("endtime", eTime);
                map.put("page", i);
                XyUrl.okPost(XyUrl.QUERY_HEMOGLOBIN, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        bpList = JSONObject.parseArray(value, HemoglobinBean.class);
                        hemoglobinList.addAll(bpList);
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


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_health_record_base_one, contentLayout, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_record:
                startActivity(new Intent(this, HemoglobinAddActivity.class));
                break;
            case R.id.tv_begin_time:
                PickerUtils.showTime(HemoglobinListActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvBegin.setText(content);
                        eTime = tvEnd.getText().toString().trim();
                        if ("选择结束时间".equals(eTime)) {
                            eTime = "";
                        }
                        bTime = tvBegin.getText().toString().trim();
                        getData(bTime, eTime);
                    }

                });
                break;
            case R.id.tv_end_time:
                PickerUtils.showTime(HemoglobinListActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvEnd.setText(content);
                        bTime = tvBegin.getText().toString().trim();
                        if ("选择开始时间".equals(bTime)) {
                            bTime = "";
                        }
                        eTime = tvEnd.getText().toString().trim();
                        getData(bTime, eTime);
                    }

                });
                break;
        }
    }

    private void refresh() {
        tvBegin.setText(getString(R.string.start_date));
        tvEnd.setText(getString(R.string.end_date));
        i = 1;
        map = new HashMap<>();
        map.put("begintime", bTime);
        map.put("endtime", eTime);
        map.put("page", i);
        XyUrl.okPost(XyUrl.QUERY_HEMOGLOBIN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                hemoglobinList = JSONObject.parseArray(value, HemoglobinBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = hemoglobinList;
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
        if (event.getCode() == ConstantParam.HEMOGLOBIN_RECORD_ADD) {
            refresh();
        }
    }
}
