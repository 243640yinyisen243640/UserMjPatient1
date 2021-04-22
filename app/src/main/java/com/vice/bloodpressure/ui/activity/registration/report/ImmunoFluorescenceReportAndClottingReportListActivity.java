package com.vice.bloodpressure.ui.activity.registration.report;

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
import com.vice.bloodpressure.adapter.ImmunoFluorescenceReportAndClottingReportAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.ImmunoFluorescenceReportAndClottingReportBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * 描述: 免疫荧光和凝血
 * 作者: LYD
 * 创建日期: 2019/11/28 14:23
 */
public class ImmunoFluorescenceReportAndClottingReportListActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10086;
    private static final int GET_DATA_MORE = 10087;
    private static final int GET_NO_DATA = 10088;

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    //分页开始
    private ImmunoFluorescenceReportAndClottingReportAdapter adapter;
    //初始数据
    private List<ImmunoFluorescenceReportAndClottingReportBean> list;
    //上拉加载数据
    private List<ImmunoFluorescenceReportAndClottingReportBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束

    //分页结束
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        getList();
        initRefresh();
    }


    /**
     * 区分标题
     */
    private void initTitle() {
        int position = getIntent().getIntExtra("position", 0);
        if (9 == position) {
            setTitle("免疫荧光");
        } else {
            setTitle("凝血");
        }
    }

    /**
     * 获取数据
     */
    private void getList() {
        int position = getIntent().getIntExtra("position", 0);
        String ofid = getIntent().getStringExtra("ofid");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        String token = user.getToken();
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("ofid", ofid);
        map.put("page", 1);
        String url;
        if (9 == position) {
            url = XyUrl.GET_IMMUNE;
        } else {
            url = XyUrl.GET_INSTRUMENT_LIST;
        }
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, ImmunoFluorescenceReportAndClottingReportBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = GET_NO_DATA;
                sendHandlerMessage(message);
            }
        });

    }

    /**
     * 刷新
     */
    private void initRefresh() {
        //刷新开始
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
                int position = getIntent().getIntExtra("position", 0);
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                String token = user.getToken();
                String ofid = getIntent().getStringExtra("ofid");
                HashMap<String, Object> map = new HashMap<>();
                map.put("access_token", token);
                map.put("ofid", ofid);
                map.put("page", pageIndex);
                String url;
                if (9 == position) {
                    url = XyUrl.GET_IMMUNE;
                } else {
                    url = XyUrl.GET_INSTRUMENT_LIST;
                }
                XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, ImmunoFluorescenceReportAndClottingReportBean.class);
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


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_immuno_fluorescence_report_and_clotting_report_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                llEmpty.setVisibility(View.GONE);
                srlList.setVisibility(View.VISIBLE);
                int position = getIntent().getIntExtra("position", 0);
                list = (List<ImmunoFluorescenceReportAndClottingReportBean>) msg.obj;
                adapter = new ImmunoFluorescenceReportAndClottingReportAdapter(getPageContext(), R.layout.item_check_data_detail_list, list, position);
                lv.setAdapter(adapter);
                break;
            case GET_DATA_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case GET_NO_DATA:
                llEmpty.setVisibility(View.VISIBLE);
                srlList.setVisibility(View.GONE);
                break;
        }
    }
}
