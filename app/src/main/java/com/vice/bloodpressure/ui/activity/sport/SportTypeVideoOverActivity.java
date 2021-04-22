package com.vice.bloodpressure.ui.activity.sport;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.activity.MainActivity;

import butterknife.BindView;

/**
 * 描述:  视频之运动完成
 * 作者: LYD
 * 创建日期: 2020/9/30 10:21
 */
public class SportTypeVideoOverActivity extends BaseHandlerActivity {
    @BindView(R.id.img_type_bg)
    ImageView imgTypeBg;
    @BindView(R.id.tv_type_over)
    TextView tvTypeOver;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.tv_sport_type)
    TextView tvSportType;
    @BindView(R.id.tv_sport_time)
    TextView tvSportTime;
    @BindView(R.id.tv_sport_kcal)
    TextView tvSportKcal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("运动完成");
        getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishToActivity(MainActivity.class, false);
            }
        });
        setOverData();
    }

    private void setOverData() {
        int sportType = getIntent().getExtras().getInt("sportType");
        int successKcal = getIntent().getExtras().getInt("successKcal");
        String sportTypeStr = getIntent().getExtras().getString("sportTypeStr");
        String successSportTime = getIntent().getExtras().getString("successSportTime");
        tvCurrentTime.setText(TimeUtils.getNowString());
        tvSportType.setText(sportTypeStr);
        tvSportTime.setText("时长:" + successSportTime);
        tvSportKcal.setText("消耗:" + successKcal + "千卡");
        if (5 == sportType) {
            imgTypeBg.setImageResource(R.drawable.sport_over_bg_skip);
            tvTypeOver.setText("完成跳绳");
        } else {
            imgTypeBg.setImageResource(R.drawable.sport_over_bg_taiji);
            tvTypeOver.setText("完成太极");
        }
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_sport_type_video_over, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

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
            ActivityUtils.finishToActivity(MainActivity.class, false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}