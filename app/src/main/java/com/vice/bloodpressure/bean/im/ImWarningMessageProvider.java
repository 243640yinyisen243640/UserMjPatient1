package com.vice.bloodpressure.bean.im;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.imp.ImWarningClickListener;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * 描述:  融云消息提供者
 * 作者: LYD
 * 创建日期: 2019/6/11 16:22
 */
@ProviderTag(messageContent = ImWarningMessage.class, showProgress = false, showReadState = true)
public class ImWarningMessageProvider extends IContainerItemProvider.MessageProvider<ImWarningMessage> {
    private static final String TAG = "ImWarningMessageProvider";
    private ImWarningClickListener listener;

    public ImWarningMessageProvider(ImWarningClickListener listener) {
        this.listener = listener;
    }

    /**
     * 初始化 View。
     *
     * @param context
     * @param viewGroup
     * @return
     */
    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_im_msg_layout, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
        viewHolder.tvTime = convertView.findViewById(R.id.tv_time);
        viewHolder.tvLeftDesc = convertView.findViewById(R.id.tv_left_desc);
        viewHolder.tvRightContent = convertView.findViewById(R.id.tv_right_content);
        viewHolder.tvDosage = convertView.findViewById(R.id.tv_dosage);
        viewHolder.llType3 = convertView.findViewById(R.id.ll_type_3);
        viewHolder.tvLeftStateDesc = convertView.findViewById(R.id.tv_left_state_desc);
        viewHolder.tvRightStateContent = convertView.findViewById(R.id.tv_right_state_content);
        viewHolder.tvLookDeal = convertView.findViewById(R.id.tv_look_deal);
        viewHolder.imgState = convertView.findViewById(R.id.img_state);
        viewHolder.flBg = convertView.findViewById(R.id.fl_bg);
        convertView.setTag(viewHolder);
        return convertView;
    }

    /**
     * 将数据填充View上
     *
     * @param view
     * @param i
     * @param imWarningMessage
     * @param uiMessage
     */
    @Override
    public void bindView(View view, int i, ImWarningMessage imWarningMessage, UIMessage uiMessage) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        //获取消息内容并解析
        String content = imWarningMessage.getContent();
        ImWarningMessageContentBean bean = GsonUtils.fromJson(content, ImWarningMessageContentBean.class);
        //1血压 2血糖 3肝病
        int type = bean.getType();
        String time = bean.getWtime();
        String title = bean.getTitle();
        String typename = bean.getTypename();
        String val = bean.getVal();
        String result = bean.getResult();
        String status = bean.getStatus();
        String unit = bean.getUnit();
        viewHolder.tvRightContent.setText(val);
        viewHolder.tvRightStateContent.setText(status);
        viewHolder.tvDosage.setText(unit);
        viewHolder.tvTitle.setText(title + "测量通知");
        //空格占位
        viewHolder.tvTime.setText(time + "                              ");
        //判断类型
        switch (type) {
            case 1:
                viewHolder.tvLeftDesc.setText("血压值");
                viewHolder.tvLeftStateDesc.setText("血压状态");
                viewHolder.imgState.setVisibility(View.VISIBLE);
                viewHolder.llType3.setVisibility(View.VISIBLE);
                break;
            case 2:
                viewHolder.tvLeftDesc.setText(typename);
                viewHolder.tvLeftStateDesc.setText("血糖状态");
                viewHolder.imgState.setVisibility(View.VISIBLE);
                viewHolder.llType3.setVisibility(View.VISIBLE);
                break;
            case 3:
                viewHolder.tvLeftDesc.setText("测量值");
                viewHolder.imgState.setVisibility(View.GONE);
                viewHolder.llType3.setVisibility(View.GONE);
                break;
        }
        //血糖血压状态
        if ("未处理".equals(result)) {
            viewHolder.imgState.setImageResource(R.drawable.im_msg_doing);
            viewHolder.tvLookDeal.setText("点击处理");
        } else {
            viewHolder.imgState.setImageResource(R.drawable.im_msg_done);
            viewHolder.tvLookDeal.setText("点击查看");
        }
        if (uiMessage.getMessageDirection() == Message.MessageDirection.RECEIVE) {
            viewHolder.flBg.setBackgroundResource(R.drawable.rc_ic_bubble_left_file);
        } else {
            viewHolder.flBg.setBackgroundResource(R.drawable.rc_ic_bubble_right_file);
        }
    }

    /**
     * 会话列表中显示
     *
     * @param imWarningMessage
     * @return
     */
    @Override
    public Spannable getContentSummary(ImWarningMessage imWarningMessage) {
        String content = imWarningMessage.getContent();
        ImWarningMessageContentBean bean = GsonUtils.fromJson(content, ImWarningMessageContentBean.class);
        //1血压 2血糖 3肝病
        int type = bean.getType();
        String val = bean.getVal();
        String status = bean.getStatus();
        String unit = bean.getUnit();
        if (1 == type) {
            return new SpannableString("血压值" + "  " + val + unit + "  " + status);
        } else if (2 == type) {
            String typename = bean.getTypename();
            return new SpannableString(typename + "  " + val + unit + "  " + status);
        } else {
            String typename = bean.getTitle();
            return new SpannableString(typename + "  " + val + "  " + unit);
        }
    }

    /**
     * 点击该类型消息触发。
     *
     * @param view
     * @param i
     * @param content
     * @param uiMessage
     */
    @Override
    public void onItemClick(View view, int i, ImWarningMessage content, UIMessage uiMessage) {
        if (listener != null) {
            listener.onCardClick(view, content);
        }
    }


    private static class ViewHolder {
        //标题
        TextView tvTitle;
        //时间
        TextView tvTime;

        TextView tvLeftDesc;
        TextView tvRightContent;
        TextView tvDosage;
        LinearLayout llType3;

        TextView tvLeftStateDesc;
        TextView tvRightStateContent;

        TextView tvLookDeal;

        ImageView imgState;
        FrameLayout flBg;
    }
}
