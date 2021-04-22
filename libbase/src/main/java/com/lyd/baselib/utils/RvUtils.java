package com.lyd.baselib.utils;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 解决ScrollView嵌套Rv滑动卡顿问题
 * 参考: https://segmentfault.com/a/1190000011553735
 */
public class RvUtils {
    private RvUtils() {
    }

    /**
     * 设置多个RecycleviewForScrollView
     *
     * @param list
     */
    public static void setRvForScrollViewList(List<RecyclerView> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setHasFixedSize(true);
            list.get(i).setNestedScrollingEnabled(false);
        }
    }

    /**
     * 设置单个RecycleviewForScrollView
     *
     * @param rv
     */
    public static void setRvForScrollView(RecyclerView rv) {
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);

    }


}
