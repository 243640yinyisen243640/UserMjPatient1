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
import com.vice.bloodpressure.bean.FoodsCategoryBean;

import java.util.List;

public class FoodFirstLevelAdapter extends XyBaseAdapter<FoodsCategoryBean> {
    private Context mContext;
    private List<FoodsCategoryBean> mList;
    private int selectedPosition = 0;

    public FoodFirstLevelAdapter(Context mContext, List<FoodsCategoryBean> mList) {
        super(mContext, mList);
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_smart_search_first_level, parent, false);
            viewHolder.llBg = convertView.findViewById(R.id.ll_bg);
            viewHolder.lineIndicator = convertView.findViewById(R.id.line_indicator);
            viewHolder.tvText = convertView.findViewById(R.id.tv_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvText.setText(mList.get(position).getFoodlei());
        if (selectedPosition == position) {
            viewHolder.llBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            viewHolder.lineIndicator.setVisibility(View.VISIBLE);
        } else {
            viewHolder.llBg.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background));
            viewHolder.lineIndicator.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public void setCheck(int position) {
        selectedPosition = position;
    }

    private class ViewHolder {
        LinearLayout llBg;
        View lineIndicator;
        TextView tvText;
    }
}
