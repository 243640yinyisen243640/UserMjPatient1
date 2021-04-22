package com.vice.bloodpressure.ui.fragment.healthydiet;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.DietPlanAddSuccessBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanQuestionActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.DietPlanResultActivity;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 健康饮食之饮食方案
 * 作者: LYD
 * 创建日期: 2019/12/17 10:03
 */
public class HealthyDietOneFragment extends BaseFragment {
    private static final int GET_DATA = 10086;
    private static final int GET_DATA_ERROR = 10087;
    @BindView(R.id.tv_next)
    ColorTextView tvNext;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_healthy_diet_one;
    }

    @Override
    protected void init(View rootView) {

    }


    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        getIsHaveReport();
    }


    /**
     * 获取是否有报告
     */
    private void getIsHaveReport() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.DIET_LAST_DIET_PLAN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                DietPlanAddSuccessBean data = JSONObject.parseObject(value, DietPlanAddSuccessBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_DATA_ERROR);
            }
        });


    }


    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            //获取到最新的Id,跳转详情
            case GET_DATA:
                DietPlanAddSuccessBean data = (DietPlanAddSuccessBean) msg.obj;
                int id = data.getId();
                intent = new Intent(getPageContext(), DietPlanResultActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case GET_DATA_ERROR:
                intent = new Intent(getPageContext(), DietPlanQuestionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
