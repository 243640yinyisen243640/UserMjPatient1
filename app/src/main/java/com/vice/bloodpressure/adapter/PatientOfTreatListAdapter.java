package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.PatientOfTreatListBean;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class PatientOfTreatListAdapter extends CommonAdapter<PatientOfTreatListBean.PatientsBean> {
    private int type;

    public PatientOfTreatListAdapter(Context context, int layoutId, List<PatientOfTreatListBean.PatientsBean> datas, int type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, PatientOfTreatListBean.PatientsBean item, int position) {
        String username = item.getUsername();
        String tel = item.getTel();
        String idcard = item.getIdcard();
        //是否默认 1是 2否
        int defaultX = item.getDefaultX();
        viewHolder.setText(R.id.tv_name, username);
        viewHolder.setText(R.id.tv_tel, tel);
        viewHolder.setText(R.id.tv_id_number, idcard);
        ColorTextView tvDefault = viewHolder.getView(R.id.tv_default);
        if (1 == defaultX) {
            tvDefault.setVisibility(View.VISIBLE);
        } else {
            tvDefault.setVisibility(View.GONE);
        }
        //选中状态设置
        CheckBox cbCheck = viewHolder.getView(R.id.cb_check);
        boolean selected = item.isSelected();
        if (selected) {
            cbCheck.setChecked(true);
        } else {
            cbCheck.setChecked(false);
        }
        //选中点击
        if (0 == type) {
            cbCheck.setVisibility(View.GONE);
        } else {
            cbCheck.setVisibility(View.VISIBLE);
        }
    }
}
