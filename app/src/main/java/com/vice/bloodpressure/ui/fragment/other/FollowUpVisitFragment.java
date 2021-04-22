package com.vice.bloodpressure.ui.fragment.other;

import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FollowUpVisitListAdapter;
import com.vice.bloodpressure.base.fragment.BaseLazyFragment;
import com.vice.bloodpressure.bean.FollowUpVisitListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述:    随访管理
 * 作者: LYD
 * 创建日期: 2019/7/29 16:49
 */
public class FollowUpVisitFragment extends BaseLazyFragment {
    private static final int GET_DATA = 10010;
    private static final int GET_DATA_MORE = 10011;
    private static final String TAG = "FollowUpVisitFragment";
    private static final int GET_NO_DATA = 30002;
    @BindView(R.id.lv_follow_up_visit)
    ListView lvFollowUpVisit;
    @BindView(R.id.srl_follow_up_visit)
    SmartRefreshLayout srlFollowUpVisit;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //分页开始
    private FollowUpVisitListAdapter adapter;
    private List<FollowUpVisitListBean.DataBean> list;//血压数据
    private List<FollowUpVisitListBean.DataBean> tempList;//上拉加载数据
    private int pageIndex = 2;//上拉加载页数
    //分页结束

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                srlFollowUpVisit.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);

                list = (List<FollowUpVisitListBean.DataBean>) msg.obj;
                String type = getArguments().getString("type");
                adapter = new FollowUpVisitListAdapter(getPageContext(), R.layout.item_follow_up_visit_list, list, type);
                lvFollowUpVisit.setAdapter(adapter);
                break;
            case GET_DATA_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                srlFollowUpVisit.finishLoadMore();
                break;
            case GET_NO_DATA:
                srlFollowUpVisit.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow_up_visit;
    }

    @Override
    protected void init(View rootView) {
        initRefresh();
    }

    /**
     * 获取列表数据
     */
    private void getFollowUpList() {
        String type = getArguments().getString("type");
        String userId = getArguments().getString("userId");
        Map<String, Object> map = new HashMap<>();
        map.put("page", 1);
        map.put("type", type);
        XyUrl.okPost(XyUrl.GET_FOLLOW_NEW, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                FollowUpVisitListBean followUpVisitListBean = JSONObject.parseObject(value, FollowUpVisitListBean.class);
                list = followUpVisitListBean.getData();
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message message = Message.obtain();
                message.what = GET_NO_DATA;
                sendHandlerMessage(message);
            }
        });

    }

    /**
     * 刷新数据
     */
    private void initRefresh() {
        //刷新开始
        srlFollowUpVisit.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlFollowUpVisit.finishRefresh(2000);
                pageIndex = 2;
                getFollowUpList();
            }
        });
        srlFollowUpVisit.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlFollowUpVisit.finishLoadMore(2000);
                String type = getArguments().getString("type");
                String userId = getArguments().getString("userId");
                HashMap<String, Object> map = new HashMap<>();
                map.put("page", pageIndex);
                map.put("type", type);
                XyUrl.okPost(XyUrl.GET_FOLLOW_NEW, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        FollowUpVisitListBean followUpVisitListBean = JSONObject.parseObject(value, FollowUpVisitListBean.class);
                        tempList = followUpVisitListBean.getData();
                        list.addAll(tempList);
                        Message message = Message.obtain();
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

    @Override
    public void loadData() {
        getFollowUpList();
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.FOLLOW_UP_VISIT_SUBMIT:
                getFollowUpList();
                break;
        }
    }


}
