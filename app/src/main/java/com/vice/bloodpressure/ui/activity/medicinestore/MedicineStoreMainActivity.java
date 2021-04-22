package com.vice.bloodpressure.ui.activity.medicinestore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MedStoreAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.MedicineStoreLevelOneBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.widget.view.decoration.GridSpacingItemDecoration;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 药品库
 * 作者: LYD
 * 创建日期: 2019/4/3 11:28
 */

public class MedicineStoreMainActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    @BindView(R.id.rv_med)
    RecyclerView rvMed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setTitle("药品库");
    }


    private void getData() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("docid", user.getDocid());
        XyUrl.okPost(XyUrl.GET_MEDICINE_LEVEL_ONE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<MedicineStoreLevelOneBean> list = JSONObject.parseArray(value, MedicineStoreLevelOneBean.class);
                Message msg = getHandlerMessage();
                msg.obj = list;
                msg.what = GET_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_new_med, contentLayout, false);
    }

    @OnClick({R.id.ll_et})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_et:
                startActivity(new Intent(getPageContext(), MedicineSearchActivity.class));
                break;
        }

    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                List<MedicineStoreLevelOneBean> mList = (List<MedicineStoreLevelOneBean>) msg.obj;
                if (mList != null && mList.size() > 0) {
                    MedStoreAdapter adapter = new MedStoreAdapter(getPageContext(), R.layout.item_med_store, mList);
                    rvMed.setLayoutManager(new GridLayoutManager(this, 3));
                    rvMed.addItemDecoration(new GridSpacingItemDecoration(3, 27, false));
                    rvMed.setAdapter(adapter);
                }
                break;
        }
    }
}
