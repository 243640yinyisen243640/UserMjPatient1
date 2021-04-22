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

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SugarControlBean;
import com.vice.bloodpressure.utils.TurnsUtils;

import java.util.HashMap;
import java.util.List;

public class SugarControlTargetAdapter extends BaseAdapter {
    //最小值 输入保存
    public HashMap<Integer, String> saveMapMin;
    //最大值 保存
    public HashMap<Integer, String> saveMapMax;

    private List<SugarControlBean> list;
    private Context context;

    public SugarControlTargetAdapter(Context context, List<SugarControlBean> list) {
        this.list = list;
        this.context = context;
        initHashMap(list);
    }

    private void initHashMap(List<SugarControlBean> list) {
        saveMapMin = new HashMap<>();
        saveMapMax = new HashMap<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                saveMapMin.put(i, list.get(i).getMin());
                saveMapMax.put(i, list.get(i).getMax());
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sugar_control_target, parent, false);
            viewHolder.tvTypeName = convertView.findViewById(R.id.tv_type_name);
            viewHolder.etMin = convertView.findViewById(R.id.et_input_min);
            viewHolder.etMax = convertView.findViewById(R.id.et_input_max);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.data_sugar_time);
        viewHolder.tvTypeName.setText(stringArray[position]);
        //设置editext一个标记
        viewHolder.etMin.setTag(position);
        //清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etMin.clearFocus();
        //将对应保存在集合中的文本内容取出来 并显示上去
        viewHolder.etMin.setText(saveMapMin.get(position));
        final EditText tempEtMin = viewHolder.etMin;
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.etMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //拦截大于40.0
                String temp = editable.toString().trim();
                double inputResult = TurnsUtils.getDouble(temp, 0);
                if (inputResult > 40.0) {
                    finalViewHolder.etMin.setText("");
                    return;
                }
                //设置小数点后一位
                //返回指定字符在此字符串中第一次出现处的索引
                int positionDot = temp.indexOf(".");
                //获取光标位置
                int index = finalViewHolder.etMin.getSelectionStart();
                //如果包含小数点
                if (positionDot >= 0 && temp.length() - 2 > positionDot) {
                    //删除小数点后一位
                    editable.delete(index - 1, index);
                }

                Integer tag = (Integer) tempEtMin.getTag();
                //在这里根据position去保存文本内容
                saveMapMin.put(tag, editable.toString());
            }
        });

        //设置editext一个标记
        viewHolder.etMax.setTag(position);
        //清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etMax.clearFocus();
        //将对应保存在集合中的文本内容取出来 并显示上去
        viewHolder.etMax.setText(saveMapMax.get(position));
        final EditText tempEtMax = viewHolder.etMax;
        ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.etMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //拦截大于40.0
                String temp = editable.toString().trim();
                double inputResult = TurnsUtils.getDouble(temp, 0);
                if (inputResult > 40.0) {
                    finalViewHolder1.etMax.setText("");
                    return;
                }
                //设置小数点后一位
                //返回指定字符在此字符串中第一次出现处的索引
                int positionDot = temp.indexOf(".");
                //获取光标位置
                int index = finalViewHolder1.etMax.getSelectionStart();
                //如果包含小数点
                if (positionDot >= 0 && temp.length() - 2 > positionDot) {
                    //删除小数点后一位
                    editable.delete(index - 1, index);
                }

                Integer tag = (Integer) tempEtMax.getTag();
                //在这里根据position去保存文本内容
                saveMapMax.put(tag, editable.toString());
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvTypeName;
        EditText etMin;
        EditText etMax;
    }
}
