package com.vice.bloodpressure.ui.fragment.smarteducation;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SmartEducationLearnListAdapter;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.SmartEducationLearnListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationHaveLearnClassListActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationLearnTimeActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 智能教育之学习列表
 * 作者: LYD
 * 创建日期: 2020/8/12 9:27
 */
public class SmartEducationLearnListFragment extends BaseEventBusFragment {
    private static final int GET_DATA = 10010;
    private static final int LORD_MORE = 10011;
    private static final int GET_NO_DATA = 30002;
    private static final int GET_DATA_30038 = 30038;
    @BindView(R.id.tv_learn_total_time)
    TextView tvLearnTotalTime;
    @BindView(R.id.tv_learn_course)
    TextView tvLearnCourse;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.srl_learn_list)
    SmartRefreshLayout srlLearnList;
    @BindView(R.id.rv_learn_list)
    RecyclerView rvLearnList;

    private List<SmartEducationLearnListBean.ListBean> list;
    private List<SmartEducationLearnListBean.ListBean> tempList;
    private int pageIndex = 2;
    private SmartEducationLearnListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_education_learn_list;
    }

    @Override
    protected void init(View rootView) {
        initRefresh();
        getLearnList();
    }

    private void initRefresh() {
        srlLearnList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlLearnList.finishRefresh(2000);
                pageIndex = 2;
                getLearnList();
            }
        });
        srlLearnList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlLearnList.finishLoadMore(2000);
                HashMap<String, Object> map = new HashMap<>();
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.SMART_EDUCATION_LEARN_LIST, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        SmartEducationLearnListBean dataBean = JSONObject.parseObject(value, SmartEducationLearnListBean.class);
                        tempList = dataBean.getList();
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

    @OnClick({R.id.ll_learn_total_time, R.id.ll_learn_course})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_learn_total_time:
                startActivity(new Intent(getPageContext(), SmartEducationLearnTimeActivity.class));
                break;
            case R.id.ll_learn_course:
                startActivity(new Intent(getPageContext(), SmartEducationHaveLearnClassListActivity.class));
                break;
        }
    }

    private void getLearnList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", 1);
        XyUrl.okPostGetErrorData(XyUrl.SMART_EDUCATION_LEARN_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SmartEducationLearnListBean dataBean = JSONObject.parseObject(value, SmartEducationLearnListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = dataBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (error == 30002) {
                    sendHandlerMessage(GET_NO_DATA);
                } else {
                    SmartEducationLearnListBean dataBean = JSONObject.parseObject(errorMsg, SmartEducationLearnListBean.class);
                    Message message = getHandlerMessage();
                    message.what = GET_DATA_30038;
                    message.obj = dataBean;
                    sendHandlerMessage(message);
                }
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                srlLearnList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                SmartEducationLearnListBean data = (SmartEducationLearnListBean) msg.obj;
                list = data.getList();
                SmartEducationLearnListBean.StatisticalBean statistical = data.getStatistical();
                int classNum = statistical.getClassnum();
                int learnTime = statistical.getLearntime();
                tvLearnTotalTime.setText(learnTime + "分钟");
                tvLearnCourse.setText(classNum + "课程");
                adapter = new SmartEducationLearnListAdapter(list, getPageContext());
                rvLearnList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvLearnList.setAdapter(adapter);
                break;
            case GET_DATA_30038:
                SmartEducationLearnListBean data30038 = (SmartEducationLearnListBean) msg.obj;
                SmartEducationLearnListBean.StatisticalBean statistical30038 = data30038.getStatistical();
                int classNum30038 = statistical30038.getClassnum();
                int learnTime30038 = statistical30038.getLearntime();
                tvLearnTotalTime.setText(learnTime30038 + "分钟");
                tvLearnCourse.setText(classNum30038 + "课程");

                srlLearnList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
            case LORD_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case GET_NO_DATA:
                srlLearnList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.SMART_EDUCATION_COURSE_REFRESH:
                getLearnList();
                break;
        }
    }
}
