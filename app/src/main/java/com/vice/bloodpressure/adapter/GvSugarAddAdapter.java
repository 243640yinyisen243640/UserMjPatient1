package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.vice.bloodpressure.R;
import com.wei.android.lib.colorview.helper.ColorTextViewHelper;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class GvSugarAddAdapter extends CommonAdapter<String> {
    private List<String> list;
    //默认选中第一个
    private int defaultSelection = 0;

    public GvSugarAddAdapter(Context context, int layoutId, List<String> datas, int defaultSelection) {
        super(context, layoutId, datas);
        this.defaultSelection = defaultSelection;
        this.list = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_time, item);
        ColorTextView tvTime = viewHolder.getView(R.id.tv_time);
        int screenWidth = ScreenUtils.getScreenWidth();
        int paddingWidth = SizeUtils.dp2px(2 * 12 + 3 * 12);
        int totalWidth = screenWidth - paddingWidth;
        int width = totalWidth / 4;

        ViewGroup.LayoutParams params = tvTime.getLayoutParams();
        params.width = width;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvTime.setLayoutParams(params);

        ColorTextViewHelper colorTextViewHelper = tvTime.getColorHelper();
        if (position == defaultSelection) {
            //选中时
            colorTextViewHelper.setTextColorNormal(ColorUtils.getColor(R.color.main_home));
            colorTextViewHelper.setBorderColorNormal(ColorUtils.getColor(R.color.main_home));
            colorTextViewHelper.setBackgroundColorNormal(ColorUtils.getColor(R.color.white));
        } else {
            //未选中时
            colorTextViewHelper.setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
            colorTextViewHelper.setBorderColorNormal(ColorUtils.getColor(R.color.blood_sugar_bg));
            colorTextViewHelper.setBackgroundColorNormal(ColorUtils.getColor(R.color.blood_sugar_bg));
        }
    }

    /**
     * 修改选中时的状态
     *
     * @param position
     */
    public void setSelect(int position) {
        if (!(position < 0 || position > list.size())) {
            defaultSelection = position;
            notifyDataSetChanged();
        }
    }


}
