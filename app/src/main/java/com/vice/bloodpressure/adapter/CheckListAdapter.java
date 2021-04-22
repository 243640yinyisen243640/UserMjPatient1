package com.vice.bloodpressure.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.ExamineBean;

import java.util.List;

public class CheckListAdapter extends BaseQuickAdapter<ExamineBean, BaseViewHolder> {
    public CheckListAdapter(@Nullable List<ExamineBean> data) {
        super(R.layout.item_check_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, ExamineBean examineBean) {
        List<String> listPic = examineBean.getPicurl();
        ImageView imgCheck = holder.getView(R.id.img_check);
        if (listPic != null && listPic.size() > 0) {
            String picUrl = listPic.get(0);
            Glide.with(Utils.getApp()).load(picUrl).into(imgCheck);
        }
        holder.setText(R.id.tv_time, examineBean.getDatetime());
        holder.setText(R.id.tv_title, examineBean.getHydname());
    }
}
