package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.utils.PickerUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EducationFiveAdapter extends CommonAdapter<String> {
    public HashMap<Integer, String> selectMapCondition;//选中保存
    public HashMap<Integer, String> selectMapTime;//选中保存
    private Context context;

    public EducationFiveAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.context = context;
        selectMapCondition = new HashMap<>();
        selectMapTime = new HashMap<>();
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv_title, s);
        View.OnClickListener conditionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] diabetesMellitusCondition = Utils.getApp().getResources().getStringArray(R.array.data_diabetes_mellitus_condition);
                PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        holder.setText(R.id.tv_condition, text);
                        selectMapCondition.put(position, text);
                    }
                }, Arrays.asList(diabetesMellitusCondition));
            }
        };
        holder.setOnClickListener(R.id.rl_condition, conditionClickListener);
        holder.setOnClickListener(R.id.tv_condition, conditionClickListener);


        View.OnClickListener timeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerUtils.showTime(context, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        holder.setText(R.id.tv_time, content);
                        selectMapTime.put(position, content);
                    }
                });
            }
        };
        holder.setOnClickListener(R.id.rl_time, timeClickListener);
        holder.setOnClickListener(R.id.tv_time, timeClickListener);
    }
}
