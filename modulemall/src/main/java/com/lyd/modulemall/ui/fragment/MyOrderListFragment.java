package com.lyd.modulemall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.lyd.baselib.base.fragment.BaseViewBindingFragment;
import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.BottomSingleChoiceStringAdapter;
import com.lyd.modulemall.adapter.MyOrderListAdapter;
import com.lyd.modulemall.bean.MyOrderListBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.FragmentMyOrderListBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.activity.address.ChangeAddressActivity;
import com.lyd.modulemall.ui.activity.order.LogisticsInfoActivity;
import com.lyd.modulemall.ui.activity.pay.OrderPayActivity;
import com.lyd.modulemall.view.popup.BottomSingleChoicePopup;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

@BindEventBus
public class MyOrderListFragment extends BaseViewBindingFragment<FragmentMyOrderListBinding> implements View.OnClickListener {
    //分页开始
    private MyOrderListAdapter adapter;
    //总数据
    private List<MyOrderListBean> list;
    //上拉加载数据
    private List<MyOrderListBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束
    private int order_status;
    private BottomSingleChoicePopup bottomSingleChoicePopup;
    private int order_id;
    private String cancel_remark;

    private String currentFragmentPosition;
    private String keyWord = "";

    @Override
    protected void init() {
        initRefresh();
    }


    //    @Override
    //    public void onFragmentFirstVisible() {
    //        super.onFragmentFirstVisible();
    //
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
        map.put("order_status", order_status);
        map.put("key_name", keyWord);
        RxHttp.postForm(MallUrl.GET_ORDER_LIST)
                .addAll(map)
                .asResponseList(MyOrderListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyOrderListBean>>() {
                    @Override
                    public void accept(List<MyOrderListBean> myOrderListBeans) throws Exception {
                        binding.srlMyOrderList.setVisibility(View.VISIBLE);
                        binding.llEmpty.setVisibility(View.GONE);
                        list = myOrderListBeans;
                        adapter = new MyOrderListAdapter(list);
                        binding.rvMyOrderList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        binding.rvMyOrderList.setAdapter(adapter);
                        setOnItemChildClickListener();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        ToastUtils.cancel();
                        binding.llEmpty.setVisibility(View.VISIBLE);
                        binding.srlMyOrderList.setVisibility(View.GONE);
                    }
                });
    }


    private void getMoreData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("order_status", order_status);
        map.put("key_name", keyWord);
        RxHttp.postForm(MallUrl.GET_ORDER_LIST)
                .addAll(map)
                .asResponseList(MyOrderListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MyOrderListBean>>() {
                    @Override
                    public void accept(List<MyOrderListBean> myOrderListBeans) throws Exception {
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
                order_id = list.get(position).getOrder_id();
                ColorTextView tvText = (ColorTextView) view;
                String text = tvText.getText().toString().trim();
                if (text.equals(getString(R.string.order_change_address))) {
                    //收货地址Id
                    String user_address_id = list.get(position).getUser_address_id();
                    Intent intent = new Intent(getPageContext(), ChangeAddressActivity.class);
                    intent.putExtra("user_address_id", user_address_id);
                    intent.putExtra("order_id", order_id);
                    startActivity(intent);
                } else if (text.equals(getString(R.string.order_cancel))) {
                    toCancelOrder();
                } else if (text.equals(getString(R.string.order_pay))) {
                    Intent intent = new Intent(getPageContext(), OrderPayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "2");
                    bundle.putString("order_id", order_id + "");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (text.equals(getString(R.string.order_add_cart))) {
                    toAddCart();
                } else if (text.equals(getString(R.string.order_check_the_logistics))) {
                    Intent intent = new Intent(getPageContext(), LogisticsInfoActivity.class);
                    intent.putExtra("order_id", order_id + "");
                    startActivity(intent);
                } else if (text.equals(getString(R.string.order_confirm))) {
                    toOperateOrder("confirm");
                } else if (text.equals(getString(R.string.order_del))) {
                    toOperateOrder("del");
                }
            }
        });
    }


    /**
     * 加入购物车
     */
    private void toAddCart() {
        HashMap map = new HashMap<>();
        map.put("order_id", order_id);
        RxHttp.postForm(MallUrl.ORDER_ADD_CART)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        ToastUtils.showShort(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    /**
     * 订单取消
     */
    private void toCancelOrder() {
        bottomSingleChoicePopup = new BottomSingleChoicePopup(getPageContext());
        TextView tvTitle = bottomSingleChoicePopup.findViewById(R.id.tv_title);
        tvTitle.setText("订单取消");
        TextView tvDesc = bottomSingleChoicePopup.findViewById(R.id.tv_desc);
        tvDesc.setText("请选择取消原因");
        //设置Rv
        RecyclerView rvCheckList = bottomSingleChoicePopup.findViewById(R.id.rv_check_list);
        String[] str = getResources().getStringArray(R.array.bottom_popup_cancel_order_and_refund_reason);
        List<String> list = Arrays.asList(str);
        //注册选择器
        SingleCheckHelper singleCheckHelper = new SingleCheckHelper();
        singleCheckHelper.register(String.class, new CheckHelper.Checker<String, BaseViewHolder>() {
            @Override
            public void check(String bean, BaseViewHolder holder) {
                cancel_remark = bean;
                CheckBox cbCheck = holder.getView(R.id.cb_check);
                cbCheck.setChecked(true);
            }

            @Override
            public void unCheck(String bean, BaseViewHolder holder) {
                CheckBox cbCheck = holder.getView(R.id.cb_check);
                cbCheck.setChecked(false);
            }
        });
        BottomSingleChoiceStringAdapter bottomSingleChoiceStringAdapter = new BottomSingleChoiceStringAdapter(list, singleCheckHelper);
        rvCheckList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvCheckList.setAdapter(bottomSingleChoiceStringAdapter);
        //
        TextView tvSure = bottomSingleChoicePopup.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(this);
        bottomSingleChoicePopup.showPopupWindow();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sure) {
            toOperateOrder("cancel");
            bottomSingleChoicePopup.dismiss();
        }
    }

    /**
     * 订单操作
     *
     * @param type 1.cancel
     *             2.del
     *             3.confirm
     */
    private void toOperateOrder(String type) {
        HashMap map = new HashMap<>();
        String url = "";
        if ("cancel".equals(type)) {
            url = MallUrl.CANCEL_ORDER;
            map.put("cancel_remark", cancel_remark);
        } else if ("del".equals(type)) {
            url = MallUrl.DEL_ORDER;
        } else if ("confirm".equals(type)) {
            url = MallUrl.CONFIRM_ORDER;
        }
        map.put("order_id", order_id);
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String msg) throws Exception {
                        ToastUtils.showShort(msg);
                        getOrderList();
                        //EventBusUtils.post(new EventMessage<>(MallConstantParam.CONFIRM_ORDER));
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
            case MallConstantParam.CONFIRM_ORDER:
                //getOrderList();
                break;

        }
    }

    @Override
    public void onLazyLoad() {
        currentFragmentPosition = getArguments().getString("position");
        order_status = getArguments().getInt("order_status");
        getOrderList();

    }
}
