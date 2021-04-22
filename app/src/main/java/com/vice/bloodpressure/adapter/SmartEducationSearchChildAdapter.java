package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SmartEducationSearchListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class SmartEducationSearchChildAdapter extends CommonAdapter<SmartEducationSearchListBean.ArtlistBean> {
    public SmartEducationSearchChildAdapter(Context context, int layoutId, List<SmartEducationSearchListBean.ArtlistBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SmartEducationSearchListBean.ArtlistBean item, int position) {
        TextView tvClassName = viewHolder.getView(R.id.tv_class_name);
        tvClassName.setText(item.getArtname());
        TextView tvClassState = viewHolder.getView(R.id.tv_class_state);
        //        int status = item.getStatus();
        //        //文章状态 1 待学习  2 学习中  3 已完成
        //        switch (status) {
        //            case 1:
        //                tvClassName.setTextColor(ColorUtils.getColor(R.color.smart_education_class_name_black));
        //                tvClassState.setText("待学习");
        //                tvClassState.setTextColor(ColorUtils.getColor(R.color.smart_education_class_state_not_learning));
        //                break;
        //            case 2:
        //                tvClassName.setTextColor(ColorUtils.getColor(R.color.smart_education_class_name_black));
        //                tvClassState.setText("学习中");
        //                tvClassState.setTextColor(ColorUtils.getColor(R.color.smart_education_class_state_learning));
        //                break;
        //            case 3:
        //                tvClassName.setTextColor(ColorUtils.getColor(R.color.smart_education_class_name_gray));
        //                tvClassState.setText("已完成");
        //                tvClassState.setTextColor(ColorUtils.getColor(R.color.smart_education_class_state_have_learn));
        //                break;
        //        }
        //1文本  2音频  3视频
        //        int type = item.getType();
        //        Intent intent = null;
        //        switch (type) {
        //            case 1:
        //                intent = new Intent(Utils.getApp(), SmartEducationClassDetailTextActivity.class);
        //                break;
        //            case 2:
        //                intent = new Intent(Utils.getApp(), SmartEducationClassDetailAudioActivity.class);
        //                break;
        //            case 3:
        //                intent = new Intent(Utils.getApp(), SmartEducationClassDetailVideoActivity.class);
        //                break;
        //        }
        //        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        Utils.getApp().startActivity(intent);
    }
}
