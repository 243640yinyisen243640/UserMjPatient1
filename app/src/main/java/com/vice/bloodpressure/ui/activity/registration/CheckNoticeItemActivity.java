package com.vice.bloodpressure.ui.activity.registration;

import android.os.Bundle;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

/**
 * 描述: 体检注意事项
 * 作者: LYD
 * 创建日期: 2019/10/30 17:55
 */
public class CheckNoticeItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("体检注意事项");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_check_notice_item, contentLayout, false);
        return view;
    }
}
