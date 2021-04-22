package com.lyd.modulemall.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.base.fragment.BaseViewBindingFragment;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.MallHomeBannerAdapter;
import com.lyd.modulemall.adapter.MallHomeClassifyAdapter;
import com.lyd.modulemall.adapter.MallHomeCouponAndActivityAdapter;
import com.lyd.modulemall.adapter.MallHomeProductAdapter;
import com.lyd.modulemall.bean.MallHomeIndexBean;
import com.lyd.modulemall.bean.MallHomeProductBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.FragmentMallHomeBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.BaseWebViewActivity;
import com.lyd.modulemall.ui.activity.ProductDetailActivity;
import com.lyd.modulemall.ui.activity.productlist.MallProductListActivity;
import com.lyd.modulemall.ui.activity.productlist.MallSearchActivity;
import com.lyd.modulemall.ui.activity.shoppingcart.ShoppingCartActivity;
import com.lyd.modulemall.utils.RxHttpPublicParamsAddUtils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wei.android.lib.colorview.view.ColorLinearLayout;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: 原生商城首页
 * 作者: LYD
 * 创建日期: 2020/12/22 16:21
 */
public class MallHomeFragment extends BaseViewBindingFragment<FragmentMallHomeBinding> implements BannerViewPager.OnPageClickListener, View.OnClickListener {
    //分页开始
    private MallHomeProductAdapter adapter;
    //总数据
    private List<MallHomeProductBean> list;
    //上拉加载数据
    private List<MallHomeProductBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束
    //HeadView Start
    private View headView;
    private ColorLinearLayout llSearch;
    private FrameLayout flShopingChat;
    private ColorTextView tvRedPoint;
    private ImageView imgCart;
    private BannerViewPager bannerView;
    private IndicatorView indicatorView;
    private RecyclerView rvMallClassify;
    private RecyclerView rvCouponAndActivity;
    private TextView tvAllGoods;
    //HeadView End
    private List<MallHomeIndexBean.SlideshowListBean> slideshow_list;


    @Override
    protected void init() {
        RxHttpPublicParamsAddUtils.initRxHttp();
        FloatingView.get().remove();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        initRv();
        initRefresh();
        getHomeProductList();
    }

    @Override
    public void onLazyLoad() {

    }

    private void initRv() {
        binding.rvMallHome.setLayoutManager(new GridLayoutManager(getPageContext(), 2));
        //HeadView
        headView = getLayoutInflater().inflate(R.layout.include_mall_top_five, (ViewGroup) binding.rvMallHome.getParent(), false);
        llSearch = headView.findViewById(R.id.ll_search);
        llSearch.setOnClickListener(this);
        flShopingChat = headView.findViewById(R.id.fl_shoping_chat);
        flShopingChat.setOnClickListener(this);
        imgCart = headView.findViewById(R.id.img_cart);
        imgCart.setOnClickListener(this);
        tvRedPoint = headView.findViewById(R.id.tv_red_point);
        bannerView = headView.findViewById(R.id.banner_view);
        indicatorView = headView.findViewById(R.id.indicator_view);
        rvMallClassify = headView.findViewById(R.id.rv_mall_classify);
        rvCouponAndActivity = headView.findViewById(R.id.rv_coupon_and_activity);
        tvAllGoods = headView.findViewById(R.id.tv_all_goods);
        tvAllGoods.setOnClickListener(this);
    }

    private void initRefresh() {
        //开启嵌套滑动
        binding.srlMallHome.setEnableNestedScroll(true);
        //刷新开始
        binding.srlMallHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                binding.srlMallHome.finishRefresh(2000);
                pageIndex = 2;
                //刷新当前页面
                getHomeProductList();
            }
        });
        binding.srlMallHome.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                binding.srlMallHome.finishLoadMore(2000);
                getMoreData();
            }
        });
        //刷新结束
    }


    /***
     * 获取首页商品数据
     */
    private void getHomeProductList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", "1");
        map.put("is_recommend", "1");
        RxHttp.postForm(MallUrl.GET_GOODS_LIST)
                .addAll(map)
                .asResponseList(MallHomeProductBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MallHomeProductBean>>() {
                    @Override
                    public void accept(List<MallHomeProductBean> mallHomeProductBean) throws Exception {
                        getHomeIndex();
                        list = mallHomeProductBean;
                        adapter = new MallHomeProductAdapter(list);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 获取首页数据
     */
    private void getHomeIndex() {
        HashMap map = new HashMap<>();
        RxHttp.postForm(MallUrl.MALL_HOME_INDEX)
                .addAll(map)
                .asResponse(MallHomeIndexBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<MallHomeIndexBean>() {
                    @Override
                    public void accept(MallHomeIndexBean data) throws Exception {
                        //轮播图
                        slideshow_list = data.getSlideshow_list();
                        //分类
                        List<MallHomeIndexBean.CategoryListBean> category_list = data.getCategory_list();
                        //活动
                        List<MallHomeIndexBean.ActivityListBean> activity_list = data.getActivity_list();
                        addHeadView();
                        //之后设置HeadView的数据
                        initBanner();
                        initClassify(category_list);
                        initCouponAndActivity(activity_list);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void addHeadView() {
        //添加头布局 开始
        adapter.addHeaderView(headView);
        //添加头布局 结束
        binding.rvMallHome.setAdapter(adapter);
    }

    /**
     * 获取首页更多商品数据
     */
    private void getMoreData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("is_recommend", 1);
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


    /**
     * 设置轮播图
     */
    @SuppressWarnings("all")
    private void initBanner() {
        //初始化属性
        bannerView.
                setPageStyle(PageStyle.MULTI_PAGE_SCALE)
                .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_15))
                .setRevealWidth(getResources().getDimensionPixelOffset(R.dimen.dp_10))

                .setIndicatorView(indicatorView)
                .setIndicatorStyle(IndicatorStyle.DASH)
                .setIndicatorSliderColor(ColorUtils.getColor(R.color.color_dc), ColorUtils.getColor(R.color.main_green))
                .setIndicatorVisibility(View.GONE)

                .setLifecycleRegistry(getLifecycle())
                .setOnPageClickListener(this)
                .setAdapter(new MallHomeBannerAdapter())
                .setInterval(3000)
                .create(slideshow_list);
    }

    /**
     * 设置分类
     *
     * @param category_list
     */
    private void initClassify(List<MallHomeIndexBean.CategoryListBean> category_list) {
        LinearLayoutManager glm = new LinearLayoutManager(getPageContext(), RecyclerView.HORIZONTAL, false);
        rvMallClassify.setLayoutManager(glm);
        MallHomeClassifyAdapter mallHomeClassifyAdapter = new MallHomeClassifyAdapter(category_list);
        rvMallClassify.setAdapter(mallHomeClassifyAdapter);
    }

    /**
     * 设置活动和优惠券
     *
     * @param activity_list
     */
    private void initCouponAndActivity(List<MallHomeIndexBean.ActivityListBean> activity_list) {
        GridLayoutManager glm = new GridLayoutManager(getPageContext(), 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int size = activity_list.size();
                if (size % 2 == 0) {
                    return 1;
                } else {
                    if (0 == position) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            }
        });
        rvCouponAndActivity.setLayoutManager(glm);
        rvCouponAndActivity.setAdapter(new MallHomeCouponAndActivityAdapter(activity_list));
    }


    /**
     * 轮播图点击进商品详情
     *
     * @param clickedView
     * @param position
     */
    @Override
    public void onPageClick(View clickedView, int position) {
        int goods_id = slideshow_list.get(position).getGoods_id();
        Intent intent = new Intent(getPageContext(), ProductDetailActivity.class);
        intent.putExtra("goods_id", goods_id);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_search) {
            startActivity(new Intent(getPageContext(), MallSearchActivity.class));
        } else if (id == R.id.fl_shoping_chat) {
            Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
            intent.putExtra("title", "客服");
            intent.putExtra("url", MallConstantParam.SERVICE_URL);
            startActivity(intent);
        } else if (id == R.id.img_cart) {
            startActivity(new Intent(getPageContext(), ShoppingCartActivity.class));
        } else if (id == R.id.tv_all_goods) {
            startActivity(new Intent(getPageContext(), MallProductListActivity.class));
        }
    }
}
