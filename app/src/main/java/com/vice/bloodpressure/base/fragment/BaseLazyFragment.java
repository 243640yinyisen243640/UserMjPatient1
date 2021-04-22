package com.vice.bloodpressure.base.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 描述:  懒加载Fragment
 * 作者: LYD
 * 创建日期: 2019/7/11 9:39
 */
public abstract class BaseLazyFragment extends BaseFragment {
    //public final String TAG = getClass().getSimpleName();
    public boolean mHaveLoadData;//表示是否请求过数据

    public abstract void loadData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //如果还没有加载过数据 && 用户切换到了这个fragment
        //那就开始加载数据
        if (!mHaveLoadData && isVisibleToUser) {
            loadData();
            //mHaveLoadData = true;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(EventMessage event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
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
    public void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
    }
}
