package com.vice.bloodpressure.ui.activity.mydevice;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MyDeviceListAdapter;
import com.vice.bloodpressure.base.activity.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MyDeviceListActivity extends BaseActivity {
    @BindView(R.id.rv_device_list)
    RecyclerView rvDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择设备");
        setRv();
    }

    private void setRv() {
        //设置文字
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.my_device_list_name);
        List<String> list = Arrays.asList(stringArray);
        rvDeviceList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvDeviceList.setAdapter(new MyDeviceListAdapter(list));
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_my_device_list, contentLayout, false);
        return view;
    }
}