package com.vice.bloodpressure.ui.activity.smarteducation;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SmartEducationListClassifyAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.SmartEducationBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述:  智能教育 分类列表
 * 作者: LYD
 * 创建日期: 2020/3/30 9:10
 */
public class SmartEducationClassifyListActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final int GET_DATA_MORE = 10011;
    private static final int GET_SMART_EDUCATION_ERROR = 10012;
    @BindView(R.id.rv_education_list)
    RecyclerView rvEducationList;
    @BindView(R.id.srl_education_list)
    SmartRefreshLayout srlEducationList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    //分页开始
    private SmartEducationListClassifyAdapter adapter;
    private List<SmartEducationBean> list;
    private List<SmartEducationBean> tempList;
    private int pageIndex = 2;
    //分页结束

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能教育");
        getMyEducationList();
        initRefresh();
    }

    private void initRefresh() {
        srlEducationList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlEducationList.finishRefresh(2000);
                pageIndex = 2;
                getMyEducationList();
            }
        });
        srlEducationList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlEducationList.finishLoadMore(2000);
                getListMore();
            }
        });
    }

    private void getListMore() {
        Map<String, Object> map = new HashMap<>();
        int id = getIntent().getIntExtra("id", 0);
        map.put("page", pageIndex);
        map.put("id", id);
        XyUrl.okPost(XyUrl.SMART_EDUCATION_SERIES_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationBean> educationListBean = JSONObject.parseArray(value, SmartEducationBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA_MORE;
                message.obj = educationListBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 获取智能教育列表
     */
    private void getMyEducationList() {
        Map<String, Object> map = new HashMap<>();
        int id = getIntent().getIntExtra("id", 0);
        map.put("page", 1);
        map.put("id", id);
        XyUrl.okPost(XyUrl.SMART_EDUCATION_SERIES_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationBean> educationListBean = JSONObject.parseArray(value, SmartEducationBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = educationListBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (ConstantParam.NO_DATA == error) {
                    Message msg = getHandlerMessage();
                    msg.what = GET_SMART_EDUCATION_ERROR;
                    sendHandlerMessage(msg);
                }
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_education_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                llEmpty.setVisibility(View.GONE);
                srlEducationList.setVisibility(View.VISIBLE);
                list = (List<SmartEducationBean>) msg.obj;
                if (list != null && list.size() > 0) {
                    adapter = new SmartEducationListClassifyAdapter(list, getPageContext());
                    rvEducationList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                    rvEducationList.setAdapter(adapter);
                }
                break;
            case GET_DATA_MORE:
                tempList = (List<SmartEducationBean>) msg.obj;
                list.addAll(tempList);
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case GET_SMART_EDUCATION_ERROR:
                llEmpty.setVisibility(View.VISIBLE);
                srlEducationList.setVisibility(View.GONE);
                break;
        }
    }
}
