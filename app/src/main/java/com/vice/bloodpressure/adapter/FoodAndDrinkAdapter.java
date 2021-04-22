package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FoodAndDrinkBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FoodAndDrinkAdapter extends CommonAdapter<FoodAndDrinkBean> {
    private Context context;

    public FoodAndDrinkAdapter(Context context, int layoutId, List<FoodAndDrinkBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, FoodAndDrinkBean item, int position) {
        viewHolder.setText(R.id.tv_time, item.getDatetime());
        switch (item.getCategory()) {
            case "1":
                viewHolder.setText(R.id.tv_type, "早餐");
                break;
            case "2":
                viewHolder.setText(R.id.tv_type, "午餐");
                break;
            case "3":
                viewHolder.setText(R.id.tv_type, "晚餐");
                break;
            case "4":
                viewHolder.setText(R.id.tv_type, "加餐");
                break;
        }

        ListView listView = viewHolder.getView(R.id.lv_record_food);
        listView.setClickable(false);
        listView.setPressed(false);
        listView.setEnabled(false);
        List<FoodAndDrinkBean.FoodsBean> list = item.getFoods();
        CommonAdapter adapter = new CommonAdapter<FoodAndDrinkBean.FoodsBean>(context,
                R.layout.item_food_recoder_listview, list) {
            @Override
            protected void convert(ViewHolder viewHolder, FoodAndDrinkBean.FoodsBean item, int position) {
                if (item.getFoodname().length() > 7) {
                    String foodName = item.getFoodname().substring(0, 7) + "...";
                    viewHolder.setText(R.id.tv_name, foodName);
                } else
                    viewHolder.setText(R.id.tv_name, item.getFoodname());
                viewHolder.setText(R.id.tv_count, item.getDosage() + "g");
                viewHolder.setText(R.id.tv_listItem_foodKcal, item.getKcal());
            }
        };

        listView.setAdapter(adapter);

        //显示总卡路里
        int kcalAll = 0;
        for (int i = 0; i < list.size(); i++) {
            int kcalItem = Integer.parseInt(list.get(i).getKcal());
            //int kcalItem = 10;
            kcalAll += kcalItem;
        }
        TextView textView = viewHolder.getView(R.id.tv_kcal);
        textView.setText("" + kcalAll);


    }
}
