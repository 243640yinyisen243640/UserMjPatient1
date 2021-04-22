package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.FoodsCategoryBean;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordlist.FoodTypeActivity;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.MyListView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 添加饮食记录
 * 作者: LYD
 * 创建日期: 2020/4/21 11:57
 */
public class FoodAddActivity extends BaseHandlerEventBusActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "FoodAddActivity";
    private static final String[] dosageNameList = {"请选择", "早餐", "午餐", "晚餐", "加餐"};
    private static final int GET_FOOD_TYPE_FIRST_LEVEL = 10010;
    private static final int GET_FOOD_TYPE_SECOND_LEVEL = 10011;
    @BindView(R.id.rl_food_type)
    RelativeLayout rlFoodType;
    @BindView(R.id.spinner_meal_type)
    Spinner spinnerMealType;
    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;
    @BindView(R.id.lv_food_add)
    MyListView lvFoodAdd;
    @BindView(R.id.et_total)
    EditText etTotal;
    @BindView(R.id.fl_et_total)
    FrameLayout flEtTotal;
    private ArrayList<FoodsCategoryListBean> list = new ArrayList<>();
    private ArrayList<String> foodList;
    private String counts;
    private String units;
    private int cardNumber;

    //食物类型
    //private HashMap<Integer, List<FoodsCategoryListBean>> secondFoodListHashMap;
    private List<FoodsCategoryBean> firstFoodList;
    private ArrayList<String> typeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //食物类型获取
        getFoodTypeFirstLevel();
        setTitle("记录饮食");
        initView();
        setTimeAndFoodType();
        showTvSave();
        setEtTotal();
    }


    /**
     * 获取食物类型一级
     */
    private void getFoodTypeFirstLevel() {
        HashMap<String, Object> map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_FOOD_CATEGORY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryBean> foodList = JSONObject.parseArray(value, FoodsCategoryBean.class);
                Message message = Message.obtain();
                message.what = GET_FOOD_TYPE_FIRST_LEVEL;
                message.obj = foodList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
            }
        });
    }

    /**
     * 获取食物类型二级
     *
     * @param id
     */
    private void getFoodTypeSecondLevel(int id) {
        HashMap map = new HashMap<>();
        map.put("classify", id);
        XyUrl.okPost(XyUrl.GET_FOOD_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> foodList = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = Message.obtain();
                message.what = GET_FOOD_TYPE_SECOND_LEVEL;
                message.obj = foodList;
                message.arg1 = id;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });

    }

    private void setEtTotal() {
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() > 0) {
                    flEtTotal.setBackgroundResource(R.drawable.food_add_total_check);
                } else {
                    flEtTotal.setBackgroundResource(R.drawable.food_add_total_uncheck);
                }
            }
        }, etTotal);
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_food_add, contentLayout, false);
    }

    private void initView() {
        TextView tvSave = getTvSave();
        tvSave.setText("保存");
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardNumber == 0) {
                    ToastUtils.showShort("请选择用餐类型");
                    return;
                }
                counts = "";
                units = "";
                for (int i = 0; i < list.size(); i++) {
                    EditText temp = lvFoodAdd.getChildAt(i).findViewById(R.id.et_food_count);
                    TextView textView = lvFoodAdd.getChildAt(i).findViewById(R.id.tv_food_kcal);
                    String g = textView.getText().toString();
                    String count = temp.getText().toString().trim();
                    if (TextUtils.isEmpty(count)) {
                        ToastUtils.showShort("请输入数量");
                        return;
                    }
                    counts = String.format("%s%s,", counts, count);
                    units = String.format("%s%s,", units, g);
                }
                String time = tvSelectTime.getText().toString().trim();
                if (TextUtils.isEmpty(time)) {
                    ToastUtils.showShort("请选择时间");
                    return;
                }
                if (list.size() == 0) {
                    ToastUtils.showShort("请添加食物");
                } else {
                    saveData(counts, time);
                }

            }
        });
        spinnerMealType.setOnItemSelectedListener(this);
        //spinner选项
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dosageNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealType.setAdapter(adapter);
    }


    private void setTimeAndFoodType() {
        SimpleDateFormat allFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), allFormat);
        tvSelectTime.setText(allTimeString);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String hour = null;
        try {
            hour = simpleDateFormat.format(allFormat.parse(allTimeString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int position;
        int time = Integer.parseInt(hour);
        if (time >= 5 && time < 9) {
            position = 1;
        } else if (time >= 10 && time < 15) {
            position = 2;
        } else if (time >= 15 && time < 19) {
            position = 3;
        } else {
            position = 4;
        }
        spinnerMealType.setSelection(position);
    }


    private void saveData(String count, String time) {
        String foodNames = "";
        String foodPics = "";
        for (int i = 0; i < list.size(); i++) {
            String tempName = list.get(i).getFoodname();
            String tempUrl = list.get(i).getPicurl();
            foodNames = foodNames + tempName + ",";
            foodPics = foodPics + tempUrl + ",";
        }
        Map<String, Object> map = new HashMap<>();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        foodNames = foodNames.substring(0, foodNames.length() - 1);
        foodPics = foodPics.substring(0, foodPics.length() - 1);
        units = units.substring(0, units.length() - 1);
        count = count.substring(0, count.length() - 1);
        map.put("foodname", foodNames);
        map.put("imgsrc", foodPics);
        map.put("dosage", count);
        map.put("category", cardNumber);
        map.put("datetime", time);
        map.put("kcalval", units);
        XyUrl.okPostSave(XyUrl.HEALTH_RECORD_ADD_FOOD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                EventBusUtils.post(new EventMessage<>(ConstantParam.FOOD_RECORD_ADD));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    private void setAllKcal() {
        long kcalAll = 0;
        for (int i = 0; i < lvFoodAdd.getCount(); i++) {
            View item = lvFoodAdd.getChildAt(i);
            TextView itemKcal = item.findViewById(R.id.tv_food_kcal);
            long kcal = Long.parseLong(itemKcal.getText().toString());
            kcalAll = kcalAll + kcal;
        }
        flEtTotal.setBackgroundResource(R.drawable.food_add_total_check);
        etTotal.setText("" + kcalAll);
    }


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.FOOD_SELECT:
                list = (ArrayList<FoodsCategoryListBean>) event.getData();
                CommonAdapter adapter = new CommonAdapter<FoodsCategoryListBean>(getPageContext(),
                        R.layout.item_foodadd_listview, list) {
                    @Override
                    protected void convert(ViewHolder viewHolder, FoodsCategoryListBean item, int position) {
                        String foodName;
                        if (item.getFoodname().length() > 7) {
                            foodName = item.getFoodname().substring(0, 7) + "...";
                        } else {
                            foodName = item.getFoodname();
                        }
                        viewHolder.setText(R.id.tv_food_name, foodName);
                        EditText editText = viewHolder.getView(R.id.et_food_count);
                        TextView tvDel = viewHolder.getView(R.id.tv_delete);
                        tvDel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reSetFoodList(position);
                                list.remove(position);
                                notifyDataSetChanged();
                                reSetListView();
                                setAllKcal();
                            }
                        });
                        float kcal_1g = ((float) TurnsUtils.getInt(item.getKcalval(), 0)) / (float) 100;
                        editText.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (s.toString().equals("")) {
                                    viewHolder.setText(R.id.tv_food_kcal, "0");
                                    setAllKcal();
                                } else {
                                    int g = Integer.parseInt(s.toString());
                                    float KCAL = g * kcal_1g;
                                    viewHolder.setText(R.id.tv_food_kcal, "" + (int) KCAL);
                                    setAllKcal();
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                };
                lvFoodAdd.setAdapter(adapter);
                break;
        }
    }

    private void reSetFoodList(int position) {
        foodList = new ArrayList<>();
        for (int i = 0; i < lvFoodAdd.getCount(); i++) {
            View view = lvFoodAdd.getChildAt(i);
            EditText editText = view.findViewById(R.id.et_food_count);
            String g = editText.getText().toString();
            foodList.add(g);
        }
        foodList.remove(position);
    }

    private void reSetListView() {
        for (int i = 0; i < lvFoodAdd.getCount(); i++) {
            View view = lvFoodAdd.getChildAt(i);
            EditText editText = view.findViewById(R.id.et_food_count);
            editText.setText(foodList.get(i));
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cardNumber = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick({R.id.rl_food_type, R.id.tv_select_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_food_type:
                Intent intent = new Intent(this, FoodTypeActivity.class);
                //ntent.putStringArrayListExtra("title", typeArray);
                startActivity(intent);
                break;
            case R.id.tv_select_time:
                PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvSelectTime.setText(content);
                    }
                });
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_FOOD_TYPE_FIRST_LEVEL:
                firstFoodList = (List<FoodsCategoryBean>) msg.obj;
                if (firstFoodList != null && firstFoodList.size() > 0) {
                    //标题
                    for (int i = 0; i < firstFoodList.size(); i++) {
                        typeArray.add(firstFoodList.get(i).getFoodlei());
                    }
                }
                break;
        }
    }
}
