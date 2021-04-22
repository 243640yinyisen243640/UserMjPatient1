package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MyReportListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  我的报告
 * 作者: LYD
 * 创建日期: 2020/4/16 17:18
 */
public class MyReportListActivity extends BaseHandlerActivity {
    @BindView(R.id.lv_my_report)
    ListView lvMyReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的报告");
        setAdapter();
    }

    private void setAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i + "");
        }
        MyReportListAdapter adapter = new MyReportListAdapter(getPageContext(), R.layout.item_my_report_list, list);
        lvMyReport.setAdapter(adapter);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_my_report_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
