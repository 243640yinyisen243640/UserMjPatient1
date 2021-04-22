package com.lyd.baselib.base.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 描述:基于ViewBinding封装的BaseFragment
 * 作者: LYD
 * 创建日期: 2020/12/22 15:20
 */
public abstract class BaseViewBindingFragment<T extends ViewBinding> extends Fragment {
    //private boolean isFirstLoad = false;


    //    //懒加载相关判断 开始
    //    private boolean mIsFirstVisible = true;
    //    private boolean isViewCreated = false;
    //    private boolean currentVisibleState = false;
    //    //懒加载相关判断 结束
    protected T binding;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<?> clazz = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = clazz.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            binding = (T) method.invoke(null, getLayoutInflater(), container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //由于Fragment的存在时间比其视图长，需要在onDestroyView()方法中清除对绑定类实例的所有引用
        binding = null;

        //isViewCreated = false;
        //mIsFirstVisible = true;
    }

    //
    //
    //    @Override
    //    public void setUserVisibleHint(boolean isVisibleToUser) {
    //        super.setUserVisibleHint(isVisibleToUser);
    //        // 对于默认 tab 和 间隔 checked tab 需要等到 isViewCreated = true 后才可以通过此通知用户可见
    //        // 这种情况下第一次可见不是在这里通知 因为 isViewCreated = false 成立,等从别的界面回到这里后会使用 onFragmentResume 通知可见
    //        // 对于非默认 tab mIsFirstVisible = true 会一直保持到选择则这个 tab 的时候，因为在 onActivityCreated 会返回 false
    //        if (isViewCreated) {
    //            if (isVisibleToUser && !currentVisibleState) {
    //                dispatchUserVisibleHint(true);
    //            } else if (!isVisibleToUser && currentVisibleState) {
    //                dispatchUserVisibleHint(false);
    //            }
    //        }
    //    }
    //
    //    @Override
    //    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    //        super.onActivityCreated(savedInstanceState);
    //        isViewCreated = true;
    //        // !isHidden() 默认为 true  在调用 hide show 的时候可以使用
    //        if (!isHidden() && getUserVisibleHint()) {
    //            // 这里的限制只能限制 A - > B 两层嵌套
    //            dispatchUserVisibleHint(true);
    //        }
    //    }
    //
    //    @Override
    //    public void onHiddenChanged(boolean hidden) {
    //        super.onHiddenChanged(hidden);
    //        LogUtils.e(getClass().getSimpleName() + "  onHiddenChanged dispatchChildVisibleState  hidden " + hidden);
    //        if (hidden) {
    //            dispatchUserVisibleHint(false);
    //        } else {
    //            dispatchUserVisibleHint(true);
    //        }
    //    }
    //
    //    @Override
    //    public void onResume() {
    //        super.onResume();
    //        if (!mIsFirstVisible) {
    //            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {
    //                dispatchUserVisibleHint(true);
    //            }
    //        }
    //    }
    //
    //    @Override
    //    public void onPause() {
    //        super.onPause();
    //        // 当前 Fragment 包含子 Fragment 的时候 dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见
    //        // 子 fragment 走到这里的时候自身又会调用一遍 ？
    //        if (currentVisibleState && getUserVisibleHint()) {
    //            dispatchUserVisibleHint(false);
    //        }
    //    }
    //
    //    private boolean isFragmentVisible(Fragment fragment) {
    //        return !fragment.isHidden() && fragment.getUserVisibleHint();
    //    }
    //
    //
    //    /**
    //     * 统一处理 显示隐藏
    //     *
    //     * @param visible
    //     */
    //    private void dispatchUserVisibleHint(boolean visible) {
    //        //当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment getUserVisibleHint = true
    //        //但当父 fragment 不可见所以 currentVisibleState = false 直接 return 掉
    //        //        LogUtils.e(getClass().getSimpleName() + "  dispatchUserVisibleHint isParentInvisible() " + isParentInvisible());
    //        // 这里限制则可以限制多层嵌套的时候子 Fragment 的分发
    //        if (visible && isParentInvisible()) return;
    //        //
    //        //        //此处是对子 Fragment 不可见的限制，因为 子 Fragment 先于父 Fragment回调本方法 currentVisibleState 置位 false
    //        //        // 当父 dispatchChildVisibleState 的时候第二次回调本方法 visible = false 所以此处 visible 将直接返回
    //        if (currentVisibleState == visible) {
    //            return;
    //        }
    //        currentVisibleState = visible;
    //        if (visible) {
    //            if (mIsFirstVisible) {
    //                mIsFirstVisible = false;
    //                onFragmentFirstVisible();
    //            }
    //            onFragmentResume();
    //            dispatchChildVisibleState(true);
    //        } else {
    //            dispatchChildVisibleState(false);
    //            onFragmentPause();
    //        }
    //    }
    //
    //    /**
    //     * 用于分发可见时间的时候父获取 fragment 是否隐藏
    //     *
    //     * @return true fragment 不可见， false 父 fragment 可见
    //     */
    //    private boolean isParentInvisible() {
    //        BaseViewBindingFragment fragment = (BaseViewBindingFragment) getParentFragment();
    //        return fragment != null && !fragment.isSupportVisible();
    //    }
    //
    //    private boolean isSupportVisible() {
    //        return currentVisibleState;
    //    }
    //
    //    /**
    //     * 当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment 的唯一或者嵌套 VP 的第一 fragment 时 getUserVisibleHint = true
    //     * 但是由于父 Fragment 还进入可见状态所以自身也是不可见的， 这个方法可以存在是因为庆幸的是 父 fragment 的生命周期回调总是先于子 Fragment
    //     * 所以在父 fragment 设置完成当前不可见状态后，需要通知子 Fragment 我不可见，你也不可见，
    //     * <p>
    //     * 因为 dispatchUserVisibleHint 中判断了 isParentInvisible 所以当 子 fragment 走到了 onActivityCreated 的时候直接 return 掉了
    //     * <p>
    //     * 当真正的外部 Fragment 可见的时候，走 setVisibleHint (VP 中)或者 onActivityCreated (hide show) 的时候
    //     * 从对应的生命周期入口调用 dispatchChildVisibleState 通知子 Fragment 可见状态
    //     *
    //     * @param visible
    //     */
    //    private void dispatchChildVisibleState(boolean visible) {
    //        FragmentManager childFragmentManager = getChildFragmentManager();
    //        List<Fragment> fragments = childFragmentManager.getFragments();
    //        if (!fragments.isEmpty()) {
    //            for (Fragment child : fragments) {
    //                if (child instanceof BaseViewBindingFragment && !child.isHidden() && child.getUserVisibleHint()) {
    //                    ((BaseViewBindingFragment) child).dispatchUserVisibleHint(visible);
    //                }
    //            }
    //        }
    //    }
    //
    //    /**
    //     * 重写此方法,获取数据即可实现懒加载
    //     */
    //    public void onFragmentFirstVisible() {
    //        LogUtils.e(getClass().getSimpleName() + "  对用户第一次可见");
    //    }
    //
    //    public void onFragmentResume() {
    //        LogUtils.e(getClass().getSimpleName() + "  对用户可见");
    //    }
    //
    //    public void onFragmentPause() {
    //        LogUtils.e(getClass().getSimpleName() + "  对用户不可见");
    //    }


    protected abstract void init();

    public Context getPageContext() {
        return mContext;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(EventMessage event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(EventMessage event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(EventMessage event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(EventMessage event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onLazyLoad();//数据加载操作
        }
    }

    //数据加载接口，留给子类实现
    public abstract void onLazyLoad();


}