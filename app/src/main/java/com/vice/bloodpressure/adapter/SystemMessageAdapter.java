package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.SystemMsgListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodPressureSubmitActivity;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitBloodSugarSubmitActivity;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitHepatopathySubmitActivity;
import com.vice.bloodpressure.ui.activity.healthrecordadd.BloodSugarAddActivity;
import com.vice.bloodpressure.ui.activity.hospital.DepartmentListActivity;
import com.vice.bloodpressure.ui.activity.hospital.DoctorInfoDetailActivity;
import com.vice.bloodpressure.ui.activity.hospital.MakeDetailsActivity;
import com.vice.bloodpressure.ui.activity.patienteducation.PatientEducationAudioActivity;
import com.vice.bloodpressure.ui.activity.patienteducation.PatientEducationVideoActivity;
import com.vice.bloodpressure.ui.activity.smartanalyse.BloodPressureReportActivity;
import com.vice.bloodpressure.ui.activity.smartanalyse.BloodSugarReportActivity;
import com.vice.bloodpressure.ui.activity.sport.SportWeekPagerDetailActivity;
import com.vice.bloodpressure.ui.activity.sysmsg.SystemMsgDetailsActivity;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;
import java.util.List;

import io.rong.imkit.RongIM;

public class SystemMessageAdapter extends BaseQuickAdapter<SystemMsgListBean, BaseViewHolder> {
    public SystemMessageAdapter(@Nullable List<SystemMsgListBean> data) {
        super(R.layout.item_system_msg_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SystemMsgListBean item) {
        int position = helper.getLayoutPosition();

        ImageView imgPic = helper.getView(R.id.img_pic);
        ColorTextView tvRedPoint = helper.getView(R.id.tv_red_point);
        //1系统消息  2医生消息
        int sysmess = item.getSysmess();
        if (1 == sysmess) {
            imgPic.setImageResource(R.mipmap.ic_launcher);
        } else {
            imgPic.setImageResource(R.drawable.message_doctor);
        }
        //1已读      2未读
        int isread = item.getIsread();
        if (1 == isread) {
            tvRedPoint.setVisibility(View.GONE);
        } else {
            tvRedPoint.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, item.getAddtime())
                .setText(R.id.tv_desc, item.getNotice());
        //消息类型
        int type = item.getType();
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (type) {
                    //医生处理患者发送添加申请结果
                    case 1:
                        intent = new Intent(v.getContext(), DoctorInfoDetailActivity.class);
                        intent.putExtra("docid", item.getDocid());
                        break;
                    //医生处理患者预约住院申请结果
                    //跳转预约住院详情
                    case 2:
                        intent = new Intent(v.getContext(), MakeDetailsActivity.class);
                        intent.putExtra("id", item.getInhosid() + "");
                        break;
                    //融云消息(进聊天)
                    //推送打开APP
                    case 3:
                    case 15:
                        RongIM.getInstance().startPrivateChat(helper.itemView.getContext(), item.getDocid() + "", "我的医生");
                        break;
                    //血糖随访
                    case 4:
                    case 7:
                        intent = new Intent(v.getContext(), FollowUpVisitBloodSugarSubmitActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        break;
                    //血压随访
                    case 5:
                    case 8:
                        intent = new Intent(v.getContext(), FollowUpVisitBloodPressureSubmitActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        break;
                    //肝病随访
                    case 6:
                    case 9:
                        intent = new Intent(v.getContext(), FollowUpVisitHepatopathySubmitActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        break;
                    //降压
                    case 11:
                        //减重
                    case 12:
                        //降糖
                    case 13:
                        intent = new Intent(v.getContext(), BaseWebViewActivity.class);
                        intent.putExtra("title", "方案详情");
                        intent.putExtra("url", item.getUrl());
                        break;
                    //中医体质
                    case 14:
                        intent = new Intent(v.getContext(), BaseWebViewActivity.class);
                        intent.putExtra("title", "体质报告");
                        intent.putExtra("url", item.getUrl());
                        break;
                    //医生发送患者宣教
                    case 16:
                        int arttype = item.getArttype();
                        switch (arttype) {
                            case 1:
                                intent = new Intent(v.getContext(), PatientEducationVideoActivity.class);
                                intent.putExtra("title", item.getArticle_title());
                                intent.putExtra("url", item.getLinks());
                                intent.putExtra("videoUrl", item.getUrl());
                                break;
                            case 2:
                                intent = new Intent(v.getContext(), PatientEducationAudioActivity.class);
                                intent.putExtra("title", item.getArticle_title());
                                intent.putExtra("url", item.getLinks());
                                intent.putExtra("audioUrl", item.getUrl());
                                intent.putExtra("id", item.getPid());
                                intent.putExtra("audioDuration", item.getDuration());
                                break;
                            case 3:
                                intent = new Intent(v.getContext(), BaseWebViewActivity.class);
                                intent.putExtra("title", item.getArticle_title());
                                intent.putExtra("url", item.getLinks());
                                break;
                        }
                        break;
                    //医生发送科室公告
                    case 17:
                        intent = new Intent(v.getContext(), DepartmentListActivity.class);
                        intent.putExtra("docid", item.getDocid() + "");
                        break;
                    //血糖未测量提醒
                    //添加血糖
                    case 18:
                        intent = new Intent(v.getContext(), BloodSugarAddActivity.class);
                        break;
                    //血糖每月分析报告
                    case 20:
                        intent = new Intent(v.getContext(), BloodSugarReportActivity.class);
                        intent.putExtra("time", item.getStarttime());
                        break;
                    //血压每月分析报告
                    case 21:
                        intent = new Intent(v.getContext(), BloodPressureReportActivity.class);
                        intent.putExtra("time", item.getStarttime());
                        intent.putExtra("type", "3");
                        break;
                    //运动周报
                    case 22:
                        intent = new Intent(v.getContext(), SportWeekPagerDetailActivity.class);
                        intent.putExtra("beginTime", item.getStarttime());
                        intent.putExtra("endTime", item.getEndtime());
                        break;
                    default:
                        intent = new Intent(v.getContext(), SystemMsgDetailsActivity.class);
                        intent.putExtra("id", item.getPid() + "");
                        break;
                }
                if (intent != null) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
                //本地更改消息状态为已读
                item.setIsread(1);
                notifyItemChanged(position);
                //接口更改消息状态为已读
                changeIsRead(item.getPid());
            }
        });
    }

    private void changeIsRead(int pid) {
        HashMap map = new HashMap<>();
        map.put("pid", pid);
        XyUrl.okPostSave(XyUrl.GET_PORT_MESSAGE_EDIT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                EventBusUtils.post(new EventMessage(ConstantParam.WARN_COUNT_REFRESH));
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }
}
