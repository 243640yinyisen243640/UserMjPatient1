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
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.TYAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.BmiBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BmiAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:  BMI记录
 * 作者: LYD
 * 创建日期: 2020/6/28 14:06
 */
public class BmiListActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {

    private static final int GET_DATA = 0x1998;
    private static final int LORD_MORE_SUCCEED = 0x2998;
    private static final int LORD_MORE_FAILED = 0x3998;
    private static final int REFRESH_SUCCEED = 0x4998;
    private static final int REFRESH_FAILED = 0x5998;
    private static final int DEL_SUCCESS = 10010;
    private static final int GET_NO_DATA = 10011;


    private SmartRefreshLayout srlBmi;
    private RecyclerView rvBmi;
    private LinearLayout llBottom;
    private TYAdapter<BmiBean> adapter;
    private int key;//根据key判断加载的item
    private Map<String, Object> map;
    private List<BmiBean> bmiList;
    private List<BmiBean> bpList;//下拉加载数据
    private TextView tvBegin;//开始时间
    private TextView tvEnd;//结束时间
    private int i = 1;//上拉加载页数
    private String bTime = "";
    private String eTime = "";
    private LoginBean user;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                bmiList = (List<BmiBean>) msg.obj;
                adapter = new TYAdapter<>(BmiListActivity.this, bmiList, key);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(BmiListActivity.this);
                linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                rvBmi.setLayoutManager(linearLayoutManager1);
                rvBmi.setAdapter(adapter);
                setLongClick(adapter);
                break;
            case REFRESH_SUCCEED:
                bmiList = (List<BmiBean>) msg.obj;
                adapter = new TYAdapter<>(BmiListActivity.this, bmiList, key);
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(BmiListActivity.this);
                linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                rvBmi.setLayoutManager(linearLayoutManager2);
                rvBmi.setAdapter(adapter);
                srlBmi.finishRefresh();
                setLongClick(adapter);
                break;
            case REFRESH_FAILED:
                ToastUtils.showShort("刷新失败");
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                srlBmi.finishRefresh();
                break;
            case LORD_MORE_SUCCEED:
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                srlBmi.finishLoadMore();
                break;
            case LORD_MORE_FAILED:
                ToastUtils.showShort("没有更多");
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                srlBmi.finishLoadMore();
                break;
            case DEL_SUCCESS:
                getData(bTime, eTime);
                EventBusUtils.post(new EventMessage<>(ConstantParam.BMI_RECORD_ADD));
                break;
            case GET_NO_DATA:
                String errorMsg = (String) msg.obj;
                rvBmi.setAdapter(null);
                ToastUtils.showShort(errorMsg);
                break;
        }
    }

    private void setLongClick(TYAdapter<BmiBean> adapter) {
        adapter.setLongClickListener(new TYAdapter.OnLongClickListener() {
            @Override
            public boolean onLongClick(int position) {
                //ToastUtils.showShort(position);
                DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                    toDoDel(position);
                });
                return false;
            }
        });
    }

    private void toDoDel(int position) {
        int id = bmiList.get(position).getId();
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 6);
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
        setTitle("BMI记录");
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        key = getIntent().getIntExtra("key", 0);
        initViews();
        getData(bTime, eTime);
    }

    private void getData(String b, String e) {
        i = 1;
        map = new HashMap<>();
        map.put("begintime", b);
        map.put("endtime", e);
        map.put("page", i);
        XyUrl.okPost(XyUrl.QUERY_BMI, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bmiList = JSONObject.parseArray(value, BmiBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = bmiList;
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
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        //添加数据
        Button btnAddRecord = findViewById(R.id.btn_add_record);
        btnAddRecord.setText("添加BMI记录   ");
        btnAddRecord.setOnClickListener(this);
        srlBmi = findViewById(R.id.srl_health_record_base);
        rvBmi = findViewById(R.id.rv_health_record_base);


        //下拉刷新
        srlBmi.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                srlBmi.finishRefresh(2000);
                refresh();
            }
        });
        //上拉加载
        srlBmi.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlBmi.finishLoadMore(2000);
                i++;
                map = new HashMap<>();
                map.put("begintime", bTime);
                map.put("endtime", eTime);
                map.put("page", i);
                XyUrl.okPost(XyUrl.QUERY_BMI, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        bpList = JSONObject.parseArray(value, BmiBean.class);
                        bmiList.addAll(bpList);
                        Message message = Message.obtain();
                        message.what = LORD_MORE_SUCCEED;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        i--;
                        sendHandlerMessage(LORD_MORE_FAILED);
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
                startActivity(new Intent(this, BmiAddActivity.class));
                break;
            case R.id.tv_begin_time:
                PickerUtils.showTime(BmiListActivity.this, new PickerUtils.TimePickerCallBack() {
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
                PickerUtils.showTime(BmiListActivity.this, new PickerUtils.TimePickerCallBack() {
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
        XyUrl.okPost(XyUrl.QUERY_BMI, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bmiList = JSONObject.parseArray(value, BmiBean.class);
                Message message = Message.obtain();
                message.what = REFRESH_SUCCEED;
                message.obj = bmiList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(REFRESH_FAILED);
            }
        });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        if (event.getCode() == ConstantParam.BMI_RECORD_ADD) {
            refresh();
        }
    }
}
