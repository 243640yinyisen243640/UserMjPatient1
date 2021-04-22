package com.vice.bloodpressure.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SugarListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SugarListAdapter extends CommonAdapter<SugarListBean> {
    private static final String TAG = "SugarListAdapter";
    private static final int DEL_SUGAR_SUCCESS = 10086;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DEL_SUGAR_SUCCESS:
                    //删除后刷新
                    EventBusUtils.post(new EventMessage<>(ConstantParam.DEL_SUGAR_REFRESH));
                    ToastUtils.showShort("删除成功");
                    break;
            }
        }
    };
    private List<SugarListBean> datas;
    private Context context;

    public SugarListAdapter(Context context, int layoutId, List<SugarListBean> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, SugarListBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        viewHolder.setText(R.id.tv_center, item.getGlucosevalue() + "");
        viewHolder.setText(R.id.tv_right, item.getType());
        viewHolder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                notifyDataSetChanged();
                deleteData(item.getId());
            }
        });
    }


    /**
     * 删除血糖
     *
     * @param id
     */
    private void deleteData(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        XyUrl.okPostSave(XyUrl.DEL_BLOOD_SUGAR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message msg = new Message();
                msg.what = DEL_SUGAR_SUCCESS;
                handler.handleMessage(msg);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

}
