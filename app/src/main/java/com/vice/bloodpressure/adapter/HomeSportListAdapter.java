package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.HomeSportListBean;

import java.util.List;

public class HomeSportListAdapter extends BaseQuickAdapter<HomeSportListBean, BaseViewHolder> {
    private Context context;

    public HomeSportListAdapter(Context context, @Nullable List<HomeSportListBean> data) {
        super(R.layout.item_home_sport_list, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, HomeSportListBean homeSportListBean) {
        String time = homeSportListBean.getTime();
        String totalKcal = homeSportListBean.getTotalkcal();
        viewHolder.setText(R.id.tv_time, time);
        TextView tvTotalKcal = viewHolder.getView(R.id.tv_total_kcal);
        SpanUtils.with(tvTotalKcal)
                .append("总消耗").setForegroundColor(ColorUtils.getColor(R.color.black_text))
                .appendSpace(15, Color.TRANSPARENT)
                .append(totalKcal).setForegroundColor(ColorUtils.getColor(R.color.home_sport_list_red))
                .append("千卡").setForegroundColor(ColorUtils.getColor(R.color.home_sport_list_red))
                .create();
        //设置列表
        List<HomeSportListBean.SportsBean> sports = homeSportListBean.getSports();
        RecyclerView rvList = viewHolder.getView(R.id.rv_sport_type_list);
        if (sports != null && sports.size() > 0) {
            rvList.setLayoutManager(new LinearLayoutManager(context));
            rvList.setAdapter(new HomeSportListChildAdapter(sports));
        }
        //        //点击进详情
        //        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Intent intent = new Intent(Utils.getApp(), HomeSportDetailActivity.class);
        //                intent.putExtra("sportListBean", homeSportListBean);
        //                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                Utils.getApp().startActivity(intent);
        //            }
        //        });
    }
}
