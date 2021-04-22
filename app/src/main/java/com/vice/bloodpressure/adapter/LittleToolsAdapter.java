package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.content.res.TypedArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class LittleToolsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LittleToolsAdapter(@Nullable List<String> data) {
        super(R.layout.item_little_tools, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String s) {
        int layoutPosition = viewHolder.getLayoutPosition();
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.little_tools_text);
        viewHolder.setText(R.id.tv_little_tools, stringArray[layoutPosition]);

        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.little_tools_img);
        viewHolder.setImageResource(R.id.img_little_tools, imgArray.getResourceId(layoutPosition, 0));

        //点击
        String[] assessmentUrls = Utils.getApp().getResources().getStringArray(R.array.assessment_url);
        List<DataBean> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            DataBean dataBean = new DataBean(0, assessmentUrls[i], "");
            list.add(dataBean);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                intent.putExtra("title", "自我检测");
                intent.putExtra("url", list.get(layoutPosition).getDescribe());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
