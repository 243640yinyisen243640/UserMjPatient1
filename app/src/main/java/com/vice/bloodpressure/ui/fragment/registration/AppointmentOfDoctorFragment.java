package com.vice.bloodpressure.ui.fragment.registration;

import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.AppointmentOfDoctorAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.AppointmentDoctorListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.widget.view.decoration.CommonItemDecoration;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  按医生预约
 * 作者: LYD
 * 创建日期: 2019/10/21 10:18
 */
public class AppointmentOfDoctorFragment extends BaseFragment {
    private static final int GET_DATA = 10010;
    private static final int GET_NO_DATA = 30002;
    private static final int REFRESH_SUCCESS = 12315;
    private static final int REFRESH_ERROR = 12316;
    private static final int GET_MORE_DATA_SUCCESS = 10086;
    private static final int GET_MORE_DATA_ERROR = 1008611;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.rv_list_of_doctor)
    RecyclerView rvListOfDoctor;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.img_hot)
    ImageView imgHot;

    //分页
    private AppointmentOfDoctorAdapter adapter;
    //总数据
    private List<AppointmentDoctorListBean> list;
    //上拉加载数据
    private List<AppointmentDoctorListBean> tempList;
    //上拉加载页数
    private int pageIndex = 1;
    //分页

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_appointment_of_doctor;
    }

    @Override
    protected void init(View rootView) {
        int depid = getArguments().getInt("depid", 0);
        getDoctorList(depid);
        initRefresh(depid);
        initRv();
    }

    /**
     *
     */
    private void initRv() {
        rvListOfDoctor.setLayoutManager(new GridLayoutManager(getPageContext(), 2));
        int spacing = ConvertUtils.dp2px(10);
        rvListOfDoctor.addItemDecoration(new CommonItemDecoration(spacing, spacing, spacing));
    }


    /**
     * 医生列表
     *
     * @param depid
     */
    private void getDoctorList(int depid) {
        HashMap map = new HashMap<>();
        map.put("schtime", "");
        map.put("depid", depid);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_SCHEDULE_DOC, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, AppointmentDoctorListBean.class);
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
     * 刷新医生列表
     *
     * @param depid
     */
    private void refreshDoctorList(int depid) {
        HashMap map = new HashMap<>();
        map.put("schtime", "");
        map.put("depid", depid);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_SCHEDULE_DOC, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, AppointmentDoctorListBean.class);
                if (list != null && list.size() > 0) {
                    Message msg = getHandlerMessage();
                    msg.obj = list;
                    msg.what = REFRESH_SUCCESS;
                    sendHandlerMessage(msg);
                } else {
                    Message msg = getHandlerMessage();
                    msg.what = REFRESH_ERROR;
                    sendHandlerMessage(msg);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                msg.what = REFRESH_ERROR;
                sendHandlerMessage(msg);
            }
        });
    }

    /**
     * 刷新
     *
     * @param depid
     */
    private void initRefresh(int depid) {

        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageIndex = 1;
                refreshDoctorList(depid);
            }
        });
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageIndex++;
                getMoreList(depid);
            }
        });
    }

    /**
     * 加载更多
     *
     * @param depid
     */
    private void getMoreList(int depid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("schtime", "");
        map.put("depid", depid);
        XyUrl.okPost(XyUrl.GET_SCHEDULE_DOC, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                LogUtils.e(value);
                tempList = JSONObject.parseArray(value, AppointmentDoctorListBean.class);
                LogUtils.e("pageIndex=======" + pageIndex);
                LogUtils.e("list.size=======" + list.size());
                LogUtils.e("tempList.size=======" + tempList.size());
                list.addAll(tempList);
                Message message = getHandlerMessage();
                message.what = GET_MORE_DATA_SUCCESS;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = GET_MORE_DATA_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_NO_DATA:
                imgHot.setVisibility(View.GONE);
                srlList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
            case GET_DATA:
                imgHot.setVisibility(View.VISIBLE);
                srlList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                list = (List<AppointmentDoctorListBean>) msg.obj;
                adapter = new AppointmentOfDoctorAdapter(getPageContext(), R.layout.item_appoint_doctor_of_doctor, list);
                rvListOfDoctor.setAdapter(adapter);
                break;
            case REFRESH_SUCCESS:
                imgHot.setVisibility(View.VISIBLE);
                srlList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                list = (List<AppointmentDoctorListBean>) msg.obj;
                adapter = new AppointmentOfDoctorAdapter(getPageContext(), R.layout.item_appoint_doctor_of_doctor, list);
                rvListOfDoctor.setAdapter(adapter);
                srlList.finishRefresh();
                adapter.notifyDataSetChanged();
                break;
            case REFRESH_ERROR:
                srlList.finishRefresh();
                adapter.notifyDataSetChanged();
                ToastUtils.showShort("没有数据");
                break;
            case GET_MORE_DATA_SUCCESS:
                srlList.finishLoadMore();
                adapter.notifyItemChanged(0);
                break;
            case GET_MORE_DATA_ERROR:
                srlList.finishLoadMore();
                ToastUtils.showShort("没有更多");
                break;
        }
    }
}
