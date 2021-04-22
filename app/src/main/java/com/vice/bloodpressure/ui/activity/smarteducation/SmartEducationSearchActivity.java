package com.vice.bloodpressure.ui.activity.smarteducation;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SmartEducationSearchAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.SmartEducationSearchListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  智能教育搜索
 * 作者: LYD
 * 创建日期: 2020/8/12 14:11
 */
public class SmartEducationSearchActivity extends BaseHandlerActivity {
    private static final int GET_SEARCH_DATA = 10010;
    private static final int GET_SEARCH_ERROR = 10011;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    ColorTextView tvSearch;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_list)
    SmartRefreshLayout srlList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能教育");
        EditTextUtils.setOnEditorActionListener(etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                String keyWord = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtils.showShort("请输入糖尿病类型或相关成因");
                    return;
                }
                toDoSearch(keyWord);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_smart_education_search, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_SEARCH_DATA:
                srlList.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                List<SmartEducationSearchListBean> list = (List<SmartEducationSearchListBean>) msg.obj;
                SmartEducationSearchAdapter adapter = new SmartEducationSearchAdapter(list, getPageContext());
                rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvList.setAdapter(adapter);
                break;
            case GET_SEARCH_ERROR:
                srlList.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        String keyWord = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入糖尿病类型或相关成因");
            return;
        }
        toDoSearch(keyWord);
    }


    /**
     * @param keyWord
     */
    private void toDoSearch(String keyWord) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyWord);
        XyUrl.okPost(XyUrl.SMART_EDUCATION_SEARCH_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationSearchListBean> list = JSONObject.parseArray(value, SmartEducationSearchListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_SEARCH_DATA;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(GET_SEARCH_ERROR);
            }
        });

    }
}