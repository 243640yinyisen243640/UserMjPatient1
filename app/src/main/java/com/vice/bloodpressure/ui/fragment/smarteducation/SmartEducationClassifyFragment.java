package com.vice.bloodpressure.ui.fragment.smarteducation;


import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SmartEducationClassifyFirstLevelAdapter;
import com.vice.bloodpressure.adapter.SmartEducationClassifySecondLevelAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.SmartEducationClassifyLeftBean;
import com.vice.bloodpressure.bean.SmartEducationClassifyRightBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationSearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  智能教育之分类页面
 * 作者: LYD
 * 创建日期: 2020/8/12 13:52
 */
public class SmartEducationClassifyFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final int GET_FIRST_CLASSIFY = 10010;
    private static final int GET_SECOND_CLASSIFY_SUCCESS = 10011;
    private static final int GET_SECOND_CLASSIFY_ERROR = 10012;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;


    private List<SmartEducationClassifyLeftBean> firstLevelList;
    private SmartEducationClassifyFirstLevelAdapter adapter;
    private int firstLevelId;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_education_classify;
    }

    @Override
    protected void init(View rootView) {
        getFirstLevel();
    }

    private void getFirstLevel() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.SMART_EDUCATION_CATTERY_FIRST_LEVEL_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationClassifyLeftBean> classifyLeftListBean = JSONObject.parseArray(value, SmartEducationClassifyLeftBean.class);
                Message message = getHandlerMessage();
                message.what = GET_FIRST_CLASSIFY;
                message.obj = classifyLeftListBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
            }
        });
    }

    private void getSecondLevel(int firstLevelId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", firstLevelId);
        XyUrl.okPost(XyUrl.SMART_EDUCATION_CATTERY_SECOND_LEVEL_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationClassifyRightBean> classifyRightBean = JSONObject.parseArray(value, SmartEducationClassifyRightBean.class);
                Message message = getHandlerMessage();
                message.what = GET_SECOND_CLASSIFY_SUCCESS;
                message.obj = classifyRightBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = GET_SECOND_CLASSIFY_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        startActivity(new Intent(getPageContext(), SmartEducationSearchActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        firstLevelId = firstLevelList.get(position).getId();
        adapter.setCheck(position);
        adapter.notifyDataSetChanged();
        getSecondLevel(firstLevelId);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_FIRST_CLASSIFY:
                //设置一级
                firstLevelList = (List<SmartEducationClassifyLeftBean>) msg.obj;
                adapter = new SmartEducationClassifyFirstLevelAdapter(getPageContext(), firstLevelList);
                lvLeft.setAdapter(adapter);
                lvLeft.setOnItemClickListener(this);
                //获取二级
                if (firstLevelList != null && firstLevelList.size() > 0) {
                    firstLevelId = firstLevelList.get(0).getId();
                    getSecondLevel(firstLevelId);
                }
                break;
            case GET_SECOND_CLASSIFY_SUCCESS:
                rvList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                ArrayList<SmartEducationClassifyRightBean> rightList = (ArrayList<SmartEducationClassifyRightBean>) msg.obj;
                SmartEducationClassifySecondLevelAdapter foodHomeSecondAdapter = new SmartEducationClassifySecondLevelAdapter(rightList);
                rvList.setLayoutManager(new GridLayoutManager(getPageContext(), 3));
                rvList.setAdapter(foodHomeSecondAdapter);
                break;
            case GET_SECOND_CLASSIFY_ERROR:
                rvList.setAdapter(null);
                rvList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }


}
