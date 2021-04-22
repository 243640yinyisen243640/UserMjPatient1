package com.vice.bloodpressure.ui.activity.registration;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.allen.library.utils.ToastUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.RegistrationHospitalListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.RegistrationHospitalListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:体检预约之医院列表
 * 作者: LYD
 * 创建日期: 2019/10/18 13:46
 */
public class PhysicalExaminationListOfHospitalActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final int GET_NO_DATA = 30002;
    private static final int GET_MORE_DATA = 10086;
    @BindView(R.id.et_search_hospital_name)
    EditText etSearchHospitalName;


    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    //分页
    private RegistrationHospitalListAdapter adapter;
    //总数据
    private List<RegistrationHospitalListBean> list;
    //上拉加载数据
    private List<RegistrationHospitalListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("体检预约");
        getHospitalList("");
        initRefresh();
        setEtSearch();
    }

    /**
     * 搜索监听
     */
    private void setEtSearch() {
        EditTextUtils.setOnEditorActionListener(etSearchHospitalName, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                checkSearch();
            }
        });
    }

    /**
     * 获取列表
     */
    private void getHospitalList(String hospitalName) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        String url = "";
        if (TextUtils.isEmpty(hospitalName)) {
            map.put("page", 1);
            url = XyUrl.GET_HOSPITAL_LIST;
        } else {
            map.put("hospitalname", hospitalName);
            url = XyUrl.GET_SEARCH_HOSPITAL;
        }
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, RegistrationHospitalListBean.class);
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

    /**
     * 刷新
     */
    private void initRefresh() {
        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlList.finishRefresh(2000);
                pageIndex = 2;
                getHospitalList("");
            }
        });
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlList.finishLoadMore(2000);
                getMoreList();
            }
        });
    }

    /**
     * 获取更多
     */
    private void getMoreList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_HOSPITAL_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, RegistrationHospitalListBean.class);
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


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_physical_examination_hospital_list, contentLayout, false);
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
                list = (List<RegistrationHospitalListBean>) msg.obj;
                adapter = new RegistrationHospitalListAdapter(getPageContext(), R.layout.item_physical_examination_hosptial_list, list);
                lvList.setAdapter(adapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        checkSearch();
    }

    /**
     * 校验
     */
    private void checkSearch() {
        String hospitalName = etSearchHospitalName.getText().toString().trim();
        if (TextUtils.isEmpty(hospitalName)) {
            ToastUtils.showToast("请输入搜索医院名称");
            return;
        }
        goSearch(hospitalName);
        KeyboardUtils.hideSoftInput(etSearchHospitalName);
    }

    /**
     * 搜索医院
     *
     * @param hospitalName
     */
    private void goSearch(String hospitalName) {
        getHospitalList(hospitalName);
    }

}
