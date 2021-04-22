package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.CommentDetailsBean;
import com.vice.bloodpressure.utils.ImageLoaderUtil;
import com.vice.bloodpressure.utils.TimeFormatUtils;

import java.util.List;


/**
 * Created by yicheng on 2018/12/11.
 * 评论者适配器
 */

public class CommentsTwoAdapter extends RecyclerView.Adapter<CommentsTwoAdapter.MyViewHolder> {

    private static final int TYPE_HEADER = 0;  //Header
    private static final int TYPE_FOOTER = 1;  //Footer
    private static final int TYPE_NORMAL = 2;  //不带有header和footer的
    private Context context;
    private List<CommentDetailsBean.CirclePing> circlePingList;
    private CommentsTwoAdapter.OnItemClickListener onItemClickListener;//回调接口
    private View mHeaderView;
    private CommentDetailsBean commentDetails;
    private ReplyAdapter replyAdapter;


    public CommentsTwoAdapter(Context context, List<CommentDetailsBean.CirclePing> circlePingList, CommentDetailsBean commentDetails) {
        this.context = context;
        this.circlePingList = circlePingList;
        this.commentDetails = commentDetails;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new CommentsTwoAdapter.MyViewHolder(mHeaderView);
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comments_two, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        //        holder.tv_comments_shuoshuo.setText(circlePingList.get(position).getInfo());
        //        holder.tv_comments_name.setText(circlePingList.get(position).getNickname());
        //        holder.tv_comments_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(circlePingList.get(position).getAddtime())));
        //        ImageLoaderUtil.loadImgQuanZi(holder.iv_comments_photo, circlePingList.get(position).getPicture(), R.mipmap.ic_launcher);


        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof MyViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                ((MyViewHolder) holder).tv_comments_name.setText(circlePingList.get(position - 1).getNickname());
                if (TextUtils.isEmpty(circlePingList.get(position - 1).getBnickname())) {
                    ((MyViewHolder) holder).tv_reply_vg.setVisibility(View.GONE);
                    ((MyViewHolder) holder).tv_comments_shuoshuo.setVisibility(View.GONE);
                    ((MyViewHolder) holder).tv_comments_shuoshuo1.setText(circlePingList.get(position - 1).getInfo());
                } else {
                    ((MyViewHolder) holder).tv_reply_vg.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).tv_comments_shuoshuo.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).tv_comments_shuoshuo.setText(circlePingList.get(position - 1).getBnickname());
                    ((MyViewHolder) holder).tv_comments_shuoshuo.setTextColor(context.getResources().getColor(R.color.main_home));
                    ((MyViewHolder) holder).tv_comments_shuoshuo1.setText("：" + circlePingList.get(position - 1).getInfo());
                }

                ((MyViewHolder) holder).tv_comments_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(circlePingList.get(position - 1).getAddtime())));
                ImageLoaderUtil.loadImgQuanZi(((MyViewHolder) holder).iv_comments_photo, circlePingList.get(position - 1).getPicture(), R.mipmap.ic_launcher);

                if (onItemClickListener != null) {
                    holder.tv_reply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = holder.getLayoutPosition();
                            onItemClickListener.onItemClick(holder.tv_reply, position - 1);
                        }
                    });
                }

                replyAdapter = new ReplyAdapter(context, circlePingList);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
                linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                holder.rv_item_comments_reply.setLayoutManager(linearLayoutManager1);
                holder.rv_item_comments_reply.setAdapter(replyAdapter);
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            ((MyViewHolder) holder).tv_comments_two_name.setText(commentDetails.getNickname());
            ((MyViewHolder) holder).tv_comments_two_shuoshuo.setText(commentDetails.getInfo());
            ((MyViewHolder) holder).tv_comments_two_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(commentDetails.getAddtime())));
            ImageLoaderUtil.loadImgQuanZi(((MyViewHolder) holder).iv_comments_two_photo, commentDetails.getPicture(), R.mipmap.ic_launcher);

            return;
        }

    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return circlePingList.size();
        } else {
            return circlePingList.size() + 1;
        }
    }

    public void setOnItemClickListener(CommentsTwoAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_comments_photo;
        TextView tv_comments_name;
        TextView tv_comments_time;
        TextView tv_comments_shuoshuo;
        TextView tv_reply;
        TextView tv_reply_vg;
        TextView tv_comments_shuoshuo1;
        RecyclerView rv_item_comments_reply;

        //header(two)
        ImageView iv_comments_two_photo;
        TextView tv_comments_two_shuoshuo, tv_comments_two_name, tv_comments_two_time;

        public MyViewHolder(View itemView) {
            super(itemView);

            if (itemView == mHeaderView) {

                iv_comments_two_photo = itemView.findViewById(R.id.iv_comments_two_photo);
                tv_comments_two_name = itemView.findViewById(R.id.tv_comments_two_name);
                tv_comments_two_time = itemView.findViewById(R.id.tv_comments_two_time);
                tv_comments_two_shuoshuo = itemView.findViewById(R.id.tv_comments_two_shuoshuo);

                return;
            }
            iv_comments_photo = itemView.findViewById(R.id.iv_comments_photo);
            tv_comments_name = itemView.findViewById(R.id.tv_comments_name);
            tv_comments_time = itemView.findViewById(R.id.tv_comments_time);
            tv_comments_shuoshuo = itemView.findViewById(R.id.tv_comments_shuoshuo);
            tv_reply = itemView.findViewById(R.id.tv_reply);
            rv_item_comments_reply = itemView.findViewById(R.id.rv_item_comments_reply);
            tv_reply_vg = itemView.findViewById(R.id.tv_reply_vg);
            tv_comments_shuoshuo1 = itemView.findViewById(R.id.tv_comments_shuoshuo1);
        }
    }

}
