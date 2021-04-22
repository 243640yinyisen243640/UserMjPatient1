package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.NewsListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FastAskListAdapter extends CommonAdapter<NewsListBean> {
    public FastAskListAdapter(Context context, int layoutId, List<NewsListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, NewsListBean item, int position) {
        QMUIRadiusImageView imgPic = viewHolder.getView(R.id.iv_pic);
        Glide.with(Utils.getApp())
                .load(item.getActurl())
                .error(R.drawable.default_medicine)
                .placeholder(R.drawable.default_medicine)
                .into(imgPic);
        viewHolder.setText(R.id.tv_know_title, item.getTitle());
        viewHolder.setText(R.id.tv_look_count, item.getBronum());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.post(new EventMessage(ConstantParam.SMART_LOOK_COUNT_REFRESH));
                Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                intent.putExtra("title", "智能搜索");
                intent.putExtra("url", item.getContenturl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
