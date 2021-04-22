package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ConvertUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.PrivacyBean;
import com.vice.bloodpressure.bean.SignDoctorInfoBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitListActivity;
import com.vice.bloodpressure.ui.activity.hospital.checkdata.CheckDataListActivity;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: FamilyDoctorBindActivity
 * @Description: 家庭医生已绑定
 * @Author: zwk
 * @CreateDate: 2019/12/30 14:17
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/12/30 14:17
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class FamilyDoctorBindActivity extends BaseHandlerActivity {
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_seven)
    TextView tvSeven;
    @BindView(R.id.tv_eight)
    TextView tvEight;

    private String docname;
    private int docid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("家庭医生");
        getDoctorInfoDetail();
        getTvSave().setText("隐私设置");
        getTvSave().setPadding(ConvertUtils.dp2px(8), 0,
                ConvertUtils.dp2px(8), 0);
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getApplicationContext(), SharedPreferencesUtils.USER_INFO);
                String token = loginBean.getToken();
                int uid = Integer.parseInt(loginBean.getUserid());
                RxHttpUtils.createApi(Service.class)
                        .getPrivacy(token, uid)
                        .compose(Transformer.switchSchedulers())
                        .subscribe(new CommonObserver<BaseData<PrivacyBean>>() {
                            @Override
                            protected void onError(String errorMsg) {

                            }

                            @Override
                            protected void onSuccess(BaseData<PrivacyBean> integerBaseData) {
                                int x = integerBaseData.getData().getIs_allow();
                                Intent intent = new Intent(getPageContext(), PrivacySettingActivity.class);
                                intent.putExtra("is_allow", x);
                                startActivity(intent);
                            }
                        });
            }
        });
    }


    /**
     * 获取医生详情
     */
    private void getDoctorInfoDetail() {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        int doctor_id = Integer.valueOf(loginBean.getSignid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", loginBean.getToken());
        map.put("docid", doctor_id);
        XyUrl.okPost(XyUrl.SIGN_DOCTOR_INFO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SignDoctorInfoBean data = JSONObject.parseObject(value, SignDoctorInfoBean.class);
                docname = data.getDocname();
                docid = data.getDocid();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_family_doctor_bind, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five, R.id.tv_six, R.id.tv_seven, R.id.tv_eight})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_one:
                startActivity(new Intent(this, HospitalizationAppointmentListActivity.class));
                break;
            case R.id.tv_two:
                intent = new Intent(getPageContext(), FollowUpVisitListActivity.class);
                intent.putExtra("is_family", true);
                startActivity(intent);
                break;
            case R.id.tv_three:
                intent = new Intent(getPageContext(), CheckDataListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_four:
                startActivity(new Intent(this, FamilyListActivity.class));
                break;
            case R.id.tv_five:
                intent = new Intent(FamilyDoctorBindActivity.this, DoctorAdviceActivity.class);
                intent.putExtra("docname", docname);
                intent.putExtra("docid", docid);
                startActivity(intent);
                break;
            case R.id.tv_six:
                RongIM.getInstance().startPrivateChat(this, docid + "", docname);
                break;
            case R.id.tv_seven:
                //startActivity(new Intent(this, ServicePackActivity.class));
                break;
            case R.id.tv_eight:
                startActivity(new Intent(this, SignProtocolActivity.class));
                break;
        }
    }
}
