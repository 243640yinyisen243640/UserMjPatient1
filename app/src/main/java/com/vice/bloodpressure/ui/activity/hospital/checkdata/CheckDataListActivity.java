package com.vice.bloodpressure.ui.activity.hospital.checkdata;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.CheckDataListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.CheckDataListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * 描述: 检验数据
 * 作者: LYD
 * 创建日期: 2019/9/9 10:46
 */
public class CheckDataListActivity extends BaseHandlerActivity {
    private static final int GET_TIME = 10010;
    @BindView(R.id.lv_check_data)
    ListView lvCheckData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ofid = getIntent().getStringExtra("ofid");
        if (TextUtils.isEmpty(ofid)) {
            //检验数据
            setTitle("检验数据");
            getTimeList("");
        } else {
            //体检 预约模块
            String name = getIntent().getStringExtra("name");
            setTitle(name + "的报告");
            getTimeList(ofid);
        }
    }


    /**
     * 获取时间列表
     *
     * @param ofid
     */
    private void getTimeList(String ofid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ofid", ofid);
        XyUrl.okPost(XyUrl.GET_EXAMINE_TIME, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                CheckDataListBean scope = JSONObject.parseObject(value, CheckDataListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_TIME;
                message.obj = scope;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 设置列表数据
     *
     * @param list
     * @param ofid
     */
    private void setLvData(ArrayList<String> list, String ofid) {
        CheckDataListAdapter adapter = new CheckDataListAdapter(getPageContext(), R.layout.item_check_data_list, list, ofid);
        lvCheckData.setAdapter(adapter);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_check_data_list, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_TIME:
                CheckDataListBean data = (CheckDataListBean) msg.obj;
                //最新更新时间：2019-08-29
                String bloodpressure = data.getBloodpressure();
                String bmi = data.getBmi();
                String cdu = data.getCdu();
                String ecg = data.getEcg();
                String ct = data.getCt();
                String routine_blood = data.getRoutine_blood();
                String tr = data.getTr();
                String routine_urinary = data.getRoutine_urinary();
                String biochemical_analysis = data.getBiochemical_analysis();
                //医生建议
                //电解质
                String docadvice = data.getDocadvice();
                String electrolyte = data.getElectrolyte();
                //免疫荧光
                String immuneinstrument = data.getImmuneinstrument();
                //凝血
                String bloodinstrument = data.getBloodinstrument();


                ArrayList<String> list = new ArrayList<>();
                String ofid = getIntent().getStringExtra("ofid");
                if (TextUtils.isEmpty(ofid)) {
                    //检验数据
                    list.add(bloodpressure);
                    list.add(bmi);
                } else {
                    //体检 预约报告
                    list.add(docadvice);
                    list.add(electrolyte);
                }
                list.add(cdu);
                list.add(ecg);
                list.add(ct);
                list.add(routine_blood);
                list.add(tr);
                list.add(routine_urinary);
                list.add(biochemical_analysis);
                if (TextUtils.isEmpty(ofid)) {
                    //检验数据
                } else {
                    //体检 预约报告
                    list.add(9, immuneinstrument);
                    list.add(10, bloodinstrument);
                }

                setLvData(list, ofid);
                break;
        }
    }
}
