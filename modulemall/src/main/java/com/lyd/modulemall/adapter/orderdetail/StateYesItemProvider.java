package com.lyd.modulemall.adapter.orderdetail;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;

public class StateYesItemProvider extends BaseItemProvider<OrderDetailMultipleEntity, BaseViewHolder> {

    private long countDownTime;
    private TextView tvDesc;

    Handler countDownTimeHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (countDownTime > 0) {
                countDownTime -= 1;
                //倒计时总时间减1
                tvDesc.setText("剩余" + secToTime(countDownTime) + "，订单自动关闭...");
                countDownTimeHandler.postDelayed(this, 1000);
            }
        }
    };


    Handler autoCountDownTimeHandler = new Handler();
    Runnable autoRunnable = new Runnable() {
        @Override
        public void run() {
            if (countDownTime > 0) {
                countDownTime -= 1;
                //倒计时总时间减1
                tvDesc.setText("剩余" + secondToTime(countDownTime) + ",自动确认...");
                countDownTimeHandler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public int viewType() {
        return MyOrderDetailAdapter.TYPE_STATE_YES;
    }

    @Override
    public int layout() {
        return R.layout.item_order_detail_type_state_yes;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, OrderDetailMultipleEntity data, int position) {
        int orderStatus = data.getMyModel().getOrder_status();
        //订单状态 1待付款 2待发货 3待收货 5已完成
        tvDesc = helper.getView(R.id.tv_type_state_desc);
        switch (orderStatus) {
            case 1:
                helper.setText(R.id.tv_type_state_text, "待付款");
                tvDesc.setVisibility(View.VISIBLE);
                countDownTime = data.getMyModel().getCount_down();
                tvDesc.setText("剩余" + secToTime(countDownTime) + "，订单自动关闭...");
                countDownTimeHandler.postDelayed(runnable, 1000);
                break;
            case 2:
                helper.setText(R.id.tv_type_state_text, "等待卖家发货");
                tvDesc.setVisibility(View.GONE);
                break;
            case 3:
                helper.setText(R.id.tv_type_state_text, "卖家已发货");
                tvDesc.setVisibility(View.VISIBLE);
                countDownTime = data.getMyModel().getAuto_receive_countdown();
                tvDesc.setText("剩余" + secondToTime(countDownTime) + ",自动确认...");
                autoCountDownTimeHandler.postDelayed(autoRunnable, 1000);
                break;
            case 5:
                helper.setText(R.id.tv_type_state_text, "交易成功");
                tvDesc.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 将秒数转换为日时分秒
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

    /**
     * 将秒数转换为日时分秒
     *
     * @param seconds
     * @return
     */
    public static String secToTime(long seconds) {
        long hour = seconds / 3600;
        long minute = (seconds - hour * 3600) / 60;
        long second = (seconds - hour * 3600 - minute * 60);
        StringBuffer sb = new StringBuffer();
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        return sb.toString();
    }
}
