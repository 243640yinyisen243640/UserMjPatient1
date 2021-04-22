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
import com.vice.bloodpressure.adapter.FamilySignDepartmentAdapter;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.bean.DepartmentEntity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.List;

import butterknife.BindView;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: SignHospitalDepartmentListActivity
 * @Description: 签约社区医院科室列表
 * @Author: zwk
 * @CreateDate: 2019/12/30 15:52
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/30 15:52
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class SignHospitalDepartmentListActivity extends BaseActivity {

    @BindView(R.id.lv_dep)
    ListView lvDep;
    private int hosId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("hosname"));
        hosId = getIntent().getIntExtra("hosid", -1);
        initView();
    }

    private void initView() {

        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getDepartments(token, hosId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<List<DepartmentEntity>>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<List<DepartmentEntity>> listBaseData) {
                        List<DepartmentEntity> departments = listBaseData.getData();
                        lvDep.setAdapter(new FamilySignDepartmentAdapter(SignHospitalDepartmentListActivity.this, departments));
                        lvDep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(SignHospitalDepartmentListActivity.this, SignHospitalDepartmentDoctorListActivity.class);
                                DepartmentEntity department = departments.get((int) id);
                                intent.putExtra("depname", department.getDepname());
                                intent.putExtra("depid", department.getDepid());
                                intent.putExtra("hosid", hosId);
                                startActivity(intent);
                            }
                        });
                    }
                });

    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_sign_hospital_department_list, contentLayout, false);
    }


}
