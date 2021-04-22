package com.lyd.modulemall.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.utils.MoneyUtils;
import com.lyd.baselib.utils.TurnsUtils;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.BottomSingleChoiceChooseCouponAdapter;
import com.lyd.modulemall.adapter.ConfirmOrderProductListAdapter;
import com.lyd.modulemall.bean.ConfirmOrderBean;
import com.lyd.modulemall.databinding.ActivityConfirmOrderBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.activity.address.ShoppingAddressListActivity;
import com.lyd.modulemall.view.popup.BottomSingleChoicePopup;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import liys.click.AClickStr;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 确认订单
 * 作者: LYD
 * 创建日期: 2021/1/27 19:02
 */
public class ConfirmOrderActivity extends BaseViewBindingActivity<ActivityConfirmOrderBinding> implements View.OnClickListener {
    private static final int TO_SELECT_ADDRESS = 10010;
    private static final String TAG = "ConfirmOrderActivity";
    private String goods_id;
    private String goods_sku_id;
    private String num;
    private String order_tag;
    private String cart_ids;
    private BottomSingleChoicePopup bottomSingleChoicePopup;
    private RecyclerView rvCheckList;
    private TextView tvSure;

    private int coupon_id;
    private String coupon_money;
    private String at_least;
    private String addressId = null;
    private double goods_total_money;
    private List<ConfirmOrderBean.CouponListBean> coupon_list;
    private ConfirmOrderBean.UserdefaultaddressBean addressBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("确认订单");
        getIntentData();
        initPopup();
        getConfirmDetail();
    }

    private void getIntentData() {
        order_tag = getIntent().getExtras().getString("order_tag");
        Log.e(TAG, "order_tag==" + order_tag);
        if ("cart".equals(order_tag)) {
            //购物车下单
            cart_ids = getIntent().getExtras().getString("cart_ids");
            Log.e(TAG, "cart_ids==" + cart_ids);
        } else {
            //直接购买
            goods_id = getIntent().getExtras().getString("goods_id");
            Log.e(TAG, "goods_id==" + goods_id);
            goods_sku_id = getIntent().getExtras().getString("goods_sku_id");
            Log.e(TAG, "goods_sku_id==" + goods_sku_id);
            num = getIntent().getExtras().getString("num");
            Log.e(TAG, "num==" + num);
        }
    }

    private void initPopup() {
        bottomSingleChoicePopup = new BottomSingleChoicePopup(getPageContext());
        rvCheckList = bottomSingleChoicePopup.findViewById(R.id.rv_check_list);
        tvSure = bottomSingleChoicePopup.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(this);
    }


    private void getConfirmDetail() {
        HashMap map = new HashMap<>();
        map.put("order_tag", order_tag);
        if ("cart".equals(order_tag)) {
            //购物车下单
            map.put("cart_ids", cart_ids);
        } else {
            //直接购买
            map.put("goods_sku_id", goods_sku_id);
            map.put("goods_id", goods_id);
            map.put("num", num);
        }
        if (!TextUtils.isEmpty(addressId)) {
            map.put("id", addressId);
        }
        RxHttp.postForm(MallUrl.GET_CONFIRM_ORDER_DETAIL)
                .addAll(map)
                .asResponse(ConfirmOrderBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ConfirmOrderBean>() {
                    @Override
                    public void accept(ConfirmOrderBean data) throws Exception {
                        //设置收货信息
                        addressBean = data.getUserdefaultaddress();
                        if (addressBean != null) {
                            addressId = addressBean.getId() + "";
                            String receiver_name = addressBean.getReceiver_name();
                            String receiver_mobile = addressBean.getReceiver_mobile();
                            String province = addressBean.getProvince();
                            String city = addressBean.getCity();
                            String area = addressBean.getArea();
                            String receiver_address = addressBean.getReceiver_address();
                            binding.tvPersonName.setText(receiver_name);
                            binding.tvPersonTel.setText(receiver_mobile);
                            binding.tvPersonAddress.setText(province + city + area + receiver_address);
                            binding.llAddress.setVisibility(View.VISIBLE);
                            binding.llAddressEmpty.setVisibility(View.GONE);
                        } else {
                            binding.llAddress.setVisibility(View.GONE);
                            binding.llAddressEmpty.setVisibility(View.VISIBLE);
                        }
                        //设置商品信息
                        List<ConfirmOrderBean.GoodsDetailBean> goods_detail = data.getGoods_detail();
                        ConfirmOrderProductListAdapter adapter = new ConfirmOrderProductListAdapter(goods_detail);
                        binding.rvGoodsList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        binding.rvGoodsList.setAdapter(adapter);
                        //设置其它信息
                        double fee = data.getFee_detail().getFee();
                        Log.e(TAG, "邮费==" + fee);
                        binding.tvFeeDetail.setText(MoneyUtils.formatPrice(fee));
                        String goods_nums = data.getGoods_nums();
                        goods_total_money = data.getGoods_total_money();
                        binding.tvProductAllCount.setText("共" + goods_nums + "件");
                        binding.tvProductAllMoney.setText(MoneyUtils.formatPrice(goods_total_money));
                        binding.tvBottomAllCount.setText("共" + goods_nums + "件");
                        binding.tvBottomAllMoney.setText(MoneyUtils.formatPrice(goods_total_money));
                        //设置优惠券
                        coupon_list = data.getCoupon_list();
                        if (coupon_list != null && coupon_list.size() > 0) {
                            binding.tvCouponText.setText("请选择优惠券");
                            //注册选择器
                            SingleCheckHelper singleCheckHelper = new SingleCheckHelper();
                            singleCheckHelper.register(ConfirmOrderBean.CouponListBean.class, new CheckHelper.Checker<ConfirmOrderBean.CouponListBean, BaseViewHolder>() {
                                @Override
                                public void check(ConfirmOrderBean.CouponListBean bean, BaseViewHolder holder) {
                                    CheckBox cbCheck = holder.getView(R.id.cb_check);
                                    cbCheck.setChecked(true);
                                    coupon_id = bean.getCouon_user_id();
                                    coupon_money = bean.getCoupon_money();
                                    at_least = bean.getAt_least();
                                    Log.e("TAG", "check: " + coupon_id);
                                    Log.e("TAG", "check: " + coupon_money);
                                }

                                @Override
                                public void unCheck(ConfirmOrderBean.CouponListBean bean, BaseViewHolder holder) {
                                    CheckBox cbCheck = holder.getView(R.id.cb_check);
                                    cbCheck.setChecked(false);
                                }
                            });
                            BottomSingleChoiceChooseCouponAdapter productLabelListAdapter = new BottomSingleChoiceChooseCouponAdapter(coupon_list, singleCheckHelper);
                            rvCheckList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvCheckList.setAdapter(productLabelListAdapter);
                        } else {
                            binding.tvCouponText.setText("暂无可用优惠券");
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @AClickStr({"ll_address_empty", "ll_address", "ll_coupon", "rl_bottom_pay"})
    public void click(View view, String viewId) {
        Intent intent;
        switch (viewId) {
            case "ll_address_empty":
            case "ll_address":
                intent = new Intent(getPageContext(), ShoppingAddressListActivity.class);
                startActivityForResult(intent, TO_SELECT_ADDRESS);
                break;
            case "ll_coupon":
                if (coupon_list != null && coupon_list.size() > 0) {
                    bottomSingleChoicePopup.showPopupWindow();
                }
                break;
            case "rl_bottom_pay":
                if (addressBean != null) {
                    String leaveMessage = binding.etOrderLeaveMessage.getText().toString().trim();
                    Bundle extras = getIntent().getExtras();
                    extras.putString("leave_message", leaveMessage);
                    extras.putString("couon_user_id", coupon_id + "");
                    extras.putString("addressId", addressId + "");
                    //区分
                    extras.putString("type", "1");
                    intent = new Intent(getPageContext(), OrderPayActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                } else {
                    ToastUtils.showShort("请选择收货地址");
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TO_SELECT_ADDRESS:
                    addressId = data.getIntExtra("id", 0) + "";
                    getConfirmDetail();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sure) {
            double allMoney = goods_total_money - TurnsUtils.getDouble(coupon_money, 0);
            binding.tvProductAllMoney.setText(MoneyUtils.formatPrice(allMoney));
            binding.tvBottomAllMoney.setText(MoneyUtils.formatPrice(allMoney));
            bottomSingleChoicePopup.dismiss();
            binding.tvCouponText.setText("省" + coupon_money + "元:  " + "满" + at_least + "-" + coupon_money);
        }
    }
}