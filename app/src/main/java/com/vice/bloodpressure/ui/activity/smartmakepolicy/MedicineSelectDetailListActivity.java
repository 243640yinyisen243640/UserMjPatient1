package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MedicineSelectDetailListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MedicineSelectDetailListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 药物选择详情
 * 作者: LYD
 * 创建日期: 2019/3/30 16:20
 */
public class MedicineSelectDetailListActivity extends BaseHandlerActivity {

    private static final int GET_DATA = 10010;
    private static final String TAG = "LYD";
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv_msg)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
        setTitle("药物选择");
        getLvData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventMessage event) {
        switch (event.getCode()) {
            case ConstantParam.CHANGE_MEDICINE:
                getLvData();
                break;
        }
    }

    private void getLvData() {
        HashMap map = new HashMap<>();
        String pid = SPStaticUtils.getString("pid");
        map.put("id", getIntent().getStringExtra("id"));
        map.put("paid", pid);
        XyUrl.okPost(XyUrl.GET_DRUGS_GROUP_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MedicineSelectDetailListBean data = JSONObject.parseObject(value, MedicineSelectDetailListBean.class);
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
        View layout = getLayoutInflater().inflate(R.layout.activity_medicine_select_detail_list, contentLayout, false);
        return layout;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                MedicineSelectDetailListBean data = (MedicineSelectDetailListBean) msg.obj;
                String groupTitle = data.getDrugtitle();
                tvTitle.setText(groupTitle);
                List<MedicineSelectDetailListBean.DrugsBean> drugList = data.getDrugs();
                if (drugList != null && drugList.size() > 0) {
                    MedicineSelectDetailListAdapter adapter = new MedicineSelectDetailListAdapter(getPageContext(), R.layout.item_medicine_select_list, drugList);
                    lv.setAdapter(adapter);
                }
                break;
        }
    }
}
