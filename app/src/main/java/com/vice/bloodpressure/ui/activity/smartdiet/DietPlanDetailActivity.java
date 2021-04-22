package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.DietPlanDetailRvAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.DietPlanChangeBean;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.LoadingDialogUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 描述:  智能食谱详情页
 * 作者: LYD
 * 创建日期: 2020/3/16 11:13
 */
public class DietPlanDetailActivity extends BaseHandlerActivity {
    private static final String TAG = "DietPlanDetailActivity";
    @BindView(R.id.rv_three_meals)
    RecyclerView rvThreeMeals;
    private List<DietPlanFoodChildBean> breakfast;
    private List<DietPlanFoodChildBean> lunch;
    private List<DietPlanFoodChildBean> dinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能食谱");
        setMore();
        int id = getIntent().getIntExtra("id", 0);
        String day = getIntent().getStringExtra("day");
        getDetail(id, day);
    }

    private void setMore() {
        getTvSave().setText("换我想吃");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClearId();
            }
        });
    }

    /**
     * 清除保存id
     */
    private void toClearId() {
        HashMap<String, Object> map = new HashMap<>();
        XyUrl.okPostSave(XyUrl.CLEAR_IDS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                int id = getIntent().getIntExtra("id", 0);
                String day = getIntent().getStringExtra("day");
                Intent intent = new Intent(getPageContext(), DietPlanOneStepsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("breakfast", (Serializable) breakfast);
                bundle.putSerializable("lunch", (Serializable) lunch);
                bundle.putSerializable("dinner", (Serializable) dinner);
                bundle.putInt("id", id);
                bundle.putString("day", day);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    private void getDetail(int id, String day) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getDietDetail(token, id, day)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<DietPlanChangeBean>>() {
                    @Override
                    protected void onSuccess(BaseData<DietPlanChangeBean> dietPlanDetailBeanBaseData) {
                        DietPlanChangeBean data = dietPlanDetailBeanBaseData.getData();
                        breakfast = data.getBreakfast();
                        lunch = data.getLunch();
                        dinner = data.getDinner();
                        setData();
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });

    }

    private void setData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i + "");
        }
        //饮食Id
        int id = getIntent().getIntExtra("id", 0);
        String day = getIntent().getStringExtra("day");
        DietPlanDetailRvAdapter adapter = new DietPlanDetailRvAdapter(list, id, day, breakfast, lunch, dinner, this);
        rvThreeMeals.setLayoutManager(new LinearLayoutManager(this));
        rvThreeMeals.setAdapter(adapter);
        //设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                getEatChange(position);
            }
        });
    }


    private void getEatChange(int position) {
        int id = getIntent().getIntExtra("id", 0);
        String day = getIntent().getStringExtra("day");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        LoadingDialogUtils.show(this);
        RxHttpUtils.createApi(Service.class)
                .getEatChange(token, id, day, position + 1 + "")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<DietPlanFoodChildBean>>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        LoadingDialogUtils.unInit();
                    }

                    @Override
                    protected void onSuccess(BaseData<List<DietPlanFoodChildBean>> listBaseData) {
                        LoadingDialogUtils.unInit();
                        List<DietPlanFoodChildBean> listData = listBaseData.getData();
                        switch (position) {
                            case 0:
                                breakfast = listData;
                                break;
                            case 1:
                                lunch = listData;
                                break;
                            case 2:
                                dinner = listData;
                                break;
                        }
                        setData();
                    }
                });
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_diet_plan_detail, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
