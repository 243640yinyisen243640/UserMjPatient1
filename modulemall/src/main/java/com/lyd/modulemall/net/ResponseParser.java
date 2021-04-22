package com.lyd.modulemall.net;


import android.content.Intent;

import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.constant.BaseConstantParam;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;
import rxhttp.wrapper.utils.Converter;

/**
 * 描述: Response<T> 数据解析器,解析完成对Response对象做判断,如果ok,返回数据 T
 * 作者: LYD
 * 创建日期: 2019/12/9 16:01
 */
@Parser(name = "Response", wrappers = {List.class})
public class ResponseParser<T> extends AbstractParser<T> {
    private static final String TAG = "ResponseParser";

    /**
     * 必须为protected
     */
    protected ResponseParser() {
        super();
    }

    public ResponseParser(Type type) {
        super(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        //获取泛型类型
        final Type type = ParameterizedTypeImpl.get(Response.class, mType);
        Response<T> data = Converter.convert(response, type);
        //获取Code,Msg以及Data
        T t = data.getData();
        int code = data.getCode();
        String msg = data.getMsg();
        if (BaseConstantParam.DEFAULT_TOKEN_EXPIRED == data.getCode()) {
            Intent intent = new Intent();
            intent.setAction("LoginOut");
            Utils.getApp().sendBroadcast(intent);
        }
        if (t == null && mType == String.class) {
            t = (T) data.getMsg();
        }
        if (data.getCode() != BaseConstantParam.DEFAULT_REQUEST_SUCCESS || t == null) {
            //code不等于200，说明数据不正确，抛出异常
            throw new ParseException(String.valueOf(code), msg, response);
        }
        return t;
    }
}
