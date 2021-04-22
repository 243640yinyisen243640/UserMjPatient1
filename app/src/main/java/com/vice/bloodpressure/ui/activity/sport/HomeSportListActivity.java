package com.vice.bloodpressure.ui.activity.sport;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.HomeSportListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.HomeSportListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:运动方案列表
 * 作者: LYD
 * 创建日期: 2020/9/25 11:14
 */
public class HomeSportListActivity extends BaseHandlerActivity {
    private static final int GET_SPORT_LIST = 10010;
    private static final int GET_SPORT_LIST_ERROR = 30002;
    @BindView(R.id.rv_sport_list)
    RecyclerView rvSportList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("运动记录");
        getTvSave().setText("运动方案");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击去答题 获取运动方案
                startActivity(new Intent(getPageContext(), HomeSportQuestionActivity.class));
            }
        });

        getSportList();
    }

    private void getSportList() {
        HashMap<String, Object> map = new HashMap<>();
        XyUrl.okPost(XyUrl.INDEX_SPORT_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<HomeSportListBean> sportListBean = JSONObject.parseArray(value, HomeSportListBean.class);
                Message message = Message.obtain();
                message.what = GET_SPORT_LIST;
                message.obj = sportListBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_SPORT_LIST_ERROR);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_home_sport_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SPORT_LIST:
                rvSportList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                List<HomeSportListBean> list = (List<HomeSportListBean>) msg.obj;
                if (list != null && list.size() > 0) {
                    rvSportList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                    rvSportList.setAdapter(new HomeSportListAdapter(getPageContext(), list));
                }
                break;
            case GET_SPORT_LIST_ERROR:
                rvSportList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }
}