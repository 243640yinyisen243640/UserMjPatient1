package com.vice.bloodpressure.ui.activity.food;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.ListCastUtils;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodSearchActivity extends BaseHandlerActivity {

    private static final int GET_SEARCH = 1;


    private int[] mIds;

    private EditText etSearch;
    private ListView lvFoodSearch;
    private String searchStr;

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_food_search, contentLayout, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchStr = getIntent().getStringExtra("searchData");
        hideTitleBar();
        initViews();
        getSearchData();
        initSearch();

    }


    private void initViews() {
        lvFoodSearch = findViewById(R.id.lv_food_search);

        ImageView ivTopBack = findViewById(R.id.iv_top_back);
        ivTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearch = findViewById(R.id.et_food_warehouse_input);
    }


    private void getSearchData() {
        etSearch.setText(searchStr);
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", searchStr);
        XyUrl.okPost(XyUrl.GET_FOOD_SEARCH, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> showMessage = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_SEARCH;
                message.obj = showMessage;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) throws JSONException {

            }
        });
    }

    private void initListViewItems() {

        lvFoodSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FoodSearchActivity.this, FoodDetailActivity.class);
                intent.putExtra("ID", mIds[position]);
                startActivity(intent);

            }
        });

    }

    private void initSearch() {
        EditTextUtils.setOnEditorActionListener(etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                String searchData = etSearch.getText().toString();
                if (TextUtils.isEmpty(searchData)) {
                    ToastUtils.showShort("请输入搜索内容");
                    //Toast.makeText(FoodSearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    searchStr = searchData;
                    lvFoodSearch.setAdapter(null);
                    getSearchData();
                }
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SEARCH:
                List<FoodsCategoryListBean> showMessage = ListCastUtils.castList(msg.obj, FoodsCategoryListBean.class);

                ArrayList<String> title = new ArrayList<>();
                ArrayList<String> energy = new ArrayList<>();
                ArrayList<String> weight = new ArrayList<>();
                mIds = new int[showMessage.size()];

                for (int i = 0; i < showMessage.size(); i++) {
                    title.add(showMessage.get(i).getFoodname());
                    energy.add(showMessage.get(i).getKcalval());
                    weight.add(showMessage.get(i).getCarbohy());
                    mIds[i] = showMessage.get(i).getId();
                }

                List<Map<String, Object>> list = new ArrayList<>();
                for (int i = 0; i < title.size(); i++) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("title", title.get(i));
                    item.put("energy", energy.get(i));
                    item.put("weight", weight.get(i));
                    list.add(item);
                }

                SimpleAdapter adapter = new SimpleAdapter(FoodSearchActivity.this, list, R.layout.item_fooditem_listview,
                        new String[]{"title", "energy", "weight"}, new int[]{R.id.tv_food_item_title, R.id.tv_food_item_energy,
                        R.id.tv_food_item_weight});


                lvFoodSearch.setAdapter(adapter);

                initListViewItems();
                break;
        }
    }
}
