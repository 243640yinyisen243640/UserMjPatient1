package com.lyd.modulemall.ui.activity.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.MultiCheckHelper;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.MoneyUtils;
import com.lyd.baselib.utils.TurnsUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.ShoppingCartErrorAdapter;
import com.lyd.modulemall.adapter.ShoppingCartNormalAdapter;
import com.lyd.modulemall.bean.ShoppingCartListBean;
import com.lyd.modulemall.databinding.ActivityShoppingCartBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.activity.pay.ConfirmOrderActivity;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.functions.Consumer;
import liys.click.AClickStr;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 购物车
 * 作者: LYD
 * 创建日期: 2021/2/1 10:03
 */
public class ShoppingCartActivity extends BaseViewBindingActivity<ActivityShoppingCartBinding> {
    private static final String TAG = "ShoppingCartActivity";
    private MultiCheckHelper mCheckHelper = new MultiCheckHelper();
    private List<ShoppingCartListBean.NormalCartBean> listNormal;
    private List<ShoppingCartListBean.NormalCartBean> listError;
    private ShoppingCartNormalAdapter shoppingCartNormalAdapter;
    private ShoppingCartErrorAdapter shoppingCartErrorAdapter;

    private HashMap<Integer, Integer> cartIdsHashMap = new HashMap<>();
    //购物车ID (多个用逗号分隔)
    private String cartIds = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        hideTitleBar();
        getShoppingCartList();
    }

    private void getShoppingCartList() {
        HashMap map = new HashMap<>();
        RxHttp.postForm(MallUrl.GET_SHOPPING_CART_LIST)
                .addAll(map)
                .asResponse(ShoppingCartListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ShoppingCartListBean>() {
                    @Override
                    public void accept(ShoppingCartListBean data) throws Exception {
                        binding.nsvHaveData.setVisibility(View.VISIBLE);
                        binding.llEmpty.setVisibility(View.GONE);
                        binding.llBottom.setVisibility(View.VISIBLE);
                        listNormal = data.getNormal_cart();
                        listError = data.getDisabled_cart();
                        setNormalList();
                        setErrorList();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        binding.nsvHaveData.setVisibility(View.GONE);
                        binding.llEmpty.setVisibility(View.VISIBLE);
                        binding.llBottom.setVisibility(View.GONE);
                        binding.tvComplete.setVisibility(View.GONE);
                        binding.tvManage.setVisibility(View.GONE);
                    }
                });
    }

    private void setNormalList() {
        //注册选择器
        mCheckHelper.register(ShoppingCartListBean.NormalCartBean.class, new CheckHelper.Checker<ShoppingCartListBean.NormalCartBean, BaseViewHolder>() {
            @Override
            public void check(ShoppingCartListBean.NormalCartBean bean, BaseViewHolder holder) {
                Log.e(TAG, "选中监听执行");
                holder.setImageResource(R.id.img_check, R.drawable.shopping_cart_checked);
                //添加选中
                int position = holder.getLayoutPosition();
                int cart_id = bean.getCart_id();
                cartIdsHashMap.put(position, cart_id);
                if (cartIdsHashMap.size() == listNormal.size()) {
                    binding.imgAllCheck.setImageResource(R.drawable.shopping_cart_checked);
                } else {
                    binding.imgAllCheck.setImageResource(R.drawable.shopping_cart_uncheck);
                }
                //计算价格
                calculateTotal();
            }

            @Override
            public void unCheck(ShoppingCartListBean.NormalCartBean bean, BaseViewHolder holder) {
                Log.e(TAG, "反选监听执行");
                holder.setImageResource(R.id.img_check, R.drawable.shopping_cart_uncheck);
                //删除选中
                int position = holder.getLayoutPosition();
                cartIdsHashMap.remove(position);
                if (cartIdsHashMap.size() == listNormal.size()) {
                    binding.imgAllCheck.setImageResource(R.drawable.shopping_cart_checked);
                } else {
                    binding.imgAllCheck.setImageResource(R.drawable.shopping_cart_uncheck);
                }
                //计算价格
                calculateTotal();
            }
        });
        shoppingCartNormalAdapter = new ShoppingCartNormalAdapter(listNormal, mCheckHelper);
        binding.rvNormalProductList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        binding.rvNormalProductList.setAdapter(shoppingCartNormalAdapter);

        shoppingCartNormalAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int id = view.getId();
                int cart_id = listNormal.get(position).getCart_id();
                int num = listNormal.get(position).getNum();
                if (id == R.id.tv_minus) {
                    if (1 == num) {
                        ToastUtils.showShort("该宝贝不能减少了呦");
                    } else {
                        toEditShoppingCart("1", cart_id);
                    }
                } else if (id == R.id.tv_plus) {
                    toEditShoppingCart("3", cart_id);
                }
            }
        });
        shoppingCartNormalAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                int cart_id = listNormal.get(position).getCart_id();
                toDelNormalProduct(cart_id + "");
                return false;
            }
        });
    }

    /**
     * 计算总价和总数量
     */
    private void calculateTotal() {
        if (cartIdsHashMap != null && cartIdsHashMap.size() > 0) {
            double allTotalPrice = 0.00;
            int allTotalCount = 0;
            for (Map.Entry<Integer, Integer> entry : cartIdsHashMap.entrySet()) {
                Integer value = entry.getKey();
                ShoppingCartListBean.NormalCartBean normalCartBean = listNormal.get(value);
                String price = normalCartBean.getPrice();
                double priceDouble = TurnsUtils.getDouble(price, 0);
                int num = normalCartBean.getNum();
                allTotalPrice += priceDouble * num;
                allTotalCount += num;
            }
            binding.tvShoppingCartAllCount.setText("结算(" + allTotalCount + ")");
            binding.tvShoppingCartAllMoney.setText("合计:" + MoneyUtils.formatPrice(allTotalPrice));
        } else {
            binding.tvShoppingCartAllCount.setText("结算");
            binding.tvShoppingCartAllMoney.setText("合计:" + "0.00");
        }
    }

    private void toEditShoppingCart(String type, int cart_id) {
        HashMap map = new HashMap<>();
        map.put("cart_id", cart_id);
        map.put("type", type);
        map.put("num", "1");
        RxHttp.postForm(MallUrl.EDIT_SHOPPING_CART_PRODUCT_COUNT)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        getShoppingCartList();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void setErrorList() {
        if (listError != null && listError.size() > 0) {
            binding.llError.setVisibility(View.VISIBLE);
            shoppingCartErrorAdapter = new ShoppingCartErrorAdapter(listError);
            binding.rvErrorProductList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            binding.rvErrorProductList.setAdapter(shoppingCartErrorAdapter);
        } else {
            binding.llError.setVisibility(View.GONE);
        }
    }

    @AClickStr({"img_back", "tv_complete", "tv_manage", "ll_all_check", "ll_bottom_pay", "tv_del_normal_product", "tv_clear_error_product", "tv_back_main"})
    public void click(View view, String viewId) {
        switch (viewId) {
            case "img_back":
                finish();
                break;
            case "tv_complete":
                setTvComplete();
                break;
            case "tv_manage":
                setTvManager();
                break;
            case "ll_all_check":
                if (mCheckHelper.isAllChecked(listNormal)) {
                    binding.imgAllCheck.setImageResource(R.drawable.shopping_cart_uncheck);
                    //取消全选
                    mCheckHelper.unCheckAll(shoppingCartNormalAdapter);
                } else {
                    binding.imgAllCheck.setImageResource(R.drawable.shopping_cart_checked);
                    //全选
                    mCheckHelper.checkAll(listNormal, shoppingCartNormalAdapter);
                }
                break;
            case "ll_bottom_pay":
                cartIds = "";
                getCartIdsFromCartIdsHashMap();
                Intent intent = new Intent(getPageContext(), ConfirmOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cart_ids", cartIds + "");
                bundle.putString("order_tag", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case "tv_del_normal_product":
                toDelNormalProduct("");
                break;
            case "tv_clear_error_product":
                toClearErrorProduct();
                break;
            case "tv_back_main":
                EventBusUtils.post(new EventMessage<>(BaseConstantParam.EventCode.MALL_FINISH_RO_MAIN_ACTIVITY));
                break;
        }
    }

    /**
     * //获取购物车id
     */
    private void getCartIdsFromCartIdsHashMap() {
        for (Map.Entry<Integer, Integer> entry : cartIdsHashMap.entrySet()) {
            Integer value = entry.getValue();
            cartIds += value + "";
            cartIds += ",";
        }
        //截取最后一个,
        if (cartIds.length() > 0) {
            cartIds = cartIds.substring(0, cartIds.length() - 1);
        }
    }

    private void toDelNormalProduct(String cartId) {
        HashMap map = new HashMap<>();
        if (TextUtils.isEmpty(cartId)) {
            cartIds = "";
            getCartIdsFromCartIdsHashMap();
            map.put("cart_ids", cartIds);
        } else {
            map.put("cart_ids", cartId);
        }
        RxHttp.postForm(MallUrl.DEL_SHOPPING_CART_PRODUCT)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        getShoppingCartList();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    private void setTvManager() {
        binding.tvManage.setVisibility(View.GONE);
        binding.tvComplete.setVisibility(View.VISIBLE);
        binding.llBottomPay.setVisibility(View.GONE);
        binding.tvDelNormalProduct.setVisibility(View.VISIBLE);
    }

    private void setTvComplete() {
        binding.tvManage.setVisibility(View.VISIBLE);
        binding.tvComplete.setVisibility(View.GONE);
        binding.llBottomPay.setVisibility(View.VISIBLE);
        binding.tvDelNormalProduct.setVisibility(View.GONE);
    }

    private void toClearErrorProduct() {
        String labelIds = "";
        for (int i = 0; i < listError.size(); i++) {
            labelIds += listError.get(i).getCart_id() + "";
            labelIds += ",";
        }
        //截取最后一个,
        if (labelIds.length() > 0) {
            labelIds = labelIds.substring(0, labelIds.length() - 1);
        }
        HashMap map = new HashMap<>();
        map.put("cart_ids", labelIds);
        RxHttp.postForm(MallUrl.DEL_SHOPPING_CART_PRODUCT)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        binding.llError.setVisibility(View.GONE);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}