package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.InHospitalAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.InHospitalEntity;
import com.vice.bloodpressure.net.Service;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: HospitalizationAppointmentListActivity
 * @Description: 预约住院列表
 * @Author: zwk
 * @CreateDate: 2020/1/3 15:18
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/1/3 15:18
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class HospitalizationAppointmentListActivity extends BaseHandlerEventBusActivity {

    private static final int INIT = 0x0001;
    private static final int LORD_MORE = 0x0002;
    private static final int REFRESH = 0x0003;
    @BindView(R.id.rv_app)
    RecyclerView rvApp;
    @BindView(R.id.srl_app)
    SmartRefreshLayout srlApp;
    @BindView(R.id.tv_add)
    ColorTextView tvAdd;
    private int page = 1;
    private InHospitalAdapter adapter;
    private List<InHospitalEntity> inHospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("预约住院");
        initData(INIT);
        initRefresh();
    }

    private void initData(int type) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        String signid = user.getSignid();
        RxHttpUtils.createApi(Service.class)
                .familyInhospitalList(token, page, Integer.valueOf(signid))
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<InHospitalEntity>>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<List<InHospitalEntity>> listBaseData) {
                        switch (type) {
                            case INIT:
                                inHospitals = listBaseData.getData();
                                if (inHospitals == null) {
                                    inHospitals = new ArrayList<>();
                                }
                                adapter = new InHospitalAdapter(HospitalizationAppointmentListActivity.this, R.layout.item_appointment, inHospitals);
                                rvApp.setAdapter(adapter);
                                rvApp.setLayoutManager(new LinearLayoutManager(HospitalizationAppointmentListActivity.this));
                                break;
                            case LORD_MORE:
                                if (listBaseData.getData() != null) {
                                    inHospitals.addAll(listBaseData.getData());
                                }
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                                srlApp.finishLoadMore();
                                break;
                            case REFRESH:
                                inHospitals = listBaseData.getData();
                                if (inHospitals == null) {
                                    inHospitals = new ArrayList<>();
                                }
                                adapter = new InHospitalAdapter(HospitalizationAppointmentListActivity.this, R.layout.item_appointment, inHospitals);
                                rvApp.setAdapter(adapter);
                                rvApp.setLayoutManager(new LinearLayoutManager(HospitalizationAppointmentListActivity.this));
                                srlApp.finishRefresh();
                                break;

                        }

                    }
                });
    }


    private void initRefresh() {
        srlApp.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlApp.finishRefresh(2000);
                page = 1;
                initData(REFRESH);
            }
        });
        srlApp.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlApp.finishLoadMore(2000);
                page++;
                initData(LORD_MORE);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hospitalization_appointment_list, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        startActivity(new Intent(this, HospitalizationAppointmentAddActivity.class));
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        if (event.getCode() == 123) {
            page = 1;
            initData(REFRESH);
        }
    }
}
