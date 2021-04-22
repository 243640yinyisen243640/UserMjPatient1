package com.vice.bloodpressure.ui.activity.registration.report;

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
import com.vice.bloodpressure.adapter.ReportDoctorAdviceListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.ReportDoctorAdviceListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  医生就诊建议
 * 作者: LYD
 * 创建日期: 2019/10/30 17:22
 */
public class ReportDoctorAdviceListActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final int GET_NO_DATA = 30002;
    private static final int GET_MORE_DATA = 10086;

    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;

    //分页
    private ReportDoctorAdviceListAdapter adapter;
    //总数据
    private List<ReportDoctorAdviceListBean> list;
    //上拉加载数据
    private List<ReportDoctorAdviceListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("医生就诊建议");
        String ofid = getIntent().getStringExtra("ofid");
        getList(ofid);
        initRefresh(ofid);
    }

    private void initRefresh(String ofid) {

        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlList.finishRefresh(2000);
                pageIndex = 2;
                getList(ofid);
            }
        });
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlList.finishLoadMore(2000);
                getMoreList(ofid);
            }
        });
    }

    private void getMoreList(String ofid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("ofid", ofid);
        XyUrl.okPost(XyUrl.GET_DOC_ADVICE_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, ReportDoctorAdviceListBean.class);
                list.addAll(tempList);
                Message message = getHandlerMessage();
                message.what = GET_MORE_DATA;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    private void getList(String ofid) {
        HashMap map = new HashMap<>();
        map.put("ofid", ofid);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_DOC_ADVICE_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, ReportDoctorAdviceListBean.class);
                if (list != null && list.size() > 0) {
                    Message msg = getHandlerMessage();
                    msg.obj = list;
                    msg.what = GET_DATA;
                    sendHandlerMessage(msg);
                } else {
                    Message msg = getHandlerMessage();
                    msg.what = GET_NO_DATA;
                    sendHandlerMessage(msg);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                msg.what = GET_NO_DATA;
                sendHandlerMessage(msg);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_report_doctor_advice_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_NO_DATA:
                srlList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
            case GET_DATA:
                srlList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                list = (List<ReportDoctorAdviceListBean>) msg.obj;
                adapter = new ReportDoctorAdviceListAdapter(getPageContext(), R.layout.item_report_doctor_advice, list);
                lvList.setAdapter(adapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
