package com.vice.bloodpressure.ui.activity.sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.utils.RxTimerUtils;

import butterknife.BindView;

/**
 * 描述:  倒计时 (3 2 1)
 * 作者: LYD
 * 创建日期: 2020/9/25 18:01
 */
public class HomeSportCountDownActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸状态栏
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.main_home).init();
        hideTitleBar();
        setTimeCountDown();
    }

    private void setTimeCountDown() {
        setCountDownView("3");

        RxTimerUtils rxTimer0 = new RxTimerUtils();
        rxTimer0.timer(1000, new RxTimerUtils.RxAction() {
            @Override
            public void action(long number) {
                setCountDownView("2");
            }
        });

        RxTimerUtils rxTimer1 = new RxTimerUtils();
        rxTimer1.timer(2000, new RxTimerUtils.RxAction() {
            @Override
            public void action(long number) {
                setCountDownView("1");
            }
        });

        RxTimerUtils rxTimer2 = new RxTimerUtils();
        rxTimer2.timer(3000, new RxTimerUtils.RxAction() {
            @Override
            public void action(long number) {
                //三秒以后跳转页面
                finish();
                Intent intent = new Intent(getPageContext(), SportTypeVideoPlayingActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_home_sport_count_down, contentLayout, false);
        return view;
    }


    private void setCountDownView(String text) {
        ScaleAnimation animation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置持续时间
        animation.setDuration(1000);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        animation.setFillAfter(true);
        //设置循环次数，0为1次
        animation.setRepeatCount(0);
        tvTime.setText(text);
        tvTime.startAnimation(animation);
    }
}