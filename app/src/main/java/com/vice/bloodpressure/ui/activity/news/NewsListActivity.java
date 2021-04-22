package com.vice.bloodpressure.ui.activity.news;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.NewsListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.NewsListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.CommonAdapterClickImp;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.RxTimerUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 资讯文章列表
 * 作者: LYD
 * 创建日期: 2019/4/12 15:57
 */
public class NewsListActivity extends BaseHandlerEventBusActivity implements CommonAdapterClickImp {

    private static final int GET_DATA_SUCCESS = 0x001213;
    private static final int GET_DATA_ERROR = 0x001314;
    private static final int REFRESH_SUCCESS = 0x001415;
    private static final int REFRESH_ERROR = 0x001516;
    private static final int LOAD_MORE_SUCCESS = 0x001718;
    private static final int LOAD_MORE_ERROR = 0x001819;
    //分页结束
    private static final int COLLECT_CHANGE = 0x002020;
    @BindView(R.id.lv_news_category_list)
    ListView lvNewsCategoryList;
    @BindView(R.id.srl_news_category_list)
    SmartRefreshLayout srlNewsCategoryList;
    //分页开始
    private List<NewsListBean> newsList;
    private List<NewsListBean> nl;//上拉加载数据
    private NewsListAdapter informationAdapter;
    private int i = 2;//页码

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
        View layout = getLayoutInflater().inflate(R.layout.activity_information, contentLayout, false);
        return layout;
    }

    private void initRefresh() {
        //下拉刷新
        srlNewsCategoryList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                srlNewsCategoryList.finishRefresh(2000);
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                String id = getIntent().getStringExtra("id");
                i = 2;
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("page", 1);
                XyUrl.okPost(XyUrl.GET__PORT_INFORMATION_INFOLIST, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        newsList = JSONObject.parseArray(value, NewsListBean.class);
                        Message message = getHandlerMessage();
                        message.what = REFRESH_SUCCESS;
                        message.obj = newsList;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        Message message = getHandlerMessage();
                        message.what = REFRESH_ERROR;
                        sendHandlerMessage(message);
                    }
                });
            }
        });
        //上拉加载
        srlNewsCategoryList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlNewsCategoryList.finishRefresh(2000);
                String id = getIntent().getStringExtra("id");
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("page", i);
                XyUrl.okPost(XyUrl.GET__PORT_INFORMATION_INFOLIST, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        nl = JSONObject.parseArray(value, NewsListBean.class);
                        newsList.addAll(nl);
                        Message message = getHandlerMessage();
                        message.what = LOAD_MORE_SUCCESS;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                        Message message = getHandlerMessage();
                        message.what = LOAD_MORE_ERROR;
                        sendHandlerMessage(message);
                    }
                });
            }
        });
    }

    /**
     * 资讯文章列表
     */
    private void getLvData() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET__PORT_INFORMATION_INFOLIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                newsList = JSONObject.parseArray(value, NewsListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA_SUCCESS;
                message.obj = newsList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = GET_DATA_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    /**
     * 资讯收藏和取消收藏
     *
     * @param pos
     */
    private void setCollection(final int pos) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("actid", newsList.get(pos).getActid());
        XyUrl.okPost(XyUrl.INFORMATION_COLLECTION, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                org.json.JSONObject jsonObject = null;
                try {
                    jsonObject = new org.json.JSONObject(value);
                    String v = jsonObject.getString("msg");
                    Message msg = getHandlerMessage();
                    msg.what = COLLECT_CHANGE;
                    msg.obj = v;
                    msg.arg1 = pos;
                    sendHandlerMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA_SUCCESS:
                newsList = (List<NewsListBean>) msg.obj;
                informationAdapter = new NewsListAdapter(getPageContext(), R.layout.item_knowledge_base, newsList, this);
                lvNewsCategoryList.setAdapter(informationAdapter);
                break;
            case GET_DATA_ERROR:

                break;
            case COLLECT_CHANGE:
                String mm = (String) msg.obj;
                ToastUtils.showShort(mm);
                int pos = msg.arg1;
                if ("收藏成功".equals(mm)) {
                    newsList.get(pos).setIscollection("1");
                } else {
                    newsList.get(pos).setIscollection("2");
                }
                informationAdapter.notifyDataSetChanged();
                break;
            case LOAD_MORE_SUCCESS:
                i++;
                informationAdapter.notifyDataSetChanged();
                srlNewsCategoryList.finishLoadMore();
                break;
            case LOAD_MORE_ERROR:
                srlNewsCategoryList.finishLoadMore();
                ToastUtils.showShort("没有更多");
                break;
            case REFRESH_SUCCESS:
                informationAdapter.notifyDataSetChanged();
                srlNewsCategoryList.finishRefresh();
                break;
            case REFRESH_ERROR:
                srlNewsCategoryList.finishRefresh();
                ToastUtils.showShort("刷新失败");
                break;
            default:
                break;
        }
    }

    @Override
    public void adapterViewClick(int position) {
        setCollection(position);
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.NEWS_LOOK_COUNT_REFRESH:
                RxTimerUtils rxTimer = new RxTimerUtils();
                rxTimer.timer(1000, new RxTimerUtils.RxAction() {
                    @Override
                    public void action(long number) {
                        getLvData();
                    }
                });
                break;
        }
    }
}
