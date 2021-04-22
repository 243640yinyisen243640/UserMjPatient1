package com.vice.bloodpressure.ui.activity.tcm;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.TcmListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.TcmListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述: 中医体质列表
 * 作者: LYD
 * 创建日期: 2019/8/14 11:59
 */
public class TcmListActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10086;
    private static final int GET_DATA_MORE = 1008611;
    @BindView(R.id.lv_tcm_list)
    ListView lvTcmList;
    @BindView(R.id.srl_tcm_list)
    SmartRefreshLayout srlTcmList;
    @BindView(R.id.iv_no_tcm)
    ImageView ivNoTcm;
    @BindView(R.id.ll_tcm_content)
    LinearLayout llTcmContent;
    //分页开始
    private TcmListAdapter adapter;
    private List<TcmListBean.DataBean> list;//血压数据
    private List<TcmListBean.DataBean> tempList;//上拉加载数据
    private int pageIndex = 2;//上拉加载页数
    //分页结束


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTcmList();
        initRefresh();
        setTitle("中医体质");
    }

    private void initRefresh() {
        //刷新开始
        srlTcmList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlTcmList.finishRefresh(2000);
                pageIndex = 2;
                getTcmList();
            }
        });
        srlTcmList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlTcmList.finishLoadMore(2000);
                HashMap<String, Object> map = new HashMap<>();
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.TCM_LIST, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        TcmListBean followUpVisitListBean = JSONObject.parseObject(value, TcmListBean.class);
                        tempList = followUpVisitListBean.getData();
                        list.addAll(tempList);
                        Message message = getHandlerMessage();
                        message.what = GET_DATA_MORE;
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

    private void getTcmList() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", 1);
        XyUrl.okPost(XyUrl.TCM_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                TcmListBean followUpVisitListBean = JSONObject.parseObject(value, TcmListBean.class);
                list = followUpVisitListBean.getData();
                Message message = getHandlerMessage();
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
        View view = getLayoutInflater().inflate(R.layout.activity_tcm_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                list = (List<TcmListBean.DataBean>) msg.obj;
                if (list.size() != 0) {
                    adapter = new TcmListAdapter(getPageContext(), R.layout.item_tcm_list, list);
                    lvTcmList.setAdapter(adapter);
                } else {
                    llTcmContent.setBackgroundResource(R.color.white);
                    srlTcmList.setVisibility(View.GONE);
                    ivNoTcm.setVisibility(View.VISIBLE);
                }
                break;
            case GET_DATA_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
