package com.vice.bloodpressure.ui.activity.hospital;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.NoticeAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.NoticeBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;


/**
 * 描述: 科室公告列表
 * 作者: LYD
 * 创建日期: 2019/3/21 15:40
 */

public class DepartmentListActivity extends BaseHandlerActivity {

    private static final int GET_DATA = 0x0096;
    private static final int LORD_MORE = 0x0097;

    //private MyDoctorBean doctor;
    private LoginBean user;

    private SmartRefreshLayout srlDepartment;
    private ListView lvDepartment;


    //分页开始
    private List<NoticeBean> list;
    private List<NoticeBean> tempList;
    private int pageIndex = 2;//页数
    private NoticeAdapter adapter;
    //分页结束


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                list = (List<NoticeBean>) msg.obj;
                adapter = new NoticeAdapter(DepartmentListActivity.this, R.layout.item_notice, list);
                lvDepartment.setAdapter(adapter);
                break;
            case LORD_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.department_sign));
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        initView();
        getData();
    }


    /**
     * 科室公告列表
     */
    private void getData() {
        HashMap map = new HashMap<>();
        map.put("docid", getIntent().getStringExtra("docid"));
        map.put("id", "");
        map.put("token", user.getToken());
        map.put("page", 1);
        XyUrl.okPost(XyUrl.DEPARTMENT_LIST_AND_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, NoticeBean.class);
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
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_department_sign, contentLayout, false);
        return layout;
    }


    private void initView() {
        srlDepartment = findViewById(R.id.srl_department);
        lvDepartment = findViewById(R.id.lv_department);
        srlDepartment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlDepartment.finishRefresh(2000);
                pageIndex = 2;
                getData();
            }
        });
        srlDepartment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlDepartment.finishLoadMore(2000);
                HashMap<String, Object> map = new HashMap<>();
                map.put("docid", getIntent().getStringExtra("docid"));
                map.put("id", "");
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.DEPARTMENT_LIST_AND_DETAIL, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, NoticeBean.class);
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
