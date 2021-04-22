package com.vice.bloodpressure.utils.rxhttputils;

import com.allen.library.base.BaseObserver;
import com.allen.library.bean.BaseData;

import io.reactivex.disposables.Disposable;

public abstract class CustomDataObserver<T> extends BaseObserver<BaseData<T>> {


    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);

    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
    }

    @Override
    public void doOnNext(BaseData<T> data) {
        //onSuccess(data.getData());
        //可以根据需求对code统一处理
        switch (data.getCode()) {
            case 200:
                onSuccess(data.getData());
                break;
            //            default:
            //                onError(data.getMsg());
            //                break;
        }
    }

    @Override
    public void doOnCompleted() {
    }
}
