package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.DataBean;

import java.util.List;

/**
 * Created by qjx on 2018/5/10.
 */

public class HomeRecycleToolsAdapter extends RecyclerView.Adapter<HomeRecycleToolsAdapter.MyViewHolder> {

    private Context context;

    private List<DataBean> mList;


    private OnItemClickListener onItemClickListener;

    public HomeRecycleToolsAdapter(Context context, List<DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HomeRecycleToolsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_tools_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.imageView.setImageResource(mList.get(position).getUrl());
        holder.textView.setText(mList.get(position).getName());
        if (onItemClickListener != null) {
            holder.rlTools.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.rlTools, position);
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

        ImageView imageView;
        TextView textView;
        RelativeLayout rlTools;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_tools_item);
            textView = itemView.findViewById(R.id.tv_name);
            rlTools = itemView.findViewById(R.id.rl_tools);
        }
    }
}
