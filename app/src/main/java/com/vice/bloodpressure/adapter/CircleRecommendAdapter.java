package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.utils.ImageLoaderUtil;
import com.lyd.baselib.utils.engine.GlideImageEngine;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yicheng on 2018/12/4.
 * 圈子首页图片展示
 */

public class CircleRecommendAdapter extends RecyclerView.Adapter<CircleRecommendAdapter.MyViewHolder> {


    private Context context;
    private List<String> mList;
    private CircleRecommendAdapter.OnItemClickListener onItemClickListener;//回调接口

    CircleRecommendAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public CircleRecommendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo_examine, parent, false));
    }

    @Override
    public void onBindViewHolder(final CircleRecommendAdapter.MyViewHolder holder, int position) {
        ImageLoaderUtil.loadImgUrl(holder.imageView, mList.get(position));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MNImageBrowser.with(context)
                        .setCurrentPosition(position)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList((ArrayList<String>) mList)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        }
        return mList.size();
    }

    public void setOnItemClickListener(CircleRecommendAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_examine);
        }
    }
}
