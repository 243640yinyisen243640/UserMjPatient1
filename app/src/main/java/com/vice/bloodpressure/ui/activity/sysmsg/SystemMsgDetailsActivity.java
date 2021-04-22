package com.vice.bloodpressure.ui.activity.sysmsg;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.ShowMessageBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 描述: 系统消息详情
 * 作者: LYD
 * 创建日期: 2019/4/12 10:32
 */

public class SystemMsgDetailsActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    @BindView(R.id.tv_sys_title)
    TextView tvSysTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("信息内容");
        getData();
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_system_details, contentLayout, false);
        return layout;
    }

    /**
     * 信息详情内容
     */
    private void getData() {
        String id = getIntent().getStringExtra("id");
        HashMap map = new HashMap<>();
        map.put("pid", id);
        XyUrl.okPost(XyUrl.GET_PORT_MESSAGE_ALTERMESSAGE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ShowMessageBean showMessage = JSONObject.parseObject(value, ShowMessageBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = showMessage;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                ShowMessageBean showMessage = (ShowMessageBean) msg.obj;
                tvSysTitle.setText(showMessage.getTitle());
                tvTime.setText(showMessage.getSendtime());
                tvContent.setText("\u3000\u3000" + showMessage.getContent());
                break;
        }
    }
}
