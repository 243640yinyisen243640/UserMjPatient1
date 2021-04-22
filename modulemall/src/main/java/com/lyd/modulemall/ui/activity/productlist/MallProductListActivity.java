package com.lyd.modulemall.ui.activity.productlist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.MultiCheckHelper;
import com.lyd.baselib.utils.edittext.EditTextUtils;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.MallProductListAdapter;
import com.lyd.modulemall.adapter.ProductLabelListAdapter;
import com.lyd.modulemall.bean.MallHomeProductBean;
import com.lyd.modulemall.bean.ProductLabelListBean;
import com.lyd.modulemall.databinding.ActivityMallProductListBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zackratos.ultimatebarx.library.UltimateBarX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述:  产品列表页
 * 作者: LYD
 * 创建日期: 2020/12/23 14:35
 */
public class MallProductListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MallProductListActivity";
    private ActivityMallProductListBinding mallProductListBinding;
    //分页开始
    private MallProductListAdapter adapter;
    //总数据
    private List<MallHomeProductBean> list;
    //上拉加载数据
    private List<MallHomeProductBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束
    //筛选开始
    //分类  //category_id
    private int categoryId = -1;
    //搜索关键字//goods_name
    private String searchKeyWord = "";
    //销量 //降序：desc //sort
    private String saleCountSort = "";
    //价格 //降序：desc 升序 asc //price_sort
    private String priceSort = "";
    //筛选(多个用逗号分隔) //goods_label_ids
    private String labelIds = "";
    //筛选结束

    private MultiCheckHelper mCheckHelper = new MultiCheckHelper();
    private HashMap<Integer, Integer> labelHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBinding();
        initStatusBar();
        initListener();
        categoryId = getIntent().getIntExtra("category_id", -1);
        searchKeyWord = getIntent().getStringExtra("searchKeyWord");
        getProductList();
        getProductLabelList();
        initRefresh();
        initEtSearch();
        //默认是0
        mallProductListBinding.llPrice.setTag(0);
    }

    private void initStatusBar() {
        UltimateBarX.with(this)
                .fitWindow(true)
                .colorRes(R.color.white)
                .light(true)
                .applyStatusBar();
    }

    private void getProductLabelList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(MallUrl.GET_GOODS_LABEL_LIST)
                .addAll(map)
                .asResponseList(ProductLabelListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<ProductLabelListBean>>() {
                    @Override
                    public void accept(List<ProductLabelListBean> list) throws Exception {

                        //注册选择器
                        mCheckHelper.register(ProductLabelListBean.class, new CheckHelper.Checker<ProductLabelListBean, BaseViewHolder>() {
                            @Override
                            public void check(ProductLabelListBean bean, BaseViewHolder holder) {
                                ColorTextView tvLabel = holder.getView(R.id.tv_label);
                                tvLabel.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white_text));
                                tvLabel.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_green));
                                int position = holder.getLayoutPosition();
                                int goods_label_id = list.get(position).getGoods_label_id();
                                labelHashMap.put(position, goods_label_id);
                                Log.e(TAG, "labelHashMap==" + labelHashMap);
                            }

                            @Override
                            public void unCheck(ProductLabelListBean bean, BaseViewHolder holder) {
                                ColorTextView tvLabel = holder.getView(R.id.tv_label);
                                tvLabel.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.green_text_666));
                                tvLabel.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_f3));
                                int position = holder.getLayoutPosition();
                                //int goods_label_id = list.get(position).getGoods_label_id();
                                labelHashMap.remove(position);
                                Log.e(TAG, "labelHashMap==" + labelHashMap);
                            }
                        });
                        ProductLabelListAdapter productLabelListAdapter = new ProductLabelListAdapter(list, mCheckHelper);
                        mallProductListBinding.rvLabel.setLayoutManager(new GridLayoutManager(MallProductListActivity.this, 3));
                        mallProductListBinding.rvLabel.setAdapter(productLabelListAdapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initRefresh() {
        //刷新开始
        mallProductListBinding.srlGoodsList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mallProductListBinding.srlGoodsList.finishRefresh(2000);
                pageIndex = 2;
                //刷新当前页面
                getProductList();
            }
        });
        mallProductListBinding.srlGoodsList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mallProductListBinding.srlGoodsList.finishLoadMore(2000);
                getMoreData();
            }
        });
        //刷新结束
    }

    private void getMoreData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("category_id", categoryId);
        map.put("goods_name", searchKeyWord);
        map.put("sort", saleCountSort);
        map.put("price_sort", priceSort);
        map.put("goods_label_ids", labelIds);
        RxHttp.postForm(MallUrl.GET_GOODS_LIST)
                .addAll(map)
                .asResponseList(MallHomeProductBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MallHomeProductBean>>() {
                    @Override
                    public void accept(List<MallHomeProductBean> myCouponListBean) throws Exception {
                        tempList = myCouponListBean;
                        list.addAll(tempList);
                        pageIndex++;
                        adapter.notifyDataSetChanged();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void getProductList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", "1");
        map.put("category_id", categoryId);
        map.put("goods_name", searchKeyWord);
        map.put("sort", saleCountSort);
        map.put("price_sort", priceSort);
        map.put("goods_label_ids", labelIds);
        RxHttp.postForm(MallUrl.GET_GOODS_LIST)
                .addAll(map)
                .asResponseList(MallHomeProductBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MallHomeProductBean>>() {
                    @Override
                    public void accept(List<MallHomeProductBean> mallHomeProductBean) throws Exception {
                        list = mallHomeProductBean;
                        mallProductListBinding.rvGoodsList.setLayoutManager(new LinearLayoutManager(MallProductListActivity.this));
                        adapter = new MallProductListAdapter(list);
                        mallProductListBinding.rvGoodsList.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        ToastUtils.showShort("没有该商品");
                    }
                });
    }


    private void initEtSearch() {
        mallProductListBinding.etSearch.setText(searchKeyWord);
        mallProductListBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mallProductListBinding.imgSearchClear.setVisibility(View.VISIBLE);
                } else {
                    mallProductListBinding.imgSearchClear.setVisibility(View.GONE);
                }
            }
        });
        EditTextUtils.setOnActionSearch(mallProductListBinding.etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                String searchStr = mallProductListBinding.etSearch.getText().toString();
                if (TextUtils.isEmpty(searchStr)) {
                    ToastUtils.showShort("请输入搜索内容");
                } else {
                    //跳转页面并搜索
                    //重置条件
                    searchKeyWord = mallProductListBinding.etSearch.getText().toString().trim();
                    saleCountSort = "";
                    priceSort = "";
                    labelIds = "";
                    //请求数据
                    getProductList();
                    //UI变动
                    mallProductListBinding.tvDefault.setTextColor(ColorUtils.getColor(R.color.main_green));
                    mallProductListBinding.tvSaleCount.setTextColor(ColorUtils.getColor(R.color.black_text));
                    mallProductListBinding.imgDown.setVisibility(View.VISIBLE);
                    mallProductListBinding.imgUp.setVisibility(View.VISIBLE);
                    mallProductListBinding.llPrice.setTag(0);
                }
            }
        });
    }


    private void setBinding() {
        mallProductListBinding = ActivityMallProductListBinding.inflate(getLayoutInflater());
        setContentView(mallProductListBinding.getRoot());
    }

    private void initListener() {
        mallProductListBinding.imgMallBack.setOnClickListener(this);
        mallProductListBinding.imgSearchClear.setOnClickListener(this);
        mallProductListBinding.tvDefault.setOnClickListener(this);
        mallProductListBinding.tvSaleCount.setOnClickListener(this);
        mallProductListBinding.llPrice.setOnClickListener(this);
        mallProductListBinding.tvFilter.setOnClickListener(this);
        mallProductListBinding.btReset.setOnClickListener(this);
        mallProductListBinding.btSure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_mall_back) {
            finish();
        } else if (view.getId() == R.id.img_search_clear) {
            mallProductListBinding.etSearch.setText("");
        } else if (view.getId() == R.id.tv_default) {
            //重置条件
            searchKeyWord = mallProductListBinding.etSearch.getText().toString().trim();
            saleCountSort = "";
            priceSort = "";
            labelIds = "";
            //请求数据
            getProductList();
            //UI变动
            mallProductListBinding.tvDefault.setTextColor(ColorUtils.getColor(R.color.main_green));
            mallProductListBinding.tvSaleCount.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.tvPrice.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.imgDown.setVisibility(View.VISIBLE);
            mallProductListBinding.imgUp.setVisibility(View.VISIBLE);
            mallProductListBinding.llPrice.setTag(0);
        } else if (view.getId() == R.id.tv_sale_count) {
            //重置条件
            searchKeyWord = mallProductListBinding.etSearch.getText().toString().trim();
            saleCountSort = "desc";
            priceSort = "";
            labelIds = "";
            //请求数据
            getProductList();
            //UI变动
            mallProductListBinding.tvDefault.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.tvSaleCount.setTextColor(ColorUtils.getColor(R.color.main_green));
            mallProductListBinding.tvPrice.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.imgDown.setVisibility(View.VISIBLE);
            mallProductListBinding.imgUp.setVisibility(View.VISIBLE);
            mallProductListBinding.llPrice.setTag(0);
        } else if (view.getId() == R.id.ll_price) {
            int tag = (int) mallProductListBinding.llPrice.getTag();
            if (0 == tag) {
                //重置条件
                searchKeyWord = mallProductListBinding.etSearch.getText().toString().trim();
                saleCountSort = "";
                priceSort = "asc";
                labelIds = "";
                //请求数据
                getProductList();
                //UI变动
                mallProductListBinding.tvDefault.setTextColor(ColorUtils.getColor(R.color.black_text));
                mallProductListBinding.tvSaleCount.setTextColor(ColorUtils.getColor(R.color.black_text));
                mallProductListBinding.tvPrice.setTextColor(ColorUtils.getColor(R.color.main_green));
                mallProductListBinding.imgDown.setVisibility(View.GONE);
                mallProductListBinding.imgUp.setVisibility(View.VISIBLE);
                mallProductListBinding.llPrice.setTag(1);
            } else if (1 == tag) {
                //重置条件
                searchKeyWord = mallProductListBinding.etSearch.getText().toString().trim();
                saleCountSort = "";
                priceSort = "desc";
                labelIds = "";
                //请求数据
                getProductList();
                //UI变动
                mallProductListBinding.tvDefault.setTextColor(ColorUtils.getColor(R.color.black_text));
                mallProductListBinding.tvSaleCount.setTextColor(ColorUtils.getColor(R.color.black_text));
                mallProductListBinding.tvPrice.setTextColor(ColorUtils.getColor(R.color.main_green));
                mallProductListBinding.imgDown.setVisibility(View.VISIBLE);
                mallProductListBinding.imgUp.setVisibility(View.GONE);
                mallProductListBinding.llPrice.setTag(0);
            }
        } else if (view.getId() == R.id.tv_filter) {
            mallProductListBinding.dlMall.openDrawer(Gravity.RIGHT);
        } else if (view.getId() == R.id.bt_reset) {
            //清除选中
            getProductLabelList();
            labelIds = "";
            //关闭抽屉
            //mallProductListBinding.dlMall.closeDrawer(Gravity.RIGHT);
            //筛选数据
            getProductList();
            //UI变动
            mallProductListBinding.tvDefault.setTextColor(ColorUtils.getColor(R.color.main_green));
            mallProductListBinding.tvSaleCount.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.tvPrice.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.imgDown.setVisibility(View.VISIBLE);
            mallProductListBinding.imgUp.setVisibility(View.VISIBLE);
            mallProductListBinding.llPrice.setTag(0);
        } else if (view.getId() == R.id.bt_sure) {
            labelIds = "";
            //获取选中标签
            for (Map.Entry<Integer, Integer> entry : labelHashMap.entrySet()) {
                Integer value = entry.getValue();
                Log.e(TAG, "标签值" + value);
                labelIds += value + "";
                labelIds += ",";
                Log.e(TAG, "所有标签==" + labelIds);
            }
            //截取最后一个,
            if (labelIds.length() > 0) {
                labelIds = labelIds.substring(0, labelIds.length() - 1);
            }
            //关闭抽屉
            mallProductListBinding.dlMall.closeDrawer(Gravity.RIGHT);
            //筛选数据
            getProductList();
            //UI变动
            mallProductListBinding.tvDefault.setTextColor(ColorUtils.getColor(R.color.main_green));
            mallProductListBinding.tvSaleCount.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.tvPrice.setTextColor(ColorUtils.getColor(R.color.black_text));
            mallProductListBinding.imgDown.setVisibility(View.VISIBLE);
            mallProductListBinding.imgUp.setVisibility(View.VISIBLE);
            mallProductListBinding.llPrice.setTag(0);
        }
    }

    //    /**
    //     * 重写 getResource 方法，防止系统字体影响
    //     * 禁止app字体大小跟随系统字体大小调节
    //     */
    //    @Override
    //    public Resources getResources() {
    //        Resources resources = super.getResources();
    //        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
    //            Configuration configuration = resources.getConfiguration();
    //            configuration.setToDefaults();
    //            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    //        }
    //        return resources;
    //    }
}