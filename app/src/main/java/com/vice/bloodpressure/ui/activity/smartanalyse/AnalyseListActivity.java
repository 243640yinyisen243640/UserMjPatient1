package com.vice.bloodpressure.ui.activity.smartanalyse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.AnalyseListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:   智能分析
 * 作者: LYD
 * 创建日期: 2019/4/16 11:03
 */

public class AnalyseListActivity extends BaseHandlerActivity implements AdapterView.OnItemClickListener {
    private static final int GET_SUCCESS = 10086;
    private static final int GET_FAILED = 10087;

    @BindView(R.id.lv_smart_analyse)
    ListView lvSmartAnalyse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.report_vr));
        setAdapter();
    }

    private void setAdapter() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        lvSmartAnalyse.setAdapter(new AnalyseListAdapter(getPageContext(), R.layout.item_analyse_list, list));
        lvSmartAnalyse.setOnItemClickListener(this);
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_report, contentLayout, false);
        return layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                isHaveBloodPressureData();
                break;
            case 1:
                Intent intent = new Intent(getPageContext(), BloodSugarReportActivity.class);
                intent.putExtra("time", "");
                startActivity(intent);
                break;
        }
    }


    /**
     *
     */
    private void isHaveBloodPressureData() {
        HashMap<String, Object> map = new HashMap<>();
        XyUrl.okPostSave(XyUrl.GET_BLOOD_DATA, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(GET_SUCCESS);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_FAILED);
            }
        });

    }

    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            case GET_SUCCESS:
                intent = new Intent(getPageContext(), BloodPressureReportActivity.class);
                intent.putExtra("time", "");
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case GET_FAILED:
                intent = new Intent(getPageContext(), BloodPressureReportNoDataActivity.class);
                startActivity(intent);
                break;
        }
    }
}
