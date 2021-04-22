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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.CheckListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.ExamineBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.CheckAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:  检查记录
 * 作者: LYD
 * 创建日期: 2020/4/22 17:35
 */
public class CheckListActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {
    private static final int GET_DATA = 0x001998;
    private static final int LORD_MORE = 0x002998;
    private static final int DEL_SUCCESS = 10010;
    private static final int GET_NO_DATA = 10011;
    private Map<String, Object> map;
    private List<ExamineBean> examineList;
    private List<ExamineBean> bpList;//下拉加载数据
    private CheckListAdapter adapter;
    private int i = 1;//页数
    private String bTime = "";
    private String eTime = "";
    private LoginBean user;


    private RecyclerView rvExamine;
    private SmartRefreshLayout srlBloodRecord;
    private TextView tvBegin;//开始时间
    private TextView tvEnd;//结束时间
    private LinearLayout llBottom;


    private void toDoDel(int position, List<ExamineBean> allList) {
        //1血糖 2血压 3饮食 4运动 5用药 6bmi 7糖化血红蛋白 8检查 9肝病营养指标
        int id = allList.get(position).getId();
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 8);
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
        setTitle("检查记录");
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        initViews();
        getData(bTime, eTime);
    }


    private void initViews() {
        llBottom = findViewById(R.id.ll_bottom);
        tvBegin = findViewById(R.id.tv_begin_time);
        tvEnd = findViewById(R.id.tv_end_time);
        tvBegin.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        rvExamine = findViewById(R.id.rv_examine);
        srlBloodRecord = findViewById(R.id.srl_health_record_base);
        Button btnAdd = findViewById(R.id.btn_add_checkRecord);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getPageContext(), CheckAddActivity.class));
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
                XyUrl.okPost(XyUrl.GET_EXAMINE, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        bpList = JSONObject.parseArray(value, ExamineBean.class);
                        examineList.addAll(bpList);
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


    /**
     * 获取检查记录
     */
    private void getData(String b, String e) {
        i = 1;
        map = new HashMap<>();
        map.put("begintime", b);
        map.put("endtime", e);
        map.put("page", i);
        XyUrl.okPost(XyUrl.GET_EXAMINE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                examineList = JSONObject.parseArray(value, ExamineBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = examineList;
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
        return getLayoutInflater().inflate(R.layout.activity_examine, contentLayout, false);
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
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
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
        XyUrl.okPost(XyUrl.GET_EXAMINE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                examineList = JSONObject.parseArray(value, ExamineBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = examineList;
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
        if (event.getCode() == ConstantParam.CHECK_RECORD_ADD) {
            refresh();
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                final List<ExamineBean> allList = (List<ExamineBean>) msg.obj;
                adapter = new CheckListAdapter(allList);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getPageContext());
                linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                rvExamine.setLayoutManager(linearLayoutManager1);
                rvExamine.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                        Intent intent = new Intent(getPageContext(), CheckDetailsActivity.class);
                        intent.putExtra("check", allList.get(position));
                        startActivity(intent);
                    }
                });
                adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                            toDoDel(position, allList);
                        });
                        return false;
                    }
                });
                break;
            case LORD_MORE:
                adapter.notifyDataSetChanged();
                break;
            case DEL_SUCCESS:
                getData(bTime, eTime);
                break;
            case GET_NO_DATA:
                String errorMsg = (String) msg.obj;
                rvExamine.setAdapter(null);
                ToastUtils.showShort(errorMsg);
                break;
        }
    }
}
