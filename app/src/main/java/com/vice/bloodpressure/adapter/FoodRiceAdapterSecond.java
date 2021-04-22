package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 描述: 食品添加
 * 作者: LYD
 * 创建日期: 2019/5/10 14:58
 */
public class FoodRiceAdapterSecond extends BaseAdapter {
    public HashMap<Integer, String> selectMap;//选中保存
    public HashMap<Integer, String> saveMap;//这个集合用来存储对应位置上Editext中的文本内容
    public HashMap<Integer, String> saveMapSecond;//这个集合用来存储对应位置上Editext中的文本内容
    private List<String> list;
    private Context context;
    private String type;

    public FoodRiceAdapterSecond(Context context, List list, String type) {
        super();
        this.context = context;
        this.list = list;
        this.type = type;
        selectMap = new HashMap<>();
        saveMap = new HashMap<>();
        saveMapSecond = new HashMap<>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food_prescription_second, parent, false);
            viewHolder.rlType = convertView.findViewById(R.id.rl_type);
            viewHolder.tvLeft = convertView.findViewById(R.id.tv_left);
            viewHolder.tvRight = convertView.findViewById(R.id.tv_right);
            viewHolder.etInput = convertView.findViewById(R.id.et_input);
            viewHolder.tvUnitLeft = convertView.findViewById(R.id.tv_unit_left);
            viewHolder.tvUnitRight = convertView.findViewById(R.id.tv_unit_right);
            viewHolder.llSecond = convertView.findViewById(R.id.ll_second);
            viewHolder.etInputSecond = convertView.findViewById(R.id.et_input_second);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //左边文字设置
        switch (type) {
            case "0":
                viewHolder.tvLeft.setText("谷薯种类");
                break;
            case "1":
                viewHolder.tvLeft.setText("蔬菜种类");
                break;
            case "2":
                viewHolder.tvLeft.setText("水果种类");
                break;
            case "3":
                viewHolder.tvLeft.setText("豆制品种类");
                break;
            case "4":
                viewHolder.tvLeft.setText("肉蛋种类");
                break;
            case "5":
                viewHolder.tvLeft.setText("奶制品种类");
                break;
            case "6":
                viewHolder.tvLeft.setText("油脂种类");
                break;
            case "7":
                viewHolder.tvLeft.setText("坚果种类");
                break;
            case "8":
                viewHolder.tvLeft.setText("酒种类");
                break;
        }
        //输入设置
        viewHolder.etInput.setTag(position);//设置editext一个标记
        viewHolder.etInput.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etInput.setText(saveMap.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditText = viewHolder.etInput;
        viewHolder.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditText.getTag();
                saveMap.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //输入设置第二
        viewHolder.etInputSecond.setTag(position);//设置editext一个标记
        viewHolder.etInputSecond.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etInputSecond.setText(saveMapSecond.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditTextSecond = viewHolder.etInputSecond;
        viewHolder.etInputSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditTextSecond.getTag();
                saveMapSecond.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //选择设置
        switch (type) {
            case "0"://谷署
                String[] stringFoodRice = context.getResources().getStringArray(R.array.data_food_rice);
                List<String> foodRiceList = Arrays.asList(stringFoodRice);
                viewHolder.tvRight.setText(selectMap.get(position));
                ViewHolder finalViewHolder = viewHolder;
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                            @Override
                            public void execEvent(String text) {
                                finalViewHolder.tvRight.setText(text);
                                selectMap.put(position, text);
                            }

                        }, foodRiceList);
                    }
                };
                viewHolder.tvRight.setOnClickListener(clickListener);
                viewHolder.rlType.setOnClickListener(clickListener);
                break;
            case "1"://蔬菜
                viewHolder.tvRight.setText("全蔬菜");
                viewHolder.tvRight.setCompoundDrawables(null, null, null, null);
                break;
            case "2"://水果
                String[] stringFoodFruit = context.getResources().getStringArray(R.array.data_food_apricot);
                List<String> foodFruitList = Arrays.asList(stringFoodFruit);
                viewHolder.tvRight.setText(selectMap.get(position));
                ViewHolder finalViewHolderFruit = viewHolder;
                View.OnClickListener clickListenerFruit = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                            @Override
                            public void execEvent(String text) {
                                finalViewHolderFruit.tvRight.setText(text);
                                selectMap.put(position, text);
                            }
                        }, foodFruitList);
                    }
                };
                viewHolder.tvRight.setOnClickListener(clickListenerFruit);
                viewHolder.rlType.setOnClickListener(clickListenerFruit);
                break;
            case "3"://豆制品
                String[] stringBeanFruit = context.getResources().getStringArray(R.array.data_food_yuba);
                List<String> foodBeanList = Arrays.asList(stringBeanFruit);
                viewHolder.tvRight.setText(selectMap.get(position));
                ViewHolder finalViewHolderBean = viewHolder;
                View.OnClickListener clickListenerBean = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                            @Override
                            public void execEvent(String text) {
                                finalViewHolderBean.tvRight.setText(text);
                                selectMap.put(position, text);
                            }

                        }, foodBeanList);
                    }
                };
                viewHolder.tvRight.setOnClickListener(clickListenerBean);
                viewHolder.rlType.setOnClickListener(clickListenerBean);
                break;
            case "4"://肉蛋
                String[] stringMeatFruit = context.getResources().getStringArray(R.array.data_food_meat);
                List<String> foodMeatList = Arrays.asList(stringMeatFruit);
                viewHolder.tvRight.setText(selectMap.get(position));
                ViewHolder finalViewHolderMeat = viewHolder;
                View.OnClickListener clickListenerMeat = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                            @Override
                            public void execEvent(String text) {
                                finalViewHolderMeat.tvRight.setText(text);
                                selectMap.put(position, text);
                            }
                        }, foodMeatList);
                    }
                };
                viewHolder.tvRight.setOnClickListener(clickListenerMeat);
                viewHolder.rlType.setOnClickListener(clickListenerMeat);
                break;
            case "5"://奶制品
                String[] stringMilkFruit = context.getResources().getStringArray(R.array.data_food_milk);
                List<String> foodMilkList = Arrays.asList(stringMilkFruit);
                viewHolder.tvRight.setText(selectMap.get(position));
                ViewHolder finalViewHolderMilk = viewHolder;
                View.OnClickListener clickListenerMilk = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                            @Override
                            public void execEvent(String text) {
                                finalViewHolderMilk.tvRight.setText(text);
                                selectMap.put(position, text);
                            }

                        }, foodMilkList);
                    }
                };
                viewHolder.tvRight.setOnClickListener(clickListenerMilk);
                viewHolder.rlType.setOnClickListener(clickListenerMilk);
                break;
            case "6"://油脂
                viewHolder.tvRight.setText("食用油（1勺）");
                viewHolder.tvRight.setCompoundDrawables(null, null, null, null);
                break;
            case "7"://坚果
                String[] stringNutFruit = context.getResources().getStringArray(R.array.data_food_nut);
                List<String> foodNutList = Arrays.asList(stringNutFruit);
                viewHolder.tvRight.setText(selectMap.get(position));
                ViewHolder finalViewHolderNut = viewHolder;
                View.OnClickListener clickListenerNut = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                            @Override
                            public void execEvent(String text) {
                                finalViewHolderNut.tvRight.setText(text);
                                selectMap.put(position, text);
                            }
                        }, foodNutList);
                    }
                };
                viewHolder.tvRight.setOnClickListener(clickListenerNut);
                viewHolder.rlType.setOnClickListener(clickListenerNut);
                break;
            case "8"://酒
                String[] stringWineFruit = context.getResources().getStringArray(R.array.data_food_wine);
                List<String> foodWineList = Arrays.asList(stringWineFruit);
                viewHolder.tvRight.setText(selectMap.get(position));
                ViewHolder finalViewHolderWine = viewHolder;
                View.OnClickListener clickListenerWine = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PickerUtils.showOption(context, new PickerUtils.TimePickerCallBack() {
                            @Override
                            public void execEvent(String text) {
                                finalViewHolderWine.tvRight.setText(text);
                                selectMap.put(position, text);
                            }

                        }, foodWineList);
                    }
                };
                viewHolder.tvRight.setOnClickListener(clickListenerWine);
                viewHolder.rlType.setOnClickListener(clickListenerWine);
                break;
        }
        //第二行左右设置
        switch (type) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
                viewHolder.tvUnitLeft.setText("分量");
                viewHolder.tvUnitRight.setText("g");
                viewHolder.llSecond.setVisibility(View.GONE);
                break;
            case "8":
                viewHolder.tvUnitLeft.setText("摄入量");
                viewHolder.tvUnitRight.setText("ml");
                viewHolder.llSecond.setVisibility(View.VISIBLE);
                break;
        }
        return convertView;
    }

    public class ViewHolder {
        RelativeLayout rlType;

        TextView tvLeft;
        TextView tvRight;

        EditText etInput;
        TextView tvUnitLeft;
        TextView tvUnitRight;

        LinearLayout llSecond;
        EditText etInputSecond;
    }
}