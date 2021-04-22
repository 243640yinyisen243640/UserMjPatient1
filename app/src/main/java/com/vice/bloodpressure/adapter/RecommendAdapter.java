package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by yicheng on 2018/12/4.
 * 圈子上传图片适配器
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {


    private Context context;
    private List<Bitmap> mList;
    private RecommendAdapter.OnItemClickListener onItemClickListener;//回调接口

    public RecommendAdapter(Context context, List<Bitmap> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public RecommendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo_examine, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecommendAdapter.MyViewHolder holder, int position) {
        ImageLoaderUtil.loadBitmap(holder.imageView, mList.get(position));
        if (onItemClickListener != null) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.imageView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(RecommendAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_examine);
        }
    }
}
