package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.HomeSignAdviceBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: DoctorAdviceActivity
 * @Description: 医生建议
 * @Author: zwk
 * @CreateDate: 2020/1/2 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/1/2 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DoctorAdviceActivity extends BaseHandlerActivity {

    private static final int GET_DATA = 0x009;
    private static final int LOAD_MORE = 0x010;
    private static final int REFRESH = 0x011;

    @BindView(R.id.rv_advice)
    RecyclerView rvAdvice;
    @BindView(R.id.srl_advice)
    SmartRefreshLayout srlAdvice;
    @BindView(R.id.tv_ask)
    ColorTextView tvAsk;
    private List<HomeSignAdviceBean> adviceBeanList;
    private String docname;
    private int docid;
    private int pageIndex = 1;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        docname = getIntent().getStringExtra("docname");
        docid = getIntent().getIntExtra("docid", -1);
        setTitle("医生建议");
        initAdvice(GET_DATA);
        initRefresh();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_doctor_advise, contentLayout, false);
    }

    private void initRefresh() {
        srlAdvice.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlAdvice.finishRefresh(2000);
                pageIndex = 1;
                initAdvice(REFRESH);
            }
        });

        srlAdvice.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlAdvice.finishLoadMore(2000);
                pageIndex++;
                initAdvice(LOAD_MORE);
            }
        });
    }

    private void initAdvice(int state) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("docid", docid);
        map.put("page", pageIndex);
        XyUrl.okPost(XyUrl.DOCTOR_ADVICE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message message = getHandlerMessage();
                switch (state) {
                    case GET_DATA:
                    case REFRESH:
                        adviceBeanList = JSONObject.parseArray(value, HomeSignAdviceBean.class);
                        break;
                    case LOAD_MORE:
                        adviceBeanList.addAll(JSONObject.parseArray(value, HomeSignAdviceBean.class));
                        break;
                }
                message.what = state;
                message.obj = adviceBeanList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @OnClick(R.id.tv_ask)
    public void onViewClicked() {
        RongIM.getInstance().startPrivateChat(this, docid + "", docname);
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
            case REFRESH:
                adviceBeanList = (List<HomeSignAdviceBean>) msg.obj;
                adapter = new MyAdapter(getPageContext(), R.layout.item_doctor_advise, adviceBeanList);
                rvAdvice.setAdapter(adapter);
                rvAdvice.setLayoutManager(new LinearLayoutManager(getPageContext()));
                srlAdvice.finishRefresh(2000);
                break;
            case LOAD_MORE:
                adapter.notifyDataSetChanged();
                srlAdvice.finishLoadMore(2000);
                break;

        }
    }

    private class MyAdapter extends CommonAdapter<HomeSignAdviceBean> {

        private MyAdapter(Context context, int layoutId, List<HomeSignAdviceBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, HomeSignAdviceBean adviceBean, int position) {
            holder.setText(R.id.tv_time, adviceBean.getCreattime());
            holder.setText(R.id.tv_title, adviceBean.getContent());
        }
    }

}
