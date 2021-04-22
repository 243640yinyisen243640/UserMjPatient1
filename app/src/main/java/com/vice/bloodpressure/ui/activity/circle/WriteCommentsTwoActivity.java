package com.vice.bloodpressure.ui.activity.circle;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.CommentsTwoAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.CircleEntityBean;
import com.vice.bloodpressure.bean.CommentDetailsBean;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yicheng on 2018/12/12.
 * 评论的评论
 */

public class WriteCommentsTwoActivity extends BaseHandlerActivity implements View.OnClickListener {

    private static final int GET_DATA = 0x0096;
    private static final int LORD_MORE = 0x0097;

    private View layout;
    private CircleEntityBean.CirclePing circlePing1;
    private ImageView iv_comments_two_photo;
    private TextView tv_comments_two_name;
    private TextView tv_comments_two_time;
    private TextView tv_comments_two_shuoshuo;
    private Map<String, Object> map;
    private CommentDetailsBean commentDetails;
    private List<CommentDetailsBean.CirclePing> circlePing;
    private List<CommentDetailsBean.CirclePing> circlePingAdd;
    private RecyclerView rcy_comments_two;
    private SmartRefreshLayout smartRefreshLayout;
    private CommentsTwoAdapter commentsTwoAdapter;
    private RelativeLayout rl_comments_two;
    private PopupWindow popupWindow, popupWindow_two;
    private EditText et_write_comments;
    private Button btn_pw_write, btn_pw_write_two;
    private LoginBean user;

    private int i = 2;

    private int pos;

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                //                    commentDetails = (CommentDetailsBean) msg.obj;
                circlePing = (List<CommentDetailsBean.CirclePing>) msg.obj;
                //                    tv_comments_two_name.setText(commentDetails.getNickname());
                //                    tv_comments_two_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(commentDetails.getAddtime())));
                //                    ImageLoaderUtil.loadImgQuanZi(iv_comments_two_photo, commentDetails.getPicture(), R.mipmap.ic_launcher);
                //                    tv_comments_two_shuoshuo.setText(commentDetails.getInfo());

                //                    circlePing = JSONObject.parseArray(commentDetails.getPing(), CommentDetailsBean.CirclePing.class);

                commentsTwoAdapter = new CommentsTwoAdapter(WriteCommentsTwoActivity.this, circlePing, commentDetails);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(WriteCommentsTwoActivity.this);
                linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                rcy_comments_two.setLayoutManager(linearLayoutManager1);
                rcy_comments_two.setAdapter(commentsTwoAdapter);
                setFooterView(rcy_comments_two);

                commentsTwoAdapter.setOnItemClickListener(new CommentsTwoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        pos = position;
                        backgroundAlpha(0.5f);
                        replaceTel_two();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
                break;
            case LORD_MORE:
                i++;
                commentsTwoAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.item_rv_two_header, view, false);
        commentsTwoAdapter.setHeaderView(footer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("问答圈子");
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        circlePing1 = (CircleEntityBean.CirclePing) getIntent().getSerializableExtra("circlePing");
        initViews();
        getData();
    }

    /**
     * 问答圈子点击评论查看详情
     */
    private void getData() {
        map = new HashMap<>();
        map.put("pingid", circlePing1.getPingid());
        map.put("page", 1);
        //        HDUrl.okPostQuanZi(HDUrl.COMMENT_DETAILS, map, new OkHttpCallBack<String>() {
        //            @Override
        //            public void onSuccess(String value) {
        //                commentDetails = JSONObject.parseObject(value, CommentDetailsBean.class);
        //                circlePing = JSONObject.parseArray(commentDetails.getPing(), CommentDetailsBean.CirclePing.class);
        //                Message message = Message.obtain();
        //                message.what = GET_DATA;
        //                message.obj = circlePing;
        //                sendHandlerMessage(message);
        //            }
        //
        //            @Override
        //            public void onError(int error, String errorMsg) {
        //
        //            }
        //        });
    }

    private void initViews() {
        //        iv_comments_two_photo = layout.findViewById(R.id.iv_comments_two_photo);
        //        tv_comments_two_name = layout.findViewById(R.id.tv_comments_two_name);
        //        tv_comments_two_time = layout.findViewById(R.id.tv_comments_two_time);
        //        tv_comments_two_shuoshuo = layout.findViewById(R.id.tv_comments_two_shuoshuo);
        rl_comments_two = layout.findViewById(R.id.rl_comments_two);
        rl_comments_two.setOnClickListener(this);

        rcy_comments_two = layout.findViewById(R.id.rcy_comments_two);
        smartRefreshLayout = layout.findViewById(R.id.srl_health_record_base);
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                smartRefreshLayout.finishRefresh(2000);
                i = 2;
                map = new HashMap<>();
                map.put("pingid", circlePing1.getPingid());
                map.put("page", 1);
                //                HDUrl.okPostQuanZi(HDUrl.COMMENT_DETAILS, map, new OkHttpCallBack<String>() {
                //                    @Override
                //                    public void onSuccess(String value) {
                //                        commentDetails = JSONObject.parseObject(value, CommentDetailsBean.class);
                //                        circlePing = JSONObject.parseArray(commentDetails.getPing(), CommentDetailsBean.CirclePing.class);
                //                        Message message = Message.obtain();
                //                        message.what = GET_DATA;
                //                        message.obj = circlePing;
                //                        sendHandlerMessage(message);
                //                    }
                //
                //                    @Override
                //                    public void onError(int error, String errorMsg) {
                //
                //                    }
                //                });
            }
        });
        //上拉加载
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                smartRefreshLayout.finishLoadMore(2000);
                map = new HashMap<>();
                map.put("pingid", circlePing1.getPingid());
                map.put("page", i);
                //                HDUrl.okPostQuanZi(HDUrl.COMMENT_DETAILS, map, new OkHttpCallBack<String>() {
                //                    @Override
                //                    public void onSuccess(String value) {
                //                        commentDetails = JSONObject.parseObject(value, CommentDetailsBean.class);
                //                        circlePingAdd = JSONObject.parseArray(commentDetails.getPing(), CommentDetailsBean.CirclePing.class);
                //                        circlePing.addAll(circlePingAdd);
                //                        Message message = Message.obtain();
                //                        message.what = LORD_MORE;
                //                        message.obj = circlePing;
                //                        sendHandlerMessage(message);
                //                    }
                //
                //                    @Override
                //                    public void onError(int error, String errorMsg) {
                //                        if (errorMsg.equals("数据为空")) {
                //                            ToastUtils.showShort("已经到底啦~");
                //                        }
                //                    }
                //                });
            }
        });
    }

    @Override
    protected View addContentLayout() {
        layout = getLayoutInflater().inflate(R.layout.activity_write_comments_two, contentLayout, false);
        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_comments_two:
                backgroundAlpha(0.5f);
                replaceTel();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.btn_pw_write:
                sendWrite();
                break;
            case R.id.btn_pw_write_two:
                sendWriteTwo();
                break;
        }
    }

    /**
     * 发布评论
     */
    private void sendWrite() {
    }

    /**
     * 写评论
     */
    private void replaceTel() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pw_write_comments, null);
        popupWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.setOnDismissListener(new WriteCommentsTwoActivity.poponDismissListener());
        et_write_comments = contentView.findViewById(R.id.et_write_comments);
        btn_pw_write = contentView.findViewById(R.id.btn_pw_write);
        et_write_comments.setOnClickListener(this);
        btn_pw_write.setOnClickListener(this);
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_write_comments, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        this.getWindow().setAttributes(lp); //act 是上下文context
    }

    private void replaceTel_two() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pw_write_comments_two, null);
        popupWindow_two = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow_two.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_two.setOutsideTouchable(true);
        popupWindow_two.setContentView(contentView);
        popupWindow_two.setOnDismissListener(new WriteCommentsTwoActivity.poponDismissListener());
        et_write_comments = contentView.findViewById(R.id.et_write_comments);
        btn_pw_write_two = contentView.findViewById(R.id.btn_pw_write_two);
        et_write_comments.setOnClickListener(this);
        btn_pw_write_two.setOnClickListener(this);
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_write_comments, null);
        popupWindow_two.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    /**
     * 发布评论2
     */
    private void sendWriteTwo() {

    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }
}
