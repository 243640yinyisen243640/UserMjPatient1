package com.vice.bloodpressure.ui.fragment.main;

import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ConvertUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.ImageMallAdapter;
import com.vice.bloodpressure.adapter.MallNativeChildListAdapter;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.MallNativeChildBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.RvUtils;
import com.lyd.baselib.widget.view.decoration.CommonItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MallNativeChildFragment extends BaseFragment {
    private static final int GET_DATA_SUCCESS = 10010;
    private static final int GET_DATA_ERROR = 10011;
    private static final int GET_DATA_MORE = 10012;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.srl_mall)
    SmartRefreshLayout srlMall;
    @BindView(R.id.rv_mall)
    RecyclerView rvMall;

    private MallNativeChildListAdapter adapter;
    private List<MallNativeChildBean.ListBean> list;
    private List<MallNativeChildBean.ListBean> tempList;
    private int pageIndex = 2;

    private List<MallNativeChildBean.BannerBean> bannerList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_native_child;
    }

    @Override
    protected void init(View rootView) {
        RvUtils.setRvForScrollView(rvMall);
        rvMall.setLayoutManager(new GridLayoutManager(getPageContext(), 2));
        int spacing = ConvertUtils.dp2px(10);
        rvMall.addItemDecoration(new CommonItemDecoration(spacing, spacing, spacing));
        int id = getArguments().getInt("id");
        getBannerAndGoodList(id);
        initRefresh(id);
    }

    private void initRefresh(int id) {
        //刷新开始
        srlMall.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                srlMall.finishRefresh(2000);
                pageIndex = 2;
                getBannerAndGoodList(id);
            }
        });
        srlMall.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                srlMall.finishLoadMore(2000);
                getMore(id);
            }
        });
        //刷新结束
    }

    private void getBannerAndGoodList(int id) {
        HashMap map = new HashMap<>();
        map.put("cid", id);
        map.put("page", 1);
        XyUrl.okPost(XyUrl.MALL_BANNER_AND_GOODS_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MallNativeChildBean data = JSONObject.parseObject(value, MallNativeChildBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA_SUCCESS;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                Message message = Message.obtain();
                message.what = GET_DATA_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    private void getMore(int id) {
        HashMap map = new HashMap<>();
        map.put("cid", id);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.MALL_BANNER_AND_GOODS_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MallNativeChildBean data = JSONObject.parseObject(value, MallNativeChildBean.class);
                tempList = data.getList();
                list.addAll(tempList);
                Message message = Message.obtain();
                message.what = GET_DATA_MORE;
                message.obj = list;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA_SUCCESS:
                MallNativeChildBean data = (MallNativeChildBean) msg.obj;
                bannerList = data.getBanner();
                setBanner(bannerList);
                list = data.getList();
                if (list != null && list.size() > 0) {
                    adapter = new MallNativeChildListAdapter(list);
                    rvMall.setAdapter(adapter);
                }
                break;
            case GET_DATA_MORE:
                pageIndex++;
                adapter.notifyDataSetChanged();
                break;
        }
    }


    /**
     * 设置轮播图
     *
     * @param bannerList
     */
    private void setBanner(List<MallNativeChildBean.BannerBean> bannerList) {
        if (bannerList != null && bannerList.size() > 0) {
            ImageMallAdapter adapter = new ImageMallAdapter(bannerList);
            banner.setAdapter(adapter)
                    .addBannerLifecycleObserver(this)
                    .setIndicator(new CircleIndicator(getPageContext())).setOnBannerListener((data, position) -> {
                if (!TextUtils.isEmpty(bannerList.get(position).getBannerurl())) {
                    Intent intentBanner = new Intent(getPageContext(), BaseWebViewActivity.class);
                    intentBanner.putExtra("title", bannerList.get(position).getName());
                    intentBanner.putExtra("url", bannerList.get(position).getGid());
                    startActivity(intentBanner);
                }
            });
        }
    }
}
