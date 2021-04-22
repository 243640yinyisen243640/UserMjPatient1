package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.NewsListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.CommonAdapterClickImp;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class NewsListAdapter extends CommonAdapter<NewsListBean> {
    private CommonAdapterClickImp adapterViewClick;

    public NewsListAdapter(Context context, int layoutId, List<NewsListBean> datas, CommonAdapterClickImp adapterViewClick) {
        super(context, layoutId, datas);
        this.adapterViewClick = adapterViewClick;
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
        TextView tvContent = viewHolder.getView(R.id.tv_content);
        if (tvContent != null) {
            viewHolder.setText(R.id.tv_content, item.getArticlebase());
        }
        viewHolder.setText(R.id.tv_look_count, item.getBronum());
        String isCollection = item.getIscollection();
        ImageView imgCollect = viewHolder.getView(R.id.iv_collect);
        if (imgCollect != null) {
            if ("1".equals(isCollection)) {
                imgCollect.setImageResource(R.drawable.heart_select);
            } else {
                imgCollect.setImageResource(R.drawable.heart_unselect);
            }
            imgCollect.setOnClickListener(new OnAdapterViewClickListener(position));
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.post(new EventMessage<>(ConstantParam.NEWS_LOOK_COUNT_REFRESH));
                Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("url", item.getContenturl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }


    private class OnAdapterViewClickListener implements View.OnClickListener {
        int position;

        OnAdapterViewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (adapterViewClick != null) {
                adapterViewClick.adapterViewClick(position);
            }
        }
    }
}
