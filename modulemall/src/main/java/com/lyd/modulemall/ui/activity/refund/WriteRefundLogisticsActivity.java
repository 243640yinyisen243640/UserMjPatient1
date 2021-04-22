package com.lyd.modulemall.ui.activity.refund;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.BottomSingleChoiceLogisticsCompanyAdapter;
import com.lyd.modulemall.bean.LogisticsCompanyListBean;
import com.lyd.modulemall.bean.RefundOrderGoodsBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.ActivityWriteRefundLogisticsBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.view.popup.BottomSingleChoicePopup;
import com.rxjava.rxlife.RxLife;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import liys.click.AClickStr;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  填写退款物流
 * 作者: LYD
 * 创建日期: 2021/1/19 14:05
 */
public class WriteRefundLogisticsActivity extends BaseViewBindingActivity<ActivityWriteRefundLogisticsBinding> implements View.OnClickListener {
    private String companyName;
    private String companyType;
    private BottomSingleChoicePopup bottomSingleChoicePopup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_refund_logistics;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("填写退货物流");
        getGoodsInfo();
    }


    private void getGoodsInfo() {
        int order_goods_id = getIntent().getIntExtra("order_goods_id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        RxHttp.postForm(MallUrl.REFUND_ORDER_GOODS)
                .addAll(map)
                .asResponse(RefundOrderGoodsBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<RefundOrderGoodsBean>() {
                    @Override
                    public void accept(RefundOrderGoodsBean data) throws Exception {
                        String goods_name = data.getGoods_name();
                        String goods_img_url = data.getGoods_img_url();
                        String sku_name = data.getSku_name();
                        Glide.with(Utils.getApp()).load(goods_img_url).into(binding.imgPic);
                        binding.tvProductName.setText(goods_name);
                        binding.tvSpecification.setText(sku_name);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @AClickStr({"ll_company", "bt_submit"})
    public void click(View view, String viewId) {
        switch (viewId) {
            case "ll_company":
                String number = binding.etNumber.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    ToastUtils.showShort("请输入物流单号");
                    return;
                }
                getCompanyFromInputNumber(number);
                break;
            case "bt_submit":
                toSubmitLogistics();
                break;
        }
    }

    private void toSubmitLogistics() {
        String number = binding.etNumber.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            ToastUtils.showShort("请输入物流单号");
            return;
        }
        String company = binding.tvCompany.getText().toString().trim();
        if (TextUtils.isEmpty(company)) {
            ToastUtils.showShort("请选择物流公司");
            return;
        }
        String tel = binding.etTel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            ToastUtils.showShort("请输入联系电话");
            return;
        }
        if (!RegexUtils.isMobileSimple(tel)) {
            ToastUtils.showShort("请输入合法的联系电话");
            return;
        }
        toDoSubmitLogistics();
    }

    private void toDoSubmitLogistics() {
        int order_goods_id = getIntent().getIntExtra("order_goods_id", 0);
        String number = binding.etNumber.getText().toString().trim();
        String tel = binding.etTel.getText().toString().trim();
        HashMap map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        map.put("refund_shipping_no", number);
        map.put("refund_mobile", tel);
        map.put("refund_express_name", companyName);
        map.put("refund_express_type", companyType);
        RxHttp.postForm(MallUrl.ADD_LOGISTICS)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        finish();
                        EventBusUtils.postSticky(new EventMessage<>(MallConstantParam.REFUND_CANCEL));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void getCompanyFromInputNumber(String number) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("refund_shipping_no", number);
        RxHttp.postForm(MallUrl.LOGISTICS_COMPANY)
                .addAll(map)
                .asResponse(LogisticsCompanyListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<LogisticsCompanyListBean>() {
                    @Override
                    public void accept(LogisticsCompanyListBean myOrderListBeans) throws Exception {
                        List<LogisticsCompanyListBean.LogisticsBean> logistics = myOrderListBeans.getLogistics();
                        showBottomPopup(logistics);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    private void showBottomPopup(List<LogisticsCompanyListBean.LogisticsBean> logistics) {
        bottomSingleChoicePopup = new BottomSingleChoicePopup(getPageContext());
        TextView tvTitle = bottomSingleChoicePopup.findViewById(R.id.tv_title);
        tvTitle.setText("物流公司");
        TextView tvDesc = bottomSingleChoicePopup.findViewById(R.id.tv_desc);
        tvDesc.setText("请选择物流公司");
        //设置Rv
        RecyclerView rvCheckList = bottomSingleChoicePopup.findViewById(R.id.rv_check_list);
        //注册选择器
        SingleCheckHelper singleCheckHelper = new SingleCheckHelper();
        singleCheckHelper.register(LogisticsCompanyListBean.LogisticsBean.class, new CheckHelper.Checker<LogisticsCompanyListBean.LogisticsBean, BaseViewHolder>() {
            @Override
            public void check(LogisticsCompanyListBean.LogisticsBean bean, BaseViewHolder holder) {
                companyName = bean.getName();
                companyType = bean.getType();
                CheckBox cbCheck = holder.getView(R.id.cb_check);
                cbCheck.setChecked(true);
            }

            @Override
            public void unCheck(LogisticsCompanyListBean.LogisticsBean bean, BaseViewHolder holder) {
                CheckBox cbCheck = holder.getView(R.id.cb_check);
                cbCheck.setChecked(false);
            }
        });
        BottomSingleChoiceLogisticsCompanyAdapter bottomSingleChoiceStringAdapter = new BottomSingleChoiceLogisticsCompanyAdapter(logistics, singleCheckHelper);
        rvCheckList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvCheckList.setAdapter(bottomSingleChoiceStringAdapter);
        //
        TextView tvSure = bottomSingleChoicePopup.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(this);
        bottomSingleChoicePopup.showPopupWindow();
    }

    @Override
    public void onClick(View v) {
        bottomSingleChoicePopup.dismiss();
        binding.tvCompany.setText(companyName);
    }
}