package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.fragment.other.BloodPressureAddManualFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 添加BMI
 * 作者: LYD
 * 创建日期: 2020/9/8 17:34
 */
public class BmiAddActivity extends BaseHandlerEventBusActivity {
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tv_more_new)
    TextView tvBaseRight;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.tv_bmi_control_target_tips)
    TextView tvTargetTips;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] typeArray;
    private String height = "170";
    private String weight = "65";
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setTitle("手动记录");
        setFragment();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        time = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_blood_pressure_and_bmi_add, contentLayout, false);
    }


    @Override
    public void processHandlerMsg(Message msg) {

    }

    private void setFragment() {
        tvTargetTips.setText("您的BMI应该控制在:");
        tvTarget.setText("18.5-23.9");
        //添加分割线
        LinearLayout linearLayout = (LinearLayout) tlTab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(ConvertUtils.dp2px(15));//设置分割线高度
        //设置下划线宽度
        tlTab.setTabIndicatorFullWidth(false);
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
                //title.setTextAppearance(getPageContext(), R.style.Tab_Text_Select);
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
        typeArray = getResources().getStringArray(R.array.bmi_add_title);
        Fragment fragment = new BloodPressureAddManualFragment(true);
        fragmentList.add(fragment);
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        vpContent.setOffscreenPageLimit(1);//ButterKnife报错处理
        tlTab.setupWithViewPager(vpContent);
    }

    @OnClick({R.id.bt_back_new, R.id.tv_more_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back_new:
                finish();
                break;
            case R.id.tv_more_new:
                saveData();
                break;
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("height", height);
        map.put("weight", weight);
        map.put("datetime", time);
        XyUrl.okPostSave(XyUrl.ADD_BMI, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(R.string.save_ok);
                EventBusUtils.post(new EventMessage<>(ConstantParam.BMI_RECORD_ADD));
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
            case ConstantParam.BMI_HEIGHT:
                String temp_height = event.getMsg();
                height = temp_height.replace(".0", "");
                break;
            case ConstantParam.BMI_WEIGHT:
                String temp_weight = event.getMsg();
                weight = temp_weight.replace(".0", "");
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
