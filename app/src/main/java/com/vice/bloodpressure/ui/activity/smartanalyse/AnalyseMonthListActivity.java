package com.vice.bloodpressure.ui.activity.smartanalyse;

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
import com.vice.bloodpressure.adapter.AnalyseMonthListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 智能分析月份列表
 * 作者: LYD
 * 创建日期: 2019/4/17 9:38
 */

public class AnalyseMonthListActivity extends BaseHandlerActivity {
    private static final int GET_LV_DATA = 10010;
    private static final int GET_MORE = 10011;
    @BindView(R.id.srl_month)
    SmartRefreshLayout srlMonth;
    @BindView(R.id.lv_month)
    ListView lvMonth;


    //分页开始
    private AnalyseMonthListAdapter adapter;
    private List<String> list;
    private List<String> tempList;
    private int pageIndex = 2;
    //分页结束

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //0 血糖
        //1 血压
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)) {
            setTitle("血糖分析报告");
        } else {
            setTitle("血压分析报告");
        }
        getLvData();
        initRefresh();
    }

    private void initRefresh() {
        //刷新开始
        srlMonth.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlMonth.finishRefresh(2000);
                pageIndex = 2;
                getLvData();
            }
        });
        srlMonth.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlMonth.finishLoadMore(2000);
                getMoreData();
            }
        });
        //刷新结束
    }


    /**
     * 获取更多
     */
    private void getMoreData() {
        HashMap map = new HashMap<>();
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_PORT_PERSONAL_MONTHLIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                tempList = JSONObject.parseArray(data, String.class);
                list.addAll(tempList);
                Message message = getHandlerMessage();
                message.what = GET_MORE;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }


    /**
     * 获取数据
     */
    private void getLvData() {
        HashMap map = new HashMap<>();
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_PORT_PERSONAL_MONTHLIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                List<String> listData = JSONObject.parseArray(data, String.class);
                Message msg = getHandlerMessage();
                msg.obj = listData;
                msg.what = GET_LV_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_analyse_month_list, contentLayout, false);
        return layout;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_LV_DATA:
                list = (List<String>) msg.obj;
                if (list != null && list.size() > 0) {
                    String type = getIntent().getStringExtra("type");
                    adapter = new AnalyseMonthListAdapter(getPageContext(), R.layout.item_analyse_month, list, type);
                    lvMonth.setAdapter(adapter);
                }
                break;
            case GET_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
