package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.Warehouse;

import java.util.List;

/**
 * Created by qjx on 2018/5/10.
 */


public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.MyViewHolder> {

    private Context context;
    private List<Warehouse> mList;
    private OnItemClickListener onItemClickListener;

    public WarehouseAdapter(Context context, List<Warehouse> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public WarehouseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_warehouse, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //ImageLoaderUtil.loadImg(holder.iv_med, mList.get(position).getPicurl(), R.drawable.b1);
        Glide.with(context)
                .load(mList.get(position).getPic())
                .into(holder.iv_warehouse);
        if (onItemClickListener != null) {
            holder.rl_warehouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.rl_warehouse, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        }
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_warehouse;
        RelativeLayout rl_warehouse;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_warehouse = itemView.findViewById(R.id.iv_warehouse);
            rl_warehouse = itemView.findViewById(R.id.rl_warehouse);
        }
    }
}
