package com.lyd.modulemall.ui.activity.refund;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.R;
import com.lyd.modulemall.adapter.refunddetail.RefundDetailAdapter;
import com.lyd.modulemall.adapter.refunddetail.RefundDetailMultipleEntity;
import com.lyd.modulemall.bean.RefundDetailBean;
import com.lyd.modulemall.constant.MallConstantParam;
import com.lyd.modulemall.databinding.ActivityRefundDetailBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.lyd.modulemall.ui.activity.order.MyOrderListActivity;
import com.rxjava.rxlife.RxLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  退款详情
 * 作者: LYD
 * 创建日期: 2021/1/18 15:00
 */
@BindEventBus
public class RefundDetailActivity extends BaseViewBindingActivity<ActivityRefundDetailBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("退款详情");
        getRefundDetail();
    }

    private void getRefundDetail() {
        int order_goods_id = getIntent().getIntExtra("order_goods_id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("order_goods_id", order_goods_id);
        RxHttp.postForm(MallUrl.REFUND_GOODS_DETAIL)
                .addAll(map)
                .asResponse(RefundDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<RefundDetailBean>() {
                    @Override
                    public void accept(RefundDetailBean data) throws Exception {
                        //服务类型
                        //1.我要退款无需退货
                        //2.我要退款退货 -注：用于是否需要填写物流信息及显示对应信息
                        int service_type = data.getService_type();
                        //退款状态
                        //1买家申请退货,等待卖家处理
                        //2.卖家同意（需要退货的发送退货地址）
                        //3.等待卖家收货
                        //4.退款成功
                        //5.拒绝退款
                        //6.买家撤销退款 (退款关闭)
                        int refund_status = data.getRefund_status();
                        List<RefundDetailMultipleEntity> list = new ArrayList<>();
                        switch (refund_status) {
                            case 1:
                            case 5:
                            case 6:
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.STATE_ONE, data));
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.PRODUCT_DETAIL, data));
                                break;
                            case 2:
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.STATE_ONE, data));
                                if (2 == service_type) {
                                    list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.RECEIVE_PRODUCT, data));
                                }
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.PRODUCT_DETAIL, data));
                                break;
                            case 3:
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.STATE_TWO, data));
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.PRODUCT_DETAIL, data));
                                break;
                            case 4:
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.STATE_ONE, data));
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.MONEY, data));
                                list.add(new RefundDetailMultipleEntity(RefundDetailMultipleEntity.PRODUCT_DETAIL, data));
                                break;
                        }
                        RefundDetailAdapter adapter = new RefundDetailAdapter(list, getPageContext());
                        binding.rvRefundDetail.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        binding.rvRefundDetail.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected void receiveStickyEvent(EventMessage event) {
        super.receiveStickyEvent(event);
        switch (event.getCode()) {
            case MallConstantParam.REFUND_CANCEL:
                getRefundDetail();
                break;
        }
    }


    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toDoFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toDoFinish() {
        EventBusUtils.post(new EventMessage<>(MallConstantParam.REFUND_SUBMIT_SUCCESS));
        ActivityUtils.finishToActivity(MyOrderListActivity.class, false);
        //finish();
    }

}