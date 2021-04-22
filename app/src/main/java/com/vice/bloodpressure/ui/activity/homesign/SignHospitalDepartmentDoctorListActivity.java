package com.vice.bloodpressure.ui.activity.homesign;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FamilySignDoctorAdapter;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.bean.DoctorEntity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.List;

import butterknife.BindView;

/*
 * 包名:     com.vice.bloodpressure.ui.activity.homesign
 * 类名:     SignHospitalDepartmentDoctorListActivity
 * 描述:     家签医生列表
 * 作者:     ZWK
 * 创建日期: 2020/1/8 13:21
 */
public class SignHospitalDepartmentDoctorListActivity extends BaseActivity {

    @BindView(R.id.lv_dep)
    ListView lvDoc;
    private int depId;
    private int hosId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("depname"));
        depId = getIntent().getIntExtra("depid", -1);
        hosId = getIntent().getIntExtra("hosid", -1);
        initView();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_sign_hospital_department_list, contentLayout, false);
    }

    private void initView() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getDoctors(token, hosId, depId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<DoctorEntity>>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<List<DoctorEntity>> listBaseData) {
                        List<DoctorEntity> doctors = listBaseData.getData();
                        lvDoc.setAdapter(new FamilySignDoctorAdapter(SignHospitalDepartmentDoctorListActivity.this, doctors));
                        lvDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                DoctorEntity doctor = doctors.get((int) id);
                                Intent intent = new Intent(SignHospitalDepartmentDoctorListActivity.this, HomeDoctorDetailActivity.class);
                                intent.putExtra("doctor_id", doctor.getUserid());
                                intent.putExtra("doctor_name", doctor.getDocname());
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

}
