package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.WarningMessageBean;

import java.util.List;

/**
 * Created by qjx on 2018/5/14.
 */

public class WarningAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WarningMessageBean> warningMessageList;
    private String type;

    public WarningAdapter(Context context, List<WarningMessageBean> warningMessageList, String type) {
        this.context = context;
        this.warningMessageList = warningMessageList;
        this.inflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public int getCount() {
        return warningMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return warningMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_blood_sugar_warning, null);
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.tv_message = convertView.findViewById(R.id.tv_message);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            holder.tv_warning = convertView.findViewById(R.id.tv_warning);
            holder.iv_warning = convertView.findViewById(R.id.iv_warning);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if ("blood".equals(type)) {
            holder.tv_title.setText("血压值预警");
        } else if ("sugar".equals(type)) {
            holder.tv_title.setText("血糖值预警");
        }

        holder.tv_message.setText(warningMessageList.get(position).getMessage());
        holder.tv_time.setText(warningMessageList.get(position).getTime());
        return convertView;
    }

    class ViewHolder {
        TextView tv_title, tv_message, tv_time, tv_warning;
        ImageView iv_warning;
    }
}
