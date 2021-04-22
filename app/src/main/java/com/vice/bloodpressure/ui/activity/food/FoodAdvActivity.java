package com.vice.bloodpressure.ui.activity.food;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

public class FoodAdvActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("TITLE");
        setTitle(title);
        initWebView();
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_foodadv, contentLayout, false);
    }

    private void initWebView() {
        WebView webView = findViewById(R.id.wv_foodAdv);
        String content = getIntent().getStringExtra("CONTENT");
        webView.loadData(content, "text/html; charset=UTF-8", null);
    }

}
