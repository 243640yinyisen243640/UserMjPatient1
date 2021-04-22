package com.vice.bloodpressure.ui.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.PatientOfTreatListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.PatientOfTreatListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 描述: 就诊人列表(承担三种状态,普通列表,选择,删除)
 * 作者: LYD
 * 创建日期: 2019/10/25 14:45
 */
public class PatientOfTreatListActivity extends BaseHandlerEventBusActivity implements View.OnClickListener {
    private static final int DEL_PATIENT_SUCCESS = 10010;
    private static final int GET_PATIENT_OF_TREAT_LIST = 10086;
    private static final String TAG = "PatientOfTreatListActivity";
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.tv_add_del_sure)
    ColorTextView tvAddDelSure;
    //来自首页还是预约信息页面
    private String from;
    //列表数据
    private PatientOfTreatListAdapter adapter;
    private List<PatientOfTreatListBean.PatientsBean> list = new ArrayList<>();
    private int isHaveDefault;
    private int posi = -1;
    private int clickCount = 0;
    //列表数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHaveDefault = getIntent().getIntExtra("isHaveDefault", 0);
        from = getIntent().getStringExtra("from");
        list = (List<PatientOfTreatListBean.PatientsBean>) getIntent().getSerializableExtra("patientList");
        setTitleBar();
        if ("main".equals(from)) {
            setLvData(0);
        } else {
            setLvData(1);
        }
    }

    /**
     * 设置标题栏
     */
    private void setTitleBar() {
        setTitle("就诊人列表");
        if ("main".equals(from)) {
            getLlMore().removeAllViews();
            TextView tvEdit = new TextView(getPageContext());
            tvEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.patient_of_treat_add_or_edit_more, 0);
            getLlMore().addView(tvEdit);
            tvAddDelSure.setText("添加就诊人");
        } else {
            getTvSave().setText("添加");
            tvAddDelSure.setText("确认");
        }
        getLlMore().setOnClickListener(this);
    }


    /**
     * 设置数据
     *
     * @param type 0:没有选中效果  1:有选中效果
     */
    private void setLvData(int type) {
        if (list != null && list.size() > 0) {
            adapter = new PatientOfTreatListAdapter(getPageContext(), R.layout.item_patient_of_treat, list, type);
            lvList.setAdapter(adapter);
            llEmpty.setVisibility(View.GONE);
            lvList.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.VISIBLE);
            lvList.setVisibility(View.GONE);
        }
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_patient_of_treat_list, contentLayout, false);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_more:
                clickCount++;
                setLlMoreClick();
                break;
        }
    }


    @OnClick(R.id.tv_add_del_sure)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_del_sure:
                String text = tvAddDelSure.getText().toString().trim();
                switch (text) {
                    case "添加就诊人":
                        //判断数据
                        checkIsCanAdd();
                        break;
                    case "删除就诊人":
                        if (-1 == posi) {
                            ToastUtils.showShort("请先选择要删除的就诊人");
                        } else {
                            if (list != null && list.size() > 0) {
                                showDelPatientOfTreat();
                            } else {
                                ToastUtils.showShort("请先添加就诊人");
                            }
                        }
                        break;
                    //直接将数据,返回到上一页面
                    case "确认":
                        if (-1 == posi) {
                            ToastUtils.showShort("请先选择要添加的就诊人");
                        } else {
                            confirmSelectThePatientOfTreat();
                        }
                        break;
                }
                break;
        }
    }

    /**
     * 校验是否可以添加
     */
    private void checkIsCanAdd() {
        if (list != null && 10 == list.size()) {
            ToastUtils.showShort("最多可添加十个就诊人！");
        } else {
            addOrEditPatientOfTreat(0, null);
        }
    }


    @OnItemClick(R.id.lv_list)
    public void onItemClick(int position) {
        String text = tvAddDelSure.getText().toString().trim();
        switch (text) {
            case "添加就诊人":
                PatientOfTreatListBean.PatientsBean patientOfTreatListBean = list.get(position);
                addOrEditPatientOfTreat(1, patientOfTreatListBean);
                break;
            case "删除就诊人":
            case "确认":
                posi = position;
                for (PatientOfTreatListBean.PatientsBean data : list) {
                    data.setSelected(false);
                }
                //点击的设置为选中
                list.get(position).setSelected(true);
                //刷新
                adapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 确认选择就诊人
     */
    private void confirmSelectThePatientOfTreat() {
        PatientOfTreatListBean.PatientsBean data = list.get(posi);
        String id = data.getId() + "";
        String name = data.getUsername();
        Intent intent = getIntent();
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        setResult(RESULT_OK, intent);
        finish();
    }


    /**
     * 添加/编辑就诊人
     */
    private void addOrEditPatientOfTreat(int isEdit, PatientOfTreatListBean.PatientsBean patientInfo) {
        Intent intent = new Intent(getPageContext(), PatientOfTreatAddOrEditActivity.class);
        intent.putExtra("isEdit", isEdit);
        intent.putExtra("patientInfo", patientInfo);
        intent.putExtra("isHaveDefault", isHaveDefault);
        startActivity(intent);
    }


    /**
     * 是否要删除就诊人
     */
    private void showDelPatientOfTreat() {
        DialogUtils.getInstance().showDialog(getPageContext(), "", "确定删除该就诊人吗？", true, new DialogUtils.DialogCallBack() {
            @Override
            public void execEvent() {
                goDelPatientOfTreat();
            }
        });
    }


    /**
     * 右上角点击
     */
    private void setLlMoreClick() {
        int i = clickCount % 2;
        if ("main".equals(from)) {
            if (1 == i) {
                setLvData(1);
                tvAddDelSure.setText("删除就诊人");
            } else {
                setLvData(0);
                tvAddDelSure.setText("添加就诊人");
            }
        } else {
            //添加就诊人
            checkIsCanAdd();
        }
    }


    /**
     * 删除就诊人
     */
    private void goDelPatientOfTreat() {
        int id = list.get(posi).getId();
        HashMap map = new HashMap<>();
        map.put("id", id);
        XyUrl.okPostSave(XyUrl.DEL_PATIENT_OF_TREAT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showShort(msg);
                //删除刷新
                Message message = getHandlerMessage();
                message.what = DEL_PATIENT_SUCCESS;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
    }

    /**
     * 获取就诊人列表
     */
    private void getPatientOfTreatList() {
        HashMap map = new HashMap<>();
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_SCH_PATIENTS_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                PatientOfTreatListBean list = JSONObject.parseObject(value, PatientOfTreatListBean.class);
                Message msg = getHandlerMessage();
                msg.what = GET_PATIENT_OF_TREAT_LIST;
                msg.obj = list;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.PATIENT_ADD_OR_EDIT:
                getPatientOfTreatList();
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            //删除成功刷新
            case DEL_PATIENT_SUCCESS:
                list.remove(posi);
                if (0 == list.size()) {
                    setLvData(0);
                    tvAddDelSure.setText("添加就诊人");
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;
            //重新获取列表 刷新数据
            case GET_PATIENT_OF_TREAT_LIST:
                //list = (List<PatientOfTreatListBean>) msg.obj;
                PatientOfTreatListBean data = (PatientOfTreatListBean) msg.obj;
                list = data.getPatients();
                isHaveDefault = data.getHasdefult();
                if ("main".equals(from)) {
                    setLvData(0);
                } else {
                    setLvData(1);
                }
                break;
        }
    }
}
