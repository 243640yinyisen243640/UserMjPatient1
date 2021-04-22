package com.vice.bloodpressure.ui.activity.medicinestore;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MedicineSearchResultListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MedicineSearchLevelOneBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 搜索结果页
 * 作者: LYD
 * 创建日期: 2019/4/9 15:47
 */
public class MedicineSearchResultListActivity extends BaseHandlerActivity {
    private static final int GET_LV_DATA = 10010;
    private static final int GET_MORE_DATA = 10011;
    private static final int GET_NO_DATA = 10012;
    @BindView(R.id.lv_medicine_result)
    ListView lvMedicineResult;
    @BindView(R.id.srl_medicine_result)
    SmartRefreshLayout srlMedicineResult;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //分页开始
    private List<MedicineSearchLevelOneBean> list;
    private List<MedicineSearchLevelOneBean> tempList;
    private int pageIndex = 2;//页数
    private MedicineSearchResultListAdapter adapter;
    //分页开始

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索结果");
        getLvData();
        initRefresh();
    }

    private void initRefresh() {
        //刷新开始
        srlMedicineResult.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlMedicineResult.finishRefresh(2000);
                pageIndex = 2;
                getLvData();
            }
        });
        srlMedicineResult.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                srlMedicineResult.finishLoadMore(2000);
                HashMap<String, Object> map = new HashMap<>();
                map.put("drugname", getIntent().getStringExtra("keyWord"));
                map.put("page", pageIndex);
                XyUrl.okPost(XyUrl.GET_MEDICINE_SEARCH, map, new OkHttpCallBack<String>() {
                    @Override
                    public void onSuccess(String value) {
                        tempList = JSONObject.parseArray(value, MedicineSearchLevelOneBean.class);
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
        });
        //刷新结束
    }

    private void getLvData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("drugname", getIntent().getStringExtra("keyWord"));
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_MEDICINE_SEARCH, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                list = JSONObject.parseArray(value, MedicineSearchLevelOneBean.class);
                Message msg = getHandlerMessage();
                msg.obj = list;
                msg.what = GET_LV_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                if (ConstantParam.NO_DATA == error) {
                    sendHandlerMessage(GET_NO_DATA);
                }
            }
        });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_medicine_search_result_list, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_LV_DATA:
                srlMedicineResult.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                list = (List<MedicineSearchLevelOneBean>) msg.obj;
                adapter = new MedicineSearchResultListAdapter(getPageContext(), R.layout.item_medicine_search_level_one, list);
                lvMedicineResult.setAdapter(adapter);
                break;
            case GET_MORE_DATA:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
            case GET_NO_DATA:
                srlMedicineResult.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }
}
