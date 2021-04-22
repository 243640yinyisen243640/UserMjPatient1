package com.vice.bloodpressure.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SugarSearchBean;
import com.vice.bloodpressure.ui.activity.healthrecordlist.HealthRecordBloodSugarListActivity;
import com.vice.bloodpressure.view.BloodSugarDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 描述: 不复用的Adapter
 * 作者: LYD
 * 创建日期: 2019/6/14 20:40
 */
public class ThirtyBottomSearchAdapter extends BaseQuickAdapter<SugarSearchBean.SearchBean.InfoBean, BaseViewHolder> {
    private Context context;

    public ThirtyBottomSearchAdapter(@Nullable List<SugarSearchBean.SearchBean.InfoBean> data, Context context) {
        super(R.layout.item_home_blood_sugar, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SugarSearchBean.SearchBean.InfoBean infoBean) {
        String ymdStr = infoBean.getTime();
        String time = ymdStrFormatToMdStr(ymdStr);
        viewHolder.setText(R.id.tv_time, time);
        FrameLayout flBeforeDawn = viewHolder.getView(R.id.fl_before_dawn);
        TextView tvBeforeDawn = viewHolder.getView(R.id.tv_before_dawn);
        ImageView imgBeforeDawnMore = viewHolder.getView(R.id.img_before_dawn_more);

        FrameLayout flBeforeBreakfast = viewHolder.getView(R.id.fl_before_breakfast);
        TextView tvBeforeBreakfast = viewHolder.getView(R.id.tv_before_breakfast);
        ImageView imgBeforeBreakfastMore = viewHolder.getView(R.id.img_before_breakfast_more);

        FrameLayout flAfterBreakfast = viewHolder.getView(R.id.fl_after_breakfast);
        TextView tvAfterBreakfast = viewHolder.getView(R.id.tv_after_breakfast);
        ImageView imgAfterBreakfastMore = viewHolder.getView(R.id.img_after_breakfast_more);

        FrameLayout flBeforeLunch = viewHolder.getView(R.id.fl_before_lunch);
        TextView tvBeforeLunch = viewHolder.getView(R.id.tv_before_lunch);
        ImageView imgBeforeLunchMore = viewHolder.getView(R.id.img_before_lunch_more);

        FrameLayout flAfterLaunch = viewHolder.getView(R.id.fl_after_launch);
        TextView tvAfterLaunch = viewHolder.getView(R.id.tv_after_launch);
        ImageView imgAfterLaunchMore = viewHolder.getView(R.id.img_after_launch_more);

        FrameLayout flBeforeDinner = viewHolder.getView(R.id.fl_before_dinner);
        TextView tvBeforeDinner = viewHolder.getView(R.id.tv_before_dinner);
        ImageView imgBeforeDinnerMore = viewHolder.getView(R.id.img_before_dinner_more);

        FrameLayout flAfterDinner = viewHolder.getView(R.id.fl_after_dinner);
        TextView tvAfterDinner = viewHolder.getView(R.id.tv_after_dinner);
        ImageView imgAfterDinnerMore = viewHolder.getView(R.id.img_after_dinner_more);

        FrameLayout flAfterSleep = viewHolder.getView(R.id.fl_after_sleep);
        TextView tvAfterSleep = viewHolder.getView(R.id.tv_after_sleep);
        ImageView imgAfterSleepMore = viewHolder.getView(R.id.img_after_sleep_more);


        List<String> lch = infoBean.getLch();
        List<String> zcq = infoBean.getZcq();
        List<String> zch = infoBean.getZch();
        List<String> wcq = infoBean.getWcq();
        List<String> wch = infoBean.getWch();
        List<String> wfq = infoBean.getWfq();
        List<String> wfh = infoBean.getWfh();
        List<String> shq = infoBean.getShq();
        //设值
        setSugarValueAndState(lch, tvBeforeDawn);
        setSugarValueAndState(zcq, tvBeforeBreakfast);
        setSugarValueAndState(zch, tvAfterBreakfast);
        setSugarValueAndState(wcq, tvBeforeLunch);
        setSugarValueAndState(wch, tvAfterLaunch);
        setSugarValueAndState(wfq, tvBeforeDinner);
        setSugarValueAndState(wfh, tvAfterDinner);
        setSugarValueAndState(shq, tvAfterSleep);

        //0否 1是
        int lchmore = infoBean.getLchmore();
        int zcqmore = infoBean.getZcqmore();
        int zchmore = infoBean.getZchmore();
        int wcqmore = infoBean.getWcqmore();
        int wchmore = infoBean.getWchmore();
        int wfqmore = infoBean.getWfqmore();
        int wfhmore = infoBean.getWfhmore();
        int shqmore = infoBean.getShqmore();
        setSugarMoreShowOrHide(lchmore, imgBeforeDawnMore);
        setSugarMoreShowOrHide(zcqmore, imgBeforeBreakfastMore);
        setSugarMoreShowOrHide(zchmore, imgAfterBreakfastMore);
        setSugarMoreShowOrHide(wcqmore, imgBeforeLunchMore);
        setSugarMoreShowOrHide(wchmore, imgAfterLaunchMore);
        setSugarMoreShowOrHide(wfqmore, imgBeforeDinnerMore);
        setSugarMoreShowOrHide(wfhmore, imgAfterDinnerMore);
        setSugarMoreShowOrHide(shqmore, imgAfterSleepMore);

        //点击处理
        setViewClick(flBeforeDawn, lch, ymdStr, "凌晨", 8);
        setViewClick(flBeforeBreakfast, zcq, ymdStr, "早餐空腹", 1);
        setViewClick(flAfterBreakfast, zch, ymdStr, "早餐后", 2);
        setViewClick(flBeforeLunch, wcq, ymdStr, "午餐前", 3);
        setViewClick(flAfterLaunch, wch, ymdStr, "午餐后", 4);
        setViewClick(flBeforeDinner, wfq, ymdStr, "晚餐前", 5);
        setViewClick(flAfterDinner, wfh, ymdStr, "晚餐后", 6);
        setViewClick(flAfterSleep, shq, ymdStr, "睡前", 7);
    }


    private void setSugarValueAndState(List<String> listStr, TextView tv) {
        if (listStr != null && listStr.size() == 2) {
            String sugarValue = listStr.get(0);
            tv.setText(sugarValue);
            //1 偏高  2偏低  3正常
            String sugarState = listStr.get(1);
            switch (sugarState) {
                case "1":
                    tv.setTextColor(ColorUtils.getColor(R.color.home_blood_sugar_text_high));
                    break;
                case "2":
                    tv.setTextColor(ColorUtils.getColor(R.color.home_blood_sugar_text_low));
                    break;
                case "3":
                    tv.setTextColor(ColorUtils.getColor(R.color.home_blood_sugar_text_normal));
                    break;
            }
        } else {
            tv.setText("+");
            tv.setTextColor(ColorUtils.getColor(R.color.home_blood_sugar_text_gray));
        }
    }

    private void setSugarMoreShowOrHide(int type, ImageView img) {
        if (0 == type) {
            img.setVisibility(View.GONE);
        } else {
            img.setVisibility(View.VISIBLE);
        }
    }

    private void setViewClick(FrameLayout fl, List<String> listStr, String time, String typeStr, int typePosition) {
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listStr != null && listStr.size() == 2) {
                    //有数据点击进列表
                    Intent intent = new Intent(Utils.getApp(), HealthRecordBloodSugarListActivity.class);
                    intent.putExtra("type", typeStr);
                    intent.putExtra("userid", "");
                    intent.putExtra("time", time);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    String timeMd = ymdStrFormatToMdStr(time);
                    //没有数据 点击添加
                    Dialog dialog = new BloodSugarDialog(context, R.style.bloodDialog, typePosition, timeMd);
                    dialog.show();
                }
            }
        });
    }


    /**
     * yyyy-MM-dd格式化成 MM.dd
     *
     * @param ymdStr
     * @return
     */
    private String ymdStrFormatToMdStr(String ymdStr) {
        String mdStr = "";
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat md = new SimpleDateFormat("MM-dd");
            Date date = ymd.parse(ymdStr);
            mdStr = md.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mdStr;
    }
}
