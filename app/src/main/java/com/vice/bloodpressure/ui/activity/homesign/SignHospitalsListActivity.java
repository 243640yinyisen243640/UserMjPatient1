package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FamilySignHospitalAdapter;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.bean.HospitalEntity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: SignHospitalsListActivity
 * @Description: 签约社区医院列表
 * @Author: zwk
 * @CreateDate: 2019/12/30 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/30 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class SignHospitalsListActivity extends BaseActivity {

    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.lv_hos)
    ListView lvHos;
    @BindView(R.id.srl_hos)
    SmartRefreshLayout srlHos;

    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("签约社区医院列表");
        init();
        initRefresh();
        initSearch();
    }


    private void initRefresh() {
        srlHos.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlHos.finishRefresh(2000);
            }
        });

        srlHos.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlHos.finishLoadMore(2000);
            }
        });
    }

    private void initSearch() {
        EditTextUtils.setOnEditorActionListener(etKey, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                key = etKey.getText().toString().trim();
                if (TextUtils.isEmpty(key)) {
                    return;
                }
                init();
            }
        });
    }

    private void init() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getHospitals(token, key)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<HospitalEntity>>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<List<HospitalEntity>> listBaseData) {
                        List<HospitalEntity> hospitals = listBaseData.getData();
                        lvHos.setAdapter(new FamilySignHospitalAdapter(SignHospitalsListActivity.this, hospitals));
                        lvHos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(SignHospitalsListActivity.this, SignHospitalDepartmentListActivity.class);
                                intent.putExtra("hosid", hospitals.get((int) id).getId());
                                intent.putExtra("hosname", hospitals.get((int) id).getHospitalname());
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_sign_hospitals_list, contentLayout, false);
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        key = etKey.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            return;
        }
        init();
    }


}
