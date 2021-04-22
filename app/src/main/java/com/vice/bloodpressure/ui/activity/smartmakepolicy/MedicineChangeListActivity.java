package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MedicineChangeListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MedicineChangeListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.CommonAdapterClickImp;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 药物更换列表
 * 作者: LYD
 * 创建日期: 2019/4/1 14:19
 */
public class MedicineChangeListActivity extends BaseHandlerActivity implements CommonAdapterClickImp {

    private static final int GET_DATA = 10010;
    private static final String TAG = "MedicineChangeActivity";
    @BindView(R.id.lv)
    ListView lv;

    private List<MedicineChangeListBean> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);//注册
        setTitle("更换药物");
        getLvData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.removeAllStickyEvents();//移除黏性事件
        EventBusUtils.unregister(this);//取消注册
    }

    /**
     * 接收黏性事件,多了个属性sticky,改为true方式启动黏性事件
     *
     * @param eventStickyMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EventBusSticky(EventMessage eventStickyMessage) {
        switch (eventStickyMessage.getCode()) {
            case ConstantParam.SEND_GROUP_ID:
                String groupId = eventStickyMessage.getMsg();
                SPStaticUtils.put("groupId", groupId);
                break;
        }
    }


    private void getLvData() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        map.put("listid", getIntent().getStringExtra("listId"));
        XyUrl.okPost(XyUrl.GET_DRUGS_OTHER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<MedicineChangeListBean> list = JSONObject.parseArray(value, MedicineChangeListBean.class);
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
        View layout = getLayoutInflater().inflate(R.layout.activity_medicine_change_list, contentLayout, false);
        return layout;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                data = (List<MedicineChangeListBean>) msg.obj;
                if (data != null && data.size() > 0) {
                    lv.setAdapter(new MedicineChangeListAdapter(getPageContext(), R.layout.item_medicine_select_list, data, this));
                }
                break;
        }
    }

    @Override
    public void adapterViewClick(int position) {
        DialogUtils.getInstance().showDialog(getPageContext(), "更换", "确定要更换药物?", true, new DialogUtils.DialogCallBack() {
            @Override
            public void execEvent() {
                change(position);
            }
        });
    }

    /**
     * 换药
     *
     * @param position
     */
    private void change(int position) {
        HashMap map = new HashMap<>();
        String pid = SPStaticUtils.getString("pid");
        String groupId = SPStaticUtils.getString("groupId");
        map.put("paid", pid);
        map.put("id", getIntent().getStringExtra("id"));
        map.put("chid", data.get(position).getId());
        map.put("groupid", groupId);
        XyUrl.okPostSave(XyUrl.DRUGS_CHANGE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                //发送
                EventBusUtils.post(new EventMessage<>(ConstantParam.CHANGE_MEDICINE));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }
}
