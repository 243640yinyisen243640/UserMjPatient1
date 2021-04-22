package com.lyd.baselib.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.R;
import com.lyd.baselib.databinding.ActivityBaseViewBindingBinding;
import com.lyd.baselib.utils.DisableDisplayDpiChangeUtils;
import com.lyd.baselib.utils.eventbus.BindEventBus;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import liys.click.OnClickUtils;


/**
 * 描述: 基于ViewBinding封装的BaseActivity
 * 作者: LYD
 * 创建日期: 2019/3/25 10:05
 */
public abstract class BaseViewBindingActivity<T extends ViewBinding> extends AppCompatActivity {
    private static final String TAG = "BaseViewBindingActivity";
    private Context mContext;
    //ViewBinding封装开始
    protected ActivityBaseViewBindingBinding baseBinding;
    protected T binding;
    //ViewBinding封装结束


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //ViewBinding封装
        initViewBinding();
        //必须在setContentView之后调用
        OnClickUtils.init(this);
        baseBinding.btBack.setOnClickListener(v -> finish());
        //状态栏
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        //初始化数据
        init(savedInstanceState);
        //横竖屏
        setScreenOrientation(true);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
        DisableDisplayDpiChangeUtils.disabledDisplayDpiChange(this);
    }

    /**
     * 封装ViewBinding
     */
    private void initViewBinding() {
        baseBinding = ActivityBaseViewBindingBinding.inflate(getLayoutInflater());
        View contentView = LayoutInflater.from(this).inflate(getLayoutId(), baseBinding.rlContent, false);
        baseBinding.rlContent.addView(contentView);
        setContentView(baseBinding.getRoot());
        try {
            //获得该类带有泛型的父类
            Type genericSuperclass = getClass().getGenericSuperclass();
            Log.e(TAG, "genericSuperclass==" + genericSuperclass);
            //ParameterizedType参数化类型，即泛型
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Log.e(TAG, "parameterizedType==" + parameterizedType);
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            Class clazz = (Class) parameterizedType.getActualTypeArguments()[0];
            Log.e(TAG, "clazz==" + clazz);
            Method method = clazz.getDeclaredMethod("bind", View.class);
            Log.e(TAG, "method==" + method);
            binding = (T) method.invoke(null, contentView);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取内容布局Id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void init(Bundle savedInstanceState);


    /**
     * 设置屏幕横竖屏切换
     *
     * @param screenRotate true  竖屏     false  横屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private void setScreenOrientation(Boolean screenRotate) {
        if (screenRotate) {
            //设置竖屏模式
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //设置横屏模式
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 获取当前上下文
     *
     * @return
     */
    public Context getPageContext() {
        return mContext;
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        baseBinding.tvTitle.setText(title);
    }

    /**
     * 设置标题
     *
     * @param
     */
    public void hideTitleBar() {
        baseBinding.rlTitleBar.setVisibility(View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0)
    public void onEventBusCome(EventMessage event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 0)
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
    protected void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
    }


//    /**
//     * 重写 getResource 方法，防止系统字体影响
//     * 禁止app字体大小跟随系统字体大小调节
//     */
//    @Override
//    public Resources getResources() {
//        Resources resources = super.getResources();
//        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
//            Configuration configuration = resources.getConfiguration();
//            configuration.setToDefaults();
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
//        return resources;
//    }
}
