package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;

import butterknife.OnClick;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: FamilyDoctorNotBindActivity
 * @Description: 家庭医生未绑定
 * @Author: zwk
 * @CreateDate: 2019/12/30 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/30 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class FamilyDoctorNotBindActivity extends BaseHandlerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("家庭医生");
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_family_doctor_not_bind, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.tv_bind_doctor, R.id.tv_my_qr_code, R.id.tv_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bind_doctor:
                startActivity(new Intent(getPageContext(), SignHospitalsListActivity.class));
                break;
            case R.id.tv_my_qr_code:
                //startActivity(new Intent(getPageContext(), MyQRCodeActivity.class));
                break;
            case R.id.tv_scan:
                //                Intent intent = new Intent(getPageContext(), ScanActivity.class);
                //                intent.putExtra("type", 0);
                //                startActivity(intent);
                break;
        }
    }
}
