package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.DietPlanSelectFoodAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 单独选择某个菜
 * 作者: LYD
 * 创建日期: 2020/3/25 13:22
 */
public class DietPlanFoodsSelectAndSearchActivity extends BaseHandlerActivity {
    private static final int ADD_SUCCESS = 10010;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    ColorTextView tvSearch;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private List<DietPlanFoodChildBean> data;
    private int selectPosition = -100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能食谱");
        getSameFood();
        setMore();
    }

    private void setMore() {
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveYourLikeFood();
            }
        });
    }


    private void saveYourLikeFood() {
        if (-100 == selectPosition) {
            ToastUtils.showShort("请选择你喜欢的菜品");
            return;
        } else {
            int id = data.get(selectPosition).getId();
            String day = getIntent().getStringExtra("day");
            String type = getIntent().getStringExtra("type");
            int index = getIntent().getIntExtra("index", 0);
            int dietid = getIntent().getIntExtra("dietPlanId", 0);
            HashMap map = new HashMap<>();
            map.put("dietid", dietid);
            map.put("day", day);
            map.put("type", type);
            map.put("index", index);
            map.put("greensid", id);
            XyUrl.okPostSave(XyUrl.GREEN_CHANGE, map, new OkHttpCallBack<String>() {
                @Override
                public void onSuccess(String value) {
                    Message message = getHandlerMessage();
                    message.what = ADD_SUCCESS;
                    sendHandlerMessage(message);
                }

                @Override
                public void onError(int errorCode, String errorMsg) {
                    ToastUtils.showShort(errorMsg);
                }
            });
        }
    }

    private void getSameFood() {
        int id = getIntent().getIntExtra("greensid", 0);
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getGreenChangeOneList(token, id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<DietPlanFoodChildBean>>>() {
                    @Override
                    protected void onSuccess(BaseData<List<DietPlanFoodChildBean>> dietPlanDetailBeanBaseData) {
                        data = dietPlanDetailBeanBaseData.getData();
                        if (data != null && data.size() > 0) {
                            setAdapter();
                        } else {
                            showErrorPage();
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        showErrorPage();
                    }
                });
    }

    private void showErrorPage() {
        llEmpty.setVisibility(View.VISIBLE);
        rvList.setVisibility(View.GONE);
    }

    private void setAdapter() {
        llEmpty.setVisibility(View.GONE);
        rvList.setVisibility(View.VISIBLE);
        DietPlanSelectFoodAdapter adapter = new DietPlanSelectFoodAdapter(data);
        rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                selectPosition = position;
            }
        });

    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_diet_plan_foods_select_and_search, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            case ADD_SUCCESS:
                int id = getIntent().getIntExtra("dietPlanId", 0);
                //清空答题页面
                finish();
                EventBusUtils.post(new EventMessage<>(ConstantParam.DIET_PLAN_CHANGE_REFRESH));
                //跳转详情
                intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        String keyWord = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入菜品名称");
            return;
        }
        toDoSearch(keyWord);
    }


    /**
     * 搜索
     *
     * @param keyWord
     */
    private void toDoSearch(String keyWord) {
        //String type = getIntent().getStringExtra("type");
        int id = getIntent().getIntExtra("greensid", 0);
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getGreensSearch(token, keyWord, id + "", "")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<DietPlanFoodChildBean>>>() {
                    @Override
                    protected void onSuccess(BaseData<List<DietPlanFoodChildBean>> dietPlanDetailBeanBaseData) {
                        data = dietPlanDetailBeanBaseData.getData();
                        if (data != null && data.size() > 0) {
                            setAdapter();
                        } else {
                            showErrorPage();
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        showErrorPage();
                    }
                });
    }
}
