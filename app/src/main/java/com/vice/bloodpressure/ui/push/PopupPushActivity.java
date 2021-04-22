package com.vice.bloodpressure.ui.push;

import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.blankj.utilcode.util.AppUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
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
import com.vice.bloodpressure.utils.RxTimerUtils;
import com.vice.bloodpressure.utils.TurnsUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 阿里推送
 * 作者: LYD
 * 创建日期: 2020/9/9 10:20
 */
public class PopupPushActivity extends AndroidPopupActivity {
    static final String TAG = "PopupPushActivity";
    private static final int GET_IM_TOKEN = 10010;


    /**
     * 实现通知打开回调方法，获取通知相关信息
     *
     * @param title   标题
     * @param summary 内容
     * @param item    额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> item) {
        Log.e(TAG, "离线推送==" + item);
        changeIsRead(item.get("pid"));
        String type = item.get("type");
        int intType = TurnsUtils.getInt(type, 0);
        Intent mainIntent = new Intent(this, MainActivity.class);
        Intent intent = null;
        switch (intType) {
            //系统消息
            case 19:
                //血糖严重偏低
            case 23:
                //血糖严重偏高
            case 24:
                intent = new Intent(this, SystemMsgDetailsActivity.class);
                intent.putExtra("id", item.get("pid"));
                break;
            //医生处理患者预约住院申请结果
            //跳转预约住院详情
            case 2:
                intent = new Intent(this, MakeDetailsActivity.class);
                intent.putExtra("id", item.get("inhosid") + "");
                break;
            //消息列表
            case 3:
            case 15:
                AppUtils.launchApp("com.vice.bloodpressure");
                RxTimerUtils rxTimer = new RxTimerUtils();
                rxTimer.timer(3 * 1000, new RxTimerUtils.RxAction() {
                    @Override
                    public void action(long number) {
                        Intent intent = new Intent(PopupPushActivity.this, SystemMsgListActivity.class);
                        Intent[] intents = new Intent[]{mainIntent, intent};
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivities(intents);
                    }
                });
                break;
            //血糖随访
            case 4:
            case 7:
                intent = new Intent(this, FollowUpVisitBloodSugarSubmitActivity.class);
                intent.putExtra("id", item.get("id") + "");
                break;
            //血压随访
            case 5:
            case 8:
                intent = new Intent(this, FollowUpVisitBloodPressureSubmitActivity.class);
                intent.putExtra("id", item.get("id") + "");
                break;
            //肝病随访
            case 6:
            case 9:
                intent = new Intent(this, FollowUpVisitHepatopathySubmitActivity.class);
                intent.putExtra("id", item.get("id") + "");
                break;
            //降压
            case 11:
                //减重
            case 12:
                //降糖
            case 13:
                intent = new Intent(this, BaseWebViewActivity.class);
                intent.putExtra("title", "方案详情");
                intent.putExtra("url", item.get("url") + "");
                break;
            //中医体质
            case 14:
                intent = new Intent(this, BaseWebViewActivity.class);
                intent.putExtra("title", "体质报告");
                intent.putExtra("url", item.get("url") + "");
                break;
            //医生发送患者宣教
            case 16:
                String artType = item.get("arttype");
                switch (artType) {
                    case "1":
                        intent = new Intent(this, PatientEducationVideoActivity.class);
                        intent.putExtra("title", item.get("article_title"));
                        intent.putExtra("url", item.get("links"));
                        intent.putExtra("videoUrl", item.get("url"));
                        break;
                    case "2":
                        intent = new Intent(this, PatientEducationAudioActivity.class);
                        intent.putExtra("title", item.get("article_title"));
                        intent.putExtra("url", item.get("links"));
                        intent.putExtra("audioUrl", item.get("url"));
                        intent.putExtra("id", 0);
                        intent.putExtra("audioDuration", item.get("duration"));
                        break;
                    case "3":
                        intent = new Intent(this, BaseWebViewActivity.class);
                        intent.putExtra("title", item.get("article_title"));
                        intent.putExtra("url", item.get("links"));
                        break;
                }
                break;
            //医生发送科室公告
            case 17:
                intent = new Intent(this, DepartmentListActivity.class);
                intent.putExtra("docid", item.get("docid"));
                break;
            //血糖未测量提醒
            //添加血糖
            case 18:
                intent = new Intent(this, BloodSugarAddActivity.class);
                break;
            //血糖每月分析报告
            case 20:
                intent = new Intent(this, BloodSugarReportActivity.class);
                intent.putExtra("time", item.get("starttime"));
                break;
            //血压每月分析报告
            case 21:
                intent = new Intent(this, BloodPressureReportActivity.class);
                intent.putExtra("time", item.get("starttime"));
                intent.putExtra("type", "3");
                break;
            //运动周报
            case 22:
                intent = new Intent(this, SportWeekPagerDetailActivity.class);
                intent.putExtra("beginTime", item.get("starttime"));
                intent.putExtra("endTime", item.get("endtime"));
                break;
            //医生处理患者发送添加申请结果
            //医生处理患者预警消息结果
            //智能教育学习提醒
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        if (intent != null) {
            Intent[] intents = new Intent[]{mainIntent, intent};
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivities(intents);
        }
        //关闭当前
        finish();
    }

    private void changeIsRead(String pid) {
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


    //    /**
    //     * 初始化融云
    //     */
    //    private void initIm() {
    //        RongIM.init(this, ConstantParam.IM_KEY);
    //    }
    //
    //    /**
    //     * 获取融云Token
    //     */
    //    private void getImToken() {
    //        HashMap map = new HashMap<>();
    //        XyUrl.okPost(XyUrl.GET_IM_TOKEN, map, new OkHttpCallBack<String>() {
    //            @Override
    //            public void onSuccess(String value) {
    //                RongYunBean rongYunBean = JSONObject.parseObject(value, RongYunBean.class);
    //                //Message message = Message.obtain();
    //                //message.what = GET_IM_TOKEN;
    //                //message.obj = rongYunBean;
    //                //sendHandlerMessage(message);
    //                connectRongServer(rongYunBean);
    //            }
    //
    //            @Override
    //            public void onError(int error, String errorMsg) {
    //
    //            }
    //        });
    //    }
    //
    //    /**
    //     * 连接融云服务器
    //     *
    //     * @param rongYun
    //     */
    //    private void connectRongServer(RongYunBean rongYun) {
    //        RongIM.connect(rongYun.getToken(), new RongIMClient.ConnectCallback() {
    //            @Override
    //            public void onSuccess(String userId) {
    //                Log.e(TAG, "连接融云服务器成功");
    //                Intent mainIntent = new Intent(PopupPushActivity.this, MainActivity.class);
    //                Intent intent = new Intent(PopupPushActivity.this, SystemMsgListActivity.class);
    //                Intent[] intents = new Intent[]{mainIntent, intent};
    //                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                startActivities(intents);
    //            }
    //
    //            @Override
    //            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
    //
    //            }
    //
    //            @Override
    //            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
    //
    //            }
    //        });
    //    }

}
