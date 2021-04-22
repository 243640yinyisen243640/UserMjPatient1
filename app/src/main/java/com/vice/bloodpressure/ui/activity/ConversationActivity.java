package com.vice.bloodpressure.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.KeyboardUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

/**
 * 描述: 聊天页面
 * 作者: LYD
 * 创建日期: 2019/3/20 16:33
 */
public class ConversationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardUtils.fixAndroidBug5497(this);
        String title = getIntent().getData().getQueryParameter("title");
        setTitle(title);
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_conversation, contentLayout, false);
        return layout;
    }
}
