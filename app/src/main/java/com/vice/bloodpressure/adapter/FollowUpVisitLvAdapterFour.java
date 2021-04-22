package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FollowUpVisitLvBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FollowUpVisitLvAdapterFour extends BaseAdapter {
    private static final String TAG = "FollowUpVisitBloodPressureSubmitActivity";
    public HashMap<Integer, String> saveMapName;//这个集合用来存储对应位置上Editext中的文本内容
    public HashMap<Integer, String> saveMapCount;//这个集合用来存储对应位置上Editext中的文本内容
    public HashMap<Integer, String> saveMapDosage;//这个集合用来存储对应位置上Editext中的文本内容
    private Context context;
    private List<FollowUpVisitLvBean> list;
    private List<List<String>> medicineList;
    private String status;

    public FollowUpVisitLvAdapterFour(Context context, List list, List<List<String>> medicineList, String status) {
        this.context = context;
        this.list = list;
        this.status = status;
        this.medicineList = medicineList;
        initHashMap(medicineList);
    }

    private void initHashMap(List<List<String>> medicineList) {
        //更新
        saveMapName = new HashMap<>();
        saveMapCount = new HashMap<>();
        saveMapDosage = new HashMap<>();
        if (medicineList != null && medicineList.size() > 0) {
            for (int i = 0; i < medicineList.size(); i++) {
                List<String> list = medicineList.get(i);
                for (int j = 0; j < list.size(); j++) {
                    String s = list.get(j);
                    if (0 == j) {
                        saveMapName.put(i, s);
                    }
                    if (1 == j) {
                        saveMapCount.put(i, s);
                    }
                    if (2 == j) {
                        saveMapDosage.put(i, s);
                    }
                }
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.include_follow_up_visit_drug_item, parent, false);
            viewHolder.tvDrugName = convertView.findViewById(R.id.tv_drug_name);
            viewHolder.etName = convertView.findViewById(R.id.et_name);
            viewHolder.etCount = convertView.findViewById(R.id.et_count);
            viewHolder.etDosage = convertView.findViewById(R.id.et_dosage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置药品
        String[] stringArray = context.getResources().getStringArray(R.array.follow_up_visit_medicine_name);
        List<String> list = Arrays.asList(stringArray);
        viewHolder.tvDrugName.setText(list.get(position));

        //设置第一名称
        viewHolder.etName.setTag(position);//设置editext一个标记
        viewHolder.etName.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etName.setText(saveMapName.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditTextName = viewHolder.etName;
        viewHolder.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditTextName.getTag();
                saveMapName.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //输入设置第二
        viewHolder.etCount.setTag(position);//设置editext一个标记
        viewHolder.etCount.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etCount.setText(saveMapCount.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditTextCount = viewHolder.etCount;
        viewHolder.etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditTextCount.getTag();
                saveMapCount.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //输入设置第三
        //输入设置第二
        viewHolder.etDosage.setTag(position);//设置editext一个标记
        viewHolder.etDosage.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etDosage.setText(saveMapDosage.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditTextDosage = viewHolder.etDosage;
        viewHolder.etDosage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditTextDosage.getTag();
                saveMapDosage.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        if ("4".equals(status) || "5".equals(status)) {
            viewHolder.etName.setFocusable(false);
            viewHolder.etName.setFocusableInTouchMode(false);
            viewHolder.etCount.setFocusable(false);
            viewHolder.etCount.setFocusableInTouchMode(false);
            viewHolder.etDosage.setFocusable(false);
            viewHolder.etDosage.setFocusableInTouchMode(false);
        }
        return convertView;
    }


    public class ViewHolder {
        TextView tvDrugName;
        EditText etName;
        EditText etCount;
        EditText etDosage;
    }
}
