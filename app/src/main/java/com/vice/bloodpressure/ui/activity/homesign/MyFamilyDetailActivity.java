package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BloodPressureListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BloodSugarListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BmiListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.CheckListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.FoodListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.HemoglobinListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.HepatopathyPabulumListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.MedicineUseListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.SportListActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFamilyDetailActivity extends BaseHandlerActivity {

    @BindView(R.id.rv_family_detail)
    RecyclerView rvFamilyDetail;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("我的家庭");

        userId = getIntent().getIntExtra("user_id", -1);

        getTvSave().setText("已签约");
        getTvSave().setPadding(ConvertUtils.dp2px(7), 0, ConvertUtils.dp2px(7), 0);
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        String[] names = Utils.getApp().getResources().getStringArray(R.array.family_detail);
        TypedArray pics = Utils.getApp().getResources().obtainTypedArray(R.array.family_detail_pic);

        List<RecordBean> recordBeans = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            recordBeans.add(new RecordBean(pics.getResourceId(i, -1), names[i]));
        }
        pics.recycle();

        rvFamilyDetail.setAdapter(new MyAdapter(this, R.layout.item_family_detail, recordBeans));
        rvFamilyDetail.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_my_family_detail, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    private class RecordBean {
        private int pic;
        private String name;

        public RecordBean(int pic, String name) {
            this.pic = pic;
            this.name = name;
        }

        public int getPic() {
            return pic;
        }

        public String getName() {
            return name;
        }
    }

    private class MyAdapter extends CommonAdapter<RecordBean> {

        private MyAdapter(Context context, int layoutId, List<RecordBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, RecordBean item, int position) {
            holder.setText(R.id.tv_record_name, item.getName());
            holder.setText(R.id.tv_tip, String.format("点击查看%s纪录", item.getName()));
            holder.setImageResource(R.id.iv_record, item.getPic());

            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (position) {
                        case 0:
                            intent = new Intent(getPageContext(), BloodSugarListActivity.class);
                            break;
                        case 1:
                            intent = new Intent(getPageContext(), BloodPressureListActivity.class);
                            intent.putExtra("key", 1);
                            break;
                        case 2:
                            intent = new Intent(getPageContext(), FoodListActivity.class);
                            break;
                        case 3:
                            intent = new Intent(getPageContext(), SportListActivity.class);
                            break;
                        case 4:
                            intent = new Intent(getPageContext(), MedicineUseListActivity.class);
                            break;
                        case 5:
                            intent = new Intent(getPageContext(), BmiListActivity.class);
                            intent.putExtra("key", 5);
                            break;
                        case 6:
                            intent = new Intent(getPageContext(), HemoglobinListActivity.class);
                            intent.putExtra("key", 6);
                            break;
                        case 7:
                            intent = new Intent(getPageContext(), CheckListActivity.class);
                            intent.putExtra("key", 7);
                            break;
                        case 8:
                            intent = new Intent(getPageContext(), HepatopathyPabulumListActivity.class);
                            break;
                        default:
                            intent = new Intent(getPageContext(), MyFamilyDetailActivity.class);
                            break;
                    }
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
                }
            });
        }
    }
}
