package com.vice.bloodpressure.ui.activity.registration.report;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.ClottingReportListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.ClottingReportListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 描述:  凝血报告
 * 作者: LYD
 * 创建日期: 2019/11/27 15:33
 */
public class ClottingReportListActivity extends BaseHandlerActivity {
    private static final int GET_DETAIL = 10086;
    @BindView(R.id.lv)
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("凝血");
        getDetail();
    }


    private void getDetail() {
        int id = getIntent().getIntExtra("id", 0);
        HashMap map = new HashMap<>();
        map.put("bdid", id);
        XyUrl.okPost(XyUrl.GET_DETAIL_INSTRUMENT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                List<ClottingReportListBean> list = JSONObject.parseArray(msg, ClottingReportListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DETAIL;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });

    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_clotting_report_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DETAIL:
                List<ClottingReportListBean> list = (List<ClottingReportListBean>) msg.obj;
                ClottingReportListAdapter adapter = new ClottingReportListAdapter(getPageContext(), R.layout.item_clotting_report, list);
                lv.setAdapter(adapter);
                break;
        }
    }
}
