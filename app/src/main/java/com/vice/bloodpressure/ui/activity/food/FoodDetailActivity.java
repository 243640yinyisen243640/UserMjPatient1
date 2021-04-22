package com.vice.bloodpressure.ui.activity.food;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FoodsDetailBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import org.json.JSONException;

import java.util.HashMap;

public class FoodDetailActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 12345;
    private TextView tvFoodDetailTitle;
    private String foodName;
    //食物每份克数,血糖生成指数,热量,蛋白质,脂肪,碳水化合物
    private TextView tvFoodDetailExplain, tvFoodDetailGival, tvFoodDetailKcalval, tvFoodDetailProtein, tvFoodDetailFatval, tvFoodDetailCarbohy;
    private String explain, gival, kcalval, protein, fatval, carbohy;
    //营养价值
    private TextView tvFoodDetailNutrition;
    private String nutrition;
    //抗糖贴士
    private TextView tvFoodDetailKtang;
    private String ktang;
    //并发症益处
    private TextView tvFoodDetailBenefits;
    private String benefits;
    private LinearLayout llFirst, llSecond, llThird;

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_food_detial, contentLayout, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String activityTitle = getIntent().getStringExtra("TITLE");
        setTitle(activityTitle);
        initViews();
        getFoodDetailData();
    }

    private void initViews() {
        tvFoodDetailTitle = findViewById(R.id.tv_food_detail_title);
        tvFoodDetailExplain = findViewById(R.id.tv_food_detail_explain);
        tvFoodDetailGival = findViewById(R.id.tv_food_detail_gival);
        tvFoodDetailKcalval = findViewById(R.id.tv_food_detail_kcalval);
        tvFoodDetailProtein = findViewById(R.id.tv_food_detail_protein);
        tvFoodDetailFatval = findViewById(R.id.tv_food_detail_fatval);
        tvFoodDetailCarbohy = findViewById(R.id.tv_food_detail_carbohy);
        tvFoodDetailNutrition = findViewById(R.id.tv_food_detail_nutrition);
        tvFoodDetailKtang = findViewById(R.id.tv_food_detail_anti_sugar);
        tvFoodDetailBenefits = findViewById(R.id.tv_food_detail_benefits);
        llFirst = findViewById(R.id.ll_food_detail_nutrition);
        llSecond = findViewById(R.id.ll_food_detail_anti_sugar);
        llThird = findViewById(R.id.ll_food_detail_complication);
    }

    private void getFoodDetailData() {
        int ID = getIntent().getIntExtra("ID", -1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", ID);
        XyUrl.okPost(XyUrl.GET_FOOD_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                FoodsDetailBean showMessage = JSONObject.parseObject(value, FoodsDetailBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = showMessage;
                sendHandlerMessage(message);

            }

            @Override
            public void onError(int errorCode, String errorMsg) throws JSONException {

            }
        });

    }

    private void initFoodDetail() {
        tvFoodDetailTitle.setText(foodName);
        tvFoodDetailExplain.setText(explain);
        tvFoodDetailGival.setText(gival);
        tvFoodDetailKcalval.setText(kcalval);
        tvFoodDetailProtein.setText(protein);
        tvFoodDetailFatval.setText(fatval);
        tvFoodDetailCarbohy.setText(carbohy);
        if (nutrition.equals(""))
            llFirst.setVisibility(View.GONE);
        else
            tvFoodDetailNutrition.setText(nutrition);

        if (ktang.equals(""))
            llSecond.setVisibility(View.GONE);
        else
            tvFoodDetailKtang.setText(ktang);

        if (benefits.equals(""))
            llThird.setVisibility(View.GONE);
        else
            tvFoodDetailBenefits.setText(benefits);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                FoodsDetailBean showMessage = (FoodsDetailBean) msg.obj;

                foodName = showMessage.getFoodname();

                explain = showMessage.getExplain();
                gival = showMessage.getGival();
                kcalval = showMessage.getKcalval();
                protein = showMessage.getProtein();
                fatval = showMessage.getFatval();
                carbohy = showMessage.getCarbohy();

                nutrition = showMessage.getNutrition();
                ktang = showMessage.getKtang();
                benefits = showMessage.getBenefits();

                initFoodDetail();
                break;
        }
    }
}
