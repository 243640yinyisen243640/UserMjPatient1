package com.lyd.modulemall.ui.activity.productlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.edittext.EditTextUtils;
import com.lyd.modulemall.R;
import com.lyd.modulemall.databinding.ActivityMallSearchBinding;
import com.zackratos.ultimatebarx.library.UltimateBarX;

/**
 * 描述:  搜索页面
 * 作者: LYD
 * 创建日期: 2020/12/24 14:11
 */
public class MallSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMallSearchBinding mallSearchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mallSearchBinding = ActivityMallSearchBinding.inflate(getLayoutInflater());
        mallSearchBinding.etSearch.requestFocus();
        setContentView(mallSearchBinding.getRoot());
        initStatusBar();
        initListener();
        initEtSearch();
    }

    private void initStatusBar() {
        UltimateBarX.with(this)
                .fitWindow(true)
                .colorRes(R.color.white)
                .light(true)
                .applyStatusBar();
    }

    private void initEtSearch() {
        mallSearchBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mallSearchBinding.imgSearchClear.setVisibility(View.VISIBLE);
                } else {
                    mallSearchBinding.imgSearchClear.setVisibility(View.GONE);
                }
            }
        });
        EditTextUtils.setOnActionSearch(mallSearchBinding.etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                String searchStr = mallSearchBinding.etSearch.getText().toString();
                if (TextUtils.isEmpty(searchStr)) {
                    ToastUtils.showShort("请输入搜索内容");
                } else {
                    //跳转页面并搜索
                    Intent intent = new Intent(MallSearchActivity.this, MallProductListActivity.class);
                    intent.putExtra("searchKeyWord", searchStr);
                    startActivity(intent);
                }
            }
        });

    }


    private void initListener() {
        mallSearchBinding.tvCancel.setOnClickListener(this);
        mallSearchBinding.imgSearchClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_cancel) {
            finish();
        } else if (view.getId() == R.id.img_search_clear) {
            mallSearchBinding.etSearch.setText("");
        }
    }

//    /**
//     * 重写 getResource 方法，防止系统字体影响
//     * 禁止app字体大小跟随系统字体大小调节
//     */
//    @Override
//    public Resources getResources() {
//        Resources resources = super.getResources();
//        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
//            Configuration configuration = resources.getConfiguration();
//            configuration.setToDefaults();
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
//        return resources;
//    }
}