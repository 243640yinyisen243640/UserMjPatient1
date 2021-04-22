package com.vice.bloodpressure.ui.fragment.smarteducation;

import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SmartEducationClassDetailCatalogueAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.SmartEducationSeriasClassListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述:  智能教育之目录Fragment
 * 作者: LYD
 * 创建日期: 2020/8/20 9:46
 */
public class SmartEducationClassDetailRightFragment extends BaseFragment {
    private static final int GET_CATALOGUE_LIST = 10010;
    @BindView(R.id.rv_class_detail_catalogue)
    RecyclerView rvClassDetailCatalogue;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_education_class_detail_right;
    }

    @Override
    protected void init(View rootView) {
        getCatalogueList();
    }

    private void getCatalogueList() {
        int cid = getArguments().getInt("cId", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        XyUrl.okPost(XyUrl.SMART_EDUCATION_SERIAS_CLASS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<SmartEducationSeriasClassListBean> list = JSONObject.parseArray(value, SmartEducationSeriasClassListBean.class);
                Message message = getHandlerMessage();
                message.what = GET_CATALOGUE_LIST;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_CATALOGUE_LIST:
                List<SmartEducationSeriasClassListBean> list = (List<SmartEducationSeriasClassListBean>) msg.obj;
                int id = getArguments().getInt("id");
                SmartEducationClassDetailCatalogueAdapter adapter = new SmartEducationClassDetailCatalogueAdapter(list, id);
                rvClassDetailCatalogue.setLayoutManager(new LinearLayoutManager(getPageContext()));
                rvClassDetailCatalogue.setAdapter(adapter);
                break;
        }
    }
}
