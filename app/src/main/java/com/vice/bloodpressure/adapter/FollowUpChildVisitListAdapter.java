package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FollowUpVisitListBean;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmitActivity;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmitActivity;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FollowUpChildVisitListAdapter extends CommonAdapter<FollowUpVisitListBean.DataBean.PlanListBean> {
    private String type;

    public FollowUpChildVisitListAdapter(Context context, int layoutId, List<FollowUpVisitListBean.DataBean.PlanListBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, FollowUpVisitListBean.DataBean.PlanListBean item, int position) {
        String addtime = item.getAddtime();
        viewHolder.setText(R.id.tv_time, addtime);
        String status = item.getStatus() + "";
        switch (status) {
            case "2"://待开启
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_doing);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "待开启");
                break;
            case "3"://未完成
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_un_done);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "未完成");
                break;
            case "4"://已完成,等待医生总结
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_done_wait_doctor);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "已完成,等待医生总结");
                break;
            case "5"://已完成,查看随访报告
                viewHolder.setImageResource(R.id.img_follow_up_visit_state, R.drawable.follow_up_visit_done_look);
                viewHolder.setText(R.id.tv_follow_up_visit_desc, "已完成,查看随访报告");
                break;
        }
        //点击进详情
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(type)) {
                    Intent intent = new Intent(Utils.getApp(), FollowUpVisitBloodSugarSubmitActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("status", status);
                    intent.putExtra("id", item.getId() + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else if ("2".equals(type)) {
                    Intent intent = new Intent(Utils.getApp(), FollowUpVisitBloodPressureSubmitActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("status", status);
                    intent.putExtra("id", item.getId() + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    Intent intent = new Intent(Utils.getApp(), FollowUpVisitHepatopathySubmitActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("status", status);
                    intent.putExtra("id", item.getId() + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            }
        });
    }
}
