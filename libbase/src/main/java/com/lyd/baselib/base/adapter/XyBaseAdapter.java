package com.lyd.baselib.base.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 描述: BaseAdapter
 * 作者: LYD
 * 所需参数: 无
 * 创建日期: 2018/10/31 16:10
 */
public abstract class XyBaseAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mList;

    public XyBaseAdapter(Context mContext, List<T> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    /**
     * 返回构造BaseAdapter的时候传入的Context对象
     *
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * 返回adapter绑定的数据源
     *
     * @return
     */
    public List<T> getList() {
        return mList;
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }


    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
