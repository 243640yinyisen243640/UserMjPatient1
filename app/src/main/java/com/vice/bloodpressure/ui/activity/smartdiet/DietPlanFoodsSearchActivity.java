package com.vice.bloodpressure.ui.activity.smartdiet;

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
import com.vice.bloodpressure.net.Service;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  食物搜索页面
 * 作者: LYD
 * 创建日期: 2020/3/24 13:15
 */
public class DietPlanFoodsSearchActivity extends BaseHandlerActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    ColorTextView tvSearch;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private int selectPosition = -100;

    private List<DietPlanFoodChildBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索");
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSave();
            }
        });
    }

    private void toSave() {
        if (-100 == selectPosition) {
            ToastUtils.showShort("请选择你搜索的产品");
            return;
        } else {
            DietPlanFoodChildBean bean = data.get(selectPosition);
            //EventBus发送
            EventBusUtils.post(new EventMessage<>(ConstantParam.SEARCH_TO_SELECT, bean));
            getIntent().putExtra("searchBean", bean);
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_diet_plan_foods_search, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        String keyWord = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入食物名称");
            return;
        }
        toSearch(keyWord);
    }


    /**
     * 搜索食物
     *
     * @param keyWord
     */
    private void toSearch(String keyWord) {
        String type = getIntent().getStringExtra("type");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getGreensSearch(token, keyWord, "", type)
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
}
