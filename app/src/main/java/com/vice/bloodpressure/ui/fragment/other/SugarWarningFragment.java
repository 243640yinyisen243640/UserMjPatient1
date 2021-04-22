package com.vice.bloodpressure.ui.fragment.other;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.WarningAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.WarningMessageBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yicheng on 2018/6/4.
 * <p>
 * 血糖预警
 */

public class SugarWarningFragment extends BaseFragment {

    private static final int GET_DATA = 0x6998;
    private ListView lvSugarWarning;
    private Context mContext;
    private LoginBean user;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                List<WarningMessageBean> wmList = (List<WarningMessageBean>) msg.obj;
                WarningAdapter warningAdapter = new WarningAdapter(mContext, wmList, "sugar");
                lvSugarWarning.setAdapter(warningAdapter);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        user = (LoginBean) SharedPreferencesUtils.getBean(mContext, SharedPreferencesUtils.USER_INFO);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sugar_warning;
    }

    @Override
    protected void init(View view) {
        lvSugarWarning = view.findViewById(R.id.lv_sugar_warning);
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", user.getToken());
        XyUrl.okPost(XyUrl.GET_BLOOD_TWORNING, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {

                List<WarningMessageBean> warningMessageList = JSONObject.parseArray(value, WarningMessageBean.class);
                Message msg = Message.obtain();
                msg.obj = warningMessageList;
                msg.what = GET_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }
}
