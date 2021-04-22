package com.vice.bloodpressure.ui.activity.circle;

import android.content.Context;
import android.content.Intent;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.CommentsAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.CircleEntityBean;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/8.
 * 写评论-页面
 */

public class WriteCommentsActivity extends BaseHandlerActivity implements View.OnClickListener {

    private static final int GET_DATA = 0x998;
    private static final int LORD_MORE = 0x996;

    private View layout;
    private ImageView iv_write_pho;
    private TextView tv_write_name;
    private TextView tv_write_time;
    private TextView tv_write_shuoshuo;

    private TextView tv_browse_write;
    private TextView tv_answer_write;
    private RelativeLayout rlBottomWrite;
    private PopupWindow popupWindow;
    private EditText et_write_comments;
    private Button btn_pw_write;
    private Map<String, Object> map;
    private LoginBean user;
    private RecyclerView rcy_write_comments_me;//被评论者自己
    private RecyclerView rvWriteComments;//评论者
    //private CircleRecommendAdapter circleRecommendAdapter;
    private String photo;
    private List<String> ppp;//存放首页图片
    private CircleEntityBean circleEntity;
    private List<CircleEntityBean.CirclePing> circlePing;
    private List<CircleEntityBean.CirclePing> circlePingAdd;//上拉加载数据
    private Intent intent;
    private CommentsAdapter commentsAdapter;
    private int i = 2;
    private SmartRefreshLayout smartRefreshLayout;

    public WriteCommentsActivity() {
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                circlePing = (List<CircleEntityBean.CirclePing>) msg.obj;
                commentsAdapter = new CommentsAdapter(WriteCommentsActivity.this, circlePing, circleEntity);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(WriteCommentsActivity.this);
                linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                rvWriteComments.setLayoutManager(linearLayoutManager1);
                rvWriteComments.setAdapter(commentsAdapter);
                setFooterView(rvWriteComments);
                commentsAdapter.setOnItemClickListener(new CommentsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //                            intent = new Intent(WriteCommentsActivity.this, WriteCommentsTwoActivity.class);
                        //                            intent.putExtra("circlePing", (Serializable) circlePing.get(position));
                        //                            startActivity(intent);
                    }
                });
                break;

            case LORD_MORE:
                i++;
                commentsAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.item_rv_header, view, false);
        commentsAdapter.setHeaderView(footer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("问答圈子");
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        circleEntity = (CircleEntityBean) getIntent().getSerializableExtra("circleEntity");
        initViews();
        getData();
    }

    /**
     * 问答圈子详情
     */
    private void getData() {
        map = new HashMap<>();
        map.put("topicid", circleEntity.getTopicid());
        map.put("page", 1);
        //        HDUrl.okPostQuanZi(HDUrl.CIRCLE_DETAILS, map, new OkHttpCallBack<String>() {
        //            @Override
        //            public void onSuccess(String value) {
        //                circleEntity = JSONObject.parseObject(value, CircleEntityBean.class);
        //                circlePing = JSONObject.parseArray(circleEntity.getPing(), CircleEntityBean.CirclePing.class);
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
        //        iv_write_pho = layout.findViewById(R.id.iv_write_pho);
        //        tv_write_name = layout.findViewById(R.id.tv_write_name);
        //        tv_write_time = layout.findViewById(R.id.tv_write_time);
        //        tv_write_shuoshuo = layout.findViewById(R.id.tv_write_shuoshuo);
        //        tv_browse_write = layout.findViewById(R.id.tv_browse_write);
        //        tv_answer_write = layout.findViewById(R.id.tv_answer_write);
        rlBottomWrite = layout.findViewById(R.id.rl_bottom_write);
        rlBottomWrite.setOnClickListener(this);

        //        ImageLoaderUtil.loadImgQuanZi(iv_write_pho, circleEntity.getPicture(), R.mipmap.ic_launcher);
        //        tv_write_name.setText(circleEntity.getNickname());
        //        tv_browse_write.setText(circleEntity.getNum()+"次浏览");
        //        tv_answer_write.setText(circleEntity.getReply()+"个回答");
        //        tv_write_shuoshuo.setText(circleEntity.getTitle());
        //        tv_write_time.setText(TimeFormatUtils.getTimeFormatText(TimeFormatUtils.getStringToDate(circleEntity.getAddtime())));

        //        photo = circleEntity.getPic();
        //
        //        if(photo.contains(";")){
        //            ppp = new ArrayList<>();
        //            String p[] = photo.split(";");
        //            for(int i=0;i<p.length;i++){
        //                ppp.add(p[i]);
        //            }
        //        }else{
        //            ppp = new ArrayList<>();
        //            ppp.add(photo);
        //        }

        //        rcy_write_comments_me = layout.findViewById(R.id.rcy_write_comments_me);
        //        circleRecommendAdapter = new CircleRecommendAdapter(this, ppp);
        //        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        //        rcy_write_comments_me.setLayoutManager(layoutManager);
        //        rcy_write_comments_me.setAdapter(circleRecommendAdapter);

        smartRefreshLayout = layout.findViewById(R.id.srl_health_record_base);
        rvWriteComments = layout.findViewById(R.id.rv_write_comments);
        rvWriteComments.setNestedScrollingEnabled(false);//禁止滑动


        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishRefresh(2000);
                i = 2;
                map = new HashMap<>();
                map.put("topicid", circleEntity.getTopicid());
                map.put("page", 1);
                //                HDUrl.okPostQuanZi(HDUrl.CIRCLE_DETAILS, map, new OkHttpCallBack<String>() {
                //                    @Override
                //                    public void onSuccess(String value) {
                //                        circleEntity = JSONObject.parseObject(value, CircleEntityBean.class);
                //                        circlePing = JSONObject.parseArray(circleEntity.getPing(), CircleEntityBean.CirclePing.class);
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
            public void onLoadMore(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishLoadMore(2000);
                map = new HashMap<>();
                map.put("topicid", circleEntity.getTopicid());
                map.put("page", i);
                //                HDUrl.okPostQuanZi(HDUrl.CIRCLE_DETAILS, map, new OkHttpCallBack<String>() {
                //                    @Override
                //                    public void onSuccess(String value) {
                //                        circleEntity = JSONObject.parseObject(value, CircleEntityBean.class);
                //                        circlePingAdd = JSONObject.parseArray(circleEntity.getPing(), CircleEntityBean.CirclePing.class);
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
        layout = getLayoutInflater().inflate(R.layout.activity_write_comments, contentLayout, false);
        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bottom_write:
                backgroundAlpha(0.5f);
                replaceTel();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.btn_pw_write:
                sendWrite();
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
        popupWindow.setOnDismissListener(new poponDismissListener());
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

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }
}
