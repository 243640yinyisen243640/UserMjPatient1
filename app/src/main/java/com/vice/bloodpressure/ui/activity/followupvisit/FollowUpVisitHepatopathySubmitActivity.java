package com.vice.bloodpressure.ui.activity.followupvisit;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FollowUpVisitAddBean;
import com.vice.bloodpressure.bean.FollowUpVisitBloodPressureDetailBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.view.popu.FollowUpVisitSavePopup;
import com.wei.android.lib.colorview.view.ColorEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 肝病随访 患者提交 及详情
 * 作者: LYD
 * 创建日期: 2020/1/3 17:30
 */
public class FollowUpVisitHepatopathySubmitActivity extends BaseHandlerActivity {
    private static final int GET_FOLLOW_UP_VISIT_DETAIL = 10086;
    @BindView(R.id.bt_back_new)
    Button btBackNew;
    @BindView(R.id.tv_title_new)
    TextView tvTitleNew;
    @BindView(R.id.tv_more_new)
    TextView tvMoreNew;

    @BindView(R.id.tv_top_add_time)
    TextView tvTopAddTime;
    @BindView(R.id.tv_top_submit_time)
    TextView tvTopSubmitTime;

    @BindView(R.id.et_summary_main_question)
    ColorEditText etSummaryMainQuestion;
    //    @BindView(R.id.et_summary_improve_measure)
    //    ColorEditText etSummaryImproveMeasure;
    @BindView(R.id.et_summary_main_purpose)
    ColorEditText etSummaryMainPurpose;
    @BindView(R.id.ll_summary)
    LinearLayout llSummary;
    @BindView(R.id.et_alt)
    ColorEditText etAlt;
    @BindView(R.id.ll_alt)
    LinearLayout llAlt;
    @BindView(R.id.et_total)
    ColorEditText etTotal;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.et_white)
    ColorEditText etWhite;
    @BindView(R.id.ll_white)
    LinearLayout llWhite;
    @BindView(R.id.et_forward)
    ColorEditText etForward;
    @BindView(R.id.ll_forward)
    LinearLayout llForward;
    @BindView(R.id.et_blood_sugar)
    ColorEditText etBloodSugar;
    @BindView(R.id.ll_blood_sugar)
    LinearLayout llBloodSugar;
    @BindView(R.id.et_blood_clotting)
    ColorEditText etBloodClotting;
    @BindView(R.id.ll_blood_clotting)
    LinearLayout llBloodClotting;
    @BindView(R.id.et_blood_red)
    ColorEditText etBloodRed;
    @BindView(R.id.ll_blood_red)
    LinearLayout llBloodRed;
    @BindView(R.id.et_blood_ammonia)
    ColorEditText etBloodAmmonia;
    @BindView(R.id.ll_blood_ammonia)
    LinearLayout llBloodAmmonia;
    @BindView(R.id.ll_index_text)
    LinearLayout llIndexText;
    @BindView(R.id.et_sas)
    ColorEditText etSas;
    @BindView(R.id.ll_sas)
    LinearLayout llSas;
    @BindView(R.id.et_ds)
    ColorEditText etDs;
    @BindView(R.id.ll_ds)
    LinearLayout llDs;
    @BindView(R.id.et_pe)
    ColorEditText etPe;
    @BindView(R.id.ll_pe)
    LinearLayout llPe;


    //保存
    private FollowUpVisitSavePopup popupBack;
    private FollowUpVisitSavePopup popupSave;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        tvTitleNew.setText("随访管理");
        getFollowUpVisitDetail();
        initPopup();
        KeyboardUtils.fixAndroidBug5497(this);
    }

    private void initPopup() {
        popupBack = new FollowUpVisitSavePopup(getPageContext());
        popupSave = new FollowUpVisitSavePopup(getPageContext());
        TextView tvContentTopBack = popupBack.findViewById(R.id.tv_content_top);
        TextView tvContentBottomBack = popupBack.findViewById(R.id.tv_content_bottom);
        TextView tvLeftBack = popupBack.findViewById(R.id.tv_left);
        TextView tvRightBack = popupBack.findViewById(R.id.tv_right);
        TextView tvContentTopSave = popupSave.findViewById(R.id.tv_content_top);
        TextView tvContentBottomSave = popupSave.findViewById(R.id.tv_content_bottom);
        TextView tvLeftSave = popupSave.findViewById(R.id.tv_left);
        TextView tvRightSave = popupSave.findViewById(R.id.tv_right);
        tvContentTopBack.setText("您还未完成管理问卷填写");
        tvContentBottomBack.setText("是否保存");
        tvLeftBack.setText("不保存");
        tvRightBack.setText("保存草稿");
        tvContentTopSave.setText("您已完成管理问卷填写");
        tvContentBottomSave.setText("是否提交");
        tvLeftSave.setText("保存草稿");
        tvRightSave.setText("完成提交");
        tvLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupBack.dismiss();
                finish();
            }
        });
        tvRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存草稿
                toDoSubmit("3");
            }
        });
        tvLeftSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存草稿
                toDoSubmit("3");
            }
        });
        tvRightSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                toDoSubmit("4");
            }
        });

    }

    private void toDoSubmit(String dataStatus) {
        String id = getIntent().getStringExtra("id");
        //最后需要转换的Bean
        FollowUpVisitAddBean postData = new FollowUpVisitAddBean();
        postData.setId(id);
        //设置数据Bean
        FollowUpVisitAddBean.DataBean dataBean = new FollowUpVisitAddBean.DataBean();
        //设置状态
        dataBean.setStatus(dataStatus);
        //设置新增十一项
        String alt = etAlt.getText().toString().trim();
        String total = etTotal.getText().toString().trim();
        String white = etWhite.getText().toString().trim();
        String forward = etForward.getText().toString().trim();
        String bloodSugar = etBloodSugar.getText().toString().trim();
        String bloodClotting = etBloodClotting.getText().toString().trim();
        String bloodRed = etBloodRed.getText().toString().trim();
        String bloodAmmonia = etBloodAmmonia.getText().toString().trim();

        String sas = etSas.getText().toString().trim();
        String ds = etDs.getText().toString().trim();
        String pe = etPe.getText().toString().trim();
        dataBean.setAlt(alt);
        dataBean.setTotal_bilirubin(total);
        dataBean.setAlbumin(white);
        dataBean.setPrealbumin(forward);

        dataBean.setBlood_sugar(bloodSugar);
        dataBean.setProthrombin(bloodClotting);
        dataBean.setHaemoglobin(bloodRed);
        dataBean.setBlood_ammonia(bloodAmmonia);

        dataBean.setSas(sas);
        dataBean.setDietary_survey(ds);
        dataBean.setPhysical_examination(pe);

        //最后数据提交
        postData.setData(dataBean);
        String jsonResult = JSON.toJSONString(postData);
        XyUrl.okPostJson(XyUrl.FOLLOW_ADD, jsonResult, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                finish();
                EventBusUtils.post(new EventMessage<>(ConstantParam.FOLLOW_UP_VISIT_SUBMIT));
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

    private void getFollowUpVisitDetail() {
        String id = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        XyUrl.okPost(XyUrl.GET_FOLLOW_DETAIL_NEW, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                FollowUpVisitBloodPressureDetailBean bean = JSONObject.parseObject(value, FollowUpVisitBloodPressureDetailBean.class);
                Message message = getHandlerMessage();
                message.what = GET_FOLLOW_UP_VISIT_DETAIL;
                message.obj = bean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_follow_up_visit_hepatopathy_submit, contentLayout, false);
        return view;
    }


    @OnClick({R.id.bt_back_new, R.id.tv_more_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back_new:
                if ("2".equals(status) || "3".equals(status)) {
                    popupBack.showPopupWindow();
                } else {
                    finish();
                }
                break;
            case R.id.tv_more_new:
                if ("2".equals(status) || "3".equals(status)) {
                    popupSave.showPopupWindow();
                }
                break;
        }
    }

    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果返回键按下
            if ("2".equals(status) || "3".equals(status)) {
                popupBack.showPopupWindow();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_FOLLOW_UP_VISIT_DETAIL:
                FollowUpVisitBloodPressureDetailBean data = (FollowUpVisitBloodPressureDetailBean) msg.obj;
                status = data.getStatus() + "";
                setRightText(status);
                setTopData(data);
                setData(data);
                break;
        }
    }

    private void setData(FollowUpVisitBloodPressureDetailBean data) {
        List<String> list = data.getQuestionstr();
        if (list != null && list.size() > 0) {
            //alt
            if (list.contains("1")) {
                llAlt.setVisibility(View.VISIBLE);
                String alt = data.getAlt();
                etAlt.setText(alt);
            }
            //总胆红素
            if (list.contains("5")) {
                llTotal.setVisibility(View.VISIBLE);
                String total_bilirubin = data.getTotal_bilirubin();
                etTotal.setText(total_bilirubin);
            }
            //白蛋白
            if (list.contains("2")) {
                llWhite.setVisibility(View.VISIBLE);
                String albumin1 = data.getAlbumin();
                etWhite.setText(albumin1);
            }
            //前白蛋白
            if (list.contains("6")) {
                llForward.setVisibility(View.VISIBLE);
                String prealbumin = data.getPrealbumin();
                etForward.setText(prealbumin);
            }
            //血糖
            if (list.contains("3")) {
                llBloodSugar.setVisibility(View.VISIBLE);
                String blood_sugar = data.getBlood_sugar();
                etBloodSugar.setText(blood_sugar);
            }
            //凝血酶原活力度
            if (list.contains("7")) {
                llBloodClotting.setVisibility(View.VISIBLE);
                String prothrombin = data.getProthrombin();
                etBloodClotting.setText(prothrombin);
            }
            //血红蛋白
            if (list.contains("4")) {
                llBloodRed.setVisibility(View.VISIBLE);
                String haemoglobin = data.getHaemoglobin();
                etBloodRed.setText(haemoglobin);
            }
            //血氨
            if (list.contains("8")) {
                llBloodAmmonia.setVisibility(View.VISIBLE);
                String blood_ammonia = data.getBlood_ammonia();
                etBloodAmmonia.setText(blood_ammonia);
            }
            if (list.contains("1") || list.contains("2") || list.contains("3") || list.contains("4") ||
                    list.contains("5") || list.contains("6") || list.contains("7") || list.contains("8")) {
                llIndexText.setVisibility(View.VISIBLE);
            }
            if (list.contains("9")) {
                llSas.setVisibility(View.VISIBLE);
                String sas = data.getSas();
                etSas.setText(sas);
            }
            if (list.contains("10")) {
                llDs.setVisibility(View.VISIBLE);
                String dietary_survey = data.getDietary_survey();
                etDs.setText(dietary_survey);
            }
            if (list.contains("11")) {
                llPe.setVisibility(View.VISIBLE);
                String physical_examination = data.getPhysical_examination();
                etPe.setText(physical_examination);
            }
        }
        int status = data.getStatus();
        if (5 == status) {
            llSummary.setVisibility(View.VISIBLE);
            String paquestion = data.getPaquestion();
            String measures = data.getMeasures();
            String target = data.getTarget();
            etSummaryMainQuestion.setText(paquestion+measures);
            //etSummaryImproveMeasure.setText(measures);
            etSummaryMainPurpose.setText(target);
        } else {
            llSummary.setVisibility(View.GONE);
        }
    }


    /**
     * 右上角
     *
     * @param status
     */
    private void setRightText(String status) {
        if ("2".equals(status) || "3".equals(status)) {
            tvMoreNew.setText("保存");
            tvMoreNew.setVisibility(View.VISIBLE);
        } else {
            tvMoreNew.setVisibility(View.GONE);
        }
    }

    /**
     * 设置头部信息时间
     *
     * @param data
     */
    private void setTopData(FollowUpVisitBloodPressureDetailBean data) {
        String etime = data.getEtime();
        String stime = data.getStime();
        tvTopAddTime.setText(String.format(getString(R.string.please_input_one), stime, etime));
        tvTopSubmitTime.setText(String.format(getString(R.string.please_input_two), etime));
    }


}
