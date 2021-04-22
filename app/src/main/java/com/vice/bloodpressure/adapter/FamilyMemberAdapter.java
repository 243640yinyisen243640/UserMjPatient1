package com.vice.bloodpressure.adapter;
/*
 * 类名:     FamilyMemberAdapter
 * 描述:     家庭列表
 * 作者:     ZWK
 * 创建日期: 2020/1/9 14:17
 */

import android.content.Context;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FamilyMemberBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


public class FamilyMemberAdapter extends CommonAdapter<FamilyMemberBean> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    public FamilyMemberAdapter(Context context, int layoutId, List<FamilyMemberBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(ViewHolder holder, FamilyMemberBean familyMember, int position) {
        holder.setText(R.id.tv_name, familyMember.getNickname());
        holder.setText(R.id.tv_identity, getRelationString(familyMember.getRelation()));
        holder.setText(R.id.tv_phone_number, familyMember.getTel());
        holder.setText(R.id.tv_id_number, familyMember.getIdcard());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, familyMember.getUserid());
            }
        });
    }

    private String getRelationString(int rel) {
        String relation = "";
        switch (rel) {
            case 1:
                relation = "户主";
                break;
            case 2:
                relation = "配偶";
                break;
            case 3:
                relation = "子";
                break;
            case 4:
                relation = "儿媳";
                break;
            case 5:
                relation = "女";
                break;
            case 6:
                relation = "女婿";
                break;
            case 7:
                relation = "外（孙）子女";
                break;
            case 8:
                relation = "其他";
                break;
        }
        return relation;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int id);
    }

}
