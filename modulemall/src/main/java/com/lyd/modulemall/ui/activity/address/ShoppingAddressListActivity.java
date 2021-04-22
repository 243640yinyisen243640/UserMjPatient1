package com.lyd.modulemall.ui.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.ShoppingAddressListAdapter;
import com.lyd.modulemall.bean.ShoppingAddressListBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.ActivityShoppingAddressListBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  收货地址列表页面
 * 作者: LYD
 * 创建日期: 2021/1/4 13:47
 */
@BindEventBus
public class ShoppingAddressListActivity extends BaseViewBindingActivity<ActivityShoppingAddressListBinding> implements View.OnClickListener {
    private ShoppingAddressListAdapter addressListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_address_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("收货地址");
        binding.tvAdd.setOnClickListener(this);
        getAddressList();
    }

    /**
     *
     */
    private void getAddressList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(MallUrl.GET_ADDRESS_LIST)
                .addAll(map)
                .asResponseList(ShoppingAddressListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<ShoppingAddressListBean>>() {
                    @Override
                    public void accept(List<ShoppingAddressListBean> list) throws Exception {
                        binding.rvList.setVisibility(View.VISIBLE);
                        binding.rlEmpty.setVisibility(View.GONE);
                        binding.rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        addressListAdapter = new ShoppingAddressListAdapter(list);
                        binding.rvList.setAdapter(addressListAdapter);
                        addressListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                                int id = list.get(position).getId();
                                toDoDel(id);
                                return false;
                            }
                        });
                        addressListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                int id = list.get(position).getId();
                                Intent intent = getIntent();
                                intent.putExtra("id", id);
                                intent.putExtra("id", id);
                                intent.putExtra("id", id);
                                intent.putExtra("id", id);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        binding.rvList.setVisibility(View.GONE);
                        binding.rlEmpty.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void toDoDel(int id) {
        HashMap map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(MallUrl.DEL_ADDRESS)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        getAddressList();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_add) {
            Intent intent = new Intent(getPageContext(), AddressAddOrEditActivity.class);
            intent.putExtra("type", "add");
            startActivity(intent);
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case MallConstantParam.ADD_ADDRESS_REFRESH:
                getAddressList();
                break;
        }
    }
}