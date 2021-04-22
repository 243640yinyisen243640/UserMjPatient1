package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MedicineSelectListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.MedicineSelectListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 药物选择列表
 * 作者: LYD
 * 创建日期: 2019/3/30 11:23
 */
public class MedicineSelectListActivity extends BaseHandlerActivity {

    private static final int GET_DATA = 10010;
    @BindView(R.id.lv_medicine_select)
    ListView lvMedicineSelect;
    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("药物选择");
        getLvData();
    }


    private void getLvData() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        XyUrl.okPost(XyUrl.GET_DRUGS_GROUP, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MedicineSelectListBean data = JSONObject.parseObject(value, MedicineSelectListBean.class);
                Message msg = getHandlerMessage();
                msg.obj = data;
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
        View layout = getLayoutInflater().inflate(R.layout.activity_medicine_select_list, contentLayout, false);
        return layout;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                MedicineSelectListBean data = (MedicineSelectListBean) msg.obj;
                String groupTitle = data.getGrouptitle();
                tvMsg.setText(groupTitle);
                List<MedicineSelectListBean.GroupsBean> groupList = data.getGroups();
                if (groupList != null && groupList.size() > 0) {
                    MedicineSelectListAdapter adapter = new MedicineSelectListAdapter(getPageContext(), R.layout.item_medicine_select_list, groupList);
                    lvMedicineSelect.setAdapter(adapter);
                }
                break;
        }
    }
}
