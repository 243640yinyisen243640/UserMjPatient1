package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MakePolicyAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.GetNewProfessionBean;
import com.vice.bloodpressure.bean.GetNewReportBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.TcmHomePageBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.nondrug.SelectPrescriptionActivity;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 智能决策
 * 作者: LYD
 * 创建日期: 2019/3/29 14:52
 */
public class MakePolicyActivity extends BaseHandlerActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MakePolicyActivity";
    //降压
    private static final int GET_NEW_REPORT = 10086;
    private static final int GET_NEW_REPORT_ERROR = 10087;
    //降糖
    private static final int GET_NEW_PROFESSION = 10010;
    private static final int GET_NEW_PROFESSION_ERROR = 10011;
    //中医体质
    private static final int GET_NEW_TCM = 10000;
    private static final int GET_NEW_TCM_ERROR = 10001;
    //减重处方
    private static final int GET_NEW_WEIGHT = 10012;
    private static final int GET_NEW_WEIGHT_ERROR = 10013;
    @BindView(R.id.lv_make_policy)
    ListView lvMakePolicy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能决策");
        setMakeAdapter();
    }


    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_make_policy, contentLayout, false);
        return layout;
    }

    /**
     * 获取最新降糖报告
     */
    private void getNewProfession() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_NEW_PROFESSION, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                GetNewProfessionBean data = JSONObject.parseObject(value, GetNewProfessionBean.class);
                Message msg = getHandlerMessage();
                msg.obj = data;
                msg.what = GET_NEW_PROFESSION;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (ConstantParam.NO_DATA == error) {
                    Message msg = getHandlerMessage();
                    msg.what = GET_NEW_PROFESSION_ERROR;
                    sendHandlerMessage(msg);
                }
            }
        });
    }

    /**
     * 获取最近降压报告
     */
    private void getNewReport() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_NEW_REPORT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                GetNewReportBean data = JSONObject.parseObject(value, GetNewReportBean.class);
                Message msg = getHandlerMessage();
                msg.obj = data;
                msg.what = GET_NEW_REPORT;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (ConstantParam.NO_DATA == error) {
                    Message msg = getHandlerMessage();
                    msg.what = GET_NEW_REPORT_ERROR;
                    sendHandlerMessage(msg);
                }
            }
        });
    }


    /**
     * 获取
     */
    private void getNewTcm() {
        HashMap map = new HashMap<>();
        XyUrl.okPostGetErrorData(XyUrl.TCM_HOME_PAGE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                TcmHomePageBean data = JSONObject.parseObject(value, TcmHomePageBean.class);
                Message msg = getHandlerMessage();
                msg.obj = data;
                msg.what = GET_NEW_TCM;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String value) {
                if (ConstantParam.NO_DATA == error) {
                    TcmHomePageBean data = JSONObject.parseObject(value, TcmHomePageBean.class);
                    Message msg = getHandlerMessage();
                    msg.obj = data;
                    msg.what = GET_NEW_TCM_ERROR;
                    sendHandlerMessage(msg);
                }
            }
        });

    }


    /**
     * 获取
     */
    private void getLoseWeight() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_NEW_WEIGHT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                GetNewProfessionBean data = JSONObject.parseObject(value, GetNewProfessionBean.class);
                Message msg = getHandlerMessage();
                msg.obj = data;
                msg.what = GET_NEW_WEIGHT;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (ConstantParam.NO_DATA == error) {
                    Message msg = getHandlerMessage();
                    msg.what = GET_NEW_WEIGHT_ERROR;
                    sendHandlerMessage(msg);
                }
            }
        });

    }


    /**
     * 个人中心设置Adapter
     */
    private void setMakeAdapter() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        MakePolicyAdapter makePolicyAdapter = new MakePolicyAdapter(
                getPageContext(), R.layout.item_make_policy, list);
        lvMakePolicy.setAdapter(makePolicyAdapter);
        lvMakePolicy.setOnItemClickListener(this);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            //获取降糖
            case GET_NEW_PROFESSION:
                GetNewProfessionBean getNewProfessionBean = (GetNewProfessionBean) msg.obj;
                String url = getNewProfessionBean.getUrl();
                if (TextUtils.isEmpty(url)) {
                    //没有创建血糖的Url
                    intent = new Intent(Utils.getApp(), SubmitApplyActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                } else {
                    //有创建血糖的Url
                    intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                    intent.putExtra("title", "处方");
                    intent.putExtra("url", url);
                    intent.putExtra("type", "homeProfession");
                    startActivity(intent);
                }
                break;
            //没有降糖方案
            case GET_NEW_PROFESSION_ERROR:
                intent = new Intent(Utils.getApp(), SelectPrescriptionActivity.class);
                startActivity(intent);
                break;
            //获取降压
            case GET_NEW_REPORT:
                GetNewReportBean data = (GetNewReportBean) msg.obj;
                int pstatus = data.getPstatus();
                String paid = data.getPaid() + "";
                switch (pstatus) {
                    //方案状态1同意 3待查看
                    case 1:
                        String treatment = data.getTreatment();
                        if ("1".equals(treatment)) {
                            //治疗方案1: 生活方式干预
                            String homePressureUrl = data.getUrl();
                            intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                            intent.putExtra("title", "血压管理方案");
                            intent.putExtra("url", homePressureUrl);
                            intent.putExtra("type", "homePressure");
                        } else {
                            //治疗方案2: 及时就诊
                            intent = new Intent(getPageContext(), HbpDetailActivity.class);
                            intent.putExtra("id", paid);
                            intent.putExtra("type", "0");
                        }
                        startActivity(intent);
                        break;
                    //提交申请
                    case 3:
                        intent = new Intent(getPageContext(), SubmitApplyActivity.class);
                        intent.putExtra("type", "0");
                        startActivity(intent);
                        break;
                }
                break;
            //没有降压方案
            case GET_NEW_REPORT_ERROR:
                intent = new Intent(getPageContext(), HbpSubmitMainActivity.class);
                startActivity(intent);
                break;
            //中医体质
            case GET_NEW_TCM:
                TcmHomePageBean tcmHaveBean = (TcmHomePageBean) msg.obj;
                SPStaticUtils.put("tcmCreateUrl", tcmHaveBean.getCreatUrl());
                String tcmShowUrl = tcmHaveBean.getUrl();
                intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "体质报告");
                intent.putExtra("url", tcmShowUrl);
                intent.putExtra("type", "homeTcm");
                startActivity(intent);
                break;
            case GET_NEW_TCM_ERROR:
                TcmHomePageBean tcmNotHaveBean = (TcmHomePageBean) msg.obj;
                String tcmCreateUrl = tcmNotHaveBean.getUrl();
                intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "体质检测");
                intent.putExtra("createUrl", tcmCreateUrl);
                startActivity(intent);
                break;
            //减重
            case GET_NEW_WEIGHT:
                GetNewProfessionBean loseWeightBean = (GetNewProfessionBean) msg.obj;
                String weightUrl = loseWeightBean.getUrl();
                intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "减重处方");
                intent.putExtra("url", weightUrl);
                intent.putExtra("type", "homeWeight");
                startActivity(intent);
                break;
            case GET_NEW_WEIGHT_ERROR:
                intent = new Intent(getPageContext(), SelectPrescriptionActivity.class);
                intent.putExtra("type", "weight");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                getNewProfession();
                break;
            case 1:
                getNewReport();
                break;
            case 2:
                getNewTcm();
                break;
            case 3:
                getLoseWeight();
                break;

        }
    }
}
