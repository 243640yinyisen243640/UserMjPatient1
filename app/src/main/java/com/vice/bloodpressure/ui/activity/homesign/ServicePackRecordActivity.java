package com.vice.bloodpressure.ui.activity.homesign;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: ServicePackRecordActivity
 * @Description: 服务包购买记录
 * @Author: ZWK
 * @CreateDate: 2020/1/3 16:49
 */

public class ServicePackRecordActivity extends BaseHandlerActivity {

    @BindView(R.id.rv_service_pack_record)
    RecyclerView rvServicePackRecord;
    @BindView(R.id.srl_service_pack_record)
    SmartRefreshLayout srlServicePackRecord;

    private List<ServicePackRecordBean> servicePackRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("服务包");
        initData();
        initRefresh();
    }


    private void initRefresh() {
        srlServicePackRecord.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlServicePackRecord.finishRefresh(2000);
            }
        });
        srlServicePackRecord.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlServicePackRecord.finishLoadMore(2000);
            }
        });
    }

    private void initData() {
        servicePackRecordList = new ArrayList<>();
        String[] strings = new String[]{"①赠1次家庭随访服务", "②2次降糖处方服务 ", "③1次降压处方服务"};
        String[] strings2 = new String[]{"①赠1次家庭随访服务", "②2次降糖处方服务 "};
        servicePackRecordList.add(new ServicePackRecordBean("", "家庭医生老人版", "购买时间：2019-12-30 17:30", "原价199",
                "￥199/人", 1, Arrays.asList(strings)));

        servicePackRecordList.add(new ServicePackRecordBean("", "家庭医生老人版", "购买时间：2019-12-30 17:30", "原价199",
                "￥199/人", 2, Arrays.asList(strings2)));

        servicePackRecordList.add(new ServicePackRecordBean("", "家庭医生老人版", "购买时间：2019-12-30 17:30", "原价199",
                "￥199/人", 3, Arrays.asList(strings)));

        rvServicePackRecord.setAdapter(new ServicePackRecordAdapter(this, R.layout.item_service_pack_record, servicePackRecordList));
        rvServicePackRecord.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_service_pack_record, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    private class ServicePackRecordAdapter extends CommonAdapter<ServicePackRecordBean> {
        private Context context;

        private ServicePackRecordAdapter(Context context, int layoutId, List<ServicePackRecordBean> datas) {
            super(context, layoutId, datas);
            this.context = context;
        }

        @Override
        protected void convert(ViewHolder holder, ServicePackRecordBean item, int position) {
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_new_price, item.getNewPrice());
            holder.setText(R.id.tv_old_price, item.getOldPrice());
            holder.setText(R.id.tv_time, item.getTime());

            TextView tvOldPrice = holder.getView(R.id.tv_old_price);
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            ImageView imageView = holder.getView(R.id.iv_record);
            Glide.with(context).load(item.getUrl()).placeholder(R.drawable.ic_service_pack_record).into(imageView);

            TextView tvState = holder.getView(R.id.tv_state);
            GradientDrawable myGrad = (GradientDrawable) tvState.getBackground();
            switch (item.getState()) {
                case 1:
                    holder.setText(R.id.tv_state, "等待医生审核");
                    myGrad.setColor(Color.parseColor("#ff999999"));
                    break;
                case 2:
                    holder.setText(R.id.tv_state, "医生审核通过");
                    myGrad.setColor(Color.parseColor("#ff00c27f"));
                    break;
                case 3:
                    holder.setText(R.id.tv_state, "服务包已过期");
                    myGrad.setColor(Color.parseColor("#ffd1cece"));
                    break;
            }

            RecyclerView recyclerView = holder.getView(R.id.rv_record_service);

            recyclerView.setAdapter(new ServiceAdapter(context, R.layout.item_service, item.getServices()));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

        }

        private class ServiceAdapter extends CommonAdapter<String> {

            private ServiceAdapter(Context context, int layoutId, List<String> datas) {
                super(context, layoutId, datas);
            }

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_service, s);
            }
        }

    }

    private class ServicePackRecordBean {

        private String url;
        private String title;
        private String time;
        private String oldPrice;
        private String newPrice;
        private int state;
        private List<String> services;

        private ServicePackRecordBean(String url, String title, String time,
                                      String oldPrice, String newPrice, int state, List<String> services) {
            this.url = url;
            this.title = title;
            this.time = time;
            this.oldPrice = oldPrice;
            this.newPrice = newPrice;
            this.state = state;
            this.services = services;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public String getNewPrice() {
            return newPrice;
        }

        public void setNewPrice(String newPrice) {
            this.newPrice = newPrice;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<String> getServices() {
            return services;
        }

        public void setServices(List<String> services) {
            this.services = services;
        }
    }

}
