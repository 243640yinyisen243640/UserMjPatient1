package com.vice.bloodpressure.ui.activity.food;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.WarehouseAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FoodsCategoryBean;
import com.vice.bloodpressure.bean.Warehouse;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.ListCastUtils;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;
import com.lyd.baselib.widget.view.decoration.GridSpacingItemDecoration;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FoodWarehouseActivity extends BaseHandlerActivity {
    private static final int GET_CATEGORY = 3;
    private static final String TAG = "FoodWarehouseActivity";
    private RecyclerView rvFoodWarehouse;
    private EditText etSearch;
    private List<Warehouse> warehouseList;

    private TypedArray foodWarehouseImages = Utils.getApp().getResources().obtainTypedArray(R.array.food_warehouse_img);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("食材库");
        initViews();
        initSearch();
        getFoodItemData();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_foodwarehouse, contentLayout, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews() {
        etSearch = findViewById(R.id.et_food_warehouse_input);
        rvFoodWarehouse = findViewById(R.id.rv_food_warehouse);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setGridViewItem() {
        WarehouseAdapter warehouseAdapter = new WarehouseAdapter(this, warehouseList);
        rvFoodWarehouse.setLayoutManager(new GridLayoutManager(this, 4));
        rvFoodWarehouse.setAdapter(warehouseAdapter);
        rvFoodWarehouse.addItemDecoration(new GridSpacingItemDecoration(4, 27, false));
        //通过在adapter中提供回调来实现item的点击事件
        warehouseAdapter.setOnItemClickListener(new WarehouseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getPageContext(), FoodItemActivity.class);
                intent.putExtra("ID", warehouseList.get(position).getId());
                intent.putExtra("TITLE", warehouseList.get(position).getName());
                startActivity(intent);
            }
        });
    }


    private void getFoodItemData() {
        HashMap<String, Object> map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_FOOD_CATEGORY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryBean> showMessage = JSONObject.parseArray(value, FoodsCategoryBean.class);
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

    private void initSearch() {
        EditTextUtils.setOnEditorActionListener(etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                String searchData = etSearch.getText().toString();
                if (TextUtils.isEmpty(searchData)) {
                    ToastUtils.showShort("请输入搜索内容");
                    //Toast.makeText(FoodWarehouseActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(FoodWarehouseActivity.this, FoodSearchActivity.class);
                    intent.putExtra("searchData", searchData);
                    startActivity(intent);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_CATEGORY:
                List<FoodsCategoryBean> showMessageCategory = ListCastUtils.castList(msg.obj, FoodsCategoryBean.class);
                ArrayList<String> categoryUrl = new ArrayList<>();
                ArrayList<String> categoryName = new ArrayList<>();
                int[] categoryID = new int[0];
                if (showMessageCategory != null) {
                    categoryID = new int[showMessageCategory.size()];
                    for (int i = 0; i < showMessageCategory.size(); i++) {
                        categoryName.add(showMessageCategory.get(i).getFoodlei());
                        //categoryUrl.add(showMessageCategory.get(i).getImgurl());
                        categoryID[i] = showMessageCategory.get(i).getId();
                    }
                }
                warehouseList = new ArrayList<>();
                for (int i = 0; i < categoryUrl.size(); i++) {
                    warehouseList.add(new Warehouse(categoryID[i], categoryName.get(i),
                            foodWarehouseImages.getResourceId(i, R.drawable.default_image)));
                }
                setGridViewItem();
                break;
        }
    }

}