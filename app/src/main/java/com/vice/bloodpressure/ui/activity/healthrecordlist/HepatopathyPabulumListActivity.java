package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
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
import com.vice.bloodpressure.adapter.HepatopathyPabulumListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.HepatopathyPabulumListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.HepatopathyPabulumAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:   肝病营养指标列表
 * 作者: LYD
 * 创建日期: 2019/9/5 17:46
 */
public class HepatopathyPabulumListActivity extends BaseHandlerEventBusActivity {
    private static final int GET_MORE = 10010;
    private static final int GET_DATA = 10012;
    private static final int DEL_SUCCESS = 10013;
    private static final int GET_NO_DATA = 10014;

    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;

    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.rv_hepatopathy_pabulum)
    RecyclerView rvHepatopathyPabulum;
    @BindView(R.id.srl_hepatopathy_pabulum)
    SmartRefreshLayout srlHepatopathyPabulum;


    private int pageIndex = 1;
    private String beginTime = "";
    private String endTime = "";

    private List<HepatopathyPabulumListBean> list;
    private List<HepatopathyPabulumListBean> tempList;//下拉加载数据
    private HepatopathyPabulumListAdapter adapter;
    private LoginBean user;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                list = (List<HepatopathyPabulumListBean>) msg.obj;
                adapter = new HepatopathyPabulumListAdapter(list);
                //设置布局管理器
                LinearLayoutManager layoutManager = new LinearLayoutManager(getPageContext());
                rvHepatopathyPabulum.setLayoutManager(layoutManager);
                rvHepatopathyPabulum.setAdapter(adapter);
                srlHepatopathyPabulum.finishRefresh();
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
            case GET_MORE:
                adapter.notifyDataSetChanged();
                srlHepatopathyPabulum.finishLoadMore();
                break;
            case DEL_SUCCESS:
                getData(beginTime, endTime);
                break;
            case GET_NO_DATA:
                String errorMsg = (String) msg.obj;
                rvHepatopathyPabulum.setAdapter(null);
                ToastUtils.showShort(errorMsg);
                break;
        }
    }

    private void toDoDel(int position) {
        //1血糖 2血压 3饮食 4运动 5用药 6bmi 7糖化血红蛋白 8检查 9肝病营养指标
        int id = list.get(position).getId();
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 9);
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
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hepatopathy_pabulum_list, contentLayout, false);
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
        setTitle("肝病营养指标记录");
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        getData(beginTime, endTime);
        initRefresh();
    }


    /**
     * 下拉刷新
     */
    private void initRefresh() {
        //下拉刷新
        srlHepatopathyPabulum.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                srlHepatopathyPabulum.finishRefresh(2000);
                pageIndex = 1;
                getData(beginTime, endTime);
            }
        });
        //上拉加载
        srlHepatopathyPabulum.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlHepatopathyPabulum.finishLoadMore(2000);
                pageIndex++;
                HashMap<String, Object> map = new HashMap<>();
                map.put("begintime", beginTime);
                map.put("endtime", endTime);
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.HEPATOPATHY_PABULUM_LIVER_LIST, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, HepatopathyPabulumListBean.class);
                        list.addAll(tempList);
                        Message message = Message.obtain();
                        message.what = GET_MORE;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        pageIndex--;
                    }
                });
            }
        });

    }


    @OnClick({R.id.rl_begin_time, R.id.rl_end_time, R.id.btn_add_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_begin_time:
                PickerUtils.showTime(HepatopathyPabulumListActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        pageIndex = 1;
                        tvBeginTime.setText(content);
                        endTime = tvEndTime.getText().toString().trim();
                        if ("选择结束时间".equals(endTime)) {
                            endTime = "";
                        }
                        beginTime = tvBeginTime.getText().toString().trim();
                        getData(beginTime, endTime);
                    }

                });
                break;
            case R.id.rl_end_time:
                PickerUtils.showTime(HepatopathyPabulumListActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        pageIndex = 1;
                        tvEndTime.setText(content);
                        beginTime = tvBeginTime.getText().toString().trim();
                        if ("选择开始时间".equals(beginTime)) {
                            beginTime = "";
                        }
                        endTime = tvEndTime.getText().toString().trim();
                        getData(beginTime, endTime);
                    }

                });
                break;
            case R.id.btn_add_record:
                startActivity(new Intent(this, HepatopathyPabulumAddActivity.class));
                break;
        }
    }

    /**
     * 获取
     *
     * @param beginTime
     * @param endTime
     */
    private void getData(String beginTime, String endTime) {
        HashMap map = new HashMap<>();
        map.put("begintime", beginTime);
        map.put("endtime", endTime);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.HEPATOPATHY_PABULUM_LIVER_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, HepatopathyPabulumListBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = list;
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
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.HEPATOPATHY_PABULUM_ADD:
                getData("", "");
                break;
        }
    }
}
