package com.vice.bloodpressure.ui.activity.hospital;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.AdviceAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.AdviceBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * 描述: 医生建议
 * 作者: LYD
 * 创建日期: 2019/3/20 21:33
 */

public class AdviceActivity extends BaseHandlerActivity implements View.OnClickListener {
    private static final int GET_DATA = 0x0096;
    private static final int LORD_MORE = 0x0097;
    private SmartRefreshLayout srlAdvice;
    private ListView lvAdvice;
    private List<AdviceBean> list;
    private List<AdviceBean> tempList;
    //页数
    private int pageIndex = 2;
    private AdviceAdapter adapter;
    //分页结束
    //private MyDoctorBean doctor;
    private LoginBean user;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                list = (List<AdviceBean>) msg.obj;
                adapter = new AdviceAdapter(getPageContext(), list);
                lvAdvice.setAdapter(adapter);
                break;
            case LORD_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_advice, contentLayout, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        setTitle(getString(R.string.doctor_advice));
        initView();
        getData();
    }

    private void initView() {
        srlAdvice = findViewById(R.id.srl_advice);
        lvAdvice = findViewById(R.id.lv_advice);
        TextView tvAskAdvice = findViewById(R.id.tv_ask_advice);
        tvAskAdvice.setOnClickListener(this);
        //刷新开始
        srlAdvice.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlAdvice.finishRefresh(2000);
                pageIndex = 2;
                getData();
            }
        });
        srlAdvice.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlAdvice.finishLoadMore(2000);
                HashMap<String, Object> map = new HashMap<>();
                map.put("docid", getIntent().getIntExtra("docId", 0));
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.DOCTOR_ADVICE, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, AdviceBean.class);
                        list.addAll(tempList);
                        Message message = Message.obtain();
                        message.what = LORD_MORE;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        });
        //刷新结束
    }

    /**
     * 医生建议
     */
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("docid", getIntent().getIntExtra("docId", 0));
        map.put("page", 1);
        XyUrl.okPost(XyUrl.DOCTOR_ADVICE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, AdviceBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ask_advice://聊天
                RongIM.getInstance().startPrivateChat(getPageContext(), getIntent().getIntExtra("docId", 0) + "", getIntent().getStringExtra("docName"));
                break;
        }
    }
}
