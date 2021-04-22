package com.vice.bloodpressure.base.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vice.bloodpressure.imp.HandlerImp;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;


/**
 * 描述: 基础Fragment
 * 作者: LYD
 * 创建日期: 2018/9/5 17:41
 */

public abstract class BaseFragment extends Fragment implements HandlerImp {

    public View rootView;
    Unbinder unbinder;
    //封装下Handler//
    private Context mContext;
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


    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getContext();
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        init(rootView);
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void init(View rootView);


    /**
     *
     * @return
     */
    public Context getPageContext() {
        return mContext;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定
        //unbinder.unbind();
    }


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
        return new Message();
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
