package com.vice.bloodpressure.ui.activity.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.HospitalLeftAdapter;
import com.vice.bloodpressure.adapter.HospitalRightAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.HospitalDetailsBean;
import com.vice.bloodpressure.bean.HospitalLeftBean;
import com.vice.bloodpressure.bean.HospitalRightBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 描述: 科室选择
 * 作者: LYD
 * 创建日期: 2019/3/19 19:37
 */

public class HospitalDetailsActivity extends BaseHandlerActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int GET_DATA = 0x996;
    private static final int GET_LEFT_DATA = 0x997;
    private static final int GET_RIGHT_DATA = 0x998;
    private static final int GET_NO_DATA_RIGHT = 10010;
    private static final int GET_NO_DATA_LEFT = 10011;
    private ImageView imgHospital;
    private TextView tvHospitalName;
    private TextView tvHospitalType;
    private TextView tvHospitalAddress;
    private ListView lvLeft;
    private ListView lvRight;
    private HospitalLeftAdapter leftAdapter;
    private HospitalDetailsBean hospitalDetails;
    private LoginBean user;
    private List<HospitalLeftBean> leftList;//血压数据
    private List<HospitalRightBean> rightList;//血压数据

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                hospitalDetails = (HospitalDetailsBean) msg.obj;
                Glide.with(com.blankj.utilcode.util.Utils.getApp())
                        .load(hospitalDetails.getImgurl())
                        .placeholder(R.drawable.default_hospital)
                        .error(R.drawable.default_hospital)
                        .into(imgHospital);
                tvHospitalAddress.setText("地址:" + hospitalDetails.getAddress());
                tvHospitalName.setText(hospitalDetails.getHospitalname());
                tvHospitalType.setText("【" + hospitalDetails.getLevel() + "级医院】");
                break;
            case GET_LEFT_DATA:
                leftList = (List<HospitalLeftBean>) msg.obj;
                leftAdapter = new HospitalLeftAdapter(HospitalDetailsActivity.this, leftList);
                lvLeft.setAdapter(leftAdapter);
                //获取
                if (leftList != null && leftList.size() > 0) {
                    getRightData(leftList.get(0).getDepid());
                }
                break;
            case GET_RIGHT_DATA:
                rightList = (List<HospitalRightBean>) msg.obj;
                HospitalRightAdapter rightAdapter = new HospitalRightAdapter(HospitalDetailsActivity.this, R.layout.item_hospital_lv_right, rightList);
                lvRight.setAdapter(rightAdapter);
                break;
            case GET_NO_DATA_LEFT:
                String errorMsgLeft = (String) msg.obj;
                lvLeft.setAdapter(null);
                ToastUtils.showShort(errorMsgLeft);
                break;
            case GET_NO_DATA_RIGHT:
                String errorMsgRight = (String) msg.obj;
                lvRight.setAdapter(null);
                ToastUtils.showShort(errorMsgRight);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.depid_choose));
        String hid = getIntent().getStringExtra("hid");
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        getData(hid);
        initView();
        //获取科室列表
        getLeftData(hid);
    }

    private void getRightData(int depid) {
        HashMap map = new HashMap<>();
        map.put("acccess_token", user.getToken());
        map.put("depid", depid);//
        map.put("page", 1);
        XyUrl.okPost(XyUrl.RIGHT_SDEPS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                rightList = JSONObject.parseArray(value, HospitalRightBean.class);
                Message message = Message.obtain();
                message.what = GET_RIGHT_DATA;
                message.obj = rightList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                if (ConstantParam.NO_DATA == errorCode) {
                    Message message = Message.obtain();
                    message.what = GET_NO_DATA_RIGHT;
                    message.obj = errorMsg;
                    sendHandlerMessage(message);
                }
            }
        });
    }

    private void getLeftData(String hid) {
        HashMap map = new HashMap<>();
        map.put("acccess_token", user.getToken());
        map.put("id", hid);
        XyUrl.okPost(XyUrl.LEFT_SDEPS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                leftList = JSONObject.parseArray(value, HospitalLeftBean.class);
                Message message = Message.obtain();
                message.what = GET_LEFT_DATA;
                message.obj = leftList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                if (ConstantParam.NO_DATA == errorCode) {
                    Message message = Message.obtain();
                    message.what = GET_NO_DATA_LEFT;
                    message.obj = errorMsg;
                    sendHandlerMessage(message);
                }

            }
        });
    }

    /**
     * 单个医院简讯和详情
     *
     * @param hid
     */
    private void getData(String hid) {
        Map<String, Object> map = new HashMap<>();
        map.put("acccess_token", user.getToken());
        map.put("id", hid);
        XyUrl.okPost(XyUrl.HOSPITAL_DETAILS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                hospitalDetails = JSONObject.parseObject(value, HospitalDetailsBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = hospitalDetails;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hospital_details, contentLayout, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_top://医院详情
                Intent intent = new Intent(this, HospitalContentsActivity.class);
                intent.putExtra("hsp", hospitalDetails);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        LinearLayout llTop = findViewById(R.id.ll_top);
        imgHospital = findViewById(R.id.iv_hospital);
        tvHospitalName = findViewById(R.id.tv_hospital_name);
        tvHospitalType = findViewById(R.id.tv_hospital_type);
        tvHospitalAddress = findViewById(R.id.tv_hospital_address);
        lvLeft = findViewById(R.id.lv_left);
        lvRight = findViewById(R.id.lv_right);
        llTop.setOnClickListener(this);
        lvLeft.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取医生列表
        getRightData(leftList.get(position).getDepid());
        //这句是通知adapter改变选中的position
        leftAdapter.clearSelection(position);
        //关键是这一句，激情了，它可以让listview改动过的数据重新加载一遍，以达到你想要的效果
        leftAdapter.notifyDataSetChanged();
    }


}
