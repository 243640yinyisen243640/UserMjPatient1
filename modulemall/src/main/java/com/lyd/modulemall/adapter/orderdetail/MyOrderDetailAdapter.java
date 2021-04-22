package com.lyd.modulemall.adapter.orderdetail;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

public class MyOrderDetailAdapter extends MultipleItemRvAdapter<OrderDetailMultipleEntity, BaseViewHolder> {
    public static final int TYPE_STATE_YES = 100;
    public static final int TYPE_STATE_NO = 200;
    public static final int TYPE_LOGISTICS = 300;
    public static final int TYPE_RECEIVE_PRODUCT = 400;
    public static final int TYPE_PRODUCT_DESC = 500;
    public static final int TYPE_ORDER_DETAIL = 600;

    public MyOrderDetailAdapter(@Nullable List<OrderDetailMultipleEntity> data) {
        super(data);
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
    protected int getViewType(OrderDetailMultipleEntity entity) {
        //根据实体类判断并返回对应的viewType，具体判断逻辑因业务不同，这里这是简单通过判断type属性
        //According to the entity class to determine and return the corresponding viewType,
        //the specific judgment logic is different because of the business, here is simply by judging the type attribute
        if (entity.type == OrderDetailMultipleEntity.STATE_YES) {
            return TYPE_STATE_YES;
        } else if (entity.type == OrderDetailMultipleEntity.STATE_NO) {
            return TYPE_STATE_NO;
        } else if (entity.type == OrderDetailMultipleEntity.LOGISTICS) {
            return TYPE_LOGISTICS;
        } else if (entity.type == OrderDetailMultipleEntity.RECEIVE_PRODUCT) {
            return TYPE_RECEIVE_PRODUCT;
        } else if (entity.type == OrderDetailMultipleEntity.PRODUCT_DESC) {
            return TYPE_PRODUCT_DESC;
        } else if (entity.type == OrderDetailMultipleEntity.ORDER_DETAIL) {
            return TYPE_ORDER_DETAIL;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册相关的条目provider
        //Register related entries provider
        mProviderDelegate.registerProvider(new StateYesItemProvider());
        mProviderDelegate.registerProvider(new StateNoItemProvider());
        mProviderDelegate.registerProvider(new StateLogisticsItemProvider());
        mProviderDelegate.registerProvider(new StateReceiveProductItemProvider());
        mProviderDelegate.registerProvider(new StateProductDescItemProvider());
        mProviderDelegate.registerProvider(new StateOrderDetailItemProvider());
    }
}
