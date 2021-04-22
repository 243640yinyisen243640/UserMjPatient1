package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.BloodOxygenListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.BloodOxygenListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodOxygenAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  血氧记录
 * 作者: LYD
 * 创建日期: 2020/5/11 9:29
 */
public class BloodOxygenListActivity extends BaseHandlerEventBusActivity {
    private static final int GET_DATA_SUCCESS = 10010;
    private static final int GET_DATA_MORE = 10011;
    private static final int DEL_SUCCESS = 10012;
    private static final int GET_DATA_ERROR = 10013;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_blood)
    LinearLayout llBlood;
    @BindView(R.id.rv_health_record_base)
    RecyclerView rvHealthRecordBase;
    @BindView(R.id.srl_health_record_base)
    SmartRefreshLayout srlHealthRecordBase;
    @BindView(R.id.btn_add_record)
    Button btnAddRecord;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private String bTime = "";
    private String eTime = "";

    private BloodOxygenListAdapter adapter;
    private List<BloodOxygenListBean> list;
    private List<BloodOxygenListBean> tempList;
    private int pageIndex = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血氧记录");
        getData(bTime, eTime);
        initRefresh();
    }

    private void initRefresh() {
        //刷新开始
        srlHealthRecordBase.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlHealthRecordBase.finishRefresh(2000);
                pageIndex = 2;
                tvBeginTime.setText(getString(R.string.start_date));
                tvEndTime.setText(getString(R.string.end_date));
                getData("", "");
            }
        });
        srlHealthRecordBase.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlHealthRecordBase.finishLoadMore(2000);
                getDataMore(bTime, eTime);
            }
        });
        //刷新结束
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_blood_oxygen_list, contentLayout, false);
        return view;
    }


    @OnClick({R.id.tv_begin_time, R.id.tv_end_time, R.id.btn_add_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_begin_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
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
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
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
            case R.id.btn_add_record:
                Intent intent = new Intent(getPageContext(), BloodOxygenAddActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void getData(String b, String e) {
        HashMap map = new HashMap<>();
        map.put("begintime", b);
        map.put("endtime", e);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_BLOOD_OXYGEN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, BloodOxygenListBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA_SUCCESS;
                message.obj = list;
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

    /**
     * 获取更多
     *
     * @param bTime
     * @param eTime
     */
    private void getDataMore(String bTime, String eTime) {
        HashMap map = new HashMap<>();
        map.put("begintime", bTime);
        map.put("endtime", eTime);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_BLOOD_OXYGEN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, BloodOxygenListBean.class);
                list.addAll(tempList);
                Message message = Message.obtain();
                message.what = GET_DATA_MORE;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                //Message message = Message.obtain();
                //message.what = GET_DATA_ERROR;
                //sendHandlerMessage(message);
            }
        });


    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.ADD_BLOOD_OXYGEN:
                getData("", "");
                break;
        }
    }

    private void toDoDel(int position) {
        //1血糖 2血压 3饮食 4运动 5用药 6bmi 7糖化血红蛋白 8检查 9肝病营养指标
        int id = list.get(position).getOid();
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 10);
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
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA_SUCCESS:
                list = (List<BloodOxygenListBean>) msg.obj;
                if (list != null && list.size() > 0) {
                    adapter = new BloodOxygenListAdapter(list);
                    rvHealthRecordBase.setLayoutManager(new LinearLayoutManager(getPageContext()));
                    rvHealthRecordBase.setAdapter(adapter);
                    adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                            DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                                toDoDel(position);
                            });
                            return false;
                        }
                    });
                }
                break;
            case GET_DATA_ERROR:
                ToastUtils.showShort("数据为空");
                list = new ArrayList<>();
                adapter = new BloodOxygenListAdapter(list);
                rvHealthRecordBase.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvHealthRecordBase.setAdapter(adapter);
                adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                            toDoDel(position);
                        });
                        return false;
                    }
                });
                break;
            case GET_DATA_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case DEL_SUCCESS:
                getData(bTime, eTime);
                break;
        }
    }
}
