package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.NoticeBean;
import com.vice.bloodpressure.ui.activity.hospital.DepartmentDetailsActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class NoticeAdapter extends CommonAdapter<NoticeBean> {
    public NoticeAdapter(Context context, int layoutId, List<NoticeBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, NoticeBean item, int position) {
        viewHolder.setText(R.id.tv_text, item.getAffiche());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), DepartmentDetailsActivity.class);
                intent.putExtra("content", item.getContent() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
