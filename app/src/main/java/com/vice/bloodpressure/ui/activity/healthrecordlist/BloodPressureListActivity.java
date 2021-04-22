package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.BloodPressureListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.BloodPressureBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodPressureAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:  记录血压
 * 作者: LYD
 * 创建日期: 2020/4/21 16:29
 */
public class BloodPressureListActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {
    private static final int GET_DATA_SUCCESS = 0x001213;
    private static final int GET_DATA_ERROR = 0x001314;
    private static final int REFRESH_SUCCESS = 0x001415;
    private static final int REFRESH_ERROR = 0x001516;
    private static final int LOAD_MORE_SUCCESS = 0x001718;
    private static final int LOAD_MORE_ERROR = 0x001819;
    private static final int DEL_SUCCESS = 10010;
    private SmartRefreshLayout srlBloodRecord;
    private RecyclerView rvBloodRecord;
    private TextView tvBegin;//开始时间
    private TextView tvEnd;//结束时间

    //private TYAdapter<BloodPressureBean> adapter;
    private BloodPressureListAdapter adapter;
    private List<BloodPressureBean> bloodPressureList;//血压数据
    private List<BloodPressureBean> bpList;//上拉加载数据
    private int i = 1;//上拉加载页数
    private String bTime = "";
    private String eTime = "";
    private Map<String, Object> map;


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA_SUCCESS:
            case REFRESH_SUCCESS:
                bloodPressureList = (List<BloodPressureBean>) msg.obj;
                adapter = new BloodPressureListAdapter(bloodPressureList);
                rvBloodRecord.setAdapter(adapter);
                rvBloodRecord.setLayoutManager(new LinearLayoutManager(getPageContext(), LinearLayoutManager.VERTICAL, false));
                setOnLongClick();
                break;
            case GET_DATA_ERROR:
                bloodPressureList = new ArrayList<>();
                adapter = new BloodPressureListAdapter(bloodPressureList);
                rvBloodRecord.setAdapter(adapter);
                rvBloodRecord.setLayoutManager(new LinearLayoutManager(getPageContext(), LinearLayoutManager.VERTICAL, false));
                break;
            case LOAD_MORE_SUCCESS:
                adapter.notifyDataSetChanged();
                srlBloodRecord.finishLoadMore();
                break;
            case LOAD_MORE_ERROR:
                ToastUtils.showShort("没有更多");
                adapter.notifyDataSetChanged();
                srlBloodRecord.finishLoadMore();
                break;
            case DEL_SUCCESS:
                getData(bTime, eTime);
                EventBusUtils.post(new EventMessage<>(ConstantParam.BLOOD_PRESSURE_RECORD_ADD));
                break;
            default:
                break;
        }
    }

    private void setOnLongClick() {
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                    toDoDel(position);
                });
                return false;
            }
        });
    }


    private void toDoDel(int position) {
        //1血糖 2血压 3饮食 4运动 5用药 6bmi 7糖化血红蛋白 8检查 9肝病营养指标
        int id = bloodPressureList.get(position).getId();
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 2);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血压记录");
        initViews();
        getData(bTime, eTime);
    }

    private void getData(String b, String e) {
        i = 1;
        map = new HashMap<>();
        map.put("begintime", b);
        map.put("endtime", e);
        map.put("page", i);
        XyUrl.okPost(XyUrl.QUERY_BLOOD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                bloodPressureList = JSONObject.parseArray(value, BloodPressureBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA_SUCCESS;
                message.obj = bloodPressureList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                Message message = Message.obtain();
                message.what = GET_DATA_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    private void initViews() {
        tvBegin = findViewById(R.id.tv_begin_time);
        tvEnd = findViewById(R.id.tv_end_time);
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        srlBloodRecord = findViewById(R.id.srl_health_record_base);
        rvBloodRecord = findViewById(R.id.rv_health_record_base);
        Button btnAddRecord = findViewById(R.id.btn_add_record);
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodPressureListActivity.this, BloodPressureAddActivity.class));
            }
        });
        //下拉刷新
        srlBloodRecord.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                srlBloodRecord.finishRefresh(2000);
                refresh();
            }
        });
        //上拉加载
        srlBloodRecord.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlBloodRecord.finishLoadMore(2000);
                i++;
                map = new HashMap<>();
                map.put("begintime", bTime);
                map.put("endtime", eTime);
                map.put("page", i);
                XyUrl.okPost(XyUrl.QUERY_BLOOD, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        bpList = JSONObject.parseArray(value, BloodPressureBean.class);
                        bloodPressureList.addAll(bpList);
                        Message message = Message.obtain();
                        message.what = LOAD_MORE_SUCCESS;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        i--;
                        Message message = Message.obtain();
                        message.what = LOAD_MORE_ERROR;
                        sendHandlerMessage(message);
                    }
                });
            }
        });
    }

    private void refresh() {
        tvBegin.setText(getString(R.string.start_date));
        tvEnd.setText(getString(R.string.end_date));
        i = 1;
        map = new HashMap<>();
        map.put("begintime", bTime);
        map.put("endtime", eTime);
        map.put("page", i);
        XyUrl.okPost(XyUrl.QUERY_BLOOD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                LogUtils.e("data---" + value);
                bloodPressureList = JSONObject.parseArray(value, BloodPressureBean.class);
                LogUtils.e("list---" + bloodPressureList);
                Message message = Message.obtain();
                message.what = REFRESH_SUCCESS;
                message.obj = bloodPressureList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
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
            case R.id.tv_begin_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
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
                PickerUtils.showTime(BloodPressureListActivity.this, new PickerUtils.TimePickerCallBack() {
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

            default:
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        if (event.getCode() == ConstantParam.BLOOD_PRESSURE_RECORD_ADD) {
            refresh();
        }
    }
}