package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SmartEducationLearnListBean;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailAudioActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailTextActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailVideoActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class SmartEducationLearnListChildAdapter extends CommonAdapter<SmartEducationLearnListBean.ListBean.ClassesBean> {

    private int learnState;

    public SmartEducationLearnListChildAdapter(Context context, int layoutId, List<SmartEducationLearnListBean.ListBean.ClassesBean> datas, int learnState) {
        super(context, layoutId, datas);
        this.learnState = learnState;
    }

    @Override
    protected void convert(ViewHolder viewHolder, SmartEducationLearnListBean.ListBean.ClassesBean item, int position) {
        TextView tvClassName = viewHolder.getView(R.id.tv_class_name);
        tvClassName.setText(item.getArtname());
        TextView tvClassState = viewHolder.getView(R.id.tv_class_state);
        if (1 == learnState) {
            tvClassState.setVisibility(View.VISIBLE);
            int status = item.getStatus();
            //文章状态 1 待学习  2 学习中  3 已完成
            switch (status) {
                case 1:
                    tvClassName.setTextColor(ColorUtils.getColor(R.color.smart_education_class_name_black));
                    tvClassState.setText("待学习");
                    tvClassState.setTextColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning));
                    break;
                case 2:
                    tvClassName.setTextColor(ColorUtils.getColor(R.color.smart_education_class_name_black));
                    tvClassState.setText("学习中");
                    tvClassState.setTextColor(ColorUtils.getColor(R.color.smart_education_class_state_learning));
                    break;
                case 3:
                    tvClassName.setTextColor(ColorUtils.getColor(R.color.smart_education_class_name_gray));
                    tvClassState.setText("已完成");
                    tvClassState.setTextColor(ColorUtils.getColor(R.color.smart_education_class_state_have_learn));
                    break;
            }
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1文本  2音频  3视频
                int type = item.getType();
                int id = item.getId();
                int cId = item.getCid();
                int status = item.getStatus();
                String link = item.getLinks();
                String webLink = item.getWeblink();
                String duration = item.getDuration();
                int readtime = item.getReadtime();
                Bundle bundle = new Bundle();
                bundle.putInt("cId", cId);
                bundle.putInt("id", id);
                bundle.putString("link", link);
                bundle.putString("webLink", webLink);
                bundle.putString("duration", duration);
                bundle.putString("from", status + "");
                bundle.putInt("type", type);
                bundle.putInt("readTime", readtime);
                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(Utils.getApp(), SmartEducationClassDetailTextActivity.class);
                        break;
                    case 2:
                        intent = new Intent(Utils.getApp(), SmartEducationClassDetailAudioActivity.class);
                        break;
                    case 3:
                        intent = new Intent(Utils.getApp(), SmartEducationClassDetailVideoActivity.class);
                        break;
                }
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
