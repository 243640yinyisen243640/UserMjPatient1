package com.vice.bloodpressure.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.LabelBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yicheng on 2018/5/24.
 */

public class LableAdapter extends BaseAdapter {

    int itemLength = 20;
    private Context context;
    private LayoutInflater inflater = null;
    private List<Integer> count;
    private List<LabelBean> labelList;
    private List<Integer> selectid;

    private int clickTemp = -1;//标识被选择的item

    private Map<Integer, Boolean> ischeck;

    private onClickMyTextView onClickMyTextView;

    public LableAdapter(Context mContext, List<LabelBean> labelList) {
        this.context = mContext;
        this.labelList = labelList;
        inflater = LayoutInflater.from(mContext);
        count = new ArrayList<>();
        selectid = new ArrayList<>();
        ischeck = new HashMap<>();
        if (labelList != null || labelList.size() > 0) {
            for (int i = 0; i < labelList.size(); i++) {
                ischeck.put(i, false);
            }
        }
    }

    public void setOnClickMyTextView(onClickMyTextView onClickMyTextView) {
        this.onClickMyTextView = onClickMyTextView;
    }

    @Override
    public int getCount() {
        if (labelList == null || labelList.size() == 0) {
            return 0;
        }
        return labelList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return labelList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void setSeclection(int pos) {
        clickTemp = pos;
        if (ischeck.get(clickTemp)) {
            ischeck.put(clickTemp, false);
            for (int i = selectid.size() - 1; i >= 0; i--) {
                if (selectid.get(i) == labelList.get(pos).getId())
                    selectid.remove(selectid.get(i));
            }
        } else {
            ischeck.put(clickTemp, true);
            selectid.add(labelList.get(pos).getId());
        }
        onClickMyTextView.myTextViewClick(selectid);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_note, null);
            holder.tv_note = convertView.findViewById(R.id.tv_item_note);
            holder.rl_choose = convertView.findViewById(R.id.rl_choose);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_note.setText(labelList.get(position).getRemarkname());
        holder.tv_note.setTag(position);

        if (ischeck.get(position)) {
            //大于5.0不支持
            //            holder.rl_choose.setBackground(context.getDrawable(R.drawable.login_button));
            //            holder.tv_note.setTextColor(context.getColor(R.color.white));
            holder.rl_choose.setBackground(context.getResources().getDrawable(R.drawable.login_button));
            holder.tv_note.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.rl_choose.setBackground(context.getResources().getDrawable(R.drawable.label_button_default));
            holder.tv_note.setTextColor(context.getResources().getColor(R.color.main_home));
        }

        return convertView;
    }


    //接口回调
    public interface onClickMyTextView {
        void myTextViewClick(List<Integer> sl);
    }

    class ViewHolder {
        TextView tv_note;
        RelativeLayout rl_choose;
    }
}
