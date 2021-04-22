package com.vice.bloodpressure.ui.activity.news;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.NewsCategoryListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.NewsCategoryListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 资讯分类
 * 作者: LYD
 * 创建日期: 2019/4/12 15:57
 */

public class NewsCategoryListActivity extends BaseHandlerActivity {

    private static final int GET_LV_DATA = 10010;
    private static final int GET_MORE_DATA = 10011;

    @BindView(R.id.lv_news_category_list)
    ListView lvNewsCategoryList;
    @BindView(R.id.srl_news_category_list)
    SmartRefreshLayout srlNewsCategoryList;
    //分页开始
    private List<NewsCategoryListBean> newsList;
    private List<NewsCategoryListBean> nList;
    private NewsCategoryListAdapter informationAdapter;
    private int pageIndex = 2;
    //分页结束


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("健康资讯");
        getLvData();
        initRefresh();
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_information, contentLayout, false);
        return layout;
    }

    private void initRefresh() {
        //下拉刷新
        srlNewsCategoryList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                srlNewsCategoryList.finishRefresh(2000);
                pageIndex = 2;
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                HashMap<String, Object> map = new HashMap<>();
                map.put("page", 1);
                map.put("docid", user.getDocid());
                XyUrl.okPost(XyUrl.GET__PORT_INFORMATION_CATEGORY, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        newsList = JSONObject.parseArray(value, NewsCategoryListBean.class);
                        Message message = getHandlerMessage();
                        message.what = GET_LV_DATA;
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
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                srlNewsCategoryList.finishLoadMore(2000);
                HashMap<String, Object> map = new HashMap<>();
                map.put("page", pageIndex);
                map.put("docid", user.getDocid());
                XyUrl.okPost(XyUrl.GET__PORT_INFORMATION_CATEGORY, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        nList = JSONObject.parseArray(value, NewsCategoryListBean.class);
                        newsList.addAll(nList);
                        Message message = getHandlerMessage();
                        message.what = GET_MORE_DATA;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        });
    }

    private void getLvData() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", 1);
        map.put("docid", user.getDocid());
        XyUrl.okPost(XyUrl.GET__PORT_INFORMATION_CATEGORY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                newsList = JSONObject.parseArray(value, NewsCategoryListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_LV_DATA;
                message.obj = newsList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_LV_DATA:
                newsList = (List<NewsCategoryListBean>) msg.obj;
                informationAdapter = new NewsCategoryListAdapter(getPageContext(), R.layout.item_news_category_list, newsList);
                lvNewsCategoryList.setAdapter(informationAdapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                informationAdapter.notifyDataSetChanged();
                break;
        }
    }
}
