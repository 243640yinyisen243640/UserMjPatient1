package com.vice.bloodpressure.ui.activity.registration.report;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.CheckDataBloodCommonAdapter;
import com.vice.bloodpressure.adapter.CheckDataDetailListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.CheckDataBloodAndPissDetailBean;
import com.vice.bloodpressure.bean.CheckDataDetailListBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.engine.GlideImageEngine;
import com.vice.bloodpressure.view.popu.CheckDataBloodAndPissCommonPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * 描述:  检验数据详情列表页
 * 作者: LYD
 * 创建日期: 2019/9/9 16:38
 */
public class ReportDataDetailListActivity extends BaseHandlerActivity {
    private static final String TAG = "CheckDataDetailListActivity";
    private static final int GET_DATA = 10010;
    private static final int GET_MORE = 10011;
    private static final int GET_DETAIL = 10012;
    private static final int GET_NO_DATA = 30002;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    //分页开始
    private List<CheckDataDetailListBean.DataBean> list;
    private List<CheckDataDetailListBean.DataBean> tempList;
    //页数
    private int pageIndex = 2;
    private CheckDataDetailListAdapter adapter;
    //分页结束

    private CheckDataBloodAndPissCommonPopup popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置标题
        setPageTitle();
        //初始化Popu
        initPopup();
        int position = getIntent().getIntExtra("checkDataType", 0);
        //String ofid = getIntent().getStringExtra("ofid");
        //获取数据
        getList(position);
        //刷新
        initRefresh(position);
    }

    /**
     * 设置标题
     */
    private void setPageTitle() {
        int type = getIntent().getIntExtra("checkDataType", 0);
        String[] title = getResources().getStringArray(R.array.report_data_title);
        for (int i = 2; i < title.length; i++) {
            if (i == type) {
                setTitle(title[type]);
            }
        }
    }

    /**
     * 初始化弹窗
     *
     * @param
     */
    private void initPopup() {
        popup = new CheckDataBloodAndPissCommonPopup(getPageContext());
    }

    /**
     * 获取列表数据
     *
     * @param position
     */
    private void getList(int position) {
        String ofid = getIntent().getStringExtra("ofid");
        String url = "";
        switch (position) {
            case 5://血常规
                url = XyUrl.GET_EXAMINE_INFO_BLOOD;
                break;
            case 7://尿常规
                url = XyUrl.GET_EXAMINE_INFO_PISS;
                break;
            default://图片点击
                url = XyUrl.GET_EXAMINE_INFO;
                break;
        }
        String type = "";
        switch (position) {
            case 2://彩超
                type = "1";
                break;
            case 3://心电图
                type = "2";
                break;
            case 4://CT
                type = "3";
                break;
            case 6://tr
                type = "4";
                break;
            case 8://tr
                type = "5";
                break;
            default://图片点击
                type = "0";
                break;
        }
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("page", 1);
        map.put("ofid", ofid);
        //类型
        map.put("type", type);
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //分别判断类型
                CheckDataDetailListBean data = JSONObject.parseObject(value, CheckDataDetailListBean.class);
                list = data.getData();
                Message msg = getHandlerMessage();
                msg.obj = list;
                msg.what = GET_DATA;
                sendHandlerMessage(msg);
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
     *
     * @param position
     */
    private void initRefresh(int position) {
        //刷新开始
        srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlList.finishRefresh(2000);
                pageIndex = 2;
                getList(position);
            }
        });
        srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlList.finishLoadMore(2000);
                String url = "";
                switch (position) {
                    case 5://血常规
                        url = XyUrl.GET_EXAMINE_INFO_BLOOD;
                        break;
                    case 7://尿常规
                        url = XyUrl.GET_EXAMINE_INFO_PISS;
                        break;
                    default://图片点击
                        url = XyUrl.GET_EXAMINE_INFO;
                        break;
                }
                String type = "";
                switch (position) {
                    case 2://彩超
                        type = "1";
                        break;
                    case 3://心电图
                        type = "2";
                        break;
                    case 4://CT
                        type = "3";
                        break;
                    case 6://tr
                        type = "4";
                        break;
                    case 8://tr
                        type = "5";
                        break;
                    default://图片点击
                        type = "0";
                        break;
                }
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                String ofid = getIntent().getStringExtra("ofid");
                HashMap map = new HashMap<>();
                map.put("type", type);
                map.put("page", pageIndex);
                map.put("ofid", ofid);
                XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        CheckDataDetailListBean data = JSONObject.parseObject(value, CheckDataDetailListBean.class);
                        tempList = data.getData();
                        list.addAll(tempList);
                        Message message = getHandlerMessage();
                        message.what = GET_MORE;
                        sendHandlerMessage(message);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        });
        //刷新结束
    }


    /**
     * 设置弹窗
     *
     * @param data
     */
    private void setPopup(CheckDataBloodAndPissDetailBean data) {
        TextView tvTitle = popup.findViewById(R.id.tv_title);
        TextView tvTime = popup.findViewById(R.id.tv_time);
        ListView lv = popup.findViewById(R.id.lv_check_data_blood_common);
        int type = getIntent().getIntExtra("checkDataType", 0);
        String addtime = data.getAddtime();
        tvTime.setText(addtime);
        ArrayList<String> list = new ArrayList<>();
        if (5 == type) {
            tvTitle.setText("血常规报告");
            list.add(data.getLeukocyte_num());
            list.add(data.getLymphocyte_rate());
            list.add(data.getMid_cell_rate());
            list.add(data.getNeutrophil_rate());
            list.add(data.getYmphocyte_num());
            list.add(data.getMid_cell_num());
            list.add(data.getNeutrophil_num());
            list.add(data.getErythrocyte_num());
            list.add(data.getHemoglobin());
            list.add(data.getHematocrit());
            list.add(data.getErythrocyte_volume());
            list.add(data.getHemoglobin_num());
            list.add(data.getHemoglobin_concentration());
            list.add(data.getErythrocyte_cv());
            list.add(data.getErythrocyte_sd());
            list.add(data.getPlatelet_num());
            list.add(data.getPlatelet_volume());
            list.add(data.getPlatelet_width());
            list.add(data.getThrombocytosis());
            list.add(data.getP_lcr());
        } else if (7 == type) {
            tvTitle.setText("尿常规报告");
            list.add(data.getNitrite());
            list.add(data.getUrobiliary());
            list.add(data.getLeukocyte());
            list.add(data.getOccult_blood());
            list.add(data.getAcidity_alkalinity());
            list.add(data.getUsg());
            list.add(data.getUrine_protein());
            list.add(data.getKetone());
            list.add(data.getBilirubin());
            list.add(data.getUrine_sugar());
            list.add(data.getVitamin_c());
        }
        CheckDataBloodCommonAdapter adapter = new CheckDataBloodCommonAdapter(getPageContext(),
                R.layout.item_check_data_blood_common, list, type);
        lv.setAdapter(adapter);
        popup.showPopupWindow();
    }


    /**
     * 获取详情
     *
     * @param type
     * @param id
     */
    private void getListDetail(int type, int id) {
        String url = "";
        if (5 == type) {
            url = XyUrl.GET_EXAMINE_INFO_BLOOD_DETAIL;
        } else if (7 == type) {
            url = XyUrl.GET_EXAMINE_INFO_PISS_DETAIL;
        }
        String ofid = getIntent().getStringExtra("ofid");
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("ofid", ofid);
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                CheckDataBloodAndPissDetailBean data = JSONObject.parseObject(value, CheckDataBloodAndPissDetailBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DETAIL;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_check_data_detail_list, contentLayout, false);
        return view;
    }

    @OnItemClick(R.id.lv_list)
    void onItemClick(int position) {
        //点击处理
        int type = getIntent().getIntExtra("checkDataType", 0);
        switch (type) {
            //电解质
            case 1:
                //血常规
            case 5:
                //尿常规
            case 7:
                int id = list.get(position).getId();
                getListDetail(type, id);
                break;
            //图片点击
            default:
                MNImageBrowser.with(ReportDataDetailListActivity.this)
                        .setCurrentPosition(position)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(list.get(position).getPicurl())
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(lvList);
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA://获取数据
                srlList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                int type = getIntent().getIntExtra("checkDataType", 0);
                list = (List<CheckDataDetailListBean.DataBean>) msg.obj;
                if (list != null && list.size() > 0) {
                    ArrayList<String> stringList = new ArrayList<>();
                    for (int i = 0; i < this.list.size(); i++) {
                        String addtime = this.list.get(i).getAddtime();
                        stringList.add(addtime);
                    }
                    adapter = new CheckDataDetailListAdapter(getPageContext(), R.layout.item_check_data_detail_list, stringList, type);
                    lvList.setAdapter(adapter);
                }
                break;
            case GET_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case GET_DETAIL:
                CheckDataBloodAndPissDetailBean data = (CheckDataBloodAndPissDetailBean) msg.obj;
                setPopup(data);
                break;
            case GET_NO_DATA:
                srlList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }
}
