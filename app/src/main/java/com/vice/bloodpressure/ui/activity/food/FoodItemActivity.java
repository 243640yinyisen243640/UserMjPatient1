package com.vice.bloodpressure.ui.activity.food;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.ListCastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodItemActivity extends BaseHandlerActivity {

    private static final int GET_CATEGORY = 1;
    private ListView lvFood;
    private ArrayList<String> titleListItem, kcalvalListItem, carbohyListItem;
    private int[] foodId;
    private String activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTitle = getIntent().getStringExtra("TITLE");
        setTitle(activityTitle);
        initViews();
        getListViewItemsData();

    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_food_item, contentLayout, false);
    }

    private void initViews() {
        lvFood = findViewById(R.id.lv_food);
    }

    private void getListViewItemsData() {
        int ID = getIntent().getIntExtra("ID", -1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("classify", ID);
        XyUrl.okPost(XyUrl.GET_FOOD_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> showMessage = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_CATEGORY;
                message.obj = showMessage;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) throws JSONException {

            }
        });
    }

    private void initListItem() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < titleListItem.size(); i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("title", titleListItem.get(i));
            item.put("energy", kcalvalListItem.get(i));
            item.put("weight", carbohyListItem.get(i));
            list.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(FoodItemActivity.this, list, R.layout.item_fooditem_listview,
                new String[]{"title", "energy", "weight"}, new int[]{R.id.tv_food_item_title, R.id.tv_food_item_energy,
                R.id.tv_food_item_weight});


        lvFood.setAdapter(adapter);
        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FoodItemActivity.this, FoodDetailActivity.class);
                intent.putExtra("ID", foodId[position]);
                intent.putExtra("TITLE", activityTitle);
                startActivity(intent);
            }
        });

    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_CATEGORY:
                List<FoodsCategoryListBean> showMessage = ListCastUtils.castList(msg.obj, FoodsCategoryListBean.class);

                titleListItem = new ArrayList<>();
                carbohyListItem = new ArrayList<>();
                kcalvalListItem = new ArrayList<>();
                foodId = new int[showMessage.size()];

                for (int i = 0; i < showMessage.size(); i++) {
                    titleListItem.add(showMessage.get(i).getFoodname());
                    carbohyListItem.add(showMessage.get(i).getCarbohy());
                    kcalvalListItem.add(showMessage.get(i).getKcalval());
                    foodId[i] = showMessage.get(i).getId();
                }

                initListItem();
                break;
        }
    }
}
