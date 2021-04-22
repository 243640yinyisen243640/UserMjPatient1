package com.vice.bloodpressure.ui.activity.sport.map;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.utils.GraduallyShowAndHideUtils;
import com.vice.bloodpressure.utils.RxTimerUtils;
import com.wei.android.lib.colorview.view.ColorRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 跑步中
 * 作者: LYD
 * 创建日期: 2020/9/28 11:01
 */
public class SportTypeMapRunningActivity extends BaseHandlerActivity {
    @BindView(R.id.tv_running_distance)
    TextView tvRunningDistance;
    @BindView(R.id.tv_speed)
    TextView tvSpeed;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_kcal)
    TextView tvKcal;
    @BindView(R.id.rl_pause)
    ColorRelativeLayout rlPause;
    @BindView(R.id.ll_continue_and_over)
    LinearLayout llContinueAndOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("跑步中");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_sport_type_map_running, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }


    @OnClick(R.id.rl_pause)
    public void onViewClicked() {
        //rlPause.setVisibility(View.GONE);
        GraduallyShowAndHideUtils alphaAnimationUtils = new GraduallyShowAndHideUtils();
        alphaAnimationUtils.setHideAnimation(rlPause, 250);

        RxTimerUtils rxTimer0 = new RxTimerUtils();
        rxTimer0.timer(250, new RxTimerUtils.RxAction() {
            @Override
            public void action(long number) {
                alphaAnimationUtils.setShowAnimation(llContinueAndOver, 250);
            }
        });
    }
}