package com.vice.bloodpressure.net;

import org.json.JSONException;

/**
 * Created by qjx on 2018/5/22.
 */

public abstract class OkHttpCallBack<T> {
    public abstract void onSuccess(T value);

    public abstract void onError(final int errorCode, final String errorMsg) throws JSONException;
}
