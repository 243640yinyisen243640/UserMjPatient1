package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SugarListAdapter;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.SugarListBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.rxhttputils.CustomDataObserver;

import java.util.List;

import butterknife.BindView;

/**
 * 描述: 每个时间点血糖测量详情
 * 作者: LYD
 * 创建日期: 2019/6/8 10:03
 */
public class HealthRecordBloodSugarListActivity extends BaseActivity {
    @BindView(R.id.tv_lv_title)
    TextView tvLvTitle;
    @BindView(R.id.lv_sugar_list)
    ListView lvSugarList;


    /**
     * 获取数据
     *
     * @param userid
     * @param time
     * @param type
     */
    private void getSugarList(String userid, String time, String type) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getSugarList(token, userid, time, type)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CustomDataObserver<List<SugarListBean>>() {

                    @Override
                    protected void onSuccess(List<SugarListBean> data) {
                        if (data != null && data.size() > 0) {
                            lvSugarList.setAdapter(new SugarListAdapter(getPageContext(), R.layout.item_sugar_list, data));
                        }
                    }
                });
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_health_record_blood_sugar_list, contentLayout, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userid = getIntent().getStringExtra("userid");
        String time = getIntent().getStringExtra("time");
        String type = getIntent().getStringExtra("type");
        setTitle(time);
        String sendType = "";
        switch (type) {
            case "凌晨":
                sendType = "8";
                break;
            case "早餐空腹":
                sendType = "1";
                break;
            case "早餐后":
                sendType = "2";
                break;
            case "午餐前":
                sendType = "3";
                break;
            case "午餐后":
                sendType = "4";
                break;
            case "晚餐前":
                sendType = "5";
                break;
            case "晚餐后":
                sendType = "6";
                break;
            case "睡前":
                sendType = "7";
                break;
        }
        tvLvTitle.setText(type);//时间点 凌晨8 后边1到7
        getSugarList(userid, time, sendType);
    }
}
