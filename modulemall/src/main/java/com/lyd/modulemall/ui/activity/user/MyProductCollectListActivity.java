package com.lyd.modulemall.ui.activity.user;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.MyProductCollectListAdapter;
import com.lyd.modulemall.bean.MyProductCollectListBean;
import com.lyd.modulemall.databinding.ActivityMyProductCollectListBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.utils.RxHttpPublicParamsAddUtils;
import com.lyd.modulemall.view.popup.BottomAddressSelectPopup;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  我的商品收藏页面
 * 作者: LYD
 * 创建日期: 2020/12/24 16:02
 */
public class MyProductCollectListActivity extends BaseViewBindingActivity<ActivityMyProductCollectListBinding> {

    //分页开始
    private MyProductCollectListAdapter adapter;
    //总数据
    private List<MyProductCollectListBean> list;
    //上拉加载数据
    private List<MyProductCollectListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_product_collect_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        RxHttpPublicParamsAddUtils.initRxHttp();
        setTitle("我的收藏");
        getMyCollectList();
        initRefresh();

    }

    private void initRefresh() {
        //刷新开始
        binding.srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                binding.srlList.finishRefresh(2000);
                pageIndex = 2;
                getMyCollectList();
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


    private void getMoreData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        RxHttp.postForm(MallUrl.GET_GOODS_COLLECTION_LIST)
                .addAll(map)
                .asResponseList(MyProductCollectListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyProductCollectListBean>>() {
                    @Override
                    public void accept(List<MyProductCollectListBean> myCouponListBean) throws Exception {
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

    private void getMyCollectList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", "1");
        RxHttp.postForm(MallUrl.GET_GOODS_COLLECTION_LIST)
                .addAll(map)
                .asResponseList(MyProductCollectListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyProductCollectListBean>>() {
                    @Override
                    public void accept(List<MyProductCollectListBean> myCouponListBean) throws Exception {
                        list = myCouponListBean;
                        adapter = new MyProductCollectListAdapter(list);
                        binding.rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        binding.rvList.setAdapter(adapter);
                        toCancelCollect(list);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }


    /**
     * 取消收藏
     *
     * @param list
     */
    private void toCancelCollect(List<MyProductCollectListBean> list) {
        //设置点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                int goods_id = list.get(position).getGoods_id();
                toDoCancel(goods_id);
            }
        });
    }

    /**
     * 收藏状态 1已收藏 2未收藏
     *
     * @param goods_id
     */
    private void toDoCancel(int goods_id) {
        HashMap map = new HashMap<>();
        map.put("goods_id", goods_id);
        map.put("collect_type", "1");
        RxHttp.postForm(MallUrl.EDIT_GOODS_COLLECTION)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        getMyCollectList();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}