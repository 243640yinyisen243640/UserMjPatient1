package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.vice.bloodpressure.R;
import com.lyd.baselib.utils.engine.GlideImageEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: NewBloodPressure
 * @Package: com.vice.bloodpressure.adapter
 * @ClassName: AddPicAdapter
 * @Description: java类作用描述
 * @Author: zwk
 * @CreateDate: 2019/11/10 8:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/10 8:32
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */

public class AddPicAdapter extends RecyclerView.Adapter<AddPicAdapter.ViewHolder> {
    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<String> list = new ArrayList<>();
    private int selectMax = 3;
    private Context context;
    private String status;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;
    private OnItemClickListener mItemClickListener;

    public AddPicAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return selectMax;
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
        View view = mInflater.inflate(R.layout.item_add_pic, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * 创建ViewHolder
     */


    private boolean isShowAddItem(int position) {
        return position >= list.size();
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.ivExamine.setImageResource(R.drawable.jiahao_check);
            viewHolder.ivExamine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!"4".equals(status) && !"5".equals(status)) {
                        mOnAddPicClickListener.onAddPicClick(viewHolder.getAdapterPosition());
                    }
                }
            });
            //viewHolder.tvDel.setVisibility(View.INVISIBLE);
        } else {
            //viewHolder.tvDel.setVisibility(View.VISIBLE);
            viewHolder.ivExamine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("4".equals(status) || "5".equals(status)) {
                        MNImageBrowser.with(context)
                                .setCurrentPosition(position)
                                .setImageEngine(new GlideImageEngine())
                                .setImageList((ArrayList<String>) list)
                                .setIndicatorHide(false)
                                .setFullScreenMode(true)
                                .show(viewHolder.ivExamine);
                    } else {
                        mOnAddPicClickListener.onAddPicClick(viewHolder.getAdapterPosition());
                    }
                }
            });


            Glide.with(Utils.getApp()).load(list.get(position)).placeholder(R.drawable.default_image).into(viewHolder.ivExamine);

            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        mItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }

    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface onAddPicClickListener {
        void onAddPicClick(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivExamine;
        TextView tvDel;

        ViewHolder(View view) {
            super(view);
            ivExamine = view.findViewById(R.id.iv_examine);
            //tvDel = view.findViewById(R.id.deleteButton);
        }
    }
}
