package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.HealthArchiveGroupLevelOneBean;
import com.vice.bloodpressure.bean.HealthArchiveGroupLevelZeroBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.imp.AdapterViewClickListener;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.ui.activity.healthrecordadd.PharmacyAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.List;

public class HealthArchiveGroupLevelAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;
    private Context mContext;
    private AdapterViewClickListener adapterViewClickListener;

    private TextView tvBmi;
    private TextView tvThr;

    public HealthArchiveGroupLevelAdapter(Context mContext, List<MultiItemEntity> data, AdapterViewClickListener adapterViewClickListener) {
        super(data);
        this.mContext = mContext;
        addItemType(TYPE_LEVEL_0, R.layout.header_health_archive);
        addItemType(TYPE_LEVEL_1, R.layout.item_health_archive);
        addItemType(TYPE_LEVEL_2, R.layout.item_health_archive_medicine_history);
        this.adapterViewClickListener = adapterViewClickListener;
    }

    public TextView getTvBmi() {
        return tvBmi;
    }

    public TextView getTvThr() {
        return tvThr;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                HealthArchiveGroupLevelZeroBean lv0 = (HealthArchiveGroupLevelZeroBean) item;
                helper.setText(R.id.tv_title_name, lv0.getGroupName())
                        .setImageResource(R.id.img_right_arrow, lv0.isExpanded() ? R.drawable.right_arrow_down : R.drawable.right_arrow);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case 1:
                HealthArchiveGroupLevelOneBean lv1 = (HealthArchiveGroupLevelOneBean) item;
                TextView tvName = helper.getView(R.id.tv_left);//左边
                TextView tvContent = helper.getView(R.id.tv_right);//右边
                tvName.setText(lv1.getName());
                if (lv1.getUnit() == null) {
                    tvContent.setText(lv1.getContent());
                } else {
                    SpannableString spannableString = new SpannableString(lv1.getContent() + " " + lv1.getUnit());
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.gray_text));
                    spannableString.setSpan(colorSpan, 0, lv1.getContent().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvContent.setText(spannableString);
                }
                if ("BMI".equals(lv1.getName())) {
                    tvBmi = tvContent;
                }
                if ("腰臀比".equals(lv1.getName())) {
                    tvThr = tvContent;
                }
                //点击
                helper.itemView.setOnClickListener(new OnAdapterViewClickListener(helper));
                break;
            case 2:
                HealthArchiveGroupLevelOneBean lv2 = (HealthArchiveGroupLevelOneBean) item;
                TextView tvLeft = helper.getView(R.id.tv_left);//左边
                TextView tvCenter = helper.getView(R.id.tv_center);//左边
                TextView tvRight = helper.getView(R.id.tv_right);//右边
                tvLeft.setText(lv2.getName());
                tvCenter.setText(lv2.getCenter());
                tvRight.setText(lv2.getContent());
                //删除用药史
                LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
                String token = userLogin.getToken();
                String id = lv2.getId();
                helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogUtils.getInstance().showNotCancelDialog(mContext, "",
                                "是否确认删除？", true, new DialogUtils.DialogCallBack() {
                                    @Override
                                    public void execEvent() {
                                        RxHttpUtils.createApi(Service.class)
                                                .delMedicine(token, id)
                                                .compose(Transformer.switchSchedulers())
                                                .subscribe(new CommonObserver<BaseData>() {
                                                    @Override
                                                    protected void onError(String errorMsg) {

                                                    }

                                                    @Override
                                                    protected void onSuccess(BaseData bean) {
                                                        int pos = helper.getAdapterPosition();
                                                        remove(pos);
                                                    }
                                                });
                                    }
                                });
                        return true;
                    }
                });
                //跳转用药史详情
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Utils.getApp(), PharmacyAddActivity.class);
                        intent.putExtra("detailBean", lv2);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                    }
                });
                break;
        }
    }

    private class OnAdapterViewClickListener implements View.OnClickListener {

        BaseViewHolder helper;

        private OnAdapterViewClickListener(BaseViewHolder helper) {
            this.helper = helper;
        }

        @Override
        public void onClick(View v) {
            if (adapterViewClickListener != null) {
                adapterViewClickListener.adapterViewClick(helper);
            }
        }
    }
}

