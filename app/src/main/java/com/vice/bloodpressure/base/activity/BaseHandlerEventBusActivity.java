package com.vice.bloodpressure.base.activity;

import android.os.Bundle;

import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 描述: Base EventBusActivity
 * 作者: LYD
 * 创建日期: 2019/6/20 14:04
 */
public abstract class BaseHandlerEventBusActivity extends BaseHandlerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
        //            EventBusUtils.register(this);
        //        }
        EventBusUtils.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0)
    public void onEventBusCome(EventMessage event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 0)
    public void onStickyEventBusCome(EventMessage event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(EventMessage event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(EventMessage event) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
        //            EventBusUtils.unregister(this);
        //        }
        EventBusUtils.unregister(this);
    }


}
