package com.vice.bloodpressure.ui.fragment.registration;

import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MyAppointmentAdapter;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.MyAppointmentListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.AdapterClickImp;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:我的预约  和  我的全部预约
 * 作者: LYD
 * 创建日期: 2019/10/28 9:41
 * 1全部 2最新
 */
public class MyAppointmentListFragment extends BaseEventBusFragment implements AdapterClickImp {

    private static final int GET_DATA = 10010;
    private static final int GET_NO_DATA = 30002;
    private static final int REFRESH_SUCCESS = 12315;
    private static final int REFRESH_ERROR = 12316;
    private static final int GET_MORE_DATA_SUCCESS = 10086;
    private static final int GET_MORE_DATA_ERROR = 1008611;
    private static final int DEL_SUCCESS = 10011;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //分页
    private MyAppointmentAdapter adapter;
    //总数据
    private List<MyAppointmentListBean> list;
    //上拉加载数据
    private List<MyAppointmentListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_appointment_list;
    }

    @Override
    protected void init(View rootView) {
        getAppointmentList(false);
        initRefresh();
    }

    /**
     * 获取列表
     */
    private void getAppointmentList(boolean scrollRefresh) {
        int type = getArguments().getInt("type", 0);
        String all = "";
        if (0 == type) {
            all = "2";
        } else {
            all = "1";
        }
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("all", all);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_SCHEDULE_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, MyAppointmentListBean.class);
                if (list != null && list.size() > 0) {
                    Message msg = getHandlerMessage();
                    msg.obj = list;
                    if (scrollRefresh) {
                        msg.what = REFRESH_SUCCESS;
                    } else {
                        msg.what = GET_DATA;
                    }
                    sendHandlerMessage(msg);
                } else {
                    Message msg = getHandlerMessage();
                    if (scrollRefresh) {
                        msg.what = REFRESH_ERROR;
                    } else {
                        msg.what = GET_NO_DATA;
                    }
                    sendHandlerMessage(msg);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                if (scrollRefresh) {
                    msg.what = REFRESH_ERROR;
                } else {
                    msg.what = GET_NO_DATA;
                }
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
                getAppointmentList(true);
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
        int type = getArguments().getInt("type", 0);
        String all = "";
        if (0 == type) {
            all = "2";
        } else {
            all = "1";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("all", all);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_SCHEDULE_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, MyAppointmentListBean.class);
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
    public void onAdapterClick(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_del:
                //删除
                MyAppointmentListBean data = list.get(position);
                int schid = data.getSchid();
                showDelDialog(schid);
                break;
        }
    }


    /**
     * 删除
     *
     * @param schid
     */
    private void showDelDialog(int schid) {
        DialogUtils.getInstance().showDialog(getPageContext(), "", "亲，确定删除预约吗？", true, new DialogUtils.DialogCallBack() {
            @Override
            public void execEvent() {
                doDel(schid);
            }
        });
    }


    /**
     * 删除预约
     *
     * @param schid
     */
    private void doDel(int schid) {
        HashMap map = new HashMap<>();
        map.put("schid", schid);
        map.put("status", 6);
        XyUrl.okPostSave(XyUrl.CANCEL_SCHEDULE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = DEL_SUCCESS;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.APPOINT_REFRESH:
                getAppointmentList(false);
                break;
        }
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
                list = (List<MyAppointmentListBean>) msg.obj;
                adapter = new MyAppointmentAdapter(getPageContext(), R.layout.item_my_appointment, list, this);
                lvList.setAdapter(adapter);
                break;
            case GET_MORE_DATA_SUCCESS:
                pageIndex++;
                srlList.finishLoadMore();
                adapter.notifyDataSetChanged();
                break;
            case GET_MORE_DATA_ERROR:
                srlList.finishLoadMore();
                adapter.notifyDataSetChanged();
                ToastUtils.showShort("没有更多");
                break;
            case REFRESH_SUCCESS:
                srlList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                list = (List<MyAppointmentListBean>) msg.obj;
                adapter = new MyAppointmentAdapter(getPageContext(), R.layout.item_my_appointment, list, this);
                lvList.setAdapter(adapter);
                srlList.finishRefresh();
                break;
            case REFRESH_ERROR:
                srlList.finishRefresh();
                adapter.notifyDataSetChanged();
                ToastUtils.showShort("刷新失败");
                break;
            case DEL_SUCCESS:
                ToastUtils.showShort("操作成功");
                getAppointmentList(false);
                break;
        }
    }
}