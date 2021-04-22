package com.vice.bloodpressure.ui.activity.medicinestore;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.MedicineSearchResultLevelAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MedicineSearchLevelOneBean;
import com.vice.bloodpressure.bean.MedicineSearchLevelZeroBean;
import com.vice.bloodpressure.imp.AdapterViewClickListenerMedicine;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 药品搜索结果
 * 作者: LYD
 * 创建日期: 2019/4/3 14:11
 */
public class MedicineClassListActivity extends BaseHandlerActivity implements AdapterViewClickListenerMedicine {

    private static final int GET_ZERO_DATA = 10010;
    private static final int GET_ONE_DATA = 10011;
    private static final String TAG = "MedicineSearchActivity";
    @BindView(R.id.rv_search_result)
    RecyclerView rvSearchResult;


    MedicineSearchResultLevelAdapter adapter;
    List<MedicineSearchLevelZeroBean> levelZeroList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheDiskStaticUtils.clear();
        setTitle("搜索结果");
        getLevelZeroData();
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_medicine_class_list, contentLayout, false);
    }

    private void getLevelZeroData() {
        String id = getIntent().getStringExtra("id");//药品一级分类id
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.GET_MEDICINE_LEVEL_TWO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<MedicineSearchLevelZeroBean> levelZeroList = JSONObject.parseArray(value, MedicineSearchLevelZeroBean.class);
                Message msg = getHandlerMessage();
                msg.obj = levelZeroList;
                msg.what = GET_ZERO_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    private void getLvOneData(int id, int pos) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id + "");
        XyUrl.okPost(XyUrl.GET_MEDICINE_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<MedicineSearchLevelOneBean> levelOneList = JSONObject.parseArray(value, MedicineSearchLevelOneBean.class);
                Message msg = getHandlerMessage();
                msg.obj = levelOneList;
                msg.what = GET_ONE_DATA;
                msg.arg1 = pos;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    public void adapterViewClick(BaseViewHolder helper, MedicineSearchLevelZeroBean lv0) {
        int pos = helper.getAdapterPosition();
        if (lv0.isExpanded()) {
            adapter.collapse(pos);//折叠
        } else {
            adapter.expand(pos);//展开
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_ZERO_DATA:
                levelZeroList = (List<MedicineSearchLevelZeroBean>) msg.obj;
                for (int i = 0; i < levelZeroList.size(); i++) {
                    getLvOneData(levelZeroList.get(i).getId(), i);
                }
                if (levelZeroList.size() > 0) {
                    //添加0级数据
                    ArrayList<MultiItemEntity> res = new ArrayList<>();
                    res.addAll(levelZeroList);
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
                    adapter = new MedicineSearchResultLevelAdapter(res, this, this);
                    rvSearchResult.setAdapter(adapter);
                    //important! setLayoutManager should be called after setAdapter
                    rvSearchResult.setLayoutManager(manager);
                }
                break;
            case GET_ONE_DATA:
                List<MedicineSearchLevelOneBean> levelOneList = (List<MedicineSearchLevelOneBean>) msg.obj;
                int pos = msg.arg1;
                MedicineSearchLevelZeroBean bean = levelZeroList.get(pos);
                for (int i = 0; i < levelOneList.size(); i++) {
                    bean.addSubItem(new MedicineSearchLevelOneBean(levelOneList.get(i).getId(), levelOneList.get(i).getMedicine(), levelOneList.get(i).getContenturl()));
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

}
