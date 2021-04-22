package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SmartEducationSeriasClassListBean;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailAudioActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailTextActivity;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassDetailVideoActivity;

import java.util.List;

public class SmartEducationClassDetailCatalogueAdapter extends BaseQuickAdapter<SmartEducationSeriasClassListBean, BaseViewHolder> {

    private int id;

    public SmartEducationClassDetailCatalogueAdapter(@Nullable List<SmartEducationSeriasClassListBean> data, int id) {
        super(R.layout.item_smart_education_class_detail_right, data);
        this.id = id;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SmartEducationSeriasClassListBean item) {
        View viewIndicator = viewHolder.getView(R.id.view_indicator);
        TextView tvCatalogue = viewHolder.getView(R.id.tv_catalogue);
        //当前正在看
        int listId = item.getId();
        tvCatalogue.setText(item.getArtname());
        if (id == listId) {
            viewIndicator.setVisibility(View.VISIBLE);
            tvCatalogue.setTextColor(ColorUtils.getColor(R.color.black_text));
        } else {
            viewIndicator.setVisibility(View.INVISIBLE);
            tvCatalogue.setTextColor(ColorUtils.getColor(R.color.gray_text));
        }
        //点击进详情
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1文本  2音频  3视频
                int type = item.getType();
                int id = item.getId();
                int cId = item.getCid();
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
                bundle.putString("from", item.getStatus() + "");
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
