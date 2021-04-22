package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.NewsCategoryListBean;
import com.vice.bloodpressure.ui.activity.news.NewsListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述: 资讯分类列表
 * 作者: LYD
 * 创建日期: 2019/4/12 16:26
 */

public class NewsCategoryListAdapter extends CommonAdapter<NewsCategoryListBean> {


    public NewsCategoryListAdapter(Context context, int layoutId, List<NewsCategoryListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, NewsCategoryListBean item, int position) {
        ImageView imgBg = viewHolder.getView(R.id.iv_bg);
        Glide.with(Utils.getApp())
                .load(item.getCateurl())
                .placeholder(R.drawable.banner)
                .error(R.drawable.banner)
                .into(imgBg);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getId() + "";
                String catename = item.getCatename();
                Intent intent = new Intent(Utils.getApp(), NewsListActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", catename);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
