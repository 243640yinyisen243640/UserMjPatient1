package com.vice.bloodpressure.ui.fragment.healthydiet;


import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.DietChildAdapter;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 食材库和菜谱库   二级分类
 * 作者: LYD
 * 创建日期: 2019/12/18 14:25
 */
public class HealthyDietChildTwoFragment extends BaseEventBusFragment {
    private static final String TAG = "HealthyDietChildTwoFragment";
    private static final int GET_DATA = 10086;
    private static final int GET_DATA_ERROR = 10087;
    private static final int GET_SEARCH_DATA = 10088;
    private static final int GET_SEARCH_ERROR = 10089;
    @BindView(R.id.lv_child)
    ListView lvChild;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private String currentFragmentPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_healthy_diet_two_and_three_child;
    }

    @Override
    protected void init(View rootView) {
        currentFragmentPosition = getArguments().getString("position");
        getLvData();
    }

    private void getLvData() {
        String type = getArguments().getString("type");
        int id = getArguments().getInt("id");
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        String url = "";
        if ("two".equals(type)) {
            url = XyUrl.GET_FOOD_LIST;
        } else {
            url = XyUrl.GET_GREENS_CLASSIFY_LIST;
        }
        //食材库
        map.put("classify", id);
        //菜谱
        map.put("classifyid", id);
        map.put("page", "1");
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> showMessage = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = showMessage;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = GET_DATA_ERROR;
                sendHandlerMessage(message);
            }
        });

    }

    /**
     * 食材搜索
     *
     * @param keyWord
     */
    private void getSearchData(String keyWord) {
        String type = getArguments().getString("type");
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyWord);
        map.put("page", "1");
        String url = "";
        if ("two".equals(type)) {
            url = XyUrl.GET_FOOD_SEARCH;
        } else {
            url = XyUrl.GET_GREENS_SEARCH;
        }
        XyUrl.okPost(url, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> showMessage = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_SEARCH_DATA;
                message.obj = showMessage;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = GET_SEARCH_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        String type = getArguments().getString("type");
        switch (msg.what) {
            case GET_DATA:
                llEmpty.setVisibility(View.GONE);
                lvChild.setVisibility(View.VISIBLE);
                List<FoodsCategoryListBean> list = (List<FoodsCategoryListBean>) msg.obj;
                if (list != null && list.size() > 0) {
                    DietChildAdapter adapter = new DietChildAdapter(getPageContext(), R.layout.item_healthy_diet_child, list, type);
                    lvChild.setAdapter(adapter);
                }
                break;
            case GET_DATA_ERROR:
                llEmpty.setVisibility(View.VISIBLE);
                lvChild.setVisibility(View.GONE);
                break;
            case GET_SEARCH_DATA:
                llEmpty.setVisibility(View.GONE);
                lvChild.setVisibility(View.VISIBLE);
                List<FoodsCategoryListBean> listSearch = (List<FoodsCategoryListBean>) msg.obj;
                if (listSearch != null && listSearch.size() > 0) {
                    DietChildAdapter adapter = new DietChildAdapter(getPageContext(), R.layout.item_healthy_diet_child, listSearch, type);
                    lvChild.setAdapter(adapter);
                }
                break;
            case GET_SEARCH_ERROR:
                llEmpty.setVisibility(View.VISIBLE);
                lvChild.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.FOOD_SEARCH:
                String currentPosition = event.getMsg();
                String keyWord = (String) event.getData();
                if (currentFragmentPosition.equals(currentPosition)) {
                    getSearchData(keyWord);
                }
                break;
            case ConstantParam.FOOD_REFRESH:
                getLvData();
                break;
        }
    }
}
