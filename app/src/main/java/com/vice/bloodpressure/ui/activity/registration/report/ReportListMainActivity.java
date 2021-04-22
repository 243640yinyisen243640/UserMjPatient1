package com.vice.bloodpressure.ui.activity.registration.report;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.ReportListMainAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.ReportListMainBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 描述:报告查询列表
 * 作者: LYD
 * 创建日期: 2019/10/30 14:34
 */
public class ReportListMainActivity extends BaseHandlerActivity {
    private static final int GET_LIST = 10010;
    private static final int GET_NO_DATA = 30002;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("报告查询");
        getList();
    }


    /**
     * 获取数据
     */
    private void getList() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_OFF_PATIENTS_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<ReportListMainBean> list = JSONObject.parseArray(value, ReportListMainBean.class);
                Message msg = getHandlerMessage();
                msg.what = GET_LIST;
                msg.obj = list;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                msg.what = GET_NO_DATA;
                sendHandlerMessage(msg);
            }
        });


    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_report_list_main, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_LIST:
                List<ReportListMainBean> list = (List<ReportListMainBean>) msg.obj;
                lvList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                ReportListMainAdapter adapter = new ReportListMainAdapter(getPageContext(), R.layout.item_report_list_main, list);
                lvList.setAdapter(adapter);
                break;
            case GET_NO_DATA:
                lvList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }
}
