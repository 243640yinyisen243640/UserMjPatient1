package com.vice.bloodpressure.bean.im;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 描述: 预警消息 融云自定义消息
 * 作者: LYD
 * 创建日期: 2019/6/11 15:37
 */
@MessageTag(value = "xy:worning", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class ImWarningMessage extends MessageContent {
    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<ImWarningMessage> CREATOR = new Creator<ImWarningMessage>() {

        @Override
        public ImWarningMessage createFromParcel(Parcel source) {
            return new ImWarningMessage(source);
        }

        @Override
        public ImWarningMessage[] newArray(int size) {
            return new ImWarningMessage[size];
        }
    };
    private final static String TAG = "TestMessage";
    //内容
    private String content;

    /**
     * 默认构造函数
     */
    public ImWarningMessage() {

    }

    /**
     * 构造函数。
     *
     * @param in 初始化传入的 Parcel。
     */
    public ImWarningMessage(Parcel in) {
        setContent(ParcelUtils.readFromParcel(in));
    }

    /**
     * 构造函数。
     *
     * @param data 初始化传入的二进制数据。
     */
    public ImWarningMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("content"))
                setContent(jsonObj.optString("content"));
        } catch (JSONException e) {
        }
    }

    /**
     * 构建一个消息实例
     *
     * @param text
     * @return
     */
    public static ImWarningMessage obtain(String text) {
        ImWarningMessage model = new ImWarningMessage();
        model.setContent(text);
        return model;
    }

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    public int describeContents() {
        return 0;
    }

    /**
     * 将本地消息对象序列化为消息数据。
     *
     * @return 消息数据。
     */
    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("content", getContent());
        } catch (JSONException e) {
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志，可能是 0 或 PARCELABLE_WRITE_RETURN_VALUE。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);
    }

    /**
     * 获取文字消息的内容。
     *
     * @return 文字消息的内容。
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文字消息的内容。
     *
     * @param content 文字消息的内容。
     */
    public void setContent(String content) {
        this.content = content;
    }
}
