package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.widget.view.decoration.GridSpacingItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: ServicePackActivity
 * @Description: 服务包列表
 * @Author: ZWK
 * @CreateDate: 2020/1/3 16:49
 */

public class ServicePackActivity extends BaseHandlerActivity {

    @BindView(R.id.rv_service_pack)
    RecyclerView rvServicePack;

    private List<ServicePackBean> servicePackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitle("服务包");

        initServicePack();

        getTvSave().setText("记录");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServicePackActivity.this, ServicePackRecordActivity.class));
            }
        });
    }

    private void initServicePack() {
        servicePackList = new ArrayList<>();
        String[] strings = new String[]{"①赠1次家庭随访服务", "②2次降糖处方服务 ", "③1次降压处方服务", "①赠1次家庭随访服务", "②2次降糖处方服务 "};
        String[] strings2 = new String[]{"①赠1次家庭随访服务", "②2次降糖处方服务 "};
        servicePackList.add(new ServicePackBean("", "家庭医生老人版", "原价199",
                "￥199/人", Arrays.asList(strings)));
        servicePackList.add(new ServicePackBean("", "家庭医生老人版", "原价199",
                "免费", Arrays.asList(strings2)));
        servicePackList.add(new ServicePackBean("", "家庭医生老人版", "原价799",
                "￥399/人", Arrays.asList(strings)));
        rvServicePack.setAdapter(new ServicePackAdapter(this, R.layout.item_service_pack, servicePackList));
        rvServicePack.setLayoutManager(new GridLayoutManager(this, 2));
        rvServicePack.addItemDecoration(new GridSpacingItemDecoration(2, ConvertUtils.dp2px(10), false));
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_service_pack, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    private class ServicePackAdapter extends CommonAdapter<ServicePackBean> {

        private Context context;

        public ServicePackAdapter(Context context, int layoutId, List<ServicePackBean> datas) {
            super(context, layoutId, datas);
            this.context = context;
        }

        @Override
        protected void convert(ViewHolder holder, ServicePackBean servicePackBean, int position) {
            holder.setText(R.id.tv_name, servicePackBean.getTitle());
            holder.setText(R.id.tv_old_price, servicePackBean.oldPrice);
            holder.setText(R.id.tv_new_price, servicePackBean.getNewPrice());
            TextView tvOldPrice = holder.getView(R.id.tv_old_price);
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            ImageView imageView = holder.getView(R.id.iv_pic);
            Glide.with(context).load(servicePackBean.getUrl()).placeholder(R.drawable.ic_service_pack_deafault)
                    .into(imageView);

            List<String> services = servicePackBean.getServices();

            RecyclerView recyclerView = holder.getView(R.id.rv_service);
            recyclerView.setAdapter(new ServiceAdapter(context, R.layout.item_service, services));
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

    private class ServicePackBean {
        private String url;
        private String title;
        private String oldPrice;
        private String newPrice;
        private List<String> services;

        private ServicePackBean(String url, String title, String oldPrice, String newPrice, List<String> services) {
            this.url = url;
            this.title = title;
            this.oldPrice = oldPrice;
            this.newPrice = newPrice;
            this.services = services;
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

        public List<String> getServices() {
            return services;
        }

        public void setServices(List<String> services) {
            this.services = services;
        }
    }

}
