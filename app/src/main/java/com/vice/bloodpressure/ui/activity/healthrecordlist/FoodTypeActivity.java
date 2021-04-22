package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FoodsCategoryBean;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.fragment.other.FoodTypeFragment;
import com.vice.bloodpressure.utils.ListCastUtils;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class FoodTypeActivity extends BaseHandlerActivity {
    private static final String TAG = "FoodTypeActivity";
    private static final int GET_DATA = 10009;
    private static final int GET_SEARCH = 10010;
    private static final int GET_SEARCH_ERROR = 10011;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.et_search)
    EditText etSearch;
    private int selectPosition;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> typeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("食物类型");
        initSaveButton();
        getTitleData();
        initSearch();
    }

    private void initSearch() {
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

    private void initSaveButton() {
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentList != null || fragmentList.size() != 0) {
                    List<FoodsCategoryListBean> listsAll = new ArrayList<>();
                    for (int i = 0; i < fragmentList.size(); i++) {
                        FoodTypeFragment temp = (FoodTypeFragment) fragmentList.get(i);
                        List<FoodsCategoryListBean> lists = temp.getCategoryList();
                        if (lists != null) {
                            listsAll.addAll(lists);
                        }
                    }
                    if (listsAll.size() == 0) {
                        ToastUtils.showShort("请选择食物");
                    } else {
                        EventBusUtils.post(new EventMessage(ConstantParam.FOOD_SELECT, listsAll, ""));
                        FoodTypeActivity.this.finish();
                    }
                }
            }
        });

    }

    private void initTabView() {
        ArrayList<Integer> imgListGrey = new ArrayList<>();
        ArrayList<Integer> imgListGreen = new ArrayList<>();
        TypedArray imgArrayUnselected = getResources().obtainTypedArray(R.array.food_type_pic_unselected);
        TypedArray imgArraySelected = getResources().obtainTypedArray(R.array.food_type_pic_selected);
        for (int i = 0; i < imgArrayUnselected.length(); i++) {
            imgListGrey.add(imgArrayUnselected.getResourceId(i, R.drawable.default_image));
            imgListGreen.add(imgArraySelected.getResourceId(i, R.drawable.default_image));
        }
        imgArrayUnselected.recycle();
        imgArraySelected.recycle();
        //ViewPager的适配器
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        //关联ViewPager和TabLayout
        tlTab.setupWithViewPager(vpContent);
        vpContent.setOffscreenPageLimit(13);
        for (int i = 0; i < typeArray.size(); i++) {
            View view = LayoutInflater.from(FoodTypeActivity.this).inflate(R.layout.item_add_food_record, null);
            TextView textView = view.findViewById(R.id.tv_add_food_record_item);
            ImageView imageView = view.findViewById(R.id.iv_add_food_record_item);
            if (i == 0) {
                imageView.setImageResource(imgListGreen.get(i));
                textView.setTextColor(ColorUtils.getColor(R.color.main_home));
            } else {
                imageView.setImageResource(imgListGrey.get(i));
                textView.setTextColor(ColorUtils.getColor(R.color.color_93));
            }
            textView.setText(typeArray.get(i));
            tlTab.getTabAt(i).setCustomView(view);
        }
        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectPosition = tab.getPosition();
                TextView textView = tab.getCustomView().findViewById(R.id.tv_add_food_record_item);
                textView.setTextColor(ColorUtils.getColor(R.color.main_home));
                ImageView imageView = tab.getCustomView().findViewById(R.id.iv_add_food_record_item);
                imageView.setImageResource(imgListGreen.get(selectPosition));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView textView = tab.getCustomView().findViewById(R.id.tv_add_food_record_item);
                textView.setTextColor(ColorUtils.getColor(R.color.color_93));
                ImageView imageView = tab.getCustomView().findViewById(R.id.iv_add_food_record_item);
                imageView.setImageResource(imgListGrey.get(position));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void getTitleData() {
        HashMap<String, Object> map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_FOOD_CATEGORY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryBean> foodList = JSONObject.parseArray(value, FoodsCategoryBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
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
        return getLayoutInflater().inflate(R.layout.activity_food_type, contentLayout, false);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                List<FoodsCategoryBean> foodList = (List<FoodsCategoryBean>) msg.obj;
                for (int i = 0; i < foodList.size(); i++) {
                    typeArray.add(foodList.get(i).getFoodlei());
                }
                //Activity向Fragment传值
                Bundle bundle = null;
                FoodTypeFragment foodTypeFragment = null;
                for (int i = 0; i < foodList.size(); i++) {
                    foodTypeFragment = new FoodTypeFragment();
                    bundle = new Bundle();
                    bundle.putInt("id", foodList.get(i).getId());
                    bundle.putInt("position", i);
                    foodTypeFragment.setArguments(bundle);
                    fragmentList.add(foodTypeFragment);
                }
                initTabView();
                break;
            case GET_SEARCH:
                List<FoodsCategoryListBean> list = ListCastUtils.castList(msg.obj, FoodsCategoryListBean.class);
                EventBusUtils.postSticky(new EventMessage<>(ConstantParam.FOOD_TYPE_SEND, list, selectPosition + ""));
                break;
            case GET_SEARCH_ERROR:
                String obj = (String) msg.obj;
                ToastUtils.showShort("没有搜索到" + obj);
                break;
        }
    }


    private class TabAdapter extends FragmentPagerAdapter {

        private TabAdapter(FragmentManager fm) {
            super(fm);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}
