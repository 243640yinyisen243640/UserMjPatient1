package com.vice.bloodpressure.ui.fragment.main;

import android.os.Message;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.imuxuan.floatingview.FloatingView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;


/**
 * 描述:  商城Fragment 没有开放
 * 作者: LYD
 * 创建日期: 2019/10/18 10:17
 */
public class MallHomeNotOpenFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_home_not_open;
    }

    @Override
    protected void init(View rootView) {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();

        FloatingView.get().remove();
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }


}
