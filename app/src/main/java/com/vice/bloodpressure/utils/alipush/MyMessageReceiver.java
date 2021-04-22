package com.vice.bloodpressure.utils.alipush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.blankj.utilcode.util.GsonUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.SystemMsgListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmitActivity;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmitActivity;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodSugarAddActivity;
import com.vice.bloodpressure.ui.activity.hospital.DepartmentListActivity;
import com.vice.bloodpressure.ui.activity.hospital.MakeDetailsActivity;
import com.vice.bloodpressure.ui.activity.patienteducation.PatientEducationAudioActivity;
import com.vice.bloodpressure.ui.activity.patienteducation.PatientEducationVideoActivity;
import com.vice.bloodpressure.ui.activity.smartanalyse.BloodPressureReportActivity;
import com.vice.bloodpressure.ui.activity.smartanalyse.BloodSugarReportActivity;
import com.vice.bloodpressure.ui.activity.sport.SportWeekPagerDetailActivity;
import com.vice.bloodpressure.ui.activity.sysmsg.SystemMsgDetailsActivity;
import com.vice.bloodpressure.ui.activity.sysmsg.SystemMsgListActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * 描述:  阿里推送
 * 作者: LYD
 * 创建日期: 2020/9/9 10:20
 */
public class MyMessageReceiver extends MessageReceiver {

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        //TODO处理推送通知
        Log.e("MyMessageReceiver", "收到扩展参数==" + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
    }

    /**
     * 通知点击处理
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "点击扩展参数==" + extraMap);
        SystemMsgListBean item = GsonUtils.fromJson(extraMap, SystemMsgListBean.class);
        int type = item.getType();
        changeIsRead(item.getPid());
        Intent intent = null;
        switch (type) {
            //系统消息
            case 19:
                //血糖严重偏低
            case 23:
                //血糖严重偏高
            case 24:
                intent = new Intent(context, SystemMsgDetailsActivity.class);
                intent.putExtra("id", item.getPid() + "");
                break;
            //医生处理患者预约住院申请结果
            //跳转预约住院详情
            case 2:
                intent = new Intent(context, MakeDetailsActivity.class);
                intent.putExtra("id", item.getInhosid() + "");
                break;
            //消息列表
            case 3:
            case 15:
                intent = new Intent(context, SystemMsgListActivity.class);
                break;
            //血糖随访
            case 4:
            case 7:
                intent = new Intent(context, FollowUpVisitBloodSugarSubmitActivity.class);
                intent.putExtra("id", item.getId() + "");
                break;
            //血压随访
            case 5:
            case 8:
                intent = new Intent(context, FollowUpVisitBloodPressureSubmitActivity.class);
                intent.putExtra("id", item.getId() + "");
                break;
            //肝病随访
            case 6:
            case 9:
                intent = new Intent(context, FollowUpVisitHepatopathySubmitActivity.class);
                intent.putExtra("id", item.getId() + "");
                break;
            //降压
            case 11:
                //减重
            case 12:
                //降糖
            case 13:
                intent = new Intent(context, BaseWebViewActivity.class);
                intent.putExtra("title", "方案详情");
                intent.putExtra("url", item.getUrl());
                break;
            //中医体质
            case 14:
                intent = new Intent(context, BaseWebViewActivity.class);
                intent.putExtra("title", "体质报告");
                intent.putExtra("url", item.getUrl());
                break;
            //医生发送患者宣教
            case 16:
                int arttype = item.getArttype();
                switch (arttype) {
                    case 1:
                        intent = new Intent(context, PatientEducationVideoActivity.class);
                        intent.putExtra("title", item.getArticle_title());
                        intent.putExtra("url", item.getLinks());
                        intent.putExtra("videoUrl", item.getUrl());
                        break;
                    case 2:
                        intent = new Intent(context, PatientEducationAudioActivity.class);
                        intent.putExtra("title", item.getArticle_title());
                        intent.putExtra("url", item.getLinks());
                        intent.putExtra("audioUrl", item.getUrl());
                        intent.putExtra("id", item.getPid());
                        intent.putExtra("audioDuration", item.getDuration());
                        break;
                    case 3:
                        intent = new Intent(context, BaseWebViewActivity.class);
                        intent.putExtra("title", item.getArticle_title());
                        intent.putExtra("url", item.getLinks());
                        break;
                }
                break;
            //医生发送科室公告
            case 17:
                intent = new Intent(context, DepartmentListActivity.class);
                intent.putExtra("docid", item.getDocid() + "");
                break;
            //血糖未测量提醒
            //添加血糖
            case 18:
                intent = new Intent(context, BloodSugarAddActivity.class);
                break;
            //血糖每月分析报告
            case 20:
                intent = new Intent(context, BloodSugarReportActivity.class);
                intent.putExtra("time", item.getStarttime());
                break;
            //血压每月分析报告
            case 21:
                intent = new Intent(context, BloodPressureReportActivity.class);
                intent.putExtra("time", item.getStarttime());
                intent.putExtra("type", "3");
                break;
            //运动周报
            case 22:
                intent = new Intent(context, SportWeekPagerDetailActivity.class);
                intent.putExtra("beginTime", item.getStarttime());
                intent.putExtra("endTime", item.getEndtime());
                break;
            //医生处理患者发送添加申请结果
            //医生处理患者预警消息结果
            //智能教育学习提醒
            default:
                intent = new Intent(context, MainActivity.class);
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
    }

    private void changeIsRead(int pid) {
        HashMap map = new HashMap<>();
        map.put("pid", pid);
        XyUrl.okPostSave(XyUrl.GET_PORT_MESSAGE_EDIT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                EventBusUtils.post(new EventMessage(ConstantParam.WARN_COUNT_REFRESH));
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }
}
