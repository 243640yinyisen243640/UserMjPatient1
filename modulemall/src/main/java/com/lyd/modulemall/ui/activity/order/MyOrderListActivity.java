package com.lyd.modulemall.ui.activity.order;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.DisableDisplayDpiChangeUtils;
import com.lyd.baselib.utils.edittext.EditTextUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.databinding.ActivityMyOrderListBinding;
import com.lyd.modulemall.ui.fragment.MyOrderListFragment;
import com.lyd.modulemall.ui.fragment.MyRefundOrderListFragment;
import com.lyd.modulemall.utils.RxHttpPublicParamsAddUtils;
import com.zackratos.ultimatebarx.library.UltimateBarX;

import java.util.ArrayList;

/**
 * 描述:  订单列表 主页面
 * 作者: LYD
 * 创建日期: 2021/1/6 13:11
 */
public class MyOrderListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MyOrderListActivity";
    private ActivityMyOrderListBinding myOrderListBinding;

    //当前
    private int currentPosition;
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] listTitle = new String[]{"全部", "待付款", "待发货", "待收货", "已完成", "退款/退货"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxHttpPublicParamsAddUtils.initRxHttp();
        myOrderListBinding = ActivityMyOrderListBinding.inflate(getLayoutInflater());
        setContentView(myOrderListBinding.getRoot());
        setViewBinding();
        setTabLayout();
        initStatusBar();
        addEdTextChanged();
        DisableDisplayDpiChangeUtils.disabledDisplayDpiChange(this);
    }

    private void addEdTextChanged() {
        myOrderListBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    myOrderListBinding.imgSearchClear.setVisibility(View.VISIBLE);
                } else {
                    myOrderListBinding.imgSearchClear.setVisibility(View.GONE);
                }
            }
        });
        EditTextUtils.setOnActionSearch(myOrderListBinding.etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                String keyWord = myOrderListBinding.etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtils.showShort("请输入搜索内容");
                    return;
                }
                EventBusUtils.post(new EventMessage(BaseConstantParam.EventCode.MALL_ORDER_SEARCH, keyWord, currentPosition + ""));
            }
        });
    }

    private void initStatusBar() {
        UltimateBarX.with(this)
                .fitWindow(true)
                .colorRes(R.color.white)
                .light(true)
                .applyStatusBar();
    }


    private void setViewBinding() {
        myOrderListBinding.imgBack.setOnClickListener(this);
        myOrderListBinding.imgSearchClear.setOnClickListener(this);
    }

    private void setTabLayout() {
        for (int i = 0; i < listTitle.length - 1; i++) {
            //设置Fragment
            MyOrderListFragment childFragment = new MyOrderListFragment();
            Bundle bundle = new Bundle();
            if (4 == i) {
                //已完成为5
                bundle.putInt("order_status", 5);
            } else {
                //0全部 1待付款 2待发货 3待收货
                bundle.putInt("order_status", i);
            }
            bundle.putString("position", i + "");
            childFragment.setArguments(bundle);
            listFragment.add(childFragment);
        }
        //退款Fragment
        Bundle refundBundle = new Bundle();
        MyRefundOrderListFragment myRefundOrderListFragment = new MyRefundOrderListFragment();
        refundBundle.putString("position", 5 + "");
        myRefundOrderListFragment.setArguments(refundBundle);
        listFragment.add(myRefundOrderListFragment);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        myOrderListBinding.vpList.setAdapter(adapter);
        myOrderListBinding.tblList.setupWithViewPager(myOrderListBinding.vpList);
        //设置选中
        //TabLayout切换监听
        myOrderListBinding.tblList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                Log.e(TAG, "当前Activity选中的Position==" + currentPosition);
                //EventBusUtils.post(new EventMessage(BaseConstantParam.EventCode.MALL_ORDER_SEARCH, keyWord, currentPosition + ""));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            finish();
        } else if (id == R.id.img_search_clear) {
            myOrderListBinding.etSearch.setText("");
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
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