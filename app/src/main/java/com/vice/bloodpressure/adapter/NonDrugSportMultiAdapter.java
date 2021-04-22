package com.vice.bloodpressure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.NonDrugSportMultiBean;
import com.vice.bloodpressure.imp.OnItemClickListener;

import java.util.HashMap;
import java.util.List;

public class NonDrugSportMultiAdapter extends RecyclerView.Adapter {
    //布局类型
    private static final int VIEW_TYPE_ONE = 1;
    private static final int VIEW_TYPE_TWO = 2;
    public static HashMap<Integer, Boolean> isSelected = new HashMap<>();
    private List<NonDrugSportMultiBean> datas;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListener newListener;

    public NonDrugSportMultiAdapter(List<NonDrugSportMultiBean> datas) {
        this.datas = datas;
        init();
    }

    private void init() {
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setNewListener(OnItemClickListener newListener) {
        this.newListener = newListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position).getType() == NonDrugSportMultiBean.Type.TypeOne) {
            return VIEW_TYPE_ONE;
        } else if (datas.get(position).getType() == NonDrugSportMultiBean.Type.TypeTwo) {
            return VIEW_TYPE_TWO;
        } else {
            return 0;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ONE:
                viewHolder = new ViewHolderOne(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_non_drug_sport, parent, false));
                break;
            case VIEW_TYPE_TWO:
                viewHolder = new ViewHolderTwo(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_non_drug_sport_select, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ONE:
                ((ViewHolderOne) holder).tvContent.setText(datas.get(position).getContent());
                ((ViewHolderOne) holder).cbCheck.setChecked(isSelected.get(position));
                if (mOnItemClickListener != null) {
                    ((ViewHolderOne) holder).itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onItemClick(((ViewHolderOne) holder).itemView, holder.getAdapterPosition());
                        }
                    });
                }
                break;
            case VIEW_TYPE_TWO:
                ((ViewHolderTwo) holder).tvContent.setText(datas.get(position).getContent());
                ((ViewHolderTwo) holder).cbCheck.setChecked(isSelected.get(position));
                ((ViewHolderTwo) holder).tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newListener.onItemClick(((ViewHolderTwo) holder).tvContent, holder.getAdapterPosition());
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        CheckBox cbCheck;
        TextView tvContent;

        ViewHolderOne(View itemView) {
            super(itemView);
            cbCheck = itemView.findViewById(R.id.cb_check);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        CheckBox cbCheck;
        TextView tvContent;
        LinearLayout llContent;

        ViewHolderTwo(View itemView) {
            super(itemView);
            cbCheck = itemView.findViewById(R.id.cb_check);
            tvContent = itemView.findViewById(R.id.tv_content);
            llContent = itemView.findViewById(R.id.ll_content);
        }
    }
}
