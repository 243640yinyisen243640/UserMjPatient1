package com.lyd.modulemall.adapter.refunddetail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.activity.refund.SelectServiceTypeActivity;
import com.lyd.modulemall.utils.DialogUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class StateOneItemProvider extends BaseItemProvider<RefundDetailMultipleEntity, BaseViewHolder> {
    private Context context;

    private long countDownTime;
    private TextView tvStateDesc;
    Handler countDownTimeHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (countDownTime > 0) {
                countDownTime -= 1;
                //倒计时总时间减1
                tvStateDesc.setText("剩余" + secondToTime(countDownTime) + "..." + "\n" + "退货物流仅支持：三通一达、顺丰、京东");
                countDownTimeHandler.postDelayed(this, 1000);
            }
        }
    };

    public StateOneItemProvider(Context context) {
        this.context = context;
    }

    @Override
    public int viewType() {
        return RefundDetailAdapter.TYPE_STATE_ONE;
    }

    @Override
    public int layout() {
        return R.layout.item_refund_detail_top_state_one;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, RefundDetailMultipleEntity data, int position) {
        int order_status = data.getMyModel().getOrder_status();

        int order_goods_id = data.getMyModel().getOrder_goods_id();
        int refund_status = data.getMyModel().getRefund_status();
        String refund_time = data.getMyModel().getRefund_time();
        tvStateDesc = helper.getView(R.id.tv_state_desc);
        LinearLayout llOperate = helper.getView(R.id.ll_operate);
        ColorTextView tvLeft = helper.getView(R.id.tv_left);
        ColorTextView tvRight = helper.getView(R.id.tv_right);
        switch (refund_status) {
            case 1:
                helper.setText(R.id.tv_state_text, "请等待卖家处理");
                helper.setText(R.id.tv_state_time, refund_time);
                tvStateDesc.setVisibility(View.VISIBLE);
                tvStateDesc.setText("您已成功发起退货退款申请，请您耐心等待卖家处理...");
                llOperate.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvLeft.setText("撤销申请");
                tvRight.setText("修改申请");
                break;
            case 2:
                //服务类型 1我要退款无需退货 2我要退款退货
                int service_type = data.getMyModel().getService_type();
                if (1 == service_type) {
                    helper.setText(R.id.tv_state_text, "卖家同意退款申请");
                    helper.setText(R.id.tv_state_time, refund_time);
                    tvStateDesc.setVisibility(View.VISIBLE);
                    tvStateDesc.setText("退款金额将退回到您的账户余额中...");
                    llOperate.setVisibility(View.GONE);
                } else {
                    helper.setText(R.id.tv_state_text, "卖家已同意，请退货并填写物流信息");
                    helper.setText(R.id.tv_state_time, refund_time);
                    tvStateDesc.setVisibility(View.VISIBLE);
                    countDownTime = data.getMyModel().getCancel_refund_countdown();
                    tvStateDesc.setText("剩余" + secondToTime(countDownTime) + "..." + "\n" + "退货物流仅支持：三通一达、顺丰、京东");
                    countDownTimeHandler.postDelayed(runnable, 1000);
                    llOperate.setVisibility(View.GONE);
                }
                break;
            case 4:
                helper.setText(R.id.tv_state_text, "退款成功");
                helper.setText(R.id.tv_state_time, refund_time);
                tvStateDesc.setVisibility(View.GONE);
                llOperate.setVisibility(View.GONE);
                break;
            case 5:
                helper.setText(R.id.tv_state_text, "卖家拒绝您的退款申请");
                helper.setText(R.id.tv_state_time, refund_time);
                tvStateDesc.setVisibility(View.VISIBLE);
                tvStateDesc.setText("您可以与卖家协调或重新申请...");
                llOperate.setVisibility(View.VISIBLE);
                tvLeft.setVisibility(View.GONE);
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("重新申请");
                break;
            case 6:
                helper.setText(R.id.tv_state_text, "退款关闭");
                helper.setText(R.id.tv_state_time, refund_time);
                tvStateDesc.setVisibility(View.VISIBLE);
                tvStateDesc.setText("您已撤销本次退款详情...");
                llOperate.setVisibility(View.GONE);
                break;
        }
        //撤销申请
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.getInstance().showDialog(context, "", "您将撤销本次申请，如果问题未解决，您还可再次发起。确定继续吗？", true, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        toDoCancel(order_goods_id);
                    }
                });
            }
        });
        //修改申请和重新申请
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), SelectServiceTypeActivity.class);
                intent.putExtra("order_goods_id", order_goods_id);
                intent.putExtra("order_status", order_status);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }

    private void toDoCancel(int order_goods_id) {
        HashMap map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        RxHttp.postForm(MallUrl.REVOCATION_REFUND)
                .addAll(map)
                .asResponse(String.class)
                //.to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        ToastUtils.showShort(data);
                        EventBusUtils.postSticky(new EventMessage<>(MallConstantParam.REFUND_CANCEL));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * 将秒数转换为日时分秒，
     *
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
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
}
