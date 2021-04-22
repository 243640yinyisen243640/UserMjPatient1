package com.lyd.modulemall.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.base.fragment.BaseViewBindingFragment;
import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.MyRefundOrderListAdapter;
import com.lyd.modulemall.bean.MyRefundOrderListBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.FragmentMyRefundOrderListBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.activity.refund.RefundDetailActivity;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

@BindEventBus
public class MyRefundOrderListFragment extends BaseViewBindingFragment<FragmentMyRefundOrderListBinding> {
    private static final String TAG = "MyOrderListActivity";
    //分页开始
    private MyRefundOrderListAdapter adapter;
    //总数据
    private List<MyRefundOrderListBean> list;
    //上拉加载数据
    private List<MyRefundOrderListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束
    private String currentFragmentPosition;
    private String keyWord = "";

    @Override
    protected void init() {
        initRefresh();
    }


    //    @Override
    //    public void onFragmentFirstVisible() {
    //        super.onFragmentFirstVisible();
    //    }

    private void initRefresh() {
        //刷新开始
        binding.srlMyOrderList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                binding.srlMyOrderList.finishRefresh(2000);
                pageIndex = 2;
                //刷新当前页面
                getOrderList();
            }
        });
        binding.srlMyOrderList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                binding.srlMyOrderList.finishLoadMore(2000);
                getMoreData();
            }
        });
        //刷新结束
    }

    private void getOrderList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", "1");
        map.put("key_name", keyWord);
        RxHttp.postForm(MallUrl.GET_REFUND_ORDER_LIST)
                .addAll(map)
                .asResponseList(MyRefundOrderListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyRefundOrderListBean>>() {
                    @Override
                    public void accept(List<MyRefundOrderListBean> myOrderListBeans) throws Exception {
                        binding.srlMyOrderList.setVisibility(View.VISIBLE);
                        binding.llEmpty.setVisibility(View.GONE);
                        list = myOrderListBeans;
                        adapter = new MyRefundOrderListAdapter(list);
                        binding.rvMyOrderList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        binding.rvMyOrderList.setAdapter(adapter);
                        setOnItemChildClickListener();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        ToastUtils.cancel();
                        binding.srlMyOrderList.setVisibility(View.GONE);
                        binding.llEmpty.setVisibility(View.VISIBLE);
                    }
                });
    }


    private void getMoreData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("key_name", keyWord);
        RxHttp.postForm(MallUrl.GET_REFUND_ORDER_LIST)
                .addAll(map)
                .asResponseList(MyRefundOrderListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyRefundOrderListBean>>() {
                    @Override
                    public void accept(List<MyRefundOrderListBean> myOrderListBeans) throws Exception {
                        tempList = myOrderListBeans;
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

    private void setOnItemChildClickListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int order_goods_id = list.get(position).getOrder_goods_id();
                int id = view.getId();
                if (id == R.id.tv_left) {
                    toOperateOrder(order_goods_id);
                } else if (id == R.id.tv_right) {
                    Intent intent = new Intent(getPageContext(), RefundDetailActivity.class);
                    intent.putExtra("order_goods_id", order_goods_id);
                    startActivity(intent);
                }
            }
        });
    }


    private void toOperateOrder(int order_goods_id) {
        HashMap map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        RxHttp.postForm(MallUrl.DEL_REFUND_GOODS)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        getOrderList();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case BaseConstantParam.EventCode.MALL_ORDER_SEARCH:
                String currentPositionSearch = event.getMsg();
                keyWord = (String) event.getData();
                if (currentFragmentPosition.equals(currentPositionSearch)) {
                    KeyboardUtils.hideSoftInput(binding.rvMyOrderList);
                    getOrderList();
                }
                break;
            case MallConstantParam.REFUND_SUBMIT_SUCCESS:
                getOrderList();
                break;
        }
    }

    @Override
    public void onLazyLoad() {
        currentFragmentPosition = getArguments().getString("position");
        Log.e(TAG, "退款position==" + currentFragmentPosition);
        getOrderList();
    }
}
