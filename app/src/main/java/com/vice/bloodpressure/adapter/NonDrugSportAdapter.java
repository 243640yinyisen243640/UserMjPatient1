package com.vice.bloodpressure.adapter;


import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.imp.OnItemClickListener;

import java.util.List;

public class NonDrugSportAdapter extends RecyclerView.Adapter {
    private List<String> datas;
    private int selected = -1;
    private OnItemClickListener mOnItemClickListener;

    public NonDrugSportAdapter(List<String> datas) {
        this.datas = datas;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_non_drug_sport, parent, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SingleViewHolder) {
            final SingleViewHolder viewHolder = (SingleViewHolder) holder;
            //0,6,7,8
            String name = datas.get(position);
            SpannableString spannableString = new SpannableString(name);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ColorUtils.getColor(R.color.red_bright));
            if (datas.size() > 4) {
                switch (position) {
                    case 0:
                        spannableString.setSpan(colorSpan, 23, name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        viewHolder.tvContent.setText(spannableString);
                        break;
                    case 6:
                        spannableString.setSpan(colorSpan, 21, name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        viewHolder.tvContent.setText(spannableString);
                        break;
                    case 7:
                        spannableString.setSpan(colorSpan, 20, name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        viewHolder.tvContent.setText(spannableString);
                        break;
                    case 8:
                        spannableString.setSpan(colorSpan, 20, name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        viewHolder.tvContent.setText(spannableString);
                        break;
                    default:
                        viewHolder.tvContent.setText(name);
                        break;
                }
            } else {
                viewHolder.tvContent.setText(name);
            }
            if (selected == position) {
                viewHolder.cbCheck.setChecked(true);
                //viewHolder.itemView.setSelected(true);
            } else {
                viewHolder.cbCheck.setChecked(false);
                //viewHolder.itemView.setSelected(false);
            }
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class SingleViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbCheck;
        TextView tvContent;

        SingleViewHolder(View itemView) {
            super(itemView);
            cbCheck = itemView.findViewById(R.id.cb_check);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
