package com.lyd.baselib.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 描述: 解决ListView只显示一行, 高度不能完全展开(重写onMeasure)
 * 作者: LYD
 * 创建日期: 2019/12/13 14:54
 */
public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
