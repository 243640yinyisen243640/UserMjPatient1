package com.vice.bloodpressure.ui.activity.registration;

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
import com.vice.bloodpressure.adapter.ElectrolyteListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.ElectrolyteListBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  电解质列表
 * 作者: LYD
 * 创建日期: 2019/10/31 9:44
 */
public class ElectrolyteListActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10086;
    private static final int GET_NO_DATA = 30002;
    private static final int GET_MORE_DATA = 10010;
    private static final int DEL_SUCCESS = 10011;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    //分页
    private ElectrolyteListAdapter adapter;
    //总数据
    private List<ElectrolyteListBean> list;
    //上拉加载数据
    private List<ElectrolyteListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;

    //分页
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("电解质");
        String ofid = getIntent().getStringExtra("ofid");
        getList();
        initRefresh();
    }

    private void initRefresh() {

        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlList.finishRefresh(2000);
                pageIndex = 2;
                getList();
            }
        });
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlList.finishLoadMore(2000);
                getMoreList();
            }
        });
    }

    private void getMoreList() {
        String ofid = getIntent().getStringExtra("ofid");
        HashMap<String, Object> map = new HashMap<>();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        map.put("page", pageIndex);
        map.put("ofid", ofid);
        XyUrl.okPost(XyUrl.GET_ELECTROLYTE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, ElectrolyteListBean.class);
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

    private void getList() {
        String ofid = getIntent().getStringExtra("ofid");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        HashMap map = new HashMap<>();
        map.put("page", 1);
        map.put("ofid", ofid);
        XyUrl.okPost(XyUrl.GET_ELECTROLYTE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, ElectrolyteListBean.class);
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
        View view = getLayoutInflater().inflate(R.layout.activity_electrolyte, contentLayout, false);
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
                list = (List<ElectrolyteListBean>) msg.obj;
                adapter = new ElectrolyteListAdapter(getPageContext(), R.layout.item_electrolyte_list, list);
                lvList.setAdapter(adapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
