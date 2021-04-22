package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vice.bloodpressure.R;

/**
 * Created by qjx on 2018/5/14.
 */

public class HealthAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] name;
    private String[] slogan;
    private TypedArray images;
    private HealthInterface healthInterface;

    public HealthAdapter(Context context, String[] hName, String[] slogan, TypedArray images, HealthInterface healthInterface) {
        this.context = context;
        this.name = hName;
        this.slogan = slogan;
        this.images = images;
        this.healthInterface = healthInterface;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return name[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_health, null);
            holder = new ViewHolder();
            holder.ivHealth = view.findViewById(R.id.img_health);
            holder.tvHealthName = view.findViewById(R.id.tv_health_name);
            holder.tvSlogan = view.findViewById(R.id.tv_health_desc);
            holder.rlHealthAdd = view.findViewById(R.id.rl_health_add);
            holder.rlHealthRecord = view.findViewById(R.id.rl_health_record);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.ivHealth.setImageResource(images.getResourceId(i, R.drawable.default_image));
        holder.tvHealthName.setText(name[i]);
        holder.tvSlogan.setText(slogan[i]);
        holder.rlHealthAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (healthInterface != null) {
                    healthInterface.addOnClick(i);
                }
            }
        });

        holder.rlHealthRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (healthInterface != null) {
                    healthInterface.lookRecord(i);
                }
            }
        });
        return view;
    }

    public interface HealthInterface {
        void addOnClick(int position);

        void lookRecord(int position);
    }

    class ViewHolder {
        TextView tvHealthName;
        TextView tvSlogan;
        ImageView ivHealth;
        RelativeLayout rlHealthAdd;
        RelativeLayout rlHealthRecord;
    }
}
