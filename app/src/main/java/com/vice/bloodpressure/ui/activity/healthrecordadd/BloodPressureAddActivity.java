package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.ScopeBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.fragment.other.BloodPressureAddManualFragment;
import com.vice.bloodpressure.view.NoScrollViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 添加血压
 * 作者: LYD
 * 创建日期: 2019/7/8 15:29
 */
public class BloodPressureAddActivity extends BaseHandlerEventBusActivity {
    private static final int GET_SCOPE = 10010;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    NoScrollViewPager vpContent;
    @BindView(R.id.tv_more_new)
    TextView tvBaseRight;
    @BindView(R.id.tv_target)
    TextView tvTarget;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] typeArray;
    private String high = "120";
    private String low = "80";
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setFragment();
        getTarget();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        time = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
    }

    private void getTarget() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_USERDATE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ScopeBean scope = JSONObject.parseObject(value, ScopeBean.class);
                Message message = Message.obtain();
                message.what = GET_SCOPE;
                message.obj = scope;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_blood_pressure_and_bmi_add, contentLayout, false);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SCOPE:
                ScopeBean targetBean = (ScopeBean) msg.obj;
                tvTarget.setText(targetBean.getBp());
                break;
        }
    }

    private void setFragment() {
        //设置字体大小
        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (0 == position) {
                    tvBaseRight.setVisibility(View.VISIBLE);
                } else {
                    tvBaseRight.setVisibility(View.GONE);
                }
                TextView title = (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextSize(18);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView title = (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextSize(16);
                title.setTextAppearance(getPageContext(), R.style.Tab_Text_UnSelect);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //设置Fragment
        typeArray = getResources().getStringArray(R.array.blood_pressure_title);
        fragmentList.add(new BloodPressureAddManualFragment());
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        vpContent.setOffscreenPageLimit(1);
        tlTab.setupWithViewPager(vpContent);
    }

    @OnClick({R.id.bt_back_new, R.id.tv_more_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back_new:
                finish();
                break;
            case R.id.tv_more_new:
                toDoSubmit();
                break;
        }
    }

    private void toDoSubmit() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        map.put("systolic", high);//收缩
        map.put("diastole", low);//舒张
        map.put("heartrate", "");
        map.put("datetime", time);
        map.put("type", "2");
        XyUrl.okPostSave(XyUrl.ADD_BLOOD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(R.string.save_ok);
                EventBusUtils.post(new EventMessage<>(ConstantParam.BLOOD_PRESSURE_RECORD_ADD));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.BLOOD_PRESSURE_ADD_HIGH:
                high = event.getMsg();
                break;
            case ConstantParam.BLOOD_PRESSURE_ADD_LOW:
                low = event.getMsg();
                break;
            case ConstantParam.BLOOD_PRESSURE_ADD_TIME:
                time = event.getMsg();
                break;
        }
    }

    private class TabAdapter extends FragmentPagerAdapter {

        private TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        //设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return typeArray[position];
        }

    }
}
