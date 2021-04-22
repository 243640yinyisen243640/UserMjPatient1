package com.lyd.modulemall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.modulemall.R;

import java.util.ArrayList;
import java.util.List;

public class AddImageThreeAdapter extends RecyclerView.Adapter<AddImageThreeAdapter.ViewHolder> {
    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<String> list = new ArrayList<>();
    private int selectMax = 3;
    private onAddPicClickListener mOnAddPicClickListener;
    private OnItemClickListener onItemClickListener;

    public AddImageThreeAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setList(List<String> list) {
        if (list.size() > selectMax) {
            list = list.subList(0, selectMax);
        }
        this.list = list;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size();
        return position == size;
    }


    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_photo_examine, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.ivExamine.setImageResource(R.drawable.picture_upload_add);
            viewHolder.ivExamine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            });
            viewHolder.imgDel.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imgDel.setVisibility(View.VISIBLE);
            if (onItemClickListener != null) {
                viewHolder.imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(position, viewHolder.imgDel);
                    }
                });
            }
            Glide.with(Utils.getApp()).load(list.get(position)).placeholder(R.drawable.default_image).into(viewHolder.ivExamine);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExamine;
        ImageView imgDel;

        ViewHolder(View view) {
            super(view);
            ivExamine = view.findViewById(R.id.iv_examine);
            imgDel = view.findViewById(R.id.img_del);
        }
    }
}
