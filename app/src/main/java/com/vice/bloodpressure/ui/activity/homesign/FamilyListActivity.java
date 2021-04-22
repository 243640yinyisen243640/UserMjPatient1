package com.vice.bloodpressure.ui.activity.homesign;

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
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FamilyMemberAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FamilyMemberBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FamilyListActivity extends BaseHandlerActivity {

    @BindView(R.id.rv_family)
    RecyclerView rvFamily;
    private FamilyMemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的家庭");
        init();
    }

    private void init() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .familyIndex(token)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<FamilyMemberBean>>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<List<FamilyMemberBean>> listBaseData) {
                        List<FamilyMemberBean> family = listBaseData.getData();
                        if (family == null) {
                            family = new ArrayList<>();
                        }
                        adapter = new FamilyMemberAdapter(getPageContext(), R.layout.item_family_sign, family);
                        rvFamily.setAdapter(adapter);
                        rvFamily.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        adapter.setOnItemClickListener(new FamilyMemberAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int id) {
                                Intent intent = new Intent(getPageContext(), MyFamilyDetailActivity.class);
                                intent.putExtra("user_id", id);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_family_list, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

}
