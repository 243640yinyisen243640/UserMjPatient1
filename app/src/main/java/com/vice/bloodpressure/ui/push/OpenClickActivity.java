//package com.vice.bloodpressure.ui.push;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.TextView;
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
//import cn.jpush.android.api.JPushInterface;
//
//public class OpenClickActivity extends Activity {
//    private static final String TAG = "OpenClickActivity";
//    /**
//     * 消息Id
//     **/
//    private static final String KEY_MSGID = "msg_id";
//    /**
//     * 该通知的下发通道
//     **/
//    private static final String KEY_WHICH_PUSH_SDK = "rom_type";
//    /**
//     * 通知标题
//     **/
//    private static final String KEY_TITLE = "n_title";
//    /**
//     * 通知内容
//     **/
//    private static final String KEY_CONTENT = "n_content";
//    /**
//     * 通知附加字段
//     **/
//    private static final String KEY_EXTRAS = "n_extras";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        TextView tv = new TextView(this);
//        setContentView(tv);
//
//        handleOpenClick();
//    }
//
//
//    /**
//     * 处理点击事件，当前启动配置的Activity都是使用
//     * Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
//     * 方式启动，只需要在onCreate中调用此方法进行处理
//     */
//    private void handleOpenClick() {
//        String data = null;
//        //获取华为平台附带的jpush信息
//        if (getIntent().getData() != null) {
//            data = getIntent().getData().toString();
//        }
//
//        //获取fcm、小米、oppo、vivo平台附带的jpush信息
//        if (TextUtils.isEmpty(data) && getIntent().getExtras() != null) {
//            data = getIntent().getExtras().getString("JMessageExtra");
//        }
//
//        if (TextUtils.isEmpty(data)) return;
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            String msgId = jsonObject.optString(KEY_MSGID);
//            byte whichPushSDK = (byte) jsonObject.optInt(KEY_WHICH_PUSH_SDK);
//            //判断跳转
//            String extras = jsonObject.optString(KEY_EXTRAS);
//            JSONObject jsonExtra = new JSONObject(extras);
//            String type = jsonExtra.optString("type");
//
//            Intent[] intents = new Intent[2];
//            intents[0] = new Intent(this, MainActivity.class);
//            switch (type) {
//                case "7"://患者添加申请( 院外管理)
//                    intents[1] = new Intent(Utils.getApp(), MainActivity.class);
//                    break;
//                case "8"://患者住院申请
//                    intents[1] = new Intent(Utils.getApp(), MakeListActivity.class);
//                    intents[1].putExtra("doctorId", jsonExtra.optString("docid"));
//                    break;
//                case "9"://预警消息处理
//                    intents[1] = new Intent(Utils.getApp(), DoctorAdviceActivity.class);
//                    intents[1].putExtra("id", jsonExtra.optString("id"));
//                    intents[1].putExtra("type", jsonExtra.optString("type"));
//                    intents[1].putExtra("typeName", jsonExtra.optString("typename"));
//                    intents[1].putExtra("val", jsonExtra.optString("val"));
//                    break;
//                case "10"://随访详情
//                case "11"://随访详情
//                    String types = jsonExtra.optString("types");
//                    String followId = jsonExtra.optString("id");
//                    if ("1".equals(types)) {
//                        intents[1] = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmitActivity.class);
//                        intents[1].putExtra("type", type);
//                        intents[1].putExtra("id", followId);
//                    } else if ("2".equals(types)) {
//                        intents[1] = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmitActivity.class);
//                        intents[1].putExtra("type", type);
//                        intents[1].putExtra("id", followId);
//                    } else {
//                        intents[1] = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
//                        intents[1].putExtra("type", type);
//                        intents[1].putExtra("id", followId);
//                    }
//                    break;
//                case "12":
//                    String wId = jsonExtra.optString("id");
//                    intents[1] = new Intent(Utils.getApp(), MyTreatPlanDetailActivity.class);
//                    intents[1].putExtra("id", wId);
//                    intents[1].putExtra("type", "0");
//                    break;
//                case "13":
//                    String url = jsonExtra.optString("url");
//                    intents[1] = new Intent(Utils.getApp(), BaseWebViewActivity.class);
//                    intents[1].putExtra("title", "处方");
//                    intents[1].putExtra("url", url);
//                    break;
//                case "14":
//                    intents[1] = new Intent(Utils.getApp(), BloodSugarAddActivity.class);
//                    break;
//                case "15":
//                    String articleUrl = jsonExtra.optString("url");
//                    intents[1] = new Intent(Utils.getApp(), BaseWebViewActivity.class);
//                    intents[1].putExtra("title", "智能教育");
//                    intents[1].putExtra("url", articleUrl);
//                    break;
//                case "16":
//                    int docid = jsonExtra.optInt("docid");
//                    String docname = jsonExtra.optString("docname");
//                    intents[1] = new Intent(Utils.getApp(), AdviceActivity.class);
//                    intents[1].putExtra("docId", docid);
//                    intents[1].putExtra("docName", docname);
//                    break;
//            }
//            startActivities(intents);
//            //关闭当前
//            finish();
//            //上报点击事件
//            JPushInterface.reportNotificationOpened(this, msgId, whichPushSDK);
//        } catch (JSONException e) {
//        }
//    }
//}
