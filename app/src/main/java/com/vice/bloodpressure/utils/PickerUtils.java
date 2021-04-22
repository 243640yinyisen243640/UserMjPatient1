package com.vice.bloodpressure.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 描述: 时间选择的工具类
 * 作者: LYD
 * 所需参数: 无
 * 创建日期: 2018/10/31 15:14
 */
public class PickerUtils {
    private PickerUtils() {
    }

    /**
     * 显示 年月日 时间
     *
     * @param context
     * @param callBack
     */
    public static void showTime(Context context, final PickerUtils.TimePickerCallBack callBack) {
        boolean[] booleans = {true, true, true, false, false, false};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        showTime(context, callBack, booleans, format);
    }

    /**
     * 显示年月日 时分日期
     *
     * @param context
     * @param callBack
     */
    public static void showTimeHm(Context context, final PickerUtils.TimePickerCallBack callBack) {
        boolean[] booleans = {true, true, true, true, true, false};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        showTime(context, callBack, booleans, format);
    }

    /**
     * 显示 时分日期
     */
    public static void showTimeHourAndMin(Context context, final PickerUtils.TimePickerCallBack callBack) {
        boolean[] booleans = {false, false, false, true, true, false};
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        showTime(context, callBack, booleans, format);
    }

    /**
     * 显示年月日 时分秒日期
     *
     * @param context
     * @param callBack
     * @param booleans
     * @param format
     */
    public static void showTime(Context context, final PickerUtils.TimePickerCallBack callBack, boolean[] booleans, SimpleDateFormat format) {
        TimePickerView timePickerView = new TimePickerBuilder(context, (date, v) -> {
            String time = format.format(date);
            callBack.execEvent(time);
        })
                .setType(booleans)
                .setContentTextSize(21)
                //设置字体大小
                .isDialog(true)
                .build();
        timePickerView.show();
        Dialog mDialog = timePickerView.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            timePickerView.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    /**
     * 显示选项
     *
     * @param context
     * @param callBack
     * @param listOption
     */
    public static void showOption(Context context, final PickerUtils.TimePickerCallBack callBack, List listOption) {
        OptionsPickerView pv = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                callBack.execEvent(listOption.get(options1).toString());
            }
        })
                .setContentTextSize(21)
                .build();//构造
        pv.show();//显示
        pv.setPicker(listOption);
        pv.setSelectOptions(0);
    }

    /**
     * 显示选项
     *
     * @param context
     * @param callBack
     * @param listOption
     */
    public static void showOptionPosition(Context context, final PickerUtils.PositionCallBack callBack, List listOption) {
        OptionsPickerView pv = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //callBack.execEvent(listOption.get(options1).toString());
                callBack.execEvent(listOption.get(options1).toString(), options1);
            }
        })
                .setContentTextSize(21)
                .build();//构造
        pv.show();//显示
        pv.setPicker(listOption);
        pv.setSelectOptions(0);
    }

    public interface TimePickerCallBack {
        void execEvent(String content);
    }


    /**
     * 回调位置
     */
    public interface PositionCallBack {
        void execEvent(String content, int position);
    }
}
