package com.vice.bloodpressure.ui.activity.smarteducation;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.SmartEducationSubmitBeginTimeBean;
import com.vice.bloodpressure.bean.TabEntity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.fragment.smarteducation.SmartEducationClassDetailLeftFragment;
import com.vice.bloodpressure.ui.fragment.smarteducation.SmartEducationClassDetailRightFragment;
import com.vice.bloodpressure.utils.GlideUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 描述:  智能教育 之图文详情
 * 作者: LYD
 * 创建日期: 2020/8/20 9:35
 */
public class SmartEducationClassDetailVideoActivity extends BaseHandlerActivity {
    private static final int TO_SUBMIT_BEGIN_TIME = 10010;
    private static final int TO_SUBMIT_END_TIME = 10011;
    private static final int TO_SUBMIT_END_FAILED = 10012;
    @BindView(R.id.vp_detail)
    JzvdStd jzvdStd;
    @BindView(R.id.tbl_detail)
    CommonTabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tv_have_learn)
    ColorTextView tvHaveLearn;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = {"内容", "目录"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String learnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能教育");
        setTableLayout();
        setIsShowBottomButton();
        setVideo();
    }

    private void setVideo() {
        String videoUrl = getIntent().getExtras().getString("link");
        //String title = getIntent().getExtras().getString("title");
        jzvdStd.setUp(videoUrl, "");
        GlideUtils.loadCover(jzvdStd.posterImageView, videoUrl);
    }

    private void setTableLayout() {
        SmartEducationClassDetailLeftFragment leftFragment = new SmartEducationClassDetailLeftFragment();
        SmartEducationClassDetailRightFragment rightFragment = new SmartEducationClassDetailRightFragment();
        leftFragment.setArguments(getIntent().getExtras());
        rightFragment.setArguments(getIntent().getExtras());
        fragmentList.add(leftFragment);
        fragmentList.add(rightFragment);
        //ViewPager的适配器
        vpContent.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //设置标题
        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i]));
        }
        tlTab.setTabData(mTabEntities);
        tlTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpContent.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tlTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIsShowBottomButton() {
        int readTime = getIntent().getExtras().getInt("readTime", 0);
        String from = getIntent().getExtras().getString("from");
        switch (from) {
            case "1":
            case "2":
                tvHaveLearn.setVisibility(View.VISIBLE);
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .take(readTime + 1)
                        .map(aLong -> readTime - aLong)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> tvHaveLearn.setEnabled(false))
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long remindTime) {
                                SpanUtils.with(tvHaveLearn)
                                        .append("我已学完").setForegroundColor(ColorUtils.getColor(R.color.white_text))
                                        .append("(" + remindTime + "s)").setForegroundColor(ColorUtils.getColor(R.color.color_666))
                                        .create();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                tvHaveLearn.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                                tvHaveLearn.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white_text));
                                tvHaveLearn.setText("我已学完");
                                tvHaveLearn.setEnabled(true);
                            }
                        });
                toSubmitBeginTime();
                break;
            default:
                tvHaveLearn.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 提交开始时间
     */
    private void toSubmitBeginTime() {
        int id = getIntent().getExtras().getInt("id");
        String startTime = TimeUtils.getNowString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("startime", startTime);
        map.put("artid", id);
        XyUrl.okPost(XyUrl.SMART_EDUCATION_LEARN_TIME_CALCULATE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SmartEducationSubmitBeginTimeBean data = JSONObject.parseObject(value, SmartEducationSubmitBeginTimeBean.class);
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = TO_SUBMIT_BEGIN_TIME;
                handlerMessage.obj = data;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_education_class_detail_video, contentLayout, false);
        return view;
    }

    @OnClick({R.id.bt_back, R.id.tv_have_learn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                toDoBack();
                break;
            case R.id.tv_have_learn:
                toSubmitEndTime(3);
                break;
        }
    }


    /**
     * 提交结束时间
     *
     * @param status
     */
    private void toSubmitEndTime(int status) {
        int id = getIntent().getExtras().getInt("id");
        String endTime = TimeUtils.getNowString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("endtime", endTime);
        map.put("learid", learnId);
        map.put("artid", id);
        map.put("status", status);
        XyUrl.okPost(XyUrl.SMART_EDUCATION_LEARN_TIME_CALCULATE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(TO_SUBMIT_END_TIME);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(TO_SUBMIT_END_FAILED);
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case TO_SUBMIT_BEGIN_TIME:
                SmartEducationSubmitBeginTimeBean data = (SmartEducationSubmitBeginTimeBean) msg.obj;
                learnId = data.getLearid();
                break;
            case TO_SUBMIT_END_TIME:
                tvHaveLearn.setVisibility(View.GONE);
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_EDUCATION_REFRESH));
                break;
            case TO_SUBMIT_END_FAILED:
                break;
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }


    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toDoBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toDoBack() {
        String type = getIntent().getExtras().getString("from");
        if ("2".equals(type)) {
            toSubmitEndTime(0);
        }
        finish();
        EventBusUtils.post(new EventMessage(ConstantParam.SMART_EDUCATION_COURSE_REFRESH));
    }
}