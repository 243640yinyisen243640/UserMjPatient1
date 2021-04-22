package com.vice.bloodpressure.ui.activity.setting;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SugarControlTargetAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.ResetTargetBean;
import com.vice.bloodpressure.bean.ScopeBean;
import com.vice.bloodpressure.bean.SugarControlBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.OkHttpInstance;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class SugarControlTargetSettingActivity extends BaseHandlerActivity implements View.OnClickListener {
    private static final String TAG = "SugarControlTargetSettingActivity";
    private static final int GET_SCOPE = 10010;
    private static final int RESET_TARGET = 10086;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_reset)
    ColorTextView tvReset;

    private SugarControlTargetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血糖控制目标设置");
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(this);
        getControlTarget();
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_sugar_control_target_setting, contentLayout, false);
        return view;
    }

    private void getControlTarget() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_USERDATE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ScopeBean scope = JSONObject.parseObject(value, ScopeBean.class);
                Message message = getHandlerMessage();
                message.what = GET_SCOPE;
                message.obj = scope;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    private void setLv(ResetTargetBean target) {
        List<SugarControlBean> list = new ArrayList<>();
        SugarControlBean bean0 = getBean(target.getEmpstomach());
        SugarControlBean bean1 = getBean(target.getAftbreakfast());
        SugarControlBean bean2 = getBean(target.getBeflunch());
        SugarControlBean bean3 = getBean(target.getAftlunch());
        SugarControlBean bean4 = getBean(target.getBefdinner());
        SugarControlBean bean5 = getBean(target.getAftdinner());
        SugarControlBean bean6 = getBean(target.getBefsleep());
        SugarControlBean bean7 = getBean(target.getInmorning());
        list.add(bean0);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        adapter = new SugarControlTargetAdapter(getPageContext(), list);
        lvList.setAdapter(adapter);
    }

    private SugarControlBean getBean(List<Double> list) {
        String min = list.get(0) + "";
        String max = list.get(1) + "";
        SugarControlBean bean = new SugarControlBean(min, max);
        return bean;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more:
                toSubmit();
                break;
        }
    }

    private void toSubmit() {
        HashMap<Integer, String> saveMapMin = adapter.saveMapMin;
        HashMap<Integer, String> saveMapMax = adapter.saveMapMax;
        ArrayList<String> listMinAndMax = new ArrayList<>();
        for (int i = 0; i < saveMapMin.size(); i++) {
            String min = saveMapMin.get(i);
            listMinAndMax.add(min);
            String max = saveMapMax.get(i);
            listMinAndMax.add(max);
        }
        //为空判断
        if (16 == listMinAndMax.size()) {
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.data_sugar_time);
            String min0 = listMinAndMax.get(0);
            String min1 = listMinAndMax.get(1);
            if (!showToast(min0, min1, stringArray[0])) {
                return;
            }
            String min2 = listMinAndMax.get(2);
            String min3 = listMinAndMax.get(3);
            if (!showToast(min2, min3, stringArray[1])) {
                return;
            }
            String min4 = listMinAndMax.get(4);
            String min5 = listMinAndMax.get(5);
            if (!showToast(min4, min5, stringArray[2])) {
                return;
            }
            String min6 = listMinAndMax.get(6);
            String min7 = listMinAndMax.get(7);
            if (!showToast(min6, min7, stringArray[3])) {
                return;
            }
            String min8 = listMinAndMax.get(8);
            String min9 = listMinAndMax.get(9);
            if (!showToast(min8, min9, stringArray[4])) {
                return;
            }
            String min10 = listMinAndMax.get(10);
            String min11 = listMinAndMax.get(11);
            if (!showToast(min10, min11, stringArray[5])) {
                return;
            }
            String min12 = listMinAndMax.get(12);
            String min13 = listMinAndMax.get(13);
            if (!showToast(min12, min13, stringArray[6])) {
                return;
            }
            String min14 = listMinAndMax.get(14);
            String min15 = listMinAndMax.get(14);
            if (!showToast(min14, min15, stringArray[7])) {
                return;
            }
        }
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("access_token", user.getToken());
        builder.addFormDataPart("empstomach[]", listMinAndMax.get(0));
        builder.addFormDataPart("empstomach[]", listMinAndMax.get(1));

        builder.addFormDataPart("aftbreakfast[]", listMinAndMax.get(2));
        builder.addFormDataPart("aftbreakfast[]", listMinAndMax.get(3));

        builder.addFormDataPart("beflunch[]", listMinAndMax.get(4));
        builder.addFormDataPart("beflunch[]", listMinAndMax.get(5));

        builder.addFormDataPart("aftlunch[]", listMinAndMax.get(6));
        builder.addFormDataPart("aftlunch[]", listMinAndMax.get(7));

        builder.addFormDataPart("befdinner[]", listMinAndMax.get(8));
        builder.addFormDataPart("befdinner[]", listMinAndMax.get(9));

        builder.addFormDataPart("aftdinner[]", listMinAndMax.get(10));
        builder.addFormDataPart("aftdinner[]", listMinAndMax.get(11));

        builder.addFormDataPart("befsleep[]", listMinAndMax.get(12));
        builder.addFormDataPart("befsleep[]", listMinAndMax.get(13));

        builder.addFormDataPart("inmorning[]", listMinAndMax.get(14));
        builder.addFormDataPart("inmorning[]", listMinAndMax.get(15));
        //        //判断为空
        //        for (int i = 0; i < listMinAndMax.size(); i++) {
        //            String s = listMinAndMax.get(i);
        //            if (TextUtils.isEmpty(s)) {
        //                ToastUtils.showShort("");
        //            }
        //        }
        Request request = new Request.Builder()
                .url(XyUrl.ADD_TARGET)
                .post(builder.build())
                .build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                LogUtils.e("onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body().string();
                try {
                    org.json.JSONObject object = new org.json.JSONObject(res);
                    String code = object.getString("code");
                    String msg = object.getString("msg");
                    if ("200".equals(code)) {
                        ToastUtils.showShort(msg);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Boolean showToast(String min, String max, String type) {
        if (TextUtils.isEmpty(min)) {
            ToastUtils.showShort("请输入" + type + "最低值");
            return false;
        }
        if (TextUtils.isEmpty(max)) {
            ToastUtils.showShort("请输入" + type + "最高值");
            return false;
        }
        double min0Double = TurnsUtils.getDouble(min, 0);
        double min1Double = TurnsUtils.getDouble(max, 0);
        if (min0Double > min1Double) {
            ToastUtils.showShort(type + "最低值必须得小于最高值哦");
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_reset)
    public void onViewClicked() {
        toReset();
    }

    private void toReset() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.RESET_TARGET, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ResetTargetBean targetBean = JSONObject.parseObject(value, ResetTargetBean.class);
                Message message = getHandlerMessage();
                message.obj = targetBean;
                message.what = RESET_TARGET;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SCOPE:
                ScopeBean data = (ScopeBean) msg.obj;
                ResetTargetBean target = data.getTarget();
                setLv(target);
                break;
            case RESET_TARGET:
                ResetTargetBean targetBean = (ResetTargetBean) msg.obj;
                setLv(targetBean);
                break;
        }
    }


}