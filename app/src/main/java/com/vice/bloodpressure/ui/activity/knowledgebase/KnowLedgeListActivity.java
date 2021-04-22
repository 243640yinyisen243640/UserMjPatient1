package com.vice.bloodpressure.ui.activity.knowledgebase;

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
import com.vice.bloodpressure.adapter.KnowledgeAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.NewsListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 知识库列表
 * 作者: LYD
 * 创建日期: 2019/4/15 10:52
 */

public class KnowLedgeListActivity extends BaseHandlerActivity {

    private static final int GET_DATA = 0x001998;
    private static final int LORD_MORE = 0x002998;

    @BindView(R.id.lv_knowledge)
    ListView lvKnowledge;
    @BindView(R.id.srl_knowledge)
    SmartRefreshLayout srlKnowledge;


    private List<NewsListBean> list;
    private List<NewsListBean> tempList;//上拉加载数据
    private int pageIndex = 2;//页码
    private KnowledgeAdapter knowledgeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        getLvData();
        initRefresh();
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_knowledge, contentLayout, false);
    }

    private void initRefresh() {
        srlKnowledge.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlKnowledge.finishRefresh(2000);
                pageIndex = 2;
                getLvData();
            }
        });
        srlKnowledge.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlKnowledge.finishLoadMore(2000);
                String id = getIntent().getStringExtra("position");
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.GET_KNOWLEDGE_LIST, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, NewsListBean.class);
                        list.addAll(tempList);
                        Message message = getHandlerMessage();
                        message.what = LORD_MORE;
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
     * 获取知识库分类列表
     */
    private void getLvData() {
        String id = getIntent().getStringExtra("position");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_KNOWLEDGE_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, NewsListBean.class);
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
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                list = (List<NewsListBean>) msg.obj;
                knowledgeAdapter = new KnowledgeAdapter(KnowLedgeListActivity.this, R.layout.item_knowledge_base_one, list);
                lvKnowledge.setAdapter(knowledgeAdapter);
                break;
            case LORD_MORE:
                pageIndex++;
                knowledgeAdapter.notifyDataSetChanged();
                break;
        }
    }
}
