package com.lyd.modulemall.adapter.refunddetail;

import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;

public class StateTwoItemProvider extends BaseItemProvider<RefundDetailMultipleEntity, BaseViewHolder> {
    private long countDownTime;
    private TextView tvStateDesc;
    Handler countDownTimeHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (countDownTime > 0) {
                countDownTime -= 1;
                //倒计时总时间减1
                String countDownTimeText = String.format(Utils.getApp().getString(R.string.refund_count_down), secondToTime(countDownTime));
                tvStateDesc.setText(countDownTimeText);
                countDownTimeHandler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public int viewType() {
        return RefundDetailAdapter.TYPE_STATE_TWO;
    }

    @Override
    public int layout() {
        return R.layout.item_refund_detail_top_state_two;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, RefundDetailMultipleEntity data, int position) {
        String refund_time = data.getMyModel().getRefund_time();
        countDownTime = data.getMyModel().getRefund_countdown();
        String refund_express_name = data.getMyModel().getRefund_express_name();
        String refund_shipping_no = data.getMyModel().getRefund_shipping_no();
        String countDownTimeText = String.format(Utils.getApp().getString(R.string.refund_count_down), secondToTime(countDownTime));
        String logistics = String.format(Utils.getApp().getString(R.string.refund_logistics), refund_express_name, refund_shipping_no);
        helper.setText(R.id.tv_state_text, "等待卖家收货");
        helper.setText(R.id.tv_state_time, refund_time);
        tvStateDesc = helper.getView(R.id.tv_count_down_time);
        tvStateDesc.setText(countDownTimeText);
        countDownTimeHandler.postDelayed(runnable, 1000);
        //helper.setText(R.id.tv_count_down_time, countDownTime);
        helper.setText(R.id.tv_logistic, logistics);
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
