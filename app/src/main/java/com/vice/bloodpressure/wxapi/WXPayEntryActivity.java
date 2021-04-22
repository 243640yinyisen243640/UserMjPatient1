package com.vice.bloodpressure.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vice.bloodpressure.R;
import com.xiaomi.mipush.sdk.Constants;

/**
 * 描述: 支付类名:WXPayEntryActivity类(包名或类名不一致会造成无法回调)
 * 作者: LYD
 * 创建日期: 2021/1/30 15:18
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    //APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxbedc85b967f57dc8";
    //IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_x_entry);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.e(TAG, "微信支付onReq");
        //finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "微信支付onResp");
        switch (resp.errCode) {
            case 0:
                Log.e(TAG, "微信支付成功回调");
                EventBusUtils.post(new EventMessage<>(BaseConstantParam.EventCode.WE_CHAT_PAY_SUCCESS));
                break;
            default:
                Log.e(TAG, "微信支付失败回调");
                EventBusUtils.post(new EventMessage<>(BaseConstantParam.EventCode.WE_CHAT_PAY_FAILED));
                break;
        }
        finish();
    }
}