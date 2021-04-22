package com.vice.bloodpressure.bean.im;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.vice.bloodpressure.ui.activity.MainActivity;

import io.rong.push.PushType;
import io.rong.push.RongPushClient;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * 描述:Push 消息监听
 * 作者: LYD
 * 创建日期: 2019/6/27 10:42
 * 修改: MiMessageReceiver
 */
public class NotificationReceiver extends PushMessageReceiver {

    private static final String TAG = "NotificationReceiver";

    /**
     * push 通知到达事件
     * // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
     *
     * @param context
     * @param pushType
     * @param pushNotificationMessage
     * @return
     */
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    /**
     * push 通知点击事件
     * // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面;
     * //返回 true, 则由您自定义处理逻辑。
     *
     * @param context
     * @param pushType
     * @param pushNotificationMessage
     * @return
     */
    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        String pushId = pushNotificationMessage.getPushId();

        RongPushClient.ConversationType conversationType = pushNotificationMessage.getConversationType();

        String targetId = pushNotificationMessage.getTargetId();

        String targetUserName = pushNotificationMessage.getTargetUserName();

        long receivedTime = pushNotificationMessage.getReceivedTime();

        String objectName = pushNotificationMessage.getObjectName();

        String senderId = pushNotificationMessage.getSenderId();

        String senderName = pushNotificationMessage.getSenderName();

        Uri senderPortrait = pushNotificationMessage.getSenderPortrait();

        String pushTitle = pushNotificationMessage.getPushTitle();

        String pushContent = pushNotificationMessage.getPushContent();

        String extra = pushNotificationMessage.getExtra();

        String pushFlag = pushNotificationMessage.getPushFlag();

        //重写跳转逻辑 全部到首页
        Intent intentMain = new Intent(context, MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentMain);
        return true;
        //return false;
    }


    @Override
    public void onThirdPartyPushState(PushType pushType, String action, long resultCode) {
        super.onThirdPartyPushState(pushType, action, resultCode);

    }
}
