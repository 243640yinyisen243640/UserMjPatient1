package com.vice.bloodpressure.ui.fragment.other;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.MultiCheckHelper;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.SelectFoodTypeAdapter;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class SelectFoodTypeFragment extends BaseEventBusFragment {
    private static final String TAG = "SelectFoodTypeFragment";
    private static final int GET_DATA = 10010;
    @BindView(R.id.rv_food_type)
    RecyclerView rvFoodType;
    private MultiCheckHelper mCheckHelper;
    private boolean[] selectList;
    private List<FoodsCategoryListBean> foodList;
    private BaseViewHolder holder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_food_type;
    }

    @Override
    protected void init(View rootView) {
        getData();
    }


    private void getData() {
        int id = getArguments().getInt("id");
        HashMap map = new HashMap<>();
        map.put("classify", id);
        XyUrl.okPost(XyUrl.GET_FOOD_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryListBean> foodList = JSONObject.parseArray(value, FoodsCategoryListBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                message.setData(bundle);
                message.obj = foodList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                foodList = (List<FoodsCategoryListBean>) msg.obj;
                selectList = new boolean[foodList.size()];
                //创建CheckHelper实例
                mCheckHelper = new MultiCheckHelper();
                //注册选择器
                mCheckHelper.register(FoodsCategoryListBean.class, new CheckHelper.Checker<FoodsCategoryListBean, BaseViewHolder>() {
                    @Override
                    public void check(FoodsCategoryListBean bean, BaseViewHolder holder) {
                        //SelectFoodTypeFragment.this.holder = holder;
                        holder.setImageResource(R.id.img_check, R.drawable.xuanzhong);
                        int position = holder.getLayoutPosition();
                        Log.e(TAG, "选中位置==" + position);
                        selectList[position] = true;
                    }

                    @Override
                    public void unCheck(FoodsCategoryListBean bean, BaseViewHolder holder) {
                        //SelectFoodTypeFragment.this.holder = holder;
                        holder.setImageResource(R.id.img_check, R.drawable.weixuanzhongnew);
                        int position = holder.getLayoutPosition();
                        Log.e(TAG, "取消选中位置==" + position);
                        selectList[position] = false;
                    }
                });
                rvFoodType.setLayoutManager(new LinearLayoutManager(getPageContext()));
                SelectFoodTypeAdapter adapter = new SelectFoodTypeAdapter(foodList, mCheckHelper);
                rvFoodType.setAdapter(adapter);
                break;
        }
    }


    public List<FoodsCategoryListBean> getCategoryList() {
        ArrayList<FoodsCategoryListBean> listSelected = new ArrayList<>();
        if (foodList != null) {
            for (int i = 0; i < selectList.length; i++) {
                if (selectList[i]) {
                    FoodsCategoryListBean showFoodBean = foodList.get(i);
                    listSelected.add(showFoodBean);
                }
            }
        }
        return listSelected;
    }


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.FOOD_TYPE_SELECT_SEND:
                List<FoodsCategoryListBean> checkList = (List<FoodsCategoryListBean>) event.getData();
                for (int i = 0; i < foodList.size(); i++) {
                    int currentClassify = foodList.get(i).getClassify();
                    int currentId = foodList.get(i).getId();
                    for (int j = 0; j < checkList.size(); j++) {
                        int classify = checkList.get(j).getClassify();
                        int id = checkList.get(j).getId();
                        if (currentClassify == classify && currentId == id) {
                            Log.e(TAG, "搜索选中位置==" + i);
                        }
                    }
                }
                break;
        }
    }
}
