package com.vice.bloodpressure.ui.activity.food;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FoodFirstLevelAdapter;
import com.vice.bloodpressure.adapter.FoodHomeSecondAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FoodsCategoryBean;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  食材库
 * 作者: LYD
 * 创建日期: 2020/4/17 15:30
 */
public class FoodHomeActivity extends BaseHandlerActivity implements AdapterView.OnItemClickListener {
    private static final int GET_FIRST_CLASSIFY = 10010;
    private static final int GET_SECOND_CLASSIFY_ERROR = 10011;
    private static final int GET_SECOND_CLASSIFY_SUCCESS = 10012;
    private static final int GET_SEARCH_DATA = 10013;
    private static final int GET_SEARCH_ERROR = 10014;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.lv_right)
    ListView lvRight;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //@BindView(R.id.srl_right)
    //SmartRefreshLayout srlRight;
    //一级
    private List<FoodsCategoryBean> firstLevelList;
    private FoodFirstLevelAdapter adapter;
    private int pageIndex = 1;
    private int firstLevelId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("食材库");
        getFirstLevel();
        setSearch();
    }

    private void setSearch() {
        EditTextUtils.setOnEditorActionListener(etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                checkSearch();
            }
        });
    }

    private void checkSearch() {
        String keyWord = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入食物名称");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyWord);
        map.put("page", "1");
        XyUrl.okPost(XyUrl.GET_FOOD_SEARCH, map, new OkHttpCallBack<String>() {
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

    /**
     * 获取食物一级分类
     */
    private void getFirstLevel() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_FOOD_CATEGORY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryBean> foodList = JSONObject.parseArray(value, FoodsCategoryBean.class);
                Message message = getHandlerMessage();
                message.what = GET_FIRST_CLASSIFY;
                message.obj = foodList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
            }
        });

    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_food_home, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_FIRST_CLASSIFY:
                firstLevelList = (List<FoodsCategoryBean>) msg.obj;
                adapter = new FoodFirstLevelAdapter(getPageContext(), firstLevelList);
                lvLeft.setAdapter(adapter);
                lvLeft.setOnItemClickListener(this);
                //获取二级
                if (firstLevelList != null && firstLevelList.size() > 0) {
                    firstLevelId = firstLevelList.get(0).getId();
                    getSecondLevel(pageIndex, firstLevelId);
                }
                break;
            case GET_SEARCH_DATA:
            case GET_SECOND_CLASSIFY_SUCCESS:
                llEmpty.setVisibility(View.GONE);
                lvRight.setVisibility(View.VISIBLE);
                List<FoodsCategoryListBean> listSecond = (List<FoodsCategoryListBean>) msg.obj;
                FoodHomeSecondAdapter foodHomeSecondAdapter = new FoodHomeSecondAdapter(getPageContext(), R.layout.item_food_home_second_level, listSecond);
                lvRight.setAdapter(foodHomeSecondAdapter);
                break;
            case GET_SECOND_CLASSIFY_ERROR:
                //
                break;
            case GET_SEARCH_ERROR:
                llEmpty.setVisibility(View.VISIBLE);
                lvRight.setVisibility(View.GONE);
                break;
        }
    }


    private void getSecondLevel(int pageIndex, int firstLevelId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("classify", firstLevelId);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.GET_FOOD_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> showMessage = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_SECOND_CLASSIFY_SUCCESS;
                message.obj = showMessage;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        firstLevelId = firstLevelList.get(position).getId();
        adapter.setCheck(position);
        adapter.notifyDataSetChanged();
        getSecondLevel(pageIndex, firstLevelId);
    }
}
