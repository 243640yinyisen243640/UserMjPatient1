//package com.vice.bloodpressure.utils.jpush;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.blankj.utilcode.util.Utils;
//import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
//import com.vice.bloodpressure.ui.activity.MainActivity;
//import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmitActivity;
//import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmitActivity;
//import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
//import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodSugarAddActivity;
//import com.vice.bloodpressure.ui.activity.hospital.AdviceActivity;
//import com.vice.bloodpressure.ui.activity.hospital.MakeListActivity;
//import com.vice.bloodpressure.ui.activity.im.DoctorAdviceActivity;
//import com.vice.bloodpressure.ui.activity.smartmakepolicy.MyTreatPlanDetailActivity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import cn.jpush.android.api.NotificationMessage;
//import cn.jpush.android.service.JPushMessageReceiver;
//
///**
// * 描述:
// * 作者: LYD
// * 创建日期: 2020/4/2 9:18
// */
//public class MyJPushReceiver extends JPushMessageReceiver {
//    private static final String TAG = "MyJPushReceiver";
//
//    @Override
//    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
//        super.onNotifyMessageArrived(context, notificationMessage);
//        //int notificationType = notificationMessage.notificationType;
//        String notificationTitle = notificationMessage.notificationTitle;
//        String notificationContent = notificationMessage.notificationContent;
//        //额外参数示例: {"key":"自定义附加字段0","type":"自定义附加字段1"}
//        String notificationExtras = notificationMessage.notificationExtras;
//    }
//
//    @Override
//    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
//        super.onNotifyMessageOpened(context, notificationMessage);
//        String notificationTitle = notificationMessage.notificationTitle;
//        String notificationContent = notificationMessage.notificationContent;
//        //额外参数示例: {"key":"自定义附加字段0","type":"自定义附加字段1"}
//        String extras = notificationMessage.notificationExtras;
//        //判断跳转
//        try {
//            JSONObject jsonExtra = new JSONObject(extras);
//            String type = jsonExtra.optString("type");
//            Intent intent = null;
//            switch (type) {
//                case "7"://患者添加申请( 院外管理 ???)
//                    intent = new Intent(Utils.getApp(), MainActivity.class);
//                    break;
//                case "8"://患者住院申请
//                    intent = new Intent(Utils.getApp(), MakeListActivity.class);
//                    intent.putExtra("doctorId", jsonExtra.optString("docid"));
//                    break;
//                case "9"://预警消息处理
//                    intent = new Intent(Utils.getApp(), DoctorAdviceActivity.class);
//                    intent.putExtra("id", jsonExtra.optString("id"));
//                    intent.putExtra("type", jsonExtra.optString("type"));
//                    intent.putExtra("typeName", jsonExtra.optString("typename"));
//                    intent.putExtra("val", jsonExtra.optString("val"));
//                    break;
//                case "10"://随访详情
//                case "11"://随访详情
//                    String types = jsonExtra.optString("types");
//                    String followId = jsonExtra.optString("id");
//                    if ("1".equals(types)) {
//                        intent = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmitActivity.class);
//                        intent.putExtra("type", type);
//                        intent.putExtra("id", followId);
//                    } else if ("2".equals(types)) {
//                        intent = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmitActivity.class);
//                        intent.putExtra("type", type);
//                        intent.putExtra("id", followId);
//                    } else {
//                        intent = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
//                        intent.putExtra("type", type);
//                        intent.putExtra("id", followId);
//                    }
//                    break;
//                case "12":
//                    String wId = jsonExtra.optString("id");
//                    intent = new Intent(Utils.getApp(), MyTreatPlanDetailActivity.class);
//                    intent.putExtra("id", wId);
//                    intent.putExtra("type", "0");
//                    break;
//                case "13":
//                    String url = jsonExtra.optString("url");
//                    intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
//                    intent.putExtra("title", "处方");
//                    intent.putExtra("url", url);
//                    break;
//                case "14":
//                    intent = new Intent(Utils.getApp(), BloodSugarAddActivity.class);
//                    break;
//                case "15":
//                    String articleUrl = jsonExtra.optString("url");
//                    intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
//                    intent.putExtra("title", "智能教育");
//                    intent.putExtra("url", articleUrl);
//                    break;
//                case "16":
//                    int docid = jsonExtra.optInt("docid");
//                    String docname = jsonExtra.optString("docname");
//                    intent = new Intent(Utils.getApp(), AdviceActivity.class);
//                    intent.putExtra("docId", docid);
//                    intent.putExtra("docName", docname);
//                    break;
//
//            }
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Utils.getApp().startActivity(intent);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
