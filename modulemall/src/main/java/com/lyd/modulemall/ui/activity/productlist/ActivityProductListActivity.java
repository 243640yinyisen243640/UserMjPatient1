package com.lyd.modulemall.ui.activity.productlist;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.modulemall.adapter.MallHomeProductAdapter;
import com.lyd.modulemall.bean.MallHomeProductBean;
import com.lyd.modulemall.databinding.ActivityProductListActivityBinding;
import com.lyd.modulemall.net.ErrorInfo;
import com.lyd.modulemall.net.MallUrl;
import com.lyd.modulemall.net.OnError;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zackratos.ultimatebarx.library.UltimateBarX;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 活动商品列表页
 * 作者: LYD
 * 创建日期: 2020/12/24 16:47
 */
public class ActivityProductListActivity extends AppCompatActivity {
    //分页开始
    private MallHomeProductAdapter adapter;
    //总数据
    private List<MallHomeProductBean> list;
    //上拉加载数据
    private List<MallHomeProductBean> tempList;
    //上拉加载页数
    private int pageIndex = 2;
    //分页结束
    private String activityId;
    private ActivityProductListActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBack();
        initStatusBar();
        setActivityPic();
        initRefresh();
        getProductList();
    }


    private void initBack() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRefresh() {
        //刷新开始
        binding.srlList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                binding.srlList.finishRefresh(2000);
                pageIndex = 2;
                //刷新当前页面
                getProductList();
            }
        });
        binding.srlList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                binding.srlList.finishLoadMore(2000);
                getMoreData();
            }
        });
        //刷新结束
    }

    private void getMoreData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        map.put("activity_id", activityId);
        RxHttp.postForm(MallUrl.GET_GOODS_LIST)
                .addAll(map)
                .asResponseList(MallHomeProductBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MallHomeProductBean>>() {
                    @Override
                    public void accept(List<MallHomeProductBean> myCouponListBean) throws Exception {
                        tempList = myCouponListBean;
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

    private void getProductList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", "1");
        map.put("activity_id", activityId);
        RxHttp.postForm(MallUrl.GET_GOODS_LIST)
                .addAll(map)
                .asResponseList(MallHomeProductBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MallHomeProductBean>>() {
                    @Override
                    public void accept(List<MallHomeProductBean> mallHomeProductBean) throws Exception {
                        list = mallHomeProductBean;
                        binding.rvList.setLayoutManager(new GridLayoutManager(ActivityProductListActivity.this, 2));
                        adapter = new MallHomeProductAdapter(list);
                        binding.rvList.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });


    }

    private void setActivityPic() {
        activityId = getIntent().getStringExtra("activity_id");
        String activity_img = getIntent().getStringExtra("activity_img");
        Glide.with(Utils.getApp()).load(activity_img).into(binding.imgActivityPic);
    }

    private void initStatusBar() {
        UltimateBarX.with(this)
                .transparent()
                .applyStatusBar();
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