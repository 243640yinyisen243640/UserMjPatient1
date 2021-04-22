package com.vice.bloodpressure.ui.activity.registration;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RegistrationDepartmentsListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.RegistrationDepartmentsListBean;
import com.vice.bloodpressure.bean.RegistrationHospitalListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  体检检查之科室列表
 * 作者: LYD
 * 创建日期: 2019/10/18 15:41
 */
public class PhysicalExaminationListOfDepartmentsActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final int GET_NO_DATA = 30002;
    private static final int GET_MORE_DATA = 10086;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //分页
    private RegistrationDepartmentsListAdapter adapter;
    //总数据
    private List<RegistrationDepartmentsListBean> list;
    //上拉加载数据
    private List<RegistrationDepartmentsListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegistrationHospitalListBean data = (RegistrationHospitalListBean) getIntent().getSerializableExtra("hospitalBean");
        String hospitalName = data.getHospitalname();
        setTitle(hospitalName);
        int id = data.getId();
        getDepartmentsList(id);
        initRefresh(id);
    }

    /**
     * 刷新
     *
     * @param id
     */
    private void initRefresh(int id) {

        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlList.finishRefresh(2000);
                pageIndex = 2;
                getDepartmentsList(id);
            }
        });
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlList.finishLoadMore(2000);
                getMoreList(id);
            }
        });
    }

    private void getMoreList(int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("hospitalid", id);
        XyUrl.okPost(XyUrl.GET_HOSPITAL_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, RegistrationDepartmentsListBean.class);
                list.addAll(tempList);
                Message message = getHandlerMessage();
                message.what = GET_MORE_DATA;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取
     *
     * @param id
     */
    private void getDepartmentsList(int id) {
        HashMap map = new HashMap<>();
        map.put("page", 1);
        map.put("hospitalid", id);
        XyUrl.okPost(XyUrl.GET_DEPARTMENT_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, RegistrationDepartmentsListBean.class);
                if (list != null && list.size() > 0) {
                    Message msg = getHandlerMessage();
                    msg.obj = list;
                    msg.what = GET_DATA;
                    sendHandlerMessage(msg);
                } else {
                    Message msg = getHandlerMessage();
                    msg.what = GET_NO_DATA;
                    sendHandlerMessage(msg);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                msg.what = GET_NO_DATA;
                sendHandlerMessage(msg);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_physical_examination_departments_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_NO_DATA:
                srlList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
            case GET_DATA:
                srlList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                list = (List<RegistrationDepartmentsListBean>) msg.obj;
                adapter = new RegistrationDepartmentsListAdapter(getPageContext(), R.layout.item_physical_examination_hosptial_list, list);
                lvList.setAdapter(adapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
