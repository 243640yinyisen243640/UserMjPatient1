package com.vice.bloodpressure.ui.activity.patienteducation;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.PatientEducationListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.PatientEducationListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述:  医生大讲堂 列表页
 * 作者: LYD
 * 创建日期: 2020/7/15 9:24
 */
public class PatientEducationListActivity extends BaseHandlerActivity {
    private static final int GET_LIST_SUCCESS = 10010;
    private static final int LORD_MORE = 10011;
    private static final int GET_NO_DATA = 10012;
    @BindView(R.id.lv_doc_class)
    ListView lvDocClass;
    @BindView(R.id.srl_doc_class)
    SmartRefreshLayout srlDocClass;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //分页开始
    private List<PatientEducationListBean> list;
    private List<PatientEducationListBean> tempList;
    private int pageIndex = 2;
    private PatientEducationListAdapter adapter;
    //分页结束


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("患者宣教");
        initRefresh();
        getPatientEducationList();
    }

    private void getPatientEducationList() {
        String docId = getIntent().getStringExtra("docId");
        Map<String, Object> map = new HashMap<>();
        map.put("docid", docId);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.DOCTOR_CLASS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, PatientEducationListBean.class);
                Message message = Message.obtain();
                message.what = GET_LIST_SUCCESS;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                msg.what = GET_NO_DATA;
                sendHandlerMessage(msg);
            }
        });
    }

    private void initRefresh() {
        //刷新开始
        srlDocClass.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlDocClass.finishRefresh(2000);
                pageIndex = 2;
                getPatientEducationList();
            }
        });
        srlDocClass.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlDocClass.finishLoadMore(2000);
                String docId = getIntent().getStringExtra("docId");
                HashMap<String, Object> map = new HashMap<>();
                map.put("docid", docId);
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.DOCTOR_CLASS, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, PatientEducationListBean.class);
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

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_doctor_class, contentLayout, false);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_NO_DATA:
                srlDocClass.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
            case GET_LIST_SUCCESS:
                srlDocClass.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                list = (List<PatientEducationListBean>) msg.obj;
                adapter = new PatientEducationListAdapter(PatientEducationListActivity.this, R.layout.item_patient_education_list, list);
                lvDocClass.setAdapter(adapter);
                break;
            case LORD_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
