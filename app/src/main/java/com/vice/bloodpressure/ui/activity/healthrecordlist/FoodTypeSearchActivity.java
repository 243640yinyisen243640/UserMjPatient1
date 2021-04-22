package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.MultiCheckHelper;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SelectFoodTypeAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2021/1/14 15:38
 */
public class FoodTypeSearchActivity extends BaseHandlerActivity {
    private static final int GET_SEARCH = 10010;
    private static final int GET_SEARCH_ERROR = 10011;
    private static final String TAG = "FoodTypeSearchActivity";
    @BindView(R.id.img_mall_back)
    ImageView imgMallBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_search_clear)
    ImageView imgSearchClear;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private List<FoodsCategoryListBean> checkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        initSearch();

    }

    private void initSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    imgSearchClear.setVisibility(View.VISIBLE);
                } else {
                    imgSearchClear.setVisibility(View.GONE);
                }
            }
        });
        EditTextUtils.setOnEditorActionListener(etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                String searchStr = etSearch.getText().toString();
                if (TextUtils.isEmpty(searchStr)) {
                    ToastUtils.showShort("请输入搜索内容");
                } else {
                    getSearch(searchStr);
                }
            }
        });
    }

    private void getSearch(String searchStr) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", searchStr);
        XyUrl.okPost(XyUrl.GET_FOOD_SEARCH, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> showMessage = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_SEARCH;
                message.obj = showMessage;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) throws JSONException {
                Message message = getHandlerMessage();
                message.what = GET_SEARCH_ERROR;
                message.obj = searchStr;
                sendHandlerMessage(message);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_food_type_search, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SEARCH:
                checkedList = new ArrayList<>();
                List<FoodsCategoryListBean> foodList = (List<FoodsCategoryListBean>) msg.obj;
                //创建CheckHelper实例
                MultiCheckHelper mCheckHelper = new MultiCheckHelper();
                //注册选择器
                mCheckHelper.register(FoodsCategoryListBean.class, new CheckHelper.Checker<FoodsCategoryListBean, BaseViewHolder>() {
                    @Override
                    public void check(FoodsCategoryListBean bean, BaseViewHolder holder) {
                        holder.setImageResource(R.id.img_check, R.drawable.xuanzhong);
                        //                        //找到属于哪个Fragment
                        //                        int classify = bean.getClassify();
                        //                        //找到属于当前的那个位置
                        //                        int id = bean.getId();
                        checkedList.add(bean);
                    }

                    @Override
                    public void unCheck(FoodsCategoryListBean bean, BaseViewHolder holder) {
                        holder.setImageResource(R.id.img_check, R.drawable.weixuanzhongnew);
                        //                        //找到属于哪个Fragment
                        //                        int classify = bean.getClassify();
                        //                        //找到属于当前的那个位置
                        //                        int id = bean.getId();
                        checkedList.remove(bean);
                    }
                });
                rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvList.setAdapter(new SelectFoodTypeAdapter(foodList, mCheckHelper));
                break;
            case GET_SEARCH_ERROR:
                String obj = (String) msg.obj;
                ToastUtils.showShort("没有搜索到" + obj);
                break;
        }
    }

    @OnClick({R.id.img_mall_back, R.id.img_search_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_mall_back:
                toDoFinish();
                break;
            case R.id.img_search_clear:
                etSearch.setText("");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toDoFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toDoFinish() {
        //        int size = checkedList.size();
        //        Log.e(TAG, "size==" + size);
        //        if (checkedList != null && checkedList.size() > 0) {
        //            for (int i = 0; i < checkedList.size(); i++) {
        //                String foodName = checkedList.get(i).getFoodname();
        //                Log.e(TAG, "选中的食物名称==" + foodName);
        //            }
        //        }
        //EventBus发送
        EventBusUtils.post(new EventMessage<>(ConstantParam.FOOD_TYPE_SELECT_SEND, checkedList));
        finish();
    }
}