package com.vice.bloodpressure.ui.activity.home;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.LittleToolsAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;

import java.util.Arrays;

import butterknife.BindView;

/**
 * 描述: 小工具列表
 * 作者: LYD
 * 创建日期: 2020/9/22 15:23
 */
public class LittleToolsListActivity extends BaseHandlerActivity {
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("小工具");
        setRv();
    }


    private void setRv() {
        rvList.setAdapter(new LittleToolsAdapter(Arrays.asList(getResources().getStringArray(R.array.little_tools_text))));
        rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_little_tools_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}