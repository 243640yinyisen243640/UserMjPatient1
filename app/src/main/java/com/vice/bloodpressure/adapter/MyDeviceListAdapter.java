package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.ui.activity.mydevice.InputImeiActivity;

import java.util.List;

public class MyDeviceListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MyDeviceListAdapter(@Nullable List<String> data) {
        super(R.layout.item_my_device_list, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String s) {
        viewHolder.setImageResource(R.id.img_device, R.drawable.device_list_vivachek);
        viewHolder.setText(R.id.tv_name, s);

                int layoutPosition = viewHolder.getLayoutPosition();
        //        //设置图片
//                TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.my_device_list_pic);
        //        viewHolder.setImageResource(R.id.img_device, imgArray.getResourceId(layoutPosition, 0));
        //        //设置文字
        //        viewHolder.setText(R.id.tv_name, s);



                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        switch (layoutPosition) {
                            case 0:
                                intent = new Intent(Utils.getApp(), InputImeiActivity.class);
                                intent.putExtra("type", "2");
                                intent.putExtra("imei", "");
                                break;
                        /*    case 1:
                                intent = new Intent(Utils.getApp(), InputImeiActivity.class);
                                intent.putExtra("type", "1");
                                intent.putExtra("imei", "");
                                break;
                            case 2:
                                intent = new Intent(Utils.getApp(), InputImeiActivity.class);
                                intent.putExtra("type", "4");
                                intent.putExtra("imei", "");
                                break;*/
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                    }
                });


    }
}
