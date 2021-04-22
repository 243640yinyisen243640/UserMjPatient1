package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.NewsListBean;
import com.vice.bloodpressure.ui.activity.knowledgebase.KnowLedgeListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


public class HomeRecycleAdapter extends CommonAdapter<NewsListBean> {

    public HomeRecycleAdapter(Context context, int layoutId, List<NewsListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, NewsListBean item, int position) {
        int width = ScreenUtils.getScreenWidth();
        int paddingWidth = ConvertUtils.dp2px(2 * 16 + 2 * 16);
        int trueWidth = width - paddingWidth;
        int itemWidth = trueWidth / 3;
        int itemHeight = (int) (itemWidth * 1.31);
        RelativeLayout.LayoutParams fp = new RelativeLayout.LayoutParams(itemWidth, itemHeight);
        ImageView imgPic = viewHolder.getView(R.id.iv_pic);
        imgPic.setLayoutParams(fp);

        Glide.with(Utils.getApp()).
                load(item.getCateurl())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(imgPic);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getId();
                String catename = item.getCatename();
                Intent intent = new Intent(Utils.getApp(), KnowLedgeListActivity.class);
                intent.putExtra("position", id);
                intent.putExtra("title", catename);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
