package com.vice.bloodpressure.ui.activity.sport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.libsteps.StepUtil;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.HomeSportTypeVideoSuccessBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.RxTimerUtils;
import com.vice.bloodpressure.view.video.JZMediaExo;
import com.vice.bloodpressure.view.video.MyJzvdForSport;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 描述:  运动类型之视频正在播放
 * 作者: LYD
 * 创建日期: 2020/9/30 11:37
 */
public class SportTypeVideoPlayingActivity extends BaseHandlerActivity {
    private static final int ADD_SUCCESS = 10010;
    @BindView(R.id.jzvd_for_sport)
    MyJzvdForSport jzvdStd;
    @BindView(R.id.img_gif)
    GifImageView imgGif;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_sport_name)
    TextView tvSportName;

    /**
     * 正计时
     */
    private int timeSS = 0;
    Handler handler = new Handler();

    @Override
    @SuppressLint("HandlerLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String sportTypeStr = getIntent().getExtras().getString("sportTypeStr");
        setTitle(sportTypeStr);
        tvSportName.setText(sportTypeStr);
        handler.postDelayed(runnable, 0);
        int sportType = getIntent().getExtras().getInt("sportType");
        if (5 == sportType) {
            jzvdStd.setVisibility(View.GONE);
            imgGif.setVisibility(View.VISIBLE);
            try {
                GifDrawable gifDrawable = new GifDrawable(Utils.getApp().getResources(), R.drawable.home_sport_skipping);
                // gif1加载一个动态图gif
                imgGif.setImageDrawable(gifDrawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            jzvdStd.setVisibility(View.VISIBLE);
            imgGif.setVisibility(View.GONE);
            //全屏显示
            Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER);
            //循环播放
            JZDataSource jzDataSource = new JZDataSource("http://video.xiyuns.cn/1602756485000.mp4");
            jzDataSource.looping = true;
            //设置播放内核ExoPlayer
            jzvdStd.setUp(jzDataSource, JzvdStd.SCREEN_FULLSCREEN, JZMediaExo.class);
            //自动播放
            jzvdStd.startVideoAfterPreloading();
            //不保存进度
            jzvdStd.SAVE_PROGRESS = false;
        }
        //设置倒计时
        setTimer();
    }


    /**
     * 倒计时(或者比较两个时间字符串一样的时候)
     */
    private void setTimer() {
        String sportTime = getIntent().getExtras().getString("sportTime");
        String[] my = sportTime.split(":");
        int hour = Integer.parseInt(my[0]);
        int min = Integer.parseInt(my[1]);
        int second = Integer.parseInt(my[2]);
        int totalSecond = hour * 3600 + min * 60 + second;
        RxTimerUtils rxTimer = new RxTimerUtils();
        rxTimer.timer(totalSecond * 1000, new RxTimerUtils.RxAction() {
            @Override
            public void action(long number) {
                //getLvData();
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_sport_type_video_playing, contentLayout, false);
        return view;
    }


    @OnClick(R.id.rl_over)
    public void onViewClicked() {
        toDoSubmit();
    }

    private void toDoSubmit() {
        int sportType = getIntent().getExtras().getInt("sportType");
        int todayStep = StepUtil.getTodayStep(getPageContext());
        HashMap map = new HashMap<>();
        map.put("sportType", sportType);
        map.put("seconds", timeSS);
        map.put("steps", todayStep);
        XyUrl.okPost(XyUrl.INDEX_ADD_SPORT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                HomeSportTypeVideoSuccessBean successBean = JSONObject.parseObject(value, HomeSportTypeVideoSuccessBean.class);
                Message message = Message.obtain();
                message.what = ADD_SUCCESS;
                message.obj = successBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    //    @Override
    //    public void onBackPressed() {
    //        if (Jzvd.backPress()) {
    //            return;
    //        }
    //        super.onBackPressed();
    //    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    /**
     * 正计时
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String sportTime = getIntent().getExtras().getString("sportTime");
            timeSS++;
            String playingTime = secToTime(timeSS);
            tvTime.setText(playingTime + "/" + sportTime);
            if (playingTime.equals(sportTime)) {
                toDoSubmit();
            }
            handler.postDelayed(this, 1000);
        }
    };

    public static String secToTime(int time) {
        String timeStr = "";
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            hour = minute / 60;
            minute = minute % 60;
            second = time - hour * 3600 - minute * 60;
            timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case ADD_SUCCESS:
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_SPORT_QUESTION_SUBMIT));
                HomeSportTypeVideoSuccessBean successBean = (HomeSportTypeVideoSuccessBean) msg.obj;
                int kcaling = successBean.getKcaling();
                String sportTime = successBean.getSportTime();
                Bundle bundle = getIntent().getExtras();
                bundle.putInt("successKcal", kcaling);
                bundle.putString("successSportTime", sportTime);
                Intent intent = new Intent(getPageContext(), SportTypeVideoOverActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}