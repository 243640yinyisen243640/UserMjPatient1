package com.lyd.modulemall.ui.activity.user;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.MyCouponListAdapter;
import com.lyd.modulemall.bean.MyCouponListBean;
import com.lyd.modulemall.databinding.ActivityMyCouponListBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.utils.RxHttpPublicParamsAddUtils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 我的优惠券 和 优惠券列表
 * 作者: LYD
 * 创建日期: 2020/12/24 15:49
 */
public class MyCouponListActivity extends BaseViewBindingActivity<ActivityMyCouponListBinding> {
    //分页开始
    private MyCouponListAdapter adapter;
    //总数据
    private List<MyCouponListBean> list;
    //上拉加载数据
    private List<MyCouponListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束
    private String activity_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        RxHttpPublicParamsAddUtils.initRxHttp();
        activity_id = getIntent().getStringExtra("activity_id");
        if ("-1".equals(activity_id)) {
            setTitle("我的优惠券");
        } else {
            setTitle("优惠券");
        }
        getMyCouponList();
        initRefresh();
    }

    /**
     * 刷新
     */
    private void initRefresh() {
        //刷新开始
        binding.srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                binding.srlList.finishRefresh(2000);
                pageIndex = 2;
                getMyCouponList();
            }
        });
        binding.srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                binding.srlList.finishLoadMore(2000);
                getMoreData();
            }
        });
        //刷新结束
    }


    /**
     * 获取我的优惠券列表
     */
    private void getMyCouponList() {
        String url;
        if ("-1".equals(activity_id)) {
            url = MallUrl.GET_MY_COUPON_LIST;
        } else {
            url = MallUrl.GET_COUPON_LIST;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", "1");
        map.put("activity_id", getIntent().getStringExtra("activity_id"));
        RxHttp.postForm(url)
                .addAll(map)
                .asResponseList(MyCouponListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyCouponListBean>>() {
                    @Override
                    public void accept(List<MyCouponListBean> myCouponListBean) throws Exception {
                        list = myCouponListBean;
                        adapter = new MyCouponListAdapter(list, getIntent().getStringExtra("activity_id"));
                        binding.rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        binding.rvList.setAdapter(adapter);
                        toGetCoupon(list);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }


    /**
     * 加载更多数据
     */
    private void getMoreData() {
        String url;
        if ("-1".equals(activity_id)) {
            url = MallUrl.GET_MY_COUPON_LIST;
        } else {
            url = MallUrl.GET_COUPON_LIST;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("activity_id", activity_id);
        RxHttp.postForm(url)
                .addAll(map)
                .asResponseList(MyCouponListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyCouponListBean>>() {
                    @Override
                    public void accept(List<MyCouponListBean> myCouponListBean) throws Exception {
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
     * 领取优惠券
     *
     * @param list
     */
    private void toGetCoupon(List<MyCouponListBean> list) {
        //设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                int coupon_id = list.get(position).getCoupon_id();
                toDoGetCoupon(coupon_id);
            }
        });
    }


    /**
     * 去领取优惠券
     *
     * @param coupon_id
     */
    private void toDoGetCoupon(int coupon_id) {
        HashMap map = new HashMap<>();
        map.put("coupon_id", coupon_id);
        RxHttp.postForm(MallUrl.GET_COUPON)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        getMyCouponList();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}