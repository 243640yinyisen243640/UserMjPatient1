package com.vice.bloodpressure.ui.fragment.healthweight;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.WeightListAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.WeightListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.WeightAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  体重记录之列表
 * 作者: LYD
 * 创建日期: 2020/5/11 10:59
 */
public class WeightListFragment extends BaseFragment {
    private static final int GET_DATA_SUCCESS = 10010;
    private static final int GET_DATA_MORE = 10011;
    private static final int DEL_SUCCESS = 10012;
    private static final int GET_DATA_ERROR = 10013;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.lv_weight_list)
    ListView lvWeightList;
    @BindView(R.id.srl_weight_list)
    SmartRefreshLayout srlWeightList;

    private String beginTime = "";
    private String endTime = "";

    private WeightListAdapter adapter;
    private List<WeightListBean> list;
    private List<WeightListBean> tempList;
    private int pageIndex = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weight_list;
    }

    @Override
    protected void init(View rootView) {
        getWeightList(beginTime, endTime);
        initRefresh();
        initLvOnLongClick();
    }

    private void initLvOnLongClick() {
        lvWeightList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
                    toDoDel(position);
                });
                return true;
            }
        });
    }

    private void toDoDel(int position) {
        //1血糖 2血压 3饮食 4运动 5用药 6bmi 7糖化血红蛋白 8检查 9肝病营养指标
        int id = list.get(position).getId();
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("type", 11);
        XyUrl.okPostSave(XyUrl.HEALTH_RECORD_DEL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = DEL_SUCCESS;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, final String errorMsg) {

            }
        });
    }


    private void initRefresh() {
        //刷新开始
        srlWeightList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlWeightList.finishRefresh(2000);
                pageIndex = 2;
                tvBeginTime.setText(getString(R.string.start_date));
                tvEndTime.setText(getString(R.string.end_date));
                getWeightList("", "");
            }
        });
        srlWeightList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlWeightList.finishLoadMore(2000);
                getDataMore(beginTime, endTime);
            }
        });
        //刷新结束
    }

    private void getWeightList(String beginTime, String endTime) {
        HashMap map = new HashMap<>();
        map.put("begintime", beginTime);
        map.put("endtime", endTime);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_WEIGHT_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, WeightListBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA_SUCCESS;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                Message message = Message.obtain();
                message.what = GET_DATA_ERROR;
                sendHandlerMessage(message);
            }
        });
    }


    /**
     * 获取更多
     *
     * @param bTime
     * @param eTime
     */
    private void getDataMore(String bTime, String eTime) {
        HashMap map = new HashMap<>();
        map.put("begintime", bTime);
        map.put("endtime", eTime);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_WEIGHT_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                tempList = JSONObject.parseArray(value, WeightListBean.class);
                list.addAll(tempList);
                Message message = Message.obtain();
                message.what = GET_DATA_MORE;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                //Message message = Message.obtain();
                //message.what = GET_DATA_ERROR;
                //sendHandlerMessage(message);
            }
        });


    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA_SUCCESS:
                list = (List<WeightListBean>) msg.obj;
                if (list != null && list.size() > 0) {
                    adapter = new WeightListAdapter(getPageContext(), R.layout.item_weight_list, list);
                    lvWeightList.setAdapter(adapter);
                }
                break;
            case GET_DATA_ERROR:
                ToastUtils.showShort("数据为空");
                list = new ArrayList<>();
                adapter = new WeightListAdapter(getPageContext(), R.layout.item_weight_list, list);
                lvWeightList.setAdapter(adapter);
                break;
            case GET_DATA_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case DEL_SUCCESS:
                getWeightList(beginTime, endTime);
                break;
        }
    }


    @OnClick({R.id.tv_begin_time, R.id.tv_end_time, R.id.btn_add_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_begin_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvBeginTime.setText(content);
                        endTime = tvEndTime.getText().toString().trim();
                        if ("选择结束时间".equals(endTime)) {
                            endTime = "";
                        }
                        beginTime = tvBeginTime.getText().toString().trim();
                        getWeightList(beginTime, endTime);
                    }

                });
                break;
            case R.id.tv_end_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvEndTime.setText(content);
                        beginTime = tvBeginTime.getText().toString().trim();
                        if ("选择开始时间".equals(beginTime)) {
                            beginTime = "";
                        }
                        endTime = tvEndTime.getText().toString().trim();
                        getWeightList(beginTime, endTime);
                    }
                });
                break;
            case R.id.btn_add_record:
                startActivity(new Intent(getPageContext(), WeightAddActivity.class));
                break;
        }
    }
}
