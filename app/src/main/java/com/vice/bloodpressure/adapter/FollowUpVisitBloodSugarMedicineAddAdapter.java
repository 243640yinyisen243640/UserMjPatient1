package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FollowUpVisitLvBean;
import com.vice.bloodpressure.imp.AdapterClickImp;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:  动态添加药品
 * 作者: LYD
 * 创建日期: 2019/11/5 17:53
 */
public class FollowUpVisitBloodSugarMedicineAddAdapter extends BaseAdapter {
    //药品名称 输入保存
    public HashMap<Integer, String> saveMapName;
    //药品次数 保存
    public HashMap<Integer, String> saveMapCount;
    //药品剂量保存
    public HashMap<Integer, String> saveMapDosage;
    //数据源
    private List<FollowUpVisitLvBean> list;
    private AdapterClickImp listener;
    private Context context;
    private String status;

    public FollowUpVisitBloodSugarMedicineAddAdapter(Context context, List list, List<List<String>> medicineList, AdapterClickImp listener, String status) {
        super();
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.status = status;

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
            viewHolder.imgAdd = convertView.findViewById(R.id.img_follow_up_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置药品名称
        int count = position + 1;
        viewHolder.tvDrugName.setText("药品名称" + count);
        //设置是否显示加号
        if (0 == position) {
            viewHolder.imgAdd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgAdd.setVisibility(View.GONE);
        }
        //设置添加点击
        viewHolder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAdapterClick(null, position);
            }
        });

        if ("4".equals(status) || "5".equals(status)) {
            viewHolder.imgAdd.setVisibility(View.GONE);
            viewHolder.etName.setFocusable(false);
            viewHolder.etName.setFocusableInTouchMode(false);
            viewHolder.etCount.setFocusable(false);
            viewHolder.etCount.setFocusableInTouchMode(false);
            viewHolder.etDosage.setFocusable(false);
            viewHolder.etDosage.setFocusableInTouchMode(false);
        }
        //药品名称输入设置
        //设置editext一个标记
        viewHolder.etName.setTag(position);
        //清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etName.clearFocus();
        //将对应保存在集合中的文本内容取出来 并显示上去
        viewHolder.etName.setText(saveMapName.get(position));
        final EditText tempEtName = viewHolder.etName;
        viewHolder.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEtName.getTag();
                saveMapName.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //药品次数
        viewHolder.etCount.setTag(position);
        //清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etCount.clearFocus();
        //将对应保存在集合中的文本内容取出来 并显示上去
        viewHolder.etCount.setText(saveMapCount.get(position));
        final EditText tempEtCount = viewHolder.etCount;
        viewHolder.etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEtCount.getTag();
                saveMapCount.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //药品剂量
        viewHolder.etDosage.setTag(position);
        //清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etDosage.clearFocus();
        //将对应保存在集合中的文本内容取出来 并显示上去
        viewHolder.etDosage.setText(saveMapDosage.get(position));
        final EditText tempEtDosage = viewHolder.etDosage;
        viewHolder.etDosage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEtDosage.getTag();
                saveMapDosage.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvDrugName;
        EditText etName;
        EditText etCount;
        EditText etDosage;
        ImageView imgAdd;
    }
}
