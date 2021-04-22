package com.lyd.modulemall.adapter.refunddetail;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

public class RefundDetailAdapter extends MultipleItemRvAdapter<RefundDetailMultipleEntity, BaseViewHolder> {
    public static final int TYPE_STATE_ONE = 100;
    public static final int TYPE_STATE_TWO = 200;
    public static final int TYPE_MONEY = 300;
    public static final int TYPE_RECEIVE_PRODUCT = 400;
    public static final int TYPE_PRODUCT_DETAIL= 500;

    private Context context;
    public RefundDetailAdapter(@Nullable List<RefundDetailMultipleEntity> data,Context context) {
        super(data);
        this.context = context;
        //构造函数若有传其他参数可以在调用finishInitialize()之前进行赋值，赋值给全局变量
        //这样getViewType()和registerItemProvider()方法中可以获取到传过来的值
        //getViewType()中可能因为某些业务逻辑，需要将某个值传递过来进行判断，返回对应的viewType
        //registerItemProvider()中可以将值传递给ItemProvider

        //If the constructor has other parameters, it needs to be assigned before calling finishInitialize() and assigned to the global variable
        // This getViewType () and registerItemProvider () method can get the value passed over
        // getViewType () may be due to some business logic, you need to pass a value to judge, return the corresponding viewType
        //RegisterItemProvider() can pass value to ItemProvider
        finishInitialize();
    }

    @Override
    protected int getViewType(RefundDetailMultipleEntity entity) {
        //根据实体类判断并返回对应的viewType，具体判断逻辑因业务不同，这里这是简单通过判断type属性
        //According to the entity class to determine and return the corresponding viewType,
        //the specific judgment logic is different because of the business, here is simply by judging the type attribute
        if (entity.type == RefundDetailMultipleEntity.STATE_ONE) {
            return TYPE_STATE_ONE;
        } else if (entity.type == RefundDetailMultipleEntity.STATE_TWO) {
            return TYPE_STATE_TWO;
        } else if (entity.type == RefundDetailMultipleEntity.MONEY) {
            return TYPE_MONEY;
        } else if (entity.type == RefundDetailMultipleEntity.RECEIVE_PRODUCT) {
            return TYPE_RECEIVE_PRODUCT;
        } else if (entity.type == RefundDetailMultipleEntity.PRODUCT_DETAIL) {
            return TYPE_PRODUCT_DETAIL;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册相关的条目provider
        //Register related entries provider
        mProviderDelegate.registerProvider(new StateOneItemProvider(context));
        mProviderDelegate.registerProvider(new StateTwoItemProvider());
        mProviderDelegate.registerProvider(new StateMoneyItemProvider());
        mProviderDelegate.registerProvider(new StateRefundDetailReceiveProductItemProvider());
        mProviderDelegate.registerProvider(new StateProductDetailItemProvider());
    }
}
