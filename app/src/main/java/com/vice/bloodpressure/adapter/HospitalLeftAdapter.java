package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.XyBaseAdapter;
import com.vice.bloodpressure.bean.HospitalLeftBean;

import java.util.List;


/**
 * 测试背景
 */
public class HospitalLeftAdapter extends XyBaseAdapter<HospitalLeftBean> {
    private Context mContext;
    private List<HospitalLeftBean> mList;
    private int selectedPosition = 0;

    public HospitalLeftAdapter(Context mContext, List<HospitalLeftBean> mList) {
        super(mContext, mList);
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hospital_lv_left, parent, false);
            viewHolder.tvLeft = convertView.findViewById(R.id.tv_left);
            viewHolder.llBg = convertView.findViewById(R.id.ll_bg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvLeft.setText(mList.get(position).getDepname());
        if (selectedPosition == position) {
            viewHolder.tvLeft.setTextColor(ContextCompat.getColor(mContext, R.color.white_text));
            viewHolder.llBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.main_home));
        } else {
            viewHolder.tvLeft.setTextColor(ContextCompat.getColor(mContext, R.color.black_text));
            viewHolder.llBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_text));
        }
        return convertView;
    }

    public void clearSelection(int position) {
        selectedPosition = position;
    }

    private class ViewHolder {
        LinearLayout llBg;
        TextView tvLeft;
    }

}
