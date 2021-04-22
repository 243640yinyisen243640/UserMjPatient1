package com.vice.bloodpressure.ui.fragment.other;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/3/16 20:03
 */
public class FoodTypeFragment extends BaseEventBusFragment implements AdapterView.OnItemClickListener {
    private static final int GET_DATA = 0x6998;
    private boolean[] selectList;
    private ListView lvFoodType;
    private List<FoodsCategoryListBean> foodList;


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                int getPosition = getArguments().getInt("position");
                foodList = (List<FoodsCategoryListBean>) msg.obj;
                selectList = new boolean[foodList.size()];
                if (foodList != null && foodList.size() > 0) {
                    CommonAdapter adapter = new CommonAdapter<FoodsCategoryListBean>(Utils.getApp(),
                            R.layout.item_food_type_layout, foodList) {
                        @Override
                        protected void convert(ViewHolder viewHolder, FoodsCategoryListBean item, int position) {
                            ImageView imageView = viewHolder.getView(R.id.iv_itemFoodType);
                            switch (getPosition + 1) {
                                case 1:
                                    imageView.setImageResource(R.drawable.gushuzalianggreen);
                                    break;
                                case 2:
                                    imageView.setImageResource(R.drawable.rougreen);
                                    break;
                                case 3:
                                    imageView.setImageResource(R.drawable.shucainew);
                                    break;
                                case 4:
                                    imageView.setImageResource(R.drawable.niunaigreen);
                                    break;
                                case 5:
                                    imageView.setImageResource(R.drawable.shuiguogreen);
                                    break;
                                case 6:
                                    imageView.setImageResource(R.drawable.zhifanggreen);
                                    break;
                                case 7:
                                    imageView.setImageResource(R.drawable.douleigreen);
                                    break;
                                case 8:
                                    imageView.setImageResource(R.drawable.danleinew);
                                    break;
                                case 9:
                                    imageView.setImageResource(R.drawable.haixiannew);
                                    break;
                                case 10:
                                    imageView.setImageResource(R.drawable.tiaoweipingreen);
                                    break;
                                case 11:
                                    imageView.setImageResource(R.drawable.jianguonew);
                                    break;
                                case 12:
                                    imageView.setImageResource(R.drawable.xiaochigreen);
                                    break;
                                case 13:
                                    imageView.setImageResource(R.drawable.yinliaogreennew);
                                    break;
                                case 14:
                                    imageView.setImageResource(R.drawable.tanggreen);
                                    break;
                            }
                            String KCAL = String.format("热量: %s千卡", item.getKcalval());
                            viewHolder.setText(R.id.tv_kcal, KCAL);
                            viewHolder.setText(R.id.tv_name, item.getFoodname());
                            String weight = String.format(Utils.getApp().getString(R.string.total_weight), item.getExplain());
                            viewHolder.setText(R.id.tv_weight, weight);
                        }
                    };
                    lvFoodType.setAdapter(adapter);
                }
                break;

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food_type;
    }

    @Override
    protected void init(View rootView) {
        lvFoodType = rootView.findViewById(R.id.lv_food_type);
        lvFoodType.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView itemSelect = view.findViewById(R.id.img_check);
        if (selectList[position]) {
            itemSelect.setImageResource(R.drawable.weixuanzhongnew);
            selectList[position] = false;
        } else {
            itemSelect.setImageResource(R.drawable.xuanzhong);
            selectList[position] = true;
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
    protected void receiveStickyEvent(EventMessage event) {
        super.receiveStickyEvent(event);
        switch (event.getCode()) {
            case ConstantParam.FOOD_TYPE_SEND:
                String msg = event.getMsg();
                int position = getArguments().getInt("position");
                if ((position + "").equals(msg)) {
                    foodList = (List<FoodsCategoryListBean>) event.getData();
                    selectList = new boolean[foodList.size()];
                    if (foodList != null && foodList.size() > 0) {
                        CommonAdapter adapter = new CommonAdapter<FoodsCategoryListBean>(Utils.getApp(),
                                R.layout.item_food_type_layout, foodList) {
                            @Override
                            protected void convert(ViewHolder viewHolder, FoodsCategoryListBean item, int position) {
                                ImageView imageView = viewHolder.getView(R.id.iv_itemFoodType);
                                switch (position + 1) {
                                    case 1:
                                        imageView.setImageResource(R.drawable.gushuzalianggreen);
                                        break;
                                    case 2:
                                        imageView.setImageResource(R.drawable.rougreen);
                                        break;
                                    case 3:
                                        imageView.setImageResource(R.drawable.shucainew);
                                        break;
                                    case 4:
                                        imageView.setImageResource(R.drawable.niunaigreen);
                                        break;
                                    case 5:
                                        imageView.setImageResource(R.drawable.shuiguogreen);
                                        break;
                                    case 6:
                                        imageView.setImageResource(R.drawable.zhifanggreen);
                                        break;
                                    case 7:
                                        imageView.setImageResource(R.drawable.douleigreen);
                                        break;
                                    case 8:
                                        imageView.setImageResource(R.drawable.danleinew);
                                        break;
                                    case 9:
                                        imageView.setImageResource(R.drawable.haixiannew);
                                        break;
                                    case 10:
                                        imageView.setImageResource(R.drawable.tiaoweipingreen);
                                        break;
                                    case 11:
                                        imageView.setImageResource(R.drawable.jianguonew);
                                        break;
                                    case 12:
                                        imageView.setImageResource(R.drawable.xiaochigreen);
                                        break;
                                    case 13:
                                        imageView.setImageResource(R.drawable.yinliaogreennew);
                                        break;
                                    case 14:
                                        imageView.setImageResource(R.drawable.tanggreen);
                                        break;
                                }
                                String KCAL = String.format("热量: %s千卡", item.getKcalval());
                                viewHolder.setText(R.id.tv_kcal, KCAL);
                                viewHolder.setText(R.id.tv_name, item.getFoodname());
                                String weight = String.format(Utils.getApp().getString(R.string.total_weight), item.getExplain());
                                viewHolder.setText(R.id.tv_weight, weight);
                            }
                        };
                        lvFoodType.setAdapter(adapter);
                    }
                }
                break;
        }
    }
}
