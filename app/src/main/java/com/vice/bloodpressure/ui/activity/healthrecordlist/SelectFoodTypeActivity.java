package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.ui.fragment.other.SelectFoodTypeFragment;
import com.wei.android.lib.colorview.view.ColorLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:  选择食物类型
 * 作者: LYD
 * 创建日期: 2021/1/15 9:19
 */
public class SelectFoodTypeActivity extends BaseHandlerEventBusActivity {
    private static final String TAG = "SelectFoodTypeActivity";
    @BindView(R.id.ll_search)
    ColorLinearLayout llSearch;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("食物类型");
        initSaveButton();
        initTabLayout();
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_select_food_type, contentLayout, false);
        return view;
    }


    private void initTabLayout() {
        ArrayList<String> typeArray = getIntent().getStringArrayListExtra("title");
        for (int i = 0; i < typeArray.size(); i++) {
            SelectFoodTypeFragment foodTypeFragment = new SelectFoodTypeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", i + 1);
            foodTypeFragment.setArguments(bundle);
            fragmentList.add(foodTypeFragment);
        }
        initTabView();
    }


    private void initTabView() {
        ArrayList<String> typeArray = getIntent().getStringArrayListExtra("title");
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
        //设置TabLayout的模式
        tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);//可滑动
        //ViewPager的适配器
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        //关联ViewPager和TabLayout
        tlTab.setupWithViewPager(vpContent);
        vpContent.setOffscreenPageLimit(13);
        for (int i = 0; i < typeArray.size(); i++) {
            View view = LayoutInflater.from(SelectFoodTypeActivity.this).inflate(R.layout.item_add_food_record, null);
            TextView textView = view.findViewById(R.id.tv_add_food_record_item);
            ImageView imageView = view.findViewById(R.id.iv_add_food_record_item);
            if (i == 0) {
                imageView.setImageResource(imgListGreen.get(i));
                textView.setTextColor(ColorUtils.getColor(R.color.main_home));
            } else
                imageView.setImageResource(imgListGrey.get(i));
            textView.setText(typeArray.get(i));
            tlTab.getTabAt(i).setCustomView(view);
        }
        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView textView = tab.getCustomView().findViewById(R.id.tv_add_food_record_item);
                textView.setTextColor(ColorUtils.getColor(R.color.main_home));
                ImageView imageView = tab.getCustomView().findViewById(R.id.iv_add_food_record_item);
                imageView.setImageResource(imgListGreen.get(position));
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

    private void initSaveButton() {
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentList != null || fragmentList.size() != 0) {
                    List<FoodsCategoryListBean> listsAll = new ArrayList<>();
                    for (int i = 0; i < fragmentList.size(); i++) {
                        SelectFoodTypeFragment temp = (SelectFoodTypeFragment) fragmentList.get(i);
                        List<FoodsCategoryListBean> lists = temp.getCategoryList();
                        if (lists != null) {
                            listsAll.addAll(lists);
                        }
                    }
                    if (listsAll.size() == 0) {
                        ToastUtils.showShort("请选择食物");
                    } else {
                        EventBusUtils.post(new EventMessage(ConstantParam.FOOD_SELECT, listsAll, ""));
                        finish();
                    }
                }
            }
        });
    }

    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        startActivity(new Intent(getPageContext(), FoodTypeSearchActivity.class));
    }

    @Override
    public void processHandlerMsg(Message msg) {

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