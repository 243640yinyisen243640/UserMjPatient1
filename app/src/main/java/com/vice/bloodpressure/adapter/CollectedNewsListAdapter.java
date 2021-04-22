package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.CollectedNewsBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * @ProjectName: NewBloodPressure
 * @Package: com.vice.bloodpressure.adapter
 * @ClassName: CollectedNewsListAdapter
 * @Description: java类作用描述
 * @Author: zwk
 * @CreateDate: 2019/10/25 11:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/10/25 11:58
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */

public class CollectedNewsListAdapter extends CommonAdapter<CollectedNewsBean> {

    private OnCollectionChangeListener onCollectionChangeListener;

    public CollectedNewsListAdapter(Context context, int layoutId, List<CollectedNewsBean> datas) {
        super(context, layoutId, datas);
    }

    public void setOnCollectionChangeListener(OnCollectionChangeListener onCollectionChangeListener) {
        this.onCollectionChangeListener = onCollectionChangeListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, CollectedNewsBean item, int position) {
        viewHolder.setText(R.id.tv_news_title, item.getTitle());
        viewHolder.setText(R.id.tv_num, item.getBronum());
        viewHolder.setText(R.id.tv_time, item.getTime());
        ImageView imageView = viewHolder.getView(R.id.iv_news);
        Glide.with(Utils.getApp()).load(item.getActurl()).placeholder(R.drawable.default_medicine).into(imageView);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                intent.putExtra("title", "详情");
                intent.putExtra("url", item.getContenturl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
        viewHolder.setOnClickListener(R.id.iv_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.setImageResource(R.id.iv_collect, R.drawable.uncollected);

                onCollectionChangeListener.OnCollectionChanged(v, item.getActid());
            }
        });
    }

    public interface OnCollectionChangeListener {
        void OnCollectionChanged(View v, String id);
    }
}
