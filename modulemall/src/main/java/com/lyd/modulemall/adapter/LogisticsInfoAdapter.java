package com.lyd.modulemall.adapter;

import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.LogisticsInfoBean;
import com.lyd.modulemall.utils.SpannableStringUtil;

import java.util.List;

public class LogisticsInfoAdapter extends BaseQuickAdapter<LogisticsInfoBean.ListBean, BaseViewHolder> {
    private List<LogisticsInfoBean.ListBean> data;
    public LogisticsInfoAdapter(@Nullable List<LogisticsInfoBean.ListBean> data) {
        super(R.layout.item_logistics_info, data);
        this.data = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LogisticsInfoBean.ListBean item) {
        //当前item的索引==1 && 物流数据的数量大于1条   ->  显示绿色大圆圈
        helper.setGone(R.id.iv_new, helper.getLayoutPosition() == 1 && data.size() > 1)
                //当前item的索引!=1 && 物流数据的数量大于1条   ->  显示灰色小圆圈
                .setGone(R.id.iv_old, helper.getLayoutPosition() != 1 && data.size() > 1)
                //当前item的索引 != 0    ->  显示圆点上面短一点的灰线
                .setVisible(R.id.v_short_line, helper.getLayoutPosition() != 0)
                //当前item的索引 != 物流数据的最后一条    ->  显示圆点下面长一点的灰线
                .setGone(R.id.v_long_line, helper.getLayoutPosition() != data.size() - 1)
                //当前item的索引 != 物流数据的最后一条    ->  显示物流时间下面的横向的灰线
                .setGone(R.id.v_bottom_line, helper.getLayoutPosition() != data.size() - 1)
                //物流时间
                .setText(R.id.tv_date, item.getTime())
                .setTextColor(R.id.tv_date, helper.getLayoutPosition() == 1 ? ColorUtils.getColor(R.color.black_text) : ColorUtils.getColor(R.color.gray_text));

        TextView tvInfo = helper.getView(R.id.tv_info);

        tvInfo.setText(SpannableStringUtil.convertTelUrl(item.getStatus()));
        tvInfo.setHighlightColor(Color.TRANSPARENT);
        tvInfo.setMovementMethod(LinkMovementMethod.getInstance());
        tvInfo.setTextColor(helper.getLayoutPosition() == 1 ? ColorUtils.getColor(R.color.black_text) : ColorUtils.getColor(R.color.gray_text));
    }

}
