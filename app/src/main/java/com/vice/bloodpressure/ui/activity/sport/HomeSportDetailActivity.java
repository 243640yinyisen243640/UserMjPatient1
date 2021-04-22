package com.vice.bloodpressure.ui.activity.sport;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.HomeSportListBean;

import java.util.List;

import butterknife.BindView;

/**
 * 描述:  运动方案详情
 * 作者: LYD
 * 创建日期: 2020/9/25 11:17
 */
public class HomeSportDetailActivity extends BaseHandlerActivity {
    @BindView(R.id.tv_state_text)
    TextView tvStateText;
    @BindView(R.id.tv_state_text_desc)
    TextView tvStateTextDesc;
    @BindView(R.id.img_undone)
    ImageView imgUndone;
    @BindView(R.id.img_good)
    ImageView imgGood;
    @BindView(R.id.img_over)
    ImageView imgOver;
    @BindView(R.id.img_line_left)
    ImageView imgLineLeft;
    @BindView(R.id.img_line_center)
    ImageView imgLineCenter;
    @BindView(R.id.img_line_right)
    ImageView imgLineRight;
    @BindView(R.id.tv_sport_detail_text)
    TextView tvSportDetailText;
    @BindView(R.id.tv_sport_detail_time)
    TextView tvSportDetailTime;
    @BindView(R.id.tv_sport_detail_desc)
    TextView tvSportDetailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("每日运动");
        setDetail();
    }

    private void setDetail() {
        HomeSportListBean sportListBean = (HomeSportListBean) getIntent().getSerializableExtra("sportListBean");
        //1 未完成  2 完成  3超额
        String result = sportListBean.getResult();
        switch (result) {
            case "1":
                tvStateText.setText("未完成");
                tvStateTextDesc.setText("很遗憾！您未能完成今日的运动计划，仍需再接再厉！");
                imgUndone.setImageResource(R.drawable.sport_detail_state_undone_checked);
                imgGood.setImageResource(R.drawable.sport_detail_state_good_uncheck);
                imgOver.setImageResource(R.drawable.sport_detail_state_over_uncheck);
                imgLineLeft.setImageResource(R.drawable.sport_detail_line_checked);
                imgLineCenter.setImageResource(R.drawable.sport_detail_line_uncheck);
                imgLineRight.setImageResource(R.drawable.sport_detail_line_uncheck);
                break;
            case "2":
                tvStateText.setText("完成");
                tvStateTextDesc.setText("非常好！您完成了今日的运动计划，请继续保持哦！");
                imgUndone.setImageResource(R.drawable.sport_detail_state_undone_uncheck);
                imgGood.setImageResource(R.drawable.sport_detail_state_good_checked);
                imgOver.setImageResource(R.drawable.sport_detail_state_over_uncheck);
                imgLineLeft.setImageResource(R.drawable.sport_detail_line_uncheck);
                imgLineCenter.setImageResource(R.drawable.sport_detail_line_checked);
                imgLineRight.setImageResource(R.drawable.sport_detail_line_uncheck);
                break;
            case "3":
                tvStateText.setText("超额完成");
                tvStateTextDesc.setText("好尴尬！您超额完成了今日的运动计划，请适量运动哦！");
                imgUndone.setImageResource(R.drawable.sport_detail_state_undone_uncheck);
                imgGood.setImageResource(R.drawable.sport_detail_state_good_uncheck);
                imgOver.setImageResource(R.drawable.sport_detail_state_over_checked);
                imgLineLeft.setImageResource(R.drawable.sport_detail_line_uncheck);
                imgLineCenter.setImageResource(R.drawable.sport_detail_line_uncheck);
                imgLineRight.setImageResource(R.drawable.sport_detail_line_checked);
                break;
        }
        List<HomeSportListBean.SportsBean> sports = sportListBean.getSports();
        String time = sportListBean.getTime();
        String message = sportListBean.getMessage();
        StringBuilder typeStr = new StringBuilder();
        if (sports != null && sports.size() > 0) {
            for (int i = 0; i < sports.size(); i++) {
                String sportType = sports.get(i).getSportType();
                if ("步数".equals(sportType)) {
                    typeStr.append("步行");
                } else {
                    typeStr.append(sportType);
                }
                typeStr.append("\u3000");
            }
        }
        tvSportDetailText.setText(typeStr);
        tvSportDetailTime.setText(time);
        tvSportDetailDesc.setText(message);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_home_sport_detail, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

}