package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;

/**
 * @ProjectName: NewBloodPressure
 * @Package: com.vice.bloodpressure.adapter
 * @ClassName: RecipeDetailPercentBarAdapter
 * @Description: java类作用描述
 * @Author: zwk
 * @CreateDate: 2019/12/19 15:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/19 15:26
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */

public class RecipeDetailPercentBarAdapter extends BaseAdapter {

    private List<String> names;
    private List<String> values;
    private Context context;

    public RecipeDetailPercentBarAdapter(Context context, List<String> names, List<String> values) {
        this.context = context;
        this.names = names;
        this.values = values;
    }

    @Override
    public int getCount() {
        return names == null ? 0 : names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_material_percent, null);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvBar = convertView.findViewById(R.id.tv_bar);
            viewHolder.tvValue = convertView.findViewById(R.id.tv_val);

            float width = getMaxTextLength(viewHolder.tvName);
            ViewGroup.LayoutParams params = viewHolder.tvName.getLayoutParams();
            params.width = (int) width;
            viewHolder.tvName.setLayoutParams(params);

            viewHolder.tvName.setText(names.get(position));
            viewHolder.tvValue.setText(percentFormat(Float.valueOf(values.get(position))));

            int screenWidth = ScreenUtils.getScreenWidth();//屏幕总宽度
            int tvLeftWidth = ConvertUtils.dp2px(10f) + (int) width;//左边宽度
            int tvRightWidth = ConvertUtils.dp2px(80f);//右边宽度
            int paddingWidth = ConvertUtils.dp2px(16 * 2);
            int marginWidth = ConvertUtils.dp2px(20 * 2 + 10);
            int tvCenterTotalWidth = screenWidth - (tvLeftWidth + tvRightWidth + paddingWidth + marginWidth);

            int max = getMaxValuePosition();

            GradientDrawable gradientDrawable;
            int[] colors = Utils.getApp().getResources().getIntArray(R.array.diet_plan_colors);

            ViewGroup.LayoutParams layoutParams = viewHolder.tvBar.getLayoutParams();
            layoutParams.width = (int) ((Float.valueOf(values.get(position)) / Float.valueOf(values.get(max))) * tvCenterTotalWidth);
            viewHolder.tvBar.setLayoutParams(layoutParams);
            gradientDrawable = (GradientDrawable) viewHolder.tvBar.getBackground();
            gradientDrawable.setColor(colors[position]);
        }

        return convertView;
    }

    private float getMaxTextLength(TextView textView) {
        TextPaint paint = textView.getPaint();
        float max = 0;
        for (int i = 0; i < names.size(); i++) {
            float len = paint.measureText(names.get(i));
            if (len > max) {
                max = len;
            }
        }
        return max;
    }

    private String percentFormat(float num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(num * 100) + "%";
    }

    private int getMaxValuePosition() {
        int maxValuePosition = 0;
        for (int i = 0; i < values.size(); i++) {
            if (Float.valueOf(values.get(i)) > Float.valueOf(values.get(maxValuePosition))) {
                maxValuePosition = i;
            }
        }
        return maxValuePosition;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvBar;
        TextView tvValue;
    }
}
