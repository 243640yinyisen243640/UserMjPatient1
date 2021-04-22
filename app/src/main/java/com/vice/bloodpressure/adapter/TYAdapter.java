package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.BmiBean;
import com.vice.bloodpressure.bean.HemoglobinBean;

import java.util.List;


public class TYAdapter<T> extends RecyclerView.Adapter<TYAdapter.MyViewHolder> {
    private Context context;
    private List<T> bsList;
    private OnLongClickListener mLongClickListener;
    private int key;

    public TYAdapter(Context context, List<T> bsList, int key) {
        this.context = context;
        this.bsList = bsList;
        this.key = key;
    }

    public void setLongClickListener(OnLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }

    @Override
    public TYAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (key == 6) {
            view = LayoutInflater.from(context).inflate(R.layout.item_blood_xueya_and_pharmacy_sport, parent, false);
        } else if (key == 5) {
            view = LayoutInflater.from(context).inflate(R.layout.item_bmi, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TYAdapter.MyViewHolder holder, int position) {
        if (key == 5) {
            List<BmiBean> bmi = (List<BmiBean>) bsList;
            holder.tvBmi.setText(bmi.get(position).getBmi() + "");
            holder.tvBmiTime.setText(bmi.get(position).getDatetime());
            holder.tvHeightWeight.setText(bmi.get(position).getHeight() + "cm/" + bmi.get(position).getWeight() + "kg");
        } else if (key == 6) {
            List<HemoglobinBean> hemoglobinList = (List<HemoglobinBean>) bsList;
            holder.tvBloodNum.setTextColor(context.getResources().getColor(R.color.red_bright));
            holder.tvBloodNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.sixteen));
            holder.tvTongYong.setText("%");
            holder.tvBloodNum.setText(hemoglobinList.get(position).getDiastaticvalue());
            holder.tvBloodpressureTime.setText(hemoglobinList.get(position).getDatetime());
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mLongClickListener.onLongClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (bsList == null || bsList.size() == 0) {
            return 0;
        }
        return bsList.size();
    }


    public interface OnLongClickListener {
        boolean onLongClick(int position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBloodNum, tvBmi;
        TextView tvBloodpressureTime, tvBmiTime;

        TextView tvHeightWeight;//身高体重
        TextView tvTongYong;//通用


        public MyViewHolder(View itemView) {
            super(itemView);
            //血压
            tvBloodNum = itemView.findViewById(R.id.tv_blood_num);
            tvBloodpressureTime = itemView.findViewById(R.id.tv_bloodpressure_time);
            //BmiBean
            tvBmi = itemView.findViewById(R.id.tv_bmi);
            tvBmiTime = itemView.findViewById(R.id.tv_bmi_time);
            tvHeightWeight = itemView.findViewById(R.id.tv_hw);
            //血红蛋白
            tvTongYong = itemView.findViewById(R.id.tv_tongyong);
            if (key == 6) {
                tvTongYong.setTextColor(Color.parseColor("#FF0404"));
                tvBloodNum.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }
    }
}
