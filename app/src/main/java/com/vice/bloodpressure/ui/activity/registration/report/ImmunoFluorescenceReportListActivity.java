package com.vice.bloodpressure.ui.activity.registration.report;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.ImmunoFluorescenceReportAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.ImmunoFluorescenceReportListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 描述: 免疫荧光
 * 作者: LYD
 * 创建日期: 2019/11/27 16:01
 */
public class ImmunoFluorescenceReportListActivity extends BaseHandlerActivity {
    private static final int GET_DETAIL = 10010;
    @BindView(R.id.lv)
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("免疫荧光");
        getDetail();
    }

    /**
     * 获取详情
     */
    private void getDetail() {
        int id = getIntent().getIntExtra("id", 0);
        HashMap map = new HashMap<>();
        map.put("icid", id);
        XyUrl.okPost(XyUrl.GET_DETAIL_IMMUNE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                List<ImmunoFluorescenceReportListBean> list = JSONObject.parseArray(msg, ImmunoFluorescenceReportListBean.class);
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
        View view = getLayoutInflater().inflate(R.layout.activity_immuno_fluorescence_report_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DETAIL:
                List<ImmunoFluorescenceReportListBean> list = (List<ImmunoFluorescenceReportListBean>) msg.obj;
                ImmunoFluorescenceReportAdapter adapter = new ImmunoFluorescenceReportAdapter(getPageContext(), R.layout.item_immuno_fluorescence_report, list);
                lv.setAdapter(adapter);
                break;
        }
    }
}
