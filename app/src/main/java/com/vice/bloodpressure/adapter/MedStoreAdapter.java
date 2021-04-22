package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MedicineStoreLevelOneBean;
import com.vice.bloodpressure.ui.activity.medicinestore.MedicineClassListActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class MedStoreAdapter extends CommonAdapter<MedicineStoreLevelOneBean> {
    public MedStoreAdapter(Context context, int layoutId, List<MedicineStoreLevelOneBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MedicineStoreLevelOneBean data, int position) {
        ImageView img = holder.getView(R.id.img_pic);
        String picurl = data.getPicurl();
        Glide.with(Utils.getApp())
                .load(picurl)
                .into(img);
        String classname = data.getClassname();
        holder.setText(R.id.tv_text, classname);

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MedicineClassListActivity.class);
                intent.putExtra("id", data.getId() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
