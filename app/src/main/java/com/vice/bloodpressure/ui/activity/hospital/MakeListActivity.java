package com.vice.bloodpressure.ui.activity.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MakeAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.MakeBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:预约住院
 * 作者: LYD
 * 创建日期: 2019/3/21 10:44
 */

public class MakeListActivity extends BaseHandlerActivity implements View.OnClickListener {

    private static final int GET_DATA = 0x0098;
    private static final int LORD_MORE = 0x0099;
    private LoginBean user;
    //分页开始
    private List<MakeBean> list;
    private List<MakeBean> tempList;
    private int pageIndex = 2;//页数
    private MakeAdapter adapter;
    //分页结束

    private SmartRefreshLayout srlMake;
    private ListView lvMake;
    private String doctorId;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                list = (List<MakeBean>) msg.obj;
                adapter = new MakeAdapter(getPageContext(), R.layout.item_make_list, list);
                lvMake.setAdapter(adapter);
                srlMake.finishRefresh();
                break;
            case LORD_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                srlMake.finishLoadMore();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.make_hospital));
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        doctorId = getIntent().getStringExtra("doctorId");
        getData();
        initView();
    }


    /**
     * 获取住院列表
     */
    private void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("docid", doctorId);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_INHOSPITAL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, MakeBean.class);
                Message msg = Message.obtain();
                msg.obj = list;
                msg.what = GET_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_make_list_hospital, contentLayout, false);
        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_make_hos:
                Intent intent = new Intent(getPageContext(), MakeActivity.class);
                intent.putExtra("doctorId", doctorId);
                startActivity(intent);
                break;
        }
    }


    private void initView() {
        srlMake = findViewById(R.id.srl_make);
        lvMake = findViewById(R.id.lv_make);
        Button btnMakeHos = findViewById(R.id.btn_make_hos);
        btnMakeHos.setOnClickListener(this);

        //刷新开始

        srlMake.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlMake.finishRefresh(2000);

                pageIndex = 2;
                getData();
            }
        });
        srlMake.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlMake.finishLoadMore(2000);
                HashMap<String, Object> map = new HashMap<>();
                map.put("docid", doctorId);
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.GET_INHOSPITAL, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, MakeBean.class);
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


}
