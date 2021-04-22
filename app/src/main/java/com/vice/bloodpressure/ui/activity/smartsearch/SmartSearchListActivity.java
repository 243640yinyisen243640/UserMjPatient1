package com.vice.bloodpressure.ui.activity.smartsearch;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FastAskListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.NewsListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.RxTimerUtils;

import java.util.HashMap;
import java.util.List;

import static com.vice.bloodpressure.utils.ListCastUtils.castList;

/**
 * 描述: 智能搜索列表
 * 作者: LYD
 * 创建日期: 2020/4/22 10:04
 */
public class SmartSearchListActivity extends BaseHandlerEventBusActivity {
    private static final int GET_DATA = 0x001213;
    private static final int REFRESH = 0x001415;
    private static final int LOAD_MORE = 0x001718;
    private static final String TAG = "SmartSearchListActivity";
    private List<NewsListBean> newsList;
    private List<NewsListBean> fastList;
    private FastAskListAdapter fastAdapter;
    private int i = 2;
    private ListView lvNewsCategoryList;
    private SmartRefreshLayout srlNewsCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能搜索");
        initViews();
        getData();
    }

    private void initViews() {
        lvNewsCategoryList = findViewById(R.id.lv_news_category_list);
        srlNewsCategoryList = findViewById(R.id.srl_news_category_list);
        //下拉刷新
        srlNewsCategoryList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                srlNewsCategoryList.finishRefresh(2000);
                int id = getIntent().getIntExtra("id", 0);
                i = 2;
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("page", 1);
                XyUrl.okPost(XyUrl.VISITINFOS, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        newsList = JSONObject.parseArray(value, NewsListBean.class);
                        Message message = getHandlerMessage();
                        message.what = REFRESH;
                        message.obj = newsList;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        });
        //上拉加载
        srlNewsCategoryList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlNewsCategoryList.finishLoadMore(2000);
                int id = getIntent().getIntExtra("id", 0);
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("page", i);
                XyUrl.okPost(XyUrl.VISITINFOS, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        fastList = JSONObject.parseArray(value, NewsListBean.class);
                        newsList.addAll(fastList);
                        Message message = getHandlerMessage();
                        message.what = LOAD_MORE;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        });
    }


    /**
     * 快速问诊
     */
    private void getData() {
        int id = getIntent().getIntExtra("id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.VISITINFOS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                newsList = JSONObject.parseArray(value, NewsListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = newsList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_information, contentLayout, false);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
            case REFRESH:
                newsList = castList(msg.obj, NewsListBean.class);
                fastAdapter = new FastAskListAdapter(getPageContext(), R.layout.item_knowledge_base_one, newsList);
                lvNewsCategoryList.setAdapter(fastAdapter);
                break;
            case LOAD_MORE:
                i++;
                fastAdapter.notifyDataSetChanged();
                srlNewsCategoryList.finishLoadMore();
                break;
            default:
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.SMART_LOOK_COUNT_REFRESH:
                RxTimerUtils rxTimer = new RxTimerUtils();
                rxTimer.timer(1000, new RxTimerUtils.RxAction() {
                    @Override
                    public void action(long number) {
                        getData();
                    }
                });
                break;
        }
    }
}
