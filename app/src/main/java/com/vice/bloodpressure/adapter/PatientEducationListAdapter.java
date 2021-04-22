package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.PatientEducationListBean;
import com.vice.bloodpressure.ui.activity.patienteducation.PatientEducationAudioActivity;
import com.vice.bloodpressure.ui.activity.patienteducation.PatientEducationVideoActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class PatientEducationListAdapter extends CommonAdapter<PatientEducationListBean> {
    public PatientEducationListAdapter(Context context, int layoutId, List<PatientEducationListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, PatientEducationListBean item, int position) {
        //1视频 2音频 3图文
        viewHolder.setText(R.id.tv_year, item.getYear());
        viewHolder.setText(R.id.tv_month_day, item.getTime());
        int type = item.getType();
        switch (type) {
            case 1:
                viewHolder.setImageResource(R.id.img_type, R.drawable.patient_education_video);
                break;
            case 2:
                viewHolder.setImageResource(R.id.img_type, R.drawable.patient_education_music);
                break;
            case 3:
                viewHolder.setImageResource(R.id.img_type, R.drawable.patient_education_text);
                break;
        }
        viewHolder.setText(R.id.tv_title, item.getTitle());
        viewHolder.setText(R.id.tv_sub_title, item.getSubtitle());
        //跳转
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(Utils.getApp(), PatientEducationVideoActivity.class);
                        intent.putExtra("url", item.getLinks());
                        intent.putExtra("videoUrl", item.getUrl());
                        intent.putExtra("title", item.getTitle());
                        break;
                    case 2:
                        intent = new Intent(Utils.getApp(), PatientEducationAudioActivity.class);
                        intent.putExtra("title", item.getTitle());
                        intent.putExtra("id", item.getId());
                        intent.putExtra("url", item.getLinks());
                        intent.putExtra("audioUrl", item.getUrl());
                        intent.putExtra("audioDuration", item.getDuration());
                        break;
                    case 3:
                        intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                        intent.putExtra("title", item.getTitle());
                        intent.putExtra("url", item.getLinks());
                        break;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
