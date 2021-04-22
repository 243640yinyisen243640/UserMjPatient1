package com.lyd.modulemall.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.MoneyUtils;
import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.PayTypeSelectAdapter;
import com.lyd.modulemall.bean.AliPayBean;
import com.lyd.modulemall.bean.OrderPayBean;
import com.lyd.modulemall.bean.PayResult;
import com.lyd.modulemall.bean.WeChatPayBean;
import com.lyd.modulemall.databinding.ActivityOrderPayBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.functions.Consumer;
import liys.click.AClickStr;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 选择支付方式
 * 作者: LYD
 * 创建日期: 2021/1/28 16:26
 */
@BindEventBus
public class OrderPayActivity extends BaseViewBindingActivity<ActivityOrderPayBinding> {
    private static final String TAG = "OrderPayActivity";
    private static final int SDK_PAY_FLAG = 10010;
    private String order_tag;
    private String cart_ids;

    private String goods_id;
    private String goods_sku_id;
    private String num;
    private String leave_message;
    private String couon_user_id;
    private String addressId;
    private String order_id;
    private String type;
    private String payType = "0";

    private long countDownTime;

    Handler countDownTimeHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (countDownTime > 0) {
                countDownTime -= 1;//倒计时总时间减1
                binding.tvCountDown.setText("剩余" + secondToTime(countDownTime) + "，订单自动关闭...");
                countDownTimeHandler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("订单支付");
        setRvType();
        type = getIntent().getExtras().getString("type");
        if ("1".equals(type)) {
            order_tag = getIntent().getExtras().getString("order_tag");
            Log.e(TAG, "order_tag==" + order_tag);
            if ("cart".equals(order_tag)) {
                //购物车下单
                cart_ids = getIntent().getExtras().getString("cart_ids");
                Log.e(TAG, "cart_ids==" + cart_ids);
            } else {
                //直接购买
                goods_id = getIntent().getExtras().getString("goods_id");
                Log.e(TAG, "goods_id==" + goods_id);
                goods_sku_id = getIntent().getExtras().getString("goods_sku_id");
                Log.e(TAG, "goods_sku_id==" + goods_sku_id);
                num = getIntent().getExtras().getString("num");
                Log.e(TAG, "num==" + num);
            }
            leave_message = getIntent().getExtras().getString("leave_message");
            couon_user_id = getIntent().getExtras().getString("couon_user_id");
            addressId = getIntent().getExtras().getString("addressId");
        } else {
            order_id = getIntent().getExtras().getString("order_id");
        }
        toCreateOrder();
    }


    private void setRvType() {
        List<String> list = new ArrayList<>();
        list.add("微信");
        list.add("支付宝");
        //注册选择器
        SingleCheckHelper singleCheckHelper = new SingleCheckHelper();
        singleCheckHelper.register(String.class, new CheckHelper.Checker<String, BaseViewHolder>() {
            @Override
            public void check(String bean, BaseViewHolder holder) {
                holder.setImageResource(R.id.img_check, R.drawable.pay_list_checked);
                int position = holder.getLayoutPosition();
                payType = position + 1 + "";
            }

            @Override
            public void unCheck(String bean, BaseViewHolder holder) {
                holder.setImageResource(R.id.img_check, R.drawable.pay_list_uncheck);
            }
        });
        PayTypeSelectAdapter payTypeSelectAdapter = new PayTypeSelectAdapter(list, singleCheckHelper);
        binding.rvPayList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        binding.rvPayList.setAdapter(payTypeSelectAdapter);
    }

    /**
     * 生成订单
     */
    private void toCreateOrder() {
        HashMap map = new HashMap<>();
        String url = "";
        if (type.equals("1")) {
            url = MallUrl.CREATE_ORDER;
            map.put("id", addressId);
            map.put("order_tag", getIntent().getExtras().getString("order_tag"));
            if ("cart".equals(order_tag)) {
                //购物车下单
                map.put("cart_ids", cart_ids);
            } else {
                //直接购买
                map.put("goods_sku_id", goods_sku_id);
                map.put("goods_id", goods_id);
                map.put("num", num);
            }
            map.put("leave_message", leave_message);
            map.put("couon_user_id", couon_user_id);
        } else if (type.equals("2")) {
            url = MallUrl.GET_ORDER_PAY_DATA;
            map.put("order_id", order_id);
            map.put("type", type);
        } else {
            return;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(OrderPayBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<OrderPayBean>() {
                    @Override
                    public void accept(OrderPayBean data) throws Exception {
                        order_id = data.getOrder_id();
                        double pay_money = data.getPay_money();
                        binding.tvPayMoney.setText(MoneyUtils.formatPrice(pay_money));
                        countDownTime = data.getCount_down();
                        binding.tvCountDown.setText("剩余" + secondToTime(countDownTime) + "，订单自动关闭...");
                        countDownTimeHandler.postDelayed(runnable, 1000);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @AClickStr({"tv_pay"})
    public void click(View view, String viewId) {
        switch (viewId) {
            case "tv_pay":
                if ("0".equals(payType)) {
                    ToastUtils.showShort("请选择支付方式");
                    return;
                } else if ("1".equals(payType)) {
                    toUseWeChatPay();
                } else if ("2".equals(payType)) {
                    toUseAliPay();
                }
                break;
        }
    }

    /**
     * 使用阿里支付
     */
    private void toUseAliPay() {
        HashMap map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("pay_type", payType);
        map.put("type", type);
        RxHttp.postForm(MallUrl.ORDER_PAY)
                .addAll(map)
                .asResponse(AliPayBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<AliPayBean>() {
                    @Override
                    public void accept(AliPayBean data) throws Exception {
                        String orderInfo = data.getAlipay_param();
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(OrderPayActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                Log.e(TAG, "App 支付同步通知参数说明" + result.toString());
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                handler.sendMessage(msg);
                            }
                        };
                        //必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 使用微信支付
     */
    private void toUseWeChatPay() {
        HashMap map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("pay_type", payType);
        map.put("type", type);
        RxHttp.postForm(MallUrl.ORDER_PAY)
                .addAll(map)
                .asResponse(WeChatPayBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<WeChatPayBean>() {
                    @Override
                    public void accept(WeChatPayBean data) throws Exception {
                        WeChatPayBean.PayParametersBean payParameters = data.getPayParameters();
                        IWXAPI iwxapi = WXAPIFactory.createWXAPI(getPageContext(), payParameters.getAppid());
                        PayReq request = new PayReq();
                        //Appid必须要传
                        request.appId = payParameters.getAppid();
                        //固定值，必须要传
                        request.packageValue = payParameters.getPackageX();
                        //后台返回的时间戳，必须要传
                        request.timeStamp = payParameters.getTimestamp();
                        //后台返回的，必须要传
                        request.prepayId = payParameters.getPrepayid();
                        //后台返回的，必须要传
                        request.partnerId = payParameters.getPartnerid();
                        //后台返回的，必须要传
                        request.nonceStr = payParameters.getNoncestr();
                        //后台返回的，必须要传
                        request.sign = payParameters.getSign();
                        //拉起支付
                        iwxapi.sendReq(request);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @SuppressWarnings("all")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Log.e(TAG, "支付宝支付开始回调");
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    //判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.e(TAG, "支付宝支付成功回调");
                        Intent intent = new Intent(getPageContext(), PayDetailActivity.class);
                        intent.putExtra("type", "success");
                        intent.putExtra("order_id", order_id);
                        startActivity(intent);
                    } else {
                        Log.e(TAG, "支付宝支付失败回调");
                        Intent intent = new Intent(getPageContext(), PayDetailActivity.class);
                        intent.putExtra("type", "failed");
                        intent.putExtra("order_id", order_id);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    /**
     * 将秒数转换为日时分秒，
     *
     * @param second
     * @return
     */
    public String secondToTime(long second) {
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        second = second % 60;                //剩余秒数
        if (days > 0) {
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        } else {
            return hours + "小时" + minutes + "分" + second + "秒";
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        Intent intent = null;
        switch (event.getCode()) {
            case BaseConstantParam.EventCode.WE_CHAT_PAY_SUCCESS:
                intent = new Intent(getPageContext(), PayDetailActivity.class);
                intent.putExtra("type", "success");
                intent.putExtra("order_id", order_id);
                startActivity(intent);
                break;
            case BaseConstantParam.EventCode.WE_CHAT_PAY_FAILED:
                intent = new Intent(getPageContext(), PayDetailActivity.class);
                intent.putExtra("type", "failed");
                intent.putExtra("order_id", order_id);
                startActivity(intent);
                break;
        }
    }
}