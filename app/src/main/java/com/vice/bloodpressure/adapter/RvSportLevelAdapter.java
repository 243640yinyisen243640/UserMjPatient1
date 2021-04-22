package com.vice.bloodpressure.adapter;

import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;

import java.util.List;

public class RvSportLevelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    //默认选中位置
    private int selected = -1;
    private com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener;

    public RvSportLevelAdapter(@Nullable List<String> datas) {
        super(R.layout.item_rv_sport_level, datas);
    }

    public void setOnItemClickListener(com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        int position = holder.getLayoutPosition();
        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.sport_level_img);
        holder.setImageResource(R.id.img_sport, imgArray.getResourceId(position, 0));
        String[] titleArray = Utils.getApp().getResources().getStringArray(R.array.sport_level_title);
        String[] descArray = Utils.getApp().getResources().getStringArray(R.array.sport_level_desc);
        holder.setText(R.id.tv_sport_title, titleArray[position]);
        TextView tvDesc = holder.getView(R.id.tv_sport_desc);
        if (3 == position) {
            tvDesc.setVisibility(View.GONE);
        } else {
            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText(descArray[position]);
        }
        //单选处理
        ImageView imgCheck = holder.getView(R.id.img_check);
        if (selected == position) {
            imgCheck.setImageResource(R.drawable.sport_level_checked);
        } else {
            imgCheck.setImageResource(R.drawable.sport_level_uncheck);
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }
}
