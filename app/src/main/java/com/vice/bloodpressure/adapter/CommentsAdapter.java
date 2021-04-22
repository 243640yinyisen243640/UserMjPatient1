package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.CircleEntityBean;
import com.vice.bloodpressure.ui.activity.circle.WriteCommentsTwoActivity;
import com.vice.bloodpressure.utils.ImageLoaderUtil;
import com.vice.bloodpressure.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yicheng on 2018/12/11.
 * 评论者适配器
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private static final int TYPE_HEADER = 0;  //Header
    private static final int TYPE_FOOTER = 1;  //Footer
    private static final int TYPE_NORMAL = 2;  //不带有header和footer的
    private Context context;
    private List<CircleEntityBean.CirclePing> circlePingList;
    private CommentsAdapter.OnItemClickListener onItemClickListener;//回调接口
    private Intent intent;
    private View mHeaderView;
    private CircleEntityBean circleEntity;
    private String photo;
    private List<String> ppp;//存放首页图片
    private CircleRecommendAdapter circleRecommendAdapter;


    public CommentsAdapter(Context context, List<CircleEntityBean.CirclePing> circlePingList, CircleEntityBean circleEntity) {
        this.context = context;
        this.circlePingList = circlePingList;
        this.circleEntity = circleEntity;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new MyViewHolder(mHeaderView);
        CommentsAdapter.MyViewHolder holder = new CommentsAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comments, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //        holder.tv_comments_shuoshuo.setText(circlePingList.get(position).getInfo());
        //        holder.tv_comments_name.setText(circlePingList.get(position).getNickname());
        //        if(!circlePingList.get(position).getReply().equals("0")){
        //            holder.tv_comments_re.setVisibility(View.VISIBLE);
        //            holder.tv_comments_re.setText(circlePingList.get(position).getReply()+"条回复");
        //        }else{
        //            holder.tv_comments_re.setVisibility(View.GONE);
        //        }
        //        holder.tv_comments_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(circlePingList.get(position).getAddtime())));
        //        ImageLoaderUtil.loadImgQuanZi(holder.iv_comments_photo, circlePingList.get(position).getPicture(), R.mipmap.ic_launcher);
        //
        //        holder.ll_comments.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                intent = new Intent(context, WriteCommentsTwoActivity.class);
        //                intent.putExtra("circlePing", circlePingList.get(position));
        //                context.startActivity(intent);
        //            }
        //        });


        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof MyViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                ((MyViewHolder) holder).tv_comments_name.setText(circlePingList.get(position - 1).getNickname());
                ((MyViewHolder) holder).tv_comments_shuoshuo.setText(circlePingList.get(position - 1).getInfo());
                ((MyViewHolder) holder).tv_comments_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(circlePingList.get(position - 1).getAddtime())));
                if (!circlePingList.get(position - 1).getReply().equals("0")) {
                    ((MyViewHolder) holder).tv_comments_re.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).tv_comments_re.setText(circlePingList.get(position - 1).getReply() + "条回复");
                } else {
                    ((MyViewHolder) holder).tv_comments_re.setVisibility(View.GONE);
                }
                ImageLoaderUtil.loadImgQuanZi(((MyViewHolder) holder).iv_comments_photo, circlePingList.get(position - 1).getPicture(), R.mipmap.ic_launcher);
                ((MyViewHolder) holder).ll_comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent = new Intent(context, WriteCommentsTwoActivity.class);
                        intent.putExtra("circlePing", circlePingList.get(position - 1));
                        context.startActivity(intent);
                    }
                });
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            ((MyViewHolder) holder).tv_item_header_name.setText(circleEntity.getNickname());
            ((MyViewHolder) holder).tv_item_header_shuoshuo.setText(circleEntity.getTitle());
            ((MyViewHolder) holder).tv_browse_item_header.setText(circleEntity.getNum() + "次浏览");
            ((MyViewHolder) holder).tv_answer_item_header.setText(circleEntity.getReply() + "个回答");
            ((MyViewHolder) holder).tv_item_header_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(circleEntity.getAddtime())));
            ImageLoaderUtil.loadImgQuanZi(((MyViewHolder) holder).iv_item_header_photo, circleEntity.getPicture(), R.mipmap.ic_launcher);

            photo = circleEntity.getPic();

            if (photo.contains(";")) {
                ppp = new ArrayList<>();
                String p[] = photo.split(";");
                for (int i = 0; i < p.length; i++) {
                    ppp.add(p[i]);
                }
            } else {
                ppp = new ArrayList<>();
                ppp.add(photo);
            }

            circleRecommendAdapter = new CircleRecommendAdapter(context, ppp);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
            ((MyViewHolder) holder).rcy_item_header_comments.setLayoutManager(layoutManager);
            ((MyViewHolder) holder).rcy_item_header_comments.setAdapter(circleRecommendAdapter);


            return;
        }

    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return circlePingList.size();
        } else if (mHeaderView != null) {
            return circlePingList.size() + 1;
        }
        return 0;
    }

    public void setOnItemClickListener(CommentsAdapter.OnItemClickListener onItemClickListener) {
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
        TextView tv_comments_re;
        LinearLayout ll_comments;

        //header
        TextView tv_item_header_name, tv_item_header_time, tv_item_header_shuoshuo, tv_browse_item_header, tv_answer_item_header;
        ImageView iv_item_header_photo;
        RecyclerView rcy_item_header_comments;

        public MyViewHolder(View itemView) {
            super(itemView);

            if (itemView == mHeaderView) {

                iv_item_header_photo = itemView.findViewById(R.id.iv_item_header_photo);
                tv_item_header_name = itemView.findViewById(R.id.tv_item_header_name);
                tv_item_header_time = itemView.findViewById(R.id.tv_item_header_time);
                tv_item_header_shuoshuo = itemView.findViewById(R.id.tv_item_header_shuoshuo);
                rcy_item_header_comments = itemView.findViewById(R.id.rcy_item_header_comments);
                tv_browse_item_header = itemView.findViewById(R.id.tv_browse_item_header);
                tv_answer_item_header = itemView.findViewById(R.id.tv_answer_item_header);

                return;
            }

            iv_comments_photo = itemView.findViewById(R.id.iv_comments_photo);
            tv_comments_name = itemView.findViewById(R.id.tv_comments_name);
            tv_comments_time = itemView.findViewById(R.id.tv_comments_time);
            tv_comments_shuoshuo = itemView.findViewById(R.id.tv_comments_shuoshuo);
            tv_comments_re = itemView.findViewById(R.id.tv_comments_re);
            ll_comments = itemView.findViewById(R.id.ll_comments);
        }
    }

}
