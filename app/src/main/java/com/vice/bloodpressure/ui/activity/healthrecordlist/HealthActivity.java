package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.HealthAdapter;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodOxygenAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodPressureAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodSugarAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BmiAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.CheckAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.FoodAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.HemoglobinAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.HepatopathyPabulumAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.PharmacyAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.SportAddActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.WeightAddActivity;

/**
 * 描述: 健康记录首页
 * 作者: LYD
 * 创建日期: 2019/3/18 13:55
 */

public class HealthActivity extends BaseActivity {

    private Intent intent;

    private TypedArray healthImages = Utils.getApp().getResources().obtainTypedArray(R.array.health_item_img);
    private String[] healthNames = Utils.getApp().getResources().getStringArray(R.array.health_item_name);
    private String[] healthSlogans = Utils.getApp().getResources().getStringArray(R.array.health_item_slogan);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("健康记录");
        initView();
    }

    private void initView() {
        ListView lvHealth = findViewById(R.id.lv_health);
        HealthAdapter healthAdapter = new HealthAdapter(this, healthNames, healthSlogans, healthImages, new HealthAdapter.HealthInterface() {
            @Override
            public void addOnClick(int position) {
                switch (position) {
                    case 0:
                        intent = new Intent(getPageContext(), BloodSugarAddActivity.class);//添加血糖
                        break;
                    case 1:
                        intent = new Intent(getPageContext(), BloodPressureAddActivity.class);//添加血压
                        break;
                    case 2:
                        intent = new Intent(getPageContext(), FoodAddActivity.class);//添加饮食
                        break;
                    case 3:
                        intent = new Intent(getPageContext(), SportAddActivity.class);//添加运动
                        break;
                    case 4:
                        intent = new Intent(getPageContext(), PharmacyAddActivity.class);//添加用药
                        intent.putExtra("type", "0");
                        break;
                    case 5:
                        intent = new Intent(getPageContext(), BmiAddActivity.class);//添加BMI
                        break;
                    case 6:
                        intent = new Intent(getPageContext(), HemoglobinAddActivity.class);//添加血红蛋白
                        break;
                    case 7:
                        intent = new Intent(getPageContext(), CheckAddActivity.class);//添加检查
                        break;
                    case 8:
                        intent = new Intent(getPageContext(), HepatopathyPabulumAddActivity.class);//肝病营养
                        break;
                    case 9:
                        intent = new Intent(getPageContext(), WeightAddActivity.class);
                        break;
                    case 10:
                        intent = new Intent(getPageContext(), BloodOxygenAddActivity.class);
                        break;
                }
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void lookRecord(int position) {//查看记录
                switch (position) {
                    case 0:
                        intent = new Intent(getPageContext(), BloodSugarListActivity.class);//血糖记录
                        break;
                    case 1:
                        intent = new Intent(getPageContext(), BloodPressureListActivity.class);//血压记录
                        break;
                    case 2:
                        intent = new Intent(getPageContext(), FoodListActivity.class);//饮食记录
                        break;
                    case 3:
                        intent = new Intent(getPageContext(), SportListActivity.class);//运动记录
                        break;
                    case 4:
                        intent = new Intent(getPageContext(), MedicineUseListActivity.class);//用药记录
                        break;
                    case 5:
                        intent = new Intent(getPageContext(), BmiListActivity.class);//体重记录
                        break;
                    case 6:
                        intent = new Intent(getPageContext(), HemoglobinListActivity.class);//血红蛋白记录
                        break;
                    case 7:
                        intent = new Intent(getPageContext(), CheckListActivity.class);//检查记录
                        break;
                    case 8:
                        intent = new Intent(getPageContext(), HepatopathyPabulumListActivity.class);//肝病记录
                        break;
                    case 9:
                        intent = new Intent(getPageContext(), WeightListActivity.class);//体重记录
                        break;
                    case 10:
                        intent = new Intent(getPageContext(), BloodOxygenListActivity.class);//血氧记录
                        break;
                }
                intent.putExtra("key", position);
                startActivity(intent);
            }
        });
        lvHealth.setAdapter(healthAdapter);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_health, contentLayout, false);
    }
}
