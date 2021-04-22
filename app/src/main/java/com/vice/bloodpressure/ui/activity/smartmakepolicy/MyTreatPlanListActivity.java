package com.vice.bloodpressure.ui.activity.smartmakepolicy;

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
import com.vice.bloodpressure.adapter.MyTreatPlanAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MyTreatPlanBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 我的治疗方案
 * 作者: LYD
 * 创建日期: 2019/3/29 17:50
 */
public class MyTreatPlanListActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final int GET_MORE_DATA = 10011;
    private static final int NO_DATA = 30002;
    @BindView(R.id.lv_my_treat_plan)
    ListView lvMyTreatPlan;
    @BindView(R.id.srl_my_treat_plan)
    SmartRefreshLayout srlMyTreatPlan;
    @BindView(R.id.iv_no_treat_plan)
    ImageView ivNoTreatPlan;
    @BindView(R.id.ll_treat_plan_content)
    LinearLayout llNoTreatPlan;
    //分页开始
    private MyTreatPlanAdapter adapter;
    //总数据
    private List<MyTreatPlanBean> list;
    //上拉加载数据
    private List<MyTreatPlanBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");
        switch (type) {
            case "0":
                setTitle("个性化降压方案");
                break;
            case "1":
                setTitle("糖尿病自我管理处方");
                break;
            case "2":
                setTitle("糖尿病病足管理处方");
                break;
            case "3":
                setTitle("运动周报");
                break;
            case "4":
                setTitle("减重自我管理处方");
                break;
        }
        getLvData(type);
        initRefresh();
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_my_treat_plan_list, contentLayout, false);
        return layout;
    }


    private void getLvData(String type) {
        HashMap map = new HashMap<>();
        map.put("page", 1);
        String url = "";
        switch (type) {
            case "0":
                url = XyUrl.PLAN_GET_PLAN_LIST;
                break;
            case "1":
                url = XyUrl.GET_DOCTOR_PROFESSION_LIST;
                break;
            case "2":
                url = XyUrl.GET_BLOOD_GLUCOSE_FOLLOW;
                break;
            case "3":
                url = XyUrl.SPORT_REPORT_LIST;
                break;
            case "4":
                url = XyUrl.GET_LOSE_WEIGHT_LIST;
                break;
        }
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, MyTreatPlanBean.class);
                Message msg = getHandlerMessage();
                msg.obj = list;
                msg.what = GET_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(NO_DATA);
            }
        });
    }

    private void getMoreData(String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        String url = "";
        switch (type) {
            case "0":
                url = XyUrl.PLAN_GET_PLAN_LIST;
                break;
            case "1":
                url = XyUrl.GET_DOCTOR_PROFESSION_LIST;
                break;
            case "2":
                url = XyUrl.GET_BLOOD_GLUCOSE_FOLLOW;
                break;
            case "3":
                url = XyUrl.SPORT_REPORT_LIST;
                break;
            case "4":
                url = XyUrl.GET_LOSE_WEIGHT_LIST;
                break;
        }
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, MyTreatPlanBean.class);
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
        srlMyTreatPlan.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlMyTreatPlan.finishRefresh(2000);
                pageIndex = 2;
                String type = getIntent().getStringExtra("type");
                getLvData(type);
            }
        });
        srlMyTreatPlan.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlMyTreatPlan.finishLoadMore(2000);
                String type = getIntent().getStringExtra("type");
                getMoreData(type);
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                list = (List<MyTreatPlanBean>) msg.obj;
                String type = getIntent().getStringExtra("type");
                adapter = new MyTreatPlanAdapter(getPageContext(), R.layout.item_my_treat_plan, list, type);
                lvMyTreatPlan.setAdapter(adapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case NO_DATA:
                llNoTreatPlan.setBackgroundResource(R.color.white);
                srlMyTreatPlan.setVisibility(View.GONE);
                ivNoTreatPlan.setVisibility(View.VISIBLE);
                break;
        }
    }
}
