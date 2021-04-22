package com.vice.bloodpressure.ui.activity.smarteducation;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SmartEducationHaveLearnClassListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.SmartEducationHaveLearnClassListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 描述:  智能教育之 已学课程 列表
 * 作者: LYD
 * 创建日期: 2020/8/19 16:51
 */
public class SmartEducationHaveLearnClassListActivity extends BaseHandlerActivity {
    private static final int GET_HAVE_LEARN_CLASS_LIST_DATA = 10010;
    private static final int GET_HAVE_LEARN_CLASS_LIST_NO_DATA = 10011;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.rv_have_learn_class)
    RecyclerView rvHaveLearnClass;
    @BindView(R.id.srl_have_learn_class)
    SmartRefreshLayout srlHaveLearnClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("已学课程");
        getHaveLearnClassList();
    }


    /**
     * 获取已学课程
     */
    private void getHaveLearnClassList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "2");
        XyUrl.okPost(XyUrl.SMART_EDUCATION_LEARN_TIME, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationHaveLearnClassListBean> list = JSONObject.parseArray(value, SmartEducationHaveLearnClassListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_HAVE_LEARN_CLASS_LIST_DATA;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_HAVE_LEARN_CLASS_LIST_NO_DATA);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_education_have_learn_class_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_HAVE_LEARN_CLASS_LIST_DATA:
                llEmpty.setVisibility(View.GONE);
                srlHaveLearnClass.setVisibility(View.VISIBLE);
                List<SmartEducationHaveLearnClassListBean> list = (List<SmartEducationHaveLearnClassListBean>) msg.obj;
                SmartEducationHaveLearnClassListAdapter adapter = new SmartEducationHaveLearnClassListAdapter(list, getPageContext());
                rvHaveLearnClass.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvHaveLearnClass.setAdapter(adapter);
                break;
            case GET_HAVE_LEARN_CLASS_LIST_NO_DATA:
                llEmpty.setVisibility(View.VISIBLE);
                srlHaveLearnClass.setVisibility(View.GONE);
                break;
        }

    }
}