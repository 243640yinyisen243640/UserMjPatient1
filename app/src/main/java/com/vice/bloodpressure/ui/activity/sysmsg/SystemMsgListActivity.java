package com.vice.bloodpressure.ui.activity.sysmsg;

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
import com.vice.bloodpressure.adapter.SystemMessageAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.SystemMsgListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.vice.bloodpressure.constant.ConstantParam.NO_DATA;

/**
 * 描述: 系统消息
 * 作者: LYD
 * 创建日期: 2019/4/11 10:18
 */

public class SystemMsgListActivity extends BaseHandlerActivity {
    private static final int GET_LV_DATA = 10010;
    private static final int GET_MORE_DATA = 10011;
    private static final String TAG = "SystemMsgListActivity";
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    //分页开始
    private SystemMessageAdapter adapter;
    //总数据
    private List<SystemMsgListBean> list;
    //上拉加载数据
    private List<SystemMsgListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.message_notification));
        getLvData();
        initRefresh();
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_system_msg_list, contentLayout, false);
        return layout;
    }


    private void getLvData() {
        HashMap map = new HashMap<>();
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_SHOW_MESSAGE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, SystemMsgListBean.class);
                Message msg = getHandlerMessage();
                msg.obj = list;
                msg.what = GET_LV_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(NO_DATA);
            }
        });
    }

    private void getMoreData() {
        HashMap map = new HashMap<>();
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_SHOW_MESSAGE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, SystemMsgListBean.class);
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

    private void initRefresh() {
        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlList.finishRefresh(2000);
                pageIndex = 2;
                getLvData();
            }
        });
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlList.finishLoadMore(2000);
                getMoreData();
            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_LV_DATA:
                //lvAddMsg.setAdapter(new AddMsgListAdapter(getPageContext(), R.layout.item_add_msg, addMsgList));
                //lvSysMsg.setAdapter(new SystemMsgListAdapter(getPageContext(), R.layout.item_sys_msg, smsMsgList));
                llEmpty.setVisibility(View.GONE);
                srlList.setVisibility(View.VISIBLE);
                list = (List<SystemMsgListBean>) msg.obj;
                adapter = new SystemMessageAdapter(list);
                rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvList.setAdapter(adapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case NO_DATA:
                llEmpty.setVisibility(View.VISIBLE);
                srlList.setVisibility(View.GONE);
                break;
        }
    }
}
