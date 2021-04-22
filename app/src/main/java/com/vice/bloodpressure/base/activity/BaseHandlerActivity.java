package com.vice.bloodpressure.base.activity;

import android.os.Handler;
import android.os.Message;

import com.vice.bloodpressure.imp.HandlerImp;

/**
 * 描述: 基础Activity
 * 作者: LYD
 * 创建日期: 2019/3/25 10:05
 */
public abstract class BaseHandlerActivity extends BaseActivity implements HandlerImp {

    /**
     * WeakHandler
     */
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            processHandlerMsg(msg);
            return false;
        }
    });


    //Handler封装开始

    /**
     * 返回处理消息的Handler对象
     *
     * @return
     */
    protected Handler getHandler() {
        return this.mHandler;
    }

    /**
     * 获取一个Message对象
     *
     * @return
     */
    protected Message getHandlerMessage() {
        return Message.obtain();
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    protected void sendHandlerMessage(Message msg) {
        mHandler.sendMessage(msg);
    }

    /**
     * 发送消息
     *
     * @param what
     */
    protected void sendHandlerMessage(int what) {
        Message msg = getHandlerMessage();
        msg.what = what;
        sendHandlerMessage(msg);
    }
    //Handler封装结束
}

