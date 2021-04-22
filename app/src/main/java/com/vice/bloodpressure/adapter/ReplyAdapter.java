package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.CommentDetailsBean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/2.
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {

    private Context mContext;
    private List<CommentDetailsBean.CirclePing> circlePingList;

    ReplyAdapter(Context context, List<CommentDetailsBean.CirclePing> circlePingList) {
        this.mContext = context;
        this.circlePingList = circlePingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_reply, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!TextUtils.isEmpty(circlePingList.get(position).getPingname())) {
            holder.tvReplyName.setText(String.format("%s回复-%s:", circlePingList.get(position).getPingname(), circlePingList.get(position).getNickname()));
            holder.tvReplyMsg.setText(circlePingList.get(position).getPinginfo());
        }
    }

    @Override
    public int getItemCount() {
        if (circlePingList == null || circlePingList.size() == 0) {
            return 0;
        }
        return circlePingList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvReplyName;
        TextView tvReplyMsg;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvReplyName = itemView.findViewById(R.id.tv_reply_reply_name);
            tvReplyMsg = itemView.findViewById(R.id.tv_reply_reply_msg);
        }
    }
}
